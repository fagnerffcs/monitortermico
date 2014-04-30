package br.com.practicalsolutions.monitortermico.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Status;

@Stateless
public class EquipamentoRegistration {
	
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Equipamento> memberEventSrc;

    public void register(Equipamento equipamento) throws Exception {
        log.info("Registering " + equipamento.getDescricao());

        Session session = (Session) em.getDelegate();
        session.persist(equipamento);
        memberEventSrc.fire(equipamento);
    }
    
    @SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentos(){
    	List<Equipamento> lista = em.createQuery("select e from Equipamento e WHERE e.status = :status").setParameter("status", Status.ATIVO).getResultList();
    	return lista;
    }

}
