package br.com.pic.folheto.filas.converters;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.entidades.MenssagemEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class EmailMessageConverter implements MessageConverter {
    private static final Logger LOG = LoggerFactory.getLogger(EmailMessageConverter.class);

    ObjectMapper mapper;


    public EmailMessageConverter(){
        mapper = new ObjectMapper();
    }
    @Override
    public Message toMessage(final Object object, final Session session) throws JMSException, MessageConversionException {
        MenssagemEmail menssagemEmail = getTypeEntity(object);

        String payload = null;

        try {
            mapper.findAndRegisterModules();
            payload = mapper.writeValueAsString(menssagemEmail);
            LOG.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            LOG.error("error converting form MenssagemEmail Interface", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    private MenssagemEmail getTypeEntity(Object object) {
        MenssagemEmail menssagemEmail = null;
        if(object instanceof ContatoDTO){
            menssagemEmail = (ContatoDTO) object;
        }

        if(object instanceof Membro){
            menssagemEmail = (Membro) object;
        }
        return menssagemEmail;
    }

    @Override
    public Object fromMessage(final Message message) throws JMSException, MessageConversionException {
        final TextMessage textMessage = (TextMessage) message;
        final Object payloadObject = textMessage.getText();
        final String payload = textMessage.getText();
        LOG.info("inbound json='{}'", payloadObject);

        MenssagemEmail menssagemRetorno = null;

        try {
            mapper.findAndRegisterModules();
            menssagemRetorno = mapper.readValue(payload, ContatoDTO.class);

        } catch (Exception e) {
            try {
                mapper.readValue(payload, Membro.class);
            } catch (JsonProcessingException ex) {
                LOG.error("error converting to MenssagemEmail Interface", e);
            }

        }

        return menssagemRetorno;
    }
}
