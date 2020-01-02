package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OfertaServicoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String idIgreja;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	private Set<String> telefones = new HashSet<>();

	private String emailServico;

	private String instagram;

	private String facebook;

	private String endereco;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;
	
	public OfertaServicoNewDTO() {
		super();
	}

	public OfertaServicoNewDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String nome,
			@NotEmpty(message = "Preenchimento obrigatório") String idIgreja,
			@NotEmpty(message = "Preenchimento obrigatório") String descricao,
			Set<String> telefones,
			String emailServico,
			String instagram,
			String facebook,
			String endereco,
			String dataCriacao
			) {
		super();
		this.id = id;
		this.nome = nome;
		this.idIgreja = idIgreja;
		this.descricao = descricao;
		this.telefones = telefones;
		this.emailServico = emailServico;
		this.instagram = instagram;
		this.facebook = facebook;
		this.endereco = endereco;
		this.dataCriacao = dataCriacao;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getIdIgreja() {
		return idIgreja;
	}

	public void setIdIgreja(String idIgreja) {
		this.idIgreja = idIgreja;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
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

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
