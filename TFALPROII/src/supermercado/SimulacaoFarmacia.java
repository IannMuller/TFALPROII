package supermercado;

import java.io.IOException;

public class SimulacaoFarmacia extends Simulacao {
	
	/**Tempo de duração da simulação*/
	private int duracao;
	/**Probabilidade de chegar um cliente*/
	private float probabilidadeChegada;
	/**Responsável por gerar clientes*/
	private GeradorClientes geradorClientes;
	/**Fila de clientes para o caixa*/
	private QueueTAD<Cliente> filaCaixa;
	/**Fila de clientes para o caixa preferencial*/
	private QueueTAD<Cliente> filaCaixaP;
	/**Fila de clientes para o balcão*/
	private QueueTAD<Cliente> filaBalcao;
	/**Fila de clientes para o balcão preferencial*/
	private QueueTAD<Cliente> filaBalcaoP;
	/**Caixa de atendimento*/
	private Caixa caixa;
	/**Caixa de atendimento preferencial*/
	private Caixa caixaP;
	/**Balcão de atendimento*/
	private Balcao balcao;
	/**Balcão de atendimento preferencial*/
	private Balcao balcaoP;
	/**Tempo médio de espera em todas filas*/
	private Acumulador statTemposEsperaFila;
	/**Tamanho médio de todas filas*/
	private Acumulador statComprimentoFilaBalcao;
	private Acumulador statComprimentoFilaBalcaoP;
	private Acumulador statComprimentoFilaCaixa;
	private Acumulador statComprimentoFilaCaixaP;
	/**Tempo em que as filas ficam vaizas*/
	private int filaBalcaoEmp;
	private int filaBalcaoPEmp;
	private int filaCaixaEmp;
	private int filaCaixaPEmp;
	/**Define se a simulação deve ser impressa na tela*/
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
	 * Simula o tráfego de clientes em uma farmácia
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
			 * Se estiver e houver algum cliente na "filaBalcao", ele vai ser atendido.
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
			 * Se estiver e houver algum cliente na "filaBalcaoP", ele vai ser atendido.
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
									
					}
						
											
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
									+  " ("+balcaoP.getClienteAtual().getTempoAtendimento()
									+" min) deixa o balcão preferencial e entra na fila do caixa - "+filaCaixa.size()+" pessoa(s).");
						
						filaCaixa.enqueue(balcaoP.dispensarClienteAtual());
					}
					
				} else{
				
					balcaoP.getClienteAtual().decrementarTempoAtendimento();
					
				}
				
			}

			//=================================================================================================\\
			
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
					
					}else {
						
						/**
						 * Testa se há algum cliente na fila preferencial para ser atendido
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
					
					}else {
						
						/**
						 * Testa se há algum cliente na fila comum para ser atendido
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
		System.out.println("Tempo de atendimento mínimo: "
		+ Leitor.getTempoMinAtendimento()+" min");
		System.out.println("Tempo de atendimento máximo: "
		+ Leitor.getTempoMaxAtendimento()+" min");
		
		
		System.out.println("\nEstatísticas de atendentes:");
		
		System.out.println("\n	Total de clientes atendidos nos balcões: " + atendidosB);
		System.out.println("	Clintes atendidos no balcão comum: "
				+ balcao.getNumeroDeAtendidos()+" ("+balcao.getNumeroDeAtendidos()/atendidosB*100+"%)");
		System.out.println("	Cliente ainda no balcao comum: "
				+ (balcao.getClienteAtual() != null));
		System.out.println("	Tempo médio de atendimento no balcão comum: " + balcao.getTempoMedioDeAtendimento()+" min");
		System.out.println("	Clintes atendidos no balcão preferencial: "
				+ balcaoP.getNumeroDeAtendidos()+" ("+balcaoP.getNumeroDeAtendidos()/atendidosB*100+"%)");
		System.out.println("	Cliente ainda no balcao preferencial: "
				+ (balcaoP.getClienteAtual() != null));
		System.out.println("	Tempo médio de atendimento no balcão preferencial: " + balcaoP.getTempoMedioDeAtendimento()+" min");
		
		System.out.println("\n	Total de clientes atendidos nos caixas: " + atendidosC);
		System.out.println("	Clientes atendidos no caixa comum: " 
		+ caixa.getNumeroDeAtendidos()+" ("+caixa.getNumeroDeAtendidos()/atendidosC*100+"%)");
		System.out.println("	Cliente ainda no caixa comum: "
				+ (caixa.getClienteAtual() != null));
		System.out.println("	Tempo médio de atendimento no caixa comum: " + caixa.getTempoMedioDeAtendimento()+" min");
		System.out.println("	Clientes atendidos no caixa preferencial: "  
				+ caixaP.getNumeroDeAtendidos()+" ("+caixaP.getNumeroDeAtendidos()/atendidosC*100+"%)");
		System.out.println("	Cliente ainda no caixa preferencial: "
				+ (caixaP.getClienteAtual() != null));
		System.out.println("	Tempo médio de atendimento no caixa preferencial: " + caixaP.getTempoMedioDeAtendimento()+" min");
		
		
		System.out.println("\nEstatísticas de filas:");
		
		System.out.println("\n	Total de clientes gerados: "
				+ geradorClientes.getQuantidadeGerada());
		System.out.println("	Tempo médio de espera: "
				+ statTemposEsperaFila.getMedia());
		
		System.out.println("\n	Clientes ainda na fila do balcão comum: "
				+ filaBalcao.size());
		System.out.println("	Comprimento médio da fila do balcão comum: "+ statComprimentoFilaBalcao.getMedia());
		System.out.println("	Maior fila no balcão comum: "
				+ filaBalcao.maxSize());
		System.out.println("	Tempo com fila comum do balcão vazia: "
				+ filaBalcaoEmp);
		
		System.out.println("	Clientes ainda na fila do balcão preferencial: "
				+ filaBalcaoP.size());
		System.out.println("	Comprimento médio da fila do balcão preferencial: "+ statComprimentoFilaBalcaoP.getMedia());
		System.out.println("	Maior fila no balcão preferencial: "
				+ filaBalcaoP.maxSize());
		System.out.println("	Tempo com fila preferencial do balcão vazia: "
				+ filaBalcaoPEmp);
		
		System.out.println("\n	Clientes ainda na fila do caixa comum: "
				+ filaCaixa.size());
		System.out.println("	Comprimento médio da fila do caixa comum: "+ statComprimentoFilaCaixa.getMedia());
		System.out.println("	Maior fila no caixa comum: "
				+ filaCaixa.maxSize());
		System.out.println("	Tempo com fila comum do caixa vazia: "
				+ filaCaixaEmp);
		System.out.println("	Tempo com fila preferencial do caixa vazia: "
				+ filaCaixaPEmp);
		
		System.out.println("	Clientes ainda na fila do caixa preferencial: "
				+ filaCaixaP.size());
		System.out.println("	Comprimento médio da fila do caixa preferencial: "+ statComprimentoFilaCaixaP.getMedia());
		System.out.println("	Maior fila no caixa preferencial: "
				+ filaCaixaP.maxSize());
		
				
	}
}