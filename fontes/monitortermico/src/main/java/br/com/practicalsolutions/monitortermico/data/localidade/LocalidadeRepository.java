package br.com.practicalsolutions.monitortermico.data.localidade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.practicalsolutions.monitortermico.model.Localidade;

@ApplicationScoped
public class LocalidadeRepository {
	
	@Inject
	private EntityManager em;
	
	public Localidade findById(Long id){
		return em.find(Localidade.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Localidade> findAll() {
		return em.createQuery("SELECT l FROM Localidade l").getResultList();
	}	

}
