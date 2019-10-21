package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;

public class FolhetoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String dataPublicacao;
	
	private String mensagem;
	
	public FolhetoDTO() {
		super();
	}

	public FolhetoDTO(Integer id, String dataPublicacao, String mensagem) {
		super();
		this.id = id;
		this.dataPublicacao = dataPublicacao;
		this.mensagem = mensagem;
	}
	
	public FolhetoDTO(Folheto folheto) {
		super();
		this.id = folheto.getId();
		this.dataPublicacao = folheto.getDataPublicado().toString();
		this.mensagem = folheto.getMensagem().getMensagem();
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


}
