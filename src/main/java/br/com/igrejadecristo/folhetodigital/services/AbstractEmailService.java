package br.com.igrejadecristo.folhetodigital.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;

public abstract class AbstractEmailService implements EmailService {
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendContato(ContatoDTO contato) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromContato(contato);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromContato(ContatoDTO contato) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(contato.getTo());
		sm.setCc(contato.getEmail());
		sm.setFrom(sender);
		sm.setSubject(contato.getAssunto());
		sm.setSentDate(LocalDate.now().toDate());
		sm.setText(contato.getMensagem());
		return sm;
	}
}
