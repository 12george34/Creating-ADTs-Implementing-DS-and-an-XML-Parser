package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E> {
	
	
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	

	public MyDLL() {
		head = null;
		tail = null;
		size = 0;
	}
	
	
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
	

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
		
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) throw new NullPointerException();
		if(index < 0 || index > size) throw new IndexOutOfBoundsException();
		
		MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);
		
		if(size == 0) {
			head = newNode;
			tail = newNode;
		} else if(index == 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		} else if(index == size) {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		} else {
			MyDLLNode<E> current = getNode(index);
			newNode.next = current;
			newNode.prev = current.prev;
			current.prev.next = newNode;
			current.prev = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		MyDLLNode<E> newNode = new MyDLLNode<> (toAdd);
		if(size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		Iterator<? extends E> it = toAdd.iterator();
		while(it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return getNode(index).data;
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		MyDLLNode<E> toRemove = getNode(index);
		
		if(size == 1) {
			head = null;
			tail = null;
		} else if(toRemove == head) {
			head = head.next;
			head.prev = null;
		} else if(toRemove == tail ) {
			tail = tail.prev;
			tail.next = null;
		} else {
			toRemove.prev.next = toRemove.next;
			toRemove.next.prev = toRemove.prev;
		}
		size--;
		return toRemove.data;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		if(toRemove == null) throw new NullPointerException();
		MyDLLNode<E> current = head;
		int index = 0;
		while(current != null) {
			if(toRemove.equals(current.data)) return remove(index);
			current = current.next;
			index++;
		}
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		if(toChange == null) throw new NullPointerException();
		MyDLLNode<E> node = getNode(index);
		E old = node.data;
		node.data = toChange;
		return old;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();
		MyDLLNode<E> current = head;
		while(current != null) {
			if(toFind.equals(current.data))  return true;
			current = current.next;
		}
		return false;
	}

	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
		if(toHold == null) throw new NullPointerException();
		if(toHold.length < size) toHold = Arrays.copyOf(toHold, size);
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
		Object[] result = new Object[size];
		MyDLLNode<E> current = head;
		int i = 0;
		while(current != null) {
			result[i++] = current.data;
			current = current.next;
		}
		return result;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private MyDLLNode<E> current = head;

			@Override
			public boolean hasNext() {
				return current != null;
			}

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
