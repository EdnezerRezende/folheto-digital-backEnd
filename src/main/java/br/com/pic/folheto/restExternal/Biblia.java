package br.com.pic.folheto.restExternal;

import br.com.pic.folheto.entidades.Capitulo;
import br.com.pic.folheto.entidades.Livro;
import br.com.pic.folheto.entidades.Versiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Biblia {
		private Livro book;
		private Capitulo chapter;
		private List<Versiculo> verses;
		
}
