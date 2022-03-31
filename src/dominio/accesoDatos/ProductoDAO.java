package dominio.accesoDatos;

import dominio.modelo.DatosEmpresa;
import dominio.modelo.Producto;
import infraestructura.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ProductoDAO {
    Connection connection;
    ConexionBD conexion = ConexionBD.getInstance();
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public boolean registrarProducto(Producto producto) {
        String sql = "INSERT INTO productos(codigoProducto, nombre, cantidad, "
                + "precioVenta, precioCompra, proveedor) VALUES(?,?,?,?,?,?)";
        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, producto.getCodigo());
            preparedStatement.setString(2, producto.getNombreProducto());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setFloat(4, producto.getPrecioVenta());
            preparedStatement.setFloat(5, producto.getPrecioCompra());
            preparedStatement.setString(6, producto.getProveedor());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public void consultarProveedor(JComboBox proveedor){
        String sql = "SELECT nombre FROM proveedores";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                proveedor.addItem(resultSet.getString("nombre"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listaProductos.add(new Producto(
                        resultSet.getInt("id"),
                        resultSet.getString("codigoProducto"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("cantidad"),
                        resultSet.getFloat("precioVenta"),
                        resultSet.getFloat("precioCompra"),
                        resultSet.getString("proveedor")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

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

    public boolean actualizarProducto(Producto producto){
        String sql = "UPDATE productos SET codigoProducto = ?, nombre = ?, cantidad = ?,"
                + "precioVenta = ?, precioCompra = ?, proveedor = ? WHERE id = ?";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, producto.getCodigo());
            preparedStatement.setString(2, producto.getNombreProducto());
            preparedStatement.setInt(3, producto.getCantidad());
            preparedStatement.setFloat(4, producto.getPrecioVenta());
            preparedStatement.setFloat(5, producto.getPrecioCompra());
            preparedStatement.setString(6, producto.getProveedor());
            preparedStatement.setInt(7, producto.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Producto buscarProducto(String codigo){
        Producto producto = new Producto();
        String sql = "SELECT * FROM productos WHERE codigoProducto = ?";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                producto.setNombreProducto(resultSet.getString("nombre"));
                producto.setPrecioVenta(resultSet.getFloat("precioVenta"));
                producto.setCantidad(resultSet.getInt("cantidad"));

            }
        } catch(SQLException e){
            System.out.println(e.toString());
        }
        return producto;
    }

    public DatosEmpresa buscarDatos(){
        DatosEmpresa datos = null;
        String sql = "SELECT * FROM config";

        try{
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                datos = new DatosEmpresa(
                        resultSet.getInt("id"),
                        resultSet.getString("nit"),
                        resultSet.getString("nombre"),
                        resultSet.getString("telefono"),
                        resultSet.getString("direccion"));

            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return datos;
    }

    public boolean actualizarDatos(DatosEmpresa datos){
        String sql = "UPDATE config SET nombre = ?, nit = ?, telefono = ?, direccion = ? WHERE id = ?";

        try {
            connection = conexion.getConexion();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, datos.getNombre());
            preparedStatement.setString(2, datos.getNit());
            preparedStatement.setString(3, datos.getTelefono());
            preparedStatement.setString(4, datos.getDireccion());
            preparedStatement.setInt(5, datos.getId());

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
