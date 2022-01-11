package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.DAOFactory;
import fr.eni.encheres.dal.daos.UtilisateursDAO;

public class UtilisateursManager {
	
	private static UtilisateursDAO utilisateursDAO;
	private static UtilisateursManager instance = null;

	private UtilisateursManager() {
	}

	public static UtilisateursManager GetInstance() {

		if(utilisateursDAO == null) {
			try {
				utilisateursDAO = (UtilisateursDAO) DAOFactory.getUtilisateursDAO();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}

		if (instance == null) {
			instance = new UtilisateursManager();
		}
		
		return instance;
	}

	public Utilisateur createUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, int credit, boolean administrateur, boolean actif, Erreurs erreurs) {
		
		Utilisateur utilisateur = new Utilisateur(
				pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit, administrateur, actif
		);

		validerMotDePasse(motDePasse, erreurs);

		validerUtilisateurUnique(utilisateur, erreurs);

		validerUtilisateur(utilisateur, erreurs);

		// On retourne un Utilisateur meme si on l'ajout pas à la DAO histoire
		// de pouvoir préremplir le formulaire à l'essai suivant

		if(erreurs.hasErrors()) {
			return utilisateur;
		}

		try {
			
			utilisateursDAO.addUtilisateurSecurise(utilisateur, motDePasse);
		} catch (DALException e) {
			
			erreurs.addErreur(e.getLocalizedMessage());
			utilisateur = null;
		}
	
		return utilisateur;
	}

	// Verifie que le nom d'utilisateur n'est pas déjà pris
	private void validerUtilisateurUnique(Utilisateur utilisateur, Erreurs erreurs) {
		try {
			Utilisateur existant = utilisateursDAO.getByPseudo(utilisateur.getPseudo());
			if(existant != null) {
				erreurs.addErreur("Cet utilisateur existe deja");
			}
		} catch (DALException e) {
			// normalement si la requete SQL lève une exception, c'est qu'aucun utilisateur
			// avec ce pseudo n'a été trouvé donc c'est ok, et on fait rien.
		}
	}

