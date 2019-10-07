package controlador;

import java.util.HashMap;
import java.util.Scanner;

import accesoDatos.I_Acceso_Datos;
import interfazUsuario.Generico;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import maquinas.Maquina;

public class Controlador {

	Scanner teclado;
	SeleccionTipoMaquina selector;
	I_Acceso_Datos accesoDatos;
	Generico interfaz;
	HashMap<Integer, Deposito> depositos;
	HashMap<String, Dispensador> dispensadores;
	Maquina maquinaRefrescos;

	String mensaje;

	public Controlador(Scanner miScanner) {
		teclado = miScanner;
		selector = new SeleccionTipoMaquina(miScanner);
		// Seleccionamos el acceso a datos
		accesoDatos = (I_Acceso_Datos) selector.elegirClase("accesoDatos");
		// Una vez tenemos el acceso a datos, obtenemos dispensadores y
		// depositos
		if (accesoDatos != null) {
			depositos = accesoDatos.obtenerDepositos();
			dispensadores = accesoDatos.obtenerDispensadores();

			if((depositos!=null) && (dispensadores!=null)){
				// Y creamos la maquina
				maquinaRefrescos = new Maquina(depositos, dispensadores);
				
				if (maquinaRefrescos != null) {
					System.out.println("Acceso a datos funcionando");
					
					// Una vez tenemos la máquina creada, iniciamos interfaz
					
					interfaz = (Generico) selector.elegirClase("interfazUsuario");
					if (interfaz != null) {
						interfaz.inicializar(this, teclado);
						interfaz.ejecucionMaquinaRefrescos();
						this.mostrarMensaje(true);
					}	else{
						System.out.println("Finaliza la ejecucion");
						System.exit(1);
					}				
					
				}  else{
					System.out.println("No se ha podido inicializar la maquina\nFinaliza la ejecucion");
					System.exit(1);
				}
			} else{
				System.out.println("No se ha podido inicializar la maquina\nFinaliza la ejecucion");
				System.exit(1);
			}
		} else{
			System.out.println("No se ha podido inicializar la maquina\nFinaliza la ejecucion");
			System.exit(1);
		}
		
	}


	// Cuando se llame a este método es que se ha finalizado la ejecucian de la maquina
	// Antes de salir almacenamos los datos
	public void finalizar(){
		
		boolean guardadosDep;
		boolean guardadosDis;
		
		guardadosDep = accesoDatos.guardarDepositos(depositos);
		guardadosDis = accesoDatos.guardarDispensadores(dispensadores);
		
		if(guardadosDep && guardadosDis){
			System.out.println("Se han almacenado los datos correctamente");
		}else{
			System.out.println("Se ha producido un error guardando los datos");
		}
		
		teclado.close();	
		System.out.println("Fin Ejecucion - Maquina Refrescos");
		System.exit(0);
	}

	
	/*
	 * METODOS PARA LLAMADAS DESDE EL INTERFAZ
	 * "Intermediario" entre interfaz y Maquina
	 */
	
	public void insertarMoneda(int op){
		boolean ok = maquinaRefrescos.insertarMoneda(op);
		this.mostrarMensaje(ok);
	}
	
	public void retornarMonedas() {
		boolean ok = maquinaRefrescos.getTeclaRetorno().pulsar();
		this.mostrarMensaje(ok);
	}
	
	public void seleccionarProducto(String op){
		String auxMensaje;
		boolean ok = maquinaRefrescos.getBotonesLatas().get(op).pulsar();

		auxMensaje = maquinaRefrescos.getMensaje();
		
		if (ok && maquinaRefrescos.getClasificador().getSaldo() > 0) {	// Si se ha servido la lata y hay saldo pasamos a devolver las monedas
			maquinaRefrescos.getTeclaRetorno().pulsar();
		}
		
		this.mostrarMensaje(auxMensaje, ok);
	} 
	
	public String verEstadoMaquina() {
		String estado = maquinaRefrescos.getClasificador().toString();
		return estado;
	}
	
	public HashMap<Integer, String> getNombreMonedas(){
		HashMap<Integer, String> nombreMonedas = maquinaRefrescos.getNombreMonedas();
		return nombreMonedas;
	}
	
	public HashMap<String, String> getNombreProductos(){
		HashMap<String, String> nombreProductos = maquinaRefrescos.getNombreProductos();
		return nombreProductos;
	}
	
	private void mostrarMensaje(boolean ok){
		mensaje = maquinaRefrescos.getMensaje();
		interfaz.mostrarMensaje(mensaje, ok);
	}
	
	private void mostrarMensaje(String primeraParte, boolean ok){
		mensaje = primeraParte + "\n" + maquinaRefrescos.getMensaje();
		interfaz.mostrarMensaje(mensaje, ok);
	}	
	
	
}
