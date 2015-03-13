package br.com.practicalsolutions.monitortermico.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Protocolo;
import br.com.practicalsolutions.monitortermico.service.EquipamentoEJB;

public class ControladorTermohigrometro {

	private static final String EQUIPAMENTO_EJB_LOOKUP = "java:global/monitorweb/EquipamentoEJB";
	private static final Logger log = LoggerFactory.getLogger(ControladorTermohigrometro.class);
	
	public byte[] getDadosFromEquipamento(Equipamento equipamento){
		byte data[] = new byte[10];
		
		try{
			//obtem os dados de acordo com o protocolo usado
			if(equipamento.getProtocolo().equals(Protocolo.EJB)){
				data = getDataFromEJb();
			} else if(equipamento.getProtocolo().equals(Protocolo.JMS)){
				data = getDataFromJms();
			} else if(equipamento.getProtocolo().equals(Protocolo.RMI)){
				data = getDataFromRmi();
			} else if(equipamento.getProtocolo().equals("SOCKET")){
				data = getDataFromSocket(equipamento);
			} else if(equipamento.getProtocolo().equals("WEB-SERVICE")){
				data = getDataFromWebService();
			} else if(equipamento.getProtocolo().equals("WEB-SOCKET")){
				data = getDataFromWebSocket(); 
			}
		} catch (Exception ex) {
			log.error("Erro ao obter os dados do equipamento " + equipamento.getDescricao() + " usando o protocolo " + equipamento.getProtocolo());
		}
		
		return data;
		
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

}
