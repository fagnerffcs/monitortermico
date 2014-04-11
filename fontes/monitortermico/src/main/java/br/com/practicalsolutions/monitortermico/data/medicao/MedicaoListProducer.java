package br.com.practicalsolutions.monitortermico.data.medicao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.model.Medicao;

@RequestScoped
public class MedicaoListProducer {
	
    @Inject
    private MedicaoRepository medicaoRepository;

    private List<Medicao> medicoes;

    // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Medicao> getMedicoes() {
        return medicoes;
    }

    public void onMedicaoListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Medicao medicao) {
    	medicoesPorEquipamento();
    }

    @PostConstruct
    public void medicoesPorEquipamento() {
        medicoes = medicaoRepository.findAll();
    }	

}
