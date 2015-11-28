package com.tlp;

import lp.motor.Element;

import java.awt.*;

/**
 * Created by nontraxx on 27-11-15.
 */
public class Ficha {
    int posx,posy;
    Element.Type tipo;
    Graphics pant;
    public void ficha(Graphics g,int x, int y){
        this.pant=g;
        this.posx=x;
        this.posy=y;
    }
}
