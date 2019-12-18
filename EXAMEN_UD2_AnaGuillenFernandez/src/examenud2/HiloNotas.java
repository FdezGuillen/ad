package examenud2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HiloNotas extends Thread{
	
	private float[] notas; //Se declara un array donde se guardarán las notas

	public void run() {
		Scanner scanner = new Scanner(System.in);
		int eleccion = -1;

		while (eleccion != 0) {
			try {
				//Se inicia un menú para gestionar las notas
				System.out.println("___Gestión de notas___");
				System.out.println("1.- Insertar notas");
				System.out.println("2.- Calcular media");
				System.out.println("0.- Volver");

				eleccion = scanner.nextInt();

				switch (eleccion) {
				case 1:
					int longitudNotas;
					System.out.println("¿Cuántas notas vas a insertar?");
					longitudNotas = scanner.nextInt(); //El usuario introduce la longitud del array notas
					scanner.nextLine();
					
					notas = new float[longitudNotas];
					
					for (int i=0; i < longitudNotas; i++) {
						//El usuario inserta las notas una por una
						System.out.printf("Nota %d: ",i+1);
						notas[i] = scanner.nextFloat();
						scanner.nextLine();
						System.out.println();
					}
					
					System.out.println("Notas insertadas correctamente. \n");
					break;
				case 2:
					if (notas == null) {
						//Si no hay notas, mensaje de error
						System.out.println("Primero tienes que insertar notas.");
					}else {
						//Si hay notas, se recorre el array con un bucle
						//Se va sumando cada nota a la variable media
						float media = 0;
						for (int i=0; i<notas.length; i++) {
							media += notas[i];
						}
						media = media/notas.length; //Se divide media por la longitud del array
						System.out.println("Nota media: " + media + "\n");
					}
					break;
				case 0:
					System.out.println();
					break;
				default:
					System.out.println("Opción incorrecta. Escoge una opción entre las ofrecidas");
					break;
				}
			} catch (InputMismatchException ime) {
				System.out.println("Error. Tienes que introducir un número");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
