package interfazUsuario;

import java.util.Scanner;
import controlador.Controlador;
import maquinas.Maquina;

/*
 * Clase abstracta de la que heredan todos los interfaces
 * 	Atributos
 * 		miControlador: para lanzar las peticiones
 * 		miScanner: para escribir por Consola
 *  Metodos genericos
 *  	inicializar => Establecemos controlador y scanner para obtener datos por teclado (si fuera necesario)
 *  	En los siguientes, lo único que se hace es llamar al metodo correspondiente del controlador
 *  	
 *  	finalizarMaquina
 *  	insertarMoneda
 *  	retornarMonedas
 *  	seleccionarProducto 
 *  	
 *  Metodos abstractos (en cada interfaz específico se hará de forma diferente)
 *  	ejecucionMaquinaRefrescos   	
 *  	verEstadoMaquina
 */

public abstract class Generico {
	
	Controlador miControlador;
	Scanner miScanner;
	
	public void inicializar(Controlador controlador, Scanner scanner){
		miControlador = controlador;
		miScanner = scanner;
	}
	
	protected void finalizarMaquina(){
		miControlador.finalizar();
	}
	
	protected void insertarMoneda(int op){
		miControlador.insertarMoneda(op);	
	}

	protected void retornarMonedas() {
		miControlador.retornarMonedas();
	}
	
	protected void seleccionarProducto(String op){
		miControlador.seleccionarProducto(op);
	} 
	
	public abstract void ejecucionMaquinaRefrescos();
	public abstract void mostrarMensaje(String m, boolean ok);
	// Para depurar, no es necesario
	protected abstract void verEstadoMaquina();


}
