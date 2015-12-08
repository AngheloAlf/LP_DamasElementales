import com.tlp.FichasTipos;
import com.tlp.FichasPowerUps;
import lp.motor.Application;
import lp.motor.Context;
import lp.motor.Element;
import lp.motor.MouseHandler;

import java.awt.*;
import java.util.ArrayList;

public class Main implements Context
{
    private Point pos;
    private ArrayList<FichasTipos> fichitas = FichasTipos.hacerListaFichas();
    private FichasTipos datos = new FichasTipos(pos, Element.Type.values()[0], -1);
    private FichasTipos aux;
    private boolean turnoJ1 = true;

    @Override
    public void update(MouseHandler mouseHandler)
    {
        // aquí actualice sus objetos para que puedan interactuar con input de usuario o entre los mismos
        // objetos.

        pos = mouseHandler.getMousePosition();
        if (mouseHandler.isButtonJustPressed())
        {
            aux = FichasTipos.getFichasTipos(fichitas, pos.x, pos.y);
            if (!datos.isPressed())
            {
                System.out.println("if (!datos.isPressed())");
                if (aux != null)
                {
                    System.out.println("\tif (aux != null)");
                    if ((turnoJ1) && (aux.getID()%2 == 0))
                    {
                        System.out.println("\t\tif ((turnoJ1) && (aux.getID()%2 == 0))");
                        aux.press(true);
                        datos.copyFicha(aux);
                    }
                    if (!(turnoJ1) && (aux.getID()%2 == 1))
                    {
                        System.out.println("\t\tif (!(turnoJ1) && (aux.getID()%2 == 1))");
                        aux.press(true);
                        datos.copyFicha(aux);
                    }
                }
            } else {
                System.out.println("if (datos.isPressed())");
                if (aux == null)
                {
                    System.out.println("\tif (aux == null)");
                    if (FichasTipos.placeFicha(fichitas, datos.getID(), pos, true))
                    {
                        System.out.println("\t\tif (FichasTipos.placeFicha(fichitas, datos.getID(), pos, true))");
                        datos.press(false);
                        turnoJ1 = !turnoJ1;
                    }
                } else {
                    System.out.println("\tif (aux != null)");
                    if (aux.isPressed())
                    {
                        System.out.println("\t\tif (aux.isPressed())");
                        if (FichasTipos.placeFicha(fichitas, datos.getID(), datos.getPos(), false))
                        {
                            System.out.println("\t\t\tif (FichasTipos.placeFicha(fichitas, datos.getID(), pos, false))");
                            datos.press(false);
                        }
                    }
                }
            }
        }
    }

    public void tablero(Graphics p)
    {
        boolean black=false;
        int x,y;
        for (x=0;x<10;x++)
        {
            black=!black;
            for(y=0;y<10;y++)
            {
                if (black)
                {
                    p.setColor(Color.BLACK);
                } else {
                    p.setColor(Color.WHITE);
                }
                black=!black;
                p.fillRect(x*60,y*60,60,60);
            }
        }
    }

    public void dibujarPuntuacion(Graphics p)
    {
        p.setColor(Color.GRAY);
        p.fillRect(600, 0, 200, 600);

        p.setColor(Color.BLACK);
        p.drawString("Jugador 1", 620, 25);
        p.drawString("Jugador 2", 620, 575);

        p.drawString("Turno de:", 620, 290);
        if (turnoJ1)
        {
            p.drawString("Jugador 1", 640, 310);
        } else {
            p.drawString("Jugador 2", 640, 310);
        }

        int puntos1 = 0;
        int puntos2 = 0;
        for (FichasTipos fichaIteracion: fichitas)
        {
            if(fichaIteracion.isComida())
            {
                if(fichaIteracion.getID()%2 == 1)
                {
                    puntos1 += 1;
                } else {
                    puntos2 += 1;
                }
            }
        }

        p.drawString("Fichas comidas: ", 630, 50);
        p.drawString(String.valueOf(puntos1), 730, 50);
        p.drawString("Fichas comidas: ", 630, 550);
        p.drawString(String.valueOf(puntos2), 730, 550);
    }

    public void drawFicha(Graphics graphics, FichasTipos fichaIteracion, boolean j1, Point fichaPos)
    {
        graphics.setColor(fichaIteracion.getColor());
        graphics.fillOval(fichaPos.x, fichaPos.y, 30, 30);
        if (j1)
        {
            graphics.setColor(Color.orange);
        } else {
            graphics.setColor(Color.LIGHT_GRAY);
        }
        graphics.fillOval(fichaPos.x+9, fichaPos.y+9, 12, 12);
    }

    public void dibujarFichas(Graphics graphics)
    {
        for (FichasTipos fichaIteracion: fichitas)
        {
            if (!fichaIteracion.isPressed() && !fichaIteracion.isComida())
            {
                drawFicha(graphics, fichaIteracion, fichaIteracion.getID()%2 == 0, fichaIteracion.getPos());
            }
        }
    }

    public void dibujarFichaMouse(Graphics graphics){
        if (datos.isPressed())
        {
            if (datos.getID()%2 == 1){
                drawFicha(graphics, datos, false, pos);
            } else {
                drawFicha(graphics, datos, true, pos);
            }
        }
    }

    @Override
    public void render(Graphics graphics)
    {
        // aquí, y solo aquí, puede dibujar cosas en la pantalla.
        tablero(graphics);

        dibujarFichas(graphics);

        dibujarFichaMouse(graphics);

        dibujarPuntuacion(graphics);
    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(800, 600, "Pokemon", 60, new Main());
    }
}