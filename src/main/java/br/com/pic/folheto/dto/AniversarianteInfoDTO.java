package br.com.pic.folheto.dto;

import java.io.Serializable;

import br.com.pic.folheto.entidades.Aniversariante;

public class AniversarianteInfoDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String dataNascimento;
	
	public AniversarianteInfoDTO() {
		super();
	}

	public AniversarianteInfoDTO(String nome, String email, String dataNascimento, Integer id) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}
	
	public AniversarianteInfoDTO(Aniversariante aniversariante) {
		super();
		this.id = aniversariante.getId();
		this.nome = aniversariante.getNome();
		this.email = aniversariante.getEmail();
		this.dataNascimento = aniversariante.getDataNascimento().toString();
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

}
