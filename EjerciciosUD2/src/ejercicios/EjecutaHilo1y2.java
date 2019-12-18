package ejercicios;

public class EjecutaHilo1y2 {

	public static void main(String[] args) throws InterruptedException {
		Hilo1 hiloPares = new Hilo1();
		Hilo2 hiloImpares = new Hilo2();
		
		hiloPares.start();
		hiloImpares.start();

		hiloPares.join();
		hiloImpares.join();
		
		System.out.println(hiloPares.getState());
		System.out.println(hiloPares.getState());
		
	}
}
