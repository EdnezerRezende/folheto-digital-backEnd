package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

public class MensagemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String mensagem;
	
	private String autor;
	
	public MensagemDTO() {
		super();
	}

	public MensagemDTO(Integer id, String mensagem, String autor) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
	}
	
	public MensagemDTO(Mensagem mensagem) {
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

}
