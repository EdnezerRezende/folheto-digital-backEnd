package br.com.pic.folheto.services;

import br.com.pic.folheto.entidades.*;
import br.com.pic.folheto.entidades.enums.Perfil;
import br.com.pic.folheto.respositories.CidadeRepository;
import br.com.pic.folheto.respositories.EstadoRepository;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

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
		final Estado acre = construtorEstado("Acre", "AC");
		final Estado alagoas = construtorEstado( "Alagoas", "AL");
		final Estado amazonas = construtorEstado( "Amazonas", "AM");
		final Estado amapa = construtorEstado( "Amapá", "AP");
		final Estado bahia = construtorEstado("Bahia", "BA");
		final Estado ceara = construtorEstado("Ceará", "CE");
		final Estado df = construtorEstado("Distrito Federal", "DF");
		final Estado espiritoSanto = construtorEstado("Espírito Santo", "ES");
		final Estado goias = construtorEstado("Goiás", "GO");
		final Estado maranhao = construtorEstado("Maranhão", "MA");
		final Estado minas = construtorEstado("Minas Gerais", "MG");
		final Estado matoSul = construtorEstado("Mato Grosso do Sul", "MS");
		final Estado mato = construtorEstado("Mato Grosso", "MT");
		final Estado para = construtorEstado("Pará", "PA");
		final Estado paraiba = construtorEstado("Paraíba", "PB");
		final Estado pernambuco = construtorEstado("Pernambuco", "PE");
		final Estado piaui = construtorEstado("Piauí", "PI");
		final Estado parana = construtorEstado("Paraná", "PR");
		final Estado rio = construtorEstado("Rio de Janeiro", "RJ");
		final Estado rioGrandeNorte = construtorEstado( "Rio Grande do Norte", "RN");
		final Estado rondonia = construtorEstado("Rondônia", "RO");
		final Estado roraima = construtorEstado("Roraima", "RR");
		final Estado rioGrandeSul = construtorEstado( "Rio Grande do Sul", "RS");
		final Estado santaCatarina = construtorEstado("Santa Catarina", "SC");
		final Estado sergipe = construtorEstado("Sergipe", "SE");
		final Estado saoPaulo = construtorEstado("São Paulo", "SP");
		final Estado tocantins = construtorEstado("Tocantins", "TO");

		estadoRepository.saveAll(Arrays.asList(acre, alagoas, amazonas, amapa, bahia, ceara, df, espiritoSanto, goias,
				maranhao, minas, matoSul, mato, para, paraiba, pernambuco, piaui, parana, rio, rioGrandeNorte, rondonia,
				roraima, rioGrandeSul, santaCatarina, sergipe, saoPaulo, tocantins));

		final Cidade cidadeIgreja = construtorCidade("Taguatinha Sul", df);

		cidadeRepository.saveAll(Arrays.asList(construtorCidade("Brasília", df),
				construtorCidade( "Taguatinha Sul", df),
				construtorCidade("Abadia de Goiás", goias),
				construtorCidade("Abadiânia", goias),
				construtorCidade("Acreúna", goias),
				construtorCidade("Adelândia", goias),
				construtorCidade("Água Fria de Goiás", goias),
				construtorCidade("Água Limpa", goias),
				construtorCidade("Águas Lindas de Goiás", goias),
				construtorCidade("Alexânia", goias),
				construtorCidade("Aloândia", goias),
				construtorCidade("Alto Horizonte", goias),
				construtorCidade("Alto Paraíso de Goiás", goias),
				construtorCidade("Alvorada do Norte", goias),
				construtorCidade("Amaralina", goias),
				construtorCidade("Americano do Brasil", goias),
				construtorCidade("Amorinópolis", goias),
				construtorCidade("Anápolis", goias),
				construtorCidade("Anhanguera", goias)));

		final Igreja igreja1 = Igreja.builder()
				.nome("Primeira Igreja de Cristo")
				.cnpj("22782170000108")
				.email("cristeltagsul@bol.com.br")
				.build();
		igreja1.getTelefones().addAll(Arrays.asList("6135631865", "6135635788"));

		final EnderecoIgreja enderecoIgreja =
				EnderecoIgreja.builder()
						.logradouro("QSB 10/11 A.E. 09")
						.cep("72015600")
						.igreja(igreja1)
						.cidade(cidadeIgreja)
						.build();
		igreja1.setEndereco(enderecoIgreja);

		igrejaRepository.saveAll(Arrays.asList(igreja1));

		final LocalDate dataNascimento = LocalDate.now();

		final Membro membro1 = Membro.builder()
						.nome("Administrador")
						.email("pic.taguatinga.app@gmail.com")
						.cpf("12345678978")
						.senha(pe.encode("picTaguatinga"))
						.igreja(igreja1)
						.dataNascimento(dataNascimento.minusDays(3))
						.build();
		membro1.addPerfil(Perfil.ADMIN);

		membro1.getTelefones().addAll(Arrays.asList("6127363323", "6193838393"));

		final EnderecoMembro e1 =
				EnderecoMembro.builder()
						.logradouro("QSB 10/11 A.E. 09")
						.numero("")
						.complemento("")
						.bairro("")
						.cep("72015600")
						.membro(membro1)
						.cidade(cidadeIgreja)
						.build();

		membro1.getEnderecos().addAll(Arrays.asList(e1));

		membroRepository.saveAll(Arrays.asList(membro1));

	}

	private Cidade construtorCidade(final String nome, final Estado estado){
		return Cidade.builder()
				.nome(nome)
				.estado(estado)
				.build();
	}
	private Estado construtorEstado(final String nome, final String uf){
		return Estado.builder()
				.nome(nome)
				.uf(uf)
				.build();
	}
}
