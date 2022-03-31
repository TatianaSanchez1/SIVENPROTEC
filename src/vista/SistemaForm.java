/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dominio.modelo.Cliente;
import dominio.modelo.DatosEmpresa;
import dominio.modelo.DetalleVenta;
import dominio.modelo.Login;
import dominio.controlador.*;
import dominio.modelo.Producto;
import dominio.modelo.Proveedor;
import dominio.modelo.Venta;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Personal
 */
public class SistemaForm extends javax.swing.JFrame {

    Login login = new Login();

    Cliente cliente = new Cliente();
    ControladorCliente controladorCliente = new ControladorCliente();

    Proveedor proveedor = new Proveedor();
    ControladorProveedor controladorProveedor = new ControladorProveedor();

    Producto producto = new Producto();
    DatosEmpresa datosEmpresa = new DatosEmpresa();
    ControladorProducto controladorProducto = new ControladorProducto();

    Venta venta = new Venta();
    DetalleVenta detalleVenta = new DetalleVenta();
    ControladorVenta controladorVenta = new ControladorVenta();

    ControladorEventos controladorEventos = new ControladorEventos();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel temporal = new DefaultTableModel();



    //
    int item;
    float totalPagar = 0.0f;

    /**
     * Creates new form Sistema
     */
    public SistemaForm() {
        initComponents();
    }

    public SistemaForm(Login privilegios){
        initComponents();
        //Centrar la venta en la pantalla
        this.setLocationRelativeTo(null);

        controladorProducto.consultarProveedor(jComboBox_ProveedorProducto_Tab4);
        AutoCompleteDecorator.decorate(jComboBox_ProveedorProducto_Tab4);

        jTextField_IDProducto_Tab1.setVisible(false);
        jTextField_DireccionCliente_Tab1.setVisible(false);
        jTextField_TelefonoCliente_Tab1.setVisible(false);
        jTextField_CorreoCliente_Tab1.setVisible(false);
        jTextField_IDCliente_Tab2.setVisible(false);
        jTextField_IDProveedor_Tab3.setVisible(false);
        jTextField_IDProducto_Tab4.setVisible(false);
        jTextField_IDVenta_Tab5.setVisible(false);
        jTextField_Id_Empresa.setVisible(false);
        jLabel_NombreVendedor.setVisible(false);

        jLabel_NombreVendedor.setText(login.getNombre());

        listarDatos();
    }

    private void listarDatos() {
        datosEmpresa = controladorProducto.buscarDatos();
        jTextField_Id_Empresa.setText("" + datosEmpresa.getId());
        jTextField_NIT_Empresa.setText("" + datosEmpresa.getNit());
        jTextField_Nombre_Empresa.setText("" + datosEmpresa.getNombre());
        jTextField_Telefono_Empresa.setText("" + datosEmpresa.getTelefono());
        jTextField_Direccion_Empresa.setText("" + datosEmpresa.getDireccion());

    }

    private void totalPagar() {
        totalPagar = 0.0f;
        int numFila = jTable_Venta_Tab1.getRowCount();

        for (int i = 0; i < numFila; i++) {
            float calcular = Float.parseFloat(String.valueOf(jTable_Venta_Tab1.getModel().getValueAt(i, 4)));
            totalPagar = totalPagar + calcular;
        }
        jLabel_ValorPagar_Tab1.setText(String.format("%.2f", totalPagar));
    }

    private void registrarVenta() {
        String cliente = jTextField_NombreCliente_Tab1.getText();
        String vendedor = "SIVENPROTEC";
        float total = totalPagar;

        venta.setCliente(cliente);
        venta.setVendedor(vendedor);
        venta.setTotal(total);

        controladorVenta.registrarVenta(venta);
    }

    private void registrarDetalle() {
        int id = controladorVenta.idVenta();
        for (int i = 0; i < jTable_Venta_Tab1.getRowCount(); i++) {
            String codigo = jTable_Venta_Tab1.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(jTable_Venta_Tab1.getValueAt(i, 2).toString());
            float precio = Float.parseFloat(jTable_Venta_Tab1.getValueAt(i, 4).toString());

            detalleVenta.setCodigoProducto(codigo);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecio(precio);
            detalleVenta.setId(id);

            controladorVenta.registrarDetalle(detalleVenta);
        }
    }

    private void actualizarStock() {
        for (int i = 0; i < jTable_Venta_Tab1.getRowCount(); i++) {
            String codigo = jTable_Venta_Tab1.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(jTable_Venta_Tab1.getValueAt(i, 2).toString());

            producto = controladorProducto.buscarProducto(codigo);
            int stockActual = producto.getCantidad() - cantidad;

            controladorVenta.actualizarStock(stockActual, codigo);

             if (controladorVenta.alerta(codigo)) {
                JOptionPane.showMessageDialog(null, "Debe realizar una nueva compra del producto "
                        + codigo, " Advertencia ",2);
                jTabbedPane1.setSelectedIndex(2);
            }
        }
    }

    private void limpiarTextFieldVenta() {
        jTextField_CodigoProducto_Tab1.setText("");
        jTextField_NombreProducto_Tab1.setText("");
        jTextField_CantidadCompra_Tab1.setText("");
        jTextField_PrecioVenta_Tab1.setText("");
        jTextField_Stock_Tab1.setText("");
        jTextField_IDProducto_Tab1.setText("");
    }

    private void limpiarTextFieldVentaCliente() {
        jTextField_DocumentoCliente_Tab1.setText("");
        jTextField_NombreCliente_Tab1.setText("");
        jTextField_DireccionCliente_Tab1.setText("");
        jTextField_TelefonoCliente_Tab1.setText("");
        jTextField_CorreoCliente_Tab1.setText("");
    }

    private void limpiarTextFieldCliente() {
        jTextField_IDCliente_Tab2.setText("");
        jTextField_DocumentoCliente_Tab2.setText("");
        jTextField_NombreCliente_Tab2.setText("");
        jTextField_TelefonoCliente_Tab2.setText("");
        jTextField_CorreoCliente_Tab2.setText("");
        jTextField_DireccionCliente_Tab2.setText("");
    }

    private void limpiarTextFieldProveedor() {
        jTextField_IDProveedor_Tab3.setText("");
        jTextField_DocumentoProveedor_Tab3.setText("");
        jTextField_NombreProveedor_Tab3.setText("");
        jTextField_TelefonoProveedor_Tab3.setText("");
        jTextField_DireccionProveedor_Tab3.setText("");
        jTextField_CodigoProducto_Tab3.setText("");
    }

    public void limpiarTextFieldProducto() {
        jTextField_CodigoProducto_Tab4.setText("");
        jTextField_NombreProducto_Tab4.setText("");
        jTextField_CantidadProducto_Tab4.setText("");
        jTextField_PrecioVenta_Tab4.setText("");
        jTextField_PrecioCompra_Tab4.setText("");
        jComboBox_ProveedorProducto_Tab4.setSelectedItem(null);
    }

    public void limpiarTextFieldDatosEmpresa(){
        jTextField_NIT_Empresa.setText("");
        jTextField_Nombre_Empresa.setText("");
        jTextField_Telefono_Empresa.setText("");
        jTextField_Direccion_Empresa.setText("");
    }

    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    private void limpiarTablaVenta() {
        temporal = (DefaultTableModel) jTable_Venta_Tab1.getModel();

        int numFilas = jTable_Venta_Tab1.getRowCount();

        for (int i = 0; i < numFilas; i++) {
            temporal.removeRow(0);
        }
    }

    private void listarClientesForm() {
        List<Cliente> listaClientes = controladorCliente.listarClientes();
        modelo = (DefaultTableModel) jTable_Clientes.getModel();

        Object[] object = new Object[6];

        for (int i = 0; i < listaClientes.size(); i++) {
            object[0] = listaClientes.get(i).getId();
            object[1] = listaClientes.get(i).getDocumentoCliente();
            object[2] = listaClientes.get(i).getNombreCliente();
            object[3] = listaClientes.get(i).getNumTelCliente();
            object[4] = listaClientes.get(i).getCorreoCliente();
            object[5] = listaClientes.get(i).getDireccionCliente();

            modelo.addRow(object);
        }
        jTable_Clientes.setModel(modelo);
    }

