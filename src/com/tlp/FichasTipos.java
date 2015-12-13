package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */
/**Clase que posee las caracteristicas de las damas, ademas de los 3 elementos*/
public class FichasTipos extends Ficha implements Element
{
    /**Almacena el tipo (Fire, Water o Leaf) asociada a la ficha*/
    private Element.Type tipo;
    /**Almacena si la dama fue presionada por el mouse*/
    private boolean presionada = false;
    /**Almacena si la dama fue comida por otra dama*/
    private boolean comida = false;
    /**Almacena si la dama se transformo a reina*/
    private boolean reina = false;
    /**Variable auxiliar para un PowerUp.
     * Esta almacena si esta dama fue seleccionada para ser obligada a ser movida*/
    private boolean obligada = false;

    /**Constructor del objeto
     * @param pos   Posicion de la dama
     * @param tipo  Tipo de la dama (Fire, Water o Leaf)
     * @param id    ID unica para cada dama
     * */
    public FichasTipos(Point pos, Element.Type tipo, int id)
    {
        super(pos, id);
        this.tipo = tipo;
    }

    /**Retorna el tipo almacenado en la dama
     * @return Retorna el tipo almacenado en la dama*/
    @Override
    public Type getType()
    {
        return this.tipo;
    }

    /**Retorna un numero asociado al tipo de elemento
     * @param tipo  Tipo: Fire, Water o Grass
     * @return      Retorna un numero: 0, 1 o 2 relacionado con el parametro
     * */
    protected static int getCorrespondingType(Element.Type tipo)
    {
        Element.Type[] c =  Element.Type.values();
        if (tipo == c[0])
        {
            return 0;
        }
        if (tipo == c[1])
        {
            return 1;
        }
        if (tipo == c[2])
        {
            return 2;
        }
        return -1;
    }

    /**
     * Revisa quien gana entre tu dama y la dama enemiga
     * @param element   Dama enemiga
     * @return          Retorna true si gana a la dama enemiga, o false si pierde
     */
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

    /** Cambia el tipo almacenado en la dama
     * @param tipo Es el tipo nuevo por el cual se cambiara el almacenado
     * */
    public void setType(Element.Type tipo)
    {
        this.tipo = tipo;
    }

    /**Revisa si la dama ha sido presionada por el mouse
     * @return True si esta presionada, false en caso contrario
     */
    public boolean isPressed()
    {
        return this.presionada;
    }

    /** Cambia el estado de la dama a apretado o desapretado
     * @param presionada True o false, en caso de apretar la dama con el mouse
     */
    public void press(boolean presionada)
    {
        this.presionada = presionada;
    }

    /**Revisa si la dama ya fue comida por otra
     * @return True si fue comida, false en caso contrario
     */
    public boolean isComida()
    {
        return this.comida;
    }

    /**Almacena que la dama fue comida
     */
    public void comer()
    {
        this.comida = true;
    }

    /**
     * Transforma a la dama en una reina
     */
    public void hacerReina()
    {
        this.reina = true;
    }

    /**Revisa si la dama es una reina
     * @return True si es una reina, false en caso contrario
     */
    public boolean isReina()
    {
        return this.reina;
    }

    /**Cambia el estado de la dama a obligado a moverse o no
     * @param ob True o false segun sea el caso
     */
    public void setObligada(boolean ob)
    {
        this.obligada = ob;
    }

    /**
     * Revisa si la dama esta obligada a moverse
     * @return True si esta obligada a moverse, o false en caso contrario
     */
    public boolean isObligada()
    {
        return this.obligada;
    }

    /**
     * Retorna un color relacionado al tipo de "this" dama
     * @return Fire-Rojo. Water-Azul. Leaf-Verde. Si por algun motivo el tipo almacenado no coincide con alguno de los anteriores, retorna amarillo
     */
    protected Color getColor()
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

    /**
     * Copia los datos que almacena la ficha fichaIteracion a "this"
     * @param fichaIteracion La ficha a ser copiada
     */
    protected void copyFicha(FichasTipos fichaIteracion)
    {
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
        this.setObligada(fichaIteracion.isObligada());
    }

    /**
     * Crea un arreglo de damas. La ID de las damas se van intercalando segun su dueño, es decir, las fichas pares son del jugador 1 y las impares del jugador 2. El elemento de cada dama se genera aleatoriamente
     * @return Retorna un arreglo que contiene los datos de las damas de ambos jugadores
     */
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
            if ((i + 1) % 5 == 0)
            {
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
