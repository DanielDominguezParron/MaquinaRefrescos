package logicaRefrescos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/*
 * Clase Clasificador
 * Es el "jefe" de la maquina. El encargado de llamar a los demas elementos para que todo funcione correctamente
 * Existe un unico objeto Clasificador
 */

public class Clasificador {

	/*
	 * Atributos de la clase 
	 * int saldo - saldo que ha introducido el usuario
	 * HashMap<String, Dispensador> misDispensadores - contiene todos los
	 * dispensadores (uno por producto de la maquina) 
	 * HashMap<Integer, Deposito> - depositos de las monedas
	 */
	private int saldo;	//En centimos
	private String mensaje; //Para guardar el "estado" y poder mostrarlo al usuario por pantalla   
	private HashMap<String, Dispensador> misDispensadores;
	private HashMap<Integer, Deposito> misDepositos; // uno por cada moneda: 1,2,5,10,20,50,1,2 (ampliar)

	/*
	 * Constructor
	 * 
	 * @param depositos - Se recibe el array de los depositos previamente creado
	 * 
	 * @see Maquina No se reciben los dispensadores: como a cada dispensador hay
	 * que asignarle un clasificador lo que se hace es crear el clasificador,
	 * posteriormente los dispensadores y luego se asignan mediante un set (ver
	 * el constructor de la clase maquina)
	 */
	public Clasificador(HashMap<Integer, Deposito> hmDepositos,
			HashMap<String, Dispensador> hmDispensadores) {
		saldo = 0;
		misDepositos = hmDepositos;
		misDispensadores = hmDispensadores;
		// Termino la inicializacion mostrando bienvenida
		mensaje = "Bienvenid@";
	}

	/*
	 * Insertar una moneda - Se llama directamente desde el menu principal 
	 * Añade una moneda al deposito correspondiente, aumenta el saldo y llama al visor
	 * 
	 * @param valor - moneda que se ha introducido. El tipo del parametro puede
	 * variar segun la implementacion
	 * 
	 */
	public boolean insertarMoneda(int valor) {
		boolean auxOk = true;
		Deposito depAux = misDepositos.get(valor);
		if(depAux!=null){
			saldo += valor;
			misDepositos.get(valor).anadir();
			mensaje = "Saldo: "+ saldo + " centimos";
			auxOk = true;
		}else{
			mensaje = "Moneda no reconocida";
			auxOk = false;
		}
		return auxOk;
	}

    /*
     * Devuelve las monedas necesarias (restar de cada deposito)
     * El saldo final debe ser 0
     * Se puede llamar desde un objeto retorno o desde esta propia clase al seleccionar un producto si hay que dar cambio
     * Hay que comprobar que hay saldo. Si no hay saldo mostrar error
     */
    public boolean retornarMonedas(){
        //miVisor.mostrarSaldo(saldo);
    	boolean auxOk = true;
        if(saldo==0){
            mensaje = "No se pueden devolver monedas porque no se ha introducido saldo";
            auxOk = false;
        }else{
            
        	/*
             * Calculo de las monedas que hay que devolver
             * Empezamos por la mas alta y vamos devolviendo si hay disponibles
             */
            
            int valorMoneda;
            int cantidadMoneda;
            Deposito auxDeposito;
            String auxMensaje = "Comienza devolución monedas";
            
            TreeSet<Integer> keys = new TreeSet<Integer>(misDepositos.keySet());
            TreeSet <Integer>keysreverse = (TreeSet<Integer>)keys.descendingSet();
            
    		for (int key : keysreverse) {
    			
    			if(saldo>0){
                    auxDeposito = misDepositos.get(key);
	                valorMoneda = auxDeposito.getValor();
	                cantidadMoneda = auxDeposito.getCantidad();

	                while((saldo>=valorMoneda) && (cantidadMoneda>0)) {
	                	auxDeposito.restar();
	                	auxMensaje += "\nDevuelta moneda de "+valorMoneda;
	                    saldo = saldo - valorMoneda;
		                cantidadMoneda = auxDeposito.getCantidad();	                    
	                }
                }else {
                	break;	// Forzamos la salida del bucle
                }    			
    			
    		}
    		
            if(saldo>0){
            	auxMensaje += "\nSe he producido un error devolviendo las monedas";
            	auxMensaje += "\nLlame al telefono gratuito 900 100 800 para poner una reclamacion por los "+saldo+" centimos que no le han sido devueltos";
            	auxMensaje += "\nDisculpe las molestias. Gracias";
            	auxOk = false;
            	
				saldo = 0;
			}

            mensaje = auxMensaje;
        	System.out.println("DEBUG: "+ auxMensaje); // DEBUG

        }
        

    	return auxOk;

    }

