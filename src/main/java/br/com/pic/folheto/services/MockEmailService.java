package br.com.pic.folheto.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info(msg.toString());

	}

	@Override
	public void sendEmailHtml(MimeMessage msg) {
		LOG.info(msg.toString());

	}

	
}
