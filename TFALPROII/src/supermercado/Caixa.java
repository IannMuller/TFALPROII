package supermercado;

public class Caixa {
	protected Cliente clienteAtual; // cliente sendo atendido no caixa
	protected int numeroAtendidos;

	public Caixa() {
		clienteAtual = null;
		numeroAtendidos = 0;
	}

	public void atenderNovoCliente(Cliente c) {
		clienteAtual = c;
	}

	public Cliente dispensarClienteAtual() {
		Cliente c = clienteAtual;
		clienteAtual = null;
		numeroAtendidos++;
		return c;
	}

	public boolean estaVazio() {
		return (clienteAtual == null);
	}

	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	public int getNumeroAtendidos() {
		return numeroAtendidos;
	}
}
