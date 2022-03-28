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
import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PequenoGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	private String responsavelCasa;
	
	private String lider;
	
	@OneToOne
	@JoinColumn(name="pequenogrupo")
	@Cascade(value = CascadeType.DELETE)
	private EnderecoPG endereco;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;
	
	private String diaSemanaAtividade;
	
	@JsonFormat(pattern="HH:mm")
	private LocalTime horaAtividade;

}
