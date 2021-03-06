package br.com.igrejadecristo.folhetodigital.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import br.com.igrejadecristo.folhetodigital.entidades.Membro;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email Enviado");
		
	}

	@Override
	public void sendEmailHtml(MimeMessage msg) {
		LOG.info("Simulando envio de email Html...");
		LOG.info(msg.toString());
		LOG.info("Email Enviado Html");
		
	}

	
}
