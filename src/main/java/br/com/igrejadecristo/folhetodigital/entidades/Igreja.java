package br.com.igrejadecristo.folhetodigital.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Igreja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private String cnpj;
	
	private String email;
	
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Membro> membros = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<PequenoGrupo> pgs = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<AgendaEvento> agendas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Devocional> Devocionais = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<OfertaServico> servicos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Mensagem> mensagens = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Missao> missoes = new ArrayList<>();
	
	@OneToOne(mappedBy = "igreja", cascade=CascadeType.ALL)
    private EnderecoIgreja endereco;
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Aniversariante> aniversariantes = new ArrayList<>();

	@ElementCollection
    @CollectionTable(name = "TELEFONEIGREJA")
    private Set<String> telefones = new HashSet<>();
	
	public Igreja() {
		super();
	}
	
	public Igreja(Integer id, String nome, 
			String cnpj, String email
			) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public EnderecoIgreja getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoIgreja endereco) {
		this.endereco = endereco;
	}
	
	public List<PequenoGrupo> getPgs() {
		return pgs;
	}

	public void setPgs(List<PequenoGrupo> pgs) {
		this.pgs = pgs;
	}

	public List<AgendaEvento> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<AgendaEvento> agendas) {
		this.agendas = agendas;
	}

	public List<Devocional> getDevocionais() {
		return Devocionais;
	}

	public void setDevocionais(List<Devocional> devocionais) {
		Devocionais = devocionais;
	}

	public List<OfertaServico> getServicos() {
		return servicos;
	}

	public void setServicos(List<OfertaServico> servicos) {
		this.servicos = servicos;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public List<Missao> getMissoes() {
		return missoes;
	}

	public void setMissoes(List<Missao> missoes) {
		this.missoes = missoes;
	}

	public List<Aniversariante> getAniversariantes() {
		return aniversariantes;
	}

	public void setAniversariantes(List<Aniversariante> aniversariantes) {
		this.aniversariantes = aniversariantes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Igreja other = (Igreja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
