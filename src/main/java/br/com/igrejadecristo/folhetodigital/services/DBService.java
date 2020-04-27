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
import br.com.igrejadecristo.folhetodigital.util.DataUtil;

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
		String dataBoletimGerado = DataUtil.obterDataGeracaoBoletim(dataHoje.toLocalDate());
		LocalDateTime dataLimiteBusca = LocalDateTime.parse(dataBoletimGerado + " 00:00",parser).plusDays(6);
		
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
				"<div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">As estrelas servem de orientação para os\r\n" + 
				" viajantes há séculos. Navegantes baseiam-se\r\n" + 
				" na posição delas para traçarem suas rotas e\r\n" + 
				" para ajustá-las. Montanhistas, corredores e\r\n" + 
				" outros atletas que praticam esportes em meio à\r\n" + 
				" natureza fazem o mesmo. Numa noite escura,\r\n" + 
				" são os pontos brilhantes que apontam o\r\n" + 
				" caminho para aqueles que olham para elas.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Na Bíblia, as estrelas têm também um papel\r\n" + 
				" importante. Deus usou os astros no céu para\r\n" + 
				" dizer a Abraão que sua descendência seria\r\n" + 
				" numerosa. Por sua vez, os reis magos ficaram\r\n" + 
				" sabendo do nascimento de Jesus e uma estrela\r\n" + 
				" os guiou até onde o menino e seus pais\r\n" + 
				" estavam. Nos dois casos citados, as estrelas\r\n" + 
				" tiveram um papel de orientação. Para Abraão,\r\n" + 
				" foram um lembrete que orientou a sua fé. Para\r\n" + 
				" os magos, os direcionou diretamente até onde\r\n" + 
				" havia nascido o Salvador.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Em Filipenses 2.15, Paulo nos encoraja a brilhar\r\n" + 
				" como estrelas no céu em meio a um mundo\r\n" + 
				" desinteressado de Deus. Ou seja, em meio a\r\n" + 
				" um mundo de escuridão, a nossa luz precisa\r\n" + 
				" brilhar para orientar, direcionar e apontar para\r\n" + 
				" Cristo.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">E como podemos fazer isso? Vivendo e\r\n" + 
				" frutificando como filhos da luz. Em Efésios 5.9\r\n" + 
				" lemos que “o fruto da luz consiste em toda\r\n" + 
				" bondade, justiça e verdade”. Ou seja, quando\r\n" + 
				" os que nos cercam buscarem a bondade, a\r\n" + 
				" justiça e a verdade, eles precisam olhar para as\r\n" + 
				"  nossas vidas para encontrarem isso.</span><br></div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">O mundo carece de referenciais e, muitas\r\n" + 
				" vezes, os procuram nos lugares errados.\r\n" + 
				" Elege artistas de TV, influenciadores digitais,\r\n" + 
				" participantes de reality shows, atletas e\r\n" + 
				" políticos como as suas fontes de bondade,\r\n" + 
				" justiça e verdade. Porém, como estrelas\r\n" + 
				" artificiais, como falsos astros, esses\r\n" + 
				" referenciais acabam confundindo os que os\r\n" + 
				" seguem e se mostram pouco eficazes. Ao\r\n" + 
				" invés de direcionarem à fonte de toda luz,\r\n" + 
				" acabam confundindo e levando a outros\r\n" + 
				" caminhos que não o de Cristo.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como cristãos, somos chamados a ser e\r\n" + 
				" fazer diferença. Em tempos de crise, como\r\n" + 
				" os que temos vivido, precisamos nos lembrar\r\n" + 
				" que cabe a nós sermos os verdadeiros\r\n" + 
				" referenciais. A nossa vida precisa exalar\r\n" + 
				" bondade em tudo o que fizermos. Os nossos\r\n" + 
				" atos precisam ser fonte de justiça. E a\r\n" + 
				" verdade permear cada escolha, cada\r\n" + 
				" palavra, cada sentimento e ação.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Precisamos ser aqueles que brilham e\r\n" + 
				" trazem esperança. Aqueles que apontam\r\n" + 
				" para um futuro onde cada lágrima será\r\n" + 
				" enxugada. Aqueles que direcionam para a\r\n" + 
				" paz que excede todo entendimento e para a\r\n" + 
				" Verdade que liberta!&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como estrelas no meio da escuridão, que\r\n" + 
				" possamos brilhar a luz de Cristo que há em\r\n" + 
				" nós!&nbsp;</div>",
				"Pra Renata Cabral", dataLimiteBusca, "O Nosso papel: Amar e servir", igreja1);

		Mensagem mensagem2 = new Mensagem(null,
				"Apenas mais uma mensagem para cadastrar no banco como exemplo "
						+ "Não deve ser considerado, apenas a título de exemplo ",
				"Teste de nova", dataLimiteBusca.plusDays(5), "Mensagem de Teste, Exemplo", igreja1);

		mensagemRepository.saveAll(Arrays.asList(mensagem1, mensagem2));

		Missao missao1 = new Missao(null,
				"A intolerância religiosa é resultado de um longo processo histórico, em que uma pessoa enfrenta perseguição, "
				+ "ofensa e agressão por expor a fé em qualquer região do mundo. A intolerânci contra cristãos perseguidos costuma "
				+ "partir de grupos extremistas, como por exemplo: Estado Islâmico, Boko Haram e Al-Shabaab.", "Pra Renata Cabral", 
				dataLimiteBusca.plusDays(5), "Intolerância Religiosa", igreja1);
		
		Missao missao2 = new Missao(null,
				"<div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">As estrelas servem de orientação para os\r\n" + 
				" viajantes há séculos. Navegantes baseiam-se\r\n" + 
				" na posição delas para traçarem suas rotas e\r\n" + 
				" para ajustá-las. Montanhistas, corredores e\r\n" + 
				" outros atletas que praticam esportes em meio à\r\n" + 
				" natureza fazem o mesmo. Numa noite escura,\r\n" + 
				" são os pontos brilhantes que apontam o\r\n" + 
				" caminho para aqueles que olham para elas.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Na Bíblia, as estrelas têm também um papel\r\n" + 
				" importante. Deus usou os astros no céu para\r\n" + 
				" dizer a Abraão que sua descendência seria\r\n" + 
				" numerosa. Por sua vez, os reis magos ficaram\r\n" + 
				" sabendo do nascimento de Jesus e uma estrela\r\n" + 
				" os guiou até onde o menino e seus pais\r\n" + 
				" estavam. Nos dois casos citados, as estrelas\r\n" + 
				" tiveram um papel de orientação. Para Abraão,\r\n" + 
				" foram um lembrete que orientou a sua fé. Para\r\n" + 
				" os magos, os direcionou diretamente até onde\r\n" + 
				" havia nascido o Salvador.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Em Filipenses 2.15, Paulo nos encoraja a brilhar\r\n" + 
				" como estrelas no céu em meio a um mundo\r\n" + 
				" desinteressado de Deus. Ou seja, em meio a\r\n" + 
				" um mundo de escuridão, a nossa luz precisa\r\n" + 
				" brilhar para orientar, direcionar e apontar para\r\n" + 
				" Cristo.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">E como podemos fazer isso? Vivendo e\r\n" + 
				" frutificando como filhos da luz. Em Efésios 5.9\r\n" + 
				" lemos que “o fruto da luz consiste em toda\r\n" + 
				" bondade, justiça e verdade”. Ou seja, quando\r\n" + 
				" os que nos cercam buscarem a bondade, a\r\n" + 
				" justiça e a verdade, eles precisam olhar para as\r\n" + 
				"  nossas vidas para encontrarem isso.</span><br></div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">O mundo carece de referenciais e, muitas\r\n" + 
				" vezes, os procuram nos lugares errados.\r\n" + 
				" Elege artistas de TV, influenciadores digitais,\r\n" + 
				" participantes de reality shows, atletas e\r\n" + 
				" políticos como as suas fontes de bondade,\r\n" + 
				" justiça e verdade. Porém, como estrelas\r\n" + 
				" artificiais, como falsos astros, esses\r\n" + 
				" referenciais acabam confundindo os que os\r\n" + 
				" seguem e se mostram pouco eficazes. Ao\r\n" + 
				" invés de direcionarem à fonte de toda luz,\r\n" + 
				" acabam confundindo e levando a outros\r\n" + 
				" caminhos que não o de Cristo.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como cristãos, somos chamados a ser e\r\n" + 
				" fazer diferença. Em tempos de crise, como\r\n" + 
				" os que temos vivido, precisamos nos lembrar\r\n" + 
				" que cabe a nós sermos os verdadeiros\r\n" + 
				" referenciais. A nossa vida precisa exalar\r\n" + 
				" bondade em tudo o que fizermos. Os nossos\r\n" + 
				" atos precisam ser fonte de justiça. E a\r\n" + 
				" verdade permear cada escolha, cada\r\n" + 
				" palavra, cada sentimento e ação.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Precisamos ser aqueles que brilham e\r\n" + 
				" trazem esperança. Aqueles que apontam\r\n" + 
				" para um futuro onde cada lágrima será\r\n" + 
				" enxugada. Aqueles que direcionam para a\r\n" + 
				" paz que excede todo entendimento e para a\r\n" + 
				" Verdade que liberta!&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como estrelas no meio da escuridão, que\r\n" + 
				" possamos brilhar a luz de Cristo que há em\r\n" + 
				" nós!&nbsp;</div>", "Pra Renata Cabral",
				dataLimiteBusca, "Intolerância Religiosa 2", igreja1);

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
