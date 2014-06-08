package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe responsável por ler dados importantes à simulação e retorná-los em
 * forma de "Properties"
 */

public class Leitor {
	private static int tempoMinAtendimento;
	private static int tempoMaxAtendimento;
	private static int duracao;
	private static float probabilidadeChegada;

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

	public static int getTempoMinAtendimento() {
		return tempoMinAtendimento;
	}

	public static int getTempoMaxAtendimento() {
		return tempoMaxAtendimento;
	}

	public static int getDuracao() {
		return duracao;
	}

	public static float getProbabilidade() {
		return probabilidadeChegada;
	}
}
