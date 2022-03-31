package dominio.controlador;

import dominio.accesoDatos.ClienteDAO;
import dominio.modelo.Cliente;
import java.util.List;

public class ControladorCliente {
    ClienteDAO clienteDAO = new ClienteDAO();

    public ControladorCliente() {
    }

    public List<Cliente> listarClientes(){
        return clienteDAO.listarClientes();
    }

    public boolean registrarClientes(Cliente cliente){
        return clienteDAO.registrarClientes(cliente);
    }

    public boolean eliminarCliente(int id){
        return clienteDAO.eliminarCliente(id);
    }

    public boolean actualizarCliente(Cliente cliente){
        return clienteDAO.actualizarCliente(cliente);
    }

    public Cliente buscarClientePorDocumento(String documento){
        return clienteDAO.buscarClientePorDocumento(documento);
    }

    public Cliente buscarClientePorNombre(String nombre){
        return clienteDAO.buscarClientePorNombre(nombre);
    }
}
