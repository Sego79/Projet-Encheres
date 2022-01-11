package fr.eni.encheres.bll;
//@author frederic

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.Utilitaires;
import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.daos.ArticlesDAO;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.DAOFactory;

public class ArticleManager {

	private static ArticlesDAO articlesDAO = null;
	private static ArticleManager instance = null;

	private ArticleManager() {
	}

	public static ArticleManager GetInstance() {

		if (articlesDAO == null) {
			try {
				articlesDAO = (ArticlesDAO) DAOFactory.getArticlesDAO();
			} catch (DALException e) {
				e.printStackTrace();
			}
		}

		if (instance == null) {
			instance = new ArticleManager();
		}

		return instance;
	}

	public Article getArticleById(Integer articleId, Erreurs erreurs) {

		Article article = null;

		try {
			article = articlesDAO.getById(articleId);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}

		return article;
	}

	public Article getByIdAvecInstance(Integer articleId, Article article, Erreurs erreurs) {
		try {
			articlesDAO.getByIdAvecInstance(article, articleId);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
		return article;
	}

	public List<Article> getCatalogue(Erreurs erreurs) {
		List<Article> articles = new ArrayList<>();
		try {
			articlesDAO.getAll(articles);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
		return articles;
	}

	public void getCatalogue(List<Article> articles, Erreurs erreurs) {
		try {
			articlesDAO.getAll(articles);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
	}

	public void getCatalogueTotal(List<Article> articles, Erreurs erreurs) {
		// liste meme encheres finies, peut etre utile pour les iterations suivantes
		try {
			articlesDAO.getAllMemeFinis(articles);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
	}

	public void addArticle(Article article, String nomArticle, String description, LocalDate dateDebutEncheres,
						   LocalDate dateFinEncheres, Integer miseAPrix, String etatVente, Erreurs erreurs) {

		if (nomArticle == null)
			erreurs.addErreur("Le nom de l'article doit être renseigné");
		if (description == null)
			erreurs.addErreur("Une description de votre article doit être renseigné");
		if (dateDebutEncheres == null)
			erreurs.addErreur("Une date de début d'enchère doit être renseignée");
		if (dateFinEncheres == null)
			erreurs.addErreur("Une date de fin d'enchère doit être renseignée");
		if (miseAPrix == null)// |miseAPRix<=0
			erreurs.addErreur("Le prix de départ doit être renseigné");
		if (etatVente == null)
			erreurs.addErreur("L'état de la vente doit être renseignée");
		
		// if (dateFinEncheres.before(dateDebutEnchere))
			//erreurs.addErreur("La date de fin d'enchère ne peut être avant la date de début")
		// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
		//erreurs.addErreur("Une enchère ne peut durer plus de 30 jours!")
		// if (dateDebutEncheres.before(LocalDate.now))
		//erreurs.addErreur("La date de début d'enchère ne peut précéder la date d'auourd'hui")
		//if (article.getNomArticle().length() > 200 || article.getNomArticle().length() < 1) {
		//erreurs.addErreur("Le nom a une longueur incorrecte");
		//}

		//if (article.getDescription().length() > 300) {
		//erreurs.addErreur("La description est trop longue");
		//}
	}

	public void modifArticle(Article article, Erreurs erreurs) {
		modifArticle(article, article.getNomArticle(), article.getDescription(), article.getDateDebutEnchere(),
				article.getTimeDebutEnchere(), article.getDateFinEnchere(), article.getTimeFinEnchere(),
				article.getMiseAPrix(), erreurs);
	}

	public void modifArticle(Article article, String nomArticle, String description, LocalDate dateDebutEncheres,
							 LocalTime heureDebutEncheres, LocalDate dateFinEncheres, LocalTime heureFinEncheres,
							 Integer miseAPrix, Erreurs erreurs) {

		if (nomArticle == null)
			erreurs.addErreur("Le nom de l'article doit être renseigné");
		if (description == null)
			erreurs.addErreur("Une description de votre article doit être renseigné");
		if (dateDebutEncheres == null)
			erreurs.addErreur("Une date de début d'enchère doit être renseignée");
		if (dateFinEncheres == null)
			erreurs.addErreur("Une date de fin d'enchère doit être renseignée");
		if (miseAPrix == null)// <=0
			erreurs.addErreur("Le prix de départ doit être renseigné");
		// if (dateFinEncheres.before(dateDebutEnchere))
					//erreurs.addErreur("La date de fin d'enchère ne peut être avant la date de début")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une enchère ne peut durer plus de 30 jours!")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de début d'enchère ne peut précéder la date d'auourd'hui")
				//if (article.getNomArticle().length() > 200 || article.getNomArticle().length() < 1) {
				//erreurs.addErreur("Le nom a une longueur incorrecte");
				//}

				//if (article.getDescription().length() > 300) {
				//erreurs.addErreur("La description est trop longue");
				//}

		if(article.aDebute()) {
			erreurs.addErreur("Vous ne pouvez plus modifier un article dont la vente a débuté");
		}

		if (erreurs.hasErrors())
			return;

		article.setNomArticle(nomArticle);
		article.setDescription(description);
		article.setDateDebutEnchere(dateDebutEncheres);
		article.setTimeDebutEnchere(heureDebutEncheres);
		article.setDateFinEnchere(dateFinEncheres);
		article.setTimeFinEnchere(heureFinEncheres);
		article.setMiseAPrix(miseAPrix);

		try {
			articlesDAO.update(article);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
	}

	private void creerArticle(Article article, Erreurs erreurs) {
		validerArticle(article, erreurs);

		if (erreurs.hasErrors())
			return;

		try {
			articlesDAO.add(article);
		} catch (DALException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}

	}

	public void supprimerArticle(Article article) throws BLLException {

		try {
			articlesDAO.remove(article);
		} catch (DALException e) {
			throw new BLLException(e.getLocalizedMessage(), e);
		}
	}

	private void validerArticle(Article article, Erreurs erreurs) {
		if (article.getNomArticle() == null)
			erreurs.addErreur("Le nom doit être renseigné");

		if (article.getDescription() == null)
			erreurs.addErreur("La description doit être renseigné");

		if (article.getMiseAPrix() == null)// <=0
			erreurs.addErreur("La mise à prix< doit être renseigné");

		if (article.getDateDebutEnchere() == null)
			erreurs.addErreur("La date du debut doit être renseigné");

		if (article.getTimeDebutEnchere() == null)
			erreurs.addErreur("L'heure du debut doit être renseigné");

		if (erreurs.hasErrors()) {
			return;
		}

		if (article.getNomArticle().length() > 200 || article.getNomArticle().length() < 1) {
			erreurs.addErreur("Le nom a une lognueur incorrecte");
		}

		if (article.getDescription().length() > 300) {
			erreurs.addErreur("La description est trop longue");
		}
		// if (dateFinEncheres.before(dateDebutEnchere))
					//erreurs.addErreur("La date de fin d'enchère ne peut être avant la date de début")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une enchère ne peut durer plus de 30 jours")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de début d'enchère ne peut précéder la date d'auourd'hui")
			//}
		// todo: autres verifications?
	}

	public Article getArticleByCategorie(Categorie categorie) throws BLLException {

		Article article = null;

		try {
			article = articlesDAO.getByCategorie(categorie);
		} catch (DALException ex) {
			throw new BLLException(ex.getLocalizedMessage(), ex);
		}

		return article;
	}

	public void sauvegarderDepuisLeWeb(String nom, String description, String prix, String dateDebut, String dateFin,
									   String rue, String codePostal, String ville, Categorie categorie, Utilisateur utilisateur, Article article,
									   Erreurs erreurs) {
		if (nom == null)
			erreurs.addErreur("Le nom doit être renseigné");
		else
			article.setNomArticle(nom);
		if (description == null)
			erreurs.addErreur("La description doit être renseignée");
		else
			article.setDescription(description);
		if (prix == null)//<=0
			erreurs.addErreur("Le prix doit être renseigné");
		if (dateDebut == null)
			erreurs.addErreur("La date doit être renseignée");
		if (dateFin == null)
			erreurs.addErreur("La date de fin doit être renseignée");
		if (rue == null)
			erreurs.addErreur("La rue doit être renseignée");
		if (codePostal == null)
			erreurs.addErreur("Le code postal doit être renseigné");
		if (ville == null)
			erreurs.addErreur("La ville doit être renseignée");
		if (categorie == null)
			erreurs.addErreur("La catégorie doit être renseignée");
		if (utilisateur == null)
			erreurs.addErreur("Il faut être connecté pour vendre un article");
		// if (dateFinEncheres.before(dateDebutEnchere))
					//erreurs.addErreur("La date de fin d'enchère ne peut être avant la date de début")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une enchère ne peut durer plus de 30 jours!")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de début d'enchère ne peut précéder la date d'auourd'hui")
				//if (article.getNomArticle().length() > 200 || article.getNomArticle().length() < 1) {
				//erreurs.addErreur("Le nom a une longueur incorrecte");
				//}

				//if (article.getDescription().length() > 300) {
				//erreurs.addErreur("La description est trop longue");
				//}

		if (erreurs.hasErrors())
			return;

		int prixInt;

		try {
			assert prix != null;
			prixInt = Integer.parseInt(prix);
		} catch (NumberFormatException e) {
			erreurs.addErreur("Valeur mise à prix malformatée");
			prixInt = 0;
		}

		article.setMiseAPrix(prixInt);

		LocalDateTime dateMise = Utilitaires.fromHTMLDateTimeLocal(dateDebut);
		LocalDateTime dateFinD = Utilitaires.fromHTMLDateTimeLocal(dateFin);

		article.setDateDebutEnchere(dateMise.toLocalDate());
		article.setTimeDebutEnchere(dateMise.toLocalTime());

		boolean finDefault = dateFin.length() == 0;
		if (!finDefault)
			article.setDateFinEnchere(dateFinD.toLocalDate().plusDays(2)); // par defaut enchere finit dans 2 jours
		else
			article.setDateFinEnchere(dateFinD.toLocalDate());
		article.setTimeFinEnchere(dateMise.toLocalTime()); // par defaut enchere finit a la meme heure

		article.setCategorie(categorie);
		article.setUtilisateur(utilisateur);
		article.setAnnuleParVendeur(false);
		article.setRecuParAcheteur(false);

		if (erreurs.hasErrors())
			return;

		creerArticle(article, erreurs);

		Retrait retrait = new Retrait(rue, codePostal, ville);
		retrait.setNoArticle(article.getNoArticle());
		try {
			RetraitsManager.GetInstance().ajouterAdresse(retrait);
		} catch (BLLException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		} catch (BusinessException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}
	}

	public void modifDepuisLeWeb(String nom, String description, String prix, String dateDebut, String heureDebut,
								 String dateFin, String heureFin,
								 String rue, String codePostal, String ville, Categorie categorie, Utilisateur utilisateur, Article article,
								 Erreurs erreurs) {
		if (dateDebut == null) {
			erreurs.addErreur("date debut ne peut etre null");
		}
		if (dateFin == null) {
			erreurs.addErreur("date fin ne peut etre null");
		}
		if(prix == null) {// | prix<0)
			erreurs.addErreur("prix ne peut etre null");//ou négatif
		}
		if(utilisateur == null) {
			erreurs.addErreur("il faut etre connecté");
		} else {
			if(utilisateur.getNoUtilisateur() != article.getUtilisateur().getNoUtilisateur()) {
				erreurs.addErreur("Vous devez etre le propriétaire de la vente");
			}
		}
		Integer miseAPrix = -1;
		try {
			miseAPrix = Integer.parseInt(prix);
		} catch (NumberFormatException | NullPointerException e) {
			erreurs.addErreur("nombre malformé");
		}
		if (erreurs.hasErrors()) {
			return;
		}

		LocalDateTime dateTimeDebut = Utilitaires.fromHTMLDateAndTime(dateDebut, heureDebut);
		LocalDateTime dateTimeFin = Utilitaires.fromHTMLDateAndTime(dateFin, heureFin);

		article.setCategorie(categorie);

		modifArticle(article, nom, description, dateTimeDebut.toLocalDate(), dateTimeDebut.toLocalTime(),
				dateTimeFin.toLocalDate(), dateTimeFin.toLocalTime(), miseAPrix, erreurs);

		if(erreurs.hasErrors()) {
			return;
		}

		try {
			Retrait retrait = RetraitsManager.GetInstance().recuperationAdresseByIdArticle(article.getNoArticle());
			retrait.setRue(rue);
			retrait.setVille(ville);
			retrait.setCodePostal(codePostal);
			RetraitsManager.GetInstance().updateAdresse(retrait);
			article.setRetrait(retrait);
		} catch (BLLException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		} catch (BusinessException e) {
			erreurs.addErreur(e.getLocalizedMessage());
		}

	}

}
