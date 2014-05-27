package supermercado;

import java.util.Random;

/*
 * Esta classe indica se um cliente sera gerado de acordo com a probabilidade indicada no construtor
 */
public class GeradorClientes
{
    private double probabilidade;
    private int quantidadeGerada;
    private static final Random gerador = new Random(); //gerador de numeros aleatorios de Java
    
    public GeradorClientes(double p)
    {
        probabilidade = p;
        quantidadeGerada = 0;
    }
    
    public boolean gerar()
    {
        boolean gerado = false;
        if(gerador.nextDouble() < probabilidade)
        {
            quantidadeGerada++;
            gerado = true;
        }
        return gerado;
    }
    
    public int getQuantidadeGerada()
    {
        return quantidadeGerada;
    }
}
