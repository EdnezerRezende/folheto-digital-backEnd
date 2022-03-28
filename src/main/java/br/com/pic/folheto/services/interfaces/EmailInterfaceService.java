package br.com.pic.folheto.services.interfaces;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.entidades.Membro;

public interface EmailInterfaceService {

	void sendContato(final ContatoDTO contato);
	
	void sendEmail(final SimpleMailMessage msg);

	void sendContatoHtml(final ContatoDTO contato);
	
	void sendNewPasswordEmail(final Membro membro);
	
	void sendNewPassWord(final Membro membro, final String senha);
	
	void sendEmailHtml(final MimeMessage msg);
}
