package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia extends Simulacao {
	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */

	private int duracao;
	private float probabilidadeChegada;
	private QueueTAD<Cliente> filaCaixa;
	private Caixa caixa;
	private GeradorClientes geradorClientes;
	private Balcao balcao;
	private QueueTAD<Cliente> filaB;
	public Acumulador statTemposEsperaFila;
	public Acumulador statComprimentosFila;
	public static boolean trace = true; // valor indica se a simulacao ira
										// imprimir

	// passo-a-passo os resultados

	public SimulacaoFarmacia(boolean trace) throws IOException {
		super(trace);
		Leitor.getProps();

		duracao = Leitor.getDuracao();
		probabilidadeChegada = Leitor.getProbabilidade();
		filaCaixa = new QueueLinked<Cliente>();
		caixa = new Caixa();
		balcao = new Balcao();
		geradorClientes = new GeradorClientes(probabilidadeChegada);
		statTemposEsperaFila = new Acumulador();
		statComprimentosFila = new Acumulador();
		filaB = new QueueLinked<Cliente>();
	}

	/**
	 * Método responsavel pela simulação. Cria uma excessão quando algum objeto
	 * receber null.
	 * 
	 * @throws Exception
	 *             Se alguma parte das listas estiverem null retorna
	 *             NullPointerException.
	 */
	public void simular() throws Exception {
		// realizar a simulacao por um certo numero de passos de duracao
		for (int tempo = 0; tempo < duracao; tempo++) {
			// verificar se um cliente chegou
			if (geradorClientes.gerar()) {
				// se cliente chegou, insere na fila do
				// balcão
				Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(),
						tempo);
				filaB.enqueue(c);
				if (trace)
					System.out.println(tempo + ": cliente " + c.getNumero()
							+ " (" + c.getTempoAtendimento()
							+ " min) entra na fila do balcão - " + filaB.size()
							+ " pessoa(s)");
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

					balcao.getClienteAtual().modifyTempoAtendimento();
					filaCaixa.enqueue(balcao.getClienteAtual());

					if (trace)
						System.out.println(tempo
								+ ": cliente "
								+ balcao.getClienteAtual().getNumero()
								+ " ("
								+ balcao.getClienteAtual()
										.getTempoAtendimento()
								+ " min) entra na fila do caixa - "
								+ filaCaixa.size() + " pessoa(s)");

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
				}
			} else {
				// se o caixa ja esta ocupado, diminuir de um em um o tempo
				// de
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
			statComprimentosFila.adicionar(filaCaixa.size());
		}

	}

	/**
	 * Reinicia os objetos.
	 */
	public void limpar() {
		filaCaixa = new QueueLinked<Cliente>();
		filaB = new QueueLinked<Cliente>();
		balcao = new Balcao();
		caixa = new Caixa();
		geradorClientes = new GeradorClientes(probabilidadeChegada);
		statTemposEsperaFila = new Acumulador();
		statComprimentosFila = new Acumulador();
	}

	/**
	 * Imprime os resultados
	 */
	public void imprimirResultados() {
		System.out.println();
		System.out.println("Resultados da Simulacao");
		System.out.println("Duracao:" + duracao);
		System.out.println("Probabilidade de chegada de clientes:"
				+ probabilidadeChegada);
		System.out.println("Tempo de atendimento minimo:"
				+ Leitor.getTempoMinAtendimento());
		System.out.println("Tempo de atendimento maximo:"
				+ Leitor.getTempoMaxAtendimento());
		System.out.println("Clientes atendidos no caixa:"
				+ caixa.getNumeroAtendidos());
		System.out.println("Clientes ainda na fila do caixa:"
				+ filaCaixa.size());
		System.out.println("Cliente ainda no caixa:"
				+ (caixa.getClienteAtual() != null));
		System.out.println("Clintes atendidos no balcão:"
				+ balcao.getNumeroDeAtendidos());
		System.out.println("Clientes ainda na fila do balcão:" + filaB.size());
		System.out.println("Total de clientes gerados:"
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("Tempo medio de espera:"
				+ statTemposEsperaFila.getMedia());
		System.out.println("Comprimento medio da fila:"
				+ statComprimentosFila.getMedia());
	}
}