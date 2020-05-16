package br.com.igrejadecristo.folhetodigital.entidades;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Devocional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	@OneToOne(cascade=CascadeType.ALL)
	private Referencia referencia;
	
	@JsonIgnore
    @ManyToOne(cascade=CascadeType.DETACH)
    @JoinColumn(name="igreja_id")
    private Igreja igreja;
	
	private String descricao;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCriacao;
	
	private String textoReferencia;
	
	@Transient
	private Boolean isAtual = false;
	
	@Transient
	private Boolean isLido = false;

	public Devocional() {
		super();
	}

	public Devocional(Integer id, Referencia referencia, Igreja igreja, String descricao, LocalDate dataCriacao, String textoReferencia) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.igreja = igreja;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.textoReferencia = textoReferencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Referencia getReferencia() {
		return referencia;
	}

	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}

	public Igreja getIgreja() {
		return igreja;
	}

	public void setIgreja(Igreja igreja) {
		this.igreja = igreja;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Boolean getIsAtual() {
		return isAtual;
	}

	public void setIsAtual(Boolean isAtual) {
		this.isAtual = isAtual;
	}

	public String getTextoReferencia() {
		return textoReferencia;
	}

	public void setTextoReferencia(String textoReferencia) {
		this.textoReferencia = textoReferencia;
	}
	
	public Boolean getIsLido() {
		return isLido;
	}

	public void setIsLido(Boolean isLido) {
		this.isLido = isLido;
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
		Devocional other = (Devocional) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
