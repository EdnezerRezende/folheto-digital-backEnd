package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;

public class FolhetoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String dataPublicacao;
	
	public FolhetoDTO() {
		super();
	}

	public FolhetoDTO(Integer id, String dataPublicacao) {
		super();
		this.id = id;
		this.dataPublicacao = dataPublicacao;
	}
	
	public FolhetoDTO(Folheto folheto) {
		super();
		this.id = folheto.getId();
		this.dataPublicacao = folheto.getDataPublicado().toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}


}
