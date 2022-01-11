package fr.eni.encheres.dal.daos;

//@author Frederic


import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;

public interface CategoriesDAO extends DAO<Categorie> {

	Categorie getById(int categorieId) throws DALException;
	Categorie getByEtiquette(String libelle) throws DALException;

	
}
