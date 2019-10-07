package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import accesoDatos.I_Acceso_Datos;
import auxiliares.LeeProperties;
import interfazUsuario.Generico;

public class SeleccionTipoMaquina {

	Scanner teclado;
	Generico interfaz;

	public SeleccionTipoMaquina(Scanner teclado) {
		this.teclado = teclado;
	}

	/*
	 * METODO PARA SELECCIONAR TIPO DE ACCESO A DATOS O INTERFAZ
	 */

	public Object elegirClase(String tipoClase) {
		HashMap<String, String> nombreClases;
		Object aux = null;
		String nombreClase;
		String rutaFichero = "Ficheros/config/" ;
		
		if(tipoClase.equals("accesoDatos")){
			rutaFichero += "classNames.properties";
		}else if(tipoClase.equals("interfazUsuario")){
			rutaFichero += "interfacesNames.properties";
		}else{
			System.out.println("Eleccion de clase no valida\nFinaliza la ejecucion");
			System.exit(1);
		}
		
		LeeProperties auxClases = new LeeProperties(rutaFichero);

		nombreClases = auxClases.getHash();
		int size = nombreClases.size();
		
		switch (size) {
		case 0: // No hay clases. Finalizamos ejecución
			System.out.println(
					"Error generando los datos: no hay clases de " + tipoClase + " en el fichero de configuracion");
			System.out.println("Finaliza la ejecucion");
			System.exit(1);
			break;
		case 1: // Solo hay una clase disponible. Seleccionamos directamente
				// dicha clase
			nombreClase = nombreClases.entrySet().iterator().next().getValue();
			System.out.println("Se ha detectado una unica clase para " + tipoClase + " en el fichero de configuracion");
			System.out.println("Tipo " + tipoClase + " seleccionado: " + nombreClase);
			aux = this.crearInstanciaClase(nombreClase, tipoClase);
			break;
		default: // Hay mas de una clase disponible. 
				 // Mostramos menu para seleccionar

			int min = 2; // 0 salir y 1 aleatorio

			int max = size + 1;

			// Guardamos en una variable el menu a escribir
			String menu = "SELECCIONE " + tipoClase + " A UTILZAR \n";
			menu += "En el fichero de configuracion se han encontrado " + nombreClases.size() + " clases disponibles\n";
			menu += ".......................... \n" + ".  0- Salir \n" + ".  1- Aleatorio  \n";

			int contador = 2; // 0 salir y 1 aleatorio
			ArrayList<String> opciones = new ArrayList<String>();

			for (HashMap.Entry<String, String> entry : nombreClases.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				menu += ".  " + contador + "- " + key + "\n";
				opciones.add(value);
				contador++;
			}

			int rd; // Numero aleatorio
			boolean salir = false;

			while (!salir) {// Estructura que repite el algoritmo de seleccion
							// de acceso a datos hasta que se seleccione 
							// la opcion salir
				System.out.println();
				System.out.println(menu);

				try {

					Integer op = teclado.nextInt(); // Se le da a la variable op
													// el valor del teclado
					switch (op) {
					case 0:// Salir
						System.out.println("Opcion SALIR seleccionada");
						System.out.println("Finaliza la ejecucion");
						System.exit(1);
						break;
					case 1: // Aleatorio
						rd = randInt(min, max);
						System.out.println("Se ha seleccionado aleatoriamente la opcion " + rd);
						// Igualamos la opcion al numero aleatorio obtenido
						// No ponemos break para que siga por el switch;
						op = rd;
					default:
						if (op <= max) {
							/*
							 * El array con los nombres de las clases empieza en 0 
							 * (0- salir y 1-aleatorio)
							 */
							nombreClase = opciones.get(op - 2);
							System.out.println("Tipo " + tipoClase + " seleccionado: " + nombreClase);
							aux = this.crearInstanciaClase(nombreClase, tipoClase);
							salir = true;
						} else {// No valido
							System.out.println("Opcion invalida: marque un numero de 1 a " + max);
						}
						break;
					}

				} catch (InputMismatchException e) {
					System.out.println("Opcion invalida: marque un numero de 1 a " + max);
					// flushing scanner
					teclado.next();
				}

			}
			
			break;

		}
		
		return aux;

	}

	/*
	 * Metodo para crear la instancia de una clase 
	 * Sirve tanto para interfaz como para acceso a datos
	 * Se hace cast en la llamada al metodo
	 */

	@SuppressWarnings("rawtypes")
	private Object crearInstanciaClase(String nombreClase, String paquete) {
		Object aux = null;

		try {
			Class miclase = Class.forName(paquete + "." + nombreClase);
			aux = miclase.newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al crear la instancia de " + paquete + ": no se ha encontrado la clase seleccionada");
			System.out.println("Finaliza la ejecucion");
			// e.printStackTrace();
			System.exit(1);
		} catch (InstantiationException e) {
			System.out.println("Error al crear la instancia de " + paquete + ": no se puede instanciar la clase seleccionada");
			System.out.println("Finaliza la ejecucion");
			// e.printStackTrace();
			System.exit(1);
		} catch (IllegalAccessException e) {
			System.out.println("Error al crear la instancia de " + paquete + ": acceso ilegal");
			System.out.println("Finaliza la ejecucion");
			// e.printStackTrace();
			System.exit(1);
		}

		return aux;
	}
	
	/**
	 * De stackoverflow
	 * 
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 * 
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}
