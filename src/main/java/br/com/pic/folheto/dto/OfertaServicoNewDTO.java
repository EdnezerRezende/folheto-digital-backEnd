package br.com.pic.folheto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class OfertaServicoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String idIgreja;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String descricao;

	private Set<String> telefones = new HashSet<>();

	private String emailServico;

	private String instagram;

	private String facebook;

	private String endereco;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataCriacao;

}
