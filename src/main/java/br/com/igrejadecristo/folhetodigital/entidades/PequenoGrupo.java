package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@Cascade(value = CascadeType.DELETE)
	private EnderecoPG endereco;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;
	
	private String diaSemanaAtividade;
	
	@JsonFormat(pattern="HH:mm")
	private LocalTime horaAtividade;

	public PequenoGrupo() {
		super();
	}
	
	public PequenoGrupo(Integer id, String responsavelCasa, String lider,  Igreja igreja, 
			String diaSemanaAtividade, 
			LocalTime horaAtividade) {
		super();
		this.id = id;
		this.responsavelCasa = responsavelCasa;
		this.lider = lider;
		this.igreja = igreja;
		this.diaSemanaAtividade = diaSemanaAtividade;
		this.horaAtividade = horaAtividade;
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
	
	public String getDiaSemanaAtividade() {
		return diaSemanaAtividade;
	}

	public void setDiaSemanaAtividade(String diaSemanaAtividade) {
		this.diaSemanaAtividade = diaSemanaAtividade;
	}

	public LocalTime getHoraAtividade() {
		return horaAtividade;
	}

	public void setHoraAtividade(LocalTime horaAtividade) {
		this.horaAtividade = horaAtividade;
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
