package com.tlp;

import lp.motor.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anghelo on 01-Dec-15.
 */
/**Clase que posee las caracteristicas de los PowerUps*/
public class FichasPowerUps extends Ficha
{
    /**Almacena si el PowerUp se encuentra en el tablero*/
    private boolean activa = true;
    /**Almacena el tipo de PowerUps que sera la ficha. Son numeros del 0 al 4*/
    private int tipoUp;
    /**Se le asigna un dueño al PowerUp una vez que ya fue tomado. 0 (cero) para Jugador1 y 1 (uno) paraa jugador 2. Se inicializa en 0 (cero)*/
    private int dueno = -1;
    /**Un contador especial para algunos PowerUps. Se inicializa en 0 (cero)*/
    private int contador = 0;

    /**
     * Constructor del objeto
     * @param pos    Posicion del PowerUps
     * @param id     ID unica de este PowerUp
     * @param tipoUp Tipo asignado a este PowerUP
     */
    public FichasPowerUps(Point pos, int id, int tipoUp)
    {
        super(pos, id);
        this.tipoUp = tipoUp;
    }

    /**
     * Verifica si la ficha esta en el tablero o no
     * @return True si esta todavia en el tablero, false en caso contrario
     */
    public boolean isActiva()
    {
        return this.activa;
    }

    /**
     * Desactiva el PowerUp
     */
    public void deActivate()
    {
        this.activa = false;
    }

    /**
     * Entrega el tipo del PowerUp
     * @return Un numero del 0 al 4, segun el orden del enunciado
     */
    public int getType()
    {
        return this.tipoUp;
    }

    /**
     * Cambia el tipo del PowerUp
     * @param tipo Numero del 0 al 4 que corresponde al tipo al que se desea cambiar
     */
    public void setType(int tipo)
    {
        this.tipoUp = tipo;
    }

    /**
     * Cuando un PowerUp es tomado, se le debe asignar un dueno usando este metodo
     * @param dueno 0 (cero) para primer jugador y 1 (uno) para segundo jugador
     */
    public void setDueno(int dueno)
    {
        this.dueno = dueno;
    }

    /**
     * Entrega el dueno de la dama
     * @return 0 (cero) para primer jugador y 1 (uno) para segundo jugador
     */
    public int getDueno()
    {
        return this.dueno;
    }

    /**
     * Cambia el numero que tiene almacenado el contador
     * @param numero El numero que guaradara
     */
    public void setContador(int numero)
    {
        this.contador = numero;
    }

    /**
     * Entrega el contador que tenga almacenado
     * @return Numero que almacena
     */
    public int getContador()
    {
        return this.contador;
    }

    /**
     * Entrega el color asociado al tipo del PowerUp
     * @return 0: Negro. 1: Amarillo. 2: Rosa. 3: Multicolor (Rojo, Azul, o Verde). 4: Cian.
     */
    protected Color getColor()
    {
        if (this.tipoUp == 0)
        {
            return Color.BLACK;
        }
        if (this.tipoUp == 1)
        {
            return Color.YELLOW;
        }
        if (this.tipoUp == 2)
        {
            return Color.PINK;
        }
        if (this.tipoUp == 3)
        {
            Random rand = new Random();
            int numeroRandom = rand.nextInt(3);
            if (numeroRandom == 0)
            {
                return Color.RED;
            }
            if (numeroRandom == 1)
            {
                return Color.BLUE;
            }
            if (numeroRandom == 2)
            {
                return Color.GREEN;
            }
            return Color.lightGray;
        }
        if (this.tipoUp == 4)
        {
            return Color.CYAN;
        }
        return Color.WHITE;
    }

    /**
     * Cambia el tipo de la dama en orden Agua-Fuego-Hierba-Agua...
     * @param fichaIteracion La dama a la cual se le cambiara el tipo
     */
    protected void changeTypeFicha(FichasTipos fichaIteracion)
    {
        int tipo = FichasTipos.getCorrespondingType(fichaIteracion.getType());
        tipo -= 1;
        if (tipo < 0)
        {
            tipo = 2;
        }
        Element.Type[] c = Element.Type.values();
        fichaIteracion.setType(c[tipo]);
    }

