package br.gov.cindacta3.monitortermico.data.equipamento;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.gov.cindacta3.monitortermico.model.Equipamento;

@ApplicationScoped
public class EquipamentoRepository {
	
	@Inject
	private EntityManager em;
	
	public Equipamento findById(Long id){
		return em.find(Equipamento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> findAll() {
		List<Equipamento> lista = em.createQuery("SELECT e FROM Equipamento e").getResultList(); 
		return lista;
	}

}
