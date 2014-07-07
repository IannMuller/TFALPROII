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

	/**
	 * Gerador rand�mico para decidir tempo de atendimento e se o cliente �
	 * preferencial ou n�o
	 */
	private Random random = new Random();

	/** Tempo que o cliente demorar� no momento do atendimento */
	private int tempoAtendimento;
	/** Determina se o cliente ser� ou n�o preferencial */
	private boolean preferencial;

	/**
	 * Construtor da classe. A partir de um leitor, recebe-se os valores de
	 * tempo m�nimo de atendimento e m�ximo para decidir o tempo definitivo do
	 * atendimento.
	 * 
	 * @param n
	 *            N�mero de identifica��o do cliente
	 * 
	 * @param c
	 *            Momento de chegada do cliente
	 * 
	 * @throws IOException
	 *             Necess�rio para o m�todo "modifyTempoAtendimento"
	 * 
	 */
	public Cliente(int n, int c) throws IOException {
		numero = n;
		instanteChegada = c;
		
		preferencial = false;

		int preferencia = random.nextInt(100) + 1 ;
		
		if (preferencia>=70)
			preferencial = true;

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
	 * D� acesso ao n�mero de identifica��o do cliente
	 * 
	 * @return numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * D� acesso ao instante de chegada
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
	 * D� acesso ao tempo de atendimento atual
	 * 
	 * @return tempoAtendimento
	 */
	public int getTempoAtendimento() {
		return tempoAtendimento;
	}
/**
 * Retorna se o cliente � preferencial ou n�o.
 * Este m�todo � usado exclusivamente em "toString",
 * portanto � privado
 * 
 * @return String informando se � ou n�o preferencial
 */
	private String getPreferencia() {
		
		String s = "Cliente Normal";
		
		if (preferencial) 
			s = "Cliente preferencial";

		return s;
	}
	/**
	 * Retorna o numero randomico armazenado em preferencia.
	 * 
	 * @return preferencia
	 */
	public boolean isPreferencial(){
		return preferencial;
	}
	/**
	 * Informa os dados pricipais do cliente em forma de String
	 * 
	 */
	 public String toString() {
		String s = ("N�mero: " + numero + "\nInstante de Chegada: "
				+ instanteChegada + "\nTempo de Atendimento: "
				+ tempoAtendimento + "\n" + getPreferencia());
		return s;
	}
}