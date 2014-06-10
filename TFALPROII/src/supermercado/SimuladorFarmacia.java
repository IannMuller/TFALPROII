package supermercado;

public class SimuladorFarmacia {
	/*
	 * Programa principal da simulacao da farmacia.
	 */
	public static void main(String[] args) throws Exception {
		
		SimulacaoFarmacia sim = new SimulacaoFarmacia(true);
		sim.simular();
		sim.imprimirResultados();
	}

}
