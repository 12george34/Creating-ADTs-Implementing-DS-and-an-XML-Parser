package utilities;

/**
 * Code for linear first-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * @param <E>
 */
public interface QueueADT<E> {
	
	/**
	 * Constructor method to create a new stack object.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: A new empty queue is created with the specified maximum size.
	 * 
	 * @param size Sets maximum size for the stack.
	 */
	public void createQueue(int size);
	
	/**
	 * The size method will return the current element count contained in the queue.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The queue is unchanged. The current element count is returned.
	 * 
	 * @return The current element count.
	 */
	public int size();
	
	/**
	 * Removes all of the elements from this queue. This list will be empty after
	 * this call returns.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The queue is empty; size is 0.
	 */
	public void clear();
	
	/**
	 * Adds new element to the tail of the queue.
	 * 
	 * Pre-condition:  toADD is not null.
	 * Post-condition: toADD is placed at the tail of the queue; size increases by 1.
	 * 
	 * @param toADD Element to be added.
	 * @throws NullPointerException If the specified element is null.
	 */
	public void enqueue(E toADD) throws NullPointerException;
	
	/**
	 * Removes first element placed in the queue. Returns the element.
	 * 
	 * Pre-condition:  The queue is not empty.
	 * Post-condition: The head element is removed and returned; size decreases by 1.
	 * 
	 * @return Returns the element being dequeued.
	 * @throws exceptions.EmptyQueueException If the queue contains no elements.
	 */
	public E dequeue() throws exceptions.EmptyQueueException;
	
	/**
	 * Looks at the top element of the queue and returns it to view.
	 * 
	 * Pre-condition:  The queue is not empty.
	 * Post-condition: The queue is unchanged. The head element is returned.
	 * 
	 * @return The top element of the queue.
	 * @throws exceptions.EmptyQueueException If the queue contains no elements.
	 */
	public E peek() throws exceptions.EmptyQueueException;
	
	/**
	 * Compares this queue to another queue for equality.
	 * 
	 * Pre-condition:  queue2 is not null.
	 * Post-condition: Both queues are unchanged. Returns true if equal, false otherwise.
	 * 
	 * @param queue2 Queue to compare to current queue.
	 * @return Returns true if both queues are equal. False if either queue have a difference.
	 * @throws NullPointerException If the specified queue is null.
	 */
	public boolean equals(QueueADT<? extends E> queue2) throws NullPointerException;
	
	/**
	 * Returns true if this list contains no elements. False if it contains elements.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The queue is unchanged.
	 * 
	 * @return True if this list contains no elements. False if it contains elements.
	 */
	public boolean isEmpty();
	
}
