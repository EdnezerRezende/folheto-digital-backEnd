package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.BoletimDTO;
import br.com.pic.folheto.dto.IgrejaInfoDTO;
import br.com.pic.folheto.dto.MissaoDTO;
import br.com.pic.folheto.entidades.Aniversariante;
import br.com.pic.folheto.entidades.Devocional;
import br.com.pic.folheto.entidades.Missao;
import br.com.pic.folheto.entidades.PequenoGrupo;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import br.com.pic.folheto.util.DataUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BoletimService {

	@Autowired
	private IgrejaService igrejaService;
	
	@Autowired
	private MissaoService missaoService;
	
	@Autowired
	private DevocionalService devocionalService;
	
	@Autowired
	private AniversarianteService aniversarianteService;
	
	@Autowired
	private PGService pgService;
	
	@Autowired
	private MensagemService mensagemService;
	
	@Autowired
	private GeraFolheto gerarBoletim;
	
	public void gerarBoletimSemanal(final Integer idIgreja, final HttpServletResponse response) throws ClassNotFoundException, JRException, SQLException, IOException {
		final DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm").withLocale(new Locale("pt", "br"));
		final LocalDateTime dataHoje = LocalDateTime.now();

		final String dataBoletimGerado = DataUtil.obterDataGeracaoBoletim(dataHoje.toLocalDate());
		final LocalDateTime dataBoletimInicio = DataUtil.converterStringLocalDateTime(dataBoletimGerado + " 00:00","d 'de' MMMM 'de' yyyy HH:mm");
			
		final LocalDateTime dataLimiteBusca = DataUtil.converterStringLocalDateTime(dataBoletimGerado + " 11:59","d 'de' MMMM 'de' yyyy HH:mm").plusDays(6);
		
		final BoletimDTO boletim = BoletimDTO.builder()
				.dataBoletim(dataBoletimGerado)
				.build();

		final IgrejaInfoDTO igreja = preencherIgreja(idIgreja, boletim);
		
		preencherMissao(dataBoletimInicio, dataLimiteBusca, boletim, igreja);
		
		preencherTextosDevocionais(idIgreja, dataBoletimInicio, dataLimiteBusca, boletim);
		
		preencherAniversariantes(idIgreja, dataBoletimInicio, dataLimiteBusca, boletim);
		
		preencherPgs(idIgreja, boletim);
		
		boletim.setMensagem(mensagemService.buscarMensagemPorIdIgrejaEDataCriado(idIgreja, dataBoletimInicio, dataLimiteBusca));
		
		gerarBoletim.gerar(boletim, response);
		
	}

	private void preencherPgs(final Integer idIgreja, final BoletimDTO boletim) {
		final List<PequenoGrupo> pgs = pgService.buscarPorIgreja(idIgreja);
		if(pgs == null) {
			throw new ObjectNotFoundException("Não Existem PG´s. Necessário cadastrar. " + PequenoGrupo.class.getName());
		}
		
		final List<String> pgsQuartaValores = new ArrayList<>();
		final List<String> pgsQuintaValores = new ArrayList<>();
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
		
		for(PequenoGrupo pg: pgs) {
			if(pg.getDiaSemanaAtividade().equals("Quarta-Feira")) {
				pgsQuartaValores.add("*"+pg.getResponsavelCasa() + " - "+ 
						pg.getHoraAtividade().format(formatter) + "h \n" + pg.getEndereco().getLogradouro() +", " 
						+pg.getEndereco().getNumero() + pg.getEndereco().getComplemento() + pg.getEndereco().getBairro() + "\n"
						+pg.getEndereco().getCidade().getNome() + " \n Líder: " +pg.getLider() 
				);
			}else if(pg.getDiaSemanaAtividade().equals("Quinta-Feira")){
				pgsQuintaValores.add("*"+pg.getResponsavelCasa() + " - "+ 
						pg.getHoraAtividade().format(formatter) + "h \n" + pg.getEndereco().getLogradouro() +", " 
						+pg.getEndereco().getNumero() + pg.getEndereco().getComplemento() + pg.getEndereco().getBairro() + "\n"
						+pg.getEndereco().getCidade().getNome() + " \n Líder: " +pg.getLider() 
				);
			}
		}
		boletim.setPgsQuartaValores(pgsQuartaValores);
		boletim.setPgsQuintaValores(pgsQuintaValores);
		
	}

	private void preencherAniversariantes(final Integer idIgreja, final LocalDateTime dataHoje, final LocalDateTime dataLimiteBusca,
		final BoletimDTO boletim) {
		final List<Aniversariante> aniversariantes = aniversarianteService.buscarAniversariantesPorIgrejaEDataNascimento(idIgreja,
				 dataHoje.toLocalDate(), dataLimiteBusca.toLocalDate());
		final List<String> aniversariantesString = new ArrayList<>();
		for(Aniversariante aniversariante: aniversariantes) {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
			aniversariantesString.add(aniversariante.getDataNascimento().format(formatter) +" - "+ aniversariante.getNome());
		}
		boletim.setAniversariantes(aniversariantesString);
	}

	private void preencherTextosDevocionais(final Integer idIgreja,final LocalDateTime dataHoje,final LocalDateTime dataLimiteBusca,
			final BoletimDTO boletim) {
		final List<Devocional> devocionais = devocionalService.buscarPorIgrejaEDataCriacao(idIgreja, dataHoje.toLocalDate(), dataLimiteBusca.toLocalDate());
		
		if(devocionais == null) {
			throw new ObjectNotFoundException("Não Existe devocionais. Necessário cadastrar para este período. " + Devocional.class.getName());
		}
		
		final List<String> devocionaisString = new ArrayList<>();
		int numeracao = 1;

		for(Devocional devocional: devocionais) {
			devocionaisString.add(numeracao + ") " + devocional.getReferencia() + " - " + devocional.getDescricao());
			numeracao++;
		}
		boletim.setTextosDevocionais(devocionaisString);
	}

	private IgrejaInfoDTO preencherIgreja(final Integer idIgreja,final BoletimDTO boletim) {
		final IgrejaInfoDTO igreja = igrejaService.findById(idIgreja);
		boletim.setIgreja(igreja);
		return igreja;
	}

	private void preencherMissao(final LocalDateTime dataHoje,final LocalDateTime dataLimiteBusca, final BoletimDTO boletim,
			final IgrejaInfoDTO igreja) {
		final List<Missao> missoes = missaoService.buscarMissaoPorIdIgrejaEDataCriado(igreja.getId(), dataHoje, dataLimiteBusca );

		if(missoes.size() == 0) {
			throw new ObjectNotFoundException("Não Existe texto de Missão. Necessário cadastrar para este período. " + Missao.class.getName());
		}

		boletim.setMissao(missoes.stream().map(missao ->
			MissaoDTO.builder()
					.id(missao.getId())
					.mensagem(missao.getMensagem())
					.autor(missao.getAutor())
					.titulo(missao.getTitulo())
					.build()
		).collect(Collectors.toList()));

	}


}
