package pucrs.alpro2.pdv;

/**
 * A classe <code>Produto</code> representa os dados de um tipo de produto a
 * venda no supermercado.
 * 
 * <p>
 * O objeto produto armazena o código EAN/IAN, uma descrição para fins de
 * etiqueta e verificação pelo caixa e cliente, a quantidade e a unidade de
 * medida de armazenamento e o valor em centavos do produto.
 * 
 * <p>
 * Caso o produto não possua um cadastro IAN/EAN ou o cadastro não esteja
 * disponível, utilize a constante IAN_INDEFINIDO.
 * 
 * <p>
 * O objeto produto é utilizado no controle de estoque e no carrinho de
 * supermercado, com a classe Item.
 * 
 * Exemplo:
 * 
 * <pre>
 * Produto p = new Produto(PRODUTO.IAN_INDEFINIDO,
 * 		&quot;Ovo Sonho de Valsa no. 20 Lacta&quot;, 330, &quot;g&quot;, 2990);
 * </pre>
 * 
 * @see http://www.gs1.org
 * @see http://www.gs1br.org
 * @see http://pt.wikipedia.org/wiki/EAN-13
 * 
 * @see https://jcp.org/en/jsr/detail?id=354
 * @see http
 *      ://stackoverflow.com/questions/285680/representing-monetary-values-in
 *      -java
 * @see http
 *      ://stackoverflow.com/questions/3730019/why-not-use-double-or-float-to
 *      -represent-currency
 * 
 * @author marco.mangan@pucrs.br
 * 
 */
public class Produto {
	public final static String IAN_INDEFINIDO = "IAN_INDEFINIDO";

	private String ian;
	private String descricao;
	private String unidade;
	private long valorEmCentavos;

	public Produto(final String ian, final String descricao, final String unidade, final long valorEmCentavos)	throws IllegalArgumentException {
		super();
		this.ian = ian;
		this.descricao = descricao;
		this.unidade = unidade;
		this.valorEmCentavos = valorEmCentavos;
		if (valorEmCentavos <= 0) {
			throw new IllegalArgumentException("Valor inv�lido");
		}
	}

	public static String getIanIndefinido() {
		return IAN_INDEFINIDO; // WTF?
	}

	public String getIan() {
		return ian;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public long getValorEmCentavos() {
		return valorEmCentavos;
	}

	@Override
	public String toString() {
		return String
				.format("Produto [ian=%s, descricao=%s, unidade=%s, valorEmCentavos=%s]",
						ian, descricao, unidade, valorEmCentavos);
	}

}
