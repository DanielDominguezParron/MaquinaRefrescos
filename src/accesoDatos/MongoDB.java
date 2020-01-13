package accesoDatos;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class MongoDB implements I_Acceso_Datos {
	private MongoClient mongo;
	DBCollection dispensadores;
	DBCollection depositos;
	DB db;

	public MongoDB() {
		/**** Connect to MongoDB ****/

		try {
			mongo = new MongoClient("localhost", 27017);
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			db = mongo.getDB("maquinarefrescos");

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			dispensadores = db.getCollection("dispensadores");
			depositos = db.getCollection("depositos");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		DBCursor cur = depositos.find();
		for (DBObject model : cur) {
			int id = Integer.parseInt((String) model.get("id"));
			String nombre = (String) model.get("nombre");
			int valor = Integer.parseInt((String) model.get("valor"));
			int cantidad = Integer.parseInt((String) model.get("cantidad"));
			Deposito deposito = new Deposito(nombre, id, cantidad);
			depositosCreados.put(valor, deposito);

		}
		return depositosCreados;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		DBCursor cur = dispensadores.find();
		for (DBObject model : cur) {
			String clave = (String) model.get("clave");
			String nombre = (String) model.get("nombre");
			int cantidad = Integer.parseInt((String) model.get("cantidad"));
			int valor = Integer.parseInt((String) model.get("precio"));
			Dispensador dispensador = new Dispensador(clave, nombre, valor, cantidad);
			dispensadoresCreados.put(clave, dispensador);

		}
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		// TODO Auto-generated method stub
		return false;
	}

}
