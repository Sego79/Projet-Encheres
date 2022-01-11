package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.misc.ArticleAchete;
import fr.eni.encheres.bo.misc.ArticleVendu;
import fr.eni.encheres.bo.misc.MeHasMany;

public class Utilisateur implements Serializable, MeHasMany<ArticleVendu> /*, MeHasMany<Encheres>, MeHasMany<ArticleAchete> */ {

	@Override
	public String toString() {
		return String.format(
				"Utilisateur : id %d, pseudo : %s, nom : %s, prenom : %s, email : %s, telephone : %s, rue : %s, code postal : %s, ville : %s, credit : %d, admin %b",
				noUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit, administrateur
		);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4374948417968605957L;

	// champs de la BDD
	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private int credit;
	private boolean administrateur;
	private boolean actif;
	
	// champs supplémentaires
	private List<Article> articlesVendus = null;
	private List<Article> articlesAchetes = null;
	private List<Enchere> encheres = null;


	public Utilisateur() {
		this(-1, "", "", "", "", "", "", "", "", 0, false, true);
	}


	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, int credit, boolean administrateur, boolean actif) {
		super();

		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.administrateur = administrateur;
		this.actif = actif;
	}
	
	//@author Lucie MAJ ajout constructeur
	public Utilisateur(int noUtilisateur) {
        this();
        this.noUtilisateur = noUtilisateur;
    }
	
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, int credit, boolean administrateur, boolean actif) {
		this(-1, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, credit, administrateur, actif);
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	
	@Override
	public void ajouter(ArticleVendu vente) {
		if(articlesVendus == null) {
			articlesVendus = new ArrayList<>();
		}
		
		articlesVendus.add(vente);
		
	}

	@Override
	public void supprimer(ArticleVendu vente) {
		articlesVendus.remove(vente);
	}
	
	public void ajouter(ArticleAchete achat) {
		if(articlesAchetes == null) {
			articlesAchetes = new ArrayList<>();
		}

		articlesAchetes.add(achat);
	}

	public void supprimer(ArticleAchete achat) {
		articlesAchetes.remove(achat);
	}
	

	public void ajouter(Enchere enchere) {
		if(encheres == null) {
			encheres = new ArrayList<>();
		}

		encheres.add(enchere);
	}
	
	public void supprimer(Enchere enchere) {
		encheres.remove(enchere);
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public boolean estConnecte() {
		return this.noUtilisateur != -1;
	}

	public void ajouterCredit(int montant) {
		this.credit += montant;
	}

	public void enleverCredit(int montant) {
		this.credit -= montant;
	}
}
