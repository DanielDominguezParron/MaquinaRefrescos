package logicaRefrescos;

/*
 * Clase Dispensador
 * Existira un objeto de esta clase por cada tipo de producto que haya en la maquina
 */
public class Dispensador {
	
	/*
	 * Atributos de la clase
	 * String nombreProducto - No esta en el diagrama pero lo añado para poder identificar el producto (coca, fanta, sprite,...)
	 * int cantidad - numero de latas de un producto que se encuentran en la maquina en un determinado momento
	 * int precio - precio que cuesta el producto (en centimos)  
	 */
	private int id;
	private String clave;
	private String nombreProducto;
	private int cantidad;
	private int precio;


	/*
	 * Constructor vacío (para Hibernate)
	 */
	
	public Dispensador(){}
	
	
	/*
	 * Constructor
	 * @param nombre - nombre del producto
	 * @param p - precio del producto
	 * @param inicial - cantidad inicial de productos
	 * El constructor no recibe el clasificador, se asigna posteriormente
	 */
	public Dispensador(String clave, String nombre,  int p, int inicial){
		this.clave = clave;
		nombreProducto = nombre;
		cantidad = inicial;
		precio = p;
	}
	
	/*
	 * Getters y setters (para Hibernate)
	 */
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getNombreProducto() {
		return nombreProducto;
	}


	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}

	
	/*
	 * Disminuye la cantidad de latas de un producto
	 * Como no es una m�quina real hay que simular de alguna forma que se ha dispensado una lata
	 * Podemos a�adir un mensaje en este m�todo o hacer que el clasificador le pida al visor que muestre un mensaje
	 */
	public void dispensar(){
		cantidad--;
	}

	

	/*
	 * toString
	 * para comprobar el estado del dispensador
	 * @return retorna un string con los datos del dispensador
	 */
	public String toString(){
		String s = "";
		s += "El dispensador de "+nombreProducto+" contiene "+cantidad+" latas que cuestan "+precio+" centimos\n";
		return s;
	}

}
