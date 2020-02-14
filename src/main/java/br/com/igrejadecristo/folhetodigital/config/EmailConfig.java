package br.com.igrejadecristo.folhetodigital.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

	 @Bean
	    public JavaMailSender getJavaMailSender() 
	    {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.gmail.com");
	        mailSender.setPort(25);
	          
	        mailSender.setUsername("pic.taguatinga.app@gmail.com");
	        mailSender.setPassword(System.getenv("SENHA_EMAIL"));
	          
	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");
	          

	        return mailSender;
	    }
}
