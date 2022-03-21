package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.filas.converters.EmailMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class ContatoService {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private EmailMessageConverter contatoMessageConverter;

	@Autowired
	private Queue queue;

	public void enviarContato(ContatoDTO contato) {
		this.jmsMessagingTemplate.convertAndSend(this.queue, contato);
	}
}
