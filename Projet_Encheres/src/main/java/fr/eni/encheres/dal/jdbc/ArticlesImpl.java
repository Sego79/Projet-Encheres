package fr.eni.encheres.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.*;
import fr.eni.encheres.dal.daos.*;

public class ArticlesImpl implements ArticlesDAO {
	UtilisateursDAO utilisateursDAO = null;
	CategoriesDAO categoriesDAO = null;
	RetraitsDAO retraitsDAO = null;



	private static final String sqlSelectById = "SELECT no_article, nom_article,description, url_image, " +
			"date_debut_encheres,heure_debut_encheres, date_fin_encheres,heure_fin_encheres,prix_initial,"
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur, no_categorie, no_utilisateur " +
			" FROM dbo.ARTICLES_VENDUS WHERE no_article=?";

	private static final String sqlSelectAll = "SELECT no_article, nom_article,description, url_image, " +
			"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial,"
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur  FROM dbo.ARTICLES_VENDUS";

	private static final String sqlSelectByCategorie = "SELECT no_article, nom_article,description, url_image, " +
			"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial, " +
			" prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur FROM dbo.ARTICLES_VENDUS WHERE no_categorie=?";

	private static final String sqlInsert = "INSERT INTO dbo.ARTICLES_VENDUS " +
			"(nom_article,description, url_image, date_debut_encheres,heure_debut_encheres, date_fin_encheres,heure_fin_encheres, prix_initial, "
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String sqlUpdate = "UPDATE dbo.ARTICLES_VENDUS SET  " +
			"nom_article=?,description=?,url_image=?,date_debut_encheres=?,heure_debut_encheres=?," +
			"date_fin_encheres=?,heure_fin_encheres=?,prix_initial=?, "
			+ "prix_vente=?,annule_par_vendeur=?,recu_par_acheteur=?,no_categorie=?,no_utilisateur=? WHERE no_article=?";

	private static final String sqlDelete = "DELETE FROM dbo.ARTICLES_VENDUS WHERE no_article=?";

	public ArticlesImpl() {
		try {
			this.utilisateursDAO = (UtilisateursDAO) DAOFactory.getUtilisateursDAO();
			this.categoriesDAO = (CategoriesDAO) DAOFactory.getCategoriesDAO();
			this.retraitsDAO = (RetraitsDAO) DAOFactory.getRetraitsDAO();
		} catch (DALException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Article getById(int id) throws DALException {
		Article article = new Article();

		getByIdAvecInstance(article, id);
		return article;
	}
	
	@Override
	public void getByIdAvecInstance(Article article, int id) throws DALException {

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(sqlSelectById)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery();){
				resultSet.next();
				article.setNoArticle(resultSet.getInt("no_article"));
				article.setNomArticle(resultSet.getString("nom_article"));
				article.setDescription(resultSet.getString("description"));
				article.setUrlImage(resultSet.getString("url_image"));
				article.setDateDebutEnchere(resultSet.getDate("date_debut_encheres").toLocalDate());
				article.setTimeDebutEnchere(resultSet.getTime("heure_debut_encheres").toLocalTime());
				article.setDateFinEnchere(resultSet.getDate("date_fin_encheres").toLocalDate());
				article.setTimeFinEnchere(resultSet.getTime("heure_fin_encheres").toLocalTime());
				article.setMiseAPrix(resultSet.getInt("prix_initial"));
				article.setPrixVente(resultSet.getInt("prix_vente"));
				article.setAnnuleParVendeur(resultSet.getBoolean("annule_par_vendeur"));
				article.setRecuParAcheteur(resultSet.getBoolean("recu_par_acheteur"));
				article.setCategorie(categoriesDAO.getById(resultSet.getInt("no_categorie")));
				article.setUtilisateur(utilisateursDAO.getById(resultSet.getInt("no_utilisateur")));
				article.setRetrait(retraitsDAO.lieuRetrait(id));
			}
		} catch (SQLException ex) {
			throw new DALException("getById failed  = " + id + " " + ex.getLocalizedMessage(), ex);
		}

	}

