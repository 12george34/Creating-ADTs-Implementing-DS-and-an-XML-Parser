package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * This class implements a queue using a doubly linked list.
 *
 * @param <E> the type of elements stored in the queue
 */
public class MyQueue<E> implements QueueADT<E>{
	
	private MyDLL<E> queue;
	
	public MyQueue() {
		queue = new MyDLL<>();
	}
	
	

	/**
	 * Adds an element to the end of the queue (FIFO).
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		queue.add(toAdd);
		
	}

	/**
	 * Removes and returns the front element of the queue.
	 */
	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("Queue is empty");
		return queue.remove(0);
	}

	/**
	 * Returns the front element without removing it.
	 */
	@Override
	public E peek() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("Nothing in Queue");
		return queue.get(0);
	}

	@Override
	public void dequeueAll() {
		queue.clear();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();
		return queue.contains(toFind);
	}

	/**
	 * Returns the position of the element in the queue (1-based index).
	 */
	@Override
	public int search(E toFind) {
		Iterator<E> it = queue.iterator();
		int pos = 1;
		while (it.hasNext()) {
			if(it.next().equals(toFind)) return pos;
			pos++;
		}
		return -1;
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public boolean equals(QueueADT<E> that) {
		if(that == null || this.size() != that.size()) return false;
		Iterator<E> thisIt = this.iterator();
		Iterator<E> thatIt = that.iterator();
		while(thisIt.hasNext()) {
			if(!thisIt.next().equals(thatIt.next())) return false;
		}
		return true;
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) throw new NullPointerException();
		return queue.toArray(holder);
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public int size() {
		return queue.size();
	}
	
}
