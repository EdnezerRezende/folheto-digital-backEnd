package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

public class MensagemNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String mensagem;
	
	private String autor;
	
	private Integer idFolheto;
	
	public MensagemNewDTO() {
		super();
	}

	public MensagemNewDTO(Integer id, String mensagem, String autor, Integer idFolheto) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
		this.idFolheto = idFolheto;
	}
	
	public MensagemNewDTO(Mensagem mensagem) {
		super();
		this.id = mensagem.getId();
		this.mensagem = mensagem.getMensagem();
		this.autor = mensagem.getAutor();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getIdFolheto() {
		return idFolheto;
	}

	public void setIdFolheto(Integer idFolheto) {
		this.idFolheto = idFolheto;
	}
}
