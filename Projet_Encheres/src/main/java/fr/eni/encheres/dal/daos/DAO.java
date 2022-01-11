package fr.eni.encheres.dal.daos;

import fr.eni.encheres.dal.DALException;

import java.util.List;


public interface DAO<T> {
	public List<T> getAll() throws DALException;
	public void add(T t) throws DALException;
	public void update(T t) throws DALException;
	public void remove(T t) throws DALException;

}
