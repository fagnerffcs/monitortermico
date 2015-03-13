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

import br.com.practicalsolutions.monitortermico.cadastro.CadastroLocalidade;
import br.com.practicalsolutions.monitortermico.model.Localidade;

@RunWith(Arquillian.class)
public class LocalidadeRegistrationIT {
	
	@Inject
	private CadastroLocalidade localidadeRegistration;
	
	@Deployment
	public static WebArchive createDeployment(){
		WebArchive war = ShrinkWrap.create(WebArchive.class, "monitorweb-test.war")
								   .addClasses(CadastroLocalidade.class,
										   	   Localidade.class)
								   .addAsResource("META-INF/persistence.xml")
								   .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		return war;
	}
	
	@Test
	public void testeInserirLocalidade(){
		Localidade l = new Localidade();
		l.setDescricao("DTCEA-RF");
		try {
			localidadeRegistration.register(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(localidadeRegistration.listarLocalidades().size() > 0);
		
	}

}
