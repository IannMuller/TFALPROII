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
	
	/** Gerador randômico para decidir tempo de atendimento */
	private Random random;
	
	/** Tempo que o cliente demorará no momento do atendimento */
	private int tempoAtendimento;

	/**
	 * Construtor da classe. A partir de um leitor, recebe-se os valores de tempo
	 * mínimo de atendimento e máximo para decidir o tempo definitivo do atendimento.
	 * 
	 * @param n
	 * 			Número de identificação do cliente
	 * 
	 * @param c
	 * 			Momento de chegada do cliente
	 * 
	 * @throws IOException
	 * 			Necessário para o método "modifyTempoAtendimento"
	 * 			
	 */
	public Cliente(int n, int c) throws IOException {
		numero = n;
		instanteChegada = c;
		
		modifyTempoAtendimento();
	}
	
	/**
	 * Modifica randomicamente o tempo de atendimento do
	 * cliente.
	 * 
	 * @throws IOException
	 * 			Proveniente da classe "Leitor"
	 */
	public void modifyTempoAtendimento () throws IOException {
		random = new Random();
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
	 * Informa os dados pricipais do cliente em forma de String
	 * 
	 * @return String com numero, instanteChegada e tempoAtendimento
	 * 
	 */
	public String toString() {
		String s = ("Número: " + numero + "\nInstante de Chegada: "
				+ instanteChegada + "\nTempo de Atendimento: " + tempoAtendimento);
		return s;
	}
}