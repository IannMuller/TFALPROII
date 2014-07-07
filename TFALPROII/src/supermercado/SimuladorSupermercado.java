package supermercado;

/**
 * Roda a simulação do supermercado
 */

public class SimuladorSupermercado {
	
	/**
	 * Método que cria e executa a simulação
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
