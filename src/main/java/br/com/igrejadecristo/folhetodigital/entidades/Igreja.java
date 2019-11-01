package br.com.igrejadecristo.folhetodigital.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<Membro> membros = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<PequenoGrupo> pgs = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
    private List<AgendaEvento> agendas = new ArrayList<>();
	
	@OneToOne(mappedBy = "igreja", cascade=CascadeType.ALL)
    private EnderecoIgreja endereco;

//	@ElementCollection
//    @CollectionTable(name = "TELEFONE")
//    private Set<String> telefones = new HashSet<>();

	
	public Igreja() {
		super();
	}
	
	public Igreja(Integer id, String nome, 
			String cnpj
			) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
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
	
	
	
	public EnderecoIgreja getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoIgreja endereco) {
		this.endereco = endereco;
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
