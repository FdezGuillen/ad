package ejercicios;

public class TresHilos extends Thread{
	
	private int num;

	public TresHilos(String nombre, int num) {
		this.num = num;
		this.setName(nombre);
		System.out.println("Creando " + this.getName());
		
	}

	public void run() {
		for (int i = 1; i <= num; i++)
			System.out.println("Hilo:" + this.getName() + " C=" + i);
	}
	
	public static void main(String[] args) {
		Hilo hilo1 = new Hilo("Hilo 1", 5);
		Hilo hilo2 = new Hilo("Hilo 2", 5);
		Hilo hilo3 = new Hilo("Hilo 3", 5);
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		
		System.out.println("3 Hilos iniciados...");
		
	}
}



