package br.com.pic.folheto.entidades;

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

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Builder
@NoArgsConstructor
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
	
	private String link;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInicio;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataFim;


}
