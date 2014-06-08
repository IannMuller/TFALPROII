package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Cliente {
	private int numero; // número do cliente
	private int instanteChegada;
	static int tempoMinAtendimento;
	static int tempoMaxAtendimento;
	public int tempoAtendimento; // quantidade de tempo que resta para o
									// cliente no caixa

	public Cliente(int n, int c) throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("dados.properties");
		props.load(file);
		String tempoMinAtendimentoP = props.getProperty("tempoMinAtendimento");
		String tempoMaxAtendimentoP = props.getProperty("tempoMaxAtendimento");
		tempoMinAtendimento = Integer.parseInt(tempoMinAtendimentoP);
		tempoMaxAtendimento = Integer.parseInt(tempoMaxAtendimentoP);
		numero = n;
		instanteChegada = c;
		tempoAtendimento = (tempoMaxAtendimento - tempoMinAtendimento + 1);

	}

	public int getNumero() {
		return numero;
	}
	public void setNumero(int aux){
		numero = aux;
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