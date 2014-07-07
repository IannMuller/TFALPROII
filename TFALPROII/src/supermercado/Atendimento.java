package supermercado;

/**
 * Classe abstrata que simula o atendimento de clientes.
 */

public abstract class Atendimento {

	/** Guarda o valor do cliente que est� sendo atendido atualmente. */
	protected Cliente clienteAtual;

	/** Guarda o valor total de clientes que foram atendidos. */
	protected int numeroDeAtendidos;
	
	/**Guarda o valor total de tempo de todos atendimentos. */
	protected double tempoTotalDeAtendimento;

	/**
	 * Construtor da classe. N�o necessita de atributos para ser inicializada
	 */
	public Atendimento() {
		clienteAtual = null;
		numeroDeAtendidos = 0;
		tempoTotalDeAtendimento = 0;
	}

	/**
	 * Simula o atendimento de um cliente. Cria uma excess�o caso j� exista um
	 * cliente sendo atendido.
	 * 
	 * @param c
	 *            O cliente que ser� atendido
	 * 
	 * @throws Exception
	 *             Se j� existe um cliente sendo atendido
	 */
	public void atenderCliente(Cliente v) throws Exception {
		if (clienteAtual != null) {
			Exception e = new Exception("j� existe um cliente sendo atendido");
			throw e;
		}
		clienteAtual = v;
		tempoTotalDeAtendimento = tempoTotalDeAtendimento + v.getTempoAtendimento();
	}

	/**
	 * Simula o dispensamento do cliente atual.
	 * 
	 * @return o cliente que ser� dispensado
	 * 
	 */
	public Cliente dispensarClienteAtual() {
		Cliente c = clienteAtual;
		clienteAtual = null;
		numeroDeAtendidos++;
		return c;

	}

	/**
	 * Indica se n�o existe um cliente sendo atendido no momento
	 * 
	 * @return se est� dispon�vel para atender um cliente
	 */
	public boolean estaVazio() {
		return (clienteAtual == null);
	}

	/**
	 * D� acesso ao cliente atual
	 * 
	 * @return clienteAtual
	 */
	public Cliente getClienteAtual() {
		return clienteAtual;
	}

	/**
	 * D� acesso ao n�mero de clientes atendidos
	 * 
	 * @return numeroDeAtendidos
	 */
	public int getNumeroDeAtendidos() {
		return numeroDeAtendidos;
	}
	
	/**
	 * D� acesso ao tempo m�dio de atendimentos
	 * 
	 * @return tempoMedioAtendimento
	 */
	public double getTempoMedioDeAtendimento(){
		if(estaVazio())
			return tempoTotalDeAtendimento/numeroDeAtendidos;
		else
			return tempoTotalDeAtendimento/(numeroDeAtendidos+1);
	}

}
