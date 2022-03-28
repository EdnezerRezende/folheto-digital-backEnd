package br.com.pic.folheto.entidades;

import br.com.pic.folheto.entidades.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
public class Membro implements Serializable, MenssagemEmail{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@Column(unique=true)
	private String email;
	
	private String cpf;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@JsonIgnore
	private String senha;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	@OneToMany(mappedBy = "membro", cascade=CascadeType.PERSIST)
    private List<EnderecoMembro> enderecos = new ArrayList<>();
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
    private Igreja igreja;

	@ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();
	
	@OneToMany(mappedBy = "membro", cascade=CascadeType.ALL)
    private List<DevocionalComentario> comentarios = new ArrayList<>();

	public Membro() {
		addPerfil(Perfil.MEMBRO);
	}


	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}

	public void setPerfis(Set<Perfil> perfis) {
		Set<Integer> listaPerfil = new HashSet<>();
		perfis.forEach(perfil -> {
			listaPerfil.add(perfil.getCod());
		});
		this.perfis = listaPerfil;
	}

}
