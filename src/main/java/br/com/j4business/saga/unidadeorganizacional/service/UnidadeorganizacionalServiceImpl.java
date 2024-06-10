package br.com.j4business.saga.unidadeorganizacional.service;

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
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.model.UnidadeorganizacionalForm;
import br.com.j4business.saga.unidadeorganizacional.repository.UnidadeorganizacionalRepository;

@Service("unidadeorganizacionalService")
public class UnidadeorganizacionalServiceImpl implements UnidadeorganizacionalService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UnidadeorganizacionalRepository unidadeorganizacionalRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(UnidadeorganizacionalService.class.getName());

	@Override
	public List<Unidadeorganizacional> getUnidadeorganizacionalAll() {
		return unidadeorganizacionalRepository.findAll();
	}

	@Override
	public Page<Unidadeorganizacional> getUnidadeorganizacionalAll(Pageable pageable) {
		return unidadeorganizacionalRepository.findAll(pageable);
	}

	@Override
	public Unidadeorganizacional getUnidadeorganizacionalByUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		
		Optional<Unidadeorganizacional> unidadeorganizacional = unidadeorganizacionalRepository.findById(unidadeorganizacionalPK);
		return unidadeorganizacional.get();
	}

	@Transactional
	public Unidadeorganizacional create(UnidadeorganizacionalForm unidadeorganizacionalForm) {
		
		Unidadeorganizacional unidadeorganizacional = new Unidadeorganizacional();
		
		unidadeorganizacional = this.converteUnidadeorganizacionalForm(unidadeorganizacionalForm);
		
		unidadeorganizacional = entityManager.merge(unidadeorganizacional);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Unidadeorganizacional Create " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacional.getUnidadeorganizacionalPK() + 
										" // Unidadeorganizacional => "+unidadeorganizacional.getUnidadeorganizacionalNome() + 
										" // Descrição => "+ unidadeorganizacional.getUnidadeorganizacionalDescricao());
		
		return unidadeorganizacional;
	}

	@Transactional
	public Unidadeorganizacional save(UnidadeorganizacionalForm unidadeorganizacionalForm) {
		
		Unidadeorganizacional unidadeorganizacional = new Unidadeorganizacional();
		
		unidadeorganizacional = this.converteUnidadeorganizacionalForm(unidadeorganizacionalForm);
		
		unidadeorganizacional = entityManager.merge(unidadeorganizacional);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Unidadeorganizacional Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacional.getUnidadeorganizacionalPK() + 
										" // Unidadeorganizacional => "+unidadeorganizacional.getUnidadeorganizacionalNome() + 
										" // Descrição => "+ unidadeorganizacional.getUnidadeorganizacionalDescricao());
		

		return entityManager.merge(unidadeorganizacional);
		
	}

	@Transactional
	public void delete(Long unidadeorganizacionalId) {

		Unidadeorganizacional unidadeorganizacional = this.getUnidadeorganizacionalByUnidadeorganizacionalPK(unidadeorganizacionalId);
		
		unidadeorganizacionalRepository.delete(unidadeorganizacional);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Unidadeorganizacional Delete " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacional.getUnidadeorganizacionalPK() + 
										" // Unidadeorganizacional => "+unidadeorganizacional.getUnidadeorganizacionalNome() + 
										" // Descrição => "+ unidadeorganizacional.getUnidadeorganizacionalDescricao());
		

    }

	@Transactional
	public Unidadeorganizacional converteUnidadeorganizacionalForm(UnidadeorganizacionalForm unidadeorganizacionalForm) {
		
		Unidadeorganizacional unidadeorganizacional = new Unidadeorganizacional();
		
		if (unidadeorganizacionalForm.getUnidadeorganizacionalPK() > 0) {
			unidadeorganizacional = this.getUnidadeorganizacionalByUnidadeorganizacionalPK(unidadeorganizacionalForm.getUnidadeorganizacionalPK());
		}
		unidadeorganizacional.setUnidadeorganizacionalNome(unidadeorganizacionalForm.getUnidadeorganizacionalNome().replaceAll("\\s+"," "));
		unidadeorganizacional.setUnidadeorganizacionalDescricao(unidadeorganizacionalForm.getUnidadeorganizacionalDescricao().replaceAll("\\s+"," "));

		unidadeorganizacional.setUnidadeorganizacionalMotivoOperacao(unidadeorganizacionalForm.getUnidadeorganizacionalMotivoOperacao().replaceAll("\\s+"," "));
		unidadeorganizacional.setUnidadeorganizacionalStatus(unidadeorganizacionalForm.getUnidadeorganizacionalStatus()+"".replaceAll("\\s+"," "));
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(unidadeorganizacionalForm.getUnidadeorganizacionalResponsavel()));
		unidadeorganizacional.setColaboradorResponsavel(colaborador);
		
		return unidadeorganizacional;
	}

	@Transactional
	public UnidadeorganizacionalForm converteUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
	
		UnidadeorganizacionalForm unidadeorganizacionalForm = new UnidadeorganizacionalForm();
		
		unidadeorganizacionalForm.setUnidadeorganizacionalPK(unidadeorganizacional.getUnidadeorganizacionalPK());
		unidadeorganizacionalForm.setUnidadeorganizacionalNome(unidadeorganizacional.getUnidadeorganizacionalNome());
		unidadeorganizacionalForm.setUnidadeorganizacionalDescricao(unidadeorganizacional.getUnidadeorganizacionalDescricao());

		unidadeorganizacionalForm.setUnidadeorganizacionalMotivoOperacao(unidadeorganizacional.getUnidadeorganizacionalMotivoOperacao());
		unidadeorganizacionalForm.setUnidadeorganizacionalStatus(AtributoStatus.valueOf(unidadeorganizacional.getUnidadeorganizacionalStatus()));
		unidadeorganizacionalForm.setUnidadeorganizacionalResponsavel(unidadeorganizacional.getColaboradorResponsavel().getPessoaPK()+"");
		
	return unidadeorganizacionalForm;
	}
	
	@Transactional
	public UnidadeorganizacionalForm converteUnidadeorganizacionalView(Unidadeorganizacional unidadeorganizacional) {
	
		UnidadeorganizacionalForm unidadeorganizacionalForm = new UnidadeorganizacionalForm();
		
		unidadeorganizacionalForm.setUnidadeorganizacionalPK(unidadeorganizacional.getUnidadeorganizacionalPK());
		unidadeorganizacionalForm.setUnidadeorganizacionalNome(unidadeorganizacional.getUnidadeorganizacionalNome());
		unidadeorganizacionalForm.setUnidadeorganizacionalDescricao(unidadeorganizacional.getUnidadeorganizacionalDescricao());

		unidadeorganizacionalForm.setUnidadeorganizacionalMotivoOperacao(unidadeorganizacional.getUnidadeorganizacionalMotivoOperacao());
		unidadeorganizacionalForm.setUnidadeorganizacionalStatus(AtributoStatus.valueOf(unidadeorganizacional.getUnidadeorganizacionalStatus()));
		unidadeorganizacionalForm.setUnidadeorganizacionalResponsavel(unidadeorganizacional.getColaboradorResponsavel().getPessoaNome());
		
	return unidadeorganizacionalForm;
	}
	

	@Transactional
	public UnidadeorganizacionalForm unidadeorganizacionalParametros(UnidadeorganizacionalForm unidadeorganizacionalForm) {
		unidadeorganizacionalForm.setUnidadeorganizacionalStatus(AtributoStatus.valueOf("ATIVO"));
	return unidadeorganizacionalForm;
	}

	@Override
	public List<Unidadeorganizacional> getByUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao,Pageable pageable) {
		return unidadeorganizacionalRepository.findByUnidadeorganizacionalDescricao(unidadeorganizacionalDescricao,pageable);
	}

	@Override
	public List<Unidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable) {
		return unidadeorganizacionalRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome,pageable);
	}

	@Override
	public List<Unidadeorganizacional> getByUnidadeorganizacionalDescricao(String unidadeorganizacionalDescricao) {
		return unidadeorganizacionalRepository.findByUnidadeorganizacionalDescricao(unidadeorganizacionalDescricao);
	}

	@Override
	public List<Unidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		return unidadeorganizacionalRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome);
	}



}
