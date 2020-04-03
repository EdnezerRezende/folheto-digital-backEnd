package br.com.igrejadecristo.folhetodigital.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.AgendaEvento;
import br.com.igrejadecristo.folhetodigital.entidades.Aniversariante;
import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.Devocional;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoIgreja;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoMembro;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoPG;
import br.com.igrejadecristo.folhetodigital.entidades.Estado;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.entidades.Missao;
import br.com.igrejadecristo.folhetodigital.entidades.OfertaServico;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;
import br.com.igrejadecristo.folhetodigital.respositories.AgendaEventoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.CidadeRepository;
import br.com.igrejadecristo.folhetodigital.respositories.DevocionalRepository;
import br.com.igrejadecristo.folhetodigital.respositories.EnderecoPGRepository;
import br.com.igrejadecristo.folhetodigital.respositories.EstadoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MissaoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.OfertaServicoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.PequenoGrupoRepository;

@Service
public class DBService {

	@Autowired
	public MembroRepository membroRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private IgrejaRepository igrejaRepository;

	@Autowired
	private MensagemRepository mensagemRepository;

	@Autowired
	private PequenoGrupoRepository pgRepository;

	@Autowired
	private EnderecoPGRepository enderecoPgRepository;
	
	@Autowired
	private AniversarianteService aniversarianteService;

	@Autowired
	private AgendaEventoRepository agendaEventoRepository;

	@Autowired
	private DevocionalRepository devocionalRepository;
	
	@Autowired
	private BoletimService boletimService;

	@Autowired
	private OfertaServicoRepository ofertaServicoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private MissaoRepository missaoRepository;

