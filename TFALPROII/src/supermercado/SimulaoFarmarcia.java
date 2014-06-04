
public class SimulaoFarmarcia {
	package supermercado;

	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */
	public class Simulacao {
		protected static int duracao;
		protected static double probabilidadeChegada;
		protected QueueTAD<Cliente> fila;
		protected Caixa caixa;
		protected GeradorClientes geradorClientes;
		public Acumulador statTemposEsperaFila;
		public Acumulador statComprimentosFila;
		public static boolean trace; // valor indica se a simulacao ira imprimir
								// passo-a-passo os resultados

		public Simulacao(boolean t) {
			fila = new QueueLinked<Cliente>();
			caixa = new Caixa();
			geradorClientes = new GeradorClientes(probabilidadeChegada);
			statTemposEsperaFila = new Acumulador();
			statComprimentosFila = new Acumulador();
			trace = t;
		}

		public void simular() {
			// realizar a simulacao por um certo numero de passos de duracao
			for (int tempo = 0; tempo < duracao; tempo++) {
				// verificar se um cliente chegou
				if (geradorClientes.gerar()) {
					// se cliente chegou, criar um cliente e inserir na fila do
					// caixa
					Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(),
							tempo);
					fila.enqueue(c);
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero()
								+ " (" + c.getTempoAtendimento()
								+ " min) entra na fila - " + fila.size()
								+ " pessoa(s)");
				}
				// verificar se o caixa esta vazio
				if (caixa.estaVazio()) {
					// se o caixa esta vazio, atender o primeiro cliente da fila se
					// ele existir
					if (!fila.isEmpty()) {
						// tirar o cliente do inicio da fila e atender no caixa
						caixa.atenderNovoCliente(fila.dequeue());
						statTemposEsperaFila.adicionar(tempo
								- caixa.getClienteAtual().getInstanteChegada());
						if (trace)
							System.out.println(tempo + ": cliente "
									+ caixa.getClienteAtual().getNumero()
									+ " chega ao caixa.");
					}
				} else {
					// se o caixa ja esta ocupado, diminuir de um em um o tempo de
					// atendimento ate chegar a zero
					if (caixa.getClienteAtual().getTempoAtendimento() == 0) {
						if (trace)
							System.out.println(tempo + ": cliente "
									+ caixa.getClienteAtual().getNumero()
									+ " deixa o caixa.");
						caixa.dispensarClienteAtual();
					} else {
						caixa.getClienteAtual().decrementarTempoAtendimento();
					}
				}
				statComprimentosFila.adicionar(fila.size());
			}
		}

		public void limpar() {
			fila = new QueueLinked<Cliente>();
			caixa = new Caixa();
			geradorClientes = new GeradorClientes(probabilidadeChegada);
			statTemposEsperaFila = new Acumulador();
			statComprimentosFila = new Acumulador();
		}

}
