package br.gov.cindacta3.monitortermico.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Medicao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="med_codigo")
	private Long id;
	
	@Column(name="med_horario")
	@Temporal(TemporalType.TIMESTAMP)
	private Date marcacao;
	
	@Column(name="med_temperatura")
	private double temperatura;
	
	@Column(name="med_umidade")
	private double umidade;
	
	@ManyToOne
	@JoinColumn(name="equ_codigo")
	private Equipamento equipamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getMarcacao() {
		return marcacao;
	}

	public void setMarcacao(Date marcacao) {
		this.marcacao = marcacao;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getUmidade() {
		return umidade;
	}

	public void setUmidade(double umidade) {
		this.umidade = umidade;
	}
	
	public Medicao(){};
	
	public Medicao(double t, double u){
		this.temperatura = t;
		this.umidade = u;
		this.marcacao = new Date();
	}

	@Override
	public String toString() {
		return "Medicao [marcacao=" + marcacao + ", temperatura=" + temperatura
				+ ", umidade=" + umidade + "]";
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

}
