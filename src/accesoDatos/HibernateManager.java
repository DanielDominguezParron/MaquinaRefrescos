package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class HibernateManager implements I_Acceso_Datos {
	Session session;

	public HibernateManager() {

		HibernateUtil util = new HibernateUtil();

		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		Query q = session.createQuery("select e from Deposito e");
		List results = q.list();

		Iterator depositoIterator = results.iterator();

		while (depositoIterator.hasNext()) {
			Deposito deposito = (Deposito) depositoIterator.next();
			depositosCreados.put(deposito.getValor(), deposito);

		}
		System.out.println("Fin Consulta Equipos");
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		int contador = 0;
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		Query q = session.createQuery("select e from Dispensador e");
		List results = q.list();

		Iterator dispensadorIterator = results.iterator();

		while (dispensadorIterator.hasNext()) {
			Dispensador dispensador = (Dispensador) dispensadorIterator.next();

			contador++;
			dispensadoresCreados.put(dispensador.getClave(), dispensador);

		}
		System.out.println("Fin Consulta Equipos");
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		Iterator<Entry<Integer, Deposito>> it = depositos.entrySet().iterator();
		while (it.hasNext()) {
			session.beginTransaction();
			Map.Entry e = (Map.Entry) it.next();
			Deposito a = (Deposito) e.getValue();
			session.update(a);
			session.getTransaction().commit();
			System.out.println("Fin guardado");
		}
		return true;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		Iterator<Entry<String, Dispensador>> it = dispensadores.entrySet().iterator();
		while (it.hasNext()) {
			session.beginTransaction();
			Map.Entry e = (Map.Entry) it.next();
			Dispensador a = (Dispensador) e.getValue();
			session.update(a);
			session.getTransaction().commit();
			System.out.println("Fin guardado");
		}
		return true;
	}

}
