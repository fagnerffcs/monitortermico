package br.com.practicalsolutions.monitortermico.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroSupervisor;
import br.com.practicalsolutions.monitortermico.model.Supervisor;

@Model
public class ControladorSupervisor {
	
	@Inject
    private FacesContext facesContext;

	@Inject
	private CadastroSupervisor supervisorRegistration;
	
	private Supervisor novoSupervisor;
    
	@Produces
    @Named
	private Supervisor getNovoSupervisor(){
    	return this.novoSupervisor;
    }
    
    public void register() {
        try {
        	supervisorRegistration.register(novoSupervisor);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado!", "Registro bem sucedido"));
            initNewMember();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível registrar devido o tipo do dado informado!", "Registration mal sucedido"));
        }
    }

    @PostConstruct
    public void initNewMember() {
        novoSupervisor = new Supervisor();
    }
    
	public List<Supervisor> listarSupervisores(){
    	return supervisorRegistration.listarSupervisores();
    }    

}
