package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.List;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;

public class FolhetoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String dataPublicacao;
	
	private String mensagem;
	
	private List<PequenoGrupo> pgs;
	
	public FolhetoDTO() {
		super();
	}

	public FolhetoDTO(Integer id, String dataPublicacao, String mensagem) {
		super();
		this.id = id;
		this.dataPublicacao = dataPublicacao;
		this.mensagem = mensagem;
	}
	
	public FolhetoDTO(Folheto folheto) {
		super();
		this.id = folheto.getId();
		this.dataPublicacao = folheto.getDataPublicado().toString();
		this.mensagem = folheto.getMensagem().getMensagem();
		this.pgs.addAll(folheto.getPg());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<PequenoGrupo> getPgs() {
		return pgs;
	}

	public void setPgs(List<PequenoGrupo> pgs) {
		this.pgs = pgs;
	}

}
