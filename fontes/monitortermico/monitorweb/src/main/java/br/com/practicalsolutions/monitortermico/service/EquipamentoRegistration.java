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

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Status;

@Stateless
public class EquipamentoRegistration {
	
    private Logger log = LoggerFactory.getLogger(EquipamentoRegistration.class);

    @PersistenceContext(unitName="primary")
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
    
    public Equipamento buscarPorId(Long id){
    	Equipamento e = (Equipamento) em.createQuery("select e from Equipamento e WHERE e.id = :id").setParameter("id", id).getSingleResult();
    	return e;
    }
    
    public Equipamento buscarPorDescricao(String desc){
    	Equipamento e = (Equipamento) em.createQuery("select e from Equipamento e WHERE e.descricao = :descricao")
    									.setParameter("descricao", desc)
    									.getSingleResult();
    	return e;
    }    
    
    public void desativarEquipamento(Equipamento e){
    	log.info("Desativando equipamento: " + e.getDescricao());
    	Session session = (Session) em.getDelegate();
    	e.setStatus(Status.INATIVO);
    	session.merge(e);
    }
    
    public void reativarEquipamento(Equipamento e){
    	log.info("Reativando equipamento: " + e.getDescricao());
    	Session session = (Session) em.getDelegate();
    	e.setStatus(Status.ATIVO);
    	session.merge(e);
    }
    
    public void removerTodosOsEquipamentos(){
    	em.createQuery("DELETE from Equipamento e").executeUpdate();
    }

	public void atualizar(Equipamento equipamento) {
		em.merge(equipamento);
	}    
    
}
