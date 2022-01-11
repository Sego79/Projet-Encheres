package fr.eni.encheres.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.RetraitsDAO;

/**
 * 
 * @author Sego
 *
 */

public class RetraitsImpl implements RetraitsDAO {

	// Recupération de l'adresse en fonction de l'article
	private static final String sqlSelectAdresseByArticle =
			"SELECT id_retrait, no_article, rue, code_postal, ville" +
			" FROM Retraits WHERE no_article=?";
	// Recupération de l'adresse en fonction de son identifiant
	private static final String sqlSelectAdresseByIdRetrait =
			"SELECT id_retrait, no_article, rue, code_postal, ville FROM Retraits WHERE id_retrait=?";
	// Requête SQL pour que le vendeur insert une nouvelle adresse
	private static final String sqlInsertAdresseRetrait = "INSERT INTO Retraits (no_article, rue, code_postal, ville)  VALUES (? , ? , ? , ?)";
	// le vendeur efface l'adresse
	private static final String sqlDeleteAdresseRetrait = "DELETE FROM Retraits WHERE id_retrait=?";
	// le vendeur MAJ l'adresse
	private static final String sqlUpdateAdresseRetrait = "UPDATE Retraits SET no_article=?,"
			+ "rue=?, code_postal=?, ville=? WHERE id_retrait=?";
	// on selectionne l'ens des infos du retrait
	private static final String sqlSelectAllCoordonnees = "SELECT no_article, id_retrait, rue, code_postal, ville FROM Retraits";

	@Override
	public Retrait lieuRetrait(int noArticle) throws DALException {
		Retrait adresseRetrait = new Retrait();
		try (PreparedStatement statement =
					 GetConnection.getConnexion().prepareStatement(sqlSelectAdresseByArticle)){
			statement.setInt(1, noArticle);
			try(ResultSet rs = statement.executeQuery()) {

				rs.next();
				adresseRetrait.setIdRetrait(rs.getInt("id_retrait"));
				adresseRetrait.setNoArticle(rs.getInt("no_article"));
				adresseRetrait.setRue(rs.getString("rue"));
				adresseRetrait.setCodePostal(rs.getString("code_postal"));
				adresseRetrait.setVille(rs.getString("ville"));
			}
		}

		catch (SQLException ex) {
			throw new DALException("selectCoordonnees failed - artv = " + noArticle + ex.getLocalizedMessage(), ex);
		}

		return adresseRetrait;
	}

	@Override
	public Retrait getById(int idRetrait) throws DALException {
		Retrait adresseRetrait = new Retrait();
		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlSelectAdresseByIdRetrait)){
			stmt.setInt(1, idRetrait);
			try (ResultSet rs = stmt.executeQuery();){
				adresseRetrait.setIdRetrait(rs.getInt("id_retrait"));
				adresseRetrait.setNoArticle(rs.getInt("no_article"));
				adresseRetrait.setRue(rs.getString("rue"));
				adresseRetrait.setCodePostal(rs.getString("code_postal"));
				adresseRetrait.setVille(rs.getString("ville"));
			}
		}

		catch (SQLException ex) {
			throw new DALException("selectCoordonnees failed - artv = " + idRetrait + " " + ex.getLocalizedMessage(), ex);
		}


		return adresseRetrait;
	}

	@Override
	public void add(Retrait adresse) throws DALException {
		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlInsertAdresseRetrait, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, adresse.getNoArticle());
			stmt.setString(2, adresse.getRue());
			stmt.setString(3, adresse.getCodePostal());
			stmt.setString(4, adresse.getVille());

			int nbRows = stmt.executeUpdate();
			if (nbRows == 1) {
				try (ResultSet rs = stmt.getGeneratedKeys();){
					if (rs.next()) {
						adresse.setIdRetrait(rs.getInt(1));
					}

				}
			}
			stmt.execute();

		} catch (SQLException ex) {
			throw new DALException("Erreur dans l'insertion de l'adresse" + " " + ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	public void remove(Retrait retrait) throws DALException {
		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlDeleteAdresseRetrait)){
			stmt.setInt(1, retrait.getIdRetrait());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("Erreur dans la suppression de l'article " + " " + ex.getLocalizedMessage(), ex);

		}
	}

	@Override
	public void update(Retrait adresse) throws DALException {

		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlUpdateAdresseRetrait)){
			stmt.setInt(1, adresse.getNoArticle());
			stmt.setString(2, adresse.getRue());
			stmt.setString(3, adresse.getCodePostal());
			stmt.setString(4, adresse.getVille());
			stmt.setInt(5, adresse.getIdRetrait());

			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("update article failed - " + adresse + " " + ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	public List<Retrait> getAll() throws DALException {
		List<Retrait> listCoordonnees = new ArrayList<>();

		try (PreparedStatement stmt = GetConnection.getConnexion().prepareStatement(sqlSelectAllCoordonnees)){
			try (ResultSet rs = stmt.executeQuery()){
				Retrait coordonnees = null;
				while (rs.next()) {
					coordonnees = new Retrait(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
							rs.getString(5));
					listCoordonnees.add(coordonnees);
				}

			}
		} catch (SQLException ex) {
			throw new DALException("selectAll failed - " + " " + ex.getLocalizedMessage(), ex);
		}

		return listCoordonnees;
	}
}
