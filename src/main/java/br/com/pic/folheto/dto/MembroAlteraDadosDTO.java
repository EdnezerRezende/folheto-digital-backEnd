package br.com.pic.folheto.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.pic.folheto.entidades.EnderecoMembro;

public class MembroAlteraDadosDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	private List<EnderecoMembro> enderecos;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private Set<String> telefones;

	public MembroAlteraDadosDTO(
			@NotEmpty(message = "Preenchimento obrigatório") @Email(message = "Email inválido") String email,
			List<EnderecoMembro> enderecos, @NotEmpty(message = "Preenchimento obrigatório") Set<String> telefones) {
		super();
		this.email = email;
		this.enderecos = enderecos;
		this.telefones = telefones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EnderecoMembro> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoMembro> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

}
