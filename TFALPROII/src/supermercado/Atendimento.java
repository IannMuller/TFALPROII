package supermercado;

/**
 * Classe abstrata que simula o atendimento de clientes.
 */

public abstract class Atendimento {
	
	/** Guarda o valor do cliente que está sendo atendido atualmente. */
	protected Cliente clienteAtual;
	
	/** Guarda o valor total de clientes que foram atendidos. */
	protected int numeroDeAtendidos;
	
	
	/** 
	 * Construtor da classe. Não necessita de atributos para ser inicializada
	 */
	public Atendimento (){
		clienteAtual = null;
		numeroDeAtendidos = 0;
		
	}
	
	/**
	 * Simula o atendimento de um cliente. Cria uma excessão caso já exista um
	 * cliente sendo atendido. 
	 * 
	 * @param c
	 * 		   O cliente que será atendido
	 * 
	 * @throws Exception
	 * 		   Se já existe um cliente sendo atendido 
	*/
	public void atenderCliente (Cliente c)  throws Exception{
		if (clienteAtual!=null){
			Exception e = new Exception("já existe um cliente sendo atendido");
			throw e;
		}
		clienteAtual = c;
	}
	
	/**
	 * Simula o dispensamento do cliente atual.
	 * 
	 * @return o cliente que será dispensado
	 * 
	 */
	public Cliente dispensarClienteAtual (){
		Cliente c = clienteAtual;
		clienteAtual = null;
		numeroDeAtendidos++;
		return c;
		
	}	
	
	/**
	 * Indica se não existe um cliente sendo atendido no momento
	 * 
	 * @return se está disponível para atender um cliente
	 */
	public boolean estaVazio() {
		return (clienteAtual == null);
	}

	/**
	 * Dá acesso ao cliente atual
	 * 
	 * @return clienteAtual
	 */
	public Cliente getClienteAtual() {
		return clienteAtual;
	}
	
	/**
	 * Dá acesso ao número de clientes atendidos
	 * 
	 * @return numeroDeAtendidos
	 */

	public int getNumeroDeAtendidos() {
		return numeroDeAtendidos;
	}
	
	
	
}