    private void listarProveedoresForm() {
        List<Proveedor> listaProveedores = controladorProveedor.listarProveedores();
        modelo = (DefaultTableModel) jTable_Proveedores.getModel();

        Object[] object = new Object[6];

        for (int i = 0; i < listaProveedores.size(); i++) {
            object[0] = listaProveedores.get(i).getId();
            object[1] = listaProveedores.get(i).getDocumentoProveedor();
            object[2] = listaProveedores.get(i).getNombreProveedor();
            object[3] = listaProveedores.get(i).getNumTelProveedor();
            object[4] = listaProveedores.get(i).getDireccionProveedor();
            object[5] = listaProveedores.get(i).getCodigoProductoProveedor();

            modelo.addRow(object);
        }
        jTable_Proveedores.setModel(modelo);
    }

    private void listarProductosForm() {
        List<Producto> listaProductos = controladorProducto.listarProductos();
        modelo = (DefaultTableModel) jTable_Productos.getModel();

        Object[] object = new Object[7];

        for (int i = 0; i < listaProductos.size(); i++) {
            object[0] = listaProductos.get(i).getId();
            object[1] = listaProductos.get(i).getCodigo();
            object[2] = listaProductos.get(i).getNombreProducto();
            object[3] = listaProductos.get(i).getCantidad();
            object[4] = listaProductos.get(i).getProveedor();
            object[5] = listaProductos.get(i).getPrecioVenta();
            object[6] = listaProductos.get(i).getPrecioCompra();

            modelo.addRow(object);
        }
        jTable_Productos.setModel(modelo);
    }

