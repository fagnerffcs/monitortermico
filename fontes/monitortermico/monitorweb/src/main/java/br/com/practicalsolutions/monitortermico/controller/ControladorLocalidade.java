package br.com.practicalsolutions.monitortermico.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;
import br.com.practicalsolutions.monitortermico.cadastro.CadastroLocalidade;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Localidade;

@ApplicationScoped
public class ControladorLocalidade {
	
	@Inject
    private FacesContext facesContext;

    @Inject
    private CadastroLocalidade localidadeRegistration;
    
    @Inject
    private CadastroEquipamento equipamentoRegistration;

    private Localidade novaLocalidade;

    @Produces
    @Named
    public Localidade getNovaLocalidade() {
        return novaLocalidade;
    }

    public void register() {
        try {
        	localidadeRegistration.register(novaLocalidade);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado!", "Registro bem sucedido"));
            initNewMember();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível registrar devido o tipo do dado informado!", "Registration mal sucedido"));
        }
    }

    @PostConstruct
    public void initNewMember() {
        novaLocalidade = new Localidade();
    }
    
    public List<Equipamento> listarEquipamentos(){
    	return equipamentoRegistration.listarEquipamentos();
    }
    
}
