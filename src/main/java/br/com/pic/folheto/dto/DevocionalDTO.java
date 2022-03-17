package br.com.pic.folheto.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DevocionalDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String referencia;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String idIgreja;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;

	public DevocionalDTO() {
		super();
	}

	public DevocionalDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String referencia,
			@NotEmpty(message = "Preenchimento obrigatório") String idIgreja,
			@NotEmpty(message = "Preenchimento obrigatório") String descricao, String dataCriacao) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.idIgreja = idIgreja;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
