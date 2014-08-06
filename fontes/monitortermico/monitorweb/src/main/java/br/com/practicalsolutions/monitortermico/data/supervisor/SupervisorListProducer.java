package br.com.practicalsolutions.monitortermico.data.supervisor;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.model.Supervisor;

@RequestScoped
public class SupervisorListProducer {

	@Inject
    private SupervisorRepository supervisorRepository;

    private List<Supervisor> supervisores;

    @Produces
    @Named
    public List<Supervisor> getSupervisores() {
        return supervisores;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Supervisor supervisor) {
    	retrieveAllSupervisores();
    }

    @PostConstruct
    public void retrieveAllSupervisores() {
        supervisores = supervisorRepository.findAll();
    }
}
