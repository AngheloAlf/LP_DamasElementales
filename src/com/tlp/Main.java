import com.tlp.FichasTipos;
import com.tlp.FichasPowerUps;
import com.tlp.Tablero;

import lp.motor.Application;
import lp.motor.Context;
import lp.motor.Element;
import lp.motor.MouseHandler;

import java.awt.*;
import java.util.ArrayList;

public class Main implements Context
{
    private Point pos;
    private ArrayList<FichasTipos> fichitas = FichasTipos.hacerListaFichas();
    private FichasTipos datos = new FichasTipos(pos, Element.Type.values()[0], -1);
    private FichasTipos aux;
    private boolean turnoJ1 = true;
    private int cantidadTurnos = 1;
    private ArrayList<FichasPowerUps> fichitasUps = new ArrayList<FichasPowerUps>();

    @Override
    public void update(MouseHandler mouseHandler)
    {
        // aquí actualice sus objetos para que puedan interactuar con input de usuario o entre los mismos
        // objetos.

        pos = mouseHandler.getMousePosition();
        if (mouseHandler.isButtonJustPressed())
        {
            aux = Tablero.getFichasTipos(fichitas, pos.x, pos.y);
            if (!datos.isPressed())
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
                }
            } else {
                if (aux == null)
                {
                    if (Tablero.placeFicha(fichitas, datos.getID(), pos, true))
                    {
                        datos.press(false);
                        turnoJ1 = !turnoJ1;
                        cantidadTurnos += 1;
                        FichasPowerUps.agregarFichaRandom(fichitas, fichitasUps, cantidadTurnos);
                    }
                } else {
                    if (aux.isPressed())
                    {
                        if (Tablero.placeFicha(fichitas, datos.getID(), datos.getPos(), false))
                        {
                            datos.press(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics graphics)
    {
        // aquí, y solo aquí, puede dibujar cosas en la pantalla.
        Tablero.dibujarTablero(graphics);

        Tablero.dibujarFichas(graphics, fichitas);

        Tablero.dibujarFichaMouse(graphics, datos, pos);

        Tablero.dibujarPowerUps(graphics, fichitasUps);

        Tablero.dibujarPuntuacion(graphics, turnoJ1, fichitas, cantidadTurnos);
    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(800, 600, "Pokemon", 60, new Main());
    }
}