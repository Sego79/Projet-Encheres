// @author Lucie
package fr.eni.encheres.dal.daos;

import java.util.List;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.DALException;


public interface EncheresDAO extends DAO<Enchere> {
		
	public List<Enchere> selectEncheresByNoUtilisateur(int noUtilisateur, Erreurs erreurs) throws DALException;

	public Enchere selectEnchereByNoArticle(int noArticle, Erreurs erreurs) throws DALException;
	
	public Enchere selectEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle, Erreurs erreurs) throws DALException;

	public void deleteEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle) throws DALException;
	
	//@authorLucie ajout requete
	//pour récupérer le plus haut montant_enchere dans les offres faites sur un article
	public int selectMeilleureOffreByNoArticle(int noArticle) throws DALException;
}
