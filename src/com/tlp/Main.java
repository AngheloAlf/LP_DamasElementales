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
                    aux.press(true);
                    datos.copyFicha(aux);
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
                    }
                } else {
                    System.out.println("\tif (aux != null)");
                    if (aux.isPressed())
                    {
                        System.out.println("\t\tif (aux.isPressed()");
                        if (FichasTipos.placeFicha(fichitas, datos.getID(), datos.getPos(), false))
                        {
                            System.out.println("\t\t\tif (FichasTipos.placeFicha(fichitas, datos.getID(), pos, true))");
                            datos.press(false);
                        }
                    }
                }
            }
        }

        //if (pos.x < 200 && pos.y < 200) System.out.println(pos);
    }

    public void tablero(Graphics p)
    {   boolean black=false;
        int x,y;
        for (x=0;x<10;x++)
        {
            black=!black;
            for(y=0;y<10;y++)
            {
                if (black){p.setColor(Color.BLACK);}
                else {p.setColor(Color.WHITE);}
                black=!black;
                p.fillRect(x*60,y*60,60,60);
            }
        }
        //p.setColor(Color.GRAY);
        //p.fillRect(600, 0, 200, 600);
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
            if (!fichaIteracion.isPressed())
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
    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(600, 600, "Pokemon", 60, new Main());
    }
}