package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Mensagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String mensagem;
	
	private String autor;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDate dataCriado;
	
	private String titulo;

	public Mensagem() {
		super();
	}
	
	public Mensagem(Integer id, String mensagem, String autor, LocalDate dataCriado, String titulo ) {
		super();
		this.id = id;
		this.mensagem = mensagem;
		this.autor = autor;
		this.dataCriado = dataCriado;
		this.titulo = titulo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "MENSAGEM", columnDefinition = "VARCHAR(1500)") 
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

	public LocalDate getDataCriado() {
		return dataCriado;
	}

	public void setDataCriado(LocalDate dataCriado) {
		this.dataCriado = dataCriado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensagem other = (Mensagem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
