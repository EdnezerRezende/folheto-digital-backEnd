package br.com.igrejadecristo.folhetodigital.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoIgreja;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoMembro;
import br.com.igrejadecristo.folhetodigital.entidades.Estado;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;
import br.com.igrejadecristo.folhetodigital.respositories.CidadeRepository;
import br.com.igrejadecristo.folhetodigital.respositories.EstadoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;

@Service
public class DBServiceProd {

	@Autowired
	public MembroRepository membroRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private IgrejaRepository igrejaRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {
//		Estados
		Estado acre = new Estado(null, "Acre", "AC");
		Estado alagoas = new Estado(null, "Alagoas", "AL");
		Estado amazonas = new Estado(null, "Amazonas", "AM");
		Estado amapa = new Estado(null, "Amapá", "AP");
		Estado bahia = new Estado(null, "Bahia", "BA");
		Estado ceara = new Estado(null, "Ceará", "CE");
		Estado df = new Estado(null, "Distrito Federal", "DF");
		Estado espiritoSanto = new Estado(null, "Espírito Santo", "ES");
		Estado goias = new Estado(null, "Goiás", "GO");
		Estado maranhao = new Estado(null, "Maranhão", "MA");
		Estado minas = new Estado(null, "Minas Gerais", "MG");
		Estado matoSul = new Estado(null, "Mato Grosso do Sul", "MS");
		Estado mato = new Estado(null, "Mato Grosso", "MT");
		Estado para = new Estado(null, "Pará", "PA");
		Estado paraiba = new Estado(null, "Paraíba", "PB");
		Estado pernambuco = new Estado(null, "Pernambuco", "PE");
		Estado piaui = new Estado(null, "Piauí", "PI");
		Estado parana = new Estado(null, "Paraná", "PR");
		Estado rio = new Estado(null, "Rio de Janeiro", "RJ");
		Estado rioGrandeNorte = new Estado(null, "Rio Grande do Norte", "RN");
		Estado rondonia = new Estado(null, "Rondônia", "RO");
		Estado roraima = new Estado(null, "Roraima", "RR");
		Estado rioGrandeSul = new Estado(null, "Rio Grande do Sul", "RS");
		Estado santaCatarina = new Estado(null, "Santa Catarina", "SC");
		Estado sergipe = new Estado(null, "Sergipe", "SE");
		Estado saoPaulo = new Estado(null, "São Paulo", "SP");
		Estado tocantins = new Estado(null, "Tocantins", "TO");

		estadoRepository.saveAll(Arrays.asList(acre, alagoas, amazonas, amapa, bahia, ceara, df, espiritoSanto, goias,
				maranhao, minas, matoSul, mato, para, paraiba, pernambuco, piaui, parana, rio, rioGrandeNorte, rondonia,
				roraima, rioGrandeSul, santaCatarina, sergipe, saoPaulo, tocantins));
		Cidade cidadeIgreja = new Cidade(null, "Taguatinha Sul", df);

		cidadeRepository.saveAll(Arrays.asList(new Cidade(null, "Brasília", df), 
				new Cidade(null, "Taguatinha Sul", df),
				new Cidade(null, "Abadia de Goiás", goias), 
				new Cidade(null, "Abadiânia", goias),
				new Cidade(null, "Acreúna", goias), 
				new Cidade(null, "Adelândia", goias),
				new Cidade(null, "Água Fria de Goiás", goias), 
				new Cidade(null, "Água Limpa", goias),
				new Cidade(null, "Águas Lindas de Goiás", goias), 
				new Cidade(null, "Alexânia", goias),
				new Cidade(null, "Aloândia", goias), 
				new Cidade(null, "Alto Horizonte", goias),
				new Cidade(null, "Alto Paraíso de Goiás", goias), 
				new Cidade(null, "Alvorada do Norte", goias),
				new Cidade(null, "Amaralina", goias), 
				new Cidade(null, "Americano do Brasil", goias),
				new Cidade(null, "Amorinópolis", goias), 
				new Cidade(null, "Anápolis", goias),
				new Cidade(null, "Anhanguera", goias)));

		Igreja igreja1 = new Igreja(null, "Primeira Igreja de Cristo", "22782170000108", "cristeltagsul@bol.com.br");
		igreja1.getTelefones().addAll(Arrays.asList("6135631865", "6135635788"));

		EnderecoIgreja enderecoIgreja = new EnderecoIgreja(null, "QSB 10/11 A.E. 09", "", "", "", "72015600", igreja1,
				cidadeIgreja);
		igreja1.setEndereco(enderecoIgreja);

		igrejaRepository.saveAll(Arrays.asList(igreja1));

		LocalDate dataNascimento = LocalDate.now();
		Membro membro1 = new Membro(null, "Administrador", "admin@gmail.com", "12345678978", pe.encode("pic123"),
				igreja1, dataNascimento.minusDays(3));
		membro1.addPerfil(Perfil.ADMIN);

		membro1.getTelefones().addAll(Arrays.asList("6127363323", "6193838393"));

		EnderecoMembro e1 = new EnderecoMembro(null, "QSB 10/11 A.E. 09", "", "", "", "72015600", membro1,
				cidadeIgreja);

		membro1.getEnderecos().addAll(Arrays.asList(e1));

		membroRepository.saveAll(Arrays.asList(membro1));

	}
}
