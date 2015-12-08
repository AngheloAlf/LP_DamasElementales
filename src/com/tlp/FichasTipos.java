package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */
public class FichasTipos extends Ficha implements Element {
    Element.Type tipo;
    boolean presionada = false;
    boolean comida = false;

    @Override
    public Type getType()
    {
        return this.tipo;
    }

    public int getCorrespondingType(Element.Type tipo)
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

    public FichasTipos(Point pos, Element.Type tipo, int id) {
        super(pos, id);
        this.tipo = tipo;
    }

    public void setType(Element.Type tipo)
    {
        this.tipo = tipo;
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

    public boolean isPressed()
    {
        return this.presionada;
    }

    public void press(boolean presionada)
    {
        this.presionada = presionada;
    }

    public static FichasTipos getFichasTipos(ArrayList<FichasTipos> fichitas, int posx, int posy)
    {
        Point fichaPos;

            for (FichasTipos fichaIteracion : fichitas)
            {
                fichaPos = fichaIteracion.getPos();
                if (((fichaPos.x - 15) < posx) && (posx < (fichaPos.x + 45) ))
                {
                    if (((fichaPos.y - 15) < posy) && (posy < (fichaPos.y + 45) ))
                    {
                        return fichaIteracion;
                    }
                }
            }
        return null;
    }

    public static Point arreglarPos(Point newFichaPos)
    {
        newFichaPos.x = (newFichaPos.x/60)*60 + 15;
        newFichaPos.y = (newFichaPos.y/60)*60 + 15;
        return newFichaPos;
    }

    public static boolean verificarComimiento(ArrayList<FichasTipos> fichitas, FichasTipos fichaIteracion, Point newFichaPos, int id, int jugador, int awayX)
    {
        int away = 120;
        int otroJugador = 0;
        if (jugador == 0)
        {
            away = -120;
            otroJugador = 1;
        }
        if ((id%2 == jugador) && (newFichaPos.y + away == fichaIteracion.getPos().y))
        {
            if (newFichaPos.x + awayX == fichaIteracion.getPos().x)
            {
                for (FichasTipos fichaCiclo: fichitas)
                {
                    if ((fichaCiclo.getPos().x + awayX/2 == fichaIteracion.getPos().x) && (fichaCiclo.getPos().y + away/2 == fichaIteracion.getPos().y))
                    {
                        if (fichaCiclo.getID()%2 == otroJugador)
                        {
                            Point nullPos = new Point(-1, -1);
                            if (fichaIteracion.testAgainst(fichaCiclo)) {
                                fichaCiclo.setPos(nullPos);
                                fichaCiclo.comer();
                                fichaIteracion.setPos(newFichaPos);
                            } else {
                                fichaIteracion.setPos(nullPos);
                                fichaIteracion.comer();
                            }
                            fichaIteracion.press(false);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean placeFicha(ArrayList<FichasTipos> fichitas, int id, Point newFichaPos, boolean valido)
    {
        if ((newFichaPos.x > 0) && (newFichaPos.x < 600) && ((newFichaPos.y > 0) && (newFichaPos.y < 600)))
        {
            for (FichasTipos fichaIteracion : fichitas)
            {
                if (fichaIteracion.getID() == id)
                {
                    newFichaPos = arreglarPos(newFichaPos);
                    if (valido)
                    {
                        if (((id%2 == 0) && (newFichaPos.y - 60 == fichaIteracion.getPos().y)) || ((id%2 == 1) && (newFichaPos.y + 60 == fichaIteracion.getPos().y)))
                        {
                            if ((newFichaPos.x + 60 == fichaIteracion.getPos().x) || (newFichaPos.x - 60 == fichaIteracion.getPos().x))
                            {
                                fichaIteracion.setPos(newFichaPos);
                                fichaIteracion.press(false);
                                return true;
                            }
                        }

                        if (verificarComimiento(fichitas, fichaIteracion, newFichaPos, id, 0, 120))
                        {
                            return true;
                        }
                        if (verificarComimiento(fichitas, fichaIteracion, newFichaPos, id, 0, -120))
                        {
                            return true;
                        }
                        if (verificarComimiento(fichitas, fichaIteracion, newFichaPos, id, 1, 120))
                        {
                            return true;
                        }
                        if (verificarComimiento(fichitas, fichaIteracion, newFichaPos, id, 1, -120))
                        {
                            return true;
                        }

                    } else {
                        fichaIteracion.setPos(newFichaPos);
                        fichaIteracion.press(false);
                        return true;
                    }
                }
            }
        }
        return false;
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
    }

    public boolean isComida()
    {
        return this.comida;
    }

    public void comer()
    {
        this.comida = true;
    }
}
