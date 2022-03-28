package br.com.pic.folheto.config;

import java.text.ParseException;

import br.com.pic.folheto.services.interfaces.EmailInterfaceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.pic.folheto.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
//	@Autowired
//	private DBServiceProd dbServiceProd;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDataBase() throws ParseException {
		
		return false;
	}
	
	@Bean
	public EmailInterfaceService emailService() {
		return new SmtpEmailService();
	}
	
}
