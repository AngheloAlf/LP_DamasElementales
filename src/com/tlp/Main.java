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
    private ArrayList<ArrayList<FichasTipos>> fichitas = FichasTipos.HacerListaFichas();
    private FichasTipos datos = new FichasTipos(pos, Element.Type.values()[0], -1);
    public Main()
    {
        // aquí puede inicializar valores y crear los objetos de juego.
    }

    @Override
    public void update(MouseHandler mouseHandler)
    {
        // aquí actualice sus objetos para que puedan interactuar con input de usuario o entre los mismos
        // objetos.

        // por ejemplo imprimir algo si el mouse está cerca de la esquina superior izquierda:
        pos = mouseHandler.getMousePosition();

        if (mouseHandler.isButtonJustPressed())
        {
            if(!datos.IsPressed()) {
                FichasTipos aux  = FichasTipos.GetFichasTipos(fichitas, pos.x, pos.y);
                if (aux != null)
                {
                    datos.CopyFicha(aux);
                }
            } else {
                System.out.println("else");

                //datos.Press(false);

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
    }

    public void DrawFicha(Graphics graphics, FichasTipos fichaIteracion, boolean j1, Point fichaPos)
    {
        graphics.setColor(fichaIteracion.GetColor());
        graphics.fillOval(fichaPos.x, fichaPos.y, 30, 30);
        if (j1)
        {
            graphics.setColor(Color.orange);
        } else {
            graphics.setColor(Color.LIGHT_GRAY);
        }
        graphics.fillOval(fichaPos.x+9, fichaPos.y+9, 12, 12);
    }

    public void DibujarFichas(Graphics graphics)
    {
        boolean j1 = true;
        for (ArrayList<FichasTipos> fichaJ : fichitas)
        {
            for (FichasTipos fichaIteracion: fichaJ)
            {
                if (!fichaIteracion.IsPressed())
                {
                    DrawFicha(graphics, fichaIteracion, j1,  fichaIteracion.GetPos());
                }
            }
            j1 = !j1;
        }
    }

    public void DibujarFichaMouse(Graphics graphics){
        if (datos.IsPressed())
        {
            if (datos.GetID()%2 == 1){
                DrawFicha(graphics, datos, false, pos);
            } else {
                DrawFicha(graphics, datos, true, pos);
            }
        }
    }

    @Override
    public void render(Graphics graphics)
    {
        // aquí, y solo aquí, puede dibujar cosas en la pantalla.
        tablero(graphics);
        // por ejemplo dibujar un círculo verde:
        //graphics.setColor(Color.GREEN);
        //graphics.fillOval(pos.x, pos.y, 30, 30);

        DibujarFichas(graphics);

        DibujarFichaMouse(graphics);

    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(600, 600, "Pokemon", 60, new Main());
    }
}