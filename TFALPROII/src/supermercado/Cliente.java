package supermercado;

import java.io.IOException;
import java.util.Random;

public class Cliente {
	private int numero; // número do cliente
	private int instanteChegada;
	private int tempoMinAtendimento;
	private int tempoMaxAtendimento;
	private Random random;
	private int tempoAtendimento; // quantidade de tempo que resta para o
									// cliente no caixa

	public Cliente(int n, int c) throws IOException {
		random = new Random();
		Leitor.getProps();

		tempoMinAtendimento = Leitor.getTempoMinAtendimento();
		tempoMaxAtendimento = Leitor.getTempoMaxAtendimento();
		numero = n;
		instanteChegada = c;
		tempoAtendimento = random.nextInt(tempoMaxAtendimento - tempoMinAtendimento) + tempoMinAtendimento;

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
	
	public String toString(){
		String s = ("Número: " + numero + "\nInstante de Chegada: "+ instanteChegada + "\nTempo de Atendimento: " + tempoAtendimento);
		return s;
	}
}