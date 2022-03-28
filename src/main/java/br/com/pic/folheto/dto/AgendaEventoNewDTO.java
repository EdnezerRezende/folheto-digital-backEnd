package br.com.pic.folheto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaEventoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String titulo;

	@NotEmpty(message="Preenchimento obrigatório")
	private String idIgreja;
	
	private String diaSemanaAtividade;
	
	private String horaAtividade;
	
	private String descricao;
	
	private Boolean isEvento;
	
	private String link;

	@JsonFormat(pattern="dd/MM/yyyy")
	private String dataInicio;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private String dataFim;

}
