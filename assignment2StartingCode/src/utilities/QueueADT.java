package utilities;

/**
 * Code for linear first-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * @param <E>
 */
public interface QueueADT<E> {
	
	
	/**
	 * The size method will return the current element count contained in the queue.
	 * 
	 * @return The current element count.
	 */
	public int size();
	
	/**
	 * Removes all of the elements from this queue. This list will be empty after
	 * this call returns.
	 */
	public void clear();
	
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
	
	/**
	 * 
	 * @param queue2 queue to compare to current queue
	 * @return returns true if both queues are equal. False if either queue have a difference
	 */
	public boolean equals(QueueADT<? extends E> queue2);
	
	/**
	 * Returns true if this list contains no elements. False if it contains elements
	 * 
	 * @return true if this list contains no elements. False if it contains elements
	 */
	public boolean isEmpty();
	
}