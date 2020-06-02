package br.com.j4business.saga.cenario.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.model.CenarioForm;
import br.com.j4business.saga.cenario.repository.CenarioRepository;

@Service("cenarioService")
public class CenarioServiceImpl implements CenarioService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private CenarioRepository cenarioRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(CenarioService.class.getName());

	@Override
	public List<Cenario> getCenarioAll() {
		return cenarioRepository.findAll();
	}

	@Override
	public Page<Cenario> getCenarioAll(Pageable pageable) {
		return cenarioRepository.findAll(pageable);
	}

	@Override
	public Cenario getCenarioByCenarioPK(long cenarioPK) {
		
		return cenarioRepository.findOne(cenarioPK);
	}

	@Transactional
	public Cenario create(CenarioForm cenarioForm) {
		
		Cenario cenario = new Cenario();
		
		cenario = this.converteCenarioForm(cenarioForm);
		
		cenario = entityManager.merge(cenario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Cenario Create " + "\n Usuário => " + username + 
										" // Id => "+cenario.getCenarioPK() + 
										" // Cenario => "+cenario.getCenarioNome() + 
										" // Descrição => "+ cenario.getCenarioDescricao());
		
		return cenario;
	}

	@Transactional
	public Cenario save(CenarioForm cenarioForm) {
		
		Cenario cenario = new Cenario();
		
		cenario = this.converteCenarioForm(cenarioForm);
		
		cenario = entityManager.merge(cenario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Cenario Save " + "\n Usuário => " + username + 
										" // Id => "+cenario.getCenarioPK() + 
										" // Cenario => "+cenario.getCenarioNome() + 
										" // Descrição => "+ cenario.getCenarioDescricao());
		

		return cenario;
		
	}

	@Transactional
	public void delete(Long cenarioId) {

		Cenario cenario = this.getCenarioByCenarioPK(cenarioId);
		
		cenarioRepository.delete(cenario.getCenarioPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Cenario Delete " + "\n Usuário => " + username + 
										" // Id => "+cenario.getCenarioPK() + 
										" // Cenario => "+cenario.getCenarioNome() + 
										" // Descrição => "+ cenario.getCenarioDescricao());
		

    }

	@Transactional
	public Cenario converteCenarioForm(CenarioForm cenarioForm) {
		
		Cenario cenario = new Cenario();
		
		if (cenarioForm.getCenarioPK() > 0) {
			cenario = this.getCenarioByCenarioPK(cenarioForm.getCenarioPK());
		}
		
		cenario.setCenarioNome(cenarioForm.getCenarioNome().replaceAll("\\s+"," "));
		cenario.setCenarioDescricao(cenarioForm.getCenarioDescricao().replaceAll("\\s+"," "));

		cenario.setCenarioMotivoOperacao(cenarioForm.getCenarioMotivoOperacao().replaceAll("\\s+"," "));
		cenario.setCenarioStatus(cenarioForm.getCenarioStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(cenarioForm.getCenarioResponsavel()));

		cenario.setColaboradorResponsavel(colaborador);
		
		return cenario;
	}

	@Transactional
	public CenarioForm converteCenario(Cenario cenario) {
	
		CenarioForm cenarioForm = new CenarioForm();
		
		cenarioForm.setCenarioPK(cenario.getCenarioPK());
		cenarioForm.setCenarioNome(cenario.getCenarioNome());
		cenarioForm.setCenarioDescricao(cenario.getCenarioDescricao());

		cenarioForm.setCenarioMotivoOperacao(cenario.getCenarioMotivoOperacao());
		cenarioForm.setCenarioStatus(AtributoStatus.valueOf(cenario.getCenarioStatus()));

		cenarioForm.setCenarioResponsavel(cenario.getColaboradorResponsavel().getPessoaPK()+"");
		
	return cenarioForm;
	}
	
	@Transactional
	public CenarioForm converteCenarioView(Cenario cenario) {
	
		CenarioForm cenarioForm = new CenarioForm();
		
		cenarioForm.setCenarioPK(cenario.getCenarioPK());
		cenarioForm.setCenarioNome(cenario.getCenarioNome());
		cenarioForm.setCenarioDescricao(cenario.getCenarioDescricao());

		cenarioForm.setCenarioMotivoOperacao(cenario.getCenarioMotivoOperacao());
		cenarioForm.setCenarioStatus(AtributoStatus.valueOf(cenario.getCenarioStatus()));

		cenarioForm.setCenarioResponsavel(cenario.getColaboradorResponsavel().getPessoaNome());
		
	return cenarioForm;
	}
	

	@Transactional
	public CenarioForm cenarioParametros(CenarioForm cenarioForm) {
	
		
		cenarioForm.setCenarioStatus(AtributoStatus.valueOf("ATIVO"));

		
	return cenarioForm;
	}

	@Override
	public List<Cenario> getByCenarioDescricao(String cenarioDescricao,Pageable pageable) {
		return cenarioRepository.findByCenarioDescricao(cenarioDescricao,pageable);
	}

	@Override
	public List<Cenario> getByCenarioNome(String cenarioNome,Pageable pageable) {
		return cenarioRepository.findByCenarioNome(cenarioNome,pageable);
	}

	@Override
	public List<Cenario> getByCenarioDescricao(String cenarioDescricao) {
		return cenarioRepository.findByCenarioDescricao(cenarioDescricao);
	}

	@Override
	public List<Cenario> getByCenarioNome(String cenarioNome) {
		return cenarioRepository.findByCenarioNome(cenarioNome);
	}



}
