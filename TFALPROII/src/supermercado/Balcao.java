package supermercado;

/*
 * Classe Que simula o balcao de atendimento
 */
public class Balcao extends Atendente {
	public QueueTAD<Cliente> filaCaixa;

	/*
	 * Classe construtor que inicia o clienteBalcao com null e o
	 * numeroAtendimentos com 0
	 */
	public Balcao() {
		super();
	}

	/*
	 * Recebe um cliente por parametro e passa para o clienteBalcao
	 */
	public void atenderCliente(Cliente c) {
		super();
	}

	/*
	 * passa null para o clienteBalcao, retorna o cliente excluido e aumenta o
	 * numeroAtendimentos em 1
	 */
	public Cliente dispensarClienteAtual() {
		super();

	}

	/*
	 * Retorna true se o balcao esta vazio
	 */
	public boolean estaVazio() {
		super();
	}

	/*
	 * Retorna o cliente no balcão
	 */
	public Cliente getClienteAtual() {
		super();
	}

	/*
	 * Retorna o numero de clientes atendidos
	 */
	public int getNumeroDeAtendidos() {
		super();
	}

	/*
	 * Adiciona o cliente do balcao na fila do caixa e retorna true caso tenha
	 * tido sucesso.
	 */
	public boolean AddFilaCaixa() {
		Cliente c = clienteAtual;
		if (filaCaixa != null) {
			filaCaixa.enqueue(c);
			return true;
		} else {
			return false;
		}
	}

}