    private void listarVentasForm() {
        List<Venta> listaVentas = controladorVenta.listarVentas();
        modelo = (DefaultTableModel) jTable_Ventas.getModel();

        Object[] object = new Object[4];

        for (int i = 0; i < listaVentas.size(); i++) {
            object[0] = listaVentas.get(i).getId();
            object[1] = listaVentas.get(i).getCliente();
            object[2] = listaVentas.get(i).getVendedor();
            object[3] = listaVentas.get(i).getTotal();

            modelo.addRow(object);
        }
        jTable_Ventas.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_BarraBusqueda = new javax.swing.JPanel();
        jButton_NuevaVenta = new javax.swing.JButton();
        jButton_Clientes = new javax.swing.JButton();
        jButton_Proveedor = new javax.swing.JButton();
        jButton_Productos = new javax.swing.JButton();
        jButton_Ventas = new javax.swing.JButton();
        jButton_Información = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2_LogoImg = new javax.swing.JLabel();
        jLabel_NombreVendedor = new javax.swing.JLabel();
        jLabel_Img_Encabezado = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_NuevaVenta = new javax.swing.JPanel();
        jLabel_CodigoProducto_Tab1 = new javax.swing.JLabel();
        jLabel_NombreProducto_Tab1 = new javax.swing.JLabel();
        jLabel_CantidadCompra_Tab1 = new javax.swing.JLabel();
        jLabel_PrecioCompra_Tab1 = new javax.swing.JLabel();
        jLabel_Stock_Tab1 = new javax.swing.JLabel();
        jButton_Eliminar_Tab1 = new javax.swing.JButton();
        jTextField_CodigoProducto_Tab1 = new javax.swing.JTextField();
        jTextField_NombreProducto_Tab1 = new javax.swing.JTextField();
        jTextField_CantidadCompra_Tab1 = new javax.swing.JTextField();
        jTextField_PrecioVenta_Tab1 = new javax.swing.JTextField();
        jTextField_Stock_Tab1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Venta_Tab1 = new javax.swing.JTable();
        jLabel_DocumentoCliente_Tab1 = new javax.swing.JLabel();
        jLabel_NombreCliente_Tab1 = new javax.swing.JLabel();
        jTextField_DocumentoCliente_Tab1 = new javax.swing.JTextField();
        jTextField_NombreCliente_Tab1 = new javax.swing.JTextField();
        jButton_GenerarVenta_Tab1 = new javax.swing.JButton();
        jLabel_TotalPagar_Tab1 = new javax.swing.JLabel();
        jLabel_ValorPagar_Tab1 = new javax.swing.JLabel();
        jTextField_DireccionCliente_Tab1 = new javax.swing.JTextField();
        jTextField_IDProducto_Tab1 = new javax.swing.JTextField();
        jTextField_TelefonoCliente_Tab1 = new javax.swing.JTextField();
        jTextField_CorreoCliente_Tab1 = new javax.swing.JTextField();
        jPanel_Clientes = new javax.swing.JPanel();
        jLabel_DocumentoCliente_Tab2 = new javax.swing.JLabel();
        jLabel_NombreCliente_Tab2 = new javax.swing.JLabel();
        jLabel_TelefonoCliente_Tab2 = new javax.swing.JLabel();
        jLabel_CorreoCliente_Tab2 = new javax.swing.JLabel();
        jLabel_DireccionCliente_Tab2 = new javax.swing.JLabel();
        jTextField_DocumentoCliente_Tab2 = new javax.swing.JTextField();
        jTextField_NombreCliente_Tab2 = new javax.swing.JTextField();
        jTextField_TelefonoCliente_Tab2 = new javax.swing.JTextField();
        jTextField_CorreoCliente_Tab2 = new javax.swing.JTextField();
        jTextField_DireccionCliente_Tab2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Clientes = new javax.swing.JTable();
        jButton_GuardarCliente_Tab2 = new javax.swing.JButton();
        jButton_ActualizarCliente_Tab2 = new javax.swing.JButton();
        jButton_EliminarCliente_Tab2 = new javax.swing.JButton();
        jButton_NuevoCliente_Tab2 = new javax.swing.JButton();
        jTextField_IDCliente_Tab2 = new javax.swing.JTextField();
        jPanel_Proveedor = new javax.swing.JPanel();
        jLabel_DocumentoProveedor_Tab3 = new javax.swing.JLabel();
        jLabel_NombreProveedor_Tab3 = new javax.swing.JLabel();
        jLabel_DireccionProveedor_Tab3 = new javax.swing.JLabel();
        jLabel_TelefonoProveedor_Tab3 = new javax.swing.JLabel();
        jLabel_CodigoProducto_Tab3 = new javax.swing.JLabel();
        jTextField_DocumentoProveedor_Tab3 = new javax.swing.JTextField();
        jTextField_NombreProveedor_Tab3 = new javax.swing.JTextField();
        jTextField_DireccionProveedor_Tab3 = new javax.swing.JTextField();
        jTextField_TelefonoProveedor_Tab3 = new javax.swing.JTextField();
        jTextField_CodigoProducto_Tab3 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_Proveedores = new javax.swing.JTable();
        jButton_GuardarProveedor_Tab3 = new javax.swing.JButton();
        jButton_NuevoProveedor_Tab3 = new javax.swing.JButton();
        jButton_EliminarProveedor_Tab3 = new javax.swing.JButton();
        jButton_ActualizarProveedor_Tab3 = new javax.swing.JButton();
        jTextField_IDProveedor_Tab3 = new javax.swing.JTextField();
        jPanel_Productos = new javax.swing.JPanel();
        jLabel_CodigoProducto_Tab4 = new javax.swing.JLabel();
        jLabel_NombreProducto_Tab4 = new javax.swing.JLabel();
        jLabel_CantidadProducto_Tab4 = new javax.swing.JLabel();
        jLabel_PrecioVenta_Tab4 = new javax.swing.JLabel();
        jLabel_PrecioCompra_Tab4 = new javax.swing.JLabel();
        jTextField_CodigoProducto_Tab4 = new javax.swing.JTextField();
        jTextField_NombreProducto_Tab4 = new javax.swing.JTextField();
        jTextField_CantidadProducto_Tab4 = new javax.swing.JTextField();
        jTextField_PrecioVenta_Tab4 = new javax.swing.JTextField();
        jTextField_PrecioCompra_Tab4 = new javax.swing.JTextField();
        jLabel_ProveedorProducto_Tab4 = new javax.swing.JLabel();
        jComboBox_ProveedorProducto_Tab4 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_Productos = new javax.swing.JTable();
        jButton_GuardarProducto_Tab4 = new javax.swing.JButton();
        jButton_ActualizarProducto_Tab4 = new javax.swing.JButton();
        jButton_EliminarProducto_Tab4 = new javax.swing.JButton();
        jButton_NuevoProducto_Tab4 = new javax.swing.JButton();
        jTextField_IDProducto_Tab4 = new javax.swing.JTextField();
        jPanel_Ventas = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_Ventas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField_IDVenta_Tab5 = new javax.swing.JTextField();
        jPanel_Informacion = new javax.swing.JPanel();
        jLabel_NIT_Empresa = new javax.swing.JLabel();
        jLabel_Nombre_Empresa = new javax.swing.JLabel();
        jLabel_Telefono_Empresa = new javax.swing.JLabel();
        jLabel_Direccion_Empresa = new javax.swing.JLabel();
        jLabel_DatosEmpresa = new javax.swing.JLabel();
        jTextField_NIT_Empresa = new javax.swing.JTextField();
        jTextField_Nombre_Empresa = new javax.swing.JTextField();
        jTextField_Telefono_Empresa = new javax.swing.JTextField();
        jTextField_Direccion_Empresa = new javax.swing.JTextField();
        jButton_Guardar_DatosEmpresa = new javax.swing.JButton();
        jTextField_Id_Empresa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Ventas de Productos Electrónicos");

        jPanel_BarraBusqueda.setBackground(new java.awt.Color(0, 5, 48));

        jButton_NuevaVenta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_NuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/nuevaVenta.png"))); // NOI18N
        jButton_NuevaVenta.setText("Nueva Venta");
        jButton_NuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevaVentaActionPerformed(evt);
            }
        });

        jButton_Clientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/Clientes.png"))); // NOI18N
        jButton_Clientes.setText("Clientes");
        jButton_Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ClientesActionPerformed(evt);
            }
        });

        jButton_Proveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/proveedor.png"))); // NOI18N
        jButton_Proveedor.setText("Proveedor");
        jButton_Proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ProveedorActionPerformed(evt);
            }
        });

        jButton_Productos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/producto.png"))); // NOI18N
        jButton_Productos.setText("Productos");
        jButton_Productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ProductosActionPerformed(evt);
            }
        });

        jButton_Ventas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/compras.png"))); // NOI18N
        jButton_Ventas.setText("Ventas");
        jButton_Ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VentasActionPerformed(evt);
            }
        });

        jButton_Información.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton_Información.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/config.png"))); // NOI18N
        jButton_Información.setText("Información");
        jButton_Información.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InformaciónActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIVENPROTEC");

        jLabel2_LogoImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/logo.png"))); // NOI18N

        jLabel_NombreVendedor.setText("SIVENPROTEC");

        javax.swing.GroupLayout jPanel_BarraBusquedaLayout = new javax.swing.GroupLayout(jPanel_BarraBusqueda);
        jPanel_BarraBusqueda.setLayout(jPanel_BarraBusquedaLayout);
        jPanel_BarraBusquedaLayout.setHorizontalGroup(
            jPanel_BarraBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BarraBusquedaLayout.createSequentialGroup()
                .addGroup(jPanel_BarraBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_BarraBusquedaLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel_BarraBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_NuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Ventas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Información, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_BarraBusquedaLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel_NombreVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_BarraBusquedaLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel2_LogoImg)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel_BarraBusquedaLayout.setVerticalGroup(
            jPanel_BarraBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_BarraBusquedaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2_LogoImg, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel_NombreVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_NuevaVenta)
                .addGap(18, 18, 18)
                .addComponent(jButton_Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Proveedor)
                .addGap(18, 18, 18)
                .addComponent(jButton_Productos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jButton_Ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Información, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        jLabel_Img_Encabezado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/encabezado.jpg"))); // NOI18N
        jLabel_Img_Encabezado.setToolTipText("");

        jLabel_CodigoProducto_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_CodigoProducto_Tab1.setText("Codigo de Producto:");

        jLabel_NombreProducto_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_NombreProducto_Tab1.setText("Nombre:");

        jLabel_CantidadCompra_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_CantidadCompra_Tab1.setText("Cantidad:");

        jLabel_PrecioCompra_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_PrecioCompra_Tab1.setText("Precio:");

        jLabel_Stock_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_Stock_Tab1.setText("Stock:");

        jButton_Eliminar_Tab1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_Eliminar_Tab1.setText("Eliminar");
        jButton_Eliminar_Tab1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Eliminar_Tab1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jButton_Eliminar_Tab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Eliminar_Tab1ActionPerformed(evt);
            }
        });

        jTextField_CodigoProducto_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_CodigoProducto_Tab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_CodigoProducto_Tab1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CodigoProducto_Tab1KeyTyped(evt);
            }
        });

        jTextField_NombreProducto_Tab1.setEditable(false);
        jTextField_NombreProducto_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_NombreProducto_Tab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NombreProducto_Tab1KeyTyped(evt);
            }
        });

        jTextField_CantidadCompra_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_CantidadCompra_Tab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_CantidadCompra_Tab1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CantidadCompra_Tab1KeyTyped(evt);
            }
        });

        jTextField_PrecioVenta_Tab1.setEditable(false);
        jTextField_PrecioVenta_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_Stock_Tab1.setEditable(false);
        jTextField_Stock_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTable_Venta_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Venta_Tab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Cantidad", "Precio", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_Venta_Tab1);
        if (jTable_Venta_Tab1.getColumnModel().getColumnCount() > 0) {
            jTable_Venta_Tab1.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable_Venta_Tab1.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTable_Venta_Tab1.getColumnModel().getColumn(2).setPreferredWidth(30);
            jTable_Venta_Tab1.getColumnModel().getColumn(3).setPreferredWidth(30);
            jTable_Venta_Tab1.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel_DocumentoCliente_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_DocumentoCliente_Tab1.setText("Documento:");

        jLabel_NombreCliente_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_NombreCliente_Tab1.setText("Nombre:");

        jTextField_DocumentoCliente_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_DocumentoCliente_Tab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_DocumentoCliente_Tab1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_DocumentoCliente_Tab1KeyTyped(evt);
            }
        });

        jTextField_NombreCliente_Tab1.setEditable(false);
        jTextField_NombreCliente_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField_NombreCliente_Tab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NombreCliente_Tab1ActionPerformed(evt);
            }
        });

        jButton_GenerarVenta_Tab1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_GenerarVenta_Tab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/print.png"))); // NOI18N
        jButton_GenerarVenta_Tab1.setText("Generar Venta");
        jButton_GenerarVenta_Tab1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jButton_GenerarVenta_Tab1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jButton_GenerarVenta_Tab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GenerarVenta_Tab1ActionPerformed(evt);
            }
        });

        jLabel_TotalPagar_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_TotalPagar_Tab1.setText("Total a Pagar:");

        jLabel_ValorPagar_Tab1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_ValorPagar_Tab1.setText("........");

        javax.swing.GroupLayout jPanel_NuevaVentaLayout = new javax.swing.GroupLayout(jPanel_NuevaVenta);
        jPanel_NuevaVenta.setLayout(jPanel_NuevaVentaLayout);
        jPanel_NuevaVentaLayout.setHorizontalGroup(
            jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addComponent(jTextField_IDProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_TotalPagar_Tab1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_ValorPagar_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_CodigoProducto_Tab1)
                                    .addComponent(jTextField_CodigoProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_NombreProducto_Tab1)
                                    .addComponent(jTextField_NombreProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_CantidadCompra_Tab1)
                                    .addComponent(jTextField_CantidadCompra_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_PrecioCompra_Tab1)
                                    .addComponent(jTextField_PrecioVenta_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_Stock_Tab1)
                                    .addComponent(jTextField_Stock_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(jButton_Eliminar_Tab1)
                                .addGap(22, 22, 22)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                                .addComponent(jLabel_DocumentoCliente_Tab1)
                                .addGap(126, 126, 126)
                                .addComponent(jLabel_NombreCliente_Tab1))
                            .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                                .addComponent(jTextField_DocumentoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_NombreCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_DireccionCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_TelefonoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_CorreoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_GenerarVenta_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(236, 236, 236))))
        );
        jPanel_NuevaVentaLayout.setVerticalGroup(
            jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_CodigoProducto_Tab1)
                            .addComponent(jLabel_NombreProducto_Tab1)
                            .addComponent(jLabel_CantidadCompra_Tab1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_CodigoProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_NombreProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_CantidadCompra_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addComponent(jLabel_Stock_Tab1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Stock_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addComponent(jLabel_PrecioCompra_Tab1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_PrecioVenta_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_Eliminar_Tab1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_ValorPagar_Tab1)
                            .addComponent(jLabel_TotalPagar_Tab1))
                        .addGap(9, 9, 9)
                        .addComponent(jButton_GenerarVenta_Tab1))
                    .addGroup(jPanel_NuevaVentaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_IDProducto_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_DocumentoCliente_Tab1)
                            .addComponent(jLabel_NombreCliente_Tab1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_NuevaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_DocumentoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_NombreCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_DireccionCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_TelefonoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_CorreoCliente_Tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel_NuevaVenta);

        jLabel_DocumentoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_DocumentoCliente_Tab2.setText("*Documento:");

        jLabel_NombreCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_NombreCliente_Tab2.setText("*Nombre Completo:");

        jLabel_TelefonoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_TelefonoCliente_Tab2.setText("*Teléfono:");

        jLabel_CorreoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_CorreoCliente_Tab2.setText("Correo:");

        jLabel_DireccionCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_DireccionCliente_Tab2.setText("Dirección:");

        jTextField_DocumentoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_NombreCliente_Tab2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_TelefonoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_CorreoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_DireccionCliente_Tab2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTable_Clientes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Documento", "Nombre", "Teléfono", "Dirección", "Correo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_ClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_Clientes);
        if (jTable_Clientes.getColumnModel().getColumnCount() > 0) {
            jTable_Clientes.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable_Clientes.getColumnModel().getColumn(1).setPreferredWidth(50);
            jTable_Clientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable_Clientes.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable_Clientes.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable_Clientes.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jButton_GuardarCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_GuardarCliente_Tab2.setText("Guardar");
        jButton_GuardarCliente_Tab2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_GuardarCliente_Tab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarCliente_Tab2ActionPerformed(evt);
            }
        });

        jButton_ActualizarCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_ActualizarCliente_Tab2.setText("Actualizar");
        jButton_ActualizarCliente_Tab2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_ActualizarCliente_Tab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarCliente_Tab2ActionPerformed(evt);
            }
        });

        jButton_EliminarCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_EliminarCliente_Tab2.setText("Eliminar");
        jButton_EliminarCliente_Tab2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_EliminarCliente_Tab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarCliente_Tab2ActionPerformed(evt);
            }
        });

        jButton_NuevoCliente_Tab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_NuevoCliente_Tab2.setText("Nuevo");
        jButton_NuevoCliente_Tab2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_NuevoCliente_Tab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoCliente_Tab2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ClientesLayout = new javax.swing.GroupLayout(jPanel_Clientes);
        jPanel_Clientes.setLayout(jPanel_ClientesLayout);
        jPanel_ClientesLayout.setHorizontalGroup(
            jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_DireccionCliente_Tab2)
                    .addComponent(jLabel_CorreoCliente_Tab2)
                    .addComponent(jLabel_TelefonoCliente_Tab2)
                    .addComponent(jLabel_NombreCliente_Tab2)
                    .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                        .addComponent(jTextField_IDCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_DocumentoCliente_Tab2))
                    .addComponent(jButton_GuardarCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_EliminarCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_DocumentoCliente_Tab2)
                            .addComponent(jTextField_NombreCliente_Tab2)
                            .addComponent(jTextField_TelefonoCliente_Tab2)
                            .addComponent(jTextField_CorreoCliente_Tab2)
                            .addComponent(jTextField_DireccionCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ClientesLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_ActualizarCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_NuevoCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_ClientesLayout.setVerticalGroup(
            jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_DocumentoCliente_Tab2)
                            .addComponent(jTextField_DocumentoCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_IDCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_NombreCliente_Tab2)
                            .addComponent(jTextField_NombreCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_TelefonoCliente_Tab2)
                            .addComponent(jTextField_TelefonoCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_CorreoCliente_Tab2)
                            .addComponent(jTextField_CorreoCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_DireccionCliente_Tab2)
                            .addComponent(jTextField_DireccionCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_NuevoCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_GuardarCliente_Tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel_ClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_EliminarCliente_Tab2, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(jButton_ActualizarCliente_Tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel_ClientesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel_Clientes);

        jLabel_DocumentoProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_DocumentoProveedor_Tab3.setText("Documento:");

        jLabel_NombreProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_NombreProveedor_Tab3.setText("Nombre:");

        jLabel_DireccionProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_DireccionProveedor_Tab3.setText("Dirección:");

        jLabel_TelefonoProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_TelefonoProveedor_Tab3.setText("Teléfono:");

        jLabel_CodigoProducto_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_CodigoProducto_Tab3.setText("Código de Producto:");

        jTextField_DocumentoProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_NombreProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_DireccionProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_TelefonoProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_CodigoProducto_Tab3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTable_Proveedores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Proveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Documento", "Nombre", "Dirección", "Teléfono", "Cod. Producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_ProveedoresMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_Proveedores);
        if (jTable_Proveedores.getColumnModel().getColumnCount() > 0) {
            jTable_Proveedores.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable_Proveedores.getColumnModel().getColumn(1).setPreferredWidth(40);
            jTable_Proveedores.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable_Proveedores.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTable_Proveedores.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTable_Proveedores.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jButton_GuardarProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_GuardarProveedor_Tab3.setText("Guardar");
        jButton_GuardarProveedor_Tab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarProveedor_Tab3ActionPerformed(evt);
            }
        });

        jButton_NuevoProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_NuevoProveedor_Tab3.setText("Nuevo");
        jButton_NuevoProveedor_Tab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoProveedor_Tab3ActionPerformed(evt);
            }
        });

        jButton_EliminarProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_EliminarProveedor_Tab3.setText("Eliminar");
        jButton_EliminarProveedor_Tab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarProveedor_Tab3ActionPerformed(evt);
            }
        });

        jButton_ActualizarProveedor_Tab3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_ActualizarProveedor_Tab3.setText("Actualizar");
        jButton_ActualizarProveedor_Tab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarProveedor_Tab3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ProveedorLayout = new javax.swing.GroupLayout(jPanel_Proveedor);
        jPanel_Proveedor.setLayout(jPanel_ProveedorLayout);
        jPanel_ProveedorLayout.setHorizontalGroup(
            jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ProveedorLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel_CodigoProducto_Tab3)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ProveedorLayout.createSequentialGroup()
                            .addComponent(jTextField_IDProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel_DocumentoProveedor_Tab3))
                        .addComponent(jLabel_NombreProveedor_Tab3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_TelefonoProveedor_Tab3)
                            .addComponent(jLabel_DireccionProveedor_Tab3)))
                    .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton_EliminarProveedor_Tab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_GuardarProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_DocumentoProveedor_Tab3)
                            .addComponent(jTextField_CodigoProducto_Tab3)
                            .addComponent(jTextField_NombreProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_DireccionProveedor_Tab3)
                            .addComponent(jTextField_TelefonoProveedor_Tab3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel_ProveedorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_NuevoProveedor_Tab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_ActualizarProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel_ProveedorLayout.setVerticalGroup(
            jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ProveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ProveedorLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_DocumentoProveedor_Tab3)
                            .addComponent(jTextField_DocumentoProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_IDProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_NombreProveedor_Tab3)
                            .addComponent(jTextField_NombreProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_DireccionProveedor_Tab3)
                            .addComponent(jTextField_DireccionProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_TelefonoProveedor_Tab3)
                            .addComponent(jTextField_TelefonoProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_CodigoProducto_Tab3)
                            .addComponent(jTextField_CodigoProducto_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_GuardarProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_NuevoProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel_ProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_ActualizarProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_EliminarProveedor_Tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab3", jPanel_Proveedor);

        jLabel_CodigoProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_CodigoProducto_Tab4.setText("Código:");

        jLabel_NombreProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_NombreProducto_Tab4.setText("Nombre:");

        jLabel_CantidadProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_CantidadProducto_Tab4.setText("Cantidad:");

        jLabel_PrecioVenta_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_PrecioVenta_Tab4.setText("Precio de Venta:");

        jLabel_PrecioCompra_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_PrecioCompra_Tab4.setText("Precio de Compra:");

        jTextField_CodigoProducto_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_NombreProducto_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_CantidadProducto_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_PrecioVenta_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_PrecioCompra_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel_ProveedorProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_ProveedorProducto_Tab4.setText("Proveedor:");

        jComboBox_ProveedorProducto_Tab4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_ProveedorProducto_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ProveedorProducto_Tab4ActionPerformed(evt);
            }
        });

        jTable_Productos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Código", "Nombre", "Cantidad", "Proveedor", "Precio de Venta", "Precio de Compra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_ProductosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable_Productos);
        if (jTable_Productos.getColumnModel().getColumnCount() > 0) {
            jTable_Productos.getColumnModel().getColumn(0).setPreferredWidth(3);
            jTable_Productos.getColumnModel().getColumn(1).setPreferredWidth(30);
            jTable_Productos.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTable_Productos.getColumnModel().getColumn(3).setPreferredWidth(10);
            jTable_Productos.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTable_Productos.getColumnModel().getColumn(5).setResizable(false);
            jTable_Productos.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTable_Productos.getColumnModel().getColumn(6).setResizable(false);
            jTable_Productos.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        jButton_GuardarProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_GuardarProducto_Tab4.setText("Guardar");
        jButton_GuardarProducto_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarProducto_Tab4ActionPerformed(evt);
            }
        });

        jButton_ActualizarProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_ActualizarProducto_Tab4.setText("Actualizar");
        jButton_ActualizarProducto_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarProducto_Tab4ActionPerformed(evt);
            }
        });

        jButton_EliminarProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_EliminarProducto_Tab4.setText("Eliminar");
        jButton_EliminarProducto_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarProducto_Tab4ActionPerformed(evt);
            }
        });

        jButton_NuevoProducto_Tab4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_NuevoProducto_Tab4.setText("Nuevo");
        jButton_NuevoProducto_Tab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoProducto_Tab4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ProductosLayout = new javax.swing.GroupLayout(jPanel_Productos);
        jPanel_Productos.setLayout(jPanel_ProductosLayout);
        jPanel_ProductosLayout.setHorizontalGroup(
            jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_ProveedorProducto_Tab4)
                            .addComponent(jLabel_PrecioCompra_Tab4)
                            .addComponent(jLabel_PrecioVenta_Tab4)
                            .addComponent(jLabel_CantidadProducto_Tab4)
                            .addComponent(jLabel_NombreProducto_Tab4)
                            .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                                .addComponent(jTextField_IDProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel_CodigoProducto_Tab4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox_ProveedorProducto_Tab4, 0, 148, Short.MAX_VALUE)
                            .addComponent(jTextField_PrecioCompra_Tab4)
                            .addComponent(jTextField_PrecioVenta_Tab4)
                            .addComponent(jTextField_CantidadProducto_Tab4)
                            .addComponent(jTextField_NombreProducto_Tab4)
                            .addComponent(jTextField_CodigoProducto_Tab4)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ProductosLayout.createSequentialGroup()
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_EliminarProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_GuardarProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_ActualizarProducto_Tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_NuevoProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel_ProductosLayout.setVerticalGroup(
            jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_CodigoProducto_Tab4)
                            .addComponent(jTextField_CodigoProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_IDProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_NombreProducto_Tab4)
                            .addComponent(jTextField_NombreProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_CantidadProducto_Tab4)
                            .addComponent(jTextField_CantidadProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_PrecioVenta_Tab4)
                            .addComponent(jTextField_PrecioVenta_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_PrecioCompra_Tab4)
                            .addComponent(jTextField_PrecioCompra_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_ProveedorProducto_Tab4)
                            .addComponent(jComboBox_ProveedorProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_GuardarProducto_Tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_ActualizarProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_ProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_EliminarProducto_Tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_NuevoProducto_Tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_ProductosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("tab4", jPanel_Productos);

        jTable_Ventas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable_Ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Vendedor", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_VentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable_Ventas);
        if (jTable_Ventas.getColumnModel().getColumnCount() > 0) {
            jTable_Ventas.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable_Ventas.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable_Ventas.getColumnModel().getColumn(2).setPreferredWidth(60);
            jTable_Ventas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/pdf.png"))); // NOI18N
        jButton1.setText("Exportar PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_VentasLayout = new javax.swing.GroupLayout(jPanel_Ventas);
        jPanel_Ventas.setLayout(jPanel_VentasLayout);
        jPanel_VentasLayout.setHorizontalGroup(
            jPanel_VentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_VentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel_VentasLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jTextField_IDVenta_Tab5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_VentasLayout.setVerticalGroup(
            jPanel_VentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_VentasLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel_VentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField_IDVenta_Tab5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab5", jPanel_Ventas);

        jLabel_NIT_Empresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_NIT_Empresa.setText("NIT:");

        jLabel_Nombre_Empresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_Nombre_Empresa.setText("Nombre:");

        jLabel_Telefono_Empresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_Telefono_Empresa.setText("Teléfono:");

        jLabel_Direccion_Empresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_Direccion_Empresa.setText("Dirección");

        jLabel_DatosEmpresa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_DatosEmpresa.setText("Datos de la Empresa");

        jTextField_NIT_Empresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_Nombre_Empresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_Telefono_Empresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTextField_Direccion_Empresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton_Guardar_DatosEmpresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_Guardar_DatosEmpresa.setText("Guardar");
        jButton_Guardar_DatosEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Guardar_DatosEmpresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_InformacionLayout = new javax.swing.GroupLayout(jPanel_Informacion);
        jPanel_Informacion.setLayout(jPanel_InformacionLayout);
        jPanel_InformacionLayout.setHorizontalGroup(
            jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_InformacionLayout.createSequentialGroup()
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_InformacionLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Id_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_InformacionLayout.createSequentialGroup()
                                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel_NIT_Empresa)
                                    .addComponent(jTextField_NIT_Empresa)
                                    .addComponent(jTextField_Telefono_Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addComponent(jLabel_Telefono_Empresa))
                                .addGap(100, 100, 100)
                                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel_Nombre_Empresa)
                                    .addComponent(jLabel_Direccion_Empresa)
                                    .addComponent(jTextField_Nombre_Empresa)
                                    .addComponent(jTextField_Direccion_Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_InformacionLayout.createSequentialGroup()
                                .addComponent(jLabel_DatosEmpresa)
                                .addGap(164, 164, 164))))
                    .addGroup(jPanel_InformacionLayout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(jButton_Guardar_DatosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(275, Short.MAX_VALUE))
        );
        jPanel_InformacionLayout.setVerticalGroup(
            jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_InformacionLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_DatosEmpresa)
                    .addComponent(jTextField_Id_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Nombre_Empresa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_NIT_Empresa, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_Nombre_Empresa, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jTextField_NIT_Empresa))
                .addGap(46, 46, 46)
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Direccion_Empresa)
                    .addComponent(jLabel_Telefono_Empresa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Telefono_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Direccion_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jButton_Guardar_DatosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );

        jTabbedPane1.addTab("tab6", jPanel_Informacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_BarraBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_Img_Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 943, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel_Img_Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel_BarraBusqueda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_NombreCliente_Tab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NombreCliente_Tab1ActionPerformed

    }//GEN-LAST:event_jTextField_NombreCliente_Tab1ActionPerformed

    private void jButton_GuardarCliente_Tab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarCliente_Tab2ActionPerformed
        if (!"".equals(jTextField_DocumentoCliente_Tab2.getText())
                && !"".equals(jTextField_NombreCliente_Tab2.getText())
                && !"".equals(jTextField_TelefonoCliente_Tab2.getText())) {

            cliente.setDocumentoCliente(jTextField_DocumentoCliente_Tab2.getText());
            cliente.setNombreCliente(jTextField_NombreCliente_Tab2.getText());
            cliente.setNumTelCliente(jTextField_TelefonoCliente_Tab2.getText());
            cliente.setCorreoCliente(jTextField_CorreoCliente_Tab2.getText());
            cliente.setDireccionCliente(jTextField_DireccionCliente_Tab2.getText());

            limpiarTabla();
            limpiarTextFieldCliente();
            listarClientesForm();

            controladorCliente.registrarClientes(cliente);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");

        } else {
            JOptionPane.showMessageDialog(null, "Los campos obligatorios están vacios", "Error", 0);
        }
    }//GEN-LAST:event_jButton_GuardarCliente_Tab2ActionPerformed

    private void jButton_ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ClientesActionPerformed
        limpiarTabla();
        listarClientesForm();

        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButton_ClientesActionPerformed

    private void jTable_ClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_ClientesMouseClicked
        int fila = jTable_Clientes.rowAtPoint(evt.getPoint());

        jTextField_IDCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 0).toString());
        jTextField_DocumentoCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 1).toString());
        jTextField_NombreCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 2).toString());
        jTextField_TelefonoCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 3).toString());
        jTextField_CorreoCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 4).toString());
        jTextField_DireccionCliente_Tab2.setText(jTable_Clientes.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_jTable_ClientesMouseClicked

    private void jButton_EliminarCliente_Tab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarCliente_Tab2ActionPerformed
        if (!"".equals(jTextField_IDCliente_Tab2.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar?");

            if (pregunta == 0) {
                int id = Integer.parseInt(jTextField_IDCliente_Tab2.getText());
                controladorCliente.eliminarCliente(id);

                limpiarTabla();
                limpiarTextFieldCliente();
                listarClientesForm();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_jButton_EliminarCliente_Tab2ActionPerformed

    private void jButton_NuevoCliente_Tab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoCliente_Tab2ActionPerformed
        limpiarTextFieldCliente();
    }//GEN-LAST:event_jButton_NuevoCliente_Tab2ActionPerformed

    private void jButton_ActualizarCliente_Tab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarCliente_Tab2ActionPerformed
        if ("".equals(jTextField_IDCliente_Tab2.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

            if (!"".equals(jTextField_DocumentoCliente_Tab2.getText())
                    && !"".equals(jTextField_NombreCliente_Tab2.getText())
                    && !"".equals(jTextField_TelefonoCliente_Tab2.getText())
                    && !"".equals(jTextField_CorreoCliente_Tab2.getText())
                    && !"".equals(jTextField_DireccionCliente_Tab2.getText())) {

                cliente.setDocumentoCliente(jTextField_DocumentoCliente_Tab2.getText());
                cliente.setNombreCliente(jTextField_NombreCliente_Tab2.getText());
                cliente.setNumTelCliente(jTextField_TelefonoCliente_Tab2.getText());
                cliente.setCorreoCliente(jTextField_CorreoCliente_Tab2.getText());
                cliente.setDireccionCliente(jTextField_DireccionCliente_Tab2.getText());
                cliente.setId(Integer.parseInt(jTextField_IDCliente_Tab2.getText()));

                controladorCliente.actualizarCliente(cliente);
                JOptionPane.showMessageDialog(null, "Cliente actualizado");

                limpiarTabla();
                limpiarTextFieldCliente();
                listarClientesForm();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_jButton_ActualizarCliente_Tab2ActionPerformed

    private void jButton_GuardarProveedor_Tab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarProveedor_Tab3ActionPerformed
        if (!"".equals(jTextField_DocumentoProveedor_Tab3.getText())
                && !"".equals(jTextField_NombreProveedor_Tab3.getText())
                && !"".equals(jTextField_DireccionProveedor_Tab3.getText())
                && !"".equals(jTextField_TelefonoProveedor_Tab3.getText())
                && !"".equals(jTextField_CodigoProducto_Tab3.getText())) {

            proveedor.setDocumentoProveedor(jTextField_DocumentoProveedor_Tab3.getText());
            proveedor.setNombreProveedor(jTextField_NombreProveedor_Tab3.getText());
            proveedor.setDireccionProveedor(jTextField_DireccionProveedor_Tab3.getText());
            proveedor.setNumTelProveedor(jTextField_TelefonoProveedor_Tab3.getText());
            proveedor.setCodigoProductoProveedor(jTextField_CodigoProducto_Tab3.getText());

            limpiarTabla();
            limpiarTextFieldProveedor();
            listarClientesForm();

            controladorProveedor.registrarProveedor(proveedor);

            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacios", "Error", 0);
        }
    }//GEN-LAST:event_jButton_GuardarProveedor_Tab3ActionPerformed

    private void jButton_ProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ProveedorActionPerformed
        limpiarTabla();
        listarProveedoresForm();

        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton_ProveedorActionPerformed

    private void jTable_ProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_ProveedoresMouseClicked
        int fila = jTable_Proveedores.rowAtPoint(evt.getPoint());

        jTextField_IDProveedor_Tab3.setText(jTable_Proveedores.getValueAt(fila, 0).toString());
        jTextField_DocumentoProveedor_Tab3.setText(jTable_Proveedores.getValueAt(fila, 1).toString());
        jTextField_NombreProveedor_Tab3.setText(jTable_Proveedores.getValueAt(fila, 2).toString());
        jTextField_DireccionProveedor_Tab3.setText(jTable_Proveedores.getValueAt(fila, 3).toString());
        jTextField_TelefonoProveedor_Tab3.setText(jTable_Proveedores.getValueAt(fila, 4).toString());
        jTextField_CodigoProducto_Tab3.setText(jTable_Proveedores.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_jTable_ProveedoresMouseClicked

    private void jButton_EliminarProveedor_Tab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarProveedor_Tab3ActionPerformed
        if (!"".equals(jTextField_IDProveedor_Tab3.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar?");

            if (pregunta == 0) {
                int id = Integer.parseInt(jTextField_IDProveedor_Tab3.getText());
                controladorProveedor.eliminarProveedor(id);

                limpiarTabla();
                limpiarTextFieldProveedor();
                listarProveedoresForm();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_jButton_EliminarProveedor_Tab3ActionPerformed

    private void jButton_ActualizarProveedor_Tab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarProveedor_Tab3ActionPerformed
        if ("".equals(jTextField_IDProveedor_Tab3.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

            if (!"".equals(jTextField_DocumentoProveedor_Tab3.getText())
                    && !"".equals(jTextField_NombreProveedor_Tab3.getText())
                    && !"".equals(jTextField_DireccionProveedor_Tab3.getText())
                    && !"".equals(jTextField_TelefonoProveedor_Tab3.getText())
                    && !"".equals(jTextField_CodigoProducto_Tab3.getText())) {

                proveedor.setDocumentoProveedor(jTextField_DocumentoProveedor_Tab3.getText());
                proveedor.setNombreProveedor(jTextField_NombreProveedor_Tab3.getText());
                proveedor.setDireccionProveedor(jTextField_DireccionProveedor_Tab3.getText());
                proveedor.setNumTelProveedor(jTextField_TelefonoProveedor_Tab3.getText());
                proveedor.setCodigoProductoProveedor(jTextField_CodigoProducto_Tab3.getText());
                proveedor.setId(Integer.parseInt(jTextField_IDProveedor_Tab3.getText()));

                controladorProveedor.actualizarProveedor(proveedor);

                JOptionPane.showMessageDialog(null, "Proveedor actualizado");

                limpiarTabla();
                limpiarTextFieldProveedor();
                listarProveedoresForm();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_jButton_ActualizarProveedor_Tab3ActionPerformed

    private void jButton_NuevoProveedor_Tab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoProveedor_Tab3ActionPerformed
        limpiarTextFieldProveedor();
    }//GEN-LAST:event_jButton_NuevoProveedor_Tab3ActionPerformed

    private void jButton_GuardarProducto_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarProducto_Tab4ActionPerformed
        if (!"".equals(jTextField_CodigoProducto_Tab4.getText())
                && !"".equals(jTextField_NombreProducto_Tab4.getText())
                && !"".equals(jTextField_CantidadProducto_Tab4.getText())
                && !"".equals(jTextField_PrecioVenta_Tab4.getText())
                && !"".equals(jTextField_PrecioCompra_Tab4.getText())
                && !"".equals(jComboBox_ProveedorProducto_Tab4.getSelectedIndex())) {

            producto.setCodigo(jTextField_CodigoProducto_Tab4.getText());
            producto.setNombreProducto(jTextField_NombreProducto_Tab4.getText());
            producto.setCantidad(Integer.parseInt(jTextField_CantidadProducto_Tab4.getText()));
            producto.setPrecioVenta(Float.parseFloat(jTextField_PrecioVenta_Tab4.getText()));
            producto.setPrecioCompra(Float.parseFloat(jTextField_PrecioCompra_Tab4.getText()));
            producto.setProveedor(jComboBox_ProveedorProducto_Tab4.getSelectedItem().toString());

            limpiarTabla();
            limpiarTextFieldProducto();
            listarClientesForm();

            controladorProducto.registrarProducto(producto);

            JOptionPane.showMessageDialog(null, "Producto Guardado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacios", "Error", 0);
        }
    }//GEN-LAST:event_jButton_GuardarProducto_Tab4ActionPerformed

    private void jButton_ProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ProductosActionPerformed
        limpiarTabla();
        listarProductosForm();

        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton_ProductosActionPerformed

    private void jTable_ProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_ProductosMouseClicked
        int fila = jTable_Productos.rowAtPoint(evt.getPoint());

        jTextField_IDProducto_Tab4.setText(jTable_Productos.getValueAt(fila, 0).toString());
        jTextField_CodigoProducto_Tab4.setText(jTable_Productos.getValueAt(fila, 1).toString());
        jTextField_NombreProducto_Tab4.setText(jTable_Productos.getValueAt(fila, 2).toString());
        jTextField_CantidadProducto_Tab4.setText(jTable_Productos.getValueAt(fila, 3).toString());
        jComboBox_ProveedorProducto_Tab4.setSelectedItem(jTable_Productos.getValueAt(fila, 4).toString());
        jTextField_PrecioVenta_Tab4.setText(jTable_Productos.getValueAt(fila, 5).toString());
        jTextField_PrecioCompra_Tab4.setText(jTable_Productos.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_jTable_ProductosMouseClicked

    private void jButton_EliminarProducto_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarProducto_Tab4ActionPerformed
        if (!"".equals(jTextField_IDProducto_Tab4.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar?");

            if (pregunta == 0) {
                int id = Integer.parseInt(jTextField_IDProducto_Tab4.getText());
                controladorProducto.eliminarProducto(id);

                limpiarTabla();
                limpiarTextFieldProducto();
                listarProductosForm();

            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_jButton_EliminarProducto_Tab4ActionPerformed

    private void jButton_ActualizarProducto_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarProducto_Tab4ActionPerformed
        if ("".equals(jTextField_IDProducto_Tab4.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

            if (!"".equals(jTextField_CodigoProducto_Tab4.getText())
                    && !"".equals(jTextField_NombreProducto_Tab4.getText())
                    && !"".equals(jTextField_CantidadProducto_Tab4.getText())
                    && !"".equals(jTextField_PrecioVenta_Tab4.getText())
                    && !"".equals(jTextField_PrecioCompra_Tab4.getText())
                    && !"".equals(jComboBox_ProveedorProducto_Tab4.getSelectedIndex())) {

                producto.setCodigo(jTextField_CodigoProducto_Tab4.getText());
                producto.setNombreProducto(jTextField_NombreProducto_Tab4.getText());
                producto.setCantidad(Integer.parseInt(jTextField_CantidadProducto_Tab4.getText()));
                producto.setPrecioVenta(Float.parseFloat(jTextField_PrecioVenta_Tab4.getText()));
                producto.setPrecioCompra(Float.parseFloat(jTextField_PrecioCompra_Tab4.getText()));
                producto.setProveedor(jComboBox_ProveedorProducto_Tab4.getSelectedItem().toString());
                producto.setId(Integer.parseInt(jTextField_IDProducto_Tab4.getText()));

                controladorProducto.actualizarProducto(producto);

                JOptionPane.showMessageDialog(null, "Producto actualizado");

                limpiarTabla();
                limpiarTextFieldProducto();
                listarProductosForm();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_jButton_ActualizarProducto_Tab4ActionPerformed

    private void jButton_NuevoProducto_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoProducto_Tab4ActionPerformed
        limpiarTextFieldProducto();
    }//GEN-LAST:event_jButton_NuevoProducto_Tab4ActionPerformed

    private void jTextField_CodigoProducto_Tab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CodigoProducto_Tab1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jTextField_CodigoProducto_Tab1.getText())) {
                String codigo = jTextField_CodigoProducto_Tab1.getText();
                producto = controladorProducto.buscarProducto(codigo);

                if (producto.getNombreProducto() != null) {
                    jTextField_NombreProducto_Tab1.setText("" + producto.getNombreProducto());
                    jTextField_CantidadCompra_Tab1.requestFocus();
                    jTextField_PrecioVenta_Tab1.setText("" + producto.getPrecioVenta());
                    jTextField_Stock_Tab1.setText("" + producto.getCantidad());
                } else {
                    limpiarTextFieldVenta();
                    jTextField_CodigoProducto_Tab1.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                jTextField_CodigoProducto_Tab1.requestFocus();
            }
        }
    }//GEN-LAST:event_jTextField_CodigoProducto_Tab1KeyPressed

    private void jTextField_CantidadCompra_Tab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CantidadCompra_Tab1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jTextField_CantidadCompra_Tab1.getText())) {
                String codigo = jTextField_CodigoProducto_Tab1.getText();
                String nombre = jTextField_NombreProducto_Tab1.getText();
                int cantidad = Integer.parseInt(jTextField_CantidadCompra_Tab1.getText());
                float precioVenta = Float.parseFloat(jTextField_PrecioVenta_Tab1.getText());
                float total = cantidad * precioVenta;

                int stock = Integer.parseInt(jTextField_Stock_Tab1.getText());
                if (stock >= cantidad) {
                    item = item + 1;
                    temporal = (DefaultTableModel) jTable_Venta_Tab1.getModel();

                    /**
                     * Validacion de productos
                     */
                    for (int i = 0; i < jTable_Venta_Tab1.getRowCount(); i++) {
                        if (jTable_Venta_Tab1.getValueAt(i, 1).equals(jTextField_NombreProducto_Tab1.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya está registrado");
                            return;
                        }
                    }

                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(codigo);
                    lista.add(nombre);
                    lista.add(cantidad);
                    lista.add(precioVenta);
                    lista.add(total);

                    Object[] object = new Object[5];
                    object[0] = lista.get(1);
                    object[1] = lista.get(2);
                    object[2] = lista.get(3);
                    object[3] = lista.get(4);
                    object[4] = lista.get(5);

                    temporal.addRow(object);

                    jTable_Venta_Tab1.setModel(temporal);

                    totalPagar();
                    limpiarTextFieldVenta();

                    jTextField_CodigoProducto_Tab1.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible", "Error", 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese la cantidad");
            }
        }
    }//GEN-LAST:event_jTextField_CantidadCompra_Tab1KeyPressed

    private void jButton_Eliminar_Tab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Eliminar_Tab1ActionPerformed
        modelo = (DefaultTableModel) jTable_Venta_Tab1.getModel();

        modelo.removeRow(jTable_Venta_Tab1.getSelectedRow());
        totalPagar();

        jTextField_CodigoProducto_Tab1.requestFocus();
    }//GEN-LAST:event_jButton_Eliminar_Tab1ActionPerformed

    private void jTextField_DocumentoCliente_Tab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_DocumentoCliente_Tab1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(jTextField_DocumentoCliente_Tab1.getText())) {
                String documento = jTextField_DocumentoCliente_Tab1.getText();
                cliente = controladorCliente.buscarClientePorDocumento(documento);

                if (cliente.getNombreCliente() != null) {
                    jTextField_NombreCliente_Tab1.setText("" + cliente.getNombreCliente());
                    jTextField_DireccionCliente_Tab1.setText("" + cliente.getDireccionCliente());
                    jTextField_TelefonoCliente_Tab1.setText("" + cliente.getNumTelCliente());
                    jTextField_CorreoCliente_Tab1.setText("" + cliente.getCorreoCliente());
                } else {
                    jTextField_DocumentoCliente_Tab1.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_jTextField_DocumentoCliente_Tab1KeyPressed

    private void jButton_GenerarVenta_Tab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GenerarVenta_Tab1ActionPerformed
        if (jTable_Venta_Tab1.getRowCount() > 0) {
            if (!"".equals(jTextField_NombreCliente_Tab1.getText())) {
                registrarVenta();
                registrarDetalle();
                actualizarStock();
                controladorVenta.generarFactura(new FacturaPDF());
                limpiarTablaVenta();
                limpiarTextFieldVentaCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta");
        }


    }//GEN-LAST:event_jButton_GenerarVenta_Tab1ActionPerformed

    private void jButton_NuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton_NuevaVentaActionPerformed

    private void jComboBox_ProveedorProducto_Tab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ProveedorProducto_Tab4ActionPerformed

    }//GEN-LAST:event_jComboBox_ProveedorProducto_Tab4ActionPerformed

    private void jTextField_CodigoProducto_Tab1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CodigoProducto_Tab1KeyTyped
        controladorEventos.numberKeyPress(evt);
    }//GEN-LAST:event_jTextField_CodigoProducto_Tab1KeyTyped

    private void jTextField_NombreProducto_Tab1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NombreProducto_Tab1KeyTyped
        controladorEventos.textKeyPress(evt);
    }//GEN-LAST:event_jTextField_NombreProducto_Tab1KeyTyped

    private void jTextField_CantidadCompra_Tab1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CantidadCompra_Tab1KeyTyped
        controladorEventos.numberKeyPress(evt);
    }//GEN-LAST:event_jTextField_CantidadCompra_Tab1KeyTyped

    private void jTextField_DocumentoCliente_Tab1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_DocumentoCliente_Tab1KeyTyped
        controladorEventos.numberKeyPress(evt);
    }//GEN-LAST:event_jTextField_DocumentoCliente_Tab1KeyTyped

    private void jButton_Guardar_DatosEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Guardar_DatosEmpresaActionPerformed

            if (!"".equals(jTextField_NIT_Empresa.getText())
                    && !"".equals(jTextField_Nombre_Empresa.getText())
                    && !"".equals(jTextField_Telefono_Empresa.getText())
                    && !"".equals(jTextField_Direccion_Empresa.getText())
                    ) {

                datosEmpresa.setNit(jTextField_NIT_Empresa.getText());
                datosEmpresa.setNombre(jTextField_Nombre_Empresa.getText());
                datosEmpresa.setDireccion(jTextField_Direccion_Empresa.getText());
                datosEmpresa.setTelefono(jTextField_Telefono_Empresa.getText());

                controladorProducto.actualizarDatos(datosEmpresa);

                JOptionPane.showMessageDialog(null, "Datos actualizados");

                listarDatos();
                limpiarTextFieldDatosEmpresa();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }

    }//GEN-LAST:event_jButton_Guardar_DatosEmpresaActionPerformed

    private void jButton_InformaciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InformaciónActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_jButton_InformaciónActionPerformed

    private void jButton_VentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VentasActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        limpiarTabla();
        listarVentasForm();
    }//GEN-LAST:event_jButton_VentasActionPerformed

    private void jTable_VentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_VentasMouseClicked
        int fila = jTable_Ventas.rowAtPoint(evt.getPoint());
        jTextField_IDVenta_Tab5.setText(jTable_Ventas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_jTable_VentasMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            int id = Integer.parseInt(jTextField_IDVenta_Tab5.getText());
            File file = new File("pdf/venta" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SistemaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SistemaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemaForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_ActualizarCliente_Tab2;
    private javax.swing.JButton jButton_ActualizarProducto_Tab4;
    private javax.swing.JButton jButton_ActualizarProveedor_Tab3;
    private javax.swing.JButton jButton_Clientes;
    private javax.swing.JButton jButton_EliminarCliente_Tab2;
    private javax.swing.JButton jButton_EliminarProducto_Tab4;
    private javax.swing.JButton jButton_EliminarProveedor_Tab3;
    private javax.swing.JButton jButton_Eliminar_Tab1;
    private javax.swing.JButton jButton_GenerarVenta_Tab1;
    private javax.swing.JButton jButton_GuardarCliente_Tab2;
    private javax.swing.JButton jButton_GuardarProducto_Tab4;
    private javax.swing.JButton jButton_GuardarProveedor_Tab3;
    private javax.swing.JButton jButton_Guardar_DatosEmpresa;
    private javax.swing.JButton jButton_Información;
    private javax.swing.JButton jButton_NuevaVenta;
    private javax.swing.JButton jButton_NuevoCliente_Tab2;
    private javax.swing.JButton jButton_NuevoProducto_Tab4;
    private javax.swing.JButton jButton_NuevoProveedor_Tab3;
    private javax.swing.JButton jButton_Productos;
    private javax.swing.JButton jButton_Proveedor;
    private javax.swing.JButton jButton_Ventas;
    private javax.swing.JComboBox<String> jComboBox_ProveedorProducto_Tab4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2_LogoImg;
    private javax.swing.JLabel jLabel_CantidadCompra_Tab1;
    private javax.swing.JLabel jLabel_CantidadProducto_Tab4;
    private javax.swing.JLabel jLabel_CodigoProducto_Tab1;
    private javax.swing.JLabel jLabel_CodigoProducto_Tab3;
    private javax.swing.JLabel jLabel_CodigoProducto_Tab4;
    private javax.swing.JLabel jLabel_CorreoCliente_Tab2;
    private javax.swing.JLabel jLabel_DatosEmpresa;
    private javax.swing.JLabel jLabel_DireccionCliente_Tab2;
    private javax.swing.JLabel jLabel_DireccionProveedor_Tab3;
    private javax.swing.JLabel jLabel_Direccion_Empresa;
    private javax.swing.JLabel jLabel_DocumentoCliente_Tab1;
    private javax.swing.JLabel jLabel_DocumentoCliente_Tab2;
    private javax.swing.JLabel jLabel_DocumentoProveedor_Tab3;
    private javax.swing.JLabel jLabel_Img_Encabezado;
    private javax.swing.JLabel jLabel_NIT_Empresa;
    private javax.swing.JLabel jLabel_NombreCliente_Tab1;
    private javax.swing.JLabel jLabel_NombreCliente_Tab2;
    private javax.swing.JLabel jLabel_NombreProducto_Tab1;
    private javax.swing.JLabel jLabel_NombreProducto_Tab4;
    private javax.swing.JLabel jLabel_NombreProveedor_Tab3;
    private javax.swing.JLabel jLabel_NombreVendedor;
    private javax.swing.JLabel jLabel_Nombre_Empresa;
    private javax.swing.JLabel jLabel_PrecioCompra_Tab1;
    private javax.swing.JLabel jLabel_PrecioCompra_Tab4;
    private javax.swing.JLabel jLabel_PrecioVenta_Tab4;
    private javax.swing.JLabel jLabel_ProveedorProducto_Tab4;
    private javax.swing.JLabel jLabel_Stock_Tab1;
    private javax.swing.JLabel jLabel_TelefonoCliente_Tab2;
    private javax.swing.JLabel jLabel_TelefonoProveedor_Tab3;
    private javax.swing.JLabel jLabel_Telefono_Empresa;
    private javax.swing.JLabel jLabel_TotalPagar_Tab1;
    private javax.swing.JLabel jLabel_ValorPagar_Tab1;
    private javax.swing.JPanel jPanel_BarraBusqueda;
    private javax.swing.JPanel jPanel_Clientes;
    private javax.swing.JPanel jPanel_Informacion;
    private javax.swing.JPanel jPanel_NuevaVenta;
    private javax.swing.JPanel jPanel_Productos;
    private javax.swing.JPanel jPanel_Proveedor;
    private javax.swing.JPanel jPanel_Ventas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_Clientes;
    private javax.swing.JTable jTable_Productos;
    private javax.swing.JTable jTable_Proveedores;
    private javax.swing.JTable jTable_Venta_Tab1;
    private javax.swing.JTable jTable_Ventas;
    private javax.swing.JTextField jTextField_CantidadCompra_Tab1;
    private javax.swing.JTextField jTextField_CantidadProducto_Tab4;
    private javax.swing.JTextField jTextField_CodigoProducto_Tab1;
    private javax.swing.JTextField jTextField_CodigoProducto_Tab3;
    private javax.swing.JTextField jTextField_CodigoProducto_Tab4;
    private javax.swing.JTextField jTextField_CorreoCliente_Tab1;
    private javax.swing.JTextField jTextField_CorreoCliente_Tab2;
    private javax.swing.JTextField jTextField_DireccionCliente_Tab1;
    private javax.swing.JTextField jTextField_DireccionCliente_Tab2;
    private javax.swing.JTextField jTextField_DireccionProveedor_Tab3;
    private javax.swing.JTextField jTextField_Direccion_Empresa;
    private javax.swing.JTextField jTextField_DocumentoCliente_Tab1;
    private javax.swing.JTextField jTextField_DocumentoCliente_Tab2;
    private javax.swing.JTextField jTextField_DocumentoProveedor_Tab3;
    private javax.swing.JTextField jTextField_IDCliente_Tab2;
    private javax.swing.JTextField jTextField_IDProducto_Tab1;
    private javax.swing.JTextField jTextField_IDProducto_Tab4;
    private javax.swing.JTextField jTextField_IDProveedor_Tab3;
    private javax.swing.JTextField jTextField_IDVenta_Tab5;
    private javax.swing.JTextField jTextField_Id_Empresa;
    private javax.swing.JTextField jTextField_NIT_Empresa;
    private javax.swing.JTextField jTextField_NombreCliente_Tab1;
    private javax.swing.JTextField jTextField_NombreCliente_Tab2;
    private javax.swing.JTextField jTextField_NombreProducto_Tab1;
    private javax.swing.JTextField jTextField_NombreProducto_Tab4;
    private javax.swing.JTextField jTextField_NombreProveedor_Tab3;
    private javax.swing.JTextField jTextField_Nombre_Empresa;
    private javax.swing.JTextField jTextField_PrecioCompra_Tab4;
    private javax.swing.JTextField jTextField_PrecioVenta_Tab1;
    private javax.swing.JTextField jTextField_PrecioVenta_Tab4;
    private javax.swing.JTextField jTextField_Stock_Tab1;
    private javax.swing.JTextField jTextField_TelefonoCliente_Tab1;
    private javax.swing.JTextField jTextField_TelefonoCliente_Tab2;
    private javax.swing.JTextField jTextField_TelefonoProveedor_Tab3;
    private javax.swing.JTextField jTextField_Telefono_Empresa;
    // End of variables declaration//GEN-END:variables
}
