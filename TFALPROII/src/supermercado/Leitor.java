package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe responsável por ler dados importantes à simulação e retorná-los em
 * forma de "Properties"
 */

public class Leitor {
	public Properties props;
	public static int tempoMinAtendimento;
	public static int tempoMaxAtendimento;
	public static int duracao;
	public static float probabilidadeChegada;

	public static Properties getProps() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("dados.properties");
		props.load(file);
		
		String duracaoP = props.getProperty("duracao");
		String probabilidadeChegadaP = props.getProperty("probabilidadeChegada");
		duracao = Integer.parseInt(duracaoP);
		probabilidadeChegada = Float.parseFloat(probabilidadeChegadaP);

		return props;
	}

	public int getTempoMinAtendimento() {
		return tempoMinAtendimento;

	}

	public int getTempoMaxAtendimento() {
		return tempoMaxAtendimento;
	}

	public int getDuracao() {
		return duracao;
	}

	public float getProbabilidade() {
		return probabilidadeChegada;
	}
}
