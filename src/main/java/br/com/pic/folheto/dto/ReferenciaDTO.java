package br.com.pic.folheto.dto;

import br.com.pic.folheto.entidades.Capitulo;
import br.com.pic.folheto.entidades.LivroDTO;
import br.com.pic.folheto.entidades.Versiculo;
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
public class ReferenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private LivroDTO book;
	
	private Capitulo chapter;
	
	private List<Versiculo> verses;

	
}
