package br.com.j4business.saga.municipio.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.municipio.model.Municipio;
import br.com.j4business.saga.municipio.model.MunicipioForm;
import br.com.j4business.saga.municipio.repository.MunicipioRepository;

@Service("municipioService")
public class MunicipioServiceImpl implements MunicipioService {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(MunicipioService.class.getName());

	@Override
	public List<Municipio> getMunicipioAll() {
		return municipioRepository.findAll();
	}

	@Override
	public Page<Municipio> getMunicipioAll(Pageable pageable) {
		return municipioRepository.findAll(pageable);
	}

	@Override
	public Municipio getMunicipioByMunicipioPK(long municipioPK) {
		
		Optional<Municipio> municipio = municipioRepository.findById(municipioPK);
		return municipio.get();
	}

	@Transactional
	public Municipio create(MunicipioForm municipioForm) {
		
		Municipio municipio = new Municipio();
		
		municipio.setMunicipioNome(municipioForm.getMunicipioNome().replaceAll("  "," "));
		municipio.setMunicipioCEP(municipioForm.getMunicipioCEP());

		municipio = entityManager.merge(municipio);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Municipio Create " + "\n Usuário => " + username + 
										" // Id => "+municipio.getMunicipioPK() + 
										" // Municipio => "+municipio.getMunicipioNome() + 
										" // Descrição => "+ municipio.getMunicipioCEP());
		
		return entityManager.merge(municipio);
	}

	@Transactional
	public Municipio save(Municipio municipio) {
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Municipio Save " + "\n Usuário => " + username + 
										" // Id => "+municipio.getMunicipioPK() + 
										" // Municipio => "+municipio.getMunicipioNome() + 
										" // Descrição => "+ municipio.getMunicipioCEP());
		

		return entityManager.merge(municipio);
		
	}

	@Transactional
	public void delete(Long municipioId) {

		Municipio municipio = this.getMunicipioByMunicipioPK(municipioId);
		
		municipioRepository.delete(municipio);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Municipio Delete " + "\n Usuário => " + username + 
										" // Id => "+municipio.getMunicipioPK() + 
										" // Municipio => "+municipio.getMunicipioNome() + 
										" // Descrição => "+ municipio.getMunicipioCEP());
		

    }

	@Transactional
	public Municipio converteMunicipioForm(MunicipioForm municipioForm) {
		
		Municipio municipio = this.getMunicipioByMunicipioPK(municipioForm.getMunicipioPK());
		municipio.setMunicipioNome(municipioForm.getMunicipioNome().replaceAll("\\s+"," "));
		municipio.setMunicipioCEP(municipioForm.getMunicipioCEP().replaceAll("\\s+"," "));

		return municipio;
	}

	@Transactional
	public MunicipioForm converteMunicipio(Municipio municipio) {
	
		MunicipioForm municipioForm = new MunicipioForm();
		
		municipioForm.setMunicipioPK(municipio.getMunicipioPK());
		municipioForm.setMunicipioNome(municipio.getMunicipioNome());
		municipioForm.setMunicipioCEP(municipio.getMunicipioCEP());

	return municipioForm;
	}
	
	@Transactional
	public MunicipioForm converteMunicipioView(Municipio municipio) {
	
		MunicipioForm municipioForm = new MunicipioForm();
		
		municipioForm.setMunicipioPK(municipio.getMunicipioPK());
		municipioForm.setMunicipioNome(municipio.getMunicipioNome());
		municipioForm.setMunicipioCEP(municipio.getMunicipioCEP());

	return municipioForm;
	}
	

	@Transactional
	public MunicipioForm municipioParametros(MunicipioForm municipioForm) {
	
		
		municipioForm.setMunicipioStatus(AtributoStatus.valueOf("ATIVO"));

		
	return municipioForm;
	}

	@Override
	public List<Municipio> getByMunicipioNome(String municipioNome,Pageable pageable) {
		return municipioRepository.findByMunicipioNome(municipioNome,pageable);
	}

	@Override
	public List<Municipio> getByMunicipioNome(String municipioNome) {
		return municipioRepository.findByMunicipioNome(municipioNome);
	}

	@Override
	public List<Municipio> getByMunicipioCEP(String municipioCEP) {
		return municipioRepository.findByMunicipioCEP(municipioCEP);
	}

	@Override
	public List<Municipio> getByMunicipioCEP(String municipioCEP,Pageable pageable) {
		return municipioRepository.findByMunicipioCEP(municipioCEP,pageable);
	}

	@Override
	public List<Municipio> getByMunicipioUF(String municipioUF) {
		return municipioRepository.findByMunicipioUF(municipioUF);
	}

	@Override
	public List<Municipio> getByMunicipioUF(String municipioUF,Pageable pageable) {
		return municipioRepository.findByMunicipioUF(municipioUF,pageable);
	}



}
