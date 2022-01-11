// @author Lucie
package fr.eni.encheres.dal.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.beans.Erreurs;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateursManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.daos.EncheresDAO;


public class EncheresImpl implements EncheresDAO {

	String sqlInsertEnchere = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, heure_enchere, montant_enchere)  VALUES (? , ? , ? , ? , ?)";
	String sqlSelectEncheresByNoUtilisateur = "SELECT no_utilisateur, no_article, date_enchere, heure_enchere, montant_enchere FROM encheres WHERE no_utilisateur = ? ";
	String sqlSelectEncheresByNoArticle = "SELECT no_utilisateur, no_article, date_enchere, heure_enchere, montant_enchere FROM encheres WHERE no_article = ? ";
	String sqlSelectEncheresByNoUtilisateurEtNoArticle = "SELECT no_utilisateur, no_article, date_enchere, heure_enchere, montant_enchere FROM encheres WHERE no_utilisateur = ? AND no_article = ? ";
	String sqlSelectAllEncheres = "SELECT no_utilisateur, no_article, date_enchere, heure_enchere, montant_enchere FROM encheres";
	String sqlUpdateEnchere = "UPDATE encheres " +
			"SET no_utilisateur = ?, no_article = ?, date_enchere = ?, heure_enchere = ?, montant_enchere = ? " +
			"WHERE no_utilisateur = ? AND no_article = ?";
	String sqlDeleteEnchereByNoUtilisateurEtNoArticle = "DELETE FROM encheres WHERE no_utilisateur = ? AND no_article = ? ";
	String sqlSelectMeilleureOffreByNoArticle = "SELECT MAX(montant_enchere) as max_enchere FROM ENCHERES WHERE no_article = ?";
	
	
	@Override
	public void add(Enchere e) throws DALException {
			try (	
					Connection con = GetConnection.getConnexion();
					PreparedStatement pstmt = con.prepareStatement(sqlInsertEnchere);
				){
					
					pstmt.setInt(1, e.getUtilisateur().getNoUtilisateur());
					pstmt.setInt(2, e.getArticle().getNoArticle());
					pstmt.setDate(3, Date.valueOf(e.getDateEnchere()));
					pstmt.setTime(4, Time.valueOf(e.getHeureEnchere()));
					pstmt.setInt(5, e.getMontantEnchere());
					
					pstmt.execute();
			}catch(SQLException ex){
					throw new DALException(ex.getLocalizedMessage(), ex);
			}
	}
	
	public List<Enchere> selectEncheresByNoUtilisateur(int noUtilisateur, Erreurs erreurs) throws DALException {
		
		try(	
				Connection con = GetConnection.getConnexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelectEncheresByNoUtilisateur);
			){
				pstmt.setInt(1, noUtilisateur);
				try(ResultSet rs =  pstmt.executeQuery();){
					List<Enchere> encheres = new ArrayList<>();
					Enchere e = null;
					while (rs.next()) {
						Utilisateur utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(rs.getInt(1), erreurs);
						Article article = ArticleManager.GetInstance().getArticleById(rs.getInt(2), erreurs);
						e = new Enchere(
								utilisateur,
								article,
								rs.getDate(3).toLocalDate(),
								rs.getTime(4).toLocalTime(),
								rs.getInt(5)
						);
						encheres.add(e);
					}
					return encheres;	
				}
		} catch (SQLException ex) {
						throw new DALException(ex.getLocalizedMessage(), ex);
		}
			
	}
	
	public Enchere selectEnchereByNoArticle(int noArticle, Erreurs erreurs) throws DALException {
		try(	
				Connection con = GetConnection.getConnexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelectEncheresByNoArticle);
			){
				pstmt.setInt(1, noArticle);
				try(ResultSet rs = pstmt.executeQuery();){
					Enchere e = null;
					if(rs.next()) {
						Utilisateur utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(rs.getInt(1), erreurs);
						Article article = ArticleManager.GetInstance().getArticleById(rs.getInt(2), erreurs);
						e = new Enchere(
								utilisateur,
								article,
								rs.getDate(3).toLocalDate(),
								rs.getTime(4).toLocalTime(),
								rs.getInt(5)
						);
					}
					return e;
				}	
		}catch (SQLException ex) {
			throw new DALException(ex.getLocalizedMessage(), ex);
		}
	}

	public Enchere selectEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle, Erreurs erreurs )throws DALException {
		try(	
				Connection con = GetConnection.getConnexion();
				PreparedStatement pstmt = con.prepareStatement(sqlSelectEncheresByNoUtilisateurEtNoArticle);
			){
				pstmt.setInt(1, noUtilisateur);
				pstmt.setInt(2, noArticle);
				try(ResultSet rs =  pstmt.executeQuery();){
					Enchere e = null;
					if(rs.next()) {
						Utilisateur utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(rs.getInt(1), erreurs);
						Article article = ArticleManager.GetInstance().getArticleById(rs.getInt(2), erreurs);
						e = new Enchere(
								utilisateur,
								article,
								rs.getDate(3).toLocalDate(),
								rs.getTime(4).toLocalTime(),
								rs.getInt(5)
						);
					}
					return e;
				}
		}	
		catch (SQLException ex) {
						throw new DALException(ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	public List<Enchere> getAll() throws DALException {
		Erreurs erreurs = new Erreurs();
		try (
				Connection con = GetConnection.getConnexion();
				Statement stmt = con.createStatement();
				ResultSet rs =  stmt.executeQuery(sqlSelectAllEncheres);	
			){
				List<Enchere> encheres=new ArrayList<>();
				Enchere e = null;
				
				while(rs.next()){
					Utilisateur utilisateur = UtilisateursManager.GetInstance().getUtilisateurById(rs.getInt(1), erreurs);
					Article article = ArticleManager.GetInstance().getArticleById(rs.getInt(2), erreurs);
					e = new Enchere(
							utilisateur,
							article,
							rs.getDate(3).toLocalDate(),
							rs.getTime(4).toLocalTime(),
							rs.getInt(5)
					);
					encheres.add(e);
				}
				return encheres;
		} catch (SQLException ex) {
				throw new DALException(ex.getLocalizedMessage(), ex);
		}
	}
	
	@Override
	public void update(Enchere e) throws DALException {
			try (
					Connection con = GetConnection.getConnexion();
					PreparedStatement pstmt = con.prepareStatement("UPDATE encheres " +
							"SET no_utilisateur = ?, no_article = ?, date_enchere = ?, heure_enchere = ?, montant_enchere = ? " +
							"WHERE  no_article = ?");
				){
					pstmt.setInt(1, e.getUtilisateur().getNoUtilisateur());
					pstmt.setInt(2, e.getArticle().getNoArticle());
					pstmt.setDate(3, Date.valueOf(e.getDateEnchere()));
					pstmt.setTime(4, Time.valueOf(e.getHeureEnchere()));
					pstmt.setInt(5, e.getMontantEnchere());
					pstmt.setInt(6, e.getArticle().getNoArticle());

					pstmt.execute();
			}catch (SQLException ex) {
					throw new DALException(ex.getLocalizedMessage(), ex);
			}
	}
	
	//à utiliser à la place de remove
	public void deleteEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle) throws DALException {
			try (	
					Connection con = GetConnection.getConnexion();
					PreparedStatement pstmt = con.prepareStatement(sqlDeleteEnchereByNoUtilisateurEtNoArticle);		
				){ 
					pstmt.setInt(1, noUtilisateur);
					pstmt.setInt(2, noArticle);
					
					pstmt.execute();
			}catch (SQLException ex) {
						throw new DALException(ex.getLocalizedMessage(), ex);
				}
	}

	@Override
	public void remove(Enchere utilisateur) throws DALException {
		
	}
	
	
	//Pas sure d'en avoir besoin
	public int selectMeilleureOffreByNoArticle(int noArticle) throws DALException{
		
		try(	
				Connection con = GetConnection.getConnexion();
				PreparedStatement pstmt = con.prepareStatement(
					"SELECT MAX(montant_enchere) as max_enchere FROM ENCHERES WHERE no_article = ?"
				);
			){
				pstmt.setInt(1, noArticle);
				try(ResultSet rs =  pstmt.executeQuery()){
					rs.next();
					int meilleureOffre = rs.getInt("max_enchere");
					return meilleureOffre;
				}
		} catch (SQLException ex) {
			throw new DALException(ex.getLocalizedMessage(), ex);
		}
	}
}
