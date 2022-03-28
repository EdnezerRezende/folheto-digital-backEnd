package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.EnderecoMembro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembroAlteraDadosDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	private List<EnderecoMembro> enderecos;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private Set<String> telefones;
}
