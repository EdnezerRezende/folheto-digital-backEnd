package br.com.pic.folheto.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfertaServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String nome;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;
	
	private String descricao;
	
	@ElementCollection
    @CollectionTable(name = "TELEFONEOFERTA")
	@Builder.Default
    private Set<String> telefones = new HashSet<>();
	
	private String emailServico;
	
	private String instagram;
	
	private String facebook;
	
	private String endereco;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataCriacao;

	
}
