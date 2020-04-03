package br.com.igrejadecristo.folhetodigital.services;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.BoletimDTO;
import br.com.igrejadecristo.folhetodigital.dto.IgrejaInfoDTO;
import br.com.igrejadecristo.folhetodigital.dto.MissaoDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Aniversariante;
import br.com.igrejadecristo.folhetodigital.entidades.Devocional;
import br.com.igrejadecristo.folhetodigital.entidades.Missao;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException;
import net.sf.jasperreports.engine.JRException;

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
	
	public void gerarBoletimSemanal(Integer idIgreja, HttpServletResponse response) throws ClassNotFoundException, JRException, SQLException, IOException {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm").withLocale(new Locale("pt", "br"));
		LocalDateTime dataHoje = LocalDateTime.now();

		String dataBoletimGerado = obterDataGeracaoBoletim(dataHoje.toLocalDate());
		LocalDateTime dataBoletimInicio = LocalDateTime.parse(dataBoletimGerado + " 11:59",parser);
			
		LocalDateTime dataLimiteBusca = LocalDateTime.parse(dataBoletimGerado + " 00:00",parser).minusDays(6);
		
		BoletimDTO boletim = new BoletimDTO();
		
		IgrejaInfoDTO igreja = preencherIgreja(idIgreja, boletim);
		
		boletim.setDataBoletim(dataBoletimGerado);
		
		preencherMissao(dataBoletimInicio, dataLimiteBusca, boletim, igreja);
		
		preencherTextosDevocionais(idIgreja, dataBoletimInicio, dataLimiteBusca, boletim);
		
		preencherAniversariantes(idIgreja, dataBoletimInicio, dataLimiteBusca, boletim);
		
		preencherPgs(idIgreja, boletim);
		
		boletim.setMensagem(mensagemService.buscarMensagemPorIdIgrejaEDataCriado(idIgreja, dataBoletimInicio, dataLimiteBusca));
		
		gerarBoletim.gerar(boletim, response);
		
	}

	private void preencherPgs(Integer idIgreja, BoletimDTO boletim) {
		List<PequenoGrupo> pgs = pgService.buscarPorIgreja(idIgreja);
		if(pgs == null) {
			throw new ObjectNotFoundException("Não Existem PG´s. Necessário cadastrar. " + PequenoGrupo.class.getName());
		}
		
		List<String> pgsQuartaValores = new ArrayList<>();
		List<String> pgsQuintaValores = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
		
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

	private void preencherAniversariantes(Integer idIgreja, LocalDateTime dataHoje, LocalDateTime dataLimiteBusca,
			BoletimDTO boletim) {
		List<Aniversariante> aniversariantes = aniversarianteService.buscarAniversariantesPorIgrejaEDataNascimento(idIgreja, 
				dataLimiteBusca.toLocalDate(), dataHoje.toLocalDate());
		List<String> aniversariantesString = new ArrayList<>();
		for(Aniversariante aniversariante: aniversariantes) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
			aniversariantesString.add(aniversariante.getDataNascimento().format(formatter) +" - "+ aniversariante.getNome());
		}
		boletim.setAniversariantes(aniversariantesString);
	}

	private void preencherTextosDevocionais(Integer idIgreja, LocalDateTime dataHoje, LocalDateTime dataLimiteBusca,
			BoletimDTO boletim) {
		List<Devocional> devocionais = devocionalService.buscarPorIgrejaEDataCriacao(idIgreja, dataLimiteBusca.toLocalDate(), dataHoje.toLocalDate());
		
		if(devocionais == null) {
			throw new ObjectNotFoundException("Não Existe devocionais. Necessário cadastrar para este período. " + Devocional.class.getName());
		}
		
		List<String> devocionaisString = new ArrayList<>();
		int numeracao = 1;
		for(Devocional devocional: devocionais) {
			devocionaisString.add(numeracao + ") " + devocional.getReferencia() + " - " + devocional.getDescricao());
			numeracao++;
		}
		boletim.setTextosDevocionais(devocionaisString);
	}

	private IgrejaInfoDTO preencherIgreja(Integer idIgreja, BoletimDTO boletim) {
		IgrejaInfoDTO igreja = igrejaService.findById(idIgreja);
		boletim.setIgreja(igreja);
		return igreja;
	}

	private void preencherMissao(LocalDateTime dataHoje, LocalDateTime dataLimiteBusca, BoletimDTO boletim,
			IgrejaInfoDTO igreja) {
		Missao missao = missaoService.buscarMissaoPorIdIgrejaEDataCriado(igreja.getId(), dataLimiteBusca, dataHoje );
		if(missao == null) {
			throw new ObjectNotFoundException("Não Existe texto de Missão. Necessário cadastrar para este período. " + Missao.class.getName());
		}
		boletim.setMissao(new MissaoDTO(missao));
	}
	
	public String obterDataGeracaoBoletim(LocalDate dataHoje) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy").withLocale(new Locale("pt", "br"));
		
		DayOfWeek dayOfWeek = dataHoje.getDayOfWeek();
		
		dataHoje = validarEObterDataDomingo(dataHoje, dayOfWeek);
		
		return dataHoje.format(parser);
	}

	private LocalDate validarEObterDataDomingo(LocalDate dataHoje, DayOfWeek dayOfWeek) {
		if(dayOfWeek== DayOfWeek.MONDAY) {
			dataHoje = LocalDate.now().minusDays(1);
		} else if(dayOfWeek== DayOfWeek.TUESDAY) {
			dataHoje = dataHoje.minusDays(2);
		} else if(dayOfWeek== DayOfWeek.WEDNESDAY) {
			dataHoje = dataHoje.minusDays(3);
		} else if(dayOfWeek== DayOfWeek.THURSDAY) {
			dataHoje = dataHoje.minusDays(4);
		} else if(dayOfWeek== DayOfWeek.FRIDAY) {
			dataHoje = dataHoje.minusDays(5);
		} else if(dayOfWeek== DayOfWeek.SATURDAY) {
			dataHoje = dataHoje.minusDays(6);
		}
		return dataHoje;
	}
}
