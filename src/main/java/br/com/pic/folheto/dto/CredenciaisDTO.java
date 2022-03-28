package br.com.pic.folheto.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;

}
