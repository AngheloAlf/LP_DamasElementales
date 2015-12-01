package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nontraxx on 27-11-15.
 */
public class Ficha {
    int posx,posy;
    Element.Type tipo;
    Graphics pant;

    public Ficha(Graphics g,int x, int y)
    {
        this.pant=g;
        this.posx=x;
        this.posy=y;
    }

    public static ArrayList<ArrayList<Ficha>> HacerListaFichas(Graphics graphics)
    {
        ArrayList<Ficha> fichasJ1 = new ArrayList<Ficha>();
        ArrayList<Ficha> fichasJ2 = new ArrayList<Ficha>();
        int contadorX = 75;
        int contadorY = 15;
        Ficha wea;
        for (int i = 0; i < 20; i++) {
            graphics.setColor(Color.BLACK);
            wea = new Ficha(graphics, contadorX, contadorY);
            fichasJ1.add(wea);

            graphics.setColor(Color.WHITE);
            wea = new Ficha(graphics, contadorX, contadorY);
            fichasJ2.add(wea);

            contadorX += 120;
            if ((i + 1) % 5 == 0) {
                contadorY += 120;
            }

            if (((i+1) % 15 == 0) || ((i + 1) % 5 == 0)) {
                contadorX = 15;
            } else {
                if ((i+1) % 10 == 0){
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
