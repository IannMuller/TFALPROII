package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe respons�vel por ler dados importantes � simula��o e retorn�-los atrav�s
 * de m�todos "get"
 */

public class Leitor {
	
	/** Tempo m�nimo para o cliente ser atendido */
	private static int tempoMinAtendimento;
	
	/** Tempo m�ximo para o cliente ser atendido */
	private static int tempoMaxAtendimento;
	
	/** Dura��o do ciclo da simula��o */
	private static int duracao;
	
	/** Probabilidade da cria��o de um cliente */
	private static float probabilidadeChegada;

	/**
	 * Respons�vel por ler os valores necess�rios para a simula��o
	 * informados a partir de um arquivo do tipo "properties"
	 * 
	 * @throws IOException
	 * 			Herdado do m�todo "load" da classe "FileInputStream"
	 * 		
	*/
	public static void getProps() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("dados.properties");
		props.load(file);
		
		String tMin = props.getProperty("tempoMinAtendimento");
		String tMax = props.getProperty("tempoMaxAtendimento");
		String duracaoP = props.getProperty("duracao");
		String probabilidadeChegadaP = props.getProperty("probabilidadeChegada");
		
		tempoMinAtendimento = Integer.parseInt(tMin);
		tempoMaxAtendimento = Integer.parseInt(tMax);
		duracao = Integer.parseInt(duracaoP);
		probabilidadeChegada = Float.parseFloat(probabilidadeChegadaP);
	}

	/**
	 * D� acesso ao tempo m�nimo de atendimento
	 * 
	 * @return tempoMinAtendimento
	 */
	public static int getTempoMinAtendimento() {
		return tempoMinAtendimento;
	}

	/**
	 * D� acesso ao tempo m�ximo de atendimento
	 * 
	 * @return tempoMaxAtendimento
	 */
	public static int getTempoMaxAtendimento() {
		return tempoMaxAtendimento;
	}

	/**
	 * D� acesso � dura��o do ciclo da simula��o
	 * 
	 * @return tempoMinAtendimento
	 */
	public static int getDuracao() {
		return duracao;
	}

	/**
	 * D� acesso � probabilidade de chegada de um novo cliente
	 * 
	 * @return probabilidadeChegada
	 */
	public static float getProbabilidade() {
		return probabilidadeChegada;
	}
}
