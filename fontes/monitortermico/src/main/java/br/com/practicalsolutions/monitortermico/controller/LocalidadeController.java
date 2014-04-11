package br.com.practicalsolutions.monitortermico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.model.Localidade;
import br.com.practicalsolutions.monitortermico.service.LocalidadeRegistration;

@Model
public class LocalidadeController {
	
	@Inject
    private FacesContext facesContext;

    @Inject
    private LocalidadeRegistration localidadeRegistration;

    private Localidade novaLocalidade;

    @Produces
    @Named
    public Localidade getNovaLocalidade() {
        return novaLocalidade;
    }

    public void register() {
        try {
        	localidadeRegistration.register(novaLocalidade);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
            initNewMember();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Not Registered due to error in data entered!", "Registration unsuccessful"));
        }
    }

    @PostConstruct
    public void initNewMember() {
        novaLocalidade = new Localidade();
    }
}
