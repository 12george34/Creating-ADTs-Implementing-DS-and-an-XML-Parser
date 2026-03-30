package implementations;

public class MyDLLNode<E> {
	
	public E data;
	public MyDLLNode<E> prev;
	public MyDLLNode<E> next;
	
	
	public MyDLLNode(E data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}
	
}
