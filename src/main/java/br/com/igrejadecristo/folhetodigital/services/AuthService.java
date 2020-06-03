package br.com.igrejadecristo.folhetodigital.services;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.NewPasswordDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;
import br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private MembroRepository membroRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
//	@Autowired
//	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Membro membro = membroRepository.findByEmail(email);
		if (membro == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		membro.setSenha(pe.encode(newPass));
		
		membroRepository.save(membro);
//		emailService.sendNewPasswordEmail(membro, newPass);
	}
	
	public void trocaSenha(NewPasswordDTO dto) {
		
		Membro membro = membroRepository.findByEmail(dto.getEmail());
		if (membro == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		membro.setSenha(pe.encode(dto.getPassword()));
		
		membroRepository.save(membro);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
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
