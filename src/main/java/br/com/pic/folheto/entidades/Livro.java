package br.com.pic.folheto.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Livro  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Abreviacao abbrev;
	
	private String nameU;
	private String author;
	private String groupe;
	private String versionN;
	
	public Livro() {
		super();
	}
	
	public Livro(Abreviacao abbrev, String nameU, String author, String groupe, String versionN) {
		super();
		this.abbrev = abbrev;
		this.nameU = nameU;
		this.author = author;
		this.groupe = groupe;
		this.versionN = versionN;
	}
	public Abreviacao getAbbrev() {
		return abbrev;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
