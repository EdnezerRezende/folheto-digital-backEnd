package br.com.igrejadecristo.folhetodigital.entidades;

import javax.persistence.Embeddable;

@Embeddable
public class LivroDTO {
	
	private Abreviacao abbrev;
	
	private String nameU;
	private String author;
	private String groupe;
	private String versionN;
	public Abreviacao getAbbrev() {
		return abbrev;
	}
	
	public LivroDTO(Abreviacao abbrev, String nameU, String author, String groupe, String versionN) {
		super();
		this.abbrev = abbrev;
		this.nameU = nameU;
		this.author = author;
		this.groupe = groupe;
		this.versionN = versionN;
	}


	public void setAbbrev(Abreviacao abbrev) {
		this.abbrev = abbrev;
	}
	public String getNameU() {
		return nameU;
	}
	public void setNameU(String nameU) {
		this.nameU = nameU;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public String getVersionN() {
		return versionN;
	}
	public void setVersionN(String versionN) {
		this.versionN = versionN;
	}
	
	
}
