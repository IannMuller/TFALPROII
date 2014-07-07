package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia extends Simulacao {
	/*
	 * Classe com a logica da simulacao passo-a-passo
	 */

	private int duracao;
	private float probabilidadeChegada;
	private QueueTAD<Cliente> filaCaixa;
	private QueueTAD<Cliente> filaCaixaP;
	private QueueTAD<Cliente> filaBalcao;
	private QueueTAD<Cliente> filaBalcaoP;
	private Caixa caixa;
	private Caixa caixaP;
	private GeradorClientes geradorClientes;
	private Balcao balcao;
	private Balcao balcaoP;
	private Acumulador statTemposEsperaFila;
	private Acumulador statComprimentosFila;
	private Acumulador maiorBalcao1;
	private Acumulador maiorBalcao2;
	private Acumulador maiorCaixa1;
	private Acumulador maiorCaixa2;
	private Acumulador fila1;
	private Acumulador fila2;
	private Acumulador fila4;
	private Acumulador fila3;
	public static boolean trace = true; // valor indica se a simulacao ira
										// imprimir

	// passo-a-passo os resultados

	public SimulacaoFarmacia(boolean trace) throws IOException {
		super(trace);
		Leitor.getProps();

		duracao = Leitor.getDuracao();
		probabilidadeChegada = Leitor.getProbabilidade();
		filaCaixa = new QueueLinked<Cliente>();
		filaCaixaP = new QueueLinked<Cliente>();
		caixa = new Caixa();
		caixaP = new Caixa();
		balcao = new Balcao();
		balcaoP = new Balcao();
		geradorClientes = new GeradorClientes(probabilidadeChegada);
		statTemposEsperaFila = new Acumulador();
		statComprimentosFila = new Acumulador();
		maiorBalcao1 = new Acumulador();
		maiorBalcao2 = new Acumulador();
		maiorCaixa1 = new Acumulador();
		maiorCaixa2 = new Acumulador();
		fila1 = new Acumulador();
		fila2 = new Acumulador();
		fila3 = new Acumulador();
		fila4 = new Acumulador();
		filaBalcao = new QueueLinked<Cliente>();
		filaBalcaoP= new QueueLinked<Cliente>();
		
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

		for (int tempo = 0; tempo < duracao; tempo++) {
			
			/**
			 *Decide se irá criar um novo cliente
			 */
			if (geradorClientes.gerar()) {
				
				/**
				 * Cria o cliente.
				 */
				Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(), tempo);
				
				/**
				 * Decide para qual fila o cliente irá. Depende se ele é preferencial
				 * ou não.
				 */
				if (!c.isPreferencial()) {
					
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero() + " (" + c.getTempoAtendimento()
								+ " min) entra na fila do balcão - "
								+ filaBalcao.size() + " pessoa(s).");
					
					filaBalcao.enqueue(c);
					
				} else{
					
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero() + " (P)(" + c.getTempoAtendimento()
							+ " min) entra na fila do balcão preferencial - "
							+ filaBalcaoP.size() + " pessoa(s).");
					
					filaBalcaoP.enqueue(c);
					
				}
				
			}
			
			/**
			 * Testa se o balcão de atendimento COMUM está vazio.
			 * Se estiver e houver algum cliente na filaBalcao, ele vai ser atendido.
			 */
			if (balcao.estaVazio()) {
				
				/**
				 * Testa se há algum cliente na fila comum para ser atendido
				 */
				if (!filaBalcao.isEmpty()) {
					
					balcao.atenderCliente(filaBalcao.dequeue());
					statTemposEsperaFila.adicionar(tempo - balcao.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ balcao.getClienteAtual().getNumero()
								+ " chega ao balcão.");
								maiorBalcao1.aumentarContador();
								
					}else {
						
						/**
						 * Testa se há algum cliente na fila preferencial que pode ser atendido
						 */
						
						if (!filaBalcaoP.isEmpty() && !balcaoP.estaVazio()){
							
							balcao.atenderCliente(filaBalcaoP.dequeue());
							statTemposEsperaFila.adicionar(tempo - balcao.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ balcao.getClienteAtual().getNumero()
										+ " (P) chega ao balcão.");
										maiorBalcao1.aumentarContador();
							
						}
						
						//fila1.aumentarContador();
				}	
				
			}else {
				
				/**
				 * Caso o balcão comum não esteja está vazio, diminui-se o tempo de atendimento
				 * do cliente atual até zero e então manda-se ele para a fila do caixa
				 */
				if(balcao.getClienteAtual().getTempoAtendimento() == 0){
					
					balcao.getClienteAtual().modifyTempoAtendimento();
					
					/**
					 * Caso o cliente seja preferencial, ele é encaminhado para a fila do caixa respectivo
					 */
					if (balcao.getClienteAtual().isPreferencial()){
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " (P)("+balcao.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balcão e entra na fila do caixa preferencial - "+filaCaixaP.size()+" pessoa(s).");
						
						filaCaixaP.enqueue(balcao.dispensarClienteAtual());
					
					}else {
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " ("+balcao.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balcão e entra na fila do caixa - "+filaCaixa.size()+" pessoa(s).");
						
						filaCaixa.enqueue(balcao.dispensarClienteAtual());
						
					}
					
					
				}else {
					
					balcao.getClienteAtual().decrementarTempoAtendimento();
					
				}
				
			}
			
			/**
			 * Testa se o balcão de atendimento PREFERENCIAL está vazio.
			 * Se estiver e houver algum cliente na filaBalcaoP, ele vai ser atendido.
			 */
			if (balcaoP.estaVazio()) {
				
				/**
				 * Testa se há algum cliente na fila preferencial para ser atendido
				 */
				if (!filaBalcaoP.isEmpty()) {

				balcaoP.atenderCliente(filaBalcaoP.dequeue());
				statTemposEsperaFila.adicionar(tempo - balcaoP.getClienteAtual().getInstanteChegada());
				
					if (trace)
						System.out.println(tempo + ": cliente "
							+ balcaoP.getClienteAtual().getNumero()
							+ " (P) chega ao balcão preferencial.");
					
						maiorBalcao1.aumentarContador();	
				
				}else {
					
					/**
					 * Testa se há algum cliente na fila comum que pode ser atendido
					 */
					if (!filaBalcao.isEmpty() && !balcao.estaVazio()){
					
						balcaoP.atenderCliente(filaBalcao.dequeue());
						statTemposEsperaFila.adicionar(tempo - balcaoP.getClienteAtual().getInstanteChegada());
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+ " chega ao balcão preferencial.");
						
							maiorBalcao1.aumentarContador();
									
					}
						
						//fila2.aumentarContador();
					
				}
			
			}else {
				
				/**
				 * Caso o balcão preferencial não esteja vazio, diminui-se o tempo de atendimento
				 * do cliente atual até zero e então manda-se ele para a fila do caixa
				 */
				if(balcaoP.getClienteAtual().getTempoAtendimento() == 0){
					
					balcaoP.getClienteAtual().modifyTempoAtendimento();
					
					/**
					 * Caso o cliente seja preferencial, ele é encaminhado para a fila do caixa respectivo
					 */
					if(balcaoP.getClienteAtual().isPreferencial()){
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+ " (P)("+balcaoP.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balcão preferencial e entra na fila do caixa preferencial - "+filaCaixaP.size()+" pessoa(s).");
						
						filaCaixaP.enqueue(balcaoP.dispensarClienteAtual());
						
					} else{
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+  "("+balcaoP.getClienteAtual().getTempoAtendimento()
									+" min) deixa o balcão preferencial e entra na fila do caixa - "+filaCaixa.size()+" pessoa(s).");
						
						filaCaixa.enqueue(balcaoP.dispensarClienteAtual());
					}
					
				} else{
				
					balcaoP.getClienteAtual().decrementarTempoAtendimento();
					
				}
				
			}

			/**
			 * Testa se o caixa COMUM está vazio.
			 * Se estiver e houver algum cliente na fila ele será atendido
			 */
			if (caixa.estaVazio()) {

				if (!filaCaixa.isEmpty()) {

					caixa.atenderCliente(filaCaixa.dequeue());
					statTemposEsperaFila.adicionar(tempo - caixa.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ caixa.getClienteAtual().getNumero()
								+ " chega ao caixa.");
					
					maiorCaixa1.aumentarContador();
					
					}else {
						
						if (!filaCaixaP.isEmpty() && !caixaP.estaVazio()){
							
							caixa.atenderCliente(filaCaixaP.dequeue());
							statTemposEsperaFila.adicionar(tempo - caixa.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " (P) chega ao caixa.");
							
							maiorCaixa1.aumentarContador();
							
						}
						
						fila3.aumentarContador();
					}
				
				}else {
					
					/**
					 * Caso o caixa comum esteja ocupado, diminui-se o tempo de de atendimento
					 * do cliente atual até alcançar zero
					 */
					if(caixa.getClienteAtual().getTempoAtendimento() == 0){
					
						if(trace){
						
							if (caixa.getClienteAtual().isPreferencial())
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " (P) deixa o caixa.");
						
							else
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " deixa o caixa.");
						}
						
						caixa.dispensarClienteAtual();
						
					}else {
					
						caixa.getClienteAtual().decrementarTempoAtendimento();
					
					}
					
				}
			
			/**
			 * Testa se o caixa PREFERENCIAL está vazio.
			 * Se estiver e houver algum cliente na fila ele será atendido
			 */
			if (caixaP.estaVazio()) {

				if (!filaCaixaP.isEmpty()) {

					caixaP.atenderCliente(filaCaixaP.dequeue());
					statTemposEsperaFila.adicionar(tempo - caixaP.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ caixaP.getClienteAtual().getNumero()
								+ " (P) chega ao caixa preferencial.");
					
					maiorCaixa2.aumentarContador();
					
					}else {
						
						if (!filaCaixa.isEmpty() && !caixa.estaVazio()){
							
							caixaP.atenderCliente(filaCaixa.dequeue());
							statTemposEsperaFila.adicionar(tempo - caixaP.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " chega ao caixa preferencial.");
							
							maiorCaixa2.aumentarContador();
							
						}
						
						fila4.aumentarContador();
					}
				
				}else {
					
					/**
					 * Caso o caixa comum esteja ocupado, diminui-se o tempo de de atendimento
					 * do cliente atual até alcançar zero
					 */
					if(caixaP.getClienteAtual().getTempoAtendimento() == 0){
					
						if(trace){
						
							if (caixaP.getClienteAtual().isPreferencial())
								System.out.println(tempo + ": cliente "
										+ caixaP.getClienteAtual().getNumero()
										+ " (P) deixa o caixa preferencial.");
						
							else
								System.out.println(tempo + ": cliente "
										+ caixaP.getClienteAtual().getNumero()
										+ " deixa o caixa preferencial.");
						}
						
						caixaP.dispensarClienteAtual();
						
					}else {
					
						caixaP.getClienteAtual().decrementarTempoAtendimento();
					
					}
					
				}
			
			}
		
		}
			
			
	/**
	 * Reinicia os objetos.
	 */
	public void limpar() {
		filaCaixa = new QueueLinked<Cliente>();
		filaBalcao = new QueueLinked<Cliente>();
		filaBalcaoP = new QueueLinked<Cliente>();
		filaCaixaP = new QueueLinked<Cliente>();
		balcao = new Balcao();
		caixa = new Caixa();
		balcaoP = new Balcao();
		caixaP = new Caixa();
		geradorClientes = new GeradorClientes(probabilidadeChegada);
		statTemposEsperaFila = new Acumulador();
		statComprimentosFila = new Acumulador();
	}

	/**
	 * Imprime os resultados
	 */
	public void imprimirResultados() {
		
		double atendidos = balcao.getNumeroDeAtendidos() + balcaoP.getNumeroDeAtendidos() +
				caixa.getNumeroDeAtendidos() + caixaP.getNumeroDeAtendidos();
		
		System.out.println("\nResultados da Simulacao");
		System.out.println("Duracao: " + duracao+" min");
		System.out.println("Probabilidade de chegada de clientes: "
		+ probabilidadeChegada*100 + "%");
		System.out.println("Tempo de atendimento mínimo: "
		+ Leitor.getTempoMinAtendimento()+" min");
		System.out.println("Tempo de atendimento máximo: "
		+ Leitor.getTempoMaxAtendimento()+" min");
		
		
		System.out.println("\nEstatísticas de atendentes:");
		
		System.out.println("Clientes atendidos no caixa comum: " 
		+ caixa.getNumeroDeAtendidos()+" ("+caixa.getNumeroDeAtendidos()/atendidos*100+"%)");
		System.out.println("Clientes atendidos no caixa preferencial: "  
				+ caixaP.getNumeroDeAtendidos()+" ("+caixaP.getNumeroDeAtendidos()/atendidos*100+"%)");
		System.out.println("Cliente ainda no caixa comum: "
				+ (caixa.getClienteAtual() != null));
		System.out.println("Cliente ainda no caixa preferencial: "
				+ (caixaP.getClienteAtual() != null));
		
		System.out.println("Clintes atendidos no balcão comum: "
				+ balcao.getNumeroDeAtendidos()+" ("+balcao.getNumeroDeAtendidos()/atendidos*100+"%)");
		System.out.println("Clintes atendidos no balcão preferencial: "
				+ balcaoP.getNumeroDeAtendidos()+" ("+balcaoP.getNumeroDeAtendidos()/atendidos*100+"%)");
		System.out.println("Cliente ainda no balcao comum: "
				+ (balcao.getClienteAtual() != null));
		System.out.println("Cliente ainda no balcao preferencial: "
				+ (balcaoP.getClienteAtual() != null));
		
		System.out.println("\nEstatísticas de filas:");
		
		System.out.println("Clientes ainda na fila do caixa comum: "
				+ filaCaixa.size());
		System.out.println("Clientes ainda na fila do caixa preferencial: "
				+ filaCaixaP.size());
		System.out.println("Clientes ainda na fila do balcão comum: "
				+ filaBalcao.size());
		System.out.println("Clientes ainda na fila do balcão preferencial: "
				+ filaBalcaoP.size());
		
		System.out.println("Total de clientes gerados: "
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("Total de clientes atendidos: "
				+ atendidos);
		System.out.println("Tempo medio de espera: "
				+ statTemposEsperaFila.getMedia());
		System.out.println("Comprimento medio da fila: "
				+ statComprimentosFila.getMedia());
		System.out.println("Maior fila no balcão comum: "
				+ maiorBalcao1.getContagem());
		System.out.println("Maior fila no balcão preferencial: "
				+ maiorBalcao2.getContagem());
		System.out.println("Maior fila no caixa comum: "
				+ maiorCaixa1.getContagem());
		System.out.println("Maior fila no caixa preferencial: "
				+ maiorCaixa2.getContagem());
		System.out.println("Tempo com fila preferencial do balcão vazia: "
				+ fila1.getContagem());
		System.out.println("Tempo com fila comum do balcão vazia: "
				+ fila2.getContagem());
		System.out.println("Tempo com fila preferencial do caixa vazia: "
				+ fila3.getContagem());
		System.out.println("Tempo com fila comum do caixa vazia: "
				+ fila4.getContagem());
	}
}