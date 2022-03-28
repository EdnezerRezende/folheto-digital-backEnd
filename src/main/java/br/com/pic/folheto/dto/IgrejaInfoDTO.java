package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.EnderecoIgreja;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IgrejaInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private String cnpj;
	
	private String email;
	
	private EnderecoIgreja endereco;

	@Builder.Default
	private Set<String> telefones = new HashSet<>();

}