    /**
     * Transforma a la ficha enemiga en ficha propia
     * @param fichitas Lista que contiene todas las fichas de ambos jugadores
     * @param pos   Posicion del mouse
     * @param id    Se usa para identificar al dueno de la dama
     * @return      True si se pudo transformar de forma exitosa, o false en caso contrario
     */
    protected static boolean transformar(ArrayList<FichasTipos> fichitas, Point pos, int id)
    {
        FichasTipos aux = null;
        int ultimoID = -1;
        for (FichasTipos fichaIteracion: fichitas)
        {
            ultimoID = fichaIteracion.getID();
            if ((fichaIteracion.getPos().x == pos.x) && (fichaIteracion.getPos().y == pos.y))
            {
                aux = fichaIteracion;
            }
        }
        if ((aux != null) && (aux.getID()%2 != id%2))
        {
            if (aux.getID()%2 == ultimoID%2)
            {
                aux.setID(ultimoID + 1);
            } else {
                aux.setID(ultimoID + 2);
            }
            if (aux.getID()%2 == 1)
            {
                if (pos.y == 15)
                {
                    aux.hacerReina();
                }
            } else {
                if (pos.y == 555)
                {
                    aux.hacerReina();
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Aleatoriamente agrega un PowerUp aleatorio en una posicion aleatoria. Mientras mas damas y PowerUps haya en pantalla, menos probable es que se ponga un PowerUp, y mientras menos damas y PowerUps hayan, mas probable es que se cree uno
     * @param fichitas Lista que contiene todas las damas de ambos jugadores
     * @param fichitasUps Lista que contiene todos los PowerUps.
     */
    public static void agregarFichaRandom(ArrayList<FichasTipos> fichitas, ArrayList<FichasPowerUps> fichitasUps)
    {
        Random rand = new Random();
        int randomX = rand.nextInt(5)*2;
        int randomY = rand.nextInt(10);

        if (randomY % 2 == 0)
        {
            randomX = randomX + 1;
        }

        randomX = randomX * 60 + 15;
        randomY = randomY * 60 + 15;

        for (FichasTipos fichaIteracion: fichitas)
        {
            if ((fichaIteracion.getPos().x == randomX) && (fichaIteracion.getPos().y == randomY) && !fichaIteracion.isComida())
            {
                return;
            }
        }

        for (FichasPowerUps fichaIteracionUps: fichitasUps)
        {
            if ((fichaIteracionUps.getPos().x == randomX) && (fichaIteracionUps.getPos().y == randomY))
            {
                return;
            }
        }
        int ultimoid = 0;
        for (FichasPowerUps fichitasIteracionUps: fichitasUps)
        {
            if (fichitasIteracionUps.getID() > ultimoid)
            {
                ultimoid = fichitasIteracionUps.getID();
            }
        }
        Point posUps = new Point(randomX, randomY);
        FichasPowerUps nuevaFicha = new FichasPowerUps(posUps, ultimoid+1, rand.nextInt(5));
        fichitasUps.add(nuevaFicha);
    }

    /**
     * Permite retroceder alguna de las damas del jugador
     * @param fichitas  Lista de damas de ambos jugadores.
     * @param datos     Variable auxiliar que contiene los datos de la dama que mueve el mouse
     * @param pos       Posicion del mouse
     * @param turnoJ1   True si es el turno del jugador 1, false en caso contrario
     * @param turnoJ2   True si es el turno del jugador 2, false en caso contrario
     * @return          False si termino la ejecucion del PowerUp, True en caso contrario
     */
    protected boolean usarCero(ArrayList<FichasTipos> fichitas, FichasTipos datos, Point pos, boolean turnoJ1, boolean turnoJ2)
    {
        FichasTipos aux = Tablero.getFichasTipos(fichitas, pos);
        if ((this.getContador()%2 == 0) && (Tablero.tomarFicha(datos, aux, turnoJ2)))
        {
            this.setContador(this.getContador()+1);
        } else {
            if (this.getContador() % 2 == 1) {
                if (aux == null) {
                    if (Tablero.placeFicha(fichitas, datos.getID(), pos, true, turnoJ1)) {
                        this.setContador(this.getContador() + 1);
                        datos.press(false);
                        return false;
                    }
                } else {
                    if (aux.isPressed()) {
                        if (Tablero.placeFicha(fichitas, datos.getID(), pos, false, turnoJ1)) {
                            datos.press(false);
                            this.setContador(this.getContador() - 1);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Metodo que se encarga de ejecutar los PowerUps
     * @param proceso   True si el PowerUp se sigue ejecutando, False si dejo de ejecutarse
     * @param fichitas  La lista de damas de ambos jugadores
     * @param datos     Variable auxiliar que contiene los datos de la dama que mueve el mouse
     * @param pos       La posicion del mouse
     * @param turnoJ1   True si es el turno del jugador 1, false en caso contrario
     * @return          True si continua la ejecucion del PowerUp, false en caso contrario
     */
    public boolean usarPowerUps(boolean proceso, ArrayList<FichasTipos> fichitas, FichasTipos datos, Point pos, boolean turnoJ1)
    {
        if (proceso)
        {
            int tipo = this.getType();
            if (tipo == 0) {
                if (!this.usarCero(fichitas, datos, pos, turnoJ1, !turnoJ1))
                {
                    this.deActivate();
                    return false;
                }
            }
            if (tipo == 1)
            {
                this.usarCero(fichitas, datos, pos, !turnoJ1, !turnoJ1);
                if (this.getContador() == 6)
                {
                    this.deActivate();
                    return false;
                }
            }
            if (tipo == 2)
            {
                FichasTipos aux = Tablero.getFichasTipos(fichitas, pos);
                if ((aux != null) && (Tablero.detectarPosible(fichitas, aux.getPos(), turnoJ1)) && (Tablero.tomarFicha(datos, aux, turnoJ1)))
                {
                    datos.setObligada(true);
                    this.deActivate();
                    return false;
                }
            }
            if (tipo == 3)
            {
                for (FichasTipos fichaIteracion : fichitas)
                {
                    if (fichaIteracion.getID() == datos.getID())
                    {
                        changeTypeFicha(fichaIteracion);
                        this.deActivate();
                        return false;
                    }
                }
            }
            if (tipo == 4) {
                pos.x = (pos.x / 60) * 60 + 15;
                pos.y = (pos.y / 60) * 60 + 15;
                if (transformar(fichitas, pos, this.getDueno()))
                {
                    this.deActivate();
                    return false;
                }
            }
        }
        return proceso;
    }
}
