package br.com.practicalsolutions.monitortermico.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import br.com.practicalsolutions.monitortermico.model.Localidade;

@Stateless
public class LocalidadeRegistration {
	
    @Inject
    private Logger log;

    @PersistenceContext(unitName="primary")
    private EntityManager em;

    @Inject
    private Event<Localidade> localidadeEventSrc;

    public void register(Localidade localidade) throws Exception {
        log.info("Registering " + localidade.getDescricao());

        Session session = (Session) em.getDelegate();
        session.persist(localidade);
        localidadeEventSrc.fire(localidade);
    }
    
    @SuppressWarnings("unchecked")
	public List<Localidade> listarLocalidades(){
    	List<Localidade> lista = em.createQuery("SELECT l FROM Localidade l").getResultList();
    	return lista;
    }    

}