	public void instantiateTestDatabase() throws ParseException {
		Estado brasilia = new Estado(null, "Brasília","DF");

		Cidade cidadeIgreja = new Cidade(null, "Taguatinha Sul", brasilia);

		Igreja igreja1 = new Igreja(null, "Primeira Igreja de Cristo", "22782170000108", "cristeltagsul@bol.com.br");
		igreja1.getTelefones().addAll(Arrays.asList("6135631865", "6135635788"));

		EnderecoIgreja enderecoIgreja = new EnderecoIgreja(null, "QSB 10/11 A.E. 09", "", "", "", "72015600", igreja1,
				cidadeIgreja);
		igreja1.setEndereco(enderecoIgreja);

		Estado est1 = new Estado(null, "Minas Gerais", "MG");
		Estado est2 = new Estado(null, "São Paulo", "SP");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		estadoRepository.saveAll(Arrays.asList(est1, est2, brasilia));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, cidadeIgreja));

		igrejaRepository.saveAll(Arrays.asList(igreja1));
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm");
		LocalDateTime dataHoje = LocalDateTime.now();
		String dataBoletimGerado = boletimService.obterDataGeracaoBoletim(dataHoje.toLocalDate());
		LocalDateTime dataLimiteBusca = LocalDateTime.parse(dataBoletimGerado + " 00:00",parser).minusDays(6);
		
		Membro membro1 = new Membro(null, "Teste", "admin@gmail.com", "12345678978", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate().minusDays(3));
		Membro membro3 = new Membro(null, "Teste1", "membro@gmail.com", "37668105026", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate().plusDays(2));
		Membro membro2 = new Membro(null, "Teste 2", "lider@gmail.com", "78945612378", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate());
		membro1.addPerfil(Perfil.ADMIN);
		membro2.addPerfil(Perfil.LIDER);

		membro1.getTelefones().addAll(Arrays.asList("6127363323", "6193838393"));
		membro2.getTelefones().addAll(Arrays.asList("6193883321", "6134252625"));
		membro3.getTelefones().addAll(Arrays.asList("6145645789", "6155688977"));

		EnderecoMembro e1 = new EnderecoMembro(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", membro1,
				c1);
		EnderecoMembro e2 = new EnderecoMembro(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", membro1,
				c2);
		EnderecoMembro e3 = new EnderecoMembro(null, "Avenida Floriano", "2106", null, "Centro", "281777012", membro2,
				c2);
		EnderecoMembro e4 = new EnderecoMembro(null, "Avenida Floriano", "2244", null, "Centro", "281777012", membro2,
				c2);
		EnderecoMembro e5 = new EnderecoMembro(null, "Avenida Floriano", "2244", null, "Centro", "281777012", membro3,
				c2);

		membro1.getEnderecos().addAll(Arrays.asList(e1, e2));
		membro2.getEnderecos().addAll(Arrays.asList(e3, e4));
		membro3.getEnderecos().addAll(Arrays.asList(e5));

		membroRepository.saveAll(Arrays.asList(membro1, membro2, membro3));
		
		Aniversariante aniversariante1 = new Aniversariante(membro1);
		Aniversariante aniversariante2 = new Aniversariante(membro2);
		Aniversariante aniversariante3 = new Aniversariante(membro3);
		aniversarianteService.salvarAniversariante(aniversariante1);
		aniversarianteService.salvarAniversariante(aniversariante2);
		aniversarianteService.salvarAniversariante(aniversariante3);
		
		
		
		Mensagem mensagem1 = new Mensagem(null,
				"Um dos devocionais dos PG´s da última semana foi o texto de 1 Co 3. "
				+ "Nele, o apóstolo Paulo diz aos irmãos da igreja em Corinto qe desejava compartilhar "
				+ "com eles coisas mais profundas mas que isso não seria possível por conta das atitudes "
				+ "desses irmãos.<hr>\"Irmãos, quando estive com vocês, não pude lhes falar como a "
				+ "pessoas espirituais, mas como se pertencessem a este mundo ou fossem criancinhas "
				+ "em Cristo\" (1 Co 3.1, NVT)<hr>O interessante do texto é observar que Paulo não "
				+ "julga a espiritualidade dos irmãos a partir dos seus dons. Ele não os chama de "
				+ "crentes carnais porque estes não falavam em línguas ou porque não tinham discernimento "
				+ "de espíritos. Paulo os chama de carnais porque suas atitudes não refletiam a sua "
				+ "espiritualidade.<div>\"(...) Têm ciúme uns dos outros, discutem e brigam entre si. "
				+ "Acaso isso não mostra que são controlados por sua natureza humana e que vivem como "
				+ "pessoas do mundo? \" (I Co 3.3, NVT)</div><hr>Apesar de frequentarem a comunidade "
				+ "cristã, aqueles irmãos não viviam uma vida de comunhão. Apesar fazerem parte de um "
				+ "corpo, eles não estavam em união buscando o bem do corpo. Na continuação do texto, "
				+ "vemos Paulo trazendo à tona o partidarismo e a divisão que havia dentro da igreja."
				+ "<hr>\"Quando um de vocês diz:&nbsp; \"Eu sigo Paulo\", e o outro diz: \"Eu sigo Apolo\", "
				+ "não estão agindo exatamente como as pessoas do mundo? \"&nbsp; (I Co 3.4, NVT)"
				+ "<hr>Paulo usa palavras duras para mostrar para aqueles irmãos que o fato de eles "
				+ "estarem na igreja não os diferenciava das pessoas do mundo se o que eles diziam crer "
				+ "não podia ser visto em suas atitudes, ou seja, de nada adiantava que eles se "
				+ "considerassem espirituais se sua espiritualidade não podia ser vista na vida "
				+ "cotidiana.<hr>E isso continua valendo nos nossos dias. De nada adianta frequentarmos "
				+ "os cultos, fazermos parte de um PG e participarmos da escola dominical se as nossas"
				+ " atitudes não são compatíveis com o Cristo que dizemos seguir. Se nos consideramos "
				+ "discípulos dEle, precisamos seguir os seus passos e viver como Ele viveu - "
				+ "especialmente do lado de fora da igreja.<hr>As nossas vidas cotidianas precisam "
				+ "refletir a vida de Cristo. Precisamos ser parecidos com Jesus no trânsito, "
				+ "no trabalho, na escola e em casa. Precisamos demonstrar com nossas atitudes "
				+ "que seguimos o Mestre de Nazaré. Enfim, precisamos colocar a nossa espiritualidade "
				+ "em prática e sermos cada vez mais parecidos com Jesus.",
				"Pra Renata Cabral", dataLimiteBusca, "O Nosso papel: Amar e servir", igreja1);

		Mensagem mensagem2 = new Mensagem(null,
				"Apenas mais uma mensagem para cadastrar no banco como exemplo "
						+ "Não deve ser considerado, apenas a título de exemplo ",
				"Teste de nova", LocalDateTime.now(), "Mensagem de Teste, Exemplo", igreja1);

		mensagemRepository.saveAll(Arrays.asList(mensagem1, mensagem2));

		Missao missao1 = new Missao(null,
				"A intolerância religiosa é resultado de um longo processo histórico, em que uma pessoa enfrenta perseguição, "
				+ "ofensa e agressão por expor a fé em qualquer região do mundo. A intolerânci contra cristãos perseguidos costuma "
				+ "partir de grupos extremistas, como por exemplo: Estado Islâmico, Boko Haram e Al-Shabaab.", "Pra Renata Cabral", 
				dataLimiteBusca, "Intolerância Religiosa", igreja1);
		
		Missao missao2 = new Missao(null,
				"A intolerância religiosa é resultado de um longo processo histórico, em que uma pessoa enfrenta perseguição, "
				+ "ofensa e agressão por expor a fé em qualquer região do mundo. A intolerânci contra cristãos perseguidos costuma "
				+ "partir de grupos extremistas, como por exemplo: Estado Islâmico, Boko Haram e Al-Shabaab.", "Pra Renata Cabral",
				LocalDateTime.now(), "Intolerância Religiosa 2", igreja1);

		missaoRepository.saveAll(Arrays.asList(missao1, missao2));

		PequenoGrupo pg1 = new PequenoGrupo(null, "Casa da maria Helena", "Iolanda", igreja1, "Quinta-Feira",
				LocalTime.of(19, 30));
		PequenoGrupo pg2 = new PequenoGrupo(null, "Casa da Cleide e Paulo", "Moisés", igreja1, "Quarta-Feira",
				LocalTime.of(20, 00));
		PequenoGrupo pg3 = new PequenoGrupo(null, "Casa da Helena e Pedro", "Pr. Euflávio", igreja1, "Quarta-Feira",
				LocalTime.of(20, 00));

		pgRepository.saveAll(Arrays.asList(pg1, pg2, pg3));

		EnderecoPG endPg = new EnderecoPG(null, "CSB 03", "Lote 5", "Apto 1201", "Ed. São José", "1234567", pg1,
				cidadeIgreja);
		EnderecoPG endPg2 = new EnderecoPG(null, "SMT Conjunto 2", "Lote 5C", "", "", "", pg2, cidadeIgreja);
		EnderecoPG endPg3 = new EnderecoPG(null, "Quadra 101, conjunto 12", "casa 5", "", "", "", pg3, cidadeIgreja);

		enderecoPgRepository.saveAll(Arrays.asList(endPg, endPg2, endPg3));

		pg1.setEndereco(endPg);
		pg2.setEndereco(endPg2);
		pg3.setEndereco(endPg3);
		pgRepository.saveAll(Arrays.asList(pg1, pg2, pg3));

		AgendaEvento agenda = new AgendaEvento(null, "Escola Bíblica", igreja1, "Domingo", LocalTime.of(18, 00), false,
				"Escola bíblica para aprender sobre a Palavra de Deus", null, null);

		AgendaEvento agenda1 = new AgendaEvento(null, "Culto de Celebração", igreja1, "Domingo", LocalTime.of(19, 00),
				false, "Culto de Louvor e adoração a Deus", null, null);

		AgendaEvento evento1 = new AgendaEvento(null, "Convenção Nacional", igreja1, "", LocalTime.of(19, 30), true,
				"Vem ai a convernção Nacional das igrejas de Cristo. O evento, que acontece de 25 a 27 de outubro em Goiânia, contará com a presença do Pr. Carlinhos Queiroz e do cantor Thiago Grulha. Já fizemos algumas reservas no Umuarama Hotel. Para garantir a sua vaga, converse com o Pr. Gerson o quanto antes",
				LocalDate.of(2019, 10, 25), LocalDate.of(2019, 10, 27));

		agendaEventoRepository.saveAll(Arrays.asList(agenda, agenda1, evento1));

		Devocional devocional1 = new Devocional(null, "Lucas 16:1-15", igreja1,
				"Examinar as escrituras para adquirir sabedoria", dataLimiteBusca.toLocalDate());
		Devocional devocional2 = new Devocional(null, "Apocalipse 1:5-10", igreja1, "Examinar", dataLimiteBusca.toLocalDate());
		devocionalRepository.saveAll(Arrays.asList(devocional1, devocional2));

		OfertaServico oferta1 = new OfertaServico(null, "Academia Maestro", igreja1,
				"Escola de música. Aprenda a tocar suas canções favoritas!", null, null, null, "Taguatinga Sul",
				LocalDate.now());
		oferta1.getTelefones().addAll(Arrays.asList("981471968"));
		OfertaServico oferta2 = new OfertaServico(null, "Fique bela com a Pra Maria", igreja1,
				"Avon. Demilus. Natura. Produtos a pronta-entrega", null, null, null, "Taguatinga Sul",
				LocalDate.now());
		oferta2.getTelefones().addAll(Arrays.asList("982912987"));
		OfertaServico oferta3 = new OfertaServico(null, "Miter Top Team - Escola de Lutas", igreja1,
				"Judô, Jiu Jitsu, Taekwondo, Karatê, Muay Thai, Boxe, MMA, Defesa pessoal, Miter Fight Training", null,
				null, null, null, LocalDate.now());
		oferta3.getTelefones().addAll(Arrays.asList("39733030", "999638966"));
		OfertaServico oferta4 = new OfertaServico(null, "Paulo e Saulo Corretora de Seguros", igreja1,
				"Plano de Seguro / Seguro Auto Paulo Chaveiro", "paulochaveiro@gmail.com", null, null, null,
				LocalDate.now());
		oferta4.getTelefones().addAll(Arrays.asList("986178886"));

		ofertaServicoRepository.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4));

	}
}
