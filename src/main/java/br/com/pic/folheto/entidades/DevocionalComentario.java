package br.com.pic.folheto.entidades;

import java.time.LocalDate;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.pic.folheto.dto.DevocionalComentarioNewDTO;
import br.com.pic.folheto.util.DataUtil;

@Entity
public class DevocionalComentario {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	@ManyToOne
    @JoinColumn(name="referencia_id")
	private Referencia referencia;
	
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="CHAMOU_ATENCAO")
	private String chamouAtencao;
	
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="SOBRE_DEUS")
	private String sobreDeus;
	
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="SOBRE_HUMANIDADE")
	private String sobreHumanidade;
	
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="O_QUE_APRENDI")
	private String oQueAprendi;

	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCriacao;
	
	private Boolean isDeletado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="membro_id")
	private Membro membro;
	
	public DevocionalComentario() {
		super();
	}
	
	public DevocionalComentario(Integer id, Referencia referencia, String chamouAtencao, String sobreDeus,
			String sobreHumanidade, String oQueAprendi, LocalDate dataCriacao) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.chamouAtencao = chamouAtencao;
		this.sobreDeus = sobreDeus;
		this.sobreHumanidade = sobreHumanidade;
		this.oQueAprendi = oQueAprendi;
		this.dataCriacao = dataCriacao;
	}

	public DevocionalComentario(DevocionalComentarioNewDTO dto, Referencia referencia, Membro membro) {
		super();
		
		this.id = dto.getId();
		this.membro = membro;
		this.referencia = referencia;
		this.chamouAtencao = dto.getChamouAtencao();
		this.sobreDeus = dto.getSobreDeus();
		this.sobreHumanidade = dto.getSobreHumanidade();
		this.oQueAprendi = dto.getoQueAprendi();
		this.dataCriacao = dto.getDataCriacao() != null ? DataUtil.converterStringParaLocalDateFormatado(dto.getDataCriacao()):LocalDate.now();
		this.isDeletado = Boolean.FALSE;
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

	public String getChamouAtencao() {
		return chamouAtencao;
	}

	public void setChamouAtencao(String chamouAtencao) {
		this.chamouAtencao = chamouAtencao;
	}

	public String getSobreDeus() {
		return sobreDeus;
	}

	public void setSobreDeus(String sobreDeus) {
		this.sobreDeus = sobreDeus;
	}

	public String getSobreHumanidade() {
		return sobreHumanidade;
	}

	public void setSobreHumanidade(String sobreHumanidade) {
		this.sobreHumanidade = sobreHumanidade;
	}

	public String getoQueAprendi() {
		return oQueAprendi;
	}

	public void setoQueAprendi(String oQueAprendi) {
		this.oQueAprendi = oQueAprendi;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	

	public Boolean getIsDeletado() {
		return isDeletado;
	}

	public void setIsDeletado(Boolean isDeletado) {
		this.isDeletado = isDeletado;
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
		DevocionalComentario other = (DevocionalComentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
