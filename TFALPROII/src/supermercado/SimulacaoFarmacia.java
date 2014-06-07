package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia {
	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */
	public class Simulacao {
		private int duracao;
		private double probabilidadeChegada;
		private QueueTAD<Cliente> fila;
		private Caixa caixa;
		private GeradorClientes geradorClientes;
		private Leitor leitor;
		private Balcao balcao;
		public Acumulador statTemposEsperaFila;
		public Acumulador statComprimentosFila;
		public QueueTAD<Cliente> filaB;
		public boolean trace; // valor indica se a simulacao ira imprimir

		// passo-a-passo os resultados

		public Simulacao(boolean t) throws IOException {
			leitor.getProp();
			String duracaoP = leitor.props.getProperty("duracao");
			String probabilidadeChegadaP = leitor.props.getProperty("probabilidadeChegada");
			duracao = Integer.parseInt(duracaoP);
			probabilidadeChegada = Integer.parseInt(probabilidadeChegadaP);
			fila = new QueueLinked<Cliente>();
			caixa = new Caixa();
			balcao = new Balcao();
			geradorClientes = new GeradorClientes(probabilidadeChegada);
			statTemposEsperaFila = new Acumulador();
			statComprimentosFila = new Acumulador();
			trace = t;
			filaB = new QueueLinked<Cliente>();
		}

		public void simular() throws Exception {
			// realizar a simulacao por um certo numero de passos de duracao
			for (int tempo = 0; tempo < duracao; tempo++) {
				// verificar se um cliente chegou
				if (geradorClientes.gerar()) {
					// se cliente chegou, criar um cliente e inserir na fila do
					// balcão
					Cliente c = new Cliente(
							geradorClientes.getQuantidadeGerada(), tempo);
					filaB.enqueue(c);
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero()
								+ " (" + c.getTempoAtendimento()
								+ " min) entra na fila do balcão - " + filaB.size()
								+ " pessoa(s)");
				}
				// verificar se o balcão esta vazio
				if (balcao.estaVazio()) {
					// se o balcão esta  vazio, atender o primeiro cliente da fila
					// se
					// ele existir
					if (!filaB.isEmpty()) {
						// tirar o cliente do inicio da fila e atender no balcão
						balcao.atenderCliente(filaB.dequeue());
						statTemposEsperaFila.adicionar(tempo
								- balcao.getClienteAtual().getInstanteChegada());
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " chega ao balcão.");
					}
				} else {
					// se o balcão ja esta ocupado, diminuir de um em um o tempo
					// de
					// atendimento ate chegar a zero
					if (balcao.getClienteAtual().getTempoAtendimento() == 0) {
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " deixa o balcao.");
						balcao.AddFilaCaixa();
						balcao.dispensarClienteAtual();
											} else {
						balcao.getClienteAtual().decrementarTempoAtendimento();
					}
				}
				statComprimentosFila.adicionar(filaB.size());
				
				if (trace)
					System.out.println(tempo + ": cliente " + c.getNumero()
							+ " (" + c.getTempoAtendimento()
							+ " min) entra na fila - " + fila.size()
							+ " pessoa(s)");
			
			}
		}

		public void limpar() {
			fila = new QueueLinked<Cliente>();
			filaB = new QueueLinked<Cliente>();
			balcao = new Balcao();
			caixa = new Caixa();
			geradorClientes = new GeradorClientes(probabilidadeChegada);
			statTemposEsperaFila = new Acumulador();
			statComprimentosFila = new Acumulador();
		}

	}
}
