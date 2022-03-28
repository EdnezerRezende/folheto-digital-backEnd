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
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Missao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	@Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="TEXTO")
	private String mensagem;
	
	private String autor;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime dataCriado;
	
	private String titulo;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="igreja_id")
	@Cascade(value = CascadeType.DETACH)
    private Igreja igreja;

	
}
