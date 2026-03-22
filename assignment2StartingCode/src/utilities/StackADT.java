package utilities;

/**
 * Code for linear last-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * 
 * @param <E> The type of elements contained in this stack
 */
public interface StackADT<E> {
	
	/**
	 * Constructor method to create a new stack object.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: A new empty stack is created with the specified maximum size.
	 * 
	 * @param size Sets maximum size for the stack.
	 */
	public void createStack(int size);
	
	/**
	 * The size method will return the current element count contained in the list.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The stack is unchanged. The current element count is returned.
	 * 
	 * @return The current element count.
	 */
	public int size();
	
	/**
	 * Removes all of the elements from this stack. This list will be empty after
	 * this call returns.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The stack is empty; size is 0.
	 */
	public void clear();
	
	/**
	 * Inserts element to top of the stack.
	 * 
	 * Pre-condition:  toAdd is not null.
	 * Post-condition: toAdd is placed on the top of the stack; size increases by 1.
	 * 
	 * @param toAdd Element to be added to top of the stack.
	 * @throws NullPointerException If the specified element is null.
	 */
	public void push(E toAdd) throws NullPointerException;
	
	/**
	 * Pops the top element of the stack off the stack and returns the element.
	 * 
	 * Pre-condition:  The stack is not empty.
	 * Post-condition: The top element is removed and returned; size decreases by 1.
	 * 
	 * @return Returns the element being popped.
	 * @throws java.util.EmptyStackException If the stack contains no elements.
	 */
	public E pop() throws java.util.EmptyStackException;
	
	/**
	 * Views the top element of the stack.
	 * 
	 * Pre-condition:  The stack is not empty.
	 * Post-condition: The stack is unchanged. The top element is returned.
	 * 
	 * @return Returns the top element of the stack.
	 * @throws java.util.EmptyStackException If the stack contains no elements.
	 */
	public E peek() throws java.util.EmptyStackException;
	
	/**
	 * Compares this stack to another stack for equality.
	 * 
	 * Pre-condition:  stack2 is not null.
	 * Post-condition: Both stacks are unchanged. Returns true if equal, false otherwise.
	 * 
	 * @param stack2 Stack to compare to current stack.
	 * @return Returns true if both stacks are equal. False if either stack have a difference.
	 * @throws NullPointerException If the specified stack is null.
	 */
	public boolean equals(StackADT<? extends E> stack2) throws NullPointerException;
	
	/**
	 * Returns true if this list contains no elements. False if it contains elements.
	 * 
	 * Pre-condition:  None.
	 * Post-condition: The stack is unchanged.
	 * 
	 * @return True if this list contains no elements. False if it contains elements.
	 */
	public boolean isEmpty();
	
}
