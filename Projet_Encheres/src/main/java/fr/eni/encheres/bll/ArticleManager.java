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
			erreurs.addErreur("Le nom de l'article doit ??tre renseign??");
		if (description == null)
			erreurs.addErreur("Une description de votre article doit ??tre renseign??");
		if (dateDebutEncheres == null)
			erreurs.addErreur("Une date de d??but d'ench??re doit ??tre renseign??e");
		if (dateFinEncheres == null)
			erreurs.addErreur("Une date de fin d'ench??re doit ??tre renseign??e");
		if (miseAPrix == null)// |miseAPRix<=0
			erreurs.addErreur("Le prix de d??part doit ??tre renseign??");
		if (etatVente == null)
			erreurs.addErreur("L'??tat de la vente doit ??tre renseign??e");
		
		// if (dateFinEncheres.before(dateDebutEnchere))
			//erreurs.addErreur("La date de fin d'ench??re ne peut ??tre avant la date de d??but")
		// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
		//erreurs.addErreur("Une ench??re ne peut durer plus de 30 jours!")
		// if (dateDebutEncheres.before(LocalDate.now))
		//erreurs.addErreur("La date de d??but d'ench??re ne peut pr??c??der la date d'auourd'hui")
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
			erreurs.addErreur("Le nom de l'article doit ??tre renseign??");
		if (description == null)
			erreurs.addErreur("Une description de votre article doit ??tre renseign??");
		if (dateDebutEncheres == null)
			erreurs.addErreur("Une date de d??but d'ench??re doit ??tre renseign??e");
		if (dateFinEncheres == null)
			erreurs.addErreur("Une date de fin d'ench??re doit ??tre renseign??e");
		if (miseAPrix == null)// <=0
			erreurs.addErreur("Le prix de d??part doit ??tre renseign??");
		// if (dateFinEncheres.before(dateDebutEnchere))
					//erreurs.addErreur("La date de fin d'ench??re ne peut ??tre avant la date de d??but")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une ench??re ne peut durer plus de 30 jours!")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de d??but d'ench??re ne peut pr??c??der la date d'auourd'hui")
				//if (article.getNomArticle().length() > 200 || article.getNomArticle().length() < 1) {
				//erreurs.addErreur("Le nom a une longueur incorrecte");
				//}

				//if (article.getDescription().length() > 300) {
				//erreurs.addErreur("La description est trop longue");
				//}

		if(article.aDebute()) {
			erreurs.addErreur("Vous ne pouvez plus modifier un article dont la vente a d??but??");
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
			erreurs.addErreur("Le nom doit ??tre renseign??");

		if (article.getDescription() == null)
			erreurs.addErreur("La description doit ??tre renseign??");

		if (article.getMiseAPrix() == null)// <=0
			erreurs.addErreur("La mise ?? prix< doit ??tre renseign??");

		if (article.getDateDebutEnchere() == null)
			erreurs.addErreur("La date du debut doit ??tre renseign??");

		if (article.getTimeDebutEnchere() == null)
			erreurs.addErreur("L'heure du debut doit ??tre renseign??");

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
					//erreurs.addErreur("La date de fin d'ench??re ne peut ??tre avant la date de d??but")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une ench??re ne peut durer plus de 30 jours")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de d??but d'ench??re ne peut pr??c??der la date d'auourd'hui")
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
			erreurs.addErreur("Le nom doit ??tre renseign??");
		else
			article.setNomArticle(nom);
		if (description == null)
			erreurs.addErreur("La description doit ??tre renseign??e");
		else
			article.setDescription(description);
		if (prix == null)//<=0
			erreurs.addErreur("Le prix doit ??tre renseign??");
		if (dateDebut == null)
			erreurs.addErreur("La date doit ??tre renseign??e");
		if (dateFin == null)
			erreurs.addErreur("La date de fin doit ??tre renseign??e");
		if (rue == null)
			erreurs.addErreur("La rue doit ??tre renseign??e");
		if (codePostal == null)
			erreurs.addErreur("Le code postal doit ??tre renseign??");
		if (ville == null)
			erreurs.addErreur("La ville doit ??tre renseign??e");
		if (categorie == null)
			erreurs.addErreur("La cat??gorie doit ??tre renseign??e");
		if (utilisateur == null)
			erreurs.addErreur("Il faut ??tre connect?? pour vendre un article");
		// if (dateFinEncheres.before(dateDebutEnchere))
					//erreurs.addErreur("La date de fin d'ench??re ne peut ??tre avant la date de d??but")
				// if (dateFinEncheres.after(LocalDate.now.plusDays(30))
				//erreurs.addErreur("Une ench??re ne peut durer plus de 30 jours!")
				// if (dateDebutEncheres.before(LocalDate.now))
				//erreurs.addErreur("La date de d??but d'ench??re ne peut pr??c??der la date d'auourd'hui")
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
			erreurs.addErreur("Valeur mise ?? prix malformat??e");
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
			erreurs.addErreur("prix ne peut etre null");//ou n??gatif
		}
		if(utilisateur == null) {
			erreurs.addErreur("il faut etre connect??");
		} else {
			if(utilisateur.getNoUtilisateur() != article.getUtilisateur().getNoUtilisateur()) {
				erreurs.addErreur("Vous devez etre le propri??taire de la vente");
			}
		}
		Integer miseAPrix = -1;
		try {
			miseAPrix = Integer.parseInt(prix);
		} catch (NumberFormatException | NullPointerException e) {
			erreurs.addErreur("nombre malform??");
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
