package exceptions;

@SuppressWarnings("serial")
public class EmptyQueueException extends Exception {

	public EmptyQueueException() {
		super("Queue is currently empty.");
	}

	public EmptyQueueException(String message) {
		super(message);
	}
}
