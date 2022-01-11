package fr.eni.encheres.dal.daos;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DALException;

import java.util.List;

//@Frederic

public interface ArticlesDAO extends DAO<Article> {

	Article getById(int id) throws DALException;
	void getByIdAvecInstance(Article article, int id) throws DALException;

	Article getByCategorie(Categorie categorie) throws DALException;
	void getAllMemeFinis(List<Article> articles) throws DALException;
	void getAll(List<Article> articles) throws DALException; // getAll qui prend une list existante
}