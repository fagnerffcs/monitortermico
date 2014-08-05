package br.com.practicalsolutions.monitortermico.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Supervisor<T> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="sup_codigo")
	private Long id;
	
	@Column(name="sup_email")
	private String email;
	
	@Column(name="sup_nome")
	private String nome;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="Equipamento_Supervisor",
			   joinColumns={@JoinColumn(name="sup_codigo")},
			   inverseJoinColumns={@JoinColumn(name="equ_codigo")})
	@LazyCollection(LazyCollectionOption.FALSE)
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Supervisor [email=" + email + ", nome=" + nome + "]";
	}

}
