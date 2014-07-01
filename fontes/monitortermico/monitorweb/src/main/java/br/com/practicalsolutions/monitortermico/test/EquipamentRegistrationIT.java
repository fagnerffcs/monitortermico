package br.com.practicalsolutions.monitortermico.test;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.practicalsolutions.monitortermico.model.Equipamento;
import br.com.practicalsolutions.monitortermico.service.EquipamentoRegistration;

@RunWith(Arquillian.class)
public class EquipamentRegistrationIT {
	
	@Inject
	private EquipamentoRegistration equipamentoRegistration;
	
	@Deployment
	public static JavaArchive createArchiveAndDeploy(){
		return ShrinkWrap.create(JavaArchive.class)
						 .addClasses(Equipamento.class, EquipamentoRegistration.class)
						 .addAsResource("META-INF/persistence.xml")
						 .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void testListarEquipamentos(){
		assertTrue(equipamentoRegistration.listarEquipamentos().isEmpty());
	}

}
