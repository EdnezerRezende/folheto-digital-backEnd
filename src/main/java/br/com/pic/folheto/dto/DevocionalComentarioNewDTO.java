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
public class DevocionalComentarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer referencia;

	private  String chamouAtencao;

	private  String sobreDeus;
	
	private  String sobreHumanidade;
	
	private  String oQueAprendi;
	
	private Integer idMembro;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;

	public DevocionalComentarioNewDTO(Integer id,
			@NotEmpty(message = "Preenchimento obrigatório") Integer referencia, String chamouAtencao,
			String sobreDeus, String sobreHumanidade, String oQueAprendi, String dataCriacao, Integer idMembro) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.chamouAtencao = chamouAtencao;
		this.sobreDeus = sobreDeus;
		this.sobreHumanidade = sobreHumanidade;
		this.oQueAprendi = oQueAprendi;
		this.dataCriacao = dataCriacao;
		this.idMembro = idMembro;
	}

	
}
