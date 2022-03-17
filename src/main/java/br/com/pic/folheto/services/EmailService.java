package br.com.pic.folheto.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.entidades.Membro;

public interface EmailService {

	void sendContato(ContatoDTO contato);
	
	void sendEmail(SimpleMailMessage msg);

	void sendContatoHtml(ContatoDTO contato);
	
	void sendNewPasswordEmail(Membro membro, String newPassWord);
	
	void sendNewPassWord(Membro membro, String senha);
	
	void sendEmailHtml(MimeMessage msg);
}
