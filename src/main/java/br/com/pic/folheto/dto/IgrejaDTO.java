package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.EnderecoIgreja;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Membro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IgrejaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private EnderecoIgreja endereco;
	
	private List<Membro> membros;

	public IgrejaDTO(Igreja igreja) {
		super();
		this.id = igreja.getId();
		this.nome = igreja.getNome();
		this.membros = igreja.getMembros();
		this.endereco = igreja.getEndereco();
	}

}
