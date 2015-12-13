package com.tlp;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Anghelo on 08-Dec-15.
 */
/**Clase que contiene todos los metodos relacionados al tablero*/
public class Tablero
{
    /**
     * Se encarga de dibujar el tablero (entiendase las casillas negras y blancas)
     * @param p Graphics para dibujar por pantalla
     */
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

    /**
     * Metodo que se encarga de dibujar el tablero lateral
     * @param p                 Graphics para dibujar por pantalla
     * @param turnoJ1           True si es el turno del jugador 1, false en caso contrario
     * @param fichitas          Lista que contiene todas las damas de ambos jugadores
     * @param cantidadTurnos    Contador que tiene la cantidad de turnos de juego
     * @param powerUpUsado      Recibe un PowerUp si fue tomado, o null si no se ha tomado alguno
     */
    public static void dibujarPuntuacion(Graphics p, boolean turnoJ1, ArrayList<FichasTipos> fichitas, int cantidadTurnos, FichasPowerUps powerUpUsado)
    {
        p.setColor(Color.GRAY);
        p.fillRect(600, 0, 200, 600);

        p.setColor(Color.BLACK);
        p.drawString("Jugador 1", 620, 25);
        p.drawString("Jugador 2", 620, 575);

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

        if ((powerUpUsado != null) && (powerUpUsado.isActiva()))
        {
            p.drawString("Jugador "+(powerUpUsado.getDueno()+1), 630, 280);
            if (powerUpUsado.getType() == 3)
            {
                p.drawString("Haga click en la pantalla", 620, 300);
                p.drawString("para transformar su ficha", 620, 320);
            }
            if (powerUpUsado.getType() == 4)
            {
                p.drawString("Seleccione una ficha enemiga", 620, 300);
                p.drawString("para transformala en tuya", 620, 320);
            }
            if (powerUpUsado.getType() == 2)
            {
                p.drawString("Seleccione una ficha enemiga", 620, 300);
                p.drawString("para que el enemigo la mueva", 620, 320);
            }
            if (powerUpUsado.getType() == 0)
            {
                p.drawString("Retroceda una ficha", 620, 300);
            }
            if (powerUpUsado.getType() == 1)
            {
                p.drawString("Avance "+(3-(powerUpUsado.getContador()/2))+" espacio(s)", 620, 300);
            }
        } else {
            p.drawString("Turno de:", 620, 200);
            if (turnoJ1)
            {
                p.drawString("Jugador 1", 640, 220);
            } else {
                p.drawString("Jugador 2", 640, 220);
            }
        }
    }

