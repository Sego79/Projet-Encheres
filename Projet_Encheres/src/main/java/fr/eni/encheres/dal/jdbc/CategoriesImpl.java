package fr.eni.encheres.dal.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.daos.CategoriesDAO;
import fr.eni.encheres.dal.DALException;


public class CategoriesImpl implements CategoriesDAO {

	static String sqlSelectById = "select no_categorie, etiquette, libelle from dbo.categories where no_categorie=?";
	static String sqlSelectByEtiq = "select no_categorie, etiquette, libelle from dbo.categories where etiquette=?";
	static String sqlSelectAll = "select no_categorie, etiquette, libelle from dbo.categories";
	static String sqlInsert = "INSERT INTO dbo.categories (etiquette, libelle) VALUES (? , ?)";
	static String sqlUpdate = "UPDATE dbo.categories SET etiquette=?, libelle=? WHERE no_categorie=?";
	static String sqlDelete = "DELETE FROM dbo.categories WHERE no_categorie=?";


	@Override
	public Categorie getById(int id) throws DALException {
		
		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(sqlSelectById)) {
			
			statement.setInt(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				resultSet.next();
				
				return new Categorie(
						resultSet.getInt("no_categorie"),
						resultSet.getString("etiquette"),
						resultSet.getString("libelle"));
			}
			
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		}
	}

	@Override
	public Categorie getByEtiquette(String etiquette) throws DALException {
		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(sqlSelectByEtiq)) {

			statement.setString(1, etiquette);

			try (ResultSet resultSet = statement.executeQuery()) {

				resultSet.next();

				return new Categorie(
						resultSet.getInt("no_categorie"),
						resultSet.getString("etiquette"),
						resultSet.getString("libelle"));
			}

		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		}
	}


	@Override
	public List<Categorie> getAll() throws DALException {
		
		List<Categorie> categories = new ArrayList<>();
		
		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(
				sqlSelectAll)) {
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				while (resultSet.next()) {
					Categorie categorie = new Categorie(
							resultSet.getInt("no_categorie"),
							resultSet.getString("etiquette"),
							resultSet.getString("libelle")
					);
					
					categories.add(categorie);
				}
			}
			
		} catch (SQLException e) {
			
			throw new DALException(e.getMessage(), e);
		}

		return categories;
	}

	@Override
	public void add(Categorie categorie) throws DALException {
	
		try (PreparedStatement statement = GetConnection.getConnexion()
				.prepareStatement(sqlInsert, new String[] { "no_categorie" })) {

			statement.setString(1, categorie.getEtiquette());
			statement.setString(2, categorie.getLibelle());

			statement.executeUpdate();
			
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				resultSet.next();
				int idUtilisateur = resultSet.getInt(1);
				categorie.setId(idUtilisateur);
			}
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		}

	}

	@Override
	public void update(Categorie utilisateur) throws DALException {
		
		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(sqlUpdate)) {
			
			statement.setString(1, utilisateur.getEtiquette());
			statement.setString(2, utilisateur.getLibelle());

			statement.setInt(3, utilisateur.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		}
		
	}

	@Override
	public void remove(Categorie utilisateur) throws DALException {
		
		try (PreparedStatement statement = GetConnection.getConnexion()
				.prepareStatement(sqlDelete)) {
			
			statement.setInt(1, utilisateur.getId());
			statement.executeUpdate();
		
		} catch (SQLException e) {
			throw new DALException(e.getMessage(), e);
		}
		
	}
	
}
