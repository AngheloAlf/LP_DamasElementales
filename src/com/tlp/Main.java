import com.tlp.Ficha;
import lp.motor.Application;
import lp.motor.Context;
import lp.motor.Element;
import lp.motor.MouseHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main implements Context
{
    private Point pos;
    private int pnt1=0,pnt2=0,turn=1;
    private Random rand= new Random();
    ArrayList<Ficha> fij1=new ArrayList<Ficha>();
    ArrayList<Ficha> fij2=new ArrayList<Ficha>();

    public Main()
    {
        int i,k;

        ArrayList<Element.Type> elemselect = new ArrayList<Element.Type>();
        elemselect.add(Element.Type.FIRE);
        elemselect.add(Element.Type.WATER);
        elemselect.add(Element.Type.LEAF);

        for (i=0;i<10;i++)
        {

            k = (i * 120) % 600;
            if (i<5)
            {
                fij1.add(new Ficha(k, 0, elemselect.get(rand.nextInt(3)),1));
                fij2.add(new Ficha(k, 600-120, elemselect.get(rand.nextInt(3)),2));
            }
            else {
                fij1.add(new Ficha(k+60, 60, elemselect.get(rand.nextInt(3)),1));
                fij2.add(new Ficha(k+60, 600-60, elemselect.get(rand.nextInt(3)),2));
            }

        }

        //elemselect.get(rand.nextInt(2));
        // aquí puede inicializar valores y crear los objetos de juego.
    }

    @Override
    public void update(MouseHandler mouseHandler)
    {
        // aquí actualice sus objetos para que puedan interactuar con input de usuario o entre los mismos
        // objetos.


        // por ejemplo imprimir algo si el mouse está cerca de la esquina superior izquierda:
        pos = mouseHandler.getMousePosition();
        //if (pos.x < 200 && pos.y < 200)
        //System.out.println(pos);
    }
    public void tablero(Graphics p)
    {
        boolean black=false;
        int x,y;
        for (x=0;x<10;x++)
        {
            black=!black;
            for(y=0;y<10;y++)
            {
                if (black){p.setColor(Color.BLACK);}
                else {p.setColor(Color.WHITE);}
                black=!black;
                p.fillRect(x*60,y*60,60,60);

            }
            for(Ficha f: fij1){f.colocar(p);}
            for(Ficha f: fij2){f.colocar(p);}
        }
    }
    public void texto(int t,int punt1, int punt2,Graphics p)
    {
        p.drawString("                    Turno del Jugador "+t+"          Puntaje Player 1 = "+punt1+"          Puntaje Player 2 = "+punt2,0,615);
    }
    @Override
    public void render(Graphics graphics)
    {
        // aquí, y solo aquí, puede dibujar cosas en la pantalla.
        tablero(graphics);
        graphics.setColor(Color.WHITE);
        texto(turn,pnt1,pnt2,graphics);
        //graphics.setColor(Color.CYAN);
        //graphics.fillOval(pos.x-6, pos.y-6, 12, 12);


    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(600, 620, "Damas_Elementales_Elepé", 60, new Main());
    }
}