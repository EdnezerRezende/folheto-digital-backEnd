package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.enums.Perfil;
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
public class MembroInfoDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String cpf;
	
	private String telefone;
	
	private Integer igrejaId;
	
	private List<Perfil> perfis;

}