	@Override
	public void getAll(List<Article> articles) throws DALException {
		LocalDate dateAjd = LocalDate.now();
		LocalTime maintenant = LocalTime.now();

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(
				"SELECT no_article, nom_article,description, url_image, " +
				"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial, " +
				" prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur  " +
				"FROM dbo.ARTICLES_VENDUS " +
				"WHERE date_debut_encheres < ? and date_fin_encheres > ? " +
				//"and heure_debut_encheres < ? and heure_fin_encheres > ? " + // il faut faire une sous requete ?
				"and annule_par_vendeur=0")){

			statement.setDate(1, Date.valueOf(dateAjd));
			statement.setDate(2, Date.valueOf(dateAjd));
			peupleSelectAll(articles, statement);
		} catch (SQLException ex) {
			throw new DALException("selectAll failed - " + ex.getLocalizedMessage(), ex);
		}
	}


	@Override
	public List<Article> getAll() throws DALException  {
		// Renvoie seulement les encheres en cours non annulées et pas encore livrées et recues
		List<Article> listArticle = new ArrayList<>();

		getAll(listArticle);
		return listArticle;
	}

	@Override
	public void getAllMemeFinis(List<Article> listArticle) throws DALException {
		// renvoie toutes les ventes sans exception
		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(
				"SELECT no_article, nom_article,description,url_image, " +
						"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial, " +
						" prix_vente,no_utilisateur,no_categorie,annule_par_vendeur, recu_par_acheteur  FROM dbo.ARTICLES_VENDUS")){
			peupleSelectAll(listArticle, statement);

		} catch (SQLException ex) {
			throw new DALException("selectAll failed - " + ex.getLocalizedMessage(), ex);
		}
	}


	private void peupleSelectAll(List<Article> listArticle, PreparedStatement statement) throws SQLException, DALException {
		try(ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				listArticle.add(new Article(
						resultSet.getInt("no_article"),
						resultSet.getString("nom_article"),
						resultSet.getString("description"),
						resultSet.getString("url_image"),
						resultSet.getDate("date_debut_encheres").toLocalDate(),
						resultSet.getTime("heure_debut_encheres").toLocalTime(),
						resultSet.getDate("date_fin_encheres").toLocalDate(),
						resultSet.getTime("heure_fin_encheres").toLocalTime(),
						resultSet.getInt("prix_initial"),
						resultSet.getInt("prix_vente"),
						resultSet.getBoolean("annule_par_vendeur"),
						resultSet.getBoolean("recu_par_acheteur"),
						categoriesDAO.getById(resultSet.getInt("no_categorie")), // todo faire pointer toutes ces nouvelles instances vers nous
						retraitsDAO.lieuRetrait(resultSet.getInt("no_article")),
						utilisateursDAO.getById(resultSet.getInt("no_utilisateur")),
						new ArrayList<>()  // ici il faut faire une jointure pour recuperer les encheres?
				));
			}
		}
	}


	@Override
	public Article getByCategorie(Categorie categorie) throws DALException {
		Article article = new Article();

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(sqlSelectByCategorie)){
			try (ResultSet resultSet = statement.executeQuery()){
				article.setNomArticle(resultSet.getString("nom_article"));
				article.setDescription(resultSet.getString("description"));
				article.setUrlImage(resultSet.getString("url_image"));
				article.setDateDebutEnchere(resultSet.getDate("date_debut_encheres").toLocalDate());
				article.setTimeDebutEnchere(resultSet.getTime("heure_debut_encheres").toLocalTime());
				article.setDateFinEnchere(resultSet.getDate("date_fin_encheres").toLocalDate());
				article.setTimeFinEnchere(resultSet.getTime("heure_fin_encheres").toLocalTime());
				article.setMiseAPrix(resultSet.getInt("prix_initial"));
				article.setPrixVente(resultSet.getInt("prix_vente"));
				article.setCategorie(categoriesDAO.getById(resultSet.getInt("no_categorie")));
				article.setUtilisateur(utilisateursDAO.getById(resultSet.getInt("no_utilisateur")));
			}
		} catch (SQLException ex) {
			throw new DALException("getByCategorie failed  = " + categorie + " " + ex.getLocalizedMessage(), ex);
		}


		return article;
	}


	@Override
	public void add(Article article) throws DALException {

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(
				sqlInsert, new String[] { "no_article" })){
			int i = 1;

			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setString(i++, article.getUrlImage());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeDebutEnchere()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeFinEnchere()));
			statement.setInt(i++, article.getMiseAPrix());
			statement.setInt(i++, article.getPrixVente());
			statement.setBoolean(i++, article.isAnnuleParVendeur());
			statement.setBoolean(i++, article.isRecuParAcheteur());
			statement.setInt(i++, article.getCategorie().getId());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());

			int nbRows = statement.executeUpdate();
			if (nbRows == 1) {
				try (ResultSet rs = statement.getGeneratedKeys()){
					if (rs.next()) {
						article.setNoArticle(rs.getInt(1));
					}

				}
			}

		} catch (SQLException | NullPointerException ex) {
			throw new DALException(ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	public void update(Article article) throws DALException {

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement("UPDATE dbo.ARTICLES_VENDUS SET  " +
				"nom_article=?,description=?,url_image=?,date_debut_encheres=?,heure_debut_encheres=?," +
				"date_fin_encheres=?,heure_fin_encheres=?,prix_initial=?, "
				+ "prix_vente=?,annule_par_vendeur=?,recu_par_acheteur=?,no_categorie=?,no_utilisateur=? WHERE no_article=?")) {

			int i = 1;

			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setString(i++, article.getUrlImage());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeDebutEnchere()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeFinEnchere()));
			statement.setInt(i++, article.getMiseAPrix());
			statement.setInt(i++, article.getPrixVente());
			statement.setBoolean(i++, article.isAnnuleParVendeur());
			statement.setBoolean(i++, article.isRecuParAcheteur());
			statement.setInt(i++, article.getCategorie().getId());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());
			statement.setInt(i++, article.getNoArticle());

			statement.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("update article failed - " + article + " " + ex.getLocalizedMessage(), ex);
		}

	}

	@Override
	public void remove(Article supArticle) throws DALException {
		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlDelete)){

			stmt.setInt(1, supArticle.getNoArticle());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("Erreur dans la suppression de l'article ", ex);

		}

	}

}