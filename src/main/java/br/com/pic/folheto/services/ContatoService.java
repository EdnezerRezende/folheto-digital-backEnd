package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.ContatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {

	@Autowired
	private EmailService emailService;
	
	public void enviarContato(ContatoDTO contato) {
		emailService.sendContatoHtml(contato);
	}
}
