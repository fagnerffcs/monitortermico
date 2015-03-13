package br.com.practicalsolutions.monitortermico.facade;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.controller.ControladorAlerta;
import br.com.practicalsolutions.monitortermico.controller.ControladorEmail;
import br.com.practicalsolutions.monitortermico.controller.ControladorEquipamento;
import br.com.practicalsolutions.monitortermico.controller.ControladorGrafico;
import br.com.practicalsolutions.monitortermico.controller.ControladorLocalidade;
import br.com.practicalsolutions.monitortermico.controller.ControladorMedicao;
import br.com.practicalsolutions.monitortermico.controller.ControladorSMS;
import br.com.practicalsolutions.monitortermico.controller.ControladorSupervisor;
import br.com.practicalsolutions.monitortermico.controller.ControladorTermohigrometro;
import br.com.practicalsolutions.monitortermico.controller.EquipamentoEdit;
import br.com.practicalsolutions.monitortermico.controller.LocalidadeEdit;
import br.com.practicalsolutions.monitortermico.controller.SupervisorEdit;

@Singleton
@Named(value="fachada")
public class Fachada {

	@Inject
	private ControladorLocalidade controladorLocalidade;
	
	@Inject
	private ControladorEquipamento controladorEquipamento;
	
	@Inject
	private ControladorSupervisor controladorSupervisor;
	
	@Inject
	private ControladorAlerta controladorAlerta;
	
	@Inject
	private ControladorMedicao controladorMedicao;
	
	@Inject
	private ControladorEmail controladorEmail;
	
	@Inject
	private ControladorSMS controladorSMS;
	
	@Inject
	private ControladorTermohigrometro controladorTermohigrometro;
	
	@Inject
	private ControladorGrafico controladorGrafico;
	
	@Inject
	private EquipamentoEdit equipamentoEdit;

	@Inject
	private LocalidadeEdit localidadeEdit;
	
	@Inject
	private SupervisorEdit supervisorEdit;
	
	public ControladorLocalidade getControladorLocalidade(){
		if(controladorLocalidade==null){
			controladorLocalidade = new ControladorLocalidade();
		}
		return controladorLocalidade;
	}
	
	public ControladorEquipamento getControladorEquipamento(){
		if(controladorEquipamento==null){
			controladorEquipamento = new ControladorEquipamento();
		} 
		return controladorEquipamento;
	}
	
	public EquipamentoEdit getEquipamentoEdit(){
		if(equipamentoEdit==null){
			equipamentoEdit = new EquipamentoEdit();
		}
		return equipamentoEdit;
	}
	
	public LocalidadeEdit getLocalidadeEdit(){
		if(localidadeEdit==null){
			localidadeEdit = new LocalidadeEdit();
		}
		return localidadeEdit;
	}
	
	public SupervisorEdit getSupervisorEdit(){
		if(supervisorEdit==null){
			supervisorEdit = new SupervisorEdit();
		}
		return supervisorEdit;
	}
	
	public ControladorSupervisor getControladorSupervisor(){
		if(controladorSupervisor==null){
			controladorSupervisor = new ControladorSupervisor();
		}
		return controladorSupervisor;
	}

	public ControladorAlerta getControladorAlerta() {
		if(controladorAlerta == null){
			controladorAlerta = new ControladorAlerta();
		}
		return controladorAlerta;
	}
	
	public ControladorMedicao getControladorMedicao(){
		if(controladorMedicao==null){
			controladorMedicao = new ControladorMedicao();
		}
		return controladorMedicao;
	}
	
	public ControladorEmail getControladorEmail(){
		if(controladorEmail == null){
			controladorEmail = new ControladorEmail();
		}
		return controladorEmail;
	}
	
	public ControladorSMS getControladorSMS(){
		if(controladorSMS == null){
			controladorSMS = new ControladorSMS();
		}
		return controladorSMS;
	}
	
	public ControladorTermohigrometro getControladorTermohigrometro(){
		if(controladorTermohigrometro == null){
			controladorTermohigrometro = new ControladorTermohigrometro();
		}
		return controladorTermohigrometro;
	}
	
	public ControladorGrafico getControladorGrafico(){
		if(controladorGrafico==null){
			controladorGrafico = new ControladorGrafico();
		}
		return controladorGrafico;
	}

}
