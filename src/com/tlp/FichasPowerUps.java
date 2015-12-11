package com.tlp;

import lp.motor.Element;
import lp.motor.MouseHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */

public class FichasPowerUps extends Ficha {
    protected boolean activa = true;
    protected int tipoUp;
    protected int dueno = -1;
    protected int contador = 0;

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

    public void setDueno(int dueno)
    {
        this.dueno = dueno;
    }

    public int getDueno()
    {
        return this.dueno;
    }

    public void aumentarContador()
    {
        this.contador += 1;
    }

    public int getContador()
    {
        return this.contador;
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
            Random rand = new Random();
            int numeroRandom = rand.nextInt(3);
            if (numeroRandom == 0)
            {
                return Color.RED;
            }
            if (numeroRandom == 1)
            {
                return Color.BLUE;
            }
            if (numeroRandom == 2)
            {
                return Color.GREEN;
            }
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
        tipo -= 1;
        if (tipo < 0)
        {
            tipo = 2;
        }
        Element.Type[] c = Element.Type.values();
        fichaIteracion.setType(c[tipo]);
    }

    public static boolean transformar(ArrayList<FichasTipos> fichitas, Point pos, int id)
    {
        FichasTipos aux = null;
        int ultimoID = -1;
        for (FichasTipos fichaIteracion: fichitas)
        {
            ultimoID = fichaIteracion.getID();
            if ((fichaIteracion.getPos().x == pos.x) && (fichaIteracion.getPos().y == pos.y))
            {
                aux = fichaIteracion;
            }
        }
        if ((aux != null) && (aux.getID()%2 != id%2))
        {
            if (aux.getID()%2 == ultimoID%2)
            {
                aux.setID(ultimoID + 1);
            } else {
                aux.setID(ultimoID + 2);
            }
            return true;
        }
        return false;
    }

    public static void agregarFichaRandom(ArrayList<FichasTipos> fichitas, ArrayList<FichasPowerUps> fichitasUps)
    {
        Random rand = new Random();
        int randomX = rand.nextInt(5)*2;
        int randomY = rand.nextInt(10);

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
        FichasPowerUps nuevaFicha = new FichasPowerUps(posUps, ultimoid+1, 0);///rand.nextInt(5));
        fichitasUps.add(nuevaFicha);
    }

    public boolean usarPowerUps(boolean proceso, ArrayList<FichasTipos> fichitas, FichasTipos datos, int idF, Point pos, boolean turnoJ1) {
        int tipo = this.getType();
        if (tipo == 0)
        {
            if (this.getContador() == 0 && (Tablero.tomarFicha(datos, Tablero.getFichasTipos(fichitas, pos.x, pos.y), !turnoJ1)))
            {
                this.aumentarContador();
            }
            if ((this.getContador() == 1) && Tablero.getFichasTipos(fichitas, pos.x, pos.y) == null)
            {
                if (Tablero.placeFicha(fichitas, datos.getID(), pos, true, turnoJ1))
                {
                    datos.press(false);
                    this.deActivate();
                    return false;
                }
            }
        }
        if (tipo == 1)
        {
            return false;
        }
        System.out.println(pos);
        if (tipo == 2)
        {
            if (Tablero.tomarFicha(datos, Tablero.getFichasTipos(fichitas, pos.x, pos.y), turnoJ1))
            {
                this.deActivate();
                return false;
            }
        }
        if (tipo == 3)
        {
            for (FichasTipos fichaIteracion: fichitas)
            {
                if (fichaIteracion.getID() == idF)
                {
                    changeTypeFicha(fichaIteracion);
                    this.deActivate();
                    return false;
                }
            }
        }
        if (tipo == 4)
        {
            pos.x = (pos.x / 60) * 60 + 15;
            pos.y = (pos.y / 60) * 60 + 15;
            if(transformar(fichitas, pos, this.getDueno()))
            {
                this.deActivate();
                return false;
            }
        }
        return proceso;
    }
}
