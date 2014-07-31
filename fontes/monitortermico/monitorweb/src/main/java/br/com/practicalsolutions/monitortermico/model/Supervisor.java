package br.com.practicalsolutions.monitortermico.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Supervisor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sup_codigo")
	private Long id;
	
	@Column(name="sup_email")
	private String email;
	
	@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(name="Equipamento_Supervisor",
			   joinColumns={@JoinColumn(name="sup_codigo")},
			   inverseJoinColumns={@JoinColumn(name="equ_codigo")})
	private List<Equipamento> equipamentos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

}
