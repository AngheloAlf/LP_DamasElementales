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
    Point pos;

    public Ficha(Point pos)
    {
        this.pos = pos;
    }

    public Point GetPos()
    {
        return this.pos;
    }

    public void SetPos(Point pos)
    {
        this.pos = pos;
    }




}
