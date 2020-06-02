package br.com.j4business.saga.questionario.service;

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
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.repository.QuestionarioRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.questionario.model.QuestionarioForm;
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.service.CenarioService;

@Service("questionarioService")
public class QuestionarioServiceImpl implements QuestionarioService {

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private QuestionarioRepository questionarioRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(QuestionarioService.class.getName());

	@Override
	public Page<Questionario> getQuestionarioAll(Pageable pageable) {
		return questionarioRepository.findAll(pageable);
	}

	@Override
	public List<Questionario> getQuestionarioAll() {
		return questionarioRepository.findAll();
	}

	@Override
	public Questionario getQuestionarioByQuestionarioPK(long questionarioPK) {
		
		return questionarioRepository.findOne(questionarioPK);
	}

	@Transactional
	public Questionario create(QuestionarioForm questionarioForm) {
		
		Questionario questionario = new Questionario();
		
		questionario = this.converteQuestionarioForm(questionarioForm);
		
		questionario = entityManager.merge(questionario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questionario Create " + "\n Usuário => " + username + 
										" // Id => "+questionario.getQuestionarioPK() + 
										" // Questionario => "+questionario.getQuestionarioNome() + 
										" // Descrição => "+ questionario.getQuestionarioDescricao());
		
		return questionario;
	}

	@Transactional
	public Questionario save(QuestionarioForm questionarioForm) {
		
		Questionario questionario = new Questionario();
		
		questionario = this.converteQuestionarioForm(questionarioForm);
		
		questionario = entityManager.merge(questionario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questionario Save " + "\n Usuário => " + username + 
										" // Id => "+questionario.getQuestionarioPK() + 
										" // Questionario => "+questionario.getQuestionarioNome() + 
										" // Descrição => "+ questionario.getQuestionarioDescricao());
		

		return questionario;
		
	}

	@Transactional
	public void delete(Long questionarioId) {

		Questionario questionario = this.getQuestionarioByQuestionarioPK(questionarioId);
		
		questionarioRepository.delete(questionario.getQuestionarioPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questionario Delete " + "\n Usuário => " + username + 
										" // Id => "+questionario.getQuestionarioPK() + 
										" // Questionario => "+questionario.getQuestionarioNome() + 
										" // Descrição => "+ questionario.getQuestionarioDescricao());
		

    }

	@Transactional
	public Questionario converteQuestionarioForm(QuestionarioForm questionarioForm) {
		
		Questionario questionario = new Questionario();
		
		if (questionarioForm.getQuestionarioPK() > 0) {
			questionario = this.getQuestionarioByQuestionarioPK(questionarioForm.getQuestionarioPK());
		}
		questionario.setQuestionarioNome(questionarioForm.getQuestionarioNome().replaceAll("\\s+"," "));
		questionario.setQuestionarioDescricao(questionarioForm.getQuestionarioDescricao().replaceAll("\\s+"," "));

		Cenario cenario = cenarioService.getCenarioByCenarioPK(Long.parseLong(questionarioForm.getQuestionarioCenario()));
		questionario.setCenario(cenario);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(questionarioForm.getQuestionarioResponsavel()));
		questionario.setColaboradorResponsavel(colaborador);
		
		questionario.setQuestionarioMotivoOperacao(questionarioForm.getQuestionarioMotivoOperacao().replaceAll("\\s+"," "));
		questionario.setQuestionarioStatus(questionarioForm.getQuestionarioStatus()+"".replaceAll("\\s+"," "));
		
		return questionario;
	}

	@Transactional
	public QuestionarioForm converteQuestionario(Questionario questionario) {
	
		QuestionarioForm questionarioForm = new QuestionarioForm();
		
		questionarioForm.setQuestionarioPK(questionario.getQuestionarioPK());
		questionarioForm.setQuestionarioNome(questionario.getQuestionarioNome());
		questionarioForm.setQuestionarioDescricao(questionario.getQuestionarioDescricao());

		questionarioForm.setQuestionarioCenario(questionario.getCenario().getCenarioPK()+"");
		questionarioForm.setQuestionarioResponsavel(questionario.getColaboradorResponsavel().getPessoaPK()+"");
		questionarioForm.setQuestionarioMotivoOperacao(questionario.getQuestionarioMotivoOperacao());
		questionarioForm.setQuestionarioStatus(AtributoStatus.valueOf(questionario.getQuestionarioStatus()));

	return questionarioForm;
	}
	
	@Transactional
	public QuestionarioForm converteQuestionarioView(Questionario questionario) {
	
		QuestionarioForm questionarioForm = new QuestionarioForm();
		
		questionarioForm.setQuestionarioPK(questionario.getQuestionarioPK());
		questionarioForm.setQuestionarioNome(questionario.getQuestionarioNome());
		questionarioForm.setQuestionarioDescricao(questionario.getQuestionarioDescricao());

		questionarioForm.setQuestionarioCenario(questionario.getCenario().getCenarioNome());
		questionarioForm.setQuestionarioResponsavel(questionario.getColaboradorResponsavel().getPessoaNome());
		questionarioForm.setQuestionarioMotivoOperacao(questionario.getQuestionarioMotivoOperacao());
		questionarioForm.setQuestionarioStatus(AtributoStatus.valueOf(questionario.getQuestionarioStatus()));
		
	return questionarioForm;
	}
	

	@Transactional
	public QuestionarioForm questionarioParametros(QuestionarioForm questionarioForm) {
	
		questionarioForm.setQuestionarioStatus(AtributoStatus.valueOf("ATIVO"));
		
	return questionarioForm;
	}

	@Override
	public List<Questionario> getByQuestionarioDescricao(String questionarioDescricao,Pageable pageable) {
		return questionarioRepository.findByQuestionarioDescricao(questionarioDescricao,pageable);
	}

	@Override
	public List<Questionario> getByQuestionarioNome(String questionarioNome,Pageable pageable) {
		return questionarioRepository.findByQuestionarioNome(questionarioNome,pageable);
	}

	@Override
	public List<Questionario> getByCenarioNome(String cenarioNome,Pageable pageable) {
		return questionarioRepository.findByCenarioNome(cenarioNome,pageable);
	}


	@Override
	public List<Questionario> getByQuestionarioDescricao(String questionarioDescricao) {
		return questionarioRepository.findByQuestionarioDescricao(questionarioDescricao);
	}

	@Override
	public List<Questionario> getByQuestionarioNome(String questionarioNome) {
		return questionarioRepository.findByQuestionarioNome(questionarioNome);
	}

	@Override
	public List<Questionario> getByCenarioNome(String cenarioNome) {
		return questionarioRepository.findByCenarioNome(cenarioNome);
	}



}
