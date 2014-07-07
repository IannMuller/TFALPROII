package supermercado;

/**
 * Classe utilitaria que realiza calculos de media aritmetica
 */
public class Acumulador {
	/** Guarda o valor de valor. */
	private double valor;
	/** Guarda a quantidade de vezes que o valor aumenta. */
	private int contador;

	/**
	 * Construtor do acumulador.
	 */
	public Acumulador() {
		valor = 0;
		contador = 1;
	}
	
	/**
	 * Reseta todos valores
	 */
	public void reset(){
		valor = 0;
		contador = 0;
	}
	
	/**
	 * Retorna o valor.
	 * 
	 * @return valor
	 */
	public double getValor() {
		return (valor);
	}

	/**
	 * Retorna o contador
	 * 
	 * @return contador
	 */
	public int getContagem() {
		return contador;
	}

	/**
	 * Adiciona um valor double ao valor ja existente e aumenta o contador.
	 * 
	 * @param n
	 */
	public void adicionar(double n) {
		valor = valor + n;
		contador++;
	}

	/**
	 * Adiciona um valor int ao valor ja existente e aumenta o contador.
	 * 
	 * @param n
	 */
	public void adicionar(int n) {
		valor = valor + n;
		contador++;
	}
	/**
	 * Se o contador nao for zero, faz a média entre valor e contador.
	 * 
	 * @return divisao entre o valor e o contador
	 */
	public double getMedia() {
		if (contador != 0)
			return valor / contador;
		else
			return 0;
	}

	public void aumentarContador() {
		contador++;
	}
}