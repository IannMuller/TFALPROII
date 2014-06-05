package supermercado;

public abstract class Atendimento {
	protected Cliente clienteAtual;
	protected int numeroDeAtendidos;
	
	
	public Atendimento (){
		clienteAtual = null;
		numeroDeAtendidos = 0;
		
	}
	
	public void atenderCliente (Cliente c)  throws Exception{
		if (clienteAtual!=null){
			Exception e = new Exception("já existe um cliente sendo atendido");
			throw e;
		}
		clienteAtual = c;
	}
	
	public Cliente dispensarClienteAtual (){
		Cliente c = clienteAtual;
		clienteAtual = null;
		numeroDeAtendidos++;
		return c;
		
	}	
	
	public boolean estaVazio() {
		return (clienteAtual == null);
	}

	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	public int getNumeroDeAtendidos() {
		return numeroDeAtendidos;
	}
	
	
	
}
