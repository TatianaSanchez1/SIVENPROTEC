package dominio.controlador;

import dominio.accesoDatos.VentaDAO;
import dominio.modelo.DetalleVenta;
import dominio.modelo.Venta;
import vista.StrategyFactura;

import java.util.List;

public class ControladorVenta {
	VentaDAO ventaDAO = new VentaDAO();


	public int registrarVenta(Venta venta) {
		return ventaDAO.registrarVenta(venta);
	}

	public int registrarDetalle(DetalleVenta detalle) {
		return ventaDAO.registrarDetalle(detalle);
	}

	public int idVenta() {
		return ventaDAO.idVenta();
	}

	public boolean actualizarStock(int cantidad, String codigo) {
		return ventaDAO.actualizarStock(cantidad, codigo);
	}

	public List<Venta> listarVentas() {
		return ventaDAO.listarVentas();
	}

	public List<DetalleVenta> listarDetalleVentas(int idVenta) {
		return ventaDAO.listarDetalleVentas(idVenta);
	}

	public boolean alerta(String codigoProducto) {
		return ventaDAO.alerta(codigoProducto);
	}

	public Venta buscarVenta(int idVenta) {
		return ventaDAO.buscarVenta(idVenta);
	}

	public void generarFactura(StrategyFactura strategyFactura) {
		strategyFactura.generarFactura();
	}
}
