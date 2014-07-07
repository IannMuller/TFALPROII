package supermercado;

/**
 * Roda a simula��o do supermercado
 */

public class SimuladorSupermercado {
	
	/**
	 * M�todo que cria e executa a simula��o
	 * 
	 * @throws Exception
	 * 				Proveniente da classe Simulacao
	 */
	public static void main(String[] args) throws Exception {
		Simulacao sim = new Simulacao(true);
		sim.simular();
		sim.imprimirResultados();
	}
}
