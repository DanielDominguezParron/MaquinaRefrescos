package accesoDatos;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		Deposito deposito;
		try {
			Statement statement = conn1.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM depositos");
			while (rs.next()) {
				String nombre = rs.getString(2);
				int id = rs.getInt(3);
				int cantidad = rs.getInt(4);
				deposito = new Deposito(nombre, id, cantidad);
				depositosCreados.put(id, deposito);
				System.out.println(deposito.toString());
			}
		} catch (Exception e) {
			System.out.println("Error Conexion");
			e.printStackTrace();
		}
		return depositosCreados;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		Dispensador dispensador;
		try {
			Statement statement = conn1.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM dispensadores");
			while (rs.next()) {
				String clave = rs.getString(2);
				String nombre = rs.getString(3);
				int valor = rs.getInt(4);
				int cantidad = rs.getInt(5);
				dispensador = new Dispensador(clave, nombre, valor, cantidad);
				dispensadoresCreados.put(clave, dispensador);
				System.out.println(dispensador.toString());
			}
		} catch (Exception e) {
			System.out.println("Error Conexion");
			e.printStackTrace();
		}
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		try {
			PreparedStatement pstmt = conn1.prepareStatement("TRUNCATE depositos");
			pstmt.executeUpdate();
			pstmt.close();
			Iterator it = depositos.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Deposito a = (Deposito) e.getValue();
				String nombre = a.getNombreMoneda();
				int valor = a.getValor();
				int cantidad = a.getCantidad();
				Statement statement = conn1.createStatement();
				String query = "INSERT INTO depositos (nombre, valor, cantidad)  values ('" + nombre + "','" + valor
						+ "','" + cantidad + "')";
				statement.executeUpdate(query);
				it.remove();
			}
			todoOK = true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = false;
		try {
			PreparedStatement pstmt = conn1.prepareStatement("TRUNCATE dispensadores");
			pstmt.executeUpdate();
			pstmt.close();
			Iterator it = dispensadores.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Dispensador a = (Dispensador) e.getValue();
				String nombre = a.getNombreProducto();
				String clave = a.getClave();
				int precio = a.getPrecio();
				int cantidad = a.getCantidad();
				Statement statement = conn1.createStatement();
				String query = "INSERT INTO dispensadores (clave, nombre, precio, cantidad)  values ('" + clave + "','"
						+ nombre + "','" + precio + "','" + cantidad + "')";
				statement.executeUpdate(query);
				it.remove();
			}
			todoOK = true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return todoOK;
	}

} // Fin de la clase