package maquinas;

import java.util.HashMap;

import logicaRefrescos.Clasificador;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import logicaRefrescos.Retorno;
import logicaRefrescos.Seleccion;

public class Maquina {

	private Clasificador clasificador;
	private HashMap<String, Seleccion> botonesLatas;
	private Retorno teclaRetorno;
	
	public Maquina (HashMap<Integer, Deposito> hmDepositos, HashMap<String, Dispensador> hmDispensadores){
		// Creamos clasificador (le pasamos como parametro los depositos y los dispensadores)		
		clasificador = new Clasificador(hmDepositos, hmDispensadores);
		// Creamos tecla retorno (le pasamos el clasificador para que lo tenga enlazado)
		teclaRetorno = new Retorno(clasificador);		
		// Creamos los botones de seleccion (uno por cada producto)
		// Recorremos hashmap de dispensadores para obtener el nombre del producto
		Seleccion nuevoSel;
		botonesLatas = new HashMap<String, Seleccion>();		
		for (String nombreProd : hmDispensadores.keySet()) {
			nuevoSel = new Seleccion(clasificador, nombreProd);
			botonesLatas.put(nombreProd, nuevoSel);
		}
	}

	/*
	 * Getters de los elementos "frontales" de la m�quina
	 */
	
	public Clasificador getClasificador() {
		return clasificador;
	}

	public HashMap<String, Seleccion> getBotonesLatas() {
		return botonesLatas;
	}

	public Retorno getTeclaRetorno() {
		return teclaRetorno;
	}
	
	/*
	 *  Getters de los elementos "interiores" de la maquina
	 */
	
	public HashMap<Integer, String> getNombreMonedas() {
		HashMap<Integer, Deposito> depositos =  clasificador.getMisDepositos();
		HashMap<Integer, String> nombreMonedas = new HashMap<Integer, String>();
		Deposito depAux;
		for (Integer key : depositos.keySet()) {
			depAux = depositos.get(key);
			nombreMonedas.put(key, depAux.getNombreMoneda());
		}		
				
		return nombreMonedas;
	}
	

	public HashMap<String, String> getNombreProductos() {
		HashMap<String, Dispensador> dispensadores =  clasificador.getMisDispensadores();
		HashMap<String, String> nombreProductos = new HashMap<String, String>();
		Dispensador disAux;
		for (String key : dispensadores.keySet()) {
			disAux = dispensadores.get(key);
			nombreProductos.put(key, disAux.getNombreProducto());
		}		
				
		return nombreProductos;
	}
	
	public String getMensaje(){
		return clasificador.getMensaje();
	}
	
	/*
	 * InsertarMoneda
	 */
	
	public boolean insertarMoneda(int valor){
		boolean ok = clasificador.insertarMoneda(valor);
		return ok;
	}
	
	/*
	 * Metodo finalizarMaquina
	 * Guarda los datos de los dispensadores y depositos (si se puede)
	 * Se llama a este metodo cuando se elige la opcion salir
	 */
	
	public String FinalizarMaquina() {
		String estado = "ESTADO FINAL DE LA M�QUINA\n";
		estado += this.getClasificador().toString()+"\n";
		return estado;
	}
	
}
