package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.igrejadecristo.folhetodigital.entidades.EnderecoPG;

public class PgNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String responsavelCasa;
	
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String lider;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String idIgreja;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String diaSemanaAtividade;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String horaAtividade;
	
	private EnderecoPG endereco;
	
	public PgNewDTO() {
		super();
	}

	public PgNewDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String responsavelCasa,
			@Length(min = 5, max = 50, message = "O tamanho deve ser entre 5 e 50 caracteres") String lider,
			@NotEmpty(message = "Preenchimento obrigatório") String idIgreja,
			@NotEmpty(message = "Preenchimento obrigatório") String diaSemanaAtividade,
			@NotEmpty(message = "Preenchimento obrigatório") String horaAtividade,
			EnderecoPG endereco) {
		super();
		this.id = id;
		this.responsavelCasa = responsavelCasa;
		this.lider = lider;
		this.idIgreja = idIgreja;
		this.diaSemanaAtividade = diaSemanaAtividade;
		this.horaAtividade = horaAtividade;
		this.endereco = endereco;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getResponsavelCasa() {
		return responsavelCasa;
	}


	public void setResponsavelCasa(String responsavelCasa) {
		this.responsavelCasa = responsavelCasa;
	}


	public String getLider() {
		return lider;
	}


	public void setLider(String lider) {
		this.lider = lider;
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


	public EnderecoPG getEndereco() {
		return endereco;
	}


	public void setEndereco(EnderecoPG endereco) {
		this.endereco = endereco;
	}

}
