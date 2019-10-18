package br.com.igrejadecristo.folhetodigital.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.Endereco;
import br.com.igrejadecristo.folhetodigital.entidades.Estado;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;
import br.com.igrejadecristo.folhetodigital.respositories.CidadeRepository;
import br.com.igrejadecristo.folhetodigital.respositories.EstadoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;

@Service
public class DBService {

//	@Autowired
//	private BCryptPasswordEncoder pe;
	
	@Autowired
	public MembroRepository membroRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public void instantiateTestDatabase() throws ParseException{
		Membro membro1 = new Membro(null, "Teste", "teste@gmail.com", "12345678978","1234");
		Membro membro2 = new Membro(null, "Teste 2", "teste2@gmail.com", "78945612378","4321");
		membro1.addPerfil(Perfil.ADMIN);
		membro2.addPerfil(Perfil.PASTOR);
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		membro1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		membro2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", membro1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", membro1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106", null, "Centro", "281777012", membro2, c2);
		Endereco e4 = new Endereco(null, "Avenida Floriano", "2244", null, "Centro", "281777012", membro2, c2);
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		membro1.getEnderecos().addAll(Arrays.asList(e1, e2));
		membro2.getEnderecos().addAll(Arrays.asList(e3, e4));
		
		membroRepository.saveAll(Arrays.asList(membro1,membro2));
	}
}
