package dominio.accesoDatos;

import dominio.modelo.DetalleVenta;
import dominio.modelo.Venta;
import infraestructura.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

	int resultado;
	private Connection connection;
	private PreparedStatement preparedStatement;

	ConexionBD conexion = ConexionBD.getInstance();


	public int registrarVenta(Venta venta) {
		String sql = "INSERT INTO ventas(cliente, vendedor, total) VALUES(?,?,?)";

		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, venta.getCliente());
			preparedStatement.setString(2, venta.getVendedor());
			preparedStatement.setFloat(3, venta.getTotal());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public int registrarDetalle(DetalleVenta detalle) {
		String sql = "INSERT INTO detalle(codigoproducto, cantidad, precio, idventa) VALUES (?,?,?,?)";

		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, detalle.getCodigoProducto());
			preparedStatement.setInt(2, detalle.getCantidad());
			preparedStatement.setFloat(3, detalle.getPrecio());
			preparedStatement.setInt(4, detalle.getId());

			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;

	}

	public int idVenta() {
		String sql = "SELECT MAX(id) FROM ventas";
		int id = 0;
		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean actualizarStock(int cantidad, String codigo) {
		String sql = "UPDATE productos SET cantidad = ? WHERE codigoproducto = ?";

		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, cantidad);
			preparedStatement.setString(2, codigo);

			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Venta> listarVentas() {
		List<Venta> listaVentas = new ArrayList<>();
		String sql = "SELECT * FROM ventas";

		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				listaVentas.add(new Venta(
						resultSet.getInt("id"),
						resultSet.getString("cliente"),
						resultSet.getString("vendedor"),
						resultSet.getFloat("total")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaVentas;
	}

	public List<DetalleVenta> listarDetalleVentas(int idVenta) {
		List<DetalleVenta> listaDetalleVentas = new ArrayList<>();
		String sql = "SELECT * FROM detalle  WHERE idVenta =" + idVenta;

		try {
			connection = conexion.getConexion();
			ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
			while (resultSet.next()) {
				listaDetalleVentas.add(new DetalleVenta(
					resultSet.getInt("id"),
					resultSet.getString("codigoProducto"),
					resultSet.getInt("cantidad"),
					resultSet.getFloat("precio"),
					resultSet.getInt("idVenta")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDetalleVentas;
	}

	public boolean alerta(String codigoProducto) {
		String sql = "SELECT * FROM productos WHERE codigoproducto = ?";
		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, codigoProducto);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getInt("cantidad") < 5) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Venta buscarVenta(int idVenta) {
		Venta venta = null;
		String sql = "SELECT * FROM ventas WHERE id = ?";
		try {
			connection = conexion.getConexion();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, idVenta);
			var resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				venta = new Venta(
					resultSet.getInt("id"),
					resultSet.getString("cliente"),
					resultSet.getString("vendedor"),
					resultSet.getFloat("total")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return venta;
	}
}
