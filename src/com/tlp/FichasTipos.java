package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */
public class FichasTipos extends Ficha implements Element {
    protected Element.Type tipo;
    protected boolean presionada = false;
    protected boolean comida = false;
    protected boolean reina = false;
    protected boolean obligada = false;

    public FichasTipos(Point pos, Element.Type tipo, int id) {
        super(pos, id);
        this.tipo = tipo;
    }

    @Override
    public Type getType()
    {
        return this.tipo;
    }

    public static int getCorrespondingType(Element.Type tipo)
    {
        Element.Type[] c =  Element.Type.values();
        if (tipo == c[0]){
            return 0;
        }
        if (tipo == c[1]){
            return 1;
        }
        if (tipo == c[2]){
            return 2;
        }
        return -1;
    }

    @Override
    public boolean testAgainst(Element element)
    {
        if (element != null)
        {
            if (this.getType() == (element.getType()))
            {
                return true;
            }

            if (getCorrespondingType(this.getType())-1 == getCorrespondingType((element.getType())))
            {
                return true;
            }

            if ((getCorrespondingType(this.getType()) == 0) && (getCorrespondingType(element.getType()) == 2))
            {
                return true;
            }
        }
        return false;
    }

    public void setType(Element.Type tipo)
    {
        this.tipo = tipo;
    }

    public boolean isPressed()
    {
        return this.presionada;
    }

    public void press(boolean presionada)
    {
        this.presionada = presionada;
    }

    public boolean isComida()
    {
        return this.comida;
    }

    public void comer()
    {
        this.comida = true;
    }

    public void hacerReina()
    {
        this.reina = true;
    }

    public boolean isReina()
    {
        return this.reina;
    }

    public void setObligada(boolean ob)
    {
        this.obligada = ob;
    }

    public boolean isObligada()
    {
        return this.obligada;
    }

    public Color getColor()
    {
        Element.Type[] c =  Element.Type.values();
        if (this.getType() == c[0])
        {
            return Color.RED;
        }
        if (this.getType() == c[1])
        {
            return Color.BLUE;
        }
        if (this.getType() == c[2])
        {
            return Color.GREEN;
        }
        return Color.YELLOW;
    }

    public void copyFicha(FichasTipos fichaIteracion){
        this.setType(fichaIteracion.getType());
        this.press(fichaIteracion.isPressed());
        this.setPos(fichaIteracion.getPos());
        this.setID(fichaIteracion.getID());
        if (fichaIteracion.isReina())
        {
            this.hacerReina();
        } else {
            this.reina = false;
        }
    }

    public static ArrayList<FichasTipos> hacerListaFichas()
    {
        ArrayList<FichasTipos> fichitas = new ArrayList<FichasTipos>();
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
            fichitas.add(wea);
            contadorID += 1;

            randomNumber = rand.nextInt(3);
            wea = new FichasTipos(contadorPos2, c[randomNumber], contadorID);
            fichitas.add(wea);
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
        return fichitas;
    }
}
