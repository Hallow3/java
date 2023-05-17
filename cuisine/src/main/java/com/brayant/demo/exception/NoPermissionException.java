package com.brayant.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoPermissionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3622080945813490122L;

	public NoPermissionException(String message) {
		super(message);
	}
}
