package br.com.pic.folheto.services;

import br.com.pic.folheto.entidades.*;
import br.com.pic.folheto.entidades.enums.Perfil;
import br.com.pic.folheto.respositories.*;
import br.com.pic.folheto.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	private OfertaServicoRepository ofertaServicoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private MissaoRepository missaoRepository;

	public void instantiateTestDatabase() throws ParseException {
		final Estado brasilia = construtorEstado("Brasília", "DF") ;

		final Cidade cidadeIgreja = construtorCidade("Taguatinga Sul", brasilia);

		final Igreja igreja1 = Igreja.builder()
				.nome("Primeira Igreja de Cristo")
				.cnpj("22782170000108")
				.email("cristeltagsul@bol.com.br")
				.build();

		igreja1.getTelefones().addAll(Arrays.asList("6135631865", "6135635788"));

		final EnderecoIgreja enderecoIgreja = EnderecoIgreja.builder()
				.logradouro("QSB 10/11 A.E. 09")
				.cep("72015600")
				.igreja(igreja1)
				.cidade(cidadeIgreja)
				.build();

		igreja1.setEndereco(enderecoIgreja);

		final Estado est1 = construtorEstado("Minas Gerais", "MG") ;
		final Estado est2 = construtorEstado("São Paulo", "SP");

		final Cidade c1 =  construtorCidade("Uberlândia", est1);
		final Cidade c2 = construtorCidade("São Paulo", est2);
		final Cidade c3 = construtorCidade("Campinas", est2);

		estadoRepository.saveAll(Arrays.asList(est1, est2, brasilia));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, cidadeIgreja));

		igrejaRepository.saveAll(Arrays.asList(igreja1));
		final DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm");
		final LocalDateTime dataHoje = LocalDateTime.now();
		final String dataBoletimGerado = DataUtil.obterDataGeracaoBoletim(dataHoje.toLocalDate());
		final LocalDateTime dataLimiteBusca = LocalDateTime.parse(dataBoletimGerado + " 00:00",parser).plusDays(6);
		
		final Membro membro1 = construtorMembro("Teste", "admin@gmail.com", "12345678978", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate().minusDays(3));
		final Membro membro2 = construtorMembro("Teste2", "lider@gmail.com", "78945612378", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate());
		final Membro membro3 = construtorMembro("Teste3", "membro@gmail.com", "37668105026", pe.encode("1234"), igreja1, dataLimiteBusca.toLocalDate().minusDays(2));
		final Membro membro4 = construtorMembro("Visitante", "visitante@gmail.com", "37668105026", pe.encode("12345678"), igreja1, dataLimiteBusca.toLocalDate().plusDays(2));

		membro1.addPerfil(Perfil.ADMIN);
		membro2.addPerfil(Perfil.LIDER);
		
		Set<Perfil> perfis = new HashSet<>();
		perfis.add(Perfil.VISITANTE);
		membro4.setPerfis(perfis);

		membro1.getTelefones().addAll(Arrays.asList("6127363323", "6193838393"));
		membro2.getTelefones().addAll(Arrays.asList("6193883321", "6134252625"));
		membro3.getTelefones().addAll(Arrays.asList("6145645789", "6155688977"));
		membro4.getTelefones().addAll(Arrays.asList("6145645789", "6155688977"));
		
		final EnderecoMembro e1 = construtorEnderecoMembro("Rua Flores", "300", "Apto 303", "Jardim", "38220834", membro1,c1);
		final EnderecoMembro e2 = construtorEnderecoMembro("Avenida Matos", "105", "Sala 800", "Centro", "38777012", membro1,c2);
		final EnderecoMembro e3 = construtorEnderecoMembro("Avenida Floriano", "2106", null, "Centro", "281777012", membro2,c2);
		final EnderecoMembro e4 = construtorEnderecoMembro("Avenida Floriano", "2244", null, "Centro", "281777012", membro2,c2);
		final EnderecoMembro e5 = construtorEnderecoMembro("Avenida Floriano", "2244", null, "Centro", "281777012", membro3,c2);

		membro1.getEnderecos().addAll(Arrays.asList(e1, e2));
		membro2.getEnderecos().addAll(Arrays.asList(e3, e4));
		membro3.getEnderecos().addAll(Arrays.asList(e5));
		membro4.getEnderecos().addAll(Arrays.asList(e5));

		membroRepository.saveAll(Arrays.asList(membro1, membro2, membro3, membro4));
		
		final Aniversariante aniversariante1 = new Aniversariante(membro1);
		final Aniversariante aniversariante2 = new Aniversariante(membro2);
		final Aniversariante aniversariante3 = new Aniversariante(membro3);
		aniversarianteService.salvarAniversariante(aniversariante1);
		aniversarianteService.salvarAniversariante(aniversariante2);
		aniversarianteService.salvarAniversariante(aniversariante3);

		final Mensagem mensagem1 = construtorMensagem("<div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">As estrelas servem de orientação para os\r\n" +
				" viajantes há séculos. Navegantes baseiam-se\r\n" +
				" caminho para aqueles que olham para elas.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Na Bíblia, as estrelas têm também um papel\r\n" +
				" havia nascido o Salvador.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Em Filipenses 2.15, Paulo nos encoraja a brilhar\r\n" +
				" Cristo.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">E como podemos fazer isso? Vivendo e\r\n" +
				" frutificando como filhos da luz. Em Efésios 5.9\r\n" +
				"  nossas vidas para encontrarem isso.</span><br></div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">O mundo carece de referenciais e, muitas\r\n" +
				" vezes, os procuram nos lugares errados.\r\n" +
				" caminhos que não o de Cristo.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como cristãos, somos chamados a ser e\r\n" +
				" verdade permear cada escolha, cada\r\n" +
				" palavra, cada sentimento e ação.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Precisamos ser aqueles que brilham e\r\n" +
				" paz que excede todo entendimento e para a\r\n" +
				" Verdade que liberta!&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como estrelas no meio da escuridão, que\r\n" +
				" nós!&nbsp;</div>","Pra Renata Cabral", "O Nosso papel: Amar e servir", dataLimiteBusca, igreja1);

		final Mensagem mensagem2 = construtorMensagem("A intolerância religiosa é resultado de um longo processo histórico, em que uma pessoa enfrenta perseguição, "
						+ "ofensa e agressão por expor a fé em qualquer região do mundo. A intolerânci contra cristãos perseguidos costuma "
						+ "partir de grupos extremistas, como por exemplo: Estado Islâmico, Boko Haram e Al-Shabaab.",
						"Pra Renata Cabral", "Intolerância Religiosa", dataLimiteBusca.plusDays(5), igreja1);

		mensagemRepository.saveAll(Arrays.asList(mensagem1, mensagem2));

		final Missao missao1 = construtorMissao("A intolerância religiosa é resultado de um longo processo histórico, em que uma pessoa enfrenta perseguição, "
				+ "ofensa e agressão por expor a fé em qualquer região do mundo. A intolerânci contra cristãos perseguidos costuma "
				+ "partir de grupos extremistas, como por exemplo: Estado Islâmico, Boko Haram e Al-Shabaab.", "Pra Renata Cabral", 
				dataLimiteBusca.plusDays(5), "Intolerância Religiosa", igreja1);
		
		final Missao missao2 = construtorMissao(
				"<div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">As estrelas servem de orientação para os\r\n" + 
				" são os pontos brilhantes que apontam o\r\n" +
				" caminho para aqueles que olham para elas.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Na Bíblia, as estrelas têm também um papel\r\n" + 
				" os magos, os direcionou diretamente até onde\r\n" +
				" havia nascido o Salvador.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">Em Filipenses 2.15, Paulo nos encoraja a brilhar\r\n" + 
				" Cristo.&nbsp;</span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\"><br></span></div><div style=\"text-align: justify;\"><span style=\"color: inherit; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-ligatures: inherit; font-variant-caps: inherit; font-weight: inherit;\">E como podemos fazer isso? Vivendo e\r\n" +
				" justiça e a verdade, eles precisam olhar para as\r\n" +
				"  nossas vidas para encontrarem isso.</span><br></div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">O mundo carece de referenciais e, muitas\r\n" + 
				" acabam confundindo e levando a outros\r\n" +
				" caminhos que não o de Cristo.&nbsp;</div><div style=\"text-align: justify;\"><br></div><div style=\"text-align: justify;\">Como cristãos, somos chamados a ser e\r\n" + 
				" nós!&nbsp;</div>", "Pra Renata Cabral",
				dataLimiteBusca, "Intolerância Religiosa 2", igreja1);

		missaoRepository.saveAll(Arrays.asList(missao1, missao2));

		final PequenoGrupo pg1 = construtorPG("Casa da maria Helena", "Iolanda", igreja1, "Quinta-Feira",
				LocalTime.of(19, 30));
		final PequenoGrupo pg2 = construtorPG("Casa da Cleide e Paulo", "Moisés", igreja1, "Quarta-Feira",
				LocalTime.of(20, 00));
		final PequenoGrupo pg3 = construtorPG("Casa da Helena e Pedro", "Pr. Euflávio", igreja1, "Quarta-Feira",
				LocalTime.of(20, 00));

		pgRepository.saveAll(Arrays.asList(pg1, pg2, pg3));

		final EnderecoPG endPg = construtorEnderecoPG(
				"CSB 03", "Lote 5", "Apto 1201", "Ed. São José", "1234567", pg1,
				cidadeIgreja);
		final EnderecoPG endPg2 =  construtorEnderecoPG("SMT Conjunto 2", "Lote 5C", "", "", "", pg2, cidadeIgreja);
		final EnderecoPG endPg3 =  construtorEnderecoPG("Quadra 101, conjunto 12", "casa 5", "", "", "", pg3, cidadeIgreja);

		enderecoPgRepository.saveAll(Arrays.asList(endPg, endPg2, endPg3));

		pg1.setEndereco(endPg);
		pg2.setEndereco(endPg2);
		pg3.setEndereco(endPg3);
		pgRepository.saveAll(Arrays.asList(pg1, pg2, pg3));

		final AgendaEvento agenda = construtorAgendaEvento("Escola Bíblica", igreja1, "Domingo", LocalTime.of(18, 00), false,
				"Escola bíblica para aprender sobre a Palavra de Deus", "urldoEvento");

		final AgendaEvento agenda1 = construtorAgendaEvento("Culto de Celebração", igreja1, "Domingo", LocalTime.of(19, 00),
				false, "Culto de Louvor e adoração a Deus","urldoEvento");

		final AgendaEvento evento1 = construtorAgendaEvento("Convenção Nacional", igreja1, "", LocalTime.of(19, 30), true,
				"Vem ai a convernção Nacional das igrejas de Cristo. O evento, que acontece de 25 a 27 de outubro em Goiânia, contará com a presença do Pr. Carlinhos Queiroz e do cantor Thiago Grulha. Já fizemos algumas reservas no Umuarama Hotel. Para garantir a sua vaga, converse com o Pr. Gerson o quanto antes",
				"urldoEvento");

		agendaEventoRepository.saveAll(Arrays.asList(agenda, agenda1, evento1));

		final OfertaServico oferta1 = construtorOfertaServico("Academia Maestro", igreja1,
				"Escola de música. Aprenda a tocar suas canções favoritas!", "Taguatinga Sul",
				LocalDate.now());
		oferta1.getTelefones().addAll(Arrays.asList("981471968"));

		final OfertaServico oferta2 = construtorOfertaServico("Fique bela com a Pra Maria", igreja1,
				"Avon. Demilus. Natura. Produtos a pronta-entrega", "Taguatinga Sul",
				LocalDate.now());
		oferta2.getTelefones().addAll(Arrays.asList("982912987"));

		final OfertaServico oferta3 = construtorOfertaServico("Miter Top Team - Escola de Lutas", igreja1,
				"Judô, Jiu Jitsu, Taekwondo, Karatê, Muay Thai, Boxe, MMA, Defesa pessoal, Miter Fight Training",
				"Taguatinga", LocalDate.now());
		oferta3.getTelefones().addAll(Arrays.asList("39733030", "999638966"));

		final OfertaServico oferta4 = construtorOfertaServico("Paulo e Saulo Corretora de Seguros", igreja1,
				"Plano de Seguro / Seguro Auto Paulo Chaveiro", "paulochaveiro@gmail.com", LocalDate.now());
		oferta4.getTelefones().addAll(Arrays.asList("986178886"));

		ofertaServicoRepository.saveAll(Arrays.asList(oferta1, oferta2, oferta3, oferta4));

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

	private Membro construtorMembro( final String nome, final String email, String cpf,final String senha, final Igreja igreja, final LocalDate dataNascimento){
		return Membro.builder()
				.nome(nome)
				.email(email)
				.cpf(cpf)
				.senha(senha)
				.igreja(igreja)
				.dataNascimento(dataNascimento)
				.build();
	}
	private EnderecoMembro construtorEnderecoMembro( final String logradouro, final String numero, final String complemento, String bairro,final String cep, final Membro membro, final Cidade cidade){
		return EnderecoMembro.builder()
				.logradouro(logradouro)
				.numero(numero)
				.complemento(complemento)
				.bairro(bairro)
				.cep(cep)
				.membro(membro)
				.cidade(cidade)
				.build();
	}

	private Mensagem construtorMensagem(final String mensagem,final String autor, final String titulo, final LocalDateTime dataCriacao, final Igreja igreja){

		return Mensagem.builder()
				.autor(titulo)
				.dataCriado(dataCriacao)
				.titulo(titulo)
				.igreja(igreja)
				.mensagem(mensagem)
				.build();
	}

	private Missao construtorMissao(final String mensagem, final String autor, final LocalDateTime dataCriacao, final String titulo, final Igreja igreja){
		return Missao.builder()
				.mensagem(mensagem)
				.autor(autor)
				.dataCriado(dataCriacao)
				.titulo(titulo)
				.igreja(igreja)
				.build();

	}

	private PequenoGrupo construtorPG(final String endereco, final String responsavelCasa,
									  final Igreja igreja, final String diaSemanaAtividade,
									  final LocalTime horario){
		return PequenoGrupo.builder()
				.endereco(EnderecoPG.builder().logradouro(endereco).build())
				.responsavelCasa(responsavelCasa)
				.igreja(igreja)
				.diaSemanaAtividade(diaSemanaAtividade)
				.horaAtividade(horario)
				.build();
	}

	private EnderecoPG construtorEnderecoPG(final String logradouro, final String complemento,
											final String numero, final String bairro, final String cep,
											final PequenoGrupo pg, final Cidade cidade){
		return EnderecoPG.builder()
				.logradouro(logradouro)
				.complemento(complemento)
				.numero(numero)
				.bairro(bairro)
				.cep(cep)
				.pg(pg)
				.cidade(cidade)
				.build();
	}

	private AgendaEvento construtorAgendaEvento(final String titulo, final Igreja igreja, final String diaSemanaAtividade, final LocalTime horaAtividade,
												final boolean evento, final String descricao, final String link){
		return AgendaEvento.builder()
				.titulo(titulo)
				.igreja(igreja)
				.diaSemanaAtividade(diaSemanaAtividade)
				.horaAtividade(horaAtividade)
				.isEvento(evento)
				.descricao(descricao)
				.link(link)
				.build();
	}

	private OfertaServico construtorOfertaServico(final String nome, final Igreja igreja, final String descricao, final String endereco, final LocalDate dataCriacao){
		return OfertaServico.builder()
				.nome(nome)
				.igreja(igreja)
				.descricao(descricao)
				.endereco(endereco)
				.dataCriacao(dataCriacao)
				.build();
	}
}
