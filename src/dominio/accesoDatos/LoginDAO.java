package dominio.accesoDatos;

import dominio.modelo.Login;
import infraestructura.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO {

    Connection connection;

    /**
     * consulta a sistemas de bases de datos en lenguaje SQL
     */
    PreparedStatement preparedStatement;

    /**
     * contiene todas las filas resultantes de la consulta SQL realizada
     */
    ResultSet resultSet;

    ConexionBD conexion = ConexionBD.getInstance();

    public Login log(String correo, String contrasena) {
        Login inicio = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, contrasena);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                inicio = new Login(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("correo"),
                        resultSet.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inicio;
    }

    public boolean registrar(Login registro){
        String sql = "INSERT INTO usuarios(nombre, correo, contrasena) VALUES (?,?,?)";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, registro.getNombre());
            preparedStatement.setString(2, registro.getCorreo());
            preparedStatement.setString(3, registro.getContrasena());

            preparedStatement.execute();

            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
