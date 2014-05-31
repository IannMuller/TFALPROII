package supermercado;

public class Atendente {
	private Cliente clienteAtual;
	private int numeroDeAtendidos;
	
	public Atendente (){
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

	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	public int getNumeroDeAtendidos() {
		return numeroDeAtendidos;
	}
	
	
	
}
