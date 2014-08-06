package br.com.practicalsolutions.monitortermico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Supervisor;
import br.com.practicalsolutions.monitortermico.service.SupervisorRegistration;

@Named
@RequestScoped
public class SupervisorEdit {
	
    protected final Logger log = LoggerFactory.getLogger(SupervisorEdit.class);	
	
	@Inject
	private SupervisorRegistration supervisorRegistration;
	
	private Supervisor supervisor;
	
	@PostConstruct
	public void init(){
		if(getSupervisor()==null){
			setSupervisor(new Supervisor());
		}
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public String salvar() throws Exception{
        if (supervisor.getId() != null) {
            log.info("Atualizando Supervisor: " + supervisor.getNome());
            supervisorRegistration.atualizar(supervisor);
        } else {
            log.info("Salvando Supervisor: " + supervisor.getNome());
            supervisorRegistration.register(supervisor);
        }

        return "cadastroSupervisor.xhtml?faces-redirect=true";
	}
	
	public String voltar(){
		return "cadastroSupervisor.xhtml?faces-redirect=true";
	}

}
