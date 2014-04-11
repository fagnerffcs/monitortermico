package br.gov.cindacta3.monitortermico.data.medicao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.gov.cindacta3.monitortermico.model.Medicao;

@ApplicationScoped
public class MedicaoRepository {
	
	@Inject
	private EntityManager em;
	
	public Medicao findById(Long id){
		return em.find(Medicao.class, id);
	}	

	@SuppressWarnings("unchecked")
	public List<Medicao> findAllById(Long id) {
		List<Medicao> medicoes = em.createQuery("SELECT m FROM Medicao m WHERE m.id = :id").setParameter("id", id).getResultList();
		return medicoes;
	}

	@SuppressWarnings("unchecked")
	public List<Medicao> findAll() {
		List<Medicao> medicoes = em.createQuery("SELECT m FROM Medicao m").getResultList();
		return medicoes;
	}
	

}
