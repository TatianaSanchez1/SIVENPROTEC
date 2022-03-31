package dominio.controlador;

import dominio.accesoDatos.ProveedorDAO;
import dominio.modelo.Proveedor;
import java.util.List;

public class ControladorProveedor {
    ProveedorDAO proveedorDAO = new ProveedorDAO();
    
    public boolean registrarProveedor(Proveedor proveedor){
        return proveedorDAO.registrarProveedor(proveedor);
    }
    
    public List<Proveedor> listarProveedores(){
        return proveedorDAO.listarProveedores();
    }
    
    public boolean eliminarProveedor(int id){
        return proveedorDAO.eliminarProveedor(id);
    }
    
    public boolean actualizarProveedor(Proveedor proveedor){
        return proveedorDAO.actualizarProveedor(proveedor);
    }
}
