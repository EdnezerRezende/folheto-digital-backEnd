package br.com.pic.folheto.dto;

import java.io.Serializable;

public class AniversarianteNewDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String dataNascimento;
	
	private Integer idIgreja;
	
	public AniversarianteNewDTO() {
		super();
	}

	public AniversarianteNewDTO(String nome, String email, String dataNascimento, Integer id, Integer idIgreja) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.idIgreja = idIgreja;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
}
