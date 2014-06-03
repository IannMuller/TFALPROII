package supermercado;

/*
 * Classe Que simula o balcao de atendimento
 */
public class Balcao implements Atendente {
	private int numeroAtendimentos;
	private Cliente clienteBalcao;
	public QueueTAD<Cliente> filaCaixa;

	/*
	 * Classe construtor que inicia o clienteBalcao com null e o
	 * numeroAtendimentos com 0
	 */
	public Balcao() {
		clienteBalcao = null;
		numeroAtendimentos = 0;
	}

	/*
	 * Recebe um cliente por parametro e passa para o clienteBalcao
	 */
	public void atenderCliente(Cliente c) {
		clienteBalcao = c;
	}

	/*
	 * passa null para o clienteBalcao, retorna o cliente excluido e aumenta o
	 * numeroAtendimentos em 1
	 */
	public Cliente dispensarClienteAtual() {
		Cliente c = clienteBalcao;
		clienteBalcao = null;
		numeroAtendimentos++;
		return c;

	}

	/*
	 * Retorna true se o balcao esta vazio
	 */
	public boolean estaVazio() {
		return (clienteBalcao == null);
	}

	/*
	 * Retorna o cliente no balcão
	 */
	public Cliente getClienteAtual() {
		return clienteBalcao;
	}

	/*
	 * Retorna o numero de clientes atendidos
	 */
	public int getNumeroDeAtendidos() {
		return numeroAtendimentos;
	}

	/*
	 * Adiciona o cliente do balcao na fila do caixa e retorna true caso tenha
	 * tido sucesso.
	 */
	public boolean AddFilaCaixa() {
		Cliente c = clienteBalcao;
		if (filaCaixa != null) {
			filaCaixa.enqueue(c);
			return true;
		} else {
			return false;
		}
	}

}
