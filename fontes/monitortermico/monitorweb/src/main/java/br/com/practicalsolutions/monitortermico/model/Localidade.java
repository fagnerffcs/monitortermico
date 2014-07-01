package br.com.practicalsolutions.monitortermico.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Localidade {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_LOCALIDADE", sequenceName="SEQ_LOCALIDADE", allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_LOCALIDADE")
	@Column(name="loc_codigo")
	private Long id;
	
	@Column(name="loc_descricao")
	private String descricao;
	
	@OneToMany
	@JoinTable(name="localidade_equipamento", joinColumns={@JoinColumn(name="loc_codigo")}, inverseJoinColumns={@JoinColumn(name="equ_codigo")})
	private List<Equipamento> equipamentos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
