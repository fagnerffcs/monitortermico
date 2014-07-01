package br.com.practicalsolutions.monitortermico.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;

@FacesConverter("equipamentoConverter")
public class EquipamentoConverter implements Converter {
	
	@Inject
	private EquipamentoRegistration equipamentoRegistration;
	
	private static final String EQUIPAMENTO_REGISTRATION_LOOKUP = "java:global/monitorweb/EquipamentoRegistration";	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Equipamento e = new Equipamento();
		if(value!=null){
			if(equipamentoRegistration==null){
				Context con;
				try {
					con = new InitialContext();
					equipamentoRegistration = (EquipamentoRegistration) con.lookup(EQUIPAMENTO_REGISTRATION_LOOKUP);
				} catch (NamingException e1) {
					e1.printStackTrace();
				}
			}
			e = equipamentoRegistration.buscarPorId(Long.parseLong(value));
		}
		return e;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return String.valueOf(((Equipamento) value).getId());
	}

}
