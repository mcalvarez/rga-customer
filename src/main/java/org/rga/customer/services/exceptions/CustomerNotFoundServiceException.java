package org.rga.customer.services.exceptions;

public class CustomerNotFoundServiceException extends
	ServiceException {

	public CustomerNotFoundServiceException() {
	}

	public CustomerNotFoundServiceException(String message) {
		super(message);
	}

	public CustomerNotFoundServiceException(Throwable cause) {
		super(cause);
	}

	public CustomerNotFoundServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerNotFoundServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
