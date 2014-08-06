package br.com.practicalsolutions.monitortermico.data.supervisor;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.practicalsolutions.monitortermico.model.Supervisor;

@ApplicationScoped
public class SupervisorRepository {
	
	@Inject
	private EntityManager em;
	
	public Supervisor findById(Long id){
		return em.find(Supervisor.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Supervisor> findAll() {
		return em.createQuery("SELECT s FROM Supervisor s").getResultList();
	}	

}
