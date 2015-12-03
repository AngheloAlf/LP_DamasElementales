package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nontraxx on 27-11-15.
 */
public class Ficha implements Element{
    int posx,posy,jug;
    boolean dispo=true;
    Element.Type tip;
    public Element.Type getType()
    {
        return tip;
    }
    public Ficha(int x, int y,Element.Type tipo,int j){
        this.posx=x;
        this.posy=y;
        this.tip=tipo;
        this.jug=j;
    }
    public boolean testAgainst(Element el)
    {
        return true;
    }
    public void editDistpo(int t, ArrayList<Ficha> fij1,ArrayList<Ficha> fij2){
        boolean con1=false,cond2=false;
        //((ff.posx==posx+60 && ff.posy==posy+60) || (ff.posx==posx-60 && ff.posy==posy+60)
    }

    public void colocar(Graphics g)
    {
        if (dispo){
            g.setColor(Color.YELLOW);
            g.fillOval(posx,posy,60,60);
        }
        if (tip== Element.Type.LEAF){
            g.setColor(Color.GREEN);
            g.fillOval(posx+2,posy+2,56,56);
        }
        if (tip== Element.Type.WATER){
            g.setColor(Color.BLUE);
            g.fillOval(posx+2,posy+2,56,56);
        }
        if (tip== Element.Type.FIRE){
            g.setColor(Color.RED);
            g.fillOval(posx+2,posy+2,56,56);
        }
        g.setColor(Color.BLACK);
        g.drawString(""+jug+"",posx+30,posy+30);
        g.drawString("("+posx+","+posy+")",posx+10,posy+50);

    }
}
