package br.com.jgb.api.exceptions;

public class BussinessException extends  RuntimeException{

    /**
	 * BussinessException
	 */
	private static final long serialVersionUID = 1L;
	//erro 500
	public BussinessException(String message){
        super(message);
    }
}
