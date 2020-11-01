package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AgendaEventoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String titulo;

	@NotEmpty(message="Preenchimento obrigatório")
	private String idIgreja;
	
	private String diaSemanaAtividade;
	
	private String horaAtividade;
	
	private String descricao;
	
	private Boolean isEvento;
	
	private String link;

	@JsonFormat(pattern="dd/MM/yyyy")
	private String dataInicio;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private String dataFim;
	
	
	public AgendaEventoNewDTO() {
		super();
	}


	public AgendaEventoNewDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String titulo,
			@NotEmpty(message = "Preenchimento obrigatório") String idIgreja, String diaSemanaAtividade,
			String horaAtividade, String descricao, Boolean isEvento, String dataInicio, String dataFim, String link) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.idIgreja = idIgreja;
		this.diaSemanaAtividade = diaSemanaAtividade;
		this.horaAtividade = horaAtividade;
		this.descricao = descricao;
		this.isEvento = isEvento;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.link = link;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIdIgreja() {
		return idIgreja;
	}

	public void setIdIgreja(String idIgreja) {
		this.idIgreja = idIgreja;
	}

	public String getDiaSemanaAtividade() {
		return diaSemanaAtividade;
	}

	public void setDiaSemanaAtividade(String diaSemanaAtividade) {
		this.diaSemanaAtividade = diaSemanaAtividade;
	}

	public String getHoraAtividade() {
		return horaAtividade;
	}

	public void setHoraAtividade(String horaAtividade) {
		this.horaAtividade = horaAtividade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getIsEvento() {
		return isEvento;
	}

	public void setIsEvento(Boolean isEvento) {
		this.isEvento = isEvento;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


}
