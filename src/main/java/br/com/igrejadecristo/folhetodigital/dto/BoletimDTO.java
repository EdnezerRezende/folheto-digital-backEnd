package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

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
	
	public BoletimDTO() {
	}

	public BoletimDTO(String dataBoletim, IgrejaInfoDTO igreja, List<String> aniversariantes,
			List<String> textosDevocionais, List<String> pgsQuartaValores, List<String> pgsQuintaValores,
			MissaoDTO missao, Mensagem mensagem) {
		super();
		this.dataBoletim = dataBoletim;
		this.igreja = igreja;
		this.aniversariantes = aniversariantes;
		this.textosDevocionais = textosDevocionais;
		this.pgsQuartaValores = pgsQuartaValores;
		this.pgsQuintaValores = pgsQuintaValores;
		this.missao = missao;
		this.mensagem = mensagem;
	}

	public String getDataBoletim() {
		return dataBoletim;
	}

	public void setDataBoletim(String dataBoletim) {
		this.dataBoletim = dataBoletim;
	}

	public IgrejaInfoDTO getIgreja() {
		return igreja;
	}

	public void setIgreja(IgrejaInfoDTO igreja) {
		this.igreja = igreja;
	}

	public List<String> getAniversariantes() {
		return aniversariantes;
	}

	public void setAniversariantes(List<String> aniversariantes) {
		this.aniversariantes = aniversariantes;
	}

	public List<String> getTextosDevocionais() {
		return textosDevocionais;
	}

	public void setTextosDevocionais(List<String> textosDevocionais) {
		this.textosDevocionais = textosDevocionais;
	}

	public List<String> getPgsQuartaValores() {
		return pgsQuartaValores;
	}

	public void setPgsQuartaValores(List<String> pgsQuartaValores) {
		this.pgsQuartaValores = pgsQuartaValores;
	}

	public List<String> getPgsQuintaValores() {
		return pgsQuintaValores;
	}

	public void setPgsQuintaValores(List<String> pgsQuintaValores) {
		this.pgsQuintaValores = pgsQuintaValores;
	}

	public MissaoDTO getMissao() {
		return missao;
	}

	public void setMissao(MissaoDTO missao) {
		this.missao = missao;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
}
