package br.com.pic.folheto.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class MissaoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String mensagem;
	
	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String autor;
	
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String titulo;
	
	private Integer igrejaId;

}
