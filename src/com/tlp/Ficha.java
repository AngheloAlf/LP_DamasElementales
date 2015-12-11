package com.tlp;

import java.awt.*;

/**
 * Created by nontraxx on 27-11-15.
 */
public class Ficha
{
    protected Point pos;
    protected int id;

    public Ficha(Point pos, int id)
    {
        this.pos = pos;
        this.id = id;
    }

    public Point getPos()
    {
        return this.pos;
    }

    public void setPos(Point pos)
    {
        this.pos = pos;
    }

    public int getID()
    {
        return this.id;
    }

    public void setID(int id)
    {
        this.id = id;
    }
}
