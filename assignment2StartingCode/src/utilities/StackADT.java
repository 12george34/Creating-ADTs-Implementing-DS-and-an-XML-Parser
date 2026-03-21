
package utilities;
/**
 * Code for linear last-in-first-out data structure. All implementors required to implement all methods of the stack
 * 
 * 
 * @param <E> The type of elements contained in this stack
 */
public interface StackADT<E> {

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
	
}
