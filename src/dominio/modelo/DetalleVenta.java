package dominio.modelo;

public class DetalleVenta {
    private int id;
    private String codigoProducto;
    private int cantidad;
    private float precio;
    private int idVenta;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, String codigoProducto, int cantidad, float precio, int idVenta) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idVenta = idVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoProducto() { return codigoProducto; }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdVenta() {
        return idVenta;
    }

}
