package ejercicios;

public class Hilo2 extends Thread{

	public void run() {
		for (int i = 1; i <= 10; i++)
			if (i%2 != 0)
				System.out.println("Nº" + i);
	}

}
