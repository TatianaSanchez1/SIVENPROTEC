package vista;

import java.awt.event.KeyEvent;

public class Eventos {
    public void textKeyPress(KeyEvent evt){
        //declaramos una variable y le asignamos un evento
        char caracter = evt.getKeyChar();
        if((caracter < 'a' || caracter > 'z') && (caracter < 'A' || caracter > 'Z') &&
                (caracter != (char) KeyEvent.VK_BACK_SPACE) && (caracter != (char) KeyEvent.VK_SPACE) ){
            evt.consume();
        }
    }
    
    public void numberKeyPress(KeyEvent evt){
        //declaramos una variable y le asignamos un evento
        char caracter = evt.getKeyChar();
        if((caracter < '0' || caracter > '9') && (caracter != (char)KeyEvent.VK_BACK_SPACE)){
            evt.consume();
        }
    }
}
