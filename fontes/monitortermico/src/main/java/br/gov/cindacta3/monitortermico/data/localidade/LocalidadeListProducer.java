package br.gov.cindacta3.monitortermico.data.localidade;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.cindacta3.monitortermico.model.Localidade;

@RequestScoped
public class LocalidadeListProducer {

	@Inject
    private LocalidadeRepository localidadeRepository;

    private List<Localidade> localidades;

    @Produces
    @Named
    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Localidade localidade) {
    	retrieveAllLocalidades();
    }

    @PostConstruct
    public void retrieveAllLocalidades() {
        localidades = localidadeRepository.findAll();
    }
}
