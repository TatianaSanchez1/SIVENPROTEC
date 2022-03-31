package dominio.modelo;

public class Cliente {

    private int id;
    private String documentoCliente;
    private String nombreCliente;
    private String numTelCliente;
    private String correoCliente;
    private String direccionCliente;

    public Cliente() {
    }

    public Cliente(int id, String documentoCliente, String nombreCliente,
            String numTelCliente, String correoCliente, String direccionCliente) {
        this.id = id;
        this.documentoCliente = documentoCliente;
        this.nombreCliente = nombreCliente;
        this.numTelCliente = numTelCliente;
        this.correoCliente = correoCliente;
        this.direccionCliente = direccionCliente;
    }

    public int getId() {
        return id;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNumTelCliente() {
        return numTelCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setNumTelCliente(String numTelCliente) {
        this.numTelCliente = numTelCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
}
