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

    public FichasTipos(Point pos, Element.Type tipo, int id) {
        super(pos, id);
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
        int contadorID = 0;

        for (int i = 0; i < 20; i++)
        {
            randomNumber = rand.nextInt(3);
            wea = new FichasTipos(contadorPos1, c[randomNumber], contadorID);
            fichasJ1.add(wea);
            contadorID += 1;

            randomNumber = rand.nextInt(3);
            wea = new FichasTipos(contadorPos2, c[randomNumber], contadorID);
            fichasJ2.add(wea);
            contadorID += 1;

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
                        //fichaIteracion.Press(true);
                        return fichaIteracion;
                    }
                }
            }
        }
        System.out.println("NULL");

        return null;
    }

    public static Point ArreglarPos(Point newFichaPos)
    {
        newFichaPos.x = (newFichaPos.x/60)*60 + 15;
        newFichaPos.y = (newFichaPos.y/60)*60 + 15;
        return newFichaPos;
    }

    public static void PlaceFicha(ArrayList<ArrayList<FichasTipos>> fichitas, int id, Point newFichaPos)
    {
        for (ArrayList<FichasTipos> fichaJ : fichitas)
        {
            for (FichasTipos fichaIteracion : fichaJ)
            {
                if (fichaIteracion.GetID() == id){
                    fichaIteracion.SetPos(ArreglarPos(newFichaPos));
                    fichaIteracion.Press(false);
                }
            }
        }
    }

    public Color GetColor()
    {
        Element.Type[] c =  Element.Type.values();
        if (this.GetType() == c[0])
        {
            return Color.RED;
        }
        if (this.GetType() == c[1])
        {
            return Color.BLUE;
        }
        if (this.GetType() == c[2])
        {
            return Color.GREEN;
        }
        return Color.YELLOW;
    }

    public void CopyFicha(FichasTipos fichaIteracion){
        this.SetType(fichaIteracion.GetType());
        this.Press(fichaIteracion.IsPressed());
        this.SetPos(fichaIteracion.GetPos());
        this.SetID(fichaIteracion.GetID());
    }
}
