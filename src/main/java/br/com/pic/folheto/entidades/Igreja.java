package br.com.pic.folheto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Igreja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Builder.Default
	private Integer id;

	@Builder.Default
	private String nome;

	@Builder.Default
	private String cnpj;

	@Builder.Default
	private String email;
	
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<Membro> membros = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<PequenoGrupo> pgs = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<AgendaEvento> agendas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<Devocional> Devocionais = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<OfertaServico> servicos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<Mensagem> mensagens = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<Missao> missoes = new ArrayList<>();
	
	@OneToOne(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private EnderecoIgreja endereco;
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Builder.Default
    private List<Aniversariante> aniversariantes = new ArrayList<>();

	@ElementCollection
    @CollectionTable(name = "TELEFONEIGREJA")
	@Builder.Default
    private Set<String> telefones = new HashSet<>();

}
