package br.com.pic.folheto.services;


import br.com.pic.folheto.dto.NewPasswordDTO;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.respositories.MembroRepository;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

	@Autowired
	private MembroRepository membroRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(final String email) {
		
		final Membro membro = membroRepository.findByEmail(email);
		if (membro == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		final String newPass = newPassword();
		salvarEEnviarSenhaGerada(membro, newPass);
	}

	private void salvarEEnviarSenhaGerada(final Membro membro, final String newPass) {
		membro.setSenha(pe.encode(newPass));

		membroRepository.save(membro);
		emailService.sendNewPasswordEmail(membro, newPass);
	}

	public void trocaSenha(final NewPasswordDTO dto) {
		
		final Membro membro = membroRepository.findByEmail(dto.getEmail());
		if (membro == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		salvarEEnviarSenhaGerada(membro, dto.getPassword());
	}

	private String newPassword() {
		final char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		final int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}