package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nontraxx on 27-11-15.
 */
public class Ficha {
    int posx, posy;
    Element.Type tipo;

    public Ficha(int x, int y, Element.Type tipo)
    {
        this.tipo=tipo;
        this.posx=x;
        this.posy=y;
    }

    public Element.Type GetType()
    {
        return this.tipo;
    }

    public int GetX()
    {
        return this.posx;
    }

    public int GetY()
    {
        return this.posy;
    }

    public void SetType(Element.Type tipo)
    {
        this.tipo = tipo;
    }

    public void SetPos(int posx, int posy)
    {
        this.posx = posx;
        this.posy = posy;
    }


    public static ArrayList<ArrayList<Ficha>> HacerListaFichas()
    {
        ArrayList<Ficha> fichasJ1 = new ArrayList<Ficha>();
        ArrayList<Ficha> fichasJ2 = new ArrayList<Ficha>();
        int contadorX = 75;
        int contadorY = 15;
        Ficha wea;
        Random rand = new Random();
        Element.Type[] c =  Element.Type.values();
        int randomNumber;

        for (int i = 0; i < 20; i++)
        {
            randomNumber = rand.nextInt(3);
            wea = new Ficha(contadorX, contadorY, c[randomNumber]);
            fichasJ1.add(wea);

            randomNumber = rand.nextInt(3);
            wea = new Ficha(contadorX, contadorY+360, c[randomNumber]);
            fichasJ2.add(wea);

            contadorX += 120;
            if ((i + 1) % 5 == 0) {
                contadorY += 60;
            }

            if ((i == 14) || (i == 4))
            {
                contadorX = 15;
            } else {
                if ((i == 9))
                {
                    contadorX = 75;
                }
            }
        }
        ArrayList<ArrayList<Ficha>> fichitas = new ArrayList<ArrayList<Ficha>>();
        fichitas.add(fichasJ1);
        fichitas.add(fichasJ2);
        return fichitas;
    }

}
