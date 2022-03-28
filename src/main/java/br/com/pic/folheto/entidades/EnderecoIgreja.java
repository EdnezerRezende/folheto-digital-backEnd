package br.com.pic.folheto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
public class EnderecoIgreja implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="igreja_id")
    private Igreja igreja;

    @ManyToOne
    @JoinColumn(name="cidade_id")
    private Cidade cidade;


}
