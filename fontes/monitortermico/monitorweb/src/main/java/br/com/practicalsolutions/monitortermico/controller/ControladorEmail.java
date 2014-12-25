package br.com.practicalsolutions.monitortermico.controller;

import javax.enterprise.inject.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.exception.SupervisorNaoCadastradoException;
import br.com.practicalsolutions.monitortermico.mail.Email;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;

@Model
public class ControladorEmail {
	
	private Logger log = LoggerFactory.getLogger(ControladorEmail.class);
	
	public void enviarAlertaPorEmail(Equipamento eq, Medicao m) throws SupervisorNaoCadastradoException {
		Email email = new Email();
		
		//TODO:configurar arquivo de properties / tabela de parametros
		email.setFrom("monitortermico@cindacta3.intraer");
		
		if(eq.getSupervisores()==null || eq.getSupervisores().size()==0){
			throw new SupervisorNaoCadastradoException(eq);
		}
		
		String[] enderecos = new String[eq.getSupervisores().size()];
		for (int i = 0; i < enderecos.length; i++) {
			enderecos[i] = eq.getSupervisores().get(i).getEmail();
		}

		email.setTo(enderecos);
		email.setSubject("Alerta monitor térmico");
		StringBuilder message = new StringBuilder();
		message.append("Equipamento " + eq.getDescricao() + " atingiu as seguintes marcações que ultrapassam os limites permitidos: \n");
		message.append("Temperatura: " + m.getTemperatura() + "\n");
		message.append("Umidade: " + m.getUmidade() + "\n\n");
		
		message.append("Limites cadastrados:"+"\n");
		message.append("Temperatura menor ou igual a " + eq.getLimiteInferiorTemperatura()+"\n");
		message.append("Temperatura maior ou igual a " + eq.getLimiteSuperiorTemperatura()+"\n");
		message.append("Umidade menor ou igual a " + eq.getLimiteInferiorUmidade()+"\n");
		message.append("Umidade maior ou igual a " + eq.getLimiteSuperiorUmidade()+"\n\n");
		
		message.append("Favor tomar alguma providência.");
		email.setBody(message.toString());
		
		try {
			email.send();
		} catch (Exception e) {
			log.error("Erro ao enviar email.");
			log.error(e.getMessage());
		}
		
	}	

}
