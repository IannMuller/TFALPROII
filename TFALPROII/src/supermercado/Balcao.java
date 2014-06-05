package supermercado;

/*
 * Classe Que simula o balcao de atendimento
 */
public class Balcao extends Atendimento {
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
	public void atenderCliente(Cliente c) throws Exception {
		super.atenderCliente(c);
		
	}

	/*
	 * passa null para o clienteBalcao, retorna o cliente excluido e aumenta o
	 * numeroAtendimentos em 1
	 */
	public Cliente dispensarClienteAtual() {
		return super.dispensarClienteAtual();

	}

	/*
	 * Retorna true se o balcao esta vazio
	 */
	public boolean estaVazio() {
	 	return super.estaVazio();
	}

	/*
	 * Retorna o cliente no balcão
	 */
	public Cliente getClienteAtual() {
		return super.getClienteAtual();
	}

	/*
	 * Retorna o numero de clientes atendidos
	 */
	public int getNumeroDeAtendidos() {
		return super.getNumeroDeAtendidos();
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
