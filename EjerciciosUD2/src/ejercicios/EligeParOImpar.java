package ejercicios;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EligeParOImpar {

	public static void main(String[] args) {
		Scanner lector = new Scanner(System.in);
		Hilo1 hiloPares = new Hilo1();
		Hilo2 hiloImpares = new Hilo2();

		int eleccion = -1;

		while (eleccion != 0) {
			try {

				System.out.println("Elige:");
				System.out.println("1-Pares");
				System.out.println("2-Impares");
				System.out.println("0-Salir");

				eleccion = lector.nextInt();

				switch (eleccion) {
				case 1:
					hiloPares.start();
					hiloPares.join();
					System.out.println(hiloPares.getState());
					
					break;
				case 2:
					hiloImpares.start();
					hiloImpares.join();
					System.out.println(hiloImpares.getState());
					break;
				case 0:
					System.out.println("Cerrando aplicación");
					break;
				default:
					System.out.println("Opción incorrecta. Escoja otra opción");
					break;
				}
				
				System.out.println();

			} catch (InputMismatchException ime) {
				System.out.println("Error. Introduzca un número");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

	}

}
