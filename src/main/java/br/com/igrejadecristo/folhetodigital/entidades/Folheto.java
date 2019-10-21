package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Folheto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDate dataPublicado;
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
    private Igreja igreja;
    
    @ManyToOne
    @JoinColumn(name="mensagem_id")
    private Mensagem mensagem;

    @ManyToMany
    @JoinColumn(name="pequenogrupo_id")
    private List<PequenoGrupo> pg = new ArrayList<>();
    
	public Folheto() {
		super();
	}
	
	public Folheto(Integer id, LocalDate dataPublicado, Igreja igreja) {
		super();
		this.id = id;
		this.dataPublicado = dataPublicado;
		this.igreja = igreja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataPublicado() {
		return dataPublicado;
	}

	public void setDataPublicado(LocalDate dataPublicado) {
		this.dataPublicado = dataPublicado;
	}
	
	public Igreja getIgreja() {
		return igreja;
	}

	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public List<PequenoGrupo> getPg() {
		return pg;
	}

	public void setPg(List<PequenoGrupo> pg) {
		this.pg = pg;
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
		Folheto other = (Folheto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
