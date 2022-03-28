package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.enums.Perfil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
public class MembroAlteraPerfilDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Set<Perfil> perfis;

}
