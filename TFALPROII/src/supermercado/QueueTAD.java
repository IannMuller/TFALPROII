package supermercado;

public interface QueueTAD<E> {
	int size();

	boolean isEmpty();

	void clear();

	E getHead();

	void enqueue(E element);

	E dequeue();
}
