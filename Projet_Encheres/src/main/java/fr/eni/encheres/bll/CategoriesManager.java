package fr.eni.encheres.bll;


import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.daos.CategoriesDAO;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.DAOFactory;

public class CategoriesManager {
	private static CategoriesDAO categoriesDAO = null;
	
	private static CategoriesManager instance = null;
	

	private CategoriesManager() {

	}

	public static CategoriesManager GetInstance() {

		if (instance == null) {
			instance = new CategoriesManager();
		}

		if(categoriesDAO == null) {
			try {
				categoriesDAO = (CategoriesDAO) DAOFactory.getCategoriesDAO();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}


		return instance;
	}

	
	public Categorie createCategorie(String etiquette, String libelle, List<String> erreurs) {
		
		Categorie categorie = new Categorie(
				etiquette,
				libelle
		);
		
		validerCategorie(categorie, erreurs);

		if(erreurs.size() > 0) {
			return null;
		}

		try {
			
			categoriesDAO.add(categorie);
		} catch (DALException e) {
			
			erreurs.add(e.getLocalizedMessage());
			categorie = null;
		}
	
		return categorie;
	}
	
	public void supprimerCategorie(Categorie categorie) throws BLLException {
		
		try {
			categoriesDAO.remove(categorie);
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}
	}

	public Categorie getCategorieById(int categorieId) throws BLLException {
		
		Categorie categorie = null;
		
		try {
			categorie = categoriesDAO.getById(categorieId);
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}

		return categorie;
	}

	public Categorie getByEtiquette(String libelle, Erreurs erreurs) {

		Categorie categorie = null;

		try {
			categorie = categoriesDAO.getByEtiquette(libelle);
		} catch (DALException e) {
			erreurs.addErreur("En cherchant une categorie par etiquette: " +e.getLocalizedMessage());
		}

		return categorie;
	}

	public List<Categorie> getAllCategories(Erreurs erreurs) {
		
		try {
			return categoriesDAO.getAll();
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
			return new ArrayList<>();
		}
	}

	public void sauvegarderCategorie(Categorie categorie, List<String> erreurs) throws BLLException {
		
		validerCategorie(categorie, erreurs);
		
		try {
			categoriesDAO.update(categorie);
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}
	}

	private void validerCategorie(Categorie categorie, List<String> erreurs) {
		
		if (categorie.getLibelle() == null) {
			erreurs.add("Le libellé de categorie ne peut pas être vide");
		}
	}

	public Object getCategorieById(String idCat, Erreurs erreurs) {
		if(idCat == null) {
			return new Categorie();
		}
		int idCatInt = -1;
		try {
			idCatInt = Integer.parseInt(idCat);
		} catch (NumberFormatException e) {
			erreurs.addErreur("nombre malformaté");
		}
		try {
			return getCategorieById(idCatInt);
		} catch (BLLException e) {
			erreurs.addErreur(e.getLocalizedMessage());
			return new Categorie();
		}
	}
}
