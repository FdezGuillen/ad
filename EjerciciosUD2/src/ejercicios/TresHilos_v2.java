package ejercicios;

public class TresHilos_v2 extends Thread{

	private int num;

	public TresHilos_v2(String nombre, int num) {
		this.num = num;
		this.setName(nombre);
		System.out.println("Creando " + this.getName());
		
	}

	public void run() {
		for (int i = 1; i <= num; i++)
			System.out.println("Hilo:" + this.getName() + " C=" + i);
	}
}