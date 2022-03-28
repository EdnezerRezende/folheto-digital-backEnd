package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.EnderecoPG;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String responsavelCasa;
	
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String lider;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String idIgreja;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String diaSemanaAtividade;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String horaAtividade;
	
	private EnderecoPG endereco;

}
