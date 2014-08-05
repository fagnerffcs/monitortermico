package br.com.practicalsolutions.monitortermico.test;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.model.Localidade;
import br.com.practicalsolutions.monitortermico.model.Medicao;
import br.com.practicalsolutions.monitortermico.model.Protocolo;
import br.com.practicalsolutions.monitortermico.model.Status;
import br.com.practicalsolutions.monitortermico.model.Supervisor;
import br.com.practicalsolutions.monitortermico.model.TipoAlerta;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;
import br.com.practicalsolutions.monitortermico.service.MedicaoRegistration;

@RunWith(Arquillian.class)
public class EquipamentoRegistronIT {

	@Inject
	private EquipamentoRegistration equipamentoRegistration;
	
	@Inject
	private MedicaoRegistration medicaoRegistration;
	
	@Deployment
	public static WebArchive createDeployment(){
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "monitorweb-test.war")
								   .addClasses(EquipamentoRegistration.class,
										   	   MedicaoRegistration.class,
										   	   Equipamento.class,
										   	   Protocolo.class,
										   	   Status.class,
										   	   Medicao.class,
										   	   TipoAlerta.class,
										   	   Supervisor.class,
										   	   Localidade.class)
								   .addAsResource("META-INF/persistence.xml")
								   .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return war;
	}
	
	@Test
	public void testeInserir(){
		Equipamento e1 = new Equipamento();
		e1.setDescricao("EQU-TESTE-01");
		e1.setStatus(Status.ATIVO);
		e1.setProtocolo(Protocolo.EJB);
		e1.setLimiteSuperiorTemperatura(100);
		e1.setLimiteSuperiorUmidade(100);
		e1.setTipoAlerta(TipoAlerta.EMAIL);
		e1.setTolerancia(5);
		
		try {
			equipamentoRegistration.register(e1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(equipamentoRegistration.listarEquipamentos().size() > 0);
	}
	
	@Test
	public void testeDesativar(){
		Equipamento e = equipamentoRegistration.buscarPorDescricao("EQU-TESTE-01");
		equipamentoRegistration.desativarEquipamento(e);
		Assert.assertEquals(Status.INATIVO, e.getStatus());
	}
	
	@Test
	public void testeRemoverTodos(){
		medicaoRegistration.removerMedicoes();
		equipamentoRegistration.removerTodosOsEquipamentos();
		Assert.assertTrue(equipamentoRegistration.listarEquipamentos().size()==0);
	}	

}
