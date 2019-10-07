package accesoDatos;

import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class SinAlmacenamiento implements I_Acceso_Datos {

	/*
	 * En este caso se crean los objetos en el propio c�digo, no se leen los datos de ning�n lado
	 * Los datos, no ser�n persistentes
	 */
	
	public SinAlmacenamiento() {
		System.out.println("ACCESO A DATOS - CREADOS A MANO");
	}

	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito auxDeposito;
		int clave;
		for (int i = 2; i < 8; i++) {
			switch (i) {
//			case 0: // 1c
//				clave = 1;
//				auxDeposito = new Deposito("UN CENTIMO",0.01, 10);
//				break;
//			case 1:
//				clave = 2;
//				auxDeposito = new Deposito("DOS CENTIMOS",0.02, 10);
//				break;
			case 2:
				clave = 5;
				auxDeposito = new Deposito("CINCO CENTIMOS",5, 10);
				break;
			case 3:
				clave = 10;
				auxDeposito = new Deposito("DIEZ CENTIMOS",10, 10);
				break;
			case 4:
				clave = 20;
				auxDeposito = new Deposito("VEINTE CENTIMOS",20, 10);
				break;
			case 5:
				clave = 50;
				auxDeposito = new Deposito("CINCUENTA CENTIMOS",50, 10);
				break;
			case 6:
				clave = 100;
				auxDeposito = new Deposito("UN EURO",100, 0);
				break;
			default:
				clave = 200;
				auxDeposito = new Deposito("DOS EUROS",200, 10);
				break;
			}
			// Una vez creado el deposito con valor de la moneda y cantidad lo metemos en el hashmap
			depositosCreados.put(clave, auxDeposito);
		}
		//System.out.println("Terminados depositos");
		return depositosCreados;
	}


	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String,Dispensador> dispensadoresCreado = new HashMap<String,Dispensador>();
		String clave;
		Dispensador dispensador;		
		// Creamos dispensadores
		// Coca-Cola
		clave = "coca";
		dispensador = new Dispensador(clave,"Coca-Cola",	100, 10);
		dispensadoresCreado.put(clave, dispensador);
		// Fanta
		clave = "fantaN";
		dispensador = new Dispensador(clave,"Fanta Naranja",	120, 10);
		dispensadoresCreado.put(clave, dispensador);
		//System.out.println("Terminados dispensadores");
		return dispensadoresCreado;
	}


	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		System.out.println("No se pueden guardar los datos de los depositos puesto que se han creado a mano");
		return false;
	}


	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		System.out.println("No se pueden guardar los datos de los dispensadores puesto que se han creado a mano");		
		return false;
	}

} // Fin de la clase
