package dominio.modelo;

public class Proveedor {
    private int id;
    private String documentoProveedor;
    private String nombreProveedor;
    private String direccionProveedor;
    private String numTelProveedor;
    private String codigoProductoProveedor;


    public Proveedor() {
    }

    public Proveedor(int id, String documentoProveedor, String nombreProveedor, String direccionProveedor, String numTelProveedor, String codigoProductoProveedor) {
        this.id = id;
        this.documentoProveedor = documentoProveedor;
        this.nombreProveedor = nombreProveedor;
        this.direccionProveedor = direccionProveedor;
        this.numTelProveedor = numTelProveedor;
        this.codigoProductoProveedor = codigoProductoProveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentoProveedor() {
        return documentoProveedor;
    }

    public void setDocumentoProveedor(String documentoProveedor) {
        this.documentoProveedor = documentoProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getNumTelProveedor() {
        return numTelProveedor;
    }

    public void setNumTelProveedor(String numTelProveedor) {
        this.numTelProveedor = numTelProveedor;
    }

    public String getCodigoProductoProveedor() {
        return codigoProductoProveedor;
    }

    public void setCodigoProductoProveedor(String codigoProductoProveedor) {
        this.codigoProductoProveedor = codigoProductoProveedor;
    }
}
