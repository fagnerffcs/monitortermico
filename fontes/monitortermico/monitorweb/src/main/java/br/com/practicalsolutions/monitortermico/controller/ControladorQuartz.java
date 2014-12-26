package br.com.practicalsolutions.monitortermico.controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;
import br.com.practicalsolutions.monitortermico.facade.Fachada;
import br.com.practicalsolutions.monitortermico.model.Equipamento;

public class ControladorQuartz implements Job {
	
	private Logger log = LoggerFactory.getLogger(ControladorQuartz.class);
	
	private static final String FACHADA_LOOKUP = "java:global/monitorweb/Fachada";	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job started...");
		
		try {
			final Context con = new InitialContext();
			Fachada fachada = (Fachada) con.lookup(FACHADA_LOOKUP);
			ControladorEquipamento controladorEquipamento = fachada.getControladorEquipamento();
			CadastroEquipamento cadastroEquipamento = controladorEquipamento.getCadastroEquipamento();
			
			for (Equipamento e : cadastroEquipamento.listarEquipamentos()) {
				ControladorMedicao controladorMedicao = fachada.getControladorMedicao();
				controladorMedicao.obterDados(e);
			}
			
		} catch (NamingException e) {
			log.error(e.getMessage());
			log.error("Erro ao invocar a fachada.");
		}

	}
	
}
