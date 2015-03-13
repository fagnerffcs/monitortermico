package br.com.practicalsolutions.monitortermico.cadastro;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Supervisor;

@Stateless
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CadastroSupervisor {
	
    private Logger log = LoggerFactory.getLogger(CadastroSupervisor.class);

    @PersistenceContext(unitName="primary")
    private EntityManager em;

	@Inject
    private Event<Supervisor> memberEventSrc;

    public void register(Supervisor supervisor) throws Exception {
        log.info("Registrando " + supervisor.getNome());

        Session session = (Session) em.getDelegate();
        session.persist(supervisor);
        memberEventSrc.fire(supervisor);
    }
    
	public List<Supervisor> listarSupervisores(){
    	List<Supervisor> lista = em.createQuery("select s from Supervisor s").getResultList();
    	return lista;
    }
    
    public Supervisor buscarPorId(Long id){
    	Supervisor s = (Supervisor) em.createQuery("select s from Supervisor s WHERE s.id = :id").setParameter("id", id).getSingleResult();
    	return s;
    }
        
    public void remover(Long id){
    	em.createQuery("DELETE from Supervisor s WHERE s.id = :id").setParameter("id", id).executeUpdate();
    }

	public void atualizar(Supervisor supervisor) {
		em.merge(supervisor);
	}    	

}
