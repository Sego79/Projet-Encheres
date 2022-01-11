package fr.eni.encheres.dal.daos;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.DAO;

/**
 * 
 * @author Sego
 *
 */

public interface RetraitsDAO extends DAO<Retrait> {

	public Retrait lieuRetrait(int noArticle) throws DALException;

	public Retrait getById(int idRetrait) throws DALException;

}
