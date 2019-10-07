package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		String Cadena;
		Deposito deposito;
		try {
			BufferedReader b = new BufferedReader(new FileReader("Ficheros/datos/depositos.txt"));
			while ((Cadena = b.readLine()) != null) {
				String[] test = Cadena.split(";");
				String nombreMoneda = test[0];
				int valor = Integer.parseInt(test[1]);
				int cantidad = Integer.parseInt(test[2]);
				deposito = new Deposito(nombreMoneda, valor, cantidad);
				depositosCreados.put(valor, deposito);
				System.out.println(deposito.toString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		String Cadena;
		Dispensador dispensador;
		try {
			BufferedReader b = new BufferedReader(new FileReader("Ficheros/datos/dispensadores.txt"));
			while ((Cadena = b.readLine()) != null) {
				String[] test = Cadena.split(";");
				String clave = test[0];
				String nombre = test[1];
				int valor = Integer.parseInt(test[2]);
				int cantidad = Integer.parseInt(test[3]);
				dispensador = new Dispensador(clave, nombre, valor, cantidad);
				dispensadoresCreados.put(clave, dispensador);
				System.out.println(dispensador.toString());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		try {
			PrintWriter writer = new PrintWriter("Ficheros/datos/depositos.txt");
			writer.close();
			Iterator it = depositos.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				PrintWriter escr = new PrintWriter(new FileWriter("Ficheros/datos/depositos.txt"));
				Deposito a = (Deposito) e.getValue();
				escr.print(a.getNombreMoneda() + a.getValor() + a.getCantidad());
				escr.close();
				it.remove();
			}
			todoOK = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = false;
		try {
			PrintWriter writer = new PrintWriter("Ficheros/datos/dispensadores.txt");
			writer.close();
			Iterator it = dispensadores.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				PrintWriter escr = new PrintWriter(new FileWriter("Ficheros/datos/dispensadores.txt"));
				Dispensador a = (Dispensador) e.getValue();
				escr.print(a.getClave() + a.getNombreProducto() + a.getPrecio() + a.getCantidad());
				escr.close();
				it.remove();
			}
			todoOK = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return todoOK;

	}

} // Fin de la clase