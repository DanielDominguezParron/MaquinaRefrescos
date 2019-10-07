package logicaRefrescos;

/*
 * Clase retorno
 * Extiende la clase tecla
 * Existir� un �nico bot�n de retorno (un �nico objeto)
 */
public class Retorno extends Tecla {

	/*
	 * La tecla de retorno est� asociada con el clasificador
	 */
	Clasificador miClasificador;

	/*
	 * Constructor
	 * @param asignado - Clasificador que se asigna al bot�n de retorno
	 */
	public Retorno(Clasificador asignado){
		miClasificador = asignado;
	}

	/*
	 * cuando se pulsa la tecla de retorno se lanza el mensaje retornarMonedas al clasificador
	 */
	public boolean pulsar() {
		boolean ok = miClasificador.retornarMonedas();
		return ok;
	}
	
}
