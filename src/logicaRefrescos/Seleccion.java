package logicaRefrescos;

/*
 * Clase selecci�n
 * Extiende la clase tecla
 * Existir� un objeto "selecci�n" por cada producto existente en la m�quina
 */
public class Seleccion extends Tecla {

	/*
	 * Atributo con el nombre del producto
	 */
	
	String nombreProducto;
	
	/*
	 * Cada tecla selecci�n tiene el clasificador asociado al que lanza el mensaje "seleccionar"
	 * cuando la tecla de selecci�n es pulsada
	 */
	private Clasificador miClasificador;
	
	/*
	 * Constructor
	 * @param asignado - Dispensador que se asigna al bot�n de selecci�n
	 */
	public Seleccion(Clasificador asignado, String producto){
		miClasificador = asignado;
		nombreProducto = producto;
	}
	
	public String getNombreProducto() {
		return nombreProducto;
	}



	/*
	 * cuando se pulsa la tecla de seleccion de un producto se lanza el mensaje seleccionar al dispensador
	 * recordamos que cada boton esta asociado con un unico dispensador
	 */
	public boolean pulsar() {
		boolean auxOk = miClasificador.seleccionarProducto(nombreProducto);
		return auxOk;
	}

}
