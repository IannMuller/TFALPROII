package supermercado;

import java.io.IOException;
import java.util.Random;

public class Cliente {
	private int numero; // número do cliente
	private int instanteChegada;
	private int tempoAtendimento; // quantidade de tempo que resta para o
									// cliente no caixa
	private static final Random gerador = new Random();
	public static int tempoMinAtendimento;
	public static int tempoMaxAtendimento;
	private Leitor leitor;
	private String tempoMinAtendimentoP;
	private String tempoMaxAtendimentoP;
		
	public Cliente(int n, int c) throws IOException {
		leitor.getProp();
		numero = n;
		instanteChegada = c;
		tempoMinAtendimentoP = leitor.props.getProperty("tempoMinAtendimento");
		tempoMaxAtendimentoP = leitor.props.getProperty("tempoMaxAtendimento");
		tempoMinAtendimento = Integer.parseInt(tempoMinAtendimentoP);
		tempoMaxAtendimento = Integer.parseInt(tempoMaxAtendimentoP);
		tempoAtendimento = gerador.nextInt(tempoMaxAtendimento
				- tempoMinAtendimento + 1)
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
}
