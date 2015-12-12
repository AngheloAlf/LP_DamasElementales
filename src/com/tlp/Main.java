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
    private int cantidadTurnos = 1;
    private ArrayList<FichasPowerUps> fichitasUps = new ArrayList<FichasPowerUps>();
    private FichasPowerUps powerUpUsado;
    private boolean proceso = false;

    @Override
    public void update(MouseHandler mouseHandler)
    {
        // aquí actualice sus objetos para que puedan interactuar con input de usuario o entre los mismos
        // objetos.

        pos = mouseHandler.getMousePosition();
        if (mouseHandler.isButtonJustPressed())
        {
            if (!proceso)
            {
                aux = Tablero.getFichasTipos(fichitas, pos.x, pos.y);
                if (!datos.isPressed())
                {
                    Tablero.tomarFicha(datos, aux, cantidadTurnos%2 == 1);
                } else {
                    if (aux == null)
                    {
                        if (Tablero.placeFicha(fichitas, datos.getID(), pos, true, cantidadTurnos%2 == 1))
                        {
                            FichasPowerUps.agregarFichaRandom(fichitas, fichitasUps);
                            datos.press(false);

                            powerUpUsado = Tablero.detectarColisionFichas(fichitas, fichitasUps);
                            if (powerUpUsado != null)
                            {
                                proceso = true;
                                powerUpUsado.setDueno(datos.getID()%2);
                            }
                            cantidadTurnos += 1;
                        }
                    } else {
                        if ((aux.isPressed()) && !datos.isObligada())
                        {
                            if (Tablero.placeFicha(fichitas, datos.getID(), datos.getPos(), false, cantidadTurnos%2 == 1))
                            {
                                datos.press(false);
                                datos.setObligada(false);
                            }
                        }
                    }
                }
            } else {
                proceso = powerUpUsado.usarPowerUps(proceso, fichitas, datos, pos, cantidadTurnos%2 == 1);
            }
        }
    }

    @Override
    public void render(Graphics graphics)
    {
        // aquí, y solo aquí, puede dibujar cosas en la pantalla.
        Tablero.dibujarTablero(graphics);

        Tablero.dibujarPowerUps(graphics, fichitasUps);

        Tablero.dibujarFichas(graphics, fichitas);

        Tablero.dibujarFichaMouse(graphics, datos, pos);

        Tablero.dibujarPuntuacion(graphics, cantidadTurnos%2 == 1, fichitas, cantidadTurnos, powerUpUsado);
    }

    public static void main(String[] args)
    {
        // el método main solo se encargará de iniciar el sistema.
        Application.start(800, 600, "Pokemon", 60, new Main());
    }
}