package ejercicios;

public class CincoHilos extends Thread{
	
	private int num;

	public CincoHilos(String nombre, int num) {
		this.num = num;
		this.setName(nombre);
		System.out.println("Creando " + this.getName());
		
	}

	public void run() {
		for (int i = 1; i <= num; i++)
			System.out.println("Hilo:" + this.getName() + " C=" + i);
	}
	
	public static void main(String[] args) {
		CincoHilos hilo1 = new CincoHilos("Hilo 1", 2);
		CincoHilos hilo2 = new CincoHilos("Hilo 2", 2);
		CincoHilos hilo3 = new CincoHilos("Hilo 3", 2);
		CincoHilos hilo4 = new CincoHilos("Hilo 4", 2);
		CincoHilos hilo5 = new CincoHilos("Hilo 5", 2);
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		hilo4.start();
		hilo5.start();
		
		System.out.println("3 Hilos iniciados...");
	}

}
