package supermercado;

/*
 * Programa principal da simulacao do supermercado.
 */
public class SimuladorSupermercado {
	public static void main(String[] args) throws Exception {
		Simulacao sim = new Simulacao(true);
		sim.simular();
		sim.imprimirResultados();
	}
}
