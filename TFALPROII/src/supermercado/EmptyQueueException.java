package supermercado;

/**
 * Exceção criada quando o método "dequeue()" é requisitado de uma fila vazia.
 */
public class EmptyQueueException extends RuntimeException {
	public EmptyQueueException() {
		super();
	}
}
