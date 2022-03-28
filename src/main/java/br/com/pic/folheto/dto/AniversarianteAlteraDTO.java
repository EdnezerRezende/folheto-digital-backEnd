package br.com.pic.folheto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AniversarianteAlteraDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	private String nome;
	
	private String dataNascimento;
	
	private Integer idIgreja;
	

}
