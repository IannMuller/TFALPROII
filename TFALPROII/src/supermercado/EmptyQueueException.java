package supermercado;

/**
 * Exce��o criada quando o m�todo "dequeue()" � requisitado de uma fila vazia.
 */
public class EmptyQueueException extends RuntimeException {
	public EmptyQueueException() {
		super();
	}
}
