package fr.eni.encheres.dal;

public class DALException extends Exception {

	private static final long serialVersionUID = 1L;

	public DALException(String message, Exception e1) {
		super(message, e1);
	}

	@Override
	public String getMessage() {
		return "Couche DAL - " + super.getMessage();
	}

}
