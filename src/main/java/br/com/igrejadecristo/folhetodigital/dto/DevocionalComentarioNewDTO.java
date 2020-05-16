package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DevocionalComentarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer referencia;

	private  String chamouAtencao;

	private  String sobreDeus;
	
	private  String sobreHumanidade;
	
	private  String oQueAprendi;
	
	private Integer idMembro;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;

	public DevocionalComentarioNewDTO() {
		super();
	}

	public DevocionalComentarioNewDTO(Integer id,
			@NotEmpty(message = "Preenchimento obrigatório") Integer referencia, String chamouAtencao,
			String sobreDeus, String sobreHumanidade, String oQueAprendi, String dataCriacao, Integer idMembro) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.chamouAtencao = chamouAtencao;
		this.sobreDeus = sobreDeus;
		this.sobreHumanidade = sobreHumanidade;
		this.oQueAprendi = oQueAprendi;
		this.dataCriacao = dataCriacao;
		this.idMembro = idMembro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChamouAtencao() {
		return chamouAtencao;
	}

	public void setChamouAtencao(String chamouAtencao) {
		this.chamouAtencao = chamouAtencao;
	}

	public String getSobreDeus() {
		return sobreDeus;
	}

	public void setSobreDeus(String sobreDeus) {
		this.sobreDeus = sobreDeus;
	}

	public String getSobreHumanidade() {
		return sobreHumanidade;
	}

	public void setSobreHumanidade(String sobreHumanidade) {
		this.sobreHumanidade = sobreHumanidade;
	}

	public String getoQueAprendi() {
		return oQueAprendi;
	}

	public void setoQueAprendi(String oQueAprendi) {
		this.oQueAprendi = oQueAprendi;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Integer getReferencia() {
		return referencia;
	}

	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}

	public Integer getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(Integer idMembro) {
		this.idMembro = idMembro;
	}
	
}
