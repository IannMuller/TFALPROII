package supermercado;

/**
 * Classe que simula um balcão onde o cliente faz requisição de produtos
 * ao atendente. Esta classe herda tudo contido na classe abstrata "Atendimento".
 */

public class Balcao extends Atendimento {
	public QueueTAD<Cliente> filaCaixa;

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

	/**
	 * Adiciona o cliente do balcao na fila do caixa e retorna true caso tenha
	 * tido sucesso.
	 */
	public boolean AddFilaCaixa() {
		Cliente c = super.clienteAtual;
		if (filaCaixa != null) {
			filaCaixa.enqueue(c);
			return true;
		} else {
			return false;
		}
	}

}
