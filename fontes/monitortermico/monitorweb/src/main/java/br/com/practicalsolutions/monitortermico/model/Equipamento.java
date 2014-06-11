package br.com.practicalsolutions.monitortermico.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Equipamento {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_EQUIPAMENTO", sequenceName="SEQ_EQUIPAMENTO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_EQUIPAMENTO")
	@Column(name="equ_codigo")
	private Long id;
	
	@Column(name="equ_descricao")
	private String descricao;
	
	@Column(name="equ_status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name="equ_endereco_ip")
	private String ip;
	
	@Column(name="equ_porta")
	private int porta;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="equipamento")
	private List<Medicao> medicoes;
	
	@Column(name="equ_limite_inf_temp")
	private double limiteInferiorTemperatura;
	
	@Column(name="equ_limite_sup_temp")
	private double limiteSuperiorTemperatura;
	
	@Column(name="equ_limite_inf_umid")
	private double limiteInferiorUmidade;
	
	@Column(name="equ_limite_sup_umid")
	private double limiteSuperiorUmidade;
	
	@Column(name="equ_protocolo")
	@Enumerated(EnumType.STRING)
	private Protocolo protocolo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public List<Medicao> getMedicoes() {
		return medicoes;
	}

	public void setMedicoes(List<Medicao> medicoes) {
		this.medicoes = medicoes;
	}

	public double getLimiteInferiorTemperatura() {
		return limiteInferiorTemperatura;
	}

	public void setLimiteInferiorTemperatura(double limiteInferiorTemperatura) {
		this.limiteInferiorTemperatura = limiteInferiorTemperatura;
	}

	public double getLimiteSuperiorTemperatura() {
		return limiteSuperiorTemperatura;
	}

	public void setLimiteSuperiorTemperatura(double limiteSuperiorTemperatura) {
		this.limiteSuperiorTemperatura = limiteSuperiorTemperatura;
	}

	public double getLimiteInferiorUmidade() {
		return limiteInferiorUmidade;
	}

	public void setLimiteInferiorUmidade(double limiteInferiorUmidade) {
		this.limiteInferiorUmidade = limiteInferiorUmidade;
	}

	public double getLimiteSuperiorUmidade() {
		return limiteSuperiorUmidade;
	}

	public void setLimiteSuperiorUmidade(double limiteSuperiorUmidade) {
		this.limiteSuperiorUmidade = limiteSuperiorUmidade;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	
}
