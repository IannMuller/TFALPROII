package supermercado;

import java.util.Random;

public class Cliente
{
	private int numero; //numero do cliente
	private int instanteChegada;
	private int tempoAtendimento; //quantidade de tempo que resta para o cliente no caixa
	private static final Random gerador = new Random();
	public static final int tempoMinAtendimento = 5;
	public static final int tempoMaxAtendimento = 10;

	public Cliente(int n, int c)
	{
	    numero = n;
	    instanteChegada = c;
	    tempoAtendimento = gerador.nextInt(tempoMaxAtendimento-tempoMinAtendimento+1)+tempoMinAtendimento; //gera valores entre 5 e 20
	}
	
	public int getNumero()
	{
	    return numero;
	}
	
	public int getInstanteChegada()
	{
	    return instanteChegada;
	}
	
	public void decrementarTempoAtendimento()
	{
	    tempoAtendimento--;
	}
	
	public int getTempoAtendimento()
	{
	    return tempoAtendimento;
	}
}
