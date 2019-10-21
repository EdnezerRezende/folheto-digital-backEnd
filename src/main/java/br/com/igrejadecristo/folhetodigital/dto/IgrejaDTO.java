package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.EnderecoIgreja;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;

public class IgrejaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private EnderecoIgreja endereco;
	
	private List<Membro> membros;

	public IgrejaDTO() {
	}

	public IgrejaDTO(Integer id, String nome, List<Membro> membros, EnderecoIgreja endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.membros = membros;
		this.endereco = endereco;
	}
	
	public IgrejaDTO(Igreja igreja) {
		super();
		this.id = igreja.getId();
		this.nome = igreja.getNome();
		this.membros = igreja.getMembros();
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

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public EnderecoIgreja getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoIgreja endereco) {
		this.endereco = endereco;
	}
	
}
