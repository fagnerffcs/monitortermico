package br.com.practicalsolutions.monitortermico.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class CrudServiceBean<T> implements CrudService<T> {

	@PersistenceContext(unitName="primary")
	private EntityManager em;
	
    private Logger log = LoggerFactory.getLogger(CrudServiceBean.class);
    
    @Inject
    private Event<T> memberEventSrc;    
	
	@Override
	public void create(T t) {
		log.info("Registrando " + t.toString());
		Session session = (Session) em.getDelegate();
		session.persist(t);
		session.flush();
		memberEventSrc.fire(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Class<?> type, Object id) {
		return (T) em.find(type, id);
	}

	@Override
	public List<?> findWithNamedQuery(String query) {
		return em.createNamedQuery(query).getResultList();
	}

	@Override
	public List<?> findAll(Class<?> type) {
		List<?> data = null;
		data = ((Session) em.getDelegate()).createQuery("from " + type.getName()).list();
		return data;
	}

	@Override
	public T update(T t) {
		return em.merge(t);
	}

	@Override
	public void delete(Class<?> type, Object id) {
		Session session = (Session) em.getDelegate();
		session.delete(type);
	}

	@Override
	public void deleteAll(Class<?> type) {
		Session session = (Session) em.getDelegate();
		session.createQuery("DELETE FROM " + type.getName()).executeUpdate();
	}

}
