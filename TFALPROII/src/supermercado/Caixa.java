package supermercado;

/**
 * Classe que simula um caixa qualquer onde o cliente realiza o pagamento
 * de suas compras. Esta classe herda tudo contido na classe abstrata "Atendimento".
 */

public class Caixa extends Atendimento {
	/** Guarda o valor do cliente que está sendo atendido atualmente. */
	protected Cliente clienteAtual;
	/** Guarda o valor do numero de clientes atendidos */
	protected int numeroAtendidos;

	public Caixa() {
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

	public int getNumeroAtendidos() {
		return super.getNumeroDeAtendidos();
	}
}