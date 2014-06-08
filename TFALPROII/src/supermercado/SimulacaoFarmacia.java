package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SimulacaoFarmacia {
	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */

		private int duracao;
		private float probabilidadeChegada;
		private QueueTAD<Cliente> filaCaixa;
		private Caixa caixa;
		private GeradorClientes geradorClientes;
		private Balcao balcao;
		public Acumulador statTemposEsperaFila;
		public Acumulador statComprimentosFila;
		public QueueTAD<Cliente> filaB;
		public Cliente c;
		public boolean trace; // valor indica se a simulacao ira imprimir

		// passo-a-passo os resultados

		public SimulacaoFarmacia(boolean t) throws IOException {
			Properties props = new Properties();
			FileInputStream file = new FileInputStream("dados.properties");
			props.load(file);
			String duracaoP = props.getProperty("duracao");
			String probabilidadeChegadaP = props.getProperty("probabilidadeChegada");
			duracao = Integer.parseInt(duracaoP);
			probabilidadeChegada = Float.parseFloat(probabilidadeChegadaP);
			filaCaixa = new QueueLinked<Cliente>();
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
				c= new Cliente(geradorClientes.getQuantidadeGerada(), tempo);
				// verificar se um cliente chegou
				if (geradorClientes.gerar()) {
					// se cliente chegou, insere na fila do
					// balcão
					filaB.enqueue(c);
					if (trace && geradorClientes.gerar()==true)
						System.out.println(tempo + ": cliente " + c.getNumero()
								+ " (" + c.getTempoAtendimento()
								+ " min) entra na fila do balcão - "
								+ filaB.size() + " pessoa(s)");
				}
				// verificar se o balcão esta vazio
				if (balcao.estaVazio()) {
					// se o balcão esta vazio, atender o primeiro cliente da
					// fila
					// se
					// ele existir
					if (!filaB.isEmpty()) {
						// tirar o cliente do inicio da fila e atender no balcão
						balcao.atenderCliente(filaB.dequeue());
						statTemposEsperaFila.adicionar(tempo- balcao.getClienteAtual().getInstanteChegada());
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
						filaCaixa.enqueue(c);
						System.out.println(tempo + ": cliente " + c.getNumeroAux()
									+ " (" + c.getTempoAtendimento()
									+ " min) entra na fila do caixa - " + filaCaixa.size()
									+ " pessoa(s)");
						balcao.dispensarClienteAtual();
											} else {
						balcao.getClienteAtual().decrementarTempoAtendimento();
					}
				}
				statComprimentosFila.adicionar(filaB.size());

			
				// verificar se o caixa esta vazio
				if (caixa.estaVazio()) {
					// se o caixa esta vazio, atender o primeiro cliente da fila
					// se
					// ele existir
					if (!filaCaixa.isEmpty()) {
						// tirar o cliente do inicio da fila e atender no caixa
						caixa.atenderCliente(filaCaixa.dequeue());
						statTemposEsperaFila.adicionar(tempo
								- caixa.getClienteAtual().getInstanteChegada());
						if (trace)
							System.out.println(tempo + ": cliente "
									+ caixa.getClienteAtual().getNumero()
									+ " chega ao caixa.");
					} else {
						// se o caixa ja esta ocupado, diminuir de um em um o
						// tempo de
						// atendimento ate chegar a zero
						if (c.getTempoAtendimento() == 0) {
							if (trace)
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " deixa o caixa.");
							caixa.dispensarClienteAtual();
						} else {
							
									c.decrementarTempoAtendimento();
						}
					}
					statComprimentosFila.adicionar(filaCaixa.size());
				}
			}
		}

		public void limpar() {
			filaCaixa = new QueueLinked<Cliente>();
			filaB = new QueueLinked<Cliente>();
			balcao = new Balcao();
			caixa = new Caixa();
			geradorClientes = new GeradorClientes(probabilidadeChegada);
			statTemposEsperaFila = new Acumulador();
			statComprimentosFila = new Acumulador();
		}



	public void imprimirResultados() {
		System.out.println();
		System.out.println("Resultados da Simulacao");
		System.out.println("Duracao:" + duracao);
		System.out.println("Probabilidade de chegada de clientes:"
				+ probabilidadeChegada);
		System.out.println("Tempo de atendimento minimo:"
				+ Cliente.tempoMinAtendimento);
		System.out.println("Tempo de atendimento maximo:"
				+ Cliente.tempoMaxAtendimento);
		System.out.println("Clientes atendidos no caixa:" + caixa.getNumeroAtendidos());
		System.out.println("Clientes ainda na fila do caixa:" + filaCaixa.size());
		System.out.println("Cliente ainda no caixa:"
				+ (caixa.getClienteAtual() != null));
		System.out.println("Clintes atendidos no balcão:" + balcao.getNumeroDeAtendidos());
		System.out.println("Clientes ainda na fila do balcão:" + filaB.size());
		System.out.println("Total de clientes gerados:"
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("Tempo medio de espera:"
				+ statTemposEsperaFila.getMedia());
		System.out.println("Comprimento medio da fila:"
				+ statComprimentosFila.getMedia());
	}
}