package br.com.pic.folheto.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.services.interfaces.EmailInterfaceService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.pic.folheto.entidades.Membro;

public abstract class AbstractEmailService implements EmailInterfaceService {
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Override
	public void sendContato(final ContatoDTO contato) {
		final SimpleMailMessage sm = prepareSimpleMailMessageFromContato(contato);
		sendEmail(sm);
	}
	
	@Override
	public void sendNewPassWord(final Membro membro,final String senha) {
		final SimpleMailMessage sm = prepareSimpleMailMessageFromMembro(membro, senha);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromContato(final ContatoDTO contato) {
		final SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(contato.getTo());
		sm.setCc(contato.getEmail());
		sm.setFrom(sender);
		sm.setSubject(contato.getAssunto());
		sm.setSentDate(LocalDate.now().toDate());
		sm.setText(contato.getMensagem());
		return sm;
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromMembro(final Membro membro, final String senha) {
		final SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(membro.getEmail());
		sm.setFrom(sender);
		sm.setSubject("PIC Boletim - Gerar nova Senha");
		sm.setSentDate(LocalDate.now().toDate());
		sm.setText("A senha foi gerada com sucesso. A senha é: " + senha);
		return sm;
	}
	
	protected String htmlFromTemplateContato(final ContatoDTO obj) {
		final Context context = new Context();
		context.setVariable("membro", obj);
		return templateEngine.process("email/envioContato", context);
		
	}
	
	protected String htmlFromTemplateRecuperacaoSenha(final Membro obj, final String senha) {
		final Context context = new Context();
		context.setVariable("membro", obj);
		context.setVariable("senha", senha);
		return templateEngine.process("email/envioSenhaEmail", context);
		
	}
	
	@Override
	public void sendContatoHtml(final ContatoDTO contato) {
		final MimeMessage mm;
		try {
			mm = prepareMimeMessageFromContato(contato);
			sendEmailHtml(mm);
		} catch (MessagingException e) {
			sendContato(contato);
		}
	}

	protected MimeMessage prepareMimeMessageFromContato(final ContatoDTO contato) throws MessagingException {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(contato.getTo());
		mmh.setCc(contato.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject(contato.getAssunto());
		mmh.setSentDate(LocalDate.now().toDate());
		mmh.setText(htmlFromTemplateContato(contato), true);
		return mimeMessage;
	}
	
	protected MimeMessage prepareMimeMessageFromMembro(final Membro membro,final String senha) throws MessagingException {
		final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		final MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(membro.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("PIC Boletim - Gerar nova Senha");
		mmh.setSentDate(LocalDate.now().toDate());
		mmh.setText(htmlFromTemplateRecuperacaoSenha(membro, senha), true);
		return mimeMessage;
	}
	
	
	public void sendNewPasswordEmail(final Membro membro) {
		final MimeMessage mm;
		try {
			mm = prepareMimeMessageFromMembro(membro, membro.getSenha());
			sendEmailHtml(mm);
		} catch (MessagingException e) {
			sendNewPassWord(membro, membro.getSenha());
		}
	}
}
