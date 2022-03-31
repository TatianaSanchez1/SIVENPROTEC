package dominio.controlador;

import vista.Eventos;
import java.awt.event.KeyEvent;

public class ControladorEventos {
    Eventos eventos = new Eventos();

    public void textKeyPress(KeyEvent evt){
        eventos.textKeyPress(evt);
    }
    
    public void numberKeyPress(KeyEvent evt){
        eventos.numberKeyPress(evt);
    }
}
