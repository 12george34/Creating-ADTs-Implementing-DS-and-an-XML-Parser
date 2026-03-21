package utilities;

/**
 * Code for linear first-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * @param <E>
 */
public interface QueueADT<E> {
	
	/**
	 * adds new element to bottom of the queue
	 * 
	 * @param toADD element to be added
	 */
	public void enqueue(E toADD);
	
	/**
	 * removes first element placed in the queue. Returns the element
	 * 
	 * @return returns the element being dequeued
	 */
	public E dequeue();
	
	/**
	 * looks at the top element of the queue and returns it to view
	 * 
	 * 
	 * @return the top element of the queue
	 */
	public E peek();
	
}