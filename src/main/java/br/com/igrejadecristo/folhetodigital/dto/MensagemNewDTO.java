package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

public class MensagemNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String mensagem;
	
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String autor;
	
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String titulo;
	
	public MensagemNewDTO() {
		super();
	}

	public MensagemNewDTO(Integer id, String mensagem, String autor,  String titulo) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
		this.titulo = titulo;
	}
	
	public MensagemNewDTO(Mensagem mensagem) {
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
