package com.tlp;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anghelo on 08-Dec-15.
 */
public class Tablero {
    public static void dibujarTablero(Graphics p)
    {
        boolean black=false;
        int x,y;
        for (x=0;x<10;x++)
        {
            black=!black;
            for(y=0;y<10;y++)
            {
                if (black)
                {
                    p.setColor(Color.BLACK);
                } else {
                    p.setColor(Color.WHITE);
                }
                black=!black;
                p.fillRect(x*60,y*60,60,60);
            }
        }
    }

    public static void dibujarPuntuacion(Graphics p, boolean turnoJ1, ArrayList<FichasTipos> fichitas, int cantidadTurnos, FichasPowerUps powerUpUsado)
    {
        p.setColor(Color.GRAY);
        p.fillRect(600, 0, 200, 600);

        p.setColor(Color.BLACK);
        p.drawString("Jugador 1", 620, 25);
        p.drawString("Jugador 2", 620, 575);

        p.drawString("Turno de:", 620, 200);
        if (turnoJ1)
        {
            p.drawString("Jugador 1", 640, 220);
        } else {
            p.drawString("Jugador 2", 640, 220);
        }

        p.drawString("Turno    "+cantidadTurnos, 620, 390);

        int puntos1 = 0;
        int puntos2 = 0;
        for (FichasTipos fichaIteracion: fichitas)
        {
            if(fichaIteracion.isComida())
            {
                if(fichaIteracion.getID()%2 == 1)
                {
                    puntos1 += 1;
                } else {
                    puntos2 += 1;
                }
            }
        }

        p.drawString("Fichas comidas: ", 630, 50);
        p.drawString(String.valueOf(puntos1), 730, 50);
        p.drawString("Fichas comidas: ", 630, 550);
        p.drawString(String.valueOf(puntos2), 730, 550);

        if ((powerUpUsado != null) && (powerUpUsado.getType() == 4) && (powerUpUsado.isActiva()))
        {
            p.drawString("Jugador "+(powerUpUsado.getDueno()+1), 630, 280);
            p.drawString("Seleccione una ficha enemiga", 620, 300);
        }
    }

    public static void drawFicha(Graphics graphics, FichasTipos fichaIteracion, boolean j1, Point fichaPos)
    {
        if (fichaIteracion.isReina())
        {
            graphics.setColor(Color.BLACK);
            graphics.fillOval(fichaPos.x, fichaPos.y, 30, 30);
            graphics.setColor(fichaIteracion.getColor());
            graphics.fillOval(fichaPos.x+3, fichaPos.y+3, 24, 24);
        } else {
            graphics.setColor(fichaIteracion.getColor());
            graphics.fillOval(fichaPos.x, fichaPos.y, 30, 30);
        }
        if (j1)
        {
            graphics.setColor(Color.orange);
        } else {
            graphics.setColor(Color.LIGHT_GRAY);
        }
        graphics.fillOval(fichaPos.x+9, fichaPos.y+9, 12, 12);
    }

    public static void dibujarFichas(Graphics graphics, ArrayList<FichasTipos> fichitas)
    {
        for (FichasTipos fichaIteracion: fichitas)
        {
            if (!fichaIteracion.isPressed() && !fichaIteracion.isComida())
            {
                drawFicha(graphics, fichaIteracion, fichaIteracion.getID()%2 == 0, fichaIteracion.getPos());
            }
            if (fichaIteracion.isPressed())
            {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fillRect(fichaIteracion.getPos().x-3, fichaIteracion.getPos().y-3, 36, 36);
            }
        }
    }

    public static void dibujarFichaMouse(Graphics graphics, FichasTipos datos, Point pos)
    {
        if (datos.isPressed())
        {
            if (datos.getID()%2 == 1){
                drawFicha(graphics, datos, false, pos);
            } else {
                drawFicha(graphics, datos, true, pos);
            }
        }
    }

