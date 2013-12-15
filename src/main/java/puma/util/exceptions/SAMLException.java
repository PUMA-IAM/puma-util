package puma.util.exceptions;

public class SAMLException extends Exception {
	private static final long serialVersionUID = 9031183137418102255L;
	
	public SAMLException(Exception enclosedException) {
		super(enclosedException);
	}
}
