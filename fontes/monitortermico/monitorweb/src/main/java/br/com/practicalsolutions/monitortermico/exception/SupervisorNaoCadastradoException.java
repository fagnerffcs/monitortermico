package br.com.practicalsolutions.monitortermico.exception;

import br.com.practicalsolutions.monitortermico.model.Equipamento;

public class SupervisorNaoCadastradoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Equipamento equipamento;

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	public String getMessage(){
		return "Equipamento " + equipamento.getDescricao() + " não possui nenhum supervisor cadastrado.";
	}

	public SupervisorNaoCadastradoException() {
		super();
	}

	public SupervisorNaoCadastradoException(Equipamento equipamento) {
		super();
		this.equipamento = equipamento;
	}

}
