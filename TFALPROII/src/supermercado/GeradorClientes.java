package supermercado;

import java.util.Random;

/**
 * Cria clientes conforme a probabilidade informada
 */
public class GeradorClientes {
	
	/**Probabilidade de gerar um novo cliente*/
	private double probabilidade;
	/**Quantidade total de clientes gerada*/
	private int quantidadeGerada;
	/**Gera o valor que define se o cliente será ou não gerado*/
	private static final Random gerador = new Random();													

	/**
	 * Construtor da classe.
	 * 
	 * @param p
	 * 			Probabilidade de gerar cliente
	 */
	
	public GeradorClientes(double p) {
		probabilidade = p;
		quantidadeGerada = 0;
	}

	/**
	 * Gera o cliente
	 * 
	 * @return
	 * 			true = cliente gerado, false = não gerado
	 */
	public boolean gerar() {
		boolean gerado = false;
		if (gerador.nextDouble() < probabilidade) {
			quantidadeGerada++;
			gerado = true;
		}
		return gerado;
	}

	/**
	 * Dá acesso à variável quantidadeGerada
	 * 
	 * @return quantidadeGerada
	 */
	public int getQuantidadeGerada() {
		return quantidadeGerada;
	}
}
