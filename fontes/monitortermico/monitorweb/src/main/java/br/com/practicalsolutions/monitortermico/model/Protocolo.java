package br.com.practicalsolutions.monitortermico.model;

public enum Protocolo {
	
	EJB("EJB"),
	JMS("JMS"),
	RMI("RMI"),
	SOCKET("SOCKET"),
	WEB_SERVICE("WEB-SERVICE"),
	WEB_SOCKET("WEB-SOCKET");
	
	private final String label;

	private Protocolo(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}	

}