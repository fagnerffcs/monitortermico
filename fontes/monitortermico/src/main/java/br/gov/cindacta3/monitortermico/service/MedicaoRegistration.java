package br.gov.cindacta3.monitortermico.service;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.cindacta3.monitortermico.model.Medicao;

@Stateless
public class MedicaoRegistration {
	
	private Logger log = LoggerFactory.getLogger(Medicao.class);
	
    @Inject
    private EntityManager em;

    @Inject
    private Event<Medicao> memberEventSrc;

    public void register(Medicao medicao) throws Exception {
        log.info("Registering " + medicao.toString());

        Session session = (Session) em.getDelegate();
        session.persist(medicao);
        memberEventSrc.fire(medicao);
    }
    
    @SuppressWarnings("unchecked")
	public List<Medicao> todasMedicoes(){
    	List<Medicao> lista = null;
    	lista = em.createQuery("SELECT m FROM Medicao m").getResultList();
    	return lista;
    }
    
    @SuppressWarnings("unchecked")
	public List<Medicao> medicoesPorEquipamento(long id, int qtde){
    	List<Medicao> lista = null;
    	lista = em.createQuery("SELECT m FROM Medicao m WHERE equipamento.id = :equipamento")
    			  .setParameter("equipamento", id)
    			  .setMaxResults(qtde)
    			  .getResultList();
    	return lista;
    }
    
    @SuppressWarnings("unchecked")
	public List<Medicao> medicoesPorEquipamentoPorPeriodo(long id, Date d1, Date d2, int qtde){
    	List<Medicao> lista = null;
    	lista = em.createQuery("SELECT m FROM Medicao m WHERE equipamento.id = :equipamento AND m.marcacao between :data1 AND :data2")
    			  .setParameter("equipamento", id)
    			  .setParameter("data1", d1)
    			  .setParameter("data2", d2)
    			  .setMaxResults(qtde)
    			  .getResultList();
    	return lista;
    }    

}
