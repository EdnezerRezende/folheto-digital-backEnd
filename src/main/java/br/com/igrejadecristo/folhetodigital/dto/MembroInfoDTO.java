package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;

public class MembroInfoDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String telefone;
	
	private Integer igrejaId;
	
	private List<Perfil> perfis;

	public MembroInfoDTO() {
		super();
	}

	public MembroInfoDTO(String nome, String email, String cpf, String telefone1, Integer igrejaId,
			List<Perfil> perfis, Integer id) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.telefone = telefone1;
		this.igrejaId = igrejaId;
		this.perfis = perfis;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone1() {
		return telefone;
	}

	public void setTelefone1(String telefone1) {
		this.telefone = telefone1;
	}

	public Integer getIgrejaId() {
		return igrejaId;
	}

	public void setIgrejaId(Integer igrejaId) {
		this.igrejaId = igrejaId;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
