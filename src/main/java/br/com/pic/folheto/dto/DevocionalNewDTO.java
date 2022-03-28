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
public class DevocionalNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private ReferenciaDTO referencia;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String idIgreja;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;
	
	private String textoReferencia;


}
