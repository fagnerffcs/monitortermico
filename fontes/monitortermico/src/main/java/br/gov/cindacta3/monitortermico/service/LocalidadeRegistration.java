package br.gov.cindacta3.monitortermico.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.gov.cindacta3.monitortermico.model.Localidade;

@Stateless
public class LocalidadeRegistration {
	
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Localidade> localidadeEventSrc;

    public void register(Localidade localidade) throws Exception {
        log.info("Registering " + localidade.getDescricao());

        Session session = (Session) em.getDelegate();
        session.persist(localidade);
        localidadeEventSrc.fire(localidade);
    }	

}
