package br.com.practicalsolutions.monitortermico.controller;

import javax.enterprise.inject.Model;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroAlerta;
import br.com.practicalsolutions.monitortermico.exception.SupervisorNaoCadastradoException;
import br.com.practicalsolutions.monitortermico.facade.Fachada;
import br.com.practicalsolutions.monitortermico.model.Alerta;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;

@Model
public class ControladorAlerta {
	
	private static Logger log = LoggerFactory.getLogger(ControladorAlerta.class);
	
	private static final String ALERTA_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroAlerta";
	private static final String FACHADA_LOOKUP = "java:global/monitorweb/Fachada";
	
	private Alerta recuperarAlertaPorEquipamento(Equipamento e) {
		Alerta a = null;
		try {
			final Context con = new InitialContext();
			CadastroAlerta cadastroAlerta = (CadastroAlerta) con.lookup(ALERTA_REGISTRATION_LOOKUP);
			try {
				a = cadastroAlerta.register(e.getId());
			} catch (Exception e1) {
				log.error("Erro ao tentar inserir um novo alerta para o equipamento " + e.getDescricao());
			}
		} catch (NamingException e1) {
			log.error("Erro ao tentar obter a instancia do CadastroAlerta");
		}
		
		return a;
	}

	private void enviarAlerta(Equipamento eq, Medicao m){
		try{
			final Context con = new InitialContext();
			Fachada fachada = (Fachada) con.lookup(FACHADA_LOOKUP);
			switch (eq.getTipoAlerta()) {
			case EMAIL:
				fachada.getControladorEmail().enviarAlertaPorEmail(eq, m);
				break;
			case SMS:
				fachada.getControladorSMS().enviarAlertaPorSMS(eq, m);
				break;
			default:
				fachada.getControladorEmail().enviarAlertaPorEmail(eq, m);
				fachada.getControladorSMS().enviarAlertaPorSMS(eq, m);
			}
		} catch(SupervisorNaoCadastradoException e) {
			log.error(e.getMessage());
		} catch (NamingException ne){
			log.error(ne.getMessage());
		}
	}
	
	private void marcarAlertaComoEnviado(Long id) {
		try {
			final Context con = new InitialContext();
			CadastroAlerta CadastroAlerta = (CadastroAlerta) con.lookup(ALERTA_REGISTRATION_LOOKUP);
			CadastroAlerta.marcarAlertaComoEnviado(id);
		} catch (NamingException e1) {
			log.error("Erro ao tentar obter a instancia do CadastroAlerta");
		}
	}
	
	public void validarNecessidadeAlerta(Medicao med, Equipamento eq){
		
		if(med.getTemperatura() > eq.getLimiteSuperiorTemperatura() ||
			   med.getTemperatura() < eq.getLimiteInferiorTemperatura()||
			   med.getUmidade() > eq.getLimiteSuperiorUmidade() ||
			   med.getUmidade() < eq.getLimiteInferiorUmidade()){
			
			Alerta a = recuperarAlertaPorEquipamento(eq);
			if(a!=null && a.getQtdeAlertas() > eq.getTolerancia() && !a.isEnviado()){
				enviarAlerta(eq, med);
				marcarAlertaComoEnviado(eq.getId());
			}
		}		
		
	}

}
