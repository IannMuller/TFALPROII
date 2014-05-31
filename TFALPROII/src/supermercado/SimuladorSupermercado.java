package supermercado;

/*
 * Programa principal da simulacao
 */
public class SimuladorSupermercado {
	public static void main(String[] args) {
		SimulaçaoFarmacia sim = new SimulaçaoFarmacia();
		sim.simular();
		sim.imprimirResultados();
	}
}
