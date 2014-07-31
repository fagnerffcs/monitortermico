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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Equipamento {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_EQUIPAMENTO", sequenceName="SEQ_EQUIPAMENTO", allocationSize=10)
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
	
	@Enumerated(EnumType.STRING)
	@Column(name="equ_protocolo")
	private Protocolo protocolo;
	
	@Column(name="equ_tolerancia", nullable=true)
	private int tolerancia;
	
	@Enumerated(EnumType.STRING)
	@Column(name="equ_tipo_alerta")
	private TipoAlerta tipoAlerta;
	
	@ManyToMany(mappedBy="equipamentos")
	private List<Supervisor> supervisores;
	
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

	public int getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(int tolerancia) {
		this.tolerancia = tolerancia;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public List<Supervisor> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Supervisor> supervisores) {
		this.supervisores = supervisores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Equipamento [descricao=" + descricao + ", status=" + status
				+ ", ip=" + ip + ", porta=" + porta
				+ ", limiteInferiorTemperatura=" + limiteInferiorTemperatura
				+ ", limiteSuperiorTemperatura=" + limiteSuperiorTemperatura
				+ ", limiteInferiorUmidade=" + limiteInferiorUmidade
				+ ", limiteSuperiorUmidade=" + limiteSuperiorUmidade
				+ ", protocolo=" + protocolo + ", tolerancia=" + tolerancia
				+ "]";
	}
	
}
