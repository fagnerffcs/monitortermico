package br.com.practicalsolutions.monitortermico.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Alerta {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_ALERTA", sequenceName="SEQ_ALERTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_ALERTA")
	@Column(name="ale_codigo")
	private Long id;
	
	@OneToOne
	private Equipamento equipamento;
	
	@Column(name="ale_qtde_alerta")
	private int qtdeAlertas;
	
	@Column(name="ale_enviado")
	private boolean enviado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public int getQtdeAlertas() {
		return qtdeAlertas;
	}

	public void setQtdeAlertas(int qtdeAlertas) {
		this.qtdeAlertas = qtdeAlertas;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
}
