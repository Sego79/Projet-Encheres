package fr.eni.encheres.bll;

import fr.eni.encheres.dal.DALException;

public class BLLException extends Exception {

	public BLLException(String string, DALException e) {
		super(string, e);
	}

	public BLLException(String string) {
		super(string);
	}

	@Override
	public String getMessage() {
		return "Couche BLL - " + super.getMessage();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// methode créée dans la servlet "afficherEncheresServlet"
	public Object getListeCodesErreur() {
		// TODO Auto-generated method stub
		return null;
	}

}
