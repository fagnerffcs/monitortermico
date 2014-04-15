package br.com.practicalsolutions.monitortermico.quartz;

import java.io.BufferedInputStream;
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

import br.com.practicalsolutions.monitortermico.mail.Email;
import br.com.practicalsolutions.monitortermico.model.Alerta;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;
import br.com.practicalsolutions.monitortermico.service.AlertaRegistration;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;
import br.com.practicalsolutions.monitortermico.service.MedicaoRegistration;

public class SocketJob implements Job {
	
	private Logger log = LoggerFactory.getLogger(SocketJob.class);
	
	private static final String ALERTA_REGISTRATION_LOOKUP = "java:global/monitortermico/AlertaRegistration";	
	private static final String EQUIPAMENTO_REGISTRATION_LOOKUP = "java:global/monitortermico/EquipamentoRegistration";
	private static final String MEDICAO_REGISTRATION_LOOKUP = "java:global/monitortermico/MedicaoRegistration";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job started...");
		
		Socket socket = null;
		BufferedInputStream receive = null;
		MedicaoRegistration medicaoRegistration = null;
		EquipamentoRegistration equipamentoRegistration = null;
		
		try {
			final Context con = new InitialContext();
			medicaoRegistration = (MedicaoRegistration) con.lookup(MEDICAO_REGISTRATION_LOOKUP);
			equipamentoRegistration = (EquipamentoRegistration) con.lookup(EQUIPAMENTO_REGISTRATION_LOOKUP);
			
			for (Equipamento e : equipamentoRegistration.listarEquipamentos()) {
				
				try {
					InetAddress address = InetAddress.getByName(e.getIp());
					socket = new Socket(address, e.getPorta());
					socket.sendUrgentData('a');
					receive = new BufferedInputStream(socket.getInputStream());
					byte data[] = new byte[10];
					receive.read(data,0,data.length);
					
					String sinal = String.valueOf((char)data[1]);
					String temp = String.format("%s%s.%s", String.valueOf((char)data[2]), 
														   String.valueOf((char)data[3]), 
														   String.valueOf((char)data[4]));
					
					double temperatura = Double.parseDouble(temp);
					
					String umidity = String.format("%s%s.%s", String.valueOf((char)data[6]), 
							   		 String.valueOf((char)data[7]), 
							   		 String.valueOf((char)data[8]));
					
					double umidade = Double.parseDouble(umidity);
					log.info("Temperatura: " + sinal+temperatura);
					log.info("Umidade: " + umidade);
					
					Medicao m = new Medicao(temperatura, umidade);
					m.setEquipamento(e);
					m.setMarcacao(new Date());
					medicaoRegistration.register(m);
					
					if(m.getTemperatura() > e.getLimiteSuperiorTemperatura() ||
					   m.getTemperatura() < e.getLimiteInferiorTemperatura()||
					   m.getUmidade() > e.getLimiteSuperiorUmidade() ||
					   m.getUmidade() < e.getLimiteInferiorUmidade()){
						Alerta a = registrarAlerta(e);
						if(a!=null && a.getQtdeAlertas() > 3 && !a.isEnviado()){
							enviarAlerta(e, m);
							marcarAlertaComoEnviado(e.getId());
						}
					}
					
					receive.close();
					socket.close();
				} catch(Exception err){
					log.info("Exception in accessing file");
					err.printStackTrace();
				}		

			}
			
		} catch (NamingException e) {
			log.error("Erro ao criar medicaoRegistration");
		}

		
	}
	
	private void marcarAlertaComoEnviado(Long id) {
		try {
			final Context con = new InitialContext();
			AlertaRegistration alertaRegistration = (AlertaRegistration) con.lookup(ALERTA_REGISTRATION_LOOKUP);
			alertaRegistration.marcarAlertaComoEnviado(id);
		} catch (NamingException e1) {
			log.error("Erro ao tentar obter a instancia do AlertaRegistration");
		}
	}

	private Alerta registrarAlerta(Equipamento e) {
		Alerta a = null;
		try {
			final Context con = new InitialContext();
			AlertaRegistration alertaRegistration = (AlertaRegistration) con.lookup(ALERTA_REGISTRATION_LOOKUP);
			a = alertaRegistration.register(e.getId());
		} catch (NamingException e1) {
			log.error("Erro ao tentar obter a instancia do AlertaRegistration");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return a;
	}

	private void enviarAlerta(Equipamento eq, Medicao m){
		Email email = new Email();
		email.setFrom("monitortermico@cindacta3.intraer");
		String[] enderecos = new String[2];
		enderecos[0] = "rodrigorvq@cindacta3.intraer";
		enderecos[1] = "fagnerffcs@cindacta3.intraer";
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
