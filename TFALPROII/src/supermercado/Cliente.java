package supermercado;

import java.io.IOException;
import java.util.Random;

/**
 * Representa um cliente com somente dados estatísticos utilizados nas
 * simulações
 */

public class Cliente {

	/** Número de identificação do cliente */
	private int numero;

	/** Momento em que o cliente chega */
	private int instanteChegada;
	
	/** Define se o cliente vai desistir da compra ou não*/
	private boolean vaiDesistir;

	/** Gerador randômico para decidir tempo de atendimento e se o cliente é preferencial ou não */
	private Random random = new Random();

	/** Tempo que o cliente demorará no momento do atendimento */
	private int tempoAtendimento;
	/** Determina se o cliente será ou não preferencial */
	private boolean preferencial;

	/**
	 * Construtor da classe. A partir de um leitor, recebe-se os valores de
	 * tempo mínimo de atendimento e máximo para decidir o tempo definitivo do
	 * atendimento.
	 * 
	 * @param n
	 *            Número de identificação do cliente
	 * 
	 * @param c
	 *            Momento de chegada do cliente
	 *            
	 * @param pref
	 * 			  Possibilidade do cliente ser preferencial
	 * 
	 * @param desist
	 * 			  Possibilidade do cliente desistir
	 * 
	 * @throws IOException
	 *             Necessário para o método "modifyTempoAtendimento"
	 * 
	 */
	public Cliente(int n, int c, float pref, float desist) throws IOException {
		numero = n;
		instanteChegada = c;
		
		preferencial = false;
		vaiDesistir = false;

		int preferencia = random.nextInt(100) + 1 ;
		int desistencia = random.nextInt(100) + 1;
		
		if (preferencia<pref*100)
			preferencial = true;
		
		if (desistencia<desist*100)
			vaiDesistir = true;

		modifyTempoAtendimento();
	}
	
	public Cliente(int n, int c) throws IOException {
		numero = n;
		instanteChegada = c;
		
		preferencial = false;
		vaiDesistir = false;
		
		modifyTempoAtendimento();
	}

	/**
	 * Modifica randomicamente o tempo de atendimento do cliente.
	 * 
	 * @throws IOException
	 *             Proveniente da classe "Leitor"
	 */
	public void modifyTempoAtendimento() throws IOException {
		Leitor.getProps();

		int tempoMinAtendimento = Leitor.getTempoMinAtendimento();
		int tempoMaxAtendimento = Leitor.getTempoMaxAtendimento();

		tempoAtendimento = random.nextInt(tempoMaxAtendimento
				- tempoMinAtendimento)
				+ tempoMinAtendimento;
	}

	/**
	 * Dá acesso ao número de identificação do cliente
	 * 
	 * @return numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Dá acesso ao instante de chegada
	 * 
	 * @return istanteChegada
	 */
	public int getInstanteChegada() {
		return instanteChegada;
	}

	/**
	 * Decrementa o valor do tempo de atendimento em 1
	 */
	public void decrementarTempoAtendimento() {
		tempoAtendimento--;
	}

	/**
	 * Dá acesso ao tempo de atendimento atual
	 * 
	 * @return tempoAtendimento
	 */
	public int getTempoAtendimento() {
		return tempoAtendimento;
	}
	
	/**
	 * Retorna se o cliente é preferencial ou não.
	 * Este método é usado exclusivamente em "toString",
	 * portanto é privado
	 * 
	 * @return String informando se é ou não preferencial
	 */
	private String getPreferencia() {
		
		String s = "Cliente Normal";
		
		if (preferencial) 
			s = "Cliente preferencial";

		return s;
	}
	
	/**
	 * Retorna se o cliente é preferencial.
	 * 
	 * @return preferencial
	 */
	public boolean isPreferencial(){
		return preferencial;
	}
	
	/**
	 * Retorna se o cliente vai desistir
	 * 
	 * @return vaiDesistir
	 */
	public boolean vaiDesistir(){
		return vaiDesistir;
	}
	
	/**
	 * Informa os dados pricipais do cliente em forma de String
	 * 
	 */
	 public String toString() {
		String s = ("Número: " + numero + "\nInstante de Chegada: "
				+ instanteChegada + "\nTempo de Atendimento: "
				+ tempoAtendimento + "\n" + getPreferencia());
		return s;
	}
}
