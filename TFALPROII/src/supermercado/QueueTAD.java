package supermercado;

public interface QueueTAD<E> {
	int size();
	
	int maxSize();

	boolean isEmpty();

	void clear();

	E getHead();

	void enqueue(E element);

	E dequeue();
}
