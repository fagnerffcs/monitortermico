package br.com.practicalsolutions.monitortermico.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Alerta {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_ALERTA", sequenceName="SEQ_ALERTA", allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_ALERTA")
	@Column(name="ale_codigo")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="equ_codigo", nullable=true)
	private Equipamento equipamento;
	
	@Column(name="ale_dt_envio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;
	
	@Column(name="ale_qtde_alerta")
	private int qtdeAlertas;
	
	@Column(name="ale_enviado")
	private boolean enviado;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ale_status")
	private Situacao status;

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

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
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

	public Situacao getStatus() {
		return status;
	}

	public void setStatus(Situacao status) {
		this.status = status;
	}
}
