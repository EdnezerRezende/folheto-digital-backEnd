package br.com.igrejadecristo.folhetodigital.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.igrejadecristo.folhetodigital.services.DBServiceProd;
import br.com.igrejadecristo.folhetodigital.services.EmailService;
import br.com.igrejadecristo.folhetodigital.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Autowired
	private DBServiceProd dbServiceProd;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbServiceProd.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
}
