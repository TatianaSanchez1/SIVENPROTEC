package dominio.controlador;

import dominio.accesoDatos.LoginDAO;
import dominio.modelo.Login;

public class ControladorLogin {
    LoginDAO loginDAO = new LoginDAO();
    
    public Login log(String correo, String contrasena){
        return loginDAO.log(correo, contrasena);
    }
    
    public boolean registrar(Login registro){
        return loginDAO.registrar(registro);
    }
}
