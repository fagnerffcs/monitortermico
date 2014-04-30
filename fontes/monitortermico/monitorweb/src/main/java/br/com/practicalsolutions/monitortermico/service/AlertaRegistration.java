package br.com.practicalsolutions.monitortermico.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;

import br.com.practicalsolutions.monitortermico.model.Alerta;
import br.com.practicalsolutions.monitortermico.model.Equipamento;

@Stateless
public class AlertaRegistration {
	
    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Alerta> memberEventSrc;

    public Alerta register(Long id) throws Exception {
    	Equipamento equipamento = (Equipamento) em.createQuery("SELECT e FROM Equipamento e where e.id = :id").setParameter("id", id).getSingleResult();
        log.info("Registering new alert for " + equipamento.getDescricao());

        Session session = (Session) em.getDelegate();
        Alerta alerta = findAlertByEquip(equipamento.getId());
        if(alerta==null){
        	alerta = new Alerta();
        	alerta.setEquipamento(equipamento);
        	alerta.setQtdeAlertas(1);
        	session.persist(alerta);
        } else {
        	alerta.setQtdeAlertas(alerta.getQtdeAlertas()+1);
        	session.merge(alerta);
        }
        memberEventSrc.fire(alerta);
        return alerta;
    }
    
    public void marcarAlertaComoEnviado(Long id){
    	log.info("Atualizando alerta como enviado");
    	Session session = (Session) em.getDelegate();
    	Alerta alerta = findAlertByEquip(id);
    	alerta.setEnviado(true);
    	session.merge(alerta);
    }
    
    public Alerta findAlertByEquip(Long id){
    	Alerta a;
    	try {
    		a = (Alerta) em.createQuery("select a from Alerta a where a.equipamento.id = :id").setParameter("id", id).getSingleResult();
    	} catch (NoResultException nre){
    		a = null;
    	}
    	return a;
    }

}
