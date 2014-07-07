package supermercado;

/**
 * Roda a simulação da farmácia
 */

public class SimuladorFarmacia {
	
	/**
	 * Método que cria e executa a simulação
	 * 
	 * @throws Exception
	 * 				Proveniente da classe SimulacaoFarmacia
	 */
	public static void main(String[] args) throws Exception {
		
		SimulacaoFarmacia sim = new SimulacaoFarmacia(true);
		sim.simular();
		sim.imprimirResultados();
	}

}
