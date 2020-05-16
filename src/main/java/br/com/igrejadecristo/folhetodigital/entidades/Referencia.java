package br.com.igrejadecristo.folhetodigital.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.igrejadecristo.folhetodigital.dto.ReferenciaDTO;


@Entity
public class Referencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade=CascadeType.ALL)
	private Livro book;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Capitulo chapter;
	
	@OneToMany(mappedBy = "referencia", cascade=CascadeType.ALL)
	private List<Versiculo> verses;
	
	@JsonIgnore
	@OneToMany(mappedBy = "referencia", cascade=CascadeType.ALL)
	private List<DevocionalComentario> comentarios;

	public Referencia() {
		super();
	}

	public Referencia(Integer id, Livro book, Capitulo chapter, List<Versiculo> verses) {
		super();
		this.id = id;
		this.book = book;
		this.chapter = chapter;
		this.verses = verses;
	}

	public Referencia(ReferenciaDTO dto) {
		super();
		this.id = dto.getId();
		this.book = new Livro();
		this.book.setAbbrev(dto.getBook().getAbbrev());
		this.book.setAuthor(dto.getBook().getAuthor());
		this.book.setGroupe(dto.getBook().getGroupe());
		this.book.setNameU(dto.getBook().getNameU());
		this.book.setVersionN(dto.getBook().getVersionN());
		this.chapter = dto.getChapter();
		this.verses = dto.getVerses();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Livro getBook() {
		return book;
	}

	public void setBook(Livro book) {
		this.book = book;
	}

	public Capitulo getChapter() {
		return chapter;
	}

	public void setChapter(Capitulo chapter) {
		this.chapter = chapter;
	}

	public List<Versiculo> getVerses() {
		return verses;
	}

	public void setVerses(List<Versiculo> verses) {
		this.verses = verses;
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
		Referencia other = (Referencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
