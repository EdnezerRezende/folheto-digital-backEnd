package br.com.pic.folheto.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import br.com.pic.folheto.entidades.Missao;
import org.hibernate.validator.constraints.Length;

public class MissaoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String mensagem;
	
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String autor;
	
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String titulo;
	
	private Integer igrejaId;
	
	public MissaoNewDTO() {
		super();
	}

	public MissaoNewDTO(Integer id, String mensagem, String autor,  String titulo) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
		this.titulo = titulo;
	}
	
	public MissaoNewDTO(Missao missao) {
		super();
		this.id = missao.getId();
		this.mensagem = missao.getMensagem();
		this.autor = missao.getAutor();
		this.titulo = missao.getTitulo();
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
	
	public Integer getIgrejaId() {
		return igrejaId;
	}

	public void setIgrejaId(Integer igrejaId) {
		this.igrejaId = igrejaId;
	}
}
