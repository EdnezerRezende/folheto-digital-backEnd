package br.com.pic.folheto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;


@SpringBootApplication
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
@OpenAPIDefinition(info = @Info(title = "PIC Folheto", version = "2.0", description = "Folheto informativo da Primeira igreja de Cristo"))
@EnableJms
public class FolhetoApplication extends SpringBootServletInitializer implements ApplicationRunner {
	private static final Logger logger = LogManager.getLogger(FolhetoApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(FolhetoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(FolhetoApplication.class);
	}

	@Override
	public void run(final ApplicationArguments args) throws Exception {
	}

	@Bean
	public Queue queueEmail() {
		return new ActiveMQQueue("queueEmail");
	}

}
