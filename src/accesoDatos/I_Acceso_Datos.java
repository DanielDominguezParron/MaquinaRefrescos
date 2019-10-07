package accesoDatos;

import java.util.ArrayList;
import java.util.HashMap;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;


public interface I_Acceso_Datos {
	
	public HashMap<Integer, Deposito>  obtenerDepositos();
	public HashMap<String, Dispensador> obtenerDispensadores();
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos);
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores);
	
}
