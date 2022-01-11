package fr.eni.encheres.bll;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.DAOFactory;
import fr.eni.encheres.dal.daos.EncheresDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Enchere;


public class EncheresManager {

	private static EncheresDAO daoEnchere = null;
	private static EncheresManager instance = null;
		
	private EncheresManager() {
	}

	public static EncheresManager GetInstance() {
		if(instance == null) {
			instance = new EncheresManager();
		}

		if(daoEnchere == null) {
			try {
				daoEnchere = (EncheresDAO) DAOFactory.getEncheresDAO();
			} catch (DALException ex) {
				ex.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Valider les données d'une enchère
	 */
	public void validerEnchere(Enchere e, Erreurs erreurs)
	{
		boolean valide = true;
		StringBuffer sb = new StringBuffer();
		
		if(e==null){
			erreurs.addErreur("Enchère null");
		}
		//Les attributs des enchères sont obligatoires
		//le numéro utilisateur ne peut pas être inférieur ou égal à 0. Il est récupéré via la connection au compte utilisateur dans ValiderOffreServlet
		if(e.getUtilisateur().getNoUtilisateur()<=0){
			sb.append("Le numéro d'utilisateur est obligatoire.\n");
			valide = false;
		}
		//le numéro article ne peut pas être inférieur ou égal à 0. Il est récupéré via la sélection de l'article par l'utilisateur au moment de faire l'enchère
		if(e.getArticle().getNoArticle()<=0){
			sb.append("Le numéro de l'article est obligatoire.\n");
			valide = false;
		}
		//le montant de l'enchère ne peut pas être inférieur ou égal à 0. L'utilisateur entre le montant souhaité
		if(e.getMontantEnchere()<=0){
			sb.append("Le montant de l'enchère doit être supérieur à zéro.\n");
			valide = false;
		}
		//la date de l'enchère ne peut pas être null. Elle est instanciée dans ValiderOffreServlet
		if(e.getDateEnchere() == null){
			sb.append("Date invalide.\n");
			valide = false;
		} else {
			if(!e.aDebute()) {
				sb.append("L'enchère n'a pas débuté");
				valide = false;
			}
			if(e.estFinie()) {
				sb.append("L'enchère est finie");
				valide = false;
			}
		}
		
		//l'heure de l'enchère ne peut pas être null. Elle est instanciée dans ValiderOffreServlet
			if(e.getHeureEnchere() == null){
				sb.append("Heure invalide.\n");
				valide = false;
			}

		Integer meilleureOffre = -1;
		try {
			meilleureOffre = getMeilleureOffre(e.getArticle().getNoArticle()) + 1;
		} catch (BLLException ex) {
			// si le jeu d'encheres est vide, aucune enchère existe
			// on met l'offre actuelle à sa mise à prix.
			meilleureOffre = e.getArticle().getMiseAPrix() + 1;
		}
		if(meilleureOffre < e.getArticle().getMiseAPrix()) {
			meilleureOffre = e.getArticle().getMiseAPrix() + 1;
		}

		if(e.getMontantEnchere() < meilleureOffre) {
			sb.append("L'offre est plus petite ou égale à la meilleur enchère actuelle");
			valide = false;
		}

		if(e.getMontantEnchere() > e.getUtilisateur().getCredit()) {
			sb.append("Vous n'avez pas assez de credits");
			valide = false;
		}



		if(!valide){
			erreurs.addErreur(sb.toString());
		}


	}
	
	/**
	 * Ajout d'une enchère
	 */
	public void addEnchere(Enchere newEnchere, Erreurs erreurs) {
		try {
			validerEnchere(newEnchere, erreurs);
			if(erreurs.hasErrors()) {
				return;
			}

			daoEnchere.add(newEnchere);

			newEnchere.getUtilisateur().enleverCredit(newEnchere.getMontantEnchere());
			UtilisateursManager.GetInstance().sauvegarderUtilisateur(newEnchere.getUtilisateur(), erreurs);

		} catch (DALException e) {
			erreurs.addErreur("Echec addEnchere " + e.getLocalizedMessage());
		}
	}

	/**
	 * updateEnchere : Modifier une enchère
	 */
	public void updateEnchere(Enchere enchere, Erreurs erreurs) {
		try {
			validerEnchere(enchere, erreurs);

			if(erreurs.hasErrors()) {
				return;
			}
			Enchere ancienneEnchere = daoEnchere.selectEnchereByNoArticle(enchere.getArticle().getNoArticle(), erreurs);
			Utilisateur ancienUtilisateur = UtilisateursManager.GetInstance().getUtilisateurById(
					ancienneEnchere.getUtilisateur().getNoUtilisateur(), erreurs
			);
			ancienUtilisateur.ajouterCredit(ancienneEnchere.getMontantEnchere());
			UtilisateursManager.GetInstance().sauvegarderUtilisateur(ancienUtilisateur, erreurs);

			daoEnchere.update(enchere);

			Utilisateur nouvelUtilisateur = UtilisateursManager.GetInstance().getUtilisateurById(
					enchere.getUtilisateur().getNoUtilisateur(), erreurs);
			nouvelUtilisateur.enleverCredit(enchere.getMontantEnchere());
			UtilisateursManager.GetInstance().sauvegarderUtilisateur(nouvelUtilisateur, erreurs);

		} catch (DALException e) {
			erreurs.addErreur("Echec updateEnchere-enchère: "+ enchere + " " + e.getLocalizedMessage());
		}
	}

	/**
	 * removeEnchere : Supprimer une enchère
	 */
	public void removeEnchere(int noUtilisateur, int noArticle, Erreurs erreurs) throws BLLException{
		try {
			Enchere ancienneEnchere = daoEnchere.selectEnchereByNoArticle(noArticle, erreurs);
			Utilisateur ancienUtilisateur = ancienneEnchere.getUtilisateur();
			ancienUtilisateur.ajouterCredit(ancienneEnchere.getMontantEnchere());

			daoEnchere.deleteEnchereByNoUtilisateurEtNoArticle(noUtilisateur, noArticle);

		} catch (DALException e) {
			erreurs.addErreur("Echec removeEnchere - " + e.getLocalizedMessage());
		}

	}

	/**
	 * getAllEncheres : Afficher toutes les enchères existantes
	 */
		public List<Enchere> getAllEncheres() throws BLLException{
			List<Enchere> encheres=null;
		try {
			
			encheres = daoEnchere.getAll();
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Erreur récupération liste de toutes les enchères", e);
		}
		
		return encheres;
	}
	
	/**
	 * getEncheresByNoUtilisateur : Afficher toutes les enchères faites par un utilisateur
	 */
		public List<Enchere> getEncheresByUtilisateur(Utilisateur utilisateur, Erreurs erreurs) {
			List<Enchere> encheres=new ArrayList<>();
			try {
				encheres = daoEnchere.selectEncheresByNoUtilisateur(utilisateur.getNoUtilisateur(), erreurs);
			} catch (DALException e) {
				erreurs.addErreur("Erreur récupération liste enchères de l'utilisateur " + e.getLocalizedMessage());
			}
			
			return encheres;
		}
	
	/**
	 * getEncheresByNoArticle : Afficher la plus haute enchere faites sur un article
	 */
		public Enchere getEnchereByNoArticle(int noArticle, Erreurs erreurs) {
			Enchere enchere=null;
			try {
				enchere = daoEnchere.selectEnchereByNoArticle(noArticle, erreurs);
			} catch (DALException e) {
				erreurs.addErreur("Erreur récupération liste enchères de l'article " + e.getLocalizedMessage());
			}
			
			return enchere;
		}

	/**
	 * getEncheresByNoUtlisateurEtNoArticle : Afficher toutes les enchères faites par un utilisateur sur un article
	 */
	public Enchere getEncheresByNoUtlisateurEtNoArticle(int noUtilisateur, int noArticle, Erreurs erreurs) throws BLLException{
		Enchere encheres=null;
		try {
			encheres =  daoEnchere.selectEnchereByNoUtilisateurEtNoArticle(noUtilisateur, noArticle, erreurs);
		} catch (DALException e) {
			e.printStackTrace();
			throw new BLLException("Erreur récupération liste enchères de l'utilisateur sur l'article", e);
		}

		return encheres;
	}
	
	

	/**
	 * selectMeilleureOffre : récupérer le montant de la plus haute offre faite sur un article
	 */
	public int getMeilleureOffre(int noArticle) throws BLLException{
		int meilleureOffre = -1;
		try {
			meilleureOffre = daoEnchere.selectMeilleureOffreByNoArticle(noArticle);
		} catch (DALException e) {
			throw new BLLException("Echec selectMeilleureOffre - " + e.getLocalizedMessage(), e);
		}
		return meilleureOffre;
	}

	public int getMeilleureOffre(int noArticle, Erreurs erreurs) {
		int meilleureOffre = 0;
		try {
			meilleureOffre = daoEnchere.selectMeilleureOffreByNoArticle(noArticle);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
		return meilleureOffre;
	}

	/**
	 * Essaye de creer une enchère, sans en instancier une
	 */
	public void essayerCreerEnchere(Article article, Utilisateur utilisateur, Integer montant, Erreurs erreurs) {
		if(article == null) {
			erreurs.addErreur("Article n'est pas trouvé.");
		}
		if(utilisateur == null) {
			erreurs.addErreur("Utilisateur pas trouvé.");
		}
		if(article != null && utilisateur != null){
			if(!utilisateur.estConnecte()) {
				erreurs.addErreur("Un utilisateur non connecté ne peut pas enchérir");
			}
			if (article.getUtilisateur().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				erreurs.addErreur("Le propriétaire de la vente ne peut pas enchérir dessus.");
			}
		}

		if(erreurs.hasErrors()) {
			return;
		}

		mettreEnchere(article, utilisateur, montant, erreurs);
	}

	private void mettreEnchere(Article article, Utilisateur utilisateur, int montant, Erreurs erreurs) {
		Enchere ancienneEnchere = null;
		try {
			ancienneEnchere = daoEnchere.selectEnchereByNoArticle(article.getNoArticle(), erreurs);
			if(ancienneEnchere != null) {
				ancienneEnchere.setMontantEnchere(montant);
				ancienneEnchere.setUtilisateur(utilisateur);
			}
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}

		if(erreurs.hasErrors()) {
			return;
		}

		if(ancienneEnchere == null) {
			addEnchere(new Enchere(utilisateur, article, LocalDate.now(), LocalTime.now(), montant), erreurs);
		} else {
			updateEnchere(ancienneEnchere, erreurs);
		}
	}
}
