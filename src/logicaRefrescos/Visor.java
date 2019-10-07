package logicaRefrescos;

/*
 * Clase visor
 * La unica funcion de esta clase es mostrar una serie de mensajes por pantalla
 * ¿Se podria usar tambien para hacer un fichero de log?
 * Ahora mismo inservible
 */

public class Visor {
	
	/*
	 * No se incluye constructor de la clase puesto que 
	 * como la clase no tiene atributos, en principio no es necesario inicializar nada
	 */

	/*
	 * Muestra un mensaje de bienvenida
	 * En principio, no hace falta parametro: se puede poner un mensaje fijo
	 * Se puede añadir parametro si se desea
	 */
	public void mostrarBienvenida(){
		String mensaje = "Bienvenido";
		this.mostrarMensaje(mensaje);
	}

	/*
	 * Muestra un mensaje de despedida
	 * En principio, no hace falta parametro: se puede poner un mensaje fijo
	 * Se puede añadir parametro si se desea
	 */	
	public void mostrarDespedida(){
		String mensaje = "Muchas gracias. Que tenga un buen dia!!!";
		this.mostrarMensaje(mensaje);
	}
	
	/*
	 * Muestra un error
	 * @param e error que se desea mostrar
	 */
	public void mostrarError(String e){
		this.mostrarMensaje(e);
	}
	
	/*
	 * Muestra el precio de un producto
	 * @param p precio que se desea mostrar
	 */	
	public void mostrarPrecio(int p, String prod){
		String mensaje = "Precio del producto " + prod+ ":"+ p +" centimos";
		this.mostrarMensaje(mensaje);
	}
	
	/*
	 * Muestra el saldo
	 * @param s saldo que se desea mostrar
	 */	
	public void mostrarSaldo(int s){
		String mensaje = "Saldo: "+ s + " centimos";
		this.mostrarMensaje(mensaje);
	}
	
	/*
	 * Se añade este metodo que no esta en el diagrama por si es necesario utilizarlo en algun momento
	 * (¿para depurar?)
	 * @param m  mensaje que se desea mostrar 
	 */
	public void mostrarMensaje(String m){
		System.out.println(m);		
	}

}
