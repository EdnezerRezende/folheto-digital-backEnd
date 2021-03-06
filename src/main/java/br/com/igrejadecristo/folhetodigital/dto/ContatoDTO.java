package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.igrejadecristo.folhetodigital.services.validation.MembroUpdate;

@MembroUpdate
public class ContatoDTO  implements Serializable {
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

	public ContatoDTO(
			@NotEmpty(message = "Preenchimento obrigatório") @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres") String nome,
			@NotEmpty(message = "Preenchimento obrigatório") @Email(message = "E-mail inválido") String email,
			String telefone,
			@Length(min = 5, max = 50, message = "O tamanho deve ser entre 5 e 50 caracteres") String assunto,
			@NotEmpty(message = "Preenchimento obrigatório") @Length(min = 11, max = 200, message = "O tamanho deve ser entre 11 e 200 caracteres") String mensagem,
			@NotEmpty(message = "Preenchimento obrigatório") String to) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.to = to;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getTo() {
		return to;
	}
	

}
