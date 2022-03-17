package br.com.pic.folheto.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AniversarianteAlteraDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	private String nome;
	
	private String dataNascimento;
	
	private Integer idIgreja;
	
	public AniversarianteAlteraDTO() {
		super();
	}

	public AniversarianteAlteraDTO(String nome, String email, String dataNascimento, Integer idIgreja) {
		super();
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.idIgreja = idIgreja;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdIgreja() {
		return idIgreja;
	}

	public void setIdIgreja(Integer idIgreja) {
		this.idIgreja = idIgreja;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
