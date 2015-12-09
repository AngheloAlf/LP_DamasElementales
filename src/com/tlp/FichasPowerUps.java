package com.tlp;

import lp.motor.Element;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anghelo on 01-Dec-15.
 */

public class FichasPowerUps extends Ficha {
    public FichasPowerUps(Point pos, int id) {
        super(pos, id);
    }

    public void changeTypeFicha(FichasTipos fichaIteracion)
    {
        int tipo = FichasTipos.getCorrespondingType(fichaIteracion.getType());
        tipo = -1;
        if (tipo < 0)
        {
            tipo = 2;
        }
        Element.Type[] c = Element.Type.values();
        fichaIteracion.setType(c[tipo]);
    }

    public static void transformar(ArrayList<FichasTipos> fichitas, int id)
    {
        FichasTipos aux = null;
        int ultimoID = -1;
        for (FichasTipos fichaIteracion: fichitas)
        {
            ultimoID = fichaIteracion.getID();
            if(ultimoID == id)
            {
                aux = fichaIteracion;
            }
        }
        if (aux != null)
        {
            if(id%2 == ultimoID%2)
            {
                aux.setID(ultimoID + 1);
            } else {
                aux.setID(ultimoID + 2);
            }
        }
    }
}
