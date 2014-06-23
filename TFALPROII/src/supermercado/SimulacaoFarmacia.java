package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia extends Simulacao {
	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */

	private int duracao;
	private float probabilidadeChegada;
	private QueueTAD<Cliente> filaCaixa;
	private QueueTAD<Cliente> filaCaixaPreferencial;
	private Caixa caixa1;
	private Caixa caixa2;
	private GeradorClientes geradorClientes;
	private Balcao balcao1;
	private Balcao balcao2;
	private QueueTAD<Cliente> filaBalcao;
	private QueueTAD<Cliente> filaBalcaoPreferencial;
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
		filaCaixaPreferencial = new QueueLinked<Cliente>();
		caixa1 = new Caixa();
		caixa2 = new Caixa();
		balcao1 = new Balcao();
		balcao2 = new Balcao();
		geradorClientes = new GeradorClientes(probabilidadeChegada);
		statTemposEsperaFila = new Acumulador();
		statComprimentosFila = new Acumulador();
		filaBalcao = new QueueLinked<Cliente>();
		filaBalcaoPreferencial = new QueueLinked<Cliente>();
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
				if (c.getNumeroPreferencia() < 35) {
					filaBalcao.enqueue(c);
					if (trace)
						System.out.println(tempo + " cliente " + c.getNumero() + " (" + c.getTempoAtendimento()
								+ " min) entra na fila do balcão - "
								+ filaBalcao.size() + " pessoa(s)");
				} 
				if(c.getNumeroPreferencia()>=35) {
					filaBalcaoPreferencial.enqueue(c);
				
				if (trace)
					System.out.println(tempo + " cliente preferencial " + c.getNumero() + " (" + c.getTempoAtendimento()
							+ " min) entra na fila do balcão - "
							+ filaBalcaoPreferencial.size() + " pessoa(s)");
				}
			}
			// verificar se o balcão esta vazio
			if (balcao1.estaVazio()) {
				// se o balcão esta vazio, atender o primeiro cliente da
				// fila
				// se
				// ele existir
				if (!filaBalcaoPreferencial.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					balcao1.atenderCliente(filaBalcaoPreferencial.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- balcao1.getClienteAtual().getInstanteChegada());
					if (trace)
						System.out.println(tempo + " cliente preferencial "
								+ balcao1.getClienteAtual().getNumero()
								+ " chega ao balcão");
					}
				
				if (balcao1.estaVazio()) {
					if (!filaBalcao.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					balcao1.atenderCliente(filaBalcao.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- balcao1.getClienteAtual().getInstanteChegada());
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao1.getClienteAtual().getNumero()
								+ " chega ao balcão.");
						}
					}
				}
			  if (balcao2.estaVazio()) {
				// se o balcão esta vazio, atender o primeiro cliente da
				// fila
				// se
				// ele existir
				if (!filaBalcaoPreferencial.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					balcao2.atenderCliente(filaBalcaoPreferencial.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- balcao2.getClienteAtual().getInstanteChegada());
					if (trace)
						System.out.println(tempo + " cliente preferencial "
								+ balcao2.getClienteAtual().getNumero()
								+ " chega ao balcão.");
					}
				
				if (balcao2.estaVazio()&&balcao1.estaVazio()==false) {
					if (!filaBalcao.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					balcao2.atenderCliente(filaBalcao.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- balcao2.getClienteAtual().getInstanteChegada());
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao2.getClienteAtual().getNumero()
								+ " chega ao balcão.");
						}
					}
				}
			   if( balcao1.estaVazio()==false) {
				// se o balcão ja esta ocupado, diminuir de um em um o tempo
				// de
				// atendimento ate chegar a zero
				if (balcao1.getClienteAtual().getTempoAtendimento() == 0) {
					if(balcao1.getClienteAtual().getNumeroPreferencia()<35){
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao1.getClienteAtual().getNumero()
								+ " deixa o balcao.");
					}
					if(balcao1.getClienteAtual().getNumeroPreferencia()>=35){
						if (trace)
							System.out.println(tempo + " cliente preferencial "
								+ balcao1.getClienteAtual().getNumero()
								+ " deixa o balcao.");
					}
					balcao1.getClienteAtual().modifyTempoAtendimento();
					if (balcao1.getClienteAtual().getNumeroPreferencia() < 35) {
						filaCaixa.enqueue(balcao1.getClienteAtual());
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao1.getClienteAtual().getNumero()
								+ " (" + balcao1.getClienteAtual().getTempoAtendimento()
								+ " min) entra na fila do caixa - " + filaCaixa.size() + " pessoa(s)");
					}
					if (balcao1.getClienteAtual().getNumeroPreferencia() >= 35) {
						filaCaixaPreferencial.enqueue(balcao1.getClienteAtual());
						if (trace)
							System.out.println(tempo + " cliente preferencial "
								+ balcao1.getClienteAtual().getNumero()
								+ " (" + balcao1.getClienteAtual().getTempoAtendimento()
								+ " min) entra na fila do caixa - " + filaCaixaPreferencial.size() + " pessoa(s)");
					}
					balcao1.dispensarClienteAtual();
				} 
				else {
					balcao1.getClienteAtual().decrementarTempoAtendimento();
				}
			 }
			   if( balcao2.estaVazio()==false)
			   if ( balcao2.getClienteAtual().getTempoAtendimento() == 0) {
					if( balcao2.getClienteAtual().getNumeroPreferencia()<35){
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao2.getClienteAtual().getNumero()
								+ " deixa o balcao.");
					}
					if(balcao2.getClienteAtual().getNumeroPreferencia()>=35){
						if (trace)
							System.out.println(tempo + " cliente preferencial "
								+ balcao2.getClienteAtual().getNumero()
								+ " deixa o balcao.");
					}
					balcao2.getClienteAtual().modifyTempoAtendimento();
					if (balcao2.getClienteAtual().getNumeroPreferencia() < 35) {
						filaCaixa.enqueue(balcao2.getClienteAtual());
						if (trace)
							System.out.println(tempo + " cliente "
								+ balcao2.getClienteAtual().getNumero()
								+ " (" + balcao2.getClienteAtual().getTempoAtendimento()
								+ " min) entra na fila do caixa - " + filaCaixa.size() + " pessoa(s)");
					}
					if (balcao2.getClienteAtual().getNumeroPreferencia() >= 35) {
						filaCaixaPreferencial.enqueue(balcao2.getClienteAtual());
						if (trace)
							System.out.println(tempo + " cliente preferencial "
								+ balcao2.getClienteAtual().getNumero()
								+ " (" + balcao2.getClienteAtual().getTempoAtendimento()
								+ " min) entra na fila do caixa - " + filaCaixaPreferencial.size() + " pessoa(s)");
					}
					balcao2.dispensarClienteAtual();
				} 
				else {
					balcao2.getClienteAtual().decrementarTempoAtendimento();
					}
			statComprimentosFila.adicionar(filaBalcao.size());
			
			// verificar se o caixa esta vazio
			if (caixa1.estaVazio()) {
				// se o caixa esta vazio, atender o primeiro cliente da fila
				// se
				// ele existir
				if (!filaCaixaPreferencial.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					caixa1.atenderCliente(filaCaixaPreferencial.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- caixa1.getClienteAtual().getInstanteChegada());
					if (trace)
						System.out.println(tempo + " cliente preferencial "
								+ caixa1.getClienteAtual().getNumero()
								+ " chega ao caixa ");
					}
				
				if (caixa1.estaVazio()){
					if (!filaCaixa.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no caixa
					caixa1.atenderCliente(filaCaixa.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- caixa1.getClienteAtual().getInstanteChegada());
						if (trace)
						System.out.println(tempo + " cliente "
								+ caixa1.getClienteAtual().getNumero() + " chega ao caixa.");
						}
					}
				}
			if (caixa2.estaVazio()&&caixa1.estaVazio()==false) {
				// se o caixa esta vazio, atender o primeiro cliente da fila
				// se
				// ele existir
				if (!filaCaixaPreferencial.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no balcão
					caixa2.atenderCliente(filaCaixaPreferencial.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- caixa2.getClienteAtual().getInstanteChegada());
					if (trace)
						System.out.println(tempo + " cliente preferencial "
								+ caixa2.getClienteAtual().getNumero()
								+ " chega ao caixa ");
					}
				
				if (caixa2.estaVazio()){
					if (!filaCaixa.isEmpty()) {
					// tirar o cliente do inicio da fila e atender no caixa
					caixa2.atenderCliente(filaCaixa.dequeue());
					statTemposEsperaFila.adicionar(tempo
							- caixa2.getClienteAtual().getInstanteChegada());
						if (trace)
						System.out.println(tempo + " cliente "
								+ caixa2.getClienteAtual().getNumero() + " chega ao caixa.");
						}
					}
				}
			    else {
				// se o caixa ja esta ocupado, diminuir de um em um o tempo
				// de
				// atendimento ate chegar a zero
				if (caixa1.getClienteAtual().getTempoAtendimento() == 0) {
					if(caixa1.getClienteAtual().getNumeroPreferencia()<35){
					if (trace)
						System.out.println(tempo  + " cliente " + caixa1.getClienteAtual().getNumero()
							+ " deixa o caixa.");
						}
					if(caixa1.getClienteAtual().getNumeroPreferencia()>=35){
						if (trace)
							System.out.println(tempo + " cliente " + caixa1.getClienteAtual().getNumero()
								+ " deixa o caixa preferencial.");
						}
						caixa1.dispensarClienteAtual();
					}
				else {

					caixa1.getClienteAtual().decrementarTempoAtendimento();
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
		filaBalcao = new QueueLinked<Cliente>();
		filaBalcaoPreferencial = new QueueLinked<Cliente>();
		filaCaixaPreferencial = new QueueLinked<Cliente>();
		balcao1 = new Balcao();
		caixa1 = new Caixa();
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
		+ caixa1.getNumeroAtendidos());
		System.out.println("Clientes ainda na fila do caixa:"
				+ filaCaixa.size());
		System.out.println("Cliente ainda no caixa:"
				+ (caixa1.getClienteAtual() != null));
		System.out.println("Clintes atendidos no balcão:"
				+ balcao1.getNumeroDeAtendidos());
		System.out.println("Clientes ainda na fila do balcão:"
				+ filaBalcao.size());
		System.out.println("Total de clientes gerados:"
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("Tempo medio de espera:"
				+ statTemposEsperaFila.getMedia());
		System.out.println("Comprimento medio da fila:"
				+ statComprimentosFila.getMedia());
	}
}