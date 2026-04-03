package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * This class implements a doubly linked list.
 *
 * @param <E> the type of elements stored in the list
 */
public class MyDLL<E> implements ListADT<E> {
	
	
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	

	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Finds and returns the node at the given index.
	 */
	private MyDLLNode<E> getNode(int index){
		MyDLLNode<E> current;
		if(index < size / 2) {
			current = head;
			for(int i = 0; i < index; i++)
				current = current.next;
		} else {
			current = tail;
			for(int i = size - 1; i > index; i--)
				current = current.prev;
		}
		return current;
	}
	
	// returns the current size of the list
	@Override
	public int size() {
		return size;
	}

	// returns the list cleared of everything
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
		
	}

	// first captures if there are any exceptions
	
	
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) throw new NullPointerException();
		if(index < 0 || index > size) throw new IndexOutOfBoundsException();
		
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		// if no nodes present that new head and tail are added
		if(size == 0) {
			head = newNode;
			tail = newNode;
		// new node is added to the front by
		// making a new spot
		// head node now placed back
		// new node is the head node
		} else if(index == 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		// adds a new node at the tail section by
		// making a new place for the node 
		// placing tail node as a space back
		// making new node the tail node
		} else if(index == size) {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
			
		} else {
			MyDLLNode<E> current = getNode(index);
			// Insert the new node between two existing nodes
			newNode.next = current;
			newNode.prev = current.prev;
			current.prev.next = newNode;
			current.prev = newNode;
		}
		size++;
		return true;
	}

	
	// this throws an exceptions handler
	
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		MyDLLNode<E> newNode = new MyDLLNode<> (toAdd);
		// if list is empty a new head and tail are placed
		if(size == 0) {
			head = newNode;
			tail = newNode;
		} 
		// if nodes already exist a new one is placed in the tail section
		else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}


	// throws exceptions handler
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		Iterator<? extends E> it = toAdd.iterator();
		// This method takes another list and adds all of its elements to the end of our list, one at a time.
		while(it.hasNext()) {
			add(it.next());
		}
		return true;
	}


	// thorws and exception handler
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		// uses getNode method to find the nodes at the index and return the data
		return getNode(index).data;
	}


	// throws exception handler
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		MyDLLNode<E> toRemove = getNode(index);

		// this makes the list empty
		if(size == 1) {
			head = null;
			tail = null;

		// this deletes the head and makes the previous node the new head
		} else if(toRemove == head) {
			head = head.next;
			head.prev = null;

		// this removes the tail and make the previous tail the new head tail
		} else if(toRemove == tail ) {
			tail = tail.prev;
			tail.next = null;
		} else {
			// Reconnect the previous and next nodes
			toRemove.prev.next = toRemove.next;
			toRemove.next.prev = toRemove.prev;
		}
		size--;
		return toRemove.data;
	}


	// throws exception hangler
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if(toRemove == null) throw new NullPointerException();
		MyDLLNode<E> current = head;
		// 	starts at the head goes one node at a time looking for anything that has toRemove and removes that node 
		int index = 0;
		while(current != null) {
			if(toRemove.equals(current.data)) return remove(index);
			current = current.next;
			index++;
		}
		return null;
	}


	// throws exception handler
	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		if(toChange == null) throw new NullPointerException();

		// takes a selected node as old and changes its data to the new node
		MyDLLNode<E> node = getNode(index);
		E old = node.data;
		node.data = toChange;
		return old;
	}


	// checks if the list is empty
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	// thorws exception handler
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();

		// checks if the data exists in the list
		MyDLLNode<E> current = head;
		while(current != null) {
			if(toFind.equals(current.data))  return true;
			current = current.next;
		}
		return false;
	}


	// thorws a exception handler
	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if(toHold == null) throw new NullPointerException();
		if(toHold.length < size) toHold = Arrays.copyOf(toHold, size);
		// goes through list copy each node into a new array one at a time 
		MyDLLNode<E> current = head;
		int i = 0;
		while(current != null) {
			toHold[i++] = current.data;
			current = current.next;
		}
		return toHold;
	}


	
	@Override
	public Object[] toArray() {
		// makes a new array list 
		Object[] result = new Object[size];
		// Walks through the list copying each value into the array.
		MyDLLNode<E> current = head;
		int i = 0;
		while(current != null) {
			result[i++] = current.data;
			current = current.next;
		}
		return result;
	}


	// allows to go through a list one node at a time
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private MyDLLNode<E> current = head;

			// checks if there are anymore nodes left to visit
			@Override
			public boolean hasNext() {
				return current != null;
			}

			// returns current node data and moves on to the next one to repeat the process
			@Override
			public E next() throws NoSuchElementException {
				if(!hasNext()) throw new NoSuchElementException();
				E data = current.data;
				current = current.next;
				return data;
			}
			
		};
	}

}
