package br.com.pic.folheto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
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

}
