package br.com.igrejadecristo.folhetodigital.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PequenoGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String responsavelCasa;
	
	private String lider;
	
	@OneToOne
	@JoinColumn(name="pequenogrupo")
	private EnderecoPG endereco;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
    private Igreja igreja;

	public PequenoGrupo() {
		super();
	}
	
	public PequenoGrupo(Integer id, String responsavelCasa, String lider,  Igreja igreja ) {
		super();
		this.id = id;
		this.responsavelCasa = responsavelCasa;
		this.lider = lider;
		this.igreja = igreja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResponsavelCasa() {
		return responsavelCasa;
	}

	public void setResponsavelCasa(String responsavelCasa) {
		this.responsavelCasa = responsavelCasa;
	}

	public String getLider() {
		return lider;
	}

	public void setLider(String lider) {
		this.lider = lider;
	}

	public EnderecoPG getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoPG endereco) {
		this.endereco = endereco;
	}

	public Igreja getIgreja() {
		return igreja;
	}

	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
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
		PequenoGrupo other = (PequenoGrupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
