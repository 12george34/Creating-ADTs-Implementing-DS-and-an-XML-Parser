
package utilities;
/**
 * Code for linear last-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * 
 * @param <E> The type of elements contained in this stack
 */
public interface StackADT<E> {

	
	/**
	 * The size method will return the current element count contained in the list.
	 * 
	 * @return The current element count.
	 */
	public int size();
	
	/**
	 * Removes all of the elements from this stack. This list will be empty after
	 * this call returns.
	 */
	public void clear();
	
	/**
	 * Inserts element to top of the stack
	 * 
	 * @param toAdd element to be added to top of the stack
	 */
	public void push(E toAdd);
	
	/**
	 * Pops the top element of the stack off the stack and returns the element
	 * 
	 * @return returns the element being popped
	 */
	public E pop();
	
	/**
	 * Views the top element of the stack.
	 * 
	 * @return returns the top element of the stack. 
	 */
	public E peek();
	
	/**
	 * 
	 * @param stack2 stack to compare to current stack
	 * @return returns true if both stacks are equal. False if either stack have a difference
	 */
	public boolean equals(StackADT<? extends E> stack2);
	
	/**
	 * Returns true if this list contains no elements. False if it contains elements
	 * 
	 * @return true if this list contains no elements. False if it contains elements
	 */
	public boolean isEmpty();
	
}
