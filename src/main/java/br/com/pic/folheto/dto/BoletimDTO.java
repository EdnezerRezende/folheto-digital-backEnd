package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.Mensagem;
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
public class BoletimDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dataBoletim;
	
	private IgrejaInfoDTO igreja;
	
	private List<String> aniversariantes;
	
	private List<String> textosDevocionais;
	
	private List<String> pgsQuartaValores;
	
	private List<String> pgsQuintaValores;
	
	private MissaoDTO missao;
	
	private Mensagem mensagem;

}
