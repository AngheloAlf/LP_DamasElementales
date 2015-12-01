import com.tlp.Ficha;
import lp.motor.Application;
import lp.motor.Context;
import lp.motor.Element;
import lp.motor.MouseHandler;

import java.awt.*;
import java.util.ArrayList;

public class Main implements Context
{
    private Point pos;
    private ArrayList<ArrayList<Ficha>> fichitas = Ficha.HacerListaFichas();
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
        /*if (pos.x < 200 && pos.y < 200)
        System.out.println(pos);*/
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

    public void DibujarFichas(Graphics graphics)
    {
        Element.Type[] c =  Element.Type.values();
        for (ArrayList<Ficha> fichaJ : fichitas)
        {
            for (Ficha fichaIteracion: fichaJ)
            {
                if (fichaIteracion.GetType() == c[0])
                {
                    graphics.setColor(Color.RED);
                }
                if (fichaIteracion.GetType() == c[1])
                {
                    graphics.setColor(Color.BLUE);
                }
                if (fichaIteracion.GetType() == c[2])
                {
                    graphics.setColor(Color.GREEN);
                }
                graphics.fillOval(fichaIteracion.GetX(), fichaIteracion.GetY(), 30, 30);
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
    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(600, 600, "Pokemon", 60, new Main());
    }
}