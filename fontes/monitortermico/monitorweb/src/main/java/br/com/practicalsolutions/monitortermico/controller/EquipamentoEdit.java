package br.com.practicalsolutions.monitortermico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;

@Named
@RequestScoped
public class EquipamentoEdit {
	
    protected final Logger log = LoggerFactory.getLogger(EquipamentoEdit.class);	
	
	@Inject
	private EquipamentoRegistration equipamentoRegistration;
	
	private Equipamento equipamento;
	
	@PostConstruct
	public void init(){
		if(getEquipamento()==null){
			setEquipamento(new Equipamento());
		}
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	public String salvarEquipamento() throws Exception{
        if (equipamento.getId() != null) {
            log.info("Atualizando equipamento: " + equipamento.getDescricao());
            equipamentoRegistration.atualizar(equipamento);
        } else {
            log.info("Salvando equipamento: " + equipamento.getDescricao());
            equipamentoRegistration.register(equipamento);
        }

        return "cadastroEquipamento.xhtml?faces-redirect=true";		
	}
	
	public String voltar(){
		return "cadastroEquipamento.xhtml?faces-redirect=true";
	}

}
