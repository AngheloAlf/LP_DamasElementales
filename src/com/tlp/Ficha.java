package com.tlp;

import java.awt.*;

/**
 * Created by nontraxx on 27-11-15.
 */

/**Posee las caracteristicas basicas de las fichas */
public class Ficha
{
    /**Almacena la posicion de las fichas*/
    private Point pos;
    /**Se registra una ID unica para cada ficha*/
    private int id;

    /**Constructor del objeto
     * @param pos   Almacena la posicion de la ficha
     * @param id    Almacena una ID unica para cada ficha
     * */
    protected Ficha(Point pos, int id)
    {
        this.pos = pos;
        this.id = id;
    }

    /**
     * @return      Entrega la posicion en pixeles de la ficha
     * */
    public Point getPos()
    {
        return this.pos;
    }

    /**Cambia la posicion de la ficha
     * @param pos   La posicion que reemplazara a la posicion almacenada en la variable
     * */
    public void setPos(Point pos)
    {
        this.pos = pos;
    }

    /**
     * @return      Retorna la ID almacenada en la ficha
     * */
    public int getID()
    {
        return this.id;
    }

    /**Cambia la id de la ficha
     * @param id    La ID que reemplazara a la ID almacenada en la variable
     * */
    public void setID(int id)
    {
        this.id = id;
    }
}
