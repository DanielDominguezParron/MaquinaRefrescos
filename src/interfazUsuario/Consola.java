package interfazUsuario;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import controlador.Controlador;
import logicaRefrescos.Seleccion;
import maquinas.Maquina;

public class Consola extends Generico  {
	
	/* 
	 * Atributos declarados en clase madre
	 * 		miControlador: para lanzar las peticiones
	 * 		miScanner: para escribir por Consola
	 * Metodos ya definidos en clase madre
	 *  	inicializar  	
	 *  	finalizarMaquina
	 *  	insertarMoneda
	 *  	retornarMonedas
	 *  	seleccionarProducto 
	 */
	
	/*
	 * Constructor vacio ya que se crea con newInstance
	 */

	public Consola(){

	}
	
	/******************************************************************************************
	 * METODOS QUE CUMPLEN LOS METODOS ABSTRACTOS 
	*******************************************************************************************/

	// Menu Principal (adaptado de Taringa.net)
	@Override
	public void ejecucionMaquinaRefrescos() {
		int op = 0; // Opcion
		boolean salir = false;
		
		while (!salir) { // Estructura que repite el algoritmo del menu principal hasta que se la condicion sea falsa
			// Se muestra el menu principal
			System.out.println(".......................... \n" 
							+  ".  1 Insertar Moneda  \n"
							+  ".  2 Retornar Monedas \n" 
							+  ".  3 Seleccionar Producto \n"
							+  ".  4 Ver estado maquina \n"
							+  ".  5 Salir \n" 
							+  "..........................");
			try{
				op = miScanner.nextInt(); // Se le da a la variable op el valor del teclado
				System.out.println("OPCION SELECCIONADA:" + op);
				switch (op) {
					case 1:// Insertar Moneda
						menuMonedas();
						break;
					case 2:// Retornar Monedas
						retornarMonedas();
						break;
					case 3:// Seleccionar Producto
						menuProductos();
						break;
					case 4:// Comprobar estado maquina (debug)
						verEstadoMaquina();
						break;
					case 5:// Salir
						finalizarMaquina();
						salir = true;
						break;
					default:// No valido
						System.out.println("Opcion invalida: marque un numero de 1 a 5");
						break;
				}
			}catch (Exception e) {
				System.out.println("Excepcion por opcion invalida: marque un numero de 1 a 5");
	            // flushing scanner
				//e.printStackTrace();
				miScanner.next();
			}
		}
	}
	
	/*
	 * verEstadoMaquina
	 */
	@Override
	public void verEstadoMaquina() {
		String estado = miControlador.verEstadoMaquina();
		System.out.println(estado);
	} // Fin verEstadoMaquina
	

	


	/*******************************************************************************************************
	 * SUBMENUS PARA SELECCIONAR MONEDAS Y PRODUCTOS
	 *******************************************************************************************************/
	
	/*
	 * Submenu para insertar moneda
	 * Bucle hasta que se pulsa la opcion 0 que se vuelve al menu inicial
	 */

	private void menuMonedas() {

		boolean salir = false;
		int op = 0; // Opcion

		HashMap<Integer, String> nombreMonedas = miControlador.getNombreMonedas();
		String nombreMoneda;

		while(!salir){
			//System.out.println("Introducir Moneda: 1- 1c, 2- 2c, 3- 5c, 4-10c, 5-20c, 6-50c, 7-1e, 8-2e, 9-menu");
			System.out.println("Introducir Moneda");
			System.out.println("0 - Volver al Menu principal");
			// Para que salga ordenado el hashmap de monedas (de stackoverflow)
			SortedSet<Integer> keys = new TreeSet<Integer>(nombreMonedas.keySet());
			for (int key : keys) { 
				nombreMoneda = nombreMonedas.get(key);
				System.out.println(key + " - " + nombreMoneda);
			}			
			try {				
				op = miScanner.nextInt();
				if(op==0){// menu
					System.out.println("Volvemos menu principal");
					salir=true;
				}else if(nombreMonedas.get(op)==null){
					System.out.println("Opcion invalida: marque una las opciones validas");				
				} else{
					insertarMoneda(op);
				}
			}catch (Exception e) {
				System.out.println("Opcion invalida: marque una las opciones validas");
				miScanner.next();
			}
		}
	}

	/*
	 * Submenu para seleccionar producto
	 * Recorre el array de botones de seleccion y muestra una opcion por cada 
	 * Si se pulsa la opcion 0 se vuelve al menu
	 * 
	 */
	private void menuProductos() {
		int op = 0; // Opcion
		boolean salir = false;
		
		HashMap<String, String> botonesLata = miControlador.getNombreProductos();
		int numProductos = botonesLata.size();
		
		if(numProductos>0){
			
			while(!salir){
			
				String submenu = "Seleccionar Producto: \n0- Menu Principal \n";
				//Recorremos el array list de productos para saber cuantos hay y mostrar el menu independientemente del numero
				int contador = 1;
				String[] arrAux = new String[botonesLata.size()];
				
				for (Map.Entry<String, String> entry : botonesLata.entrySet()) {
				    String key = entry.getKey();
				    String nombreProducto = entry.getValue();
				    arrAux[contador-1] = key;
				    submenu += contador+"- "+ nombreProducto +"\n"; 
					contador++;
				    
				    // ...
				}
				
//				Iterator it = botonesLata.entrySet().iterator();
//				while (it.hasNext()) {
//					Map.Entry e = (Map.Entry)it.next();
//					Seleccion aux = (Seleccion) e.getValue();
//					arrAux[contador-1] = (String) e.getKey();
//					submenu += contador+"- "+aux.getNombreProducto()+"\n"; 
//					contador++;
//				}
				System.out.println(submenu);
				
				try {	
					op = miScanner.nextInt();
					switch (op) {
						case 0: // Menu
							System.out.println("Volvemos menu principal");
							salir = true;
							break;
						default: // Boton de seleccion de una lata o no valido
							if(op<=numProductos){
								String aux = arrAux[op-1];
								seleccionarProducto(aux);
								salir = true;
							}else{
								System.out.println("Opcion invalida: marque un numero de 0 a "+numProductos);
							}
							break;
					}
					
				}catch (Exception e) {
					System.out.println("Opcion invalida: marque una las opciones validas");
					miScanner.next();
				}
				
			}
			
		}else{
			System.out.println("No hay productos en la maquina");
		}
	} // Fin seleccionarProducto

	@Override
	public void mostrarMensaje(String m, boolean ok) {
		System.out.println("***************");
		System.out.println(m);
		System.out.println("***************");
	}
	
}
