package br.com.practicalsolutions.monitortermico.facade;

import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.com.practicalsolutions.monitortermico.controller.EquipamentoController;
import br.com.practicalsolutions.monitortermico.controller.EquipamentoEdit;
import br.com.practicalsolutions.monitortermico.controller.LocalidadeController;
import br.com.practicalsolutions.monitortermico.controller.SupervisorController;

@Singleton
@ManagedBean(name="fachada")
public class Fachada {

	@Inject
	private LocalidadeController localidadeController;
	
	@Inject
	private EquipamentoController equipamentoController;
	
	@Inject
	private EquipamentoEdit equipamentoEdit;
	
	@Inject
	private SupervisorController supervisorController;
	
	public LocalidadeController getLocalidadeController(){
		if(localidadeController==null){
			
		}
		return localidadeController;
	}
	
	public EquipamentoController getEquipamentoController(){
		if(equipamentoController==null){
			equipamentoController = new EquipamentoController();
		}
		return equipamentoController;
	}
	
	public EquipamentoEdit getEquipamentoEdit(){
		if(equipamentoEdit==null){
			equipamentoEdit = new EquipamentoEdit();
		}
		return equipamentoEdit;
	}
	
	public SupervisorController getSupervisorController(){
		if(supervisorController==null){
			supervisorController = new SupervisorController();
		}
		return supervisorController;
	}		
}
