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
@NoArgsConstructor
public class Igreja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private String cnpj;

	private String email;
	
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("membros")
    private List<Membro> membros = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("pgs")
    private List<PequenoGrupo> pgs = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("agendas")
    private List<AgendaEvento> agendas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("devocionais")
    private List<Devocional> devocionals = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("servicos")
    private List<OfertaServico> servicos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("mensagens")
    private List<Mensagem> mensagens = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("missoes")
    private List<Missao> missoes = new ArrayList<>();
	
	@OneToOne(mappedBy = "igreja", cascade=CascadeType.ALL)
    private EnderecoIgreja endereco;
	
	@JsonIgnore
	@OneToMany(mappedBy = "igreja", cascade=CascadeType.ALL)
	@Singular("aniversariantes")
    private List<Aniversariante> aniversariantes = new ArrayList<>();

	@ElementCollection
    @CollectionTable(name = "TELEFONEIGREJA")
	@Singular("telefones")
    private Set<String> telefones = new HashSet<>();

}
