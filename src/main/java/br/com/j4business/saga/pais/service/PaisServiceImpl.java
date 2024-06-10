package br.com.j4business.saga.pais.service;

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
import br.com.j4business.saga.pais.model.Pais;
import br.com.j4business.saga.pais.model.PaisForm;
import br.com.j4business.saga.pais.repository.PaisRepository;

@Service("paisService")
public class PaisServiceImpl implements PaisService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(PaisService.class.getName());

	@Override
	public List<Pais> getPaisAll() {
		return paisRepository.findAll();
	}

	@Override
	public Page<Pais> getPaisAll(Pageable pageable) {
		return paisRepository.findAll(pageable);
	}

	@Override
	public Pais getPaisByPaisPK(long paisPK) {
		
		Optional<Pais> pais = paisRepository.findById(paisPK);
		return pais.get();
	}

	@Transactional
	public Pais create(PaisForm paisForm) {
		
		Pais pais = new Pais();
		
		pais.setPaisNome(paisForm.getPaisNome().replaceAll("  "," "));
		pais.setPaisCodigo(paisForm.getPaisCodigo());
		pais.setPaisSigla(paisForm.getPaisSigla());

		pais = entityManager.merge(pais);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Pais Create " + "\n Usuário => " + username + 
										" // Id => "+pais.getPaisPK() + 
										" // Pais => "+pais.getPaisNome() + 
										" // Descrição => "+ pais.getPaisSigla());
		
		return entityManager.merge(pais);
	}

	@Transactional
	public Pais save(Pais pais) {
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Pais Save " + "\n Usuário => " + username + 
										" // Id => "+pais.getPaisPK() + 
										" // Pais => "+pais.getPaisNome() + 
										" // Descrição => "+ pais.getPaisSigla());
		

		return entityManager.merge(pais);
		
	}

	@Transactional
	public void delete(Long paisId) {

		Pais pais = this.getPaisByPaisPK(paisId);
		
		paisRepository.delete(pais);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Pais Delete " + "\n Usuário => " + username + 
										" // Id => "+pais.getPaisPK() + 
										" // Pais => "+pais.getPaisNome() + 
										" // Descrição => "+ pais.getPaisSigla());
		

    }

	@Transactional
	public Pais convertePaisForm(PaisForm paisForm) {
		
		Pais pais = this.getPaisByPaisPK(paisForm.getPaisPK());
		pais.setPaisNome(paisForm.getPaisNome().replaceAll("\\s+"," "));
		pais.setPaisCodigo(paisForm.getPaisCodigo());
		pais.setPaisSigla(paisForm.getPaisSigla().replaceAll("\\s+"," "));

		return pais;
	}

	@Transactional
	public PaisForm convertePais(Pais pais) {
	
		PaisForm paisForm = new PaisForm();
		
		paisForm.setPaisPK(pais.getPaisPK());
		paisForm.setPaisNome(pais.getPaisNome());
		paisForm.setPaisSigla(pais.getPaisSigla());
		paisForm.setPaisCodigo(pais.getPaisCodigo());

	return paisForm;
	}
	
	@Transactional
	public PaisForm convertePaisView(Pais pais) {
	
		PaisForm paisForm = new PaisForm();
		
		paisForm.setPaisPK(pais.getPaisPK());
		paisForm.setPaisNome(pais.getPaisNome());
		paisForm.setPaisSigla(pais.getPaisSigla());
		paisForm.setPaisCodigo(pais.getPaisCodigo());

	return paisForm;
	}
	

	@Transactional
	public PaisForm paisParametros(PaisForm paisForm) {
	
		
		paisForm.setPaisStatus(AtributoStatus.valueOf("ATIVO"));

		
	return paisForm;
	}

	@Override
	public List<Pais> getByPaisSigla(String paisSigla,Pageable pageable) {
		return paisRepository.findByPaisSigla(paisSigla,pageable);
	}

	@Override
	public List<Pais> getByPaisNome(String paisNome,Pageable pageable) {
		return paisRepository.findByPaisNome(paisNome,pageable);
	}

	@Override
	public List<Pais> getByPaisSigla(String paisSigla) {
		return paisRepository.findByPaisSigla(paisSigla);
	}

	@Override
	public List<Pais> getByPaisNome(String paisNome) {
		return paisRepository.findByPaisNome(paisNome);
	}



}
