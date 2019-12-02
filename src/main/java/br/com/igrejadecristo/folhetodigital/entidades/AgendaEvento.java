package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AgendaEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String titulo;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;
	
	private String diaSemanaAtividade;
	
	@JsonFormat(pattern="HH:mm")
	private LocalTime horaAtividade;
	
	private Boolean isEvento;
	
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="TEXTO")
	private String descricao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataInicio;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataFim;

	public AgendaEvento() {
		super();
	}
	
	public AgendaEvento(Integer id, String titulo, Igreja igreja, String diaSemanaAtividade, LocalTime horaAtividade,
			Boolean isEvento, String descricao, LocalDate dataInicio, LocalDate dataFim) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.igreja = igreja;
		this.diaSemanaAtividade = diaSemanaAtividade;
		this.horaAtividade = horaAtividade;
		this.isEvento = isEvento;
		this.descricao = descricao;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Boolean getIsEvento() {
		return isEvento;
	}

	public void setIsEvento(Boolean isEvento) {
		this.isEvento = isEvento;
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
		AgendaEvento other = (AgendaEvento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
