package br.com.igrejadecristo.folhetodigital.dto;

import java.io.Serializable;
import java.util.Set;

import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;

public class MembroAlteraPerfilDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private Set<Perfil> perfis;
	
	public MembroAlteraPerfilDTO() {
		super();
	}

	public MembroAlteraPerfilDTO(Integer id, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.perfis = perfis;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}
	


}
