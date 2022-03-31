package infraestructura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	private static ConexionBD instance;
	private static Connection connection;

	/**
	 * Se establece la conexión entre la base de datos
	 */
	private ConexionBD() {
		Connection result = null;
		try {
			result = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sivenprotec?serverTimezone=UTC",
					"root",
					""
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = result;
	}

	public static ConexionBD getInstance() {
		if (instance == null) {
			return new ConexionBD();
		} else {
			return instance;
		}
	}

	public Connection getConexion() {
		return connection;
	}
}
