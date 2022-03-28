package br.com.pic.folheto.filas;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.entidades.MenssagemEmail;
import br.com.pic.folheto.services.interfaces.EmailInterfaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ListernerEmail {
    private static final Logger LOG = LoggerFactory.getLogger(ListernerEmail.class);

    @Autowired
    private EmailInterfaceService emailService;


    @JmsListener(destination = "queueEmail")
    public void receive(final MenssagemEmail menssagemEmail) {
        if(menssagemEmail instanceof  ContatoDTO)  emailService.sendContatoHtml((ContatoDTO) menssagemEmail);
        if(menssagemEmail instanceof Membro) emailService.sendNewPasswordEmail((Membro) menssagemEmail);
    }
}
