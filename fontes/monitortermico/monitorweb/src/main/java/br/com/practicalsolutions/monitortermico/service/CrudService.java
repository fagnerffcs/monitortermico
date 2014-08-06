package br.com.practicalsolutions.monitortermico.service;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CrudService<T> {
	
	public void create(T t);
	public T findById(Class<?> type, Object id);
	public List<?> findWithNamedQuery(String query);
	public List<?> findAll(Class<?> type);
	public T update(T t);
	public void delete(Class<?> type, Object id);
	public void deleteAll(Class<?> type);

}
