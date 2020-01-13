package accesoDatos;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class PHPDB implements I_Acceso_Datos {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET, upd;

	public PHPDB() {

		encargadoPeticiones = new ApiRequests();

		// Cambiar esta url para que funcione la aplicacion

		SERVER_PATH = "http://localhost/ADAT_MaquinaRefrescos_Alumnos/servidor/";

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		GET = "leeDepositos.php";
		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para coches");

			String url = SERVER_PATH + GET;

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray depositos = (JSONArray) respuesta.get("coches");
			for (Object object : depositos) {
				JSONObject aux = (JSONObject) object;

				int cantidad = Integer.parseInt(aux.get("cantidad").toString());
				int id = Integer.parseInt(aux.get("valor").toString());
				String nombre = aux.get("nombre").toString();
				Deposito deposito = new Deposito(nombre, id, cantidad);
				depositosCreados.put(id, deposito);
				System.out.println(deposito.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		GET = "leeDispensadores.php";
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para coches");

			String url = SERVER_PATH + GET;

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response);
			JSONArray depositos = (JSONArray) respuesta.get("coches");
			for (Object object : depositos) {
				JSONObject aux = (JSONObject) object;

				int cantidad = Integer.parseInt(aux.get("cantidad").toString());
				int valor = Integer.parseInt(aux.get("valor").toString());
				String nombre = aux.get("nombre").toString();
				String clave = aux.get("clave").toString();
				Dispensador dispensador = new Dispensador(clave, nombre, valor, cantidad);
				dispensadoresCreados.put(clave, dispensador);
				System.out.println(dispensador.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		upd = "actualizarDepositos.php";
		JSONObject objCoche = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		try {
			Iterator it = depositos.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Deposito a = (Deposito) e.getValue();
				int valor = a.getValor();
				int cantidad = a.getCantidad();
				objCoche.put("valor", valor);
				objCoche.put("cantidad", cantidad);
				objPeticion.put("depositosUpd", objCoche);
				String json = objPeticion.toJSONString();

				System.out.println("Lanzamos peticion JSON para almacenar un jugador");

				String url = SERVER_PATH + upd;

				System.out.println("La url a la que lanzamos la peticiÃ›n es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				// System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);

				System.out.println("El json que recibimos es: ");
				System.out.println(response);

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
				System.out.println(respuesta);
				it.remove();
			}
			todoOK = true;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;
		upd = "actualizarDispensadores.php";
		JSONObject objCoche = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		try {
			Iterator it = dispensadores.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Dispensador a = (Dispensador) e.getValue();
				String clave = a.getClave();
				int cantidad = a.getCantidad();
				it.remove();
				objCoche.put("clave", clave);
				objCoche.put("cantidad", cantidad);

				objPeticion.put("dispensadoresUpd", objCoche);
				String json = objPeticion.toJSONString();

				System.out.println("Lanzamos peticion JSON para almacenar un jugador");

				String url = SERVER_PATH + upd;

				System.out.println("La url a la que lanzamos la peticiÃ›n es " + url);
				System.out.println("El json que enviamos es: ");
				System.out.println(json);
				// System.exit(-1);

				String response = encargadoPeticiones.postRequest(url, json);

				System.out.println("El json que recibimos es: ");
				System.out.println(response);

				JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
				System.out.println(respuesta);

			}
			todoOK = true;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return todoOK;
	}

}
