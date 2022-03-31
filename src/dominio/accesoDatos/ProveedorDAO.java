package dominio.accesoDatos;

import dominio.modelo.Proveedor;
import infraestructura.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    Connection connection;
    ConexionBD conexion = ConexionBD.getInstance();
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    public boolean registrarProveedor(Proveedor proveedor){
        String sql = "INSERT INTO proveedores(documento,nombre,direccion,telefono,codigoProducto) VALUES (?,?,?,?,?)";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, proveedor.getDocumentoProveedor());
            preparedStatement.setString(2, proveedor.getNombreProveedor());
            preparedStatement.setString(3, proveedor.getDireccionProveedor());
            preparedStatement.setString(4, proveedor.getNumTelProveedor());
            preparedStatement.setString(5, proveedor.getCodigoProductoProveedor());
            preparedStatement.execute();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Proveedor> listarProveedores(){
        List<Proveedor> listaProveedores = new ArrayList();

        String sql = "SELECT * FROM proveedores";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                listaProveedores.add(new Proveedor(
                        resultSet.getInt("id"),
                        resultSet.getString("documento"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getString("codigoProducto")
                ));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return listaProveedores;
    }

    public boolean eliminarProveedor(int id){
        String sql = "DELETE FROM proveedores WHERE id = ?";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProveedor(Proveedor proveedor){
        String sql = "UPDATE proveedores SET documento = ? ,nombre = ? ,"
                + "direccion = ?, telefono = ?, codigoProducto = ? WHERE id = ?";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, proveedor.getDocumentoProveedor());
            preparedStatement.setString(2, proveedor.getNombreProveedor());
            preparedStatement.setString(3, proveedor.getDireccionProveedor());
            preparedStatement.setString(4, proveedor.getNumTelProveedor());
            preparedStatement.setString(5, proveedor.getCodigoProductoProveedor());
            preparedStatement.setInt(6, proveedor.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
