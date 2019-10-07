package controlador;

import java.util.Scanner;

public class Principal {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println("Inicio Ejecucion - Maquina Refrescos");
		Scanner miScanner = new Scanner(System.in); // Para leer las opciones de teclado
		
		Controlador miControlador = new Controlador(miScanner);		
		
	}



}