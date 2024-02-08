package br.com.jgb.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends EntityNotFoundException {

    /**
	 * class ObjectNotFoundException
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
        super(message);
    }

}
