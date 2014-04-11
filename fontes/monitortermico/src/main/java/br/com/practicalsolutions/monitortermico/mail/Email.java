package br.com.practicalsolutions.monitortermico.mail;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;


@Named
@SessionScoped
public class Email implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String[] to;
	
	private String from;
	
	private String subject;
	
	private String body;

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public void send() throws Exception {
		Session session = null;	
	
		InitialContext context = new InitialContext();
		session = (Session) context.lookup("java:jboss/mail/Expresso");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		
		Address[] toAddress = new Address[to.length];
		for (int i = 0; i < to.length; i++) {
			toAddress[i] = new InternetAddress(to[i]);
		}
		
		message.setRecipients(Message.RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setContent(body, "text/plain; charset=ISO-8859-1");
		Transport.send(message);
	}	
	
}
