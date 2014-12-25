package br.com.practicalsolutions.monitortermico.controller;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroMedicao;
import br.com.practicalsolutions.monitortermico.facade.Fachada;
import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Medicao;

public class ControladorMedicao {
	
	private Logger log = LoggerFactory.getLogger(ControladorMedicao.class);
	
	private static final String FACHADA_LOOKUP = "java:global/monitorweb/Fachada";	
	private static final String MEDICAO_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroMedicao";
	
	public void obterDados(Equipamento e){
		log.info("Obtendo dados do equipamento " + e.getDescricao());
				  
		try {
			final Context con = new InitialContext();
			Fachada fachada = (Fachada) con.lookup(FACHADA_LOOKUP);
			CadastroMedicao cadastroMedicao = (CadastroMedicao) con.lookup(MEDICAO_REGISTRATION_LOOKUP);
			
			byte data[] = new byte[10];
			data = fachada.getControladorTermohigrometro().getDadosFromEquipamento(e);
			
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
			try {
				cadastroMedicao.register(m);
				fachada.getControladorAlerta().validarNecessidadeAlerta(m, e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
					
		} catch (NamingException ne) {
			log.error("Erro ao criar CadastroMedicao");
		}

	}
	
}
