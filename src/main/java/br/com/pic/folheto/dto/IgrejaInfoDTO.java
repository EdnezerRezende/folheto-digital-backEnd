package br.com.pic.folheto.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.pic.folheto.entidades.EnderecoIgreja;
import br.com.pic.folheto.entidades.Igreja;

public class IgrejaInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private String cnpj;
	
	private String email;
	
	private EnderecoIgreja endereco;
	
	private Set<String> telefones = new HashSet<>();

	public IgrejaInfoDTO() {
		super();
	}

	
	public IgrejaInfoDTO(Integer id, String nome, String cnpj, String email, EnderecoIgreja endereco,
			Set<String> telefones) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.email = email;
		this.endereco = endereco;
		this.telefones = telefones;
	}


	public IgrejaInfoDTO(Igreja igreja) {
		super();
		this.id = igreja.getId();
		this.nome = igreja.getNome();
		this.cnpj = igreja.getCnpj();
		this.email = igreja.getEmail();
		this.telefones = igreja.getTelefones();
		this.endereco = igreja.getEndereco();
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

	public String getCnpj() {
		return cnpj;
	}


	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Set<String> getTelefones() {
		return telefones;
	}


	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}


	public EnderecoIgreja getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoIgreja endereco) {
		this.endereco = endereco;
	}
	
}
