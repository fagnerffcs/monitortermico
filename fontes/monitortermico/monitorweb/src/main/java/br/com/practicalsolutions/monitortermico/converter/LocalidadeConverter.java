package br.com.practicalsolutions.monitortermico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.model.Localidade;
import br.com.practicalsolutions.monitortermico.service.LocalidadeRegistration;

@Named
public class LocalidadeConverter implements Converter {
	
	@Inject
	private LocalidadeRegistration localidadeRegistration;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value!=null){
			Localidade l = localidadeRegistration.buscarPorId(Long.parseLong(value));
			return l;
		} else {
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Localidade) value).getId());
	}

}