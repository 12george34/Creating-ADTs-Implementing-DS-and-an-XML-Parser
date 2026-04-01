package exceptions;

@SuppressWarnings("serial")
public class EmptyStackException extends Exception {

	public EmptyStackException() {
		super("Stack is currently empty.");
	}

	public EmptyStackException(String message) {
		super(message);
	}
}
