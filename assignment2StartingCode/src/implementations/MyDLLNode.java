package implementations;

/**
 * This class represents a node in a doubly linked list.
 *
 * @param <E> the type of data stored in the node
 */
public class MyDLLNode<E> {
	
	public E data;
	public MyDLLNode<E> prev;
	public MyDLLNode<E> next;
	
	/**
	 * Creates a new node with the given data.
	 */
	public MyDLLNode(E data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}
	
}
