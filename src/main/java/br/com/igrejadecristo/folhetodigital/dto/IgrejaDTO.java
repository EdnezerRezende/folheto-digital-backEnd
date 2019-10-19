package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;

public class IgrejaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;

	private List<Folheto> folhetos = new ArrayList<Folheto>();
	
	public IgrejaDTO() {
	}

	public IgrejaDTO(Integer id, String nome, List<Folheto> folhetos) {
		super();
		this.id = id;
		this.nome = nome;
		this.folhetos = folhetos;
	}
	
	public IgrejaDTO(Igreja igreja) {
		super();
		this.id = igreja.getId();
		this.nome = igreja.getNome();
		this.folhetos = igreja.getFolhetos();
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

	public List<Folheto> getFolhetos() {
		return folhetos;
	}

	public void setFolhetos(List<Folheto> folhetos) {
		this.folhetos = folhetos;
	}
	
	
}
