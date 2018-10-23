package fr.deboissieu.calculassmat.commons.exceptions;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -3693903156132234733L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}