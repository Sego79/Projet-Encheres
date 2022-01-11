package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.misc.MeHasMany;

/**
 * 
 * @author Sego
 *
 */

public class Retrait implements Serializable, MeHasMany<Article> {

	private static final long serialVersionUID = 3153865924155982476L;

	// champs de la BDD
	private int noArticle;
	private int idRetrait;
	private String rue;
	private String codePostal;
	private String ville;

	// champs supplémentaires
	private List<Article> lieuRetrait = null;

	/**
	 * Constructeur
	 */
	public Retrait() {
	}

	/**
	 * Constructeur
	 * 
	 * @param rue
	 * @param codePostal
	 * @param ville
	 */
	public Retrait(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	/**
	 * Constructeur
	 * 
	 * @param noArticle
	 * @param IdRetrait
	 * @param rue
	 * @param codePostal
	 * @param ville
	 */
	public Retrait(int noArticle, int idRetrait, String rue, String codePostal, String ville) {
		this.noArticle = noArticle;
		this.idRetrait = idRetrait;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public int getNoArticle() {
		return this.noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public int getIdRetrait() {
		return this.idRetrait;
	}

	public void setIdRetrait(int idRetrait) {
		this.idRetrait = idRetrait;
	}

	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		return "Retrait [idRetrait=" + this.idRetrait + ", rue=" + this.rue + ", codePostal=" + this.codePostal
				+ ", ville=" + this.ville + "]";
	}

//Création de méthodes permettant de faire le lien avec la classe Article
	@Override
	public void ajouter(Article adresseRetrait) {
		if (lieuRetrait == null) {
			lieuRetrait = new ArrayList<>();
		}

		lieuRetrait.add(adresseRetrait);

	}

	@Override
	public void supprimer(Article adresseRetrait) {
		lieuRetrait.remove(adresseRetrait);
	}

}