	public Utilisateur createUtilisateurDepuisLeWeb(String pseudo, String nom, String prenom, 
			String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, String credit, String administrateur, String actif, Erreurs erreurs) {
		
		if(pseudo == null) erreurs.addErreur("Le pseudo doit être renseigné");
		if(nom == null) erreurs.addErreur("Le nom doit être renseigné");
		if(prenom == null) erreurs.addErreur("Le prenom doit être renseigné");
		if(email == null) erreurs.addErreur("L'email doit être renseigné");
		if(telephone == null) erreurs.addErreur("Le telephone doit être renseigné");
		if(rue == null) erreurs.addErreur("La rue doit être renseignée");
		if(codePostal == null) erreurs.addErreur("Le code postal doit être renseigné");
		if(ville == null) erreurs.addErreur("La ville doit être renseignée");
		if(motDePasse == null) erreurs.addErreur("Le mot de passe doit être renseigné");
		if(credit == null) erreurs.addErreur("Le credit doit être renseigné");
		
		boolean is_admin = false;
		
		if(administrateur == null) {
			is_admin = false;
		} else {
			if(administrateur.equalsIgnoreCase("on")) {
				is_admin = true;
			} else if (administrateur.equalsIgnoreCase("off")) {
				is_admin = false;
			} else {
				erreurs.addErreur("Statut administrateur indefini");
			}
		}

		boolean is_actif = false;

		if(actif == null) {
			is_actif = false;
		} else {
			if(actif.equalsIgnoreCase("on")) {
				is_actif = true;
			} else if (actif.equalsIgnoreCase("off")) {
				is_actif = false;
			} else {
				erreurs.addErreur("Statut actif indefini");
			}
		}


		Integer credit_int = 0;
		
		try{
			credit_int = Integer.parseInt(credit);
		} catch(NumberFormatException e) {
			erreurs.addErreur("Impossible de convertir le crédit en nombre entier");
		}
		
		if (erreurs.hasErrors()) return null;
		
		return createUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit_int, is_admin, is_actif, erreurs);
	}

	public void modifUtilisateurDepuisLeWeb(Utilisateur utilisateur, String pseudo, String nom, String prenom, 
			String email, String telephone, String rue,
			String codePostal, String ville, String credit, String administrateur, String actif, Erreurs erreurs) {
		
		if(pseudo == null) erreurs.addErreur("Le pseudo doit être renseigné");
		if(nom == null) erreurs.addErreur("Le nom doit être renseigné");
		if(prenom == null) erreurs.addErreur("Le prenom doit être renseigné");
		if(email == null) erreurs.addErreur("L'email doit être renseigné");
		if(telephone == null) erreurs.addErreur("Le telephone doit être renseigné");
		if(rue == null) erreurs.addErreur("La rue doit être renseignée");
		if(codePostal == null) erreurs.addErreur("Le code postal doit être renseigné");
		if(ville == null) erreurs.addErreur("La ville doit être renseignée");
		if(credit == null) erreurs.addErreur("Le credit doit être renseigné");
		
		boolean is_admin = false;
		if(administrateur == null) {
			is_admin = false;
		} else {
			if(administrateur.equalsIgnoreCase("on")) {
				is_admin = true;
			} else if (administrateur.equalsIgnoreCase("off")) {
				is_admin = false;
			} else {
				erreurs.addErreur("Statut administrateur indefini");
			}
		}

		boolean is_actif = false;

		if(actif == null) {
			is_actif = false;
		} else {
			if(actif.equalsIgnoreCase("on")) {
				is_actif = true;
			} else if (actif.equalsIgnoreCase("off")) {
				is_actif = false;
			} else {
				erreurs.addErreur("Statut actif indefini");
			}
		}


		Integer credit_int = 0;
		try{
			credit_int = Integer.parseInt(credit);
		} catch(NumberFormatException e) {
			erreurs.addErreur("Impossible de convertir le crédit en nombre entier");
		}
		
		if (erreurs.hasErrors()) return;
		
		utilisateur.setPseudo(pseudo);
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setEmail(email);
		utilisateur.setTelephone(telephone);
		utilisateur.setRue(rue);
		utilisateur.setCodePostal(codePostal);
		utilisateur.setVille(ville);
		utilisateur.setCredit(credit_int);
		utilisateur.setAdministrateur(is_admin);
		utilisateur.setActif(is_actif);

		sauvegarderUtilisateur(utilisateur, erreurs);
	}

	
	public void supprimerUtilisateur(Utilisateur utilisateur) throws BLLException {
		
		try {
			utilisateursDAO.remove(utilisateur);
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}
	}

	public Utilisateur getUtilisateurById(int utilisateurId, Erreurs erreurs)  {
		
		Utilisateur utilisateur = new Utilisateur();

		getUtilisateurById(utilisateur, utilisateurId, erreurs);
		return utilisateur;
	}

	public void getUtilisateurById(Utilisateur utilisateur, int utilisateurId, Erreurs erreurs) {
		try {
			utilisateursDAO.getById(utilisateur, utilisateurId);
		} catch (DALException e) {
			erreurs.addErreur("En cherchant un utilisateur: " +e.getLocalizedMessage());
		}
	}

	public Utilisateur getUtilisateurAvecLoginMotDePasse(String utilisateurPseudo, String motDePasse, Erreurs erreurs) {

		Utilisateur existant = new Utilisateur();
		try {
			existant = utilisateursDAO.getByPseudoEtMotDePasse(utilisateurPseudo, motDePasse);
			if(existant == null) {
				erreurs.addErreur("Utilisateur impossible à trouver");
			}
			else if(!existant.isActif()) {
				erreurs.addErreur("Utilisateur supprimmé");
			}
		} catch (DALException e) {
			erreurs.addErreur("Utilisateur inexistant");
		}

		return existant;
	}

	public List<Utilisateur> getAllUtilisateur() throws BLLException {
		
		try {
			return utilisateursDAO.getAll();
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}
	}

	public void sauvegarderUtilisateur(Utilisateur utilisateur, Erreurs erreurs) {
		
		validerUtilisateur(utilisateur, erreurs);
		
		try {
			this.utilisateursDAO.update(utilisateur);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
	}

	private void validerUtilisateur(Utilisateur utilisateur, Erreurs erreurs) {
		
		if(utilisateur.getPseudo() == null) erreurs.addErreur("Le pseudo doit être renseigné");
		
		if(utilisateur.getNom() == null) erreurs.addErreur("Le nom doit être renseigné");
		
		if(utilisateur.getPrenom() == null) erreurs.addErreur("Le prenom doit être renseigné");
		
		if(utilisateur.getEmail() == null) erreurs.addErreur("L'email doit être renseigné");
		
		if(utilisateur.getTelephone() == null) erreurs.addErreur("Le telephone doit être renseigné");
		
		if(utilisateur.getRue() == null) erreurs.addErreur("La rue doit être renseignée");
		
		if(utilisateur.getCodePostal() == null) erreurs.addErreur("Le code postal doit être renseigné");
		
		if(utilisateur.getVille() == null) erreurs.addErreur("La ville doit être renseignée");
		
		if(erreurs.hasErrors()) {
			return;
		}

		if (utilisateur.getPseudo().length() < 1 || utilisateur.getPseudo().length() > 30) {
			erreurs.addErreur("Le pseudo a pas la bonne longueur.");
		}
		
		if (utilisateur.getNom().length() < 1 || utilisateur.getNom().length() > 50) {
			erreurs.addErreur("Le nom a pas la bonne longueur.");
		}
		
		if (utilisateur.getPrenom().length() < 1 || utilisateur.getPrenom().length() > 50) {
			erreurs.addErreur("Le prénom a pas la bonne longueur.");
		}
		
		if (utilisateur.getEmail().length() < 1 || utilisateur.getEmail().length() > 50) {
			erreurs.addErreur("L'email a pas la bonne longueur.");
		}
		
		if (utilisateur.getTelephone().length() < 1 || utilisateur.getTelephone().length() > 15) {
			erreurs.addErreur("Le téléphone a pas la bonne longueur.");
		}
		
		if (utilisateur.getRue().length() < 1 || utilisateur.getRue().length() > 250) {
			erreurs.addErreur("La rue a pas la bonne longueur.");
		}
		
		if (utilisateur.getCodePostal().length() != 5) {
			erreurs.addErreur("Le code postal a pas la bonne longueur.");
		}
		
		if (utilisateur.getVille().length() < 1 || utilisateur.getVille().length() > 50) {
			erreurs.addErreur("La ville a pas la bonne longueur.");
		}
	}
	
	private void validerMotDePasse(String motDePasse, Erreurs erreurs) {
		
		if(motDePasse == null) erreurs.addErreur("Le mot de passe doit être renseigné");
		
		if(erreurs.hasErrors()) {
			return;
		}

		if (motDePasse.length() < 6) {
			erreurs.addErreur("Le mot de passe doit faire 6 caractères au moins !");
		}
	}

	private void validerMotDePasseRepete(String motDePasse, String motDePasseRepete, Erreurs erreurs) {
		
		if(motDePasse == null) erreurs.addErreur("Le mot de passe doit être renseigné");
		
		if(motDePasseRepete == null) erreurs.addErreur("Le mot de passe répété doit être renseigné");

		if(erreurs.hasErrors()) {
			return;
		}
		
		if (motDePasse.length() < 6) {
			erreurs.addErreur("Le mot de passe doit faire 6 caractères au moins !");
		}
		
		if(!motDePasse.equals(motDePasseRepete)) {
			erreurs.addErreur("Le mot de passe répété doit être identique !");
		}
	}
	
	public Utilisateur traiteRequeteInscription(String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, String motDePasseRepete,
			Erreurs errors) {
		
		validerMotDePasseRepete(motDePasse, motDePasseRepete, errors);

		return createUtilisateur(
				pseudo, nom, prenom, email, telephone, rue,
				codePostal, ville, motDePasse, 100, false, true, errors);

	}

	public void modifieDepuisLeWeb(
			Utilisateur utilisateurConnecte, String pseudo, String nom, String prenom, String email,
			String telephone, String rue, String codePostal, String ville,
			String motDePasseOriginal, String motDePasse, String motDePasseRepete, Erreurs erreurs) {

		if(pseudo == null) erreurs.addErreur("Le pseudo doit être renseigné");
		if(nom == null) erreurs.addErreur("Le nom doit être renseigné");
		if(prenom == null) erreurs.addErreur("Le prenom doit être renseigné");
		if(email == null) erreurs.addErreur("L'email doit être renseigné");
		if(telephone == null) erreurs.addErreur("Le telephone doit être renseigné");
		if(rue == null) erreurs.addErreur("La rue doit être renseignée");
		if(codePostal == null) erreurs.addErreur("Le code postal doit être renseigné");
		if(ville == null) erreurs.addErreur("La ville doit être renseignée");
		if(motDePasseOriginal == null) erreurs.addErreur("Le mdp doit être renseigné");
		if(motDePasse == null) erreurs.addErreur("Le mdp doit être renseigné");
		if(motDePasseRepete == null) erreurs.addErreur("Le mdp doit être renseigné");

		if(erreurs.hasErrors()) return;

		utilisateurConnecte.setPseudo(pseudo);
		utilisateurConnecte.setNom(nom);
		utilisateurConnecte.setPrenom(prenom);
		utilisateurConnecte.setEmail(email);
		utilisateurConnecte.setTelephone(telephone);
		utilisateurConnecte.setRue(rue);
		utilisateurConnecte.setCodePostal(codePostal);
		utilisateurConnecte.setVille(ville);

		try {
			utilisateursDAO.update(utilisateurConnecte);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}

		if(erreurs.hasErrors()) return;

		if(motDePasseOriginal.length() > 0) {

			validerMotDePasseRepete(motDePasse, motDePasseRepete, erreurs);

			Utilisateur utilisateurValide = getUtilisateurAvecLoginMotDePasse(pseudo, motDePasseOriginal, erreurs);

			if(!erreurs.hasErrors()) {
				try {
					utilisateursDAO.changeMDP(utilisateurConnecte, motDePasse);
				} catch (DALException e) {
					erreurs.addErreur(e.getLocalizedMessage());
				}
			}
		}

	}

	public void retirerCredits(Utilisateur utilisateur, int creditsARetirer, Erreurs erreurs) {
		
		int ancienTotal = utilisateur.getCredit();
		//Si utilisateur.credit > 0
		if(ancienTotal - creditsARetirer >=0) {
			//appeler retirerCredits
			try {
				utilisateursDAO.retirerCredits(utilisateur, creditsARetirer);
			} catch (DALException ex) {
				erreurs.addErreur(ex.getLocalizedMessage());
			}
		}
		else {
			erreurs.addErreur("Vous n'avez pas assez de crédit");
		}

	}
	
}
