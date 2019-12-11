package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OfertaServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String nome;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;
	
	private String descricao;
	
	@ElementCollection
    @CollectionTable(name = "TELEFONEOFERTA")
    private Set<String> telefones = new HashSet<>();
	
	private String emailServico;
	
	private String instagram;
	
	private String facebook;
	
	private String endereco;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCriacao;
	
	public OfertaServico() {
		super();
	}
	
	public OfertaServico(Integer id, String nome, Igreja igreja, String descricao,
			String emailServico, String instagram, String facebook, String endereco, LocalDate dataCriacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.igreja = igreja;
		this.descricao = descricao;
		this.emailServico = emailServico;
		this.instagram = instagram;
		this.facebook = facebook;
		this.endereco = endereco;
		this.dataCriacao = dataCriacao;
	}




	public Set<String> getTelefones() {
		return telefones;
	}




	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}




	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Igreja getIgreja() {
		return igreja;
	}


	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getEmailServico() {
		return emailServico;
	}


	public void setEmailServico(String emailServico) {
		this.emailServico = emailServico;
	}


	public String getInstagram() {
		return instagram;
	}


	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}


	public String getFacebook() {
		return facebook;
	}


	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public LocalDate getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
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
		OfertaServico other = (OfertaServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
