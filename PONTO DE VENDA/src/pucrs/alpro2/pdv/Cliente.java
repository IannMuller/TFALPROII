package pucrs.alpro2.pdv;

/**
 * A classe <code>Cliente</code> representa um cliente de supermercado.
 * 
 * <p>
 * Um objeto da classe cliente armazena o número de cadastro de pessoa física
 * para fins de nota fiscal.
 * 
 * <p>
 * O objeto armazena um endereço de correio eletrônico para envio de cópia de
 * nota fiscal e para relacionamento com o cliente.
 * 
 * <p>
 * O número de cadastro de pessoa física deve ser verificado para evitar erros
 * de digitação e cadastro duplicado.
 * 
 * <p>
 * Caso o cliente não informe ou não tenho um número de CPF, indicar a constante
 * CPF_INDEFINIDO.
 * 
 * @see http://www.receita.fazenda.gov.br/pessoafisica/cpf/cadastropf.htm
 * @see http://pt.wikipedia.org/wiki/Cpf
 * @see https://nfg.sefaz.rs.gov.br
 * 
 * @author marco.mangan@pucrs.br
 */
public class Cliente {
	public final static String CPF_INDEFINIDO = "CPF_INDEFINIDO";

	private String cpf;
	private String email;

	public Cliente(final String cpf, final String email) throws IllegalArgumentException {
		super();
		this.cpf = cpf;
		this.email = email;
		
		if(cpf==null){
			throw new IllegalArgumentException();
			}
		if(email==null){
			throw new IllegalArgumentException();
		}			
	if(cpf.length()<14){
		throw new IllegalArgumentException();
		
		
	 }
	
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return String.format("Cliente [cpf=%s, email=%s]", cpf, email);
	}

}
