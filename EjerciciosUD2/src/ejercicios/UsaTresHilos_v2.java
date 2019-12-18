package ejercicios;

public class UsaTresHilos_v2 {
	
	public static void main(String[] args) {
		TresHilos_v2 hilo1 = new TresHilos_v2("Hilo 1", 5);
		TresHilos_v2 hilo2 = new TresHilos_v2("Hilo 2", 5);
		TresHilos_v2 hilo3 = new TresHilos_v2("Hilo 3", 5);
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		
		System.out.println("3 Hilos iniciados...");
	}

}

