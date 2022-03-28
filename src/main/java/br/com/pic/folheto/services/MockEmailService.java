package br.com.pic.folheto.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(final SimpleMailMessage msg) {
		LOG.info(msg.toString());

	}

	@Override
	public void sendEmailHtml(final MimeMessage msg) {
		LOG.info("Send Email New Password: ");
		LOG.info(msg.toString());

	}

	
}
