package logicaRefrescos;

/*
 * Clase Tecla abstracta
 * Contiene un unico metodo "pulsar" abstracto. Se definira en las clases hijas que extiendan tecla
 * Devuelve Ok true o false dependiendo de si ha habido algun error
 */
public abstract class Tecla {
	
	public abstract boolean pulsar();
	
}
