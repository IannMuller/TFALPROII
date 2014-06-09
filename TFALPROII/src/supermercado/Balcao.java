package supermercado;

/**
 * Classe que simula um balcão onde o cliente faz requisição de produtos
 * ao atendente. Esta classe herda tudo contido na classe abstrata "Atendimento".
 */

public class Balcao extends Atendimento {

	public Balcao() {
		super();
	}

	public void atenderCliente(Cliente c) throws Exception {
		super.atenderCliente(c);

	}

	public Cliente dispensarClienteAtual() {
		return super.dispensarClienteAtual();

	}

	public boolean estaVazio() {
	 	return super.estaVazio();
	}

	public Cliente getClienteAtual() {
		return super.getClienteAtual();
	}

	public int getNumeroDeAtendidos() {
		return super.getNumeroDeAtendidos();
	}

}