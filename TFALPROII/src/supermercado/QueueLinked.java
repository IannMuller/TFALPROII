package supermercado;

public class QueueLinked<E> implements QueueTAD<E> {
	private static final class Node<E> {
		/** Guarda o valor de um objeto do nodo. */
		public E element;
		/** Variavel usada para pegar o proximo nodo. */
		public Node<E> next;

		/**
		 * Classe interna do nodo.
		 * 
		 * @param e
		 *            O elemento que será passado pro nodo
		 */
		public Node(E e) {
			element = e;
			next = null;
		}
	}

	private Node<E> head;
	private Node<E> tail;
	private int count;
	private int maxSize;

	public QueueLinked() {
		head = null;
		tail = null;
		count = 0;
		maxSize = 0;
	}
	
	public int maxSize(){
		return maxSize;
	}

	public int size() {
		return count;
	}

	public boolean isEmpty() {
		return (count == 0);
	}

	public void clear() {
		head = null;
		tail = null;
		count = 0;
	}

	public E getHead() {
		if (isEmpty())
			throw new EmptyQueueException();
		return head.element;
	}

	public void enqueue(E element) {
		Node<E> n = new Node<E>(element);
		if (head == null)
			head = n;
		else
			tail.next = n;
		tail = n;
		count++;
		
		if(count>maxSize)
			maxSize = count;
		
	}

	public E dequeue() {
		if (isEmpty())
			throw new EmptyQueueException();
		Node<E> target = head;
		E item = target.element;
		head = target.next;
		target.element = null;
		target.next = null;
		if (head == null)
			tail = null;
		count--;
		return item;
	}
}
