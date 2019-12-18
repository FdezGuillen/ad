package ejercicios;

public class PrimerHiloStop extends Thread {

	private boolean vivo;

	public PrimerHiloStop() {
		this.vivo = true;
	}

	public void pararHilo() {
		vivo = false;
	}

	public void run() {
		int cont = 1;
		while (vivo) {
			System.out.println("Este hilo se detendrá cuando el contador llegue a 2");
			System.out.println("Valor de contador: " + cont);
			cont++;
			if (cont > 2) {
				pararHilo();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		PrimerHiloStop hilo1 = new PrimerHiloStop();
		hilo1.start();
		hilo1.join();
		System.out.println(hilo1.getState());
	}
}
