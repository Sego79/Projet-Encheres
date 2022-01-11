//@author Frédéric

package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Article implements Comparable<Article> {

	private int noArticle;
	private String nomArticle;
	private String description;
	private String urlImage;
	private LocalDate dateDebutEnchere;
	private LocalTime timeDebutEnchere;
	private LocalDate dateFinEnchere;
	private LocalTime timeFinEnchere;
	private int miseAPrix;
	private int prixVente;
	private boolean annuleParVendeur;
	private boolean recuParAcheteur;

	// ajout
	private Categorie categorie;
	private Retrait retrait;
	private Utilisateur utilisateur;
	private List<Enchere> encheres;

	public Article() {
		this(
				-1,
				"",
				"",
				"/images/fauteuil.jpg",
				LocalDate.now(),
				LocalTime.now(),
				LocalDate.now(),
				LocalTime.now(),
				0,
				0,
				false,
				false,
				new Categorie(), new Retrait(), new Utilisateur(), new ArrayList<>()
		);
	}


	/**
	 * @author Sego Constructeur avec tous les paramètres
	 */
	public Article(int noArticle, String nomArticle, String description, String urlImage,
				   LocalDate dateDebutEnchere, LocalTime timeDebutEnchere,
			LocalDate dateFinEnchere, LocalTime timeFinEnchere, int miseAPrix, int prixVente,
		    boolean annuleParVendeur, boolean recuParAcheteur, Categorie categorie,
			Retrait retrait, Utilisateur utilisateur, List<Enchere> encheres) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.urlImage = urlImage;
		this.dateDebutEnchere = dateDebutEnchere;
		this.timeDebutEnchere = timeDebutEnchere;
		this.dateFinEnchere = dateFinEnchere;
		this.timeFinEnchere = timeFinEnchere;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.categorie = categorie;
		this.annuleParVendeur = annuleParVendeur;
		this.recuParAcheteur = recuParAcheteur;
		this.retrait = retrait;
		this.utilisateur = utilisateur;
		this.encheres = encheres;
	}

	public Article(int noArticle) {
		this();
		this.noArticle = noArticle;
	}

	public int getNoArticle() {
		return this.noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return this.nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMiseAPrix() {
		return this.miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return this.prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Retrait getRetrait() {
		return this.retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Enchere> getEncheres() {
		return this.encheres;
	}

	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}

	@Override
	public String toString() {
		return "Article [noArticle=" + this.noArticle + ", nomArticle=" + this.nomArticle + ", description="
				+ this.description + ", urlImage=" + this.urlImage + ", dateDebutEnchere=" + this.dateDebutEnchere + ", dateFinEnchere="
				+ this.dateFinEnchere + ", miseAPrix=" + this.miseAPrix + ", prixVente=" + this.prixVente
				+ ", etatVente=" + this.annuleParVendeur + this.recuParAcheteur + ", categorie=" + this.categorie + ", retrait=" + this.retrait
				+ ", utilisateur=" + this.utilisateur + ", encheres=" + this.encheres + "]";
	}

	public LocalDate getDateDebutEnchere() {
		return dateDebutEnchere;
	}

	public void setDateDebutEnchere(LocalDate dateDebutEnchere) {
		this.dateDebutEnchere = dateDebutEnchere;
	}

	public LocalTime getTimeDebutEnchere() {
		return timeDebutEnchere;
	}

	public void setTimeDebutEnchere(LocalTime timeDebutEnchere) {
		this.timeDebutEnchere = timeDebutEnchere;
	}

	public void setDateFinEnchere(LocalDate dateFinEnchere) {
		this.dateFinEnchere = dateFinEnchere;
	}

	public LocalTime getTimeFinEnchere() {
		return timeFinEnchere;
	}

	public void setTimeFinEnchere(LocalTime timeFinEnchere) {
		this.timeFinEnchere = timeFinEnchere;
	}

	public LocalDate getDateFinEnchere() {
		return dateFinEnchere;
	}

	@Override
	public int compareTo(Article a) {
		return dateDebutEnchere.compareTo(a.getDateDebutEnchere());
	}

	public boolean isAnnuleParVendeur() {
		return annuleParVendeur;
	}

	public void setAnnuleParVendeur(boolean annuleParVendeur) {
		this.annuleParVendeur = annuleParVendeur;
	}

	public boolean isRecuParAcheteur() {
		return recuParAcheteur;
	}

	public void setRecuParAcheteur(boolean recuParAcheteur) {
		this.recuParAcheteur = recuParAcheteur;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public boolean aDebute() { // soit date hier, soit date aujourd'hui mais heure inferieure
		return this.dateDebutEnchere.isBefore(LocalDate.now()) || 
				this.dateDebutEnchere.equals(LocalDate.now()) && this.timeDebutEnchere.isBefore(LocalTime.now());
	}

	public boolean estFinie() {
		return this.dateFinEnchere.isBefore(LocalDate.now()) || 
				this.dateFinEnchere.equals(LocalDate.now()) && this.timeFinEnchere.isBefore(LocalTime.now());
	}

	public boolean peutEncherir() {
		// TODO Auto-generated method stub
		return aDebute() && !estFinie();
	}
}
