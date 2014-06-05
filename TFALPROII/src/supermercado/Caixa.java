package supermercado;

public class Caixa extends Atendimento {
	protected Cliente clienteAtual; // cliente sendo atendido no caixa
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
