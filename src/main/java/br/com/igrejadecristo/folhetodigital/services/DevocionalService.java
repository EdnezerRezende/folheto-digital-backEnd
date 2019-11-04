package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.DevocionalNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Devocional;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.respositories.DevocionalRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;

@Service
public class DevocionalService {

	@Autowired
	private DevocionalRepository devocionalDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<Devocional> buscarTodos() {
		return devocionalDao.findAllByOrderByDataCriacao();
	}

	public List<Devocional> buscarPorIgreja(Integer idIgreja) {
		return devocionalDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public Devocional salvar(DevocionalNewDTO dto) {
		Boolean existe = false;

		if (dto.getId() == null) {
			existe = devocionalDao.existsByReferencia(dto.getReferencia());

			if (existe) {
				throw new RuntimeException("Ocorreu um erro, já existe o Devocional com essa referencia no sistema!");
			}
		}

		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		Devocional agenda = new Devocional(dto.getId(), dto.getReferencia(), igreja, dto.getDescricao() , LocalDate.parse(dto.getDataCriacao()));

		return devocionalDao.save(agenda);
	}

	public void deletar(Integer id) {
		devocionalDao.deleteById(id);
	}
}
