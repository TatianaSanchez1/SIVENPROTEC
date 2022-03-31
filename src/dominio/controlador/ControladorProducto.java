package dominio.controlador;

import dominio.accesoDatos.ProductoDAO;
import dominio.modelo.DatosEmpresa;
import dominio.modelo.Producto;
import java.util.List;
import javax.swing.JComboBox;

public class ControladorProducto {
    ProductoDAO productoDAO = new ProductoDAO();
    
    public ControladorProducto() {
    }
    
    
    public boolean registrarProducto(Producto producto){
        return productoDAO.registrarProducto(producto);
    }
    
    public void consultarProveedor(JComboBox proveedor){
        productoDAO.consultarProveedor(proveedor);
    }
    
    public List<Producto> listarProductos(){
        return productoDAO.listarProductos();
    }
    
    public boolean eliminarProducto(int id){
        return productoDAO.eliminarProducto(id);
    }
    
    public boolean actualizarProducto(Producto producto){
        return productoDAO.actualizarProducto(producto);
    }
    
    public Producto buscarProducto(String codigo){
        return productoDAO.buscarProducto(codigo);
    }
    
    public DatosEmpresa buscarDatos(){
        return productoDAO.buscarDatos();
    }
    
    public boolean actualizarDatos(DatosEmpresa datos){
        return productoDAO.actualizarDatos(datos);
    }
}
