package com.tlp;

import lp.motor.Element;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */

public class FichasPowerUps extends Ficha {
    protected boolean activa = true;
    protected int tipoUp;

    public FichasPowerUps(Point pos, int id, int tipoUp) {
        super(pos, id);
        this.tipoUp = tipoUp;
    }

    public boolean isActiva()
    {
        return this.activa;
    }

    public void deActivate()
    {
        this.activa = false;
    }

    public int getType()
    {
        return this.tipoUp;
    }

    public void setType(int tipo)
    {
        this.tipoUp = tipo;
    }

    public Color getColor()
    {
        if (this.tipoUp == 0)
        {
            return Color.BLACK;
        }
        if (this.tipoUp == 1)
        {
            return Color.YELLOW;
        }
        if (this.tipoUp == 2)
        {
            return Color.PINK;
        }
        if (this.tipoUp == 3)
        {
            return Color.lightGray;
        }
        if (this.tipoUp == 4)
        {
            return Color.CYAN;
        }
        return Color.WHITE;
    }

    public void changeTypeFicha(FichasTipos fichaIteracion)
    {
        int tipo = FichasTipos.getCorrespondingType(fichaIteracion.getType());
        tipo = -1;
        if (tipo < 0)
        {
            tipo = 2;
        }
        Element.Type[] c = Element.Type.values();
        fichaIteracion.setType(c[tipo]);
    }

    public static void transformar(ArrayList<FichasTipos> fichitas, int id)
    {
        FichasTipos aux = null;
        int ultimoID = -1;
        for (FichasTipos fichaIteracion: fichitas)
        {
            ultimoID = fichaIteracion.getID();
            if(ultimoID == id)
            {
                aux = fichaIteracion;
            }
        }
        if (aux != null)
        {
            if(id%2 == ultimoID%2)
            {
                aux.setID(ultimoID + 1);
            } else {
                aux.setID(ultimoID + 2);
            }
        }
    }

    public static void agregarFichaRandom(ArrayList<FichasTipos> fichitas, ArrayList<FichasPowerUps> fichitasUps)
    {

        Random rand = new Random();
        int randomX = rand.nextInt(5)*2;
        int randomY = rand.nextInt(10);
        System.out.println(randomX+" "+randomY);

        if (randomY % 2 == 0)
        {
            randomX = randomX + 1;
        }

        randomX = randomX * 60 + 15;
        randomY = randomY * 60 + 15;

        for (FichasTipos fichaIteracion: fichitas)
        {
            if ((fichaIteracion.getPos().x == randomX) && (fichaIteracion.getPos().y == randomY))
            {
                return;
            }
        }

        for (FichasPowerUps fichaIteracionUps: fichitasUps)
        {
            if ((fichaIteracionUps.getPos().x == randomX) && (fichaIteracionUps.getPos().y == randomY))
            {
                return;
            }
        }
        int ultimoid = 0;
        for (FichasPowerUps fichitasIteracionUps: fichitasUps)
        {
            if (fichitasIteracionUps.getID() > ultimoid)
            {
                ultimoid = fichitasIteracionUps.getID();
            }
        }
        Point posUps = new Point(randomX, randomY);
        System.out.println(posUps);
        FichasPowerUps nuevaFicha = new FichasPowerUps(posUps, ultimoid+1, rand.nextInt(5));
        fichitasUps.add(nuevaFicha);
    }

    public void usarPowerUps() {
        int tipo = this.getType();
        if (tipo == 0)
        {

        }
        if (tipo == 1)
        {

        }
        if (tipo == 2)
        {

        }
        if (tipo == 3)
        {

        }
        if (tipo == 4)
        {

        }


        // ultima linea
        this.deActivate();
    }
}
