package br.com.pic.folheto.entidades;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
@Data
@Builder
@NoArgsConstructor
public class LivroDTO {
	
	@ManyToOne
	@JoinColumn(name = "abbrev_id")
	private Abreviacao abbrev;
	
	private String nameU;
	private String author;
	private String groupe;
	private String versionN;

}
