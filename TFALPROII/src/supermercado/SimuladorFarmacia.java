package supermercado;

public class SimuladorFarmacia {

	public static void main(String[] args) throws Exception {
		
		SimulacaoFarmacia sim = new SimulacaoFarmacia(true);
		sim.simular();
		sim.imprimirResultados();
	}

}
