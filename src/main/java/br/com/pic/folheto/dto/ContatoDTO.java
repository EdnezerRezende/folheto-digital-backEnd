package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.MenssagemEmail;
import br.com.pic.folheto.services.validation.MembroUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@MembroUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO  implements Serializable, MenssagemEmail {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="E-mail inválido")
	private String email;
	
	private String telefone;

	@Length(min=5, max=50, message="O tamanho deve ser entre 5 e 50 caracteres")
	private String assunto;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=11, max=200, message="O tamanho deve ser entre 11 e 200 caracteres")
	private String mensagem;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String to;


}
