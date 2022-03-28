package br.com.pic.folheto.entidades;

import br.com.pic.folheto.dto.ReferenciaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
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
	@OneToMany(mappedBy = "referencia")
	private List<DevocionalComentario> comentarios;

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

}