    /**
     * Metodo que se encarga de dibujar una Dama por pantala
     * @param graphics          Graphics para dibujar por pantalla
     * @param fichaIteracion    La Dama que se dibujara
     * @param fichaPos          La posicion en la cual se dibujara la ficha
     */
    private static void drawFicha(Graphics graphics, FichasTipos fichaIteracion, Point fichaPos)
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
        if (fichaIteracion.getID()%2 == 0)
        {
            graphics.setColor(Color.orange);
        } else {
            graphics.setColor(Color.LIGHT_GRAY);
        }
        graphics.fillOval(fichaPos.x+9, fichaPos.y+9, 12, 12);
    }

    /**
     * Metodo que se encarga de dibujar las damas de ambos jugadores
     * @param graphics Graphics para dibujar por pantalla
     * @param fichitas Lista que contiene todas las damas de ambos jugadores
     */
    public static void dibujarFichas(Graphics graphics, ArrayList<FichasTipos> fichitas)
    {
        for (FichasTipos fichaIteracion: fichitas)
        {
            if (!fichaIteracion.isPressed() && !fichaIteracion.isComida())
            {
                drawFicha(graphics, fichaIteracion, fichaIteracion.getPos());
            }
            if (fichaIteracion.isPressed())
            {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.fillRect(fichaIteracion.getPos().x-3, fichaIteracion.getPos().y-3, 36, 36);
            }
        }
    }

    /**
     * Metodo que se encarga de dibujar una ficha en la misma posicion que el mouse
     * @param graphics  Graphics para dibujar por pantalla
     * @param datos     Variable que contiene los datos de la dama
     * @param pos       Posicion del mouse
     */
    public static void dibujarFichaMouse(Graphics graphics, FichasTipos datos, Point pos)
    {
        if (datos.isPressed())
        {
            drawFicha(graphics, datos, pos);
        }
    }

    /**
     * Metodo que se encarga de dibujar los PowerUps
     * @param graphics      Graphics para dibujar por pantalla
     * @param fichitasUps   Lista que contiene todos los PowerUps
     */
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

    /**
     * Metodo que se encarga de tomar una ficha del tablero y asignarla al mouse, siempre y cuando sea una ficha del mismo jugador
     * @param datos     Donde se almacenan los datos de la ficha del mouse. NO puede ser null
     * @param aux       La ficha que se desea tomar
     * @param turnoJ1   True si es el turno del jugador 1, false en caso contrario
     * @return          True si la ficha pudo ser tomada con exito, false en caso contrario
     */
    public static boolean tomarFicha(FichasTipos datos, FichasTipos aux, boolean turnoJ1)
    {
        if (aux != null)
        {
            if ((turnoJ1) && (aux.getID()%2 == 0))
            {
                aux.press(true);
                datos.copyFicha(aux);
                return true;
            }
            if (!(turnoJ1) && (aux.getID()%2 == 1))
            {
                aux.press(true);
                datos.copyFicha(aux);
                return true;
            }
        }
        return false;
    }

    /**
     * Busca una dama en funcion de la posicion entregada
     * @param fichitas  La lista de damas de ambos jugadores
     * @param pos       La posicion del mouse
     * @return          Si es que existe una dama en la posicion entregada, se retorna la dama, en caso contrario retorna null
     */
    public static FichasTipos getFichasTipos(ArrayList<FichasTipos> fichitas, Point pos)
    {
        Point fichaPos;

        for (FichasTipos fichaIteracion : fichitas)
        {
            fichaPos = fichaIteracion.getPos();
            if (((fichaPos.x - 15) < pos.x) && (pos.x < (fichaPos.x + 45) ))
            {
                if (((fichaPos.y - 15) < pos.y) && (pos.y < (fichaPos.y + 45) ))
                {
                    return fichaIteracion;
                }
            }
        }
        return null;
    }

    /**
     * Convierte la posicion en pixeles a una posicion en pixeles en la que podria estar una dama
     * @param newFichaPos   La posicion del mouse
     * @return              La posicion arreglada y acercada a donde podria estar una ficha
     */
    private static Point arreglarPos(Point newFichaPos)
    {
        Point posFix = new Point();
        posFix.x = (newFichaPos.x/60)*60 + 15;
        posFix.y = (newFichaPos.y/60)*60 + 15;
        return posFix;
    }

    /**
     * Verifica si la dama se encuentra en una posicion apta como para transformse en reina, si es asi, se transforma en reina
     * @param fichaIteracion    La dama la cual se verificara.
     */
    private static void verificarCreacionReina(FichasTipos fichaIteracion)
    {
        if ((fichaIteracion.getID()%2 == 0) && (fichaIteracion.getPos().y == 555))
        {
            fichaIteracion.hacerReina();
        }
        if ((fichaIteracion.getID()%2 == 1) && (fichaIteracion.getPos().y == 15))
        {
            fichaIteracion.hacerReina();
        }
    }

    /**
     * Revisa si una dama puede comerse a otra dama
     * @param fichitas          La lista de damas de ambos jugadores
     * @param fichaIteracion    La dama que comera a otra dama
     * @param newFichaPos       La posicion del mouse fixeada (ver arreglarPos)
     * @param jugador           0 (cero) si es jugador1 o 1 (uno) si es jugador2
     * @param awayX             Distancia entre la dama y donde se colocara (120 o -120)
     * @return                  La dama que sera comida, o null si no encontro ninguna
     */
    private static FichasTipos verificarComimiento(ArrayList<FichasTipos> fichitas, FichasTipos fichaIteracion, Point newFichaPos, int jugador, int awayX)
    {
        int away = 120;
        int otroJugador = 0;
        if (jugador == 0)
        {
            away = -120;
            otroJugador = 1;
        }

        if (newFichaPos.x + awayX == fichaIteracion.getPos().x)
        {
            for (FichasTipos fichaCiclo : fichitas)
            {
                if ((fichaCiclo.getPos().x + awayX / 2 == fichaIteracion.getPos().x) && ((fichaCiclo.getPos().y + (away / 2) == fichaIteracion.getPos().y) || (fichaIteracion.isReina() && (fichaCiclo.getPos().y - (away / 2) == fichaIteracion.getPos().y))))
                {
                    if (fichaCiclo.getID() % 2 == otroJugador)
                    {
                        if (((newFichaPos.y + away == fichaIteracion.getPos().y) && (fichaIteracion.getID() % 2 == jugador)) || ((fichaIteracion.isReina()) && (newFichaPos.y - away == fichaIteracion.getPos().y)))
                        {
                            return fichaCiclo;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Hace que la dama fichaIteracion se coma a la dama fichaCiclo (siempre y cuando le gane segun el tipo)
     * @param fichaIteracion    La dama que va a comer
     * @param fichaCiclo        La ficha que va a ser comida
     * @param newFichaPos       La nueva posicionde la ficha
     */
    private static void comerALaOtraDama(FichasTipos fichaIteracion, FichasTipos fichaCiclo, Point newFichaPos)
    {
        Point nullPos = new Point(-1, -1);
        if (fichaIteracion.testAgainst(fichaCiclo))
        {
            fichaCiclo.setPos(nullPos);
            fichaCiclo.comer();
            fichaIteracion.setPos(newFichaPos);
            verificarCreacionReina(fichaIteracion);
        } else {
            fichaIteracion.setPos(nullPos);
            fichaIteracion.comer();
        }
        fichaIteracion.press(false);
    }

    /**
     * Pone una ficha en la posicion indicada, siempre y cuando sea valido el lugar en el cual se quiere dejar
     * @param fichitas      Lista de todas las damas de ambos jugadores
     * @param id            ID de la dama en cuestion
     * @param newFichaPos   La nueva posicion donde se dejara la dama (posicion del mouse)
     * @param valido        True para revisar que las jugadas sean validas, false en caso contrario
     * @param jugador       True si es jugador1, false en caso contrario
     * @return              True si se pudo mover la dama, false en caso contrario
     */
    public static boolean placeFicha(ArrayList<FichasTipos> fichitas, int id, Point newFichaPos, boolean valido, boolean jugador)
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
                                    verificarCreacionReina(fichaIteracion);
                                    return true;
                                }
                            }
                            else{
                                if (((jugador) && (newFichaPos.y - 60 == fichaIteracion.getPos().y)) || ((!jugador) && (newFichaPos.y + 60 == fichaIteracion.getPos().y)))
                                {
                                    fichaIteracion.setPos(newFichaPos);
                                    fichaIteracion.press(false);
                                    verificarCreacionReina(fichaIteracion);
                                    return true;
                                }
                            }
                        }

                        FichasTipos fichaCiclo;
                        if ((fichaCiclo = verificarComimiento(fichitas, fichaIteracion, newFichaPos, 0, 120)) != null)
                        {
                            comerALaOtraDama(fichaIteracion, fichaCiclo, newFichaPos);
                            return true;
                        }
                        if ((fichaCiclo = verificarComimiento(fichitas, fichaIteracion, newFichaPos, 0, -120)) != null)
                        {
                            comerALaOtraDama(fichaIteracion, fichaCiclo, newFichaPos);
                            return true;
                        }
                        if ((fichaCiclo = verificarComimiento(fichitas, fichaIteracion, newFichaPos, 1, 120)) != null)
                        {
                            comerALaOtraDama(fichaIteracion, fichaCiclo, newFichaPos);
                            return true;
                        }
                        if ((fichaCiclo = verificarComimiento(fichitas, fichaIteracion, newFichaPos, 1, -120)) != null)
                        {
                            comerALaOtraDama(fichaIteracion, fichaCiclo, newFichaPos);
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

    /**
     * Detecta la colision entre una dama y algun PowerUp
     * @param fichitas      Lista que contiene las damas de ambos jugadores
     * @param fichitasUps   Lista que contiene los PowerUps
     * @return              Retorna el PowerUp que colisiono con la dama, o null si ninguno colisiono
     */
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

    /**
     * Revisa si la dama puede moverse (no incluye la posibilidad de comer)
     * @param fichitas  Lista de todas las damas de ambos jugadores
     * @param pos       Posicion de la dama
     * @param turnoJ1   True si es jugador 1, false en caso contrario
     * @return          True si la dama seleccionada se podria mover de forma normal, false en caso contrario
     */
    protected static boolean detectarPosible(ArrayList<FichasTipos> fichitas, Point pos, boolean turnoJ1)
    {
        int contador = 0;
        for(FichasTipos fichaIteracion: fichitas)
        {
            if ((fichaIteracion.getPos().x+60 == pos.x) || (fichaIteracion.getPos().x-60 == pos.x))
            {
                if ((turnoJ1) && (fichaIteracion.getPos().y-60 == pos.y))
                {
                    contador += 1;
                }
                if ((!turnoJ1) && (fichaIteracion.getPos().y+60 == pos.y))
                {
                    contador += 1;
                }
                if (contador == 2)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
