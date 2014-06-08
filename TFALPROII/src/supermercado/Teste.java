package supermercado;

public class Teste {
static Leitor leitor;
	public static void main(String[] args) throws Exception {
		
		SimulacaoFarmacia sim = new SimulacaoFarmacia(true);
		sim.simular();
		sim.imprimirResultados();
	}

}
