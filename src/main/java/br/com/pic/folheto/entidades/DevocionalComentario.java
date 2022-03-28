package br.com.pic.folheto.entidades;

import br.com.pic.folheto.dto.DevocionalComentarioNewDTO;
import br.com.pic.folheto.util.DataUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
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

	public DevocionalComentario(DevocionalComentarioNewDTO dto, Referencia referencia, Membro membro) {
		super();
		
		this.id = dto.getId();
		this.membro = membro;
		this.referencia = referencia;
		this.chamouAtencao = dto.getChamouAtencao();
		this.sobreDeus = dto.getSobreDeus();
		this.sobreHumanidade = dto.getSobreHumanidade();
		this.oQueAprendi = dto.getOQueAprendi();
		this.dataCriacao = dto.getDataCriacao() != null ? DataUtil.converterStringParaLocalDateFormatado(dto.getDataCriacao()):LocalDate.now();
		this.isDeletado = Boolean.FALSE;
	}

}
