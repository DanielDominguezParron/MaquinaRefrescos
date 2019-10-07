package interfazUsuario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.*;


public class Grafico extends Generico implements ActionListener{

	/* 
	 * Atributos declarados en clase madre
	 * 		miControlador: para lanzar las peticiones
	 * 		miScanner: para escribir por Consola
	 * Metodos ya definidos en clase madre
	 *  	inicializar  	
	 *  	finalizarMaquina
	 *  	insertarMoneda
	 *  	retornarMonedas
	 *  	seleccionarProducto
	 *  Atributos propios
	 *  	ventana : para el JFrame 
	 */
	
	private JFrame ventana;	
	private JLabel mensaje;
	
	/*
	 * Constructor vacio ya que se crea con newInstance
	 */	
	
	public Grafico() {
		ventana=new JFrame();
		
		// Añadimos un listener para controlar el cierre de la ventana (hay que llamar al controlador)
		ventana.addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent evt ) {
				System.out.println("CERRANDO");
				finalizarMaquina();
			}
		}); 	
		
	}

	@Override
	public void ejecucionMaquinaRefrescos() {

		ventana.setLayout(null);
		JLabel texto1 = new JLabel("MOTIS VENDING");
		texto1.setBounds(100,10,200,40);
		ventana.add(texto1);
		
		mensaje = new JLabel();
		mensaje.setBounds(400,100,400,450);
		mensaje.setBorder(BorderFactory.createLineBorder(Color.black));
		ventana.add(mensaje);
		
		JButton estado = new JButton("Ver Estado Maquina");
		estado.setBounds(100,100,200,30);
		estado.setName("ver-estado");
		estado.addActionListener(this);		
		ventana.add(estado);
		
		JButton retorno = new JButton("Retornar Monedas");
		retorno.setBounds(100,150,200,30);
		retorno.setName("retorno-monedas");
		retorno.addActionListener(this);		
		ventana.add(retorno);
				
		ventana.setBounds(10,10,1200,600);
		ventana.setVisible(true);
		ventana.setResizable(false);
		
		mostrarMonedas();
		mostrarProductos();
	}

	@Override
	protected void verEstadoMaquina() {
		String estado = miControlador.verEstadoMaquina();
		estado = procesarMensaje (estado);
		// Se puede hacer mostrando un dialogbox
		JOptionPane.showMessageDialog(ventana, estado);
		// O en el "visor"
		//mostrarMensaje(estado, true);
		
	}
	
	@Override
	public void mostrarMensaje(String m, boolean ok){
		
		m = procesarMensaje (m);
		mensaje.setText(m);
		Color borde;
		if(ok){
			borde = Color.green;
		}else{
			borde = Color.red;
		}
			
		mensaje.setBorder(BorderFactory.createLineBorder(borde));
		
	}

	public String procesarMensaje(String m){
		String aux;
		
		aux  = "<html>";
		m = m.replace("\n","<br>");
		aux += m;
		aux += "</html>";
		return aux;
	}
	

	/*******************************************************************************************************
	 * METODOS PARA SELECCIONAR MONEDAS Y PRODUCTOS
	 *******************************************************************************************************/
	
	/*
	 * Metodo para mostrar posibles monedas a insertar (lo hacemos con botones)
	 */

	private void mostrarMonedas() {

		HashMap<Integer, String> nombreMonedas = miControlador.getNombreMonedas();
		String nombreMoneda;
		String nombreBoton;
		JButton boton;
		int y = 250;
				
		// Para que salga ordenado el hashmap de monedas (de stackoverflow)
		SortedSet<Integer> keys = new TreeSet<Integer>(nombreMonedas.keySet());
		for (int key : keys) {
			nombreMoneda = nombreMonedas.get(key);
			boton = new JButton(nombreMoneda);
			boton.setBounds(100,y,200,30);
			nombreBoton = "moneda-"+key;
			boton.setName(nombreBoton);
			boton.addActionListener(this);
			ventana.add(boton);
			y += 50;
		}

	}

	/*
	 * Metodo para mostrar posibles productos a seleccionar (lo hacemos con botones)
	 */

	private void mostrarProductos() {

		HashMap<String, String> nombreProductos = miControlador.getNombreProductos();
		String nombreProducto;
		String nombreBoton;
		JButton boton;
		int y = 250;
				
		// Para que salga ordenado el hashmap de productos (de stackoverflow)
		SortedSet<String> keys = new TreeSet<String>(nombreProductos.keySet());
		for (String key : keys) {

			nombreProducto = nombreProductos.get(key);
			boton = new JButton(nombreProducto);
			boton.setBounds(850,y,200,30);
			nombreBoton = "producto-"+key;
			boton.setName(nombreBoton);
			boton.addActionListener(this);
			ventana.add(boton);
			y += 50;
		}

	}
	
	// Para controlar los eventos de la pantalla (pulsar botones)
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton source = (JButton) e.getSource();
		
		String nombreBoton = source.getName();
		String[] parts = nombreBoton.split("-");
		String prefijo = parts[0];
		String sufijo = parts[1];
		
		// Dependiendo de la primera parte del nombre del boton sabemos si han pulsado moneda, producto o retorno
		//System.out.println(prefijo);
		
		switch(prefijo){
			case "moneda":
				int moneda = Integer.parseInt(sufijo);
				this.insertarMoneda(moneda);
				break;
			case "producto":
				String clave = sufijo;
				this.seleccionarProducto(clave);
				break;
			case "retorno":
				this.retornarMonedas();
				break;
			case "ver":
				this.verEstadoMaquina();
				break;
			default:
				System.out.println("Boton no reconocido");
				System.out.println("Finaliza la ejecucion");
				System.exit(1);
		}
		
//		if(prefijo.equals("moneda")){
//			int moneda = Integer.parseInt(sufijo);
//			this.insertarMoneda(moneda);
//		}else if(prefijo.equals("producto")){
//			String clave = sufijo;
//			this.seleccionarProducto(clave);
//		}else if{
//			this.retornarMonedas();
//		}
		
		//JOptionPane.showMessageDialog(source, nombreBoton + " button has been pressed");
		
	}
	
}
