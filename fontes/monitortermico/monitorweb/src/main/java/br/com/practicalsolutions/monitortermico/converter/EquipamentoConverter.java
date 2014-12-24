package br.com.practicalsolutions.monitortermico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;
import br.com.practicalsolutions.monitortermico.model.Equipamento;

@Named
public class EquipamentoConverter implements Converter {
	
	@Inject
	private CadastroEquipamento equipamentoRegistration;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value!=null){
			Equipamento e = equipamentoRegistration.buscarPorId(Long.parseLong(value));
			return e;
		} else {
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Equipamento) value).getId());
	}

}