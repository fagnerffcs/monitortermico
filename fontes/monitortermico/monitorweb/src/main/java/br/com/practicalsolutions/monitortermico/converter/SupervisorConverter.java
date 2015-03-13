package br.com.practicalsolutions.monitortermico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroSupervisor;
import br.com.practicalsolutions.monitortermico.model.Supervisor;

@Named
public class SupervisorConverter implements Converter {
	
	@Inject
	private  CadastroSupervisor supervisorRegistration;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value!=null){
			//Supervisor s = supervisorRegistration.bufindById(Supervisor.class, Long.parseLong(value));
			Supervisor s = supervisorRegistration.buscarPorId(Long.parseLong(value));
			return s;
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Supervisor) value).getId());
	}

}
