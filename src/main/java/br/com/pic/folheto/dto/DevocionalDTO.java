package br.com.pic.folheto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class DevocionalDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String referencia;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String idIgreja;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;

	public DevocionalDTO(Integer id, @NotEmpty(message = "Preenchimento obrigatório") String referencia,
			@NotEmpty(message = "Preenchimento obrigatório") String idIgreja,
			@NotEmpty(message = "Preenchimento obrigatório") String descricao, String dataCriacao) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.idIgreja = idIgreja;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
	}


}
