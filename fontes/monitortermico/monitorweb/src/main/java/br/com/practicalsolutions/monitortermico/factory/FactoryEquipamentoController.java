package br.com.practicalsolutions.monitortermico.factory;

import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.practicalsolutions.monitortermico.cadastro.CadastroEquipamento;

@Singleton
public class FactoryEquipamentoController {
	
	private static final String EQUIPAMENTO_REGISTRATION_LOOKUP = "java:global/monitorweb/CadastroEquipamento";
	private static CadastroEquipamento cadastroEquipamento = null;
	
	private static CadastroEquipamento getCadastroEquipamentoInstance(){
		if(cadastroEquipamento==null){
			Context con;
			try {
				con = new InitialContext();
				cadastroEquipamento = (CadastroEquipamento) con.lookup(EQUIPAMENTO_REGISTRATION_LOOKUP);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return cadastroEquipamento;
	}

}
