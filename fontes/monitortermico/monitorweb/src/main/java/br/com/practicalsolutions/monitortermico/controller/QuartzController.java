package br.com.practicalsolutions.monitortermico.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroAlerta;
import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;
import br.com.practicalsolutions.monitortermico.cadastro.CadastroMedicao;
import br.com.practicalsolutions.monitortermico.exception.SupervisorNaoCadastradoException;
import br.com.practicalsolutions.monitortermico.mail.Email;
import br.com.practicalsolutions.monitortermico.model.Alerta;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;
import br.com.practicalsolutions.monitortermico.model.Protocolo;
import br.com.practicalsolutions.monitortermico.service.EquipamentoEJB;

public class QuartzController implements Job {
	
	private Logger log = LoggerFactory.getLogger(QuartzController.class);
	
	private static final String ALERTA_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroAlerta";	
	private static final String EQUIPAMENTO_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroEquipamento";
	private static final String MEDICAO_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroMedicao";
	private static final String EQUIPAMENTO_EJB_LOOKUP = "java:global/monitorweb/EquipamentoEJB";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job started...");
		
		CadastroMedicao CadastroMedicao = null;
		CadastroEquipamento CadastroEquipamento = null;
				  
		try {
			final Context con = new InitialContext();
			CadastroMedicao = (CadastroMedicao) con.lookup(MEDICAO_REGISTRATION_LOOKUP);
			CadastroEquipamento = (CadastroEquipamento) con.lookup(EQUIPAMENTO_REGISTRATION_LOOKUP);
			
			for (Equipamento e : CadastroEquipamento.listarEquipamentos()) {
				
				try {
					byte data[] = new byte[10];
					
					//obtem os dados de acordo com o protocolo usado
					if(e.getProtocolo().equals(Protocolo.EJB)){
						data = getDataFromEJb();
					} else if(e.getProtocolo().equals(Protocolo.JMS)){
						data = getDataFromJms();
					} else if(e.getProtocolo().equals(Protocolo.RMI)){
						data = getDataFromRmi();
					} else if(e.getProtocolo().equals("SOCKET")){
						data = getDataFromSocket(e);
					} else if(e.getProtocolo().equals("WEB-SERVICE")){
						data = getDataFromWebService();
					} else if(e.getProtocolo().equals("WEB-SOCKET")){
						data = getDataFromWebSocket(); 
					}
					
					String sinal = String.valueOf((char)data[1]);
					String temp = String.format("%s%s.%s", String.valueOf(data[2]), 
														   String.valueOf(data[3]), 
														   String.valueOf(data[4]));
					
					double temperatura = Double.parseDouble(temp);
					
					String umidity = String.format("%s%s.%s", String.valueOf(data[6]), 
							   		 						  String.valueOf(data[7]), 
							   		 						  String.valueOf(data[8]));
					
					double umidade = Double.parseDouble(umidity);
					log.info("Temperatura: " + sinal+temperatura);
					log.info("Umidade: " + umidade);
					
					Medicao m = new Medicao(temperatura, umidade);
					m.setEquipamento(e);
					m.setMarcacao(new Date());
					CadastroMedicao.register(m);
					
					if(m.getTemperatura() > e.getLimiteSuperiorTemperatura() ||
					   m.getTemperatura() < e.getLimiteInferiorTemperatura()||
					   m.getUmidade() > e.getLimiteSuperiorUmidade() ||
					   m.getUmidade() < e.getLimiteInferiorUmidade()){
						Alerta a = registrarAlerta(e);
						if(a!=null && a.getQtdeAlertas() > e.getTolerancia() && !a.isEnviado()){
							enviarAlerta(e, m);
							marcarAlertaComoEnviado(e.getId());
						}
					}
					
				} catch(Exception err){
					log.info("Exception in accessing file");
					err.printStackTrace();
				}

			}
			
		} catch (NamingException e) {
			log.error("Erro ao criar CadastroMedicao");
		}

		
	}
	
	//TODO:implementar a obtencao de dados via RMI	
	private byte[] getDataFromRmi() {
		return null;
	}

	//TODO:implementar a obtencao de dados via JMS
	private byte[] getDataFromJms() {
		return null;
	}

	//TODO:implementar a obtencao de dados via web service
	private byte[] getDataFromWebService() {
		return null;
	}

	private byte[] getDataFromSocket(Equipamento e) throws IOException {
		Socket socket = null;
		BufferedInputStream receive = null;
		byte[] data = new byte[10];
		
		InetAddress address = InetAddress.getByName(e.getIp());
		socket = new Socket(address, e.getPorta());
		socket.sendUrgentData('a');
		receive = new BufferedInputStream(socket.getInputStream());
		receive.read(data,0,data.length);	
		
		return data;
	}

	//TODO:implementar a obtencao dos dados via web socket
	private byte[] getDataFromWebSocket() {
		return null;
	}

	private byte[] getDataFromEJb() throws NamingException {
		final Context con = new InitialContext();
		EquipamentoEJB ejb = (EquipamentoEJB) con.lookup(EQUIPAMENTO_EJB_LOOKUP);
		return ejb.sendData("a");
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

	private Alerta registrarAlerta(Equipamento e) {
		Alerta a = null;
		try {
			final Context con = new InitialContext();
			CadastroAlerta CadastroAlerta = (CadastroAlerta) con.lookup(ALERTA_REGISTRATION_LOOKUP);
			a = CadastroAlerta.register(e.getId());
		} catch (NamingException e1) {
			log.error("Erro ao tentar obter a instancia do CadastroAlerta");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return a;
	}

	private void enviarAlerta(Equipamento eq, Medicao m){
		try{
			switch (eq.getTipoAlerta()) {
			case EMAIL:
				enviarAlertaPorEmail(eq, m);
				break;
			case SMS:
				enviarAlertaPorSMS(eq, m);
				break;
			default:
				enviarAlertaPorEmail(eq, m);
				enviarAlertaPorSMS(eq, m);
			}
		} catch(SupervisorNaoCadastradoException e) {
			log.error(e.getMessage());
		}
	}

	//TODO:implementar envio via SMS
	private void enviarAlertaPorSMS(Equipamento eq, Medicao m) {
		
	}

	private void enviarAlertaPorEmail(Equipamento eq, Medicao m) throws SupervisorNaoCadastradoException {
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