	public boolean seleccionarProducto(String clave) {
		/*
		 * Si no hay disponiblidad => Mostramos error "Producto agotado" 
		 * Si saldo = 0 => Mostramos precio 
		 * Si saldo > 0 y saldo < precio => Mostramos error "Saldo insuficiente: se necesitan X euros" 
		 * Si no hay cambio => Mostramos error "No hay monedas para proporcionar cambio"
		 * Si saldo > precio y hay cambio => Dispensamos producto, retornamos monedas y mostramos mensaje despedida
		 */

		Dispensador dispensadorSeleccionado = misDispensadores.get(clave);
		int disponibles = dispensadorSeleccionado.getCantidad();
		int precio = dispensadorSeleccionado.getPrecio();
		String nombreProducto = dispensadorSeleccionado.getNombreProducto();
		boolean auxOk = true;

		if (disponibles == 0) {
			mensaje = "Producto "+ nombreProducto+" agotado";
			auxOk = false;
		} else {
			if (saldo == 0) {
				mensaje = "El precio de "+nombreProducto + " es " + precio + " centimos";
			} else {
				if (saldo < precio) {
					mensaje = "Saldo insuficiente.\n El precio del producto es "
							+ precio + " centimos.\n Ha introducido " + saldo + " centimos";
					auxOk = false;
				} else {
					if (!hayCambio(saldo - precio)) {
						mensaje = "No hay monedas para proporcionar el cambio";
						auxOk = false;
					} else {// TODO OK
						dispensadorSeleccionado.dispensar();

						mensaje = "Servida una lata de " + nombreProducto;
						
						System.out.println("DEBUG: "+mensaje); // DEBUG
						saldo = saldo - precio;
// Hacemos la devolución de monedas desde el controlador para poder controlar la salida de la lata
//						if (saldo > 0) {
//							this.retornarMonedas();
//						}
					}
				}
			}
		}
		
		return auxOk;

	}

	private boolean hayCambio(double auxSaldo) {
		boolean devolver = true;
		// COMPLETAR (llamando a retornar monedas o copiando el algoritmo pero
		// sin restar las monedas
		return devolver;
	}

	/*
	 * toString para comprobar el estado de la maquina
	 * 
	 * @return retorna un string con los datos obtenidos El clasificador tiene
	 * acceso a los depositos y dispensadores que es lo que nos interesa
	 * comprobar
	 */
	public String toString() {
		String s = "";
		s += "Estado de la maquina de refrescos\n";
		s += "Saldo: " + saldo + "centimos\n";
		// Recorremos depositos		
		s += "Hay " + misDepositos.size() + " depositos de monedas\n";
		Deposito auxDep;
		
		// Para que salga ordenado el hashmap de monedas (de stackoverflow)
		SortedSet<Integer> keys = new TreeSet<Integer>(misDepositos.keySet());
		for (int key : keys) {
			auxDep = (Deposito) misDepositos.get(key);
		    s += auxDep.toString();
		}
		// Recorremos Dispensadores
	    s += "Hay " + misDispensadores.size() + " dispensadores de productos\n";
		Dispensador auxDis;
		
		
		for (HashMap.Entry<String, Dispensador> entry : misDispensadores.entrySet()) {
		    auxDis = (Dispensador) entry.getValue();
		    s += auxDis.toString();
		}
		// Retornamos el string;
		return s;
	}

	public HashMap<String, Dispensador> getMisDispensadores() {
		return misDispensadores;
	}

	public HashMap<Integer, Deposito> getMisDepositos() {
		return misDepositos;
	}

	public String getMensaje() {
		return mensaje;
	}

	public int getSaldo() {
		return saldo;
	}
	
	
	


} // Fin de la clase clasificador
