package br.com.pic.folheto.entidades;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Aniversariante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String nome;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@Column(unique=true)
	private String email;
	
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
    private Igreja igreja;

	public Aniversariante(Membro membro) {
		super();
		this.id = membro.getId();
		this.nome = membro.getNome();
		this.dataNascimento = membro.getDataNascimento();
		this.email = membro.getEmail();
		this.igreja = membro.getIgreja();
	}

}
