package br.gov.cindacta3.monitortermico.model;

public enum Status {
	ATIVO("Ativo"), 
	EM_MANUTENCAO("Em Operação"),
	INOPERANTE("Inoperante");
	
	private final String label;

	private Status(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
