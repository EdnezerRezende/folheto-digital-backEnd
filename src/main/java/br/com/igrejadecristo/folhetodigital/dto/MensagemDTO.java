package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

public class MensagemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String mensagem;
	
	private String autor;
	
	private String titulo;
	
	public MensagemDTO() {
		super();
	}

	public MensagemDTO(Integer id, String mensagem, String autor, String titulo) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
		this.titulo = titulo;
	}
	
	public MensagemDTO(Mensagem mensagem) {
		super();
		this.id = mensagem.getId();
		this.mensagem = mensagem.getMensagem();
		this.autor = mensagem.getAutor();
		this.titulo = mensagem.getTitulo();
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
