package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * This class implements a FIFO queue using a doubly linked list.
 *
 * @param <E> the type of elements stored in the queue
 */
public class MyQueue<E> implements QueueADT<E>{
	
	private MyDLL<E> queue;
	
	public MyQueue() {
		queue = new MyDLL<>();
	}
	
	

	/**
	 * implements isEmpty method of QueueADT
	 * Adds an element to the end of the queue (FIFO).
	 *
	 * @param toAdd element to add to queue
	 */
	@Override
	public void enqueue(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		queue.add(toAdd);
		
	}

	/**
	 * implements dequeue method of QueueADT
	 * removes and returns first element of queue
	 *
	 * @return first element of queue
	 */
	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("Queue is empty");
		return queue.remove(0);
	}


	/**
	 * implements peek method of QueueADT
	 * Returns the front element without removing it.
	 *
	 * @return first element of queue
	 */
	@Override
	public E peek() throws EmptyQueueException {
		if(isEmpty()) throw new EmptyQueueException("Nothing in Queue");
		return queue.get(0);
	}

	/**
	 * implements dequeue method of QueueADT
	 * clears entire queue of elements
	 *
	 */
	@Override
	public void dequeueAll() {
		queue.clear();
	}

	/**
	 * implements isEmpty method of QueueADT
	 * verifies if queue is empty of elements
	 *
	 *
	 *@return true if queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * implements contains method of QueueADT
	 * searchs queue for element and returns true if found
	 *
	 * @param toFind element to find in queue
	 * @return true if element is found
	 *
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();
		return queue.contains(toFind);
	}

	/**
	 * implements search method of QueueADT
	 * searchs queue for element and returns index of first element match
	 *
	 * @param toFind element to find in queue
	 * @return index associated with found element in the queue or -1 if no element found
	 *
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

	/**
	*
	* implements Iterator method of QueueADT
	* utilizes underlying iterator subclass of MyDLL to generator an iterator of the queue
	*
	* @return the iterator of the queue
	*
	*/
	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	/**
	*
	* implements equals method of StackADT
	* utilizes underlying methods of MyDLL to compare two queues
	* checks if sizes of the two stacks are equal. if they are, compares the two queues element by elemeny
	*
	* @param that another queue to be compared with the current queue
	* @return true if both queues are equal
	*
	*/
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

	/**
	*
	* implements Object[] toArray() method of QueueADT
	* utilizes underlying methods of MyDLL to produce an array of objects that is a copy of the queue
	*
	* @return returns the array copy of the queue
	*
	*/
	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	/**
	*
	* implements E[] toArray() method of QueueADT
	* utilizes underlying methods of MyDLL to produce an array of elements that is a copy of the queue
	*
	* @param holder list of elements where the queue copy is to be reproduced and held
	* @return returns the array copy of the queue
	*
	*/
	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) throw new NullPointerException();
		return queue.toArray(holder);
	}
	/**
	* implements isFull method of QueueADT
	*
	* MyQueue built with unlimited size in mind, queue cannot be full
	*@return false as queue cannot be full as constructed
	*/
	@Override
	public boolean isFull() {
		return false;
	}
	
	/**
	 * implements size method of QueueADT
	 * returns internal size variable
	 *
	 * @return size of queue
	 */
	@Override
	public int size() {
		return queue.size();
	}
	
}
