package examenud2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HiloDatos extends Thread{
	
	private String nombre;
	private String apellidos;
	private int edad;
	private String dni;
	
	public void run() {
		
		Scanner scanner = new Scanner(System.in);
		
		//Este hilo pide los datos Nombre, Apellidos, Edad y DNI
		//Cuando se ha terminado de insertar datos, los imprime
		
		//En el caso de haber algún error, se terminará la ejecución del hilo
	
			try {
				System.out.println("Inserta tus datos personales:");
				
				System.out.println("Nombre: ");
				nombre = scanner.nextLine();
				//No hace falta limpiar el buffer cuando Scanner utiliza nextLine()
				
				System.out.println("Apellidos: ");
				apellidos = scanner.nextLine();
				
				System.out.println("Edad: ");
				edad = scanner.nextInt();
				scanner.nextLine();//Limpia el buffer de entrada
				
				System.out.println("DNI: ");
				dni = scanner.nextLine();
				
				System.out.println("Datos insertados con éxito. Estos son tus datos:");
				System.out.println("Nombre: " + nombre + "\n"
						+ "Apellidos: " + apellidos + "\n"
						+ "Edad: " + edad + "\n"
						+ "DNI: " + dni + "\n");
				
			} catch (InputMismatchException ime) {
				System.out.println("Error. Tienes que introducir un número entero. Cancelando inserción");
				scanner.nextLine();//Limpia el buffer de entrada
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage() + ". Cancelando inserción");
				scanner.nextLine();//Limpia el buffer de entrada
			}
			
		}
	
	
	

}
