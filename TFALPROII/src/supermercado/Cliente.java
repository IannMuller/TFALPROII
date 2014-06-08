package supermercado;

import java.io.IOException;
import java.util.Random;

/**
 * Representa um cliente com somente dados estat�sticos utilizados nas
 * simula��es
*/

public class Cliente {
	
	/** N�mero de identifica��o do cliente */
	private int numero;
	
	/** Momento em que o cliente chega */
	private int instanteChegada;
	
	/** Gerador rand�mico para decidir tempo de atendimento */
	private Random random;
	
	/** Tempo que o cliente demorar� no momento do atendimento */
	private int tempoAtendimento;

	/**
	 * Construtor da classe. A partir de um leitor, recebe-se os valores de tempo
	 * m�nimo de atendimento e m�ximo para decidir o tempo definitivo do atendimento.
	 * 
	 * @param n
	 * 			N�mero de identifica��o do cliente
	 * 
	 * @param c
	 * 			Momento de chegada do cliente
	 * 
	 * @throws IOException
	 * 			
	 */
	public Cliente(int n, int c) throws IOException {
		numero = n;
		instanteChegada = c;
		
		modifyTempoAtendimento();
	}

	public void modifyTempoAtendimento () throws IOException {
		random = new Random();
		Leitor.getProps();
		
		int tempoMinAtendimento = Leitor.getTempoMinAtendimento();
		int tempoMaxAtendimento = Leitor.getTempoMaxAtendimento();
		
		tempoAtendimento = random.nextInt(tempoMaxAtendimento
				- tempoMinAtendimento)
				+ tempoMinAtendimento;
	}
	
	public int getNumero() {
		return numero;
	}

	public int getInstanteChegada() {
		return instanteChegada;
	}

	public void decrementarTempoAtendimento() {
		tempoAtendimento--;
	}

	public int getTempoAtendimento() {
		return tempoAtendimento;
	}

	public String toString() {
		String s = ("N�mero: " + numero + "\nInstante de Chegada: "
				+ instanteChegada + "\nTempo de Atendimento: " + tempoAtendimento);
		return s;
	}
}