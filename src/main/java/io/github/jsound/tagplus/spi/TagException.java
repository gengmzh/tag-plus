/*
 * 
 */
package io.github.jsound.tagplus.spi;

/**
 * This is the exception that's generated while reading or writing tag failed, and the application might want to catch.
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class TagException extends Exception {

	private static final long serialVersionUID = -505336593035862656L;

	/**
	 * Create a new {@link TagException}.
	 */
	public TagException() {
		super();
	}

	/**
	 * Create a new {@link TagException} with the specified message.
	 */
	public TagException(String message) {
		super(message);
	}

	/**
	 * Create a new {@link TagException} with the specified cause.
	 */
	public TagException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new {@link TagException} with the specified detail message and cause.
	 */
	public TagException(String message, Throwable cause) {
		super(message, cause);
	}

}
