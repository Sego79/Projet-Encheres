package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import fr.eni.encheres.dal.DALException;

class GetConnection {

	public static Connection getConnexion() throws DALException {
		Context context;
		DataSource dataSource;
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
			return dataSource.getConnection();
		} catch (NamingException | SQLException e1) {
			throw new DALException(e1.getMessage(), e1);
		}
	}

// fermeture de la connexion à la BDD
	static void close(AutoCloseable resource) throws DALException {
		if (resource != null) {

			try {
				resource.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
