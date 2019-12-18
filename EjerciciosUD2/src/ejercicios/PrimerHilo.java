package ejercicios;

public class PrimerHilo extends Thread{

	private int num;
	
	public PrimerHilo(int num) {
		this.num = num;
	}
	public void run() {
		for (int i = 1; i <= num; i++)
			System.out.println("En el hilo... " + i);
	}
	
}
