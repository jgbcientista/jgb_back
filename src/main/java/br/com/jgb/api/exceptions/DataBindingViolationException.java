package br.com.jgb.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataBindingViolationException extends DataIntegrityViolationException {

    /**
	 * class DataBindingViolationException
	 */
	private static final long serialVersionUID = 1L;

	public DataBindingViolationException(String message) {
        super(message);
    }

}
