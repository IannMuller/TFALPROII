package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia extends Simulacao {
	
	/**Tempo de dura��o da simula��o*/
	private int duracao;
	/**Probabilidade de chegar um cliente*/
	private float probabilidadeChegada;
	/**Respons�vel por gerar clientes*/
	private GeradorClientes geradorClientes;
	/**Fila de clientes para o caixa*/
	private QueueTAD<Cliente> filaCaixa;
	/**Fila de clientes para o caixa preferencial*/
	private QueueTAD<Cliente> filaCaixaP;
	/**Fila de clientes para o balc�o*/
	private QueueTAD<Cliente> filaBalcao;
	/**Fila de clientes para o balc�o preferencial*/
	private QueueTAD<Cliente> filaBalcaoP;
	/**Caixa de atendimento*/
	private Caixa caixa;
	/**Caixa de atendimento preferencial*/
	private Caixa caixaP;
	/**Balc�o de atendimento*/
	private Balcao balcao;
	/**Balc�o de atendimento preferencial*/
	private Balcao balcaoP;
	/**Tempo m�dio de espera em todas filas*/
	private Acumulador statTemposEsperaFila;
	/**Tamanho m�dio de todas filas*/
	private Acumulador statComprimentoFilaBalcao;
	private Acumulador statComprimentoFilaBalcaoP;
	private Acumulador statComprimentoFilaCaixa;
	private Acumulador statComprimentoFilaCaixaP;
	/**Tempo em que as filas ficam vaizas*/
	private int filaBalcaoEmp;
	private int filaBalcaoPEmp;
	private int filaCaixaEmp;
	private int filaCaixaPEmp;
	/**Define se a simula��o deve ser impressa na tela*/
	public static boolean trace = true;
	
	/**
	 * Construtor da classe
	 * 
	 * @param trace
	 * @throws IOException
	 * 				Proveniente da classe "Leitor"
	 */
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
		statComprimentoFilaBalcao = new Acumulador();
		statComprimentoFilaBalcaoP = new Acumulador();
		statComprimentoFilaCaixa = new Acumulador();
		statComprimentoFilaCaixaP = new Acumulador();
		filaBalcao = new QueueLinked<Cliente>();
		filaBalcaoP= new QueueLinked<Cliente>();
		
	}

	/**
	 * Simula o tr�fego de clientes em uma farm�cia
	 * 
	 * @throws Exception
	 *             Se alguma parte das listas estiverem null retorna
	 *             NullPointerException.
	 */
	public void simular() throws Exception {

		for (int tempo = 0; tempo < duracao; tempo++) {
			
			/**
			 *Decide se ir� criar um novo cliente
			 */
			if (geradorClientes.gerar()) {
				
				/**
				 * Cria o cliente.
				 */
				Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(), tempo);
				
				/**
				 * Decide para qual fila o cliente ir�. Depende se ele � preferencial
				 * ou n�o.
				 */
				if (!c.isPreferencial()) {
					
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero() + " (" + c.getTempoAtendimento()
								+ " min) entra na fila do balc�o - "
								+ filaBalcao.size() + " pessoa(s).");
					
					filaBalcao.enqueue(c);
					
				} else{
					
					if (trace)
						System.out.println(tempo + ": cliente " + c.getNumero() + " (P)(" + c.getTempoAtendimento()
							+ " min) entra na fila do balc�o preferencial - "
							+ filaBalcaoP.size() + " pessoa(s).");
					
					filaBalcaoP.enqueue(c);
					
				}
					
			}
			
			/**
			 * Testa se o balc�o de atendimento COMUM est� vazio.
			 * Se estiver e houver algum cliente na "filaBalcao", ele vai ser atendido.
			 */
			if (balcao.estaVazio()) {
				
				/**
				 * Testa se h� algum cliente na fila comum para ser atendido
				 */
				if (!filaBalcao.isEmpty()) {
					
					balcao.atenderCliente(filaBalcao.dequeue());
					statTemposEsperaFila.adicionar(tempo - balcao.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ balcao.getClienteAtual().getNumero()
								+ " chega ao balc�o.");
								
					}else {
						
						/**
						 * Testa se h� algum cliente na fila preferencial que pode ser atendido
						 */
						
						if (!filaBalcaoP.isEmpty() && !balcaoP.estaVazio()){
							
							balcao.atenderCliente(filaBalcaoP.dequeue());
							statTemposEsperaFila.adicionar(tempo - balcao.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ balcao.getClienteAtual().getNumero()
										+ " (P) chega ao balc�o.");
							
						}
						
						//fila1.aumentarContador();
				}	
				
			}else {
				
				/**
				 * Caso o balc�o comum n�o esteja est� vazio, diminui-se o tempo de atendimento
				 * do cliente atual at� zero e ent�o manda-se ele para a fila do caixa
				 */
				if(balcao.getClienteAtual().getTempoAtendimento() == 0){
					
					balcao.getClienteAtual().modifyTempoAtendimento();
					
					/**
					 * Caso o cliente seja preferencial, ele � encaminhado para a fila do caixa respectivo
					 */
					if (balcao.getClienteAtual().isPreferencial()){
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " (P)("+balcao.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balc�o e entra na fila do caixa preferencial - "+filaCaixaP.size()+" pessoa(s).");
						
						filaCaixaP.enqueue(balcao.dispensarClienteAtual());
					
					}else {
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcao.getClienteAtual().getNumero()
									+ " ("+balcao.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balc�o e entra na fila do caixa - "+filaCaixa.size()+" pessoa(s).");
						
						filaCaixa.enqueue(balcao.dispensarClienteAtual());
						
					}
					
					
				}else {
					
					balcao.getClienteAtual().decrementarTempoAtendimento();
					
				}
				
			}
			
			/**
			 * Testa se o balc�o de atendimento PREFERENCIAL est� vazio.
			 * Se estiver e houver algum cliente na "filaBalcaoP", ele vai ser atendido.
			 */
			if (balcaoP.estaVazio()) {
				
				/**
				 * Testa se h� algum cliente na fila preferencial para ser atendido
				 */
				if (!filaBalcaoP.isEmpty()) {

				balcaoP.atenderCliente(filaBalcaoP.dequeue());
				statTemposEsperaFila.adicionar(tempo - balcaoP.getClienteAtual().getInstanteChegada());
				
					if (trace)
						System.out.println(tempo + ": cliente "
							+ balcaoP.getClienteAtual().getNumero()
							+ " (P) chega ao balc�o preferencial.");
				
				}else {
					
					/**
					 * Testa se h� algum cliente na fila comum que pode ser atendido
					 */
					if (!filaBalcao.isEmpty() && !balcao.estaVazio()){
					
						balcaoP.atenderCliente(filaBalcao.dequeue());
						statTemposEsperaFila.adicionar(tempo - balcaoP.getClienteAtual().getInstanteChegada());
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+ " chega ao balc�o preferencial.");
									
					}
						
											
				}
			
			}else {
				
				/**
				 * Caso o balc�o preferencial n�o esteja vazio, diminui-se o tempo de atendimento
				 * do cliente atual at� zero e ent�o manda-se ele para a fila do caixa
				 */
				if(balcaoP.getClienteAtual().getTempoAtendimento() == 0){
					
					balcaoP.getClienteAtual().modifyTempoAtendimento();
					
					/**
					 * Caso o cliente seja preferencial, ele � encaminhado para a fila do caixa respectivo
					 */
					if(balcaoP.getClienteAtual().isPreferencial()){
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+ " (P)("+balcaoP.getClienteAtual().getTempoAtendimento()
									+ " min) deixa o balc�o preferencial e entra na fila do caixa preferencial - "+filaCaixaP.size()+" pessoa(s).");
						
						filaCaixaP.enqueue(balcaoP.dispensarClienteAtual());
						
					} else{
						
						if (trace)
							System.out.println(tempo + ": cliente "
									+ balcaoP.getClienteAtual().getNumero()
									+  " ("+balcaoP.getClienteAtual().getTempoAtendimento()
									+" min) deixa o balc�o preferencial e entra na fila do caixa - "+filaCaixa.size()+" pessoa(s).");
						
						filaCaixa.enqueue(balcaoP.dispensarClienteAtual());
					}
					
				} else{
				
					balcaoP.getClienteAtual().decrementarTempoAtendimento();
					
				}
				
			}

			//=================================================================================================\\
			
			/**
			 * Testa se o caixa COMUM est� vazio.
			 * Se estiver e houver algum cliente na fila ele ser� atendido
			 */
			if (caixa.estaVazio()) {

				if (!filaCaixa.isEmpty()) {

					caixa.atenderCliente(filaCaixa.dequeue());
					statTemposEsperaFila.adicionar(tempo - caixa.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ caixa.getClienteAtual().getNumero()
								+ " chega ao caixa.");
					
					}else {
						
						/**
						 * Testa se h� algum cliente na fila preferencial para ser atendido
						 */
						if (!filaCaixaP.isEmpty() && !caixaP.estaVazio()){
							
							caixa.atenderCliente(filaCaixaP.dequeue());
							statTemposEsperaFila.adicionar(tempo - caixa.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ caixa.getClienteAtual().getNumero()
										+ " (P) chega ao caixa.");
							
						}
						
						statComprimentoFilaCaixa.aumentarContador();
					}
				
				}else {
					
					/**
					 * Caso o caixa comum esteja ocupado, diminui-se o tempo de de atendimento
					 * do cliente atual at� alcan�ar zero
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
			 * Testa se o caixa PREFERENCIAL est� vazio.
			 * Se estiver e houver algum cliente na fila ele ser� atendido
			 */
			if (caixaP.estaVazio()) {

				if (!filaCaixaP.isEmpty()) {

					caixaP.atenderCliente(filaCaixaP.dequeue());
					statTemposEsperaFila.adicionar(tempo - caixaP.getClienteAtual().getInstanteChegada());
					
					if (trace)
						System.out.println(tempo + ": cliente "
								+ caixaP.getClienteAtual().getNumero()
								+ " (P) chega ao caixa preferencial.");
					
					}else {
						
						/**
						 * Testa se h� algum cliente na fila comum para ser atendido
						*/
						if (!filaCaixa.isEmpty() && !caixa.estaVazio()){
							
							caixaP.atenderCliente(filaCaixa.dequeue());
							statTemposEsperaFila.adicionar(tempo - caixaP.getClienteAtual().getInstanteChegada());
							
							if (trace)
								System.out.println(tempo + ": cliente "
										+ caixaP.getClienteAtual().getNumero()
										+ " chega ao caixa preferencial.");
							
						}
						
						statComprimentoFilaCaixaP.aumentarContador();
					}
				
				}else {
					
					/**
					 * Caso o caixa preferencial esteja ocupado, diminui-se o tempo de de atendimento
					 * do cliente atual at� alcan�ar zero
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
			
			statComprimentoFilaBalcao.adicionar(filaBalcao.size());
			statComprimentoFilaBalcaoP.adicionar(filaBalcaoP.size());
			statComprimentoFilaCaixa.adicionar(filaCaixa.size());
			statComprimentoFilaCaixaP.adicionar(filaCaixaP.size());
				if(filaBalcao.isEmpty()){
				filaBalcaoEmp++;
				}
				if(filaBalcaoP.isEmpty()){
				filaBalcaoPEmp++;
				}
				if(filaCaixa.isEmpty()){
					filaCaixaEmp++;
				}
				if(filaCaixaP.isEmpty()){
					filaCaixaPEmp++;
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
		
		double atendidosB = balcao.getNumeroDeAtendidos() + balcaoP.getNumeroDeAtendidos();
		double atendidosC = caixa.getNumeroDeAtendidos() + caixaP.getNumeroDeAtendidos();
		
		System.out.println("\nResultados da Simulacao");
		System.out.println("Duracao: " + duracao+" min");
		System.out.println("Probabilidade de chegada de clientes: "
		+ probabilidadeChegada*100 + "%");
		System.out.println("Tempo de atendimento m�nimo: "
		+ Leitor.getTempoMinAtendimento()+" min");
		System.out.println("Tempo de atendimento m�ximo: "
		+ Leitor.getTempoMaxAtendimento()+" min");
		
		
		System.out.println("\nEstat�sticas de atendentes:");
		
		System.out.println("\n	Total de clientes atendidos nos balc�es: " + atendidosB);
		System.out.println("	Clintes atendidos no balc�o comum: "
				+ balcao.getNumeroDeAtendidos()+" ("+balcao.getNumeroDeAtendidos()/atendidosB*100+"%)");
		System.out.println("	Cliente ainda no balcao comum: "
				+ (balcao.getClienteAtual() != null));
		System.out.println("	Tempo m�dio de atendimento no balc�o comum: " + balcao.getTempoMedioDeAtendimento()+" min");
		System.out.println("	Clintes atendidos no balc�o preferencial: "
				+ balcaoP.getNumeroDeAtendidos()+" ("+balcaoP.getNumeroDeAtendidos()/atendidosB*100+"%)");
		System.out.println("	Cliente ainda no balcao preferencial: "
				+ (balcaoP.getClienteAtual() != null));
		System.out.println("	Tempo m�dio de atendimento no balc�o preferencial: " + balcaoP.getTempoMedioDeAtendimento()+" min");
		
		System.out.println("\n	Total de clientes atendidos nos caixas: " + atendidosC);
		System.out.println("	Clientes atendidos no caixa comum: " 
		+ caixa.getNumeroDeAtendidos()+" ("+caixa.getNumeroDeAtendidos()/atendidosC*100+"%)");
		System.out.println("	Cliente ainda no caixa comum: "
				+ (caixa.getClienteAtual() != null));
		System.out.println("	Tempo m�dio de atendimento no caixa comum: " + caixa.getTempoMedioDeAtendimento()+" min");
		System.out.println("	Clientes atendidos no caixa preferencial: "  
				+ caixaP.getNumeroDeAtendidos()+" ("+caixaP.getNumeroDeAtendidos()/atendidosC*100+"%)");
		System.out.println("	Cliente ainda no caixa preferencial: "
				+ (caixaP.getClienteAtual() != null));
		System.out.println("	Tempo m�dio de atendimento no caixa preferencial: " + caixaP.getTempoMedioDeAtendimento()+" min");
		
		
		System.out.println("\nEstat�sticas de filas:");
		
		System.out.println("\n	Total de clientes gerados: "
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("	Tempo m�dio de espera: "
				+ statTemposEsperaFila.getMedia());
		
		System.out.println("\n	Clientes ainda na fila do balc�o comum: "
				+ filaBalcao.size());
		System.out.println("	Comprimento m�dio da fila do balc�o comum: "+ statComprimentoFilaBalcao.getMedia());
		System.out.println("	Maior fila no balc�o comum: "
				+ filaBalcao.maxSize());
		System.out.println("	Tempo com fila comum do balc�o vazia: "
				+ filaBalcaoEmp);
		
		System.out.println("	Clientes ainda na fila do balc�o preferencial: "
				+ filaBalcaoP.size());
		System.out.println("	Comprimento m�dio da fila do balc�o preferencial: "+ statComprimentoFilaBalcaoP.getMedia());
		System.out.println("	Maior fila no balc�o preferencial: "
				+ filaBalcaoP.maxSize());
		System.out.println("	Tempo com fila preferencial do balc�o vazia: "
				+ filaBalcaoPEmp);
		
		System.out.println("\n	Clientes ainda na fila do caixa comum: "
				+ filaCaixa.size());
		System.out.println("	Comprimento m�dio da fila do caixa comum: "+ statComprimentoFilaCaixa.getMedia());
		System.out.println("	Maior fila no caixa comum: "
				+ filaCaixa.maxSize());
		System.out.println("	Tempo com fila comum do caixa vazia: "
				+ filaCaixaEmp);
		System.out.println("	Tempo com fila preferencial do caixa vazia: "
				+ filaCaixaPEmp);
		
		System.out.println("	Clientes ainda na fila do caixa preferencial: "
				+ filaCaixaP.size());
		System.out.println("	Comprimento m�dio da fila do caixa preferencial: "+ statComprimentoFilaCaixaP.getMedia());
		System.out.println("	Maior fila no caixa preferencial: "
				+ filaCaixaP.maxSize());
		
				
	}
}