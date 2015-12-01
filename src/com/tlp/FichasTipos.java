package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */
public class FichasTipos extends Ficha {
    Element.Type tipo;
    boolean presionada = false;

    public FichasTipos(Point pos, Element.Type tipo) {
        super(pos);
        this.tipo = tipo;
    }

    public Element.Type GetType()
    {
        return this.tipo;
    }

    public void SetType(Element.Type tipo)
    {
        this.tipo = tipo;
    }

    public static ArrayList<ArrayList<FichasTipos>> HacerListaFichas()
    {
        ArrayList<FichasTipos> fichasJ1 = new ArrayList<FichasTipos>();
        ArrayList<FichasTipos> fichasJ2 = new ArrayList<FichasTipos>();
        Point contadorPos1 = new Point(75, 15);
        Point contadorPos2 = new Point(75, 375);

        FichasTipos wea;
        Random rand = new Random();
        Element.Type[] c =  Element.Type.values();
        int randomNumber;

        for (int i = 0; i < 20; i++)
        {
            randomNumber = rand.nextInt(3);
            wea = new FichasTipos(contadorPos1, c[randomNumber]);
            fichasJ1.add(wea);

            randomNumber = rand.nextInt(3);
            wea = new FichasTipos(contadorPos2, c[randomNumber]);
            fichasJ2.add(wea);

            contadorPos1 = new Point(contadorPos1.x, contadorPos1.y);
            contadorPos2 = new Point(contadorPos2.x, contadorPos2.y);
            contadorPos1.x += 120;
            contadorPos2.x += 120;
            if ((i + 1) % 5 == 0) {
                contadorPos1.y += 60;
                contadorPos2.y += 60;
            }

            if ((i == 14) || (i == 4))
            {
                contadorPos1.x = 15;
                contadorPos2.x = 15;
            } else {
                if ((i == 9))
                {
                    contadorPos1.x = 75;
                    contadorPos2.x = 75;
                }
            }
        }

        ArrayList<ArrayList<FichasTipos>> fichitas = new ArrayList<ArrayList<FichasTipos>>();
        fichitas.add(fichasJ1);
        fichitas.add(fichasJ2);
        return fichitas;
    }

    public boolean IsPressed()
    {
        return this.presionada;
    }

    public void Press(boolean presionada)
    {
        this.presionada = presionada;
    }

    public static FichasTipos GetFichasTipos(ArrayList<ArrayList<FichasTipos>> fichitas, int posx, int posy)
    {
        Point fichaPos;
        for (ArrayList<FichasTipos> fichaJ : fichitas)
        {
            for (FichasTipos fichaIteracion : fichaJ)
            {
                fichaPos = fichaIteracion.GetPos();
                if (((fichaPos.x - 15) < posx) && (posx < (fichaPos.x + 45) ))
                {
                    if (((fichaPos.y - 15) < posy) && (posy < (fichaPos.y + 45) ))
                    {
                        System.out.println("Seleccionaste una ficha");
                        fichaIteracion.Press(true);
                        return fichaIteracion;
                    }
                }
            }
        }
        System.out.println("NOPE");

        return null;
    }

    public static Color GetColor(FichasTipos fichaIteracion)
    {
        Element.Type[] c =  Element.Type.values();
        if (fichaIteracion.GetType() == c[0])
        {
            return Color.RED;
        }
        if (fichaIteracion.GetType() == c[1])
        {
            return Color.BLUE;
        }
        if (fichaIteracion.GetType() == c[2])
        {
            return Color.GREEN;
        }
        return Color.YELLOW;
    }
}
