package br.com.igrejadecristo.folhetodigital.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;

public abstract class AbstractEmailService implements EmailService {
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String htmlFromTemplateContato(ContatoDTO obj) {
		Context context = new Context();
		context.setVariable("membro", obj);
		return templateEngine.process("email/envioContato", context);
		
	}
	
	@Override
	public void sendContatoHtml(ContatoDTO contato) {
		MimeMessage mm;
		try {
			mm = prepareMimeMessageFromContato(contato);
			sendEmailHtml(mm);
		} catch (MessagingException e) {
			sendContato(contato);
		}
	}

	protected MimeMessage prepareMimeMessageFromContato(ContatoDTO contato) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(contato.getTo());
		mmh.setCc(contato.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject(contato.getAssunto());
		mmh.setSentDate(LocalDate.now().toDate());
		mmh.setText(htmlFromTemplateContato(contato), true);
		return mimeMessage;
	}
}