    public static void dibujarPowerUps(Graphics graphics, ArrayList<FichasPowerUps> fichitasUps)
    {
        for (FichasPowerUps fichaIteracion: fichitasUps)
        {
            if (fichaIteracion.isActiva())
            {
                graphics.setColor(fichaIteracion.getColor());
                graphics.fillOval(fichaIteracion.getPos().x, fichaIteracion.getPos().y, 30, 30);
            }
        }
    }

    public static void tomarFicha(FichasTipos datos, FichasTipos aux, boolean turnoJ1)
    {
        if (aux != null)
        {
            if ((turnoJ1) && (aux.getID()%2 == 0))
            {
                aux.press(true);
                datos.copyFicha(aux);
            }
            if (!(turnoJ1) && (aux.getID()%2 == 1))
            {
                aux.press(true);
                datos.copyFicha(aux);
            }
        }    }

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

    public static void verificarCreacionReina(FichasTipos fichaIteracion, Point newFichaPos, int id)
    {
        if ((id%2 == 0) && (newFichaPos.y == 555))
        {
            fichaIteracion.hacerReina();
        }
        if ((id%2 == 1) && (newFichaPos.y == 15))
        {
            fichaIteracion.hacerReina();
        }
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
        if (jugador == 1)
        {
            away = 120;
            otroJugador = 0;
        }

        if (newFichaPos.x + awayX == fichaIteracion.getPos().x)
        {
            for (FichasTipos fichaCiclo : fichitas)
            {
                if ((fichaCiclo.getPos().x + awayX / 2 == fichaIteracion.getPos().x) && ((fichaCiclo.getPos().y + (away / 2) == fichaIteracion.getPos().y) || (fichaIteracion.isReina() && (fichaCiclo.getPos().y - (away / 2) == fichaIteracion.getPos().y))))
                {
                    if (fichaCiclo.getID() % 2 == otroJugador)
                    {
                        if (((newFichaPos.y + away == fichaIteracion.getPos().y) && (id % 2 == jugador)) || ((fichaIteracion.isReina()) && (newFichaPos.y - away == fichaIteracion.getPos().y)))
                        {
                            Point nullPos = new Point(-1, -1);
                            if (fichaIteracion.testAgainst(fichaCiclo)) {
                                fichaCiclo.setPos(nullPos);
                                fichaCiclo.comer();
                                fichaIteracion.setPos(newFichaPos);
                                verificarCreacionReina(fichaIteracion, newFichaPos, id);
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
                        if ((newFichaPos.x + 60 == fichaIteracion.getPos().x) || (newFichaPos.x - 60 == fichaIteracion.getPos().x))
                        {
                            if (fichaIteracion.isReina())
                            {
                                if ((newFichaPos.y - 60 == fichaIteracion.getPos().y) || (newFichaPos.y + 60 == fichaIteracion.getPos().y))
                                {
                                    fichaIteracion.setPos(newFichaPos);
                                    fichaIteracion.press(false);
                                    verificarCreacionReina(fichaIteracion, newFichaPos, id);
                                    return true;
                                }
                            }
                            else{
                                if (((id%2 == 0) && (newFichaPos.y - 60 == fichaIteracion.getPos().y)) || ((id%2 == 1) && (newFichaPos.y + 60 == fichaIteracion.getPos().y)))
                                {
                                    fichaIteracion.setPos(newFichaPos);
                                    fichaIteracion.press(false);
                                    verificarCreacionReina(fichaIteracion, newFichaPos, id);
                                    return true;
                                }
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

    public static FichasPowerUps detectarColisionFichas(ArrayList<FichasTipos> fichitas, ArrayList<FichasPowerUps> fichitasUps)
    {
        for (FichasTipos fichaIteracion: fichitas)
        {
            for (FichasPowerUps fichaCicloUps: fichitasUps)
            {
                if ((fichaIteracion.getPos().x == fichaCicloUps.getPos().x) && (fichaIteracion.getPos().y == fichaCicloUps.getPos().y))
                {
                    if (fichaCicloUps.isActiva())
                    {
                        return fichaCicloUps;
                    }
                }
            }
        }
        return null;
    }
}
