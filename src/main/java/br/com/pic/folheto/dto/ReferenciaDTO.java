package br.com.pic.folheto.dto;

import java.io.Serializable;
import java.util.List;

import br.com.pic.folheto.entidades.Capitulo;
import br.com.pic.folheto.entidades.LivroDTO;
import br.com.pic.folheto.entidades.Versiculo;

public class ReferenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private LivroDTO book;
	
	private Capitulo chapter;
	
	private List<Versiculo> verses;
	
	public ReferenciaDTO() {
		super();
	}
	

	public ReferenciaDTO(Integer id, LivroDTO book, Capitulo chapter, List<Versiculo> verses) {
		super();
		this.id = id;
		this.book = book;
		this.chapter = chapter;
		this.verses = verses;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LivroDTO getBook() {
		return book;
	}

	public void setBook(LivroDTO book) {
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

	
}
