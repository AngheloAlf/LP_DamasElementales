package com.tlp;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anghelo on 08-Dec-15.
 */
public class Tablero {
    public static void dibujarTablero(Graphics p)
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

    public static void dibujarPuntuacion(Graphics p, boolean turnoJ1, ArrayList<FichasTipos> fichitas)
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


    public static void drawFicha(Graphics graphics, FichasTipos fichaIteracion, boolean j1, Point fichaPos)
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

    public static void dibujarFichas(Graphics graphics, ArrayList<FichasTipos> fichitas)
    {
        for (FichasTipos fichaIteracion: fichitas)
        {
            if (!fichaIteracion.isPressed() && !fichaIteracion.isComida())
            {
                drawFicha(graphics, fichaIteracion, fichaIteracion.getID()%2 == 0, fichaIteracion.getPos());
            }
        }
    }

    public static void dibujarFichaMouse(Graphics graphics, FichasTipos datos, Point pos){
        if (datos.isPressed())
        {
            if (datos.getID()%2 == 1){
                drawFicha(graphics, datos, false, pos);
            } else {
                drawFicha(graphics, datos, true, pos);
            }
        }
    }
}
