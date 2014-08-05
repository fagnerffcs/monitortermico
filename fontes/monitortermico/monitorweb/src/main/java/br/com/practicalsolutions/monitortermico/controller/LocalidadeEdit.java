package br.com.practicalsolutions.monitortermico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Localidade;
import br.com.practicalsolutions.monitortermico.service.LocalidadeRegistration;

@Named
@RequestScoped
public class LocalidadeEdit {
	
    protected final Logger log = LoggerFactory.getLogger(LocalidadeEdit.class);	
	
	@Inject
	private LocalidadeRegistration localidadeRegistration;
	
	private Localidade localidade;
	
	@PostConstruct
	public void init(){
		if(getLocalidade()==null){
			setLocalidade(new Localidade());
		}
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	public String salvarLocalidade() throws Exception{
        if (localidade.getId() != null) {
            log.info("Atualizando Localidade: " + localidade.getDescricao());
            localidadeRegistration.atualizar(localidade);
        } else {
            log.info("Salvando Localidade: " + localidade.getDescricao());
            localidadeRegistration.register(localidade);
        }

        return "cadastroLocalidade.xhtml?faces-redirect=true";		
	}
	
	public String voltar(){
		return "cadastroLocalidade.xhtml?faces-redirect=true";
	}

}
