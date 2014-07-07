package supermercado;

/**
 * Roda a simula��o da farm�cia
 */

public class SimuladorFarmacia {
	
	/**
	 * M�todo que cria e executa a simula��o
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
