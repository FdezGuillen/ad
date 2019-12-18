package examenud2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	static Scanner scanner; 

	public static void main(String[] args) {
		scanner = new Scanner(System.in); //Crea el scanner
		menuInicio();//Ejecuta método menuInicio()
	}
	
	public static void menuInicio() {
		//En este método tenemos el primer menú, en el que decimos al programa si somos alumno o profesor
		
		int eleccion = -1;

		while (eleccion != 0) {
			//El menú se ejecutará mientras eleccion sea igual a 0
			
			try {
				System.out.println("*****GESTIÓN DEL CENTRO*****");
				System.out.println("¿Eres alumno o profesor? Elige una opción");
				System.out.println("1.- Alumno");
				System.out.println("2.- Profesor");
				System.out.println("0.- Salir del programa");

				eleccion = scanner.nextInt();//Lee un número entero pasado por teclado y lo asigna a eleccion

				//Según el valor de eleccion, inicia menuInteraccion() pasándole una booleana como parámetro
				//Si somos profesores, esta booleana será true. Si no, será false
				switch (eleccion) {
				case 1:
					System.out.println();
					menuInteraccion(false);
					break;
				case 2:
					System.out.println();
					menuInteraccion(true);
					break;
				case 0:
					System.out.println("Cerrando aplicación");//Si eleccion es 0, el bucle termina y se cierra el programa
					break;
				default:
					System.out.println("Opción incorrecta. Escoge una opción entre las ofrecidas");
					break;
				}

			} catch (InputMismatchException ime) {
				//Con esta excepción controlamos que el número pasado por teclado sea un entero
				System.out.println("Error. Tienes que introducir un número entero");
				scanner.nextLine();//Limpiamos el buffer de entrada
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
	
	public static void menuInteraccion(boolean esProfesor) {
		//En este método se inicia otro menú
		//Se da la opción de insertar datos y gestionar notas (sólo si el usuario es un profesor)
		
		//Declaramos los hilos
		HiloDatos hiloDatos;
		HiloNotas hiloNotas;
		
		int eleccion = -1;

		while (eleccion != 0) {
			try {
				//Imprimimos las opciones
				if (esProfesor)
					System.out.println("BIENVENIDO, PROFESOR");
				else
					System.out.println("BIENVENIDO, ALUMNO");
				
				System.out.println("Escoge una opción");
				System.out.println("1.- Insertar datos personales");
				if(esProfesor) {
					System.out.println("2.- Gestionar notas");
				}
				System.out.println("0.- Salir del programa");

				eleccion = scanner.nextInt();

				switch (eleccion) {
				case 1:
					//Creamos hilo para insertar datos, empezamos su ejecución
					hiloDatos = new HiloDatos();
					hiloDatos.start();
					hiloDatos.join();//Se espera a que el hilo termine antes de seguir con el programa
					break;
				case 2:
					//Si el usuario es profesor, crea un hilo para gestionar notas e inicia su ejecución
					if(esProfesor) {
						hiloNotas = new HiloNotas();
						hiloNotas.start();
						hiloNotas.join();//Se espera a que el hilo termine antes de seguir con el programa
					}else {
						System.out.println("Opción incorrecta. Escoge una opción entre las ofrecidas");
					}
						
					break;
				case 0:
					System.out.println("Cerrando aplicación");
					System.exit(0);//Cierra la aplicación
					break;
				default:
					System.out.println("Opción incorrecta. Escoge una opción entre las ofrecidas");
					break;
				}

			} catch (InputMismatchException ime) {
				System.out.println("Error. Tienes que introducir un número entero");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
	}
}
