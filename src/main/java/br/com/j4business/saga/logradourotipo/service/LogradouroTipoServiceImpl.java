package br.com.j4business.saga.logradourotipo.service;

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
import br.com.j4business.saga.logradourotipo.model.LogradouroTipo;
import br.com.j4business.saga.logradourotipo.repository.LogradouroTipoRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.logradourotipo.model.LogradouroTipoForm;

@Service("logradouroTipoService")
public class LogradouroTipoServiceImpl implements LogradouroTipoService {

	@Autowired
	private LogradouroTipoRepository logradouroTipoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(LogradouroTipoService.class.getName());

	@Override
	public Page<LogradouroTipo> getLogradouroTipoAll(Pageable pageable) {
		return logradouroTipoRepository.findAll(pageable);
	}

	@Override
	public List<LogradouroTipo> getLogradouroTipoAll() {
		return logradouroTipoRepository.findAll();
	}

	@Override
	public LogradouroTipo getLogradouroTipoByLogradouroTipoPK(long logradouroTipoPK) {
		
		Optional<LogradouroTipo> logradouroTipo =  logradouroTipoRepository.findById(logradouroTipoPK);
		return logradouroTipo.get();
	}

	@Transactional
	public LogradouroTipo create(LogradouroTipoForm logradouroTipoForm) {
		
		LogradouroTipo logradouroTipo = new LogradouroTipo();
		
		logradouroTipo = this.converteLogradouroTipoForm(logradouroTipoForm);
		
		logradouroTipo = entityManager.merge(logradouroTipo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("LogradouroTipo Create " + "\n Usuário => " + username + 
										" // Id => "+logradouroTipo.getLogradouroTipoPK() + 
										" // LogradouroTipo => "+logradouroTipo.getLogradouroTipoNome() + 
										" // Descrição => "+ logradouroTipo.getLogradouroTipoDescricao());
		
		return logradouroTipo;
	}

	@Transactional
	public LogradouroTipo save(LogradouroTipoForm logradouroTipoForm) {
		
		LogradouroTipo logradouroTipo = new LogradouroTipo();
		
		logradouroTipo = this.converteLogradouroTipoForm(logradouroTipoForm);
		
		logradouroTipo = entityManager.merge(logradouroTipo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("LogradouroTipo Save " + "\n Usuário => " + username + 
										" // Id => "+logradouroTipo.getLogradouroTipoPK() + 
										" // LogradouroTipo => "+logradouroTipo.getLogradouroTipoNome() + 
										" // Descrição => "+ logradouroTipo.getLogradouroTipoDescricao());
		

		return logradouroTipo;
		
	}

	@Transactional
	public void delete(Long logradouroTipoId) {

		LogradouroTipo logradouroTipo = this.getLogradouroTipoByLogradouroTipoPK(logradouroTipoId);
		
		logradouroTipoRepository.delete(logradouroTipo);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("LogradouroTipo Delete " + "\n Usuário => " + username + 
										" // Id => "+logradouroTipo.getLogradouroTipoPK() + 
										" // LogradouroTipo => "+logradouroTipo.getLogradouroTipoNome() + 
										" // Descrição => "+ logradouroTipo.getLogradouroTipoDescricao());
		

    }

	@Transactional
	public LogradouroTipo converteLogradouroTipoForm(LogradouroTipoForm logradouroTipoForm) {
		
		LogradouroTipo logradouroTipo = new LogradouroTipo();
		
		if (logradouroTipoForm.getLogradouroTipoPK() > 0) {
			logradouroTipo = this.getLogradouroTipoByLogradouroTipoPK(logradouroTipoForm.getLogradouroTipoPK());
		}
		logradouroTipo.setLogradouroTipoNome(logradouroTipoForm.getLogradouroTipoNome().replaceAll("\\s+"," "));
		logradouroTipo.setLogradouroTipoDescricao(logradouroTipoForm.getLogradouroTipoDescricao().replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(logradouroTipoForm.getLogradouroTipoResponsavel()));

		logradouroTipo.setColaborador(colaborador);
		
		return logradouroTipo;
	}

	@Transactional
	public LogradouroTipoForm converteLogradouroTipo(LogradouroTipo logradouroTipo) {
	
		LogradouroTipoForm logradouroTipoForm = new LogradouroTipoForm();
		
		logradouroTipoForm.setLogradouroTipoPK(logradouroTipo.getLogradouroTipoPK());
		logradouroTipoForm.setLogradouroTipoNome(logradouroTipo.getLogradouroTipoNome());
		logradouroTipoForm.setLogradouroTipoDescricao(logradouroTipo.getLogradouroTipoDescricao());

		logradouroTipoForm.setLogradouroTipoResponsavel(logradouroTipo.getColaborador().getPessoaPK()+"");
		
	return logradouroTipoForm;
	}
	
	@Transactional
	public LogradouroTipoForm converteLogradouroTipoView(LogradouroTipo logradouroTipo) {
	
		LogradouroTipoForm logradouroTipoForm = new LogradouroTipoForm();
		
		logradouroTipoForm.setLogradouroTipoPK(logradouroTipo.getLogradouroTipoPK());
		logradouroTipoForm.setLogradouroTipoNome(logradouroTipo.getLogradouroTipoNome());
		logradouroTipoForm.setLogradouroTipoDescricao(logradouroTipo.getLogradouroTipoDescricao());

		logradouroTipoForm.setLogradouroTipoResponsavel(logradouroTipo.getColaborador().getPessoaNome());
		
	return logradouroTipoForm;
	}
	

	@Transactional
	public LogradouroTipoForm logradouroTipoParametros(LogradouroTipoForm logradouroTipoForm) {
	
		
		logradouroTipoForm.setLogradouroTipoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return logradouroTipoForm;
	}

	@Override
	public List<LogradouroTipo> getByLogradouroTipoDescricao(String logradouroTipoDescricao,Pageable pageable) {
		return logradouroTipoRepository.findByLogradouroTipoDescricao(logradouroTipoDescricao,pageable);
	}

	@Override
	public List<LogradouroTipo> getByLogradouroTipoNome(String logradouroTipoNome,Pageable pageable) {
		return logradouroTipoRepository.findByLogradouroTipoNome(logradouroTipoNome,pageable);
	}


	@Override
	public List<LogradouroTipo> getByLogradouroTipoDescricao(String logradouroTipoDescricao) {
		return logradouroTipoRepository.findByLogradouroTipoDescricao(logradouroTipoDescricao);
	}

	@Override
	public List<LogradouroTipo> getByLogradouroTipoNome(String logradouroTipoNome) {
		return logradouroTipoRepository.findByLogradouroTipoNome(logradouroTipoNome);
	}



}
