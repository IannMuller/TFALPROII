package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe responsável por ler dados importantes à simulação e retorná-los através
 * de métodos "get"
 */

public class Leitor {
	
	/** Tempo mínimo para o cliente ser atendido */
	private static int tempoMinAtendimento;
	
	/** Tempo máximo para o cliente ser atendido */
	private static int tempoMaxAtendimento;
	
	/** Duração do ciclo da simulação */
	private static int duracao;
	
	/** Probabilidade da criação de um cliente */
	private static float probabilidadeChegada;

	/**
	 * Responsável por ler os valores necessários para a simulação
	 * informados a partir de um arquivo do tipo "properties"
	 * 
	 * @throws IOException
	 * 			Herdado do método "load" da classe "FileInputStream"
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
	 * Dá acesso ao tempo mínimo de atendimento
	 * 
	 * @return tempoMinAtendimento
	 */
	public static int getTempoMinAtendimento() {
		return tempoMinAtendimento;
	}

	/**
	 * Dá acesso ao tempo máximo de atendimento
	 * 
	 * @return tempoMaxAtendimento
	 */
	public static int getTempoMaxAtendimento() {
		return tempoMaxAtendimento;
	}

	/**
	 * Dá acesso à duração do ciclo da simulação
	 * 
	 * @return tempoMinAtendimento
	 */
	public static int getDuracao() {
		return duracao;
	}

	/**
	 * Dá acesso à probabilidade de chegada de um novo cliente
	 * 
	 * @return probabilidadeChegada
	 */
	public static float getProbabilidade() {
		return probabilidadeChegada;
	}
}
