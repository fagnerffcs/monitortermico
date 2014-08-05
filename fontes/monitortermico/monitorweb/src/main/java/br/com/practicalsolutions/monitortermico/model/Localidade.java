package br.com.practicalsolutions.monitortermico.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Localidade {
	
	@Id
	@SequenceGenerator(name="SEQUENCE_LOCALIDADE", sequenceName="SEQ_LOCALIDADE", allocationSize=10)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_LOCALIDADE")
	@Column(name="loc_codigo")
	private Long id;
	
	@Column(name="loc_descricao")
	private String descricao;
	
	@OneToMany(mappedBy="localidade")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Equipamento> equipamentosSupervisionados;

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

	public List<Equipamento> getEquipamentosSupervisionados() {
		return equipamentosSupervisionados;
	}

	public void setEquipamentosSupervisionados(
			List<Equipamento> equipamentosSupervisionados) {
		this.equipamentosSupervisionados = equipamentosSupervisionados;
	}

}
