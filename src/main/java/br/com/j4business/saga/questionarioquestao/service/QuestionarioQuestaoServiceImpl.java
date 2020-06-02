package br.com.j4business.saga.questionarioquestao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processovideo.model.ProcessoVideo;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestao;
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.service.QuestionarioService;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestaoForm;
import br.com.j4business.saga.questionarioquestao.repository.QuestionarioQuestaoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("questionarioQuestaoService")
public class QuestionarioQuestaoServiceImpl implements QuestionarioQuestaoService {

	@Autowired
	private QuestionarioService questionarioService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private QuestionarioQuestaoRepository questionarioQuestaoRepository;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(QuestionarioQuestaoService.class.getName());


	@Override
	public List<QuestionarioQuestao> getByQuestionarioNome(String questionarioNome,Pageable pageable) {
		return questionarioQuestaoRepository.findByQuestionarioNome(questionarioNome,pageable);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestaoNome(String questaoNome,Pageable pageable) {
		return questionarioQuestaoRepository.findByQuestaoNome(questaoNome,pageable);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestionarioNome(String questionarioNome) {
		return questionarioQuestaoRepository.findByQuestionarioNome(questionarioNome);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestaoNome(String questaoNome) {
		return questionarioQuestaoRepository.findByQuestaoNome(questaoNome);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestaoPK(long questaoPK,Pageable pageable) {
		return questionarioQuestaoRepository.findByQuestaoPK(questaoPK,pageable);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestionarioPK(long questionarioPK,Pageable pageable) {
		return questionarioQuestaoRepository.findByQuestionarioPK(questionarioPK,pageable);
	}

	@Override
	public List<QuestionarioQuestao> getByQuestionarioPK(long questionarioPK) {
		return questionarioQuestaoRepository.findByQuestionarioPK(questionarioPK);
	}

	@Override
	public List<QuestionarioQuestao> getQuestionarioQuestaoAll(Pageable pageable) {
		return questionarioQuestaoRepository.findQuestionarioQuestaoAll(pageable);
	}

	@Override
	public QuestionarioQuestao getQuestionarioQuestaoByQuestionarioQuestaoPK(long questionarioQuestaoPK) {
		return questionarioQuestaoRepository.findOne(questionarioQuestaoPK);
	}

	@Override
	public QuestionarioQuestao getByQuestionarioAndQuestao (Questionario questionario,Questao questao) {
		
		return questionarioQuestaoRepository.findByQuestionarioAndQuestao(questionario,questao);
	}

	@Transactional
	public QuestionarioQuestao create(QuestionarioQuestaoForm questionarioQuestaoForm) {
		
		QuestionarioQuestao questionarioQuestao = new QuestionarioQuestao();
		
		questionarioQuestao = this.converteQuestionarioQuestaoForm(questionarioQuestaoForm);
		
		questionarioQuestao = entityManager.merge(questionarioQuestao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questao Create " + "\n Usuário => " + username + 
				" // Id => "+questionarioQuestao.getQuestionarioQuestaoPK() + 
				" // Questionario Id => "+questionarioQuestao.getQuestionario().getQuestionarioPK() + 
				" // Questao Id => "+questionarioQuestao.getQuestao().getQuestaoPK() + 
				" // Valor => "+questionarioQuestao.getQuestionarioQuestaoSequencia()); 
		
		return questionarioQuestao;
	}

	@Transactional
	public QuestionarioQuestao save(QuestionarioQuestaoForm questionarioQuestaoForm) {

		QuestionarioQuestao questionarioQuestao = new QuestionarioQuestao();
		
		questionarioQuestao = this.converteQuestionarioQuestaoForm(questionarioQuestaoForm);
		
		questionarioQuestao = entityManager.merge(questionarioQuestao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("QuestionarioQuestao Save " + "\n Usuário => " + username + 
										" // Id => "+questionarioQuestao.getQuestionarioQuestaoPK() + 
										" // Questionario Id => "+questionarioQuestao.getQuestionario().getQuestionarioPK() + 
										" // Questao Id => "+questionarioQuestao.getQuestao().getQuestaoPK() + 
										" // Valor => "+questionarioQuestao.getQuestionarioQuestaoSequencia()); 
		return questionarioQuestao;
	}

	@Transactional
	public void delete(Long questionarioQuestaoPK) {

		QuestionarioQuestao questionarioQuestaoTemp = this.getQuestionarioQuestaoByQuestionarioQuestaoPK(questionarioQuestaoPK);

		questionarioQuestaoRepository.delete(questionarioQuestaoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("QuestionarioQuestao Save " + "\n Usuário => " + username + 
										" // Id => "+questionarioQuestaoTemp.getQuestionarioQuestaoPK() + 
										" // Questionario Id => "+questionarioQuestaoTemp.getQuestionario().getQuestionarioPK() + 
										" // Questao Id => "+questionarioQuestaoTemp.getQuestao().getQuestaoPK() + 
										" // Valor => "+questionarioQuestaoTemp.getQuestionarioQuestaoSequencia()); 
    }

	@Transactional
	public void deleteByQuestao(Questao questao) {
		
		List<QuestionarioQuestao> questionarioQuestaoList = questionarioQuestaoRepository.findByQuestao(questao);

		questionarioQuestaoRepository.delete(questionarioQuestaoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		questionarioQuestaoList.forEach((QuestionarioQuestao questionarioQuestao) -> {						
			logger.info("QuestionarioQuestao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+questionarioQuestao.getQuestionarioQuestaoPK() + 
											" // Questionario Id => "+questionarioQuestao.getQuestionario().getQuestionarioPK() + 
											" // Questao Id => "+questionarioQuestao.getQuestao().getQuestaoPK() + 
											" // Valor => "+questionarioQuestao.getQuestionarioQuestaoSequencia()); 
		});
    }

	@Transactional
	public QuestionarioQuestao converteQuestionarioQuestaoForm(QuestionarioQuestaoForm questionarioQuestaoForm) {
		
		QuestionarioQuestao questionarioQuestao = new QuestionarioQuestao();
		
		if (questionarioQuestaoForm.getQuestionarioQuestaoPK() > 0) {
			questionarioQuestao = this.getQuestionarioQuestaoByQuestionarioQuestaoPK(questionarioQuestaoForm.getQuestionarioQuestaoPK());
		}
		
		Questao questao = questaoService.getQuestaoByQuestaoPK(Long.parseLong(questionarioQuestaoForm.getQuestaoNome()));
		questionarioQuestao.setQuestao(questao);

		Questionario questionario = questionarioService.getQuestionarioByQuestionarioPK(Long.parseLong(questionarioQuestaoForm.getQuestionarioNome()));
		questionarioQuestao.setQuestionario(questionario);

		questionarioQuestao.setQuestionarioQuestaoSequencia(Integer.parseInt(questionarioQuestaoForm.getQuestionarioQuestaoSequencia()+"".replaceAll("\\s+"," ")));

		questionarioQuestao.setQuestionarioQuestaoMotivoOperacao(questionarioQuestaoForm.getQuestionarioQuestaoMotivoOperacao().replaceAll("\\s+"," "));
		questionarioQuestao.setQuestionarioQuestaoStatus(questionarioQuestaoForm.getQuestionarioQuestaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(questionarioQuestaoForm.getQuestionarioQuestaoResponsavel()));
		questionarioQuestao.setColaboradorResponsavel(colaborador);
		
		return questionarioQuestao;
	}

	@Transactional
	public QuestionarioQuestaoForm converteQuestionarioQuestao(QuestionarioQuestao questionarioQuestao) {
	
		QuestionarioQuestaoForm questionarioQuestaoForm = new QuestionarioQuestaoForm();
		
		questionarioQuestaoForm.setQuestionarioQuestaoPK(questionarioQuestao.getQuestionarioQuestaoPK());
		questionarioQuestaoForm.setQuestionarioNome(questionarioQuestao.getQuestionario().getQuestionarioPK()+"");
		questionarioQuestaoForm.setQuestaoNome(questionarioQuestao.getQuestao().getQuestaoPK()+"");
		questionarioQuestaoForm.setQuestionarioQuestaoSequencia(questionarioQuestao.getQuestionarioQuestaoSequencia()+"");

		questionarioQuestaoForm.setQuestionarioQuestaoMotivoOperacao(questionarioQuestao.getQuestionarioQuestaoMotivoOperacao());
		questionarioQuestaoForm.setQuestionarioQuestaoStatus(AtributoStatus.valueOf(questionarioQuestao.getQuestionarioQuestaoStatus()));

		questionarioQuestaoForm.setQuestionarioQuestaoResponsavel(questionarioQuestao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return questionarioQuestaoForm;
	}
	
	@Transactional
	public QuestionarioQuestaoForm converteQuestionarioQuestaoView(QuestionarioQuestao questionarioQuestao) {
	
		QuestionarioQuestaoForm questionarioQuestaoForm = new QuestionarioQuestaoForm();
		
		questionarioQuestaoForm.setQuestionarioQuestaoPK(questionarioQuestao.getQuestionarioQuestaoPK());
		questionarioQuestaoForm.setQuestionarioNome(questionarioQuestao.getQuestionario().getQuestionarioNome());
		questionarioQuestaoForm.setQuestaoNome(questionarioQuestao.getQuestao().getQuestaoNome());
		questionarioQuestaoForm.setQuestionarioQuestaoSequencia(questionarioQuestao.getQuestionarioQuestaoSequencia()+"");

		questionarioQuestaoForm.setQuestionarioQuestaoMotivoOperacao(questionarioQuestao.getQuestionarioQuestaoMotivoOperacao());
		questionarioQuestaoForm.setQuestionarioQuestaoStatus(AtributoStatus.valueOf(questionarioQuestao.getQuestionarioQuestaoStatus()));

		questionarioQuestaoForm.setQuestionarioQuestaoResponsavel(questionarioQuestao.getColaboradorResponsavel().getPessoaNome());
		
	return questionarioQuestaoForm;
	}
	

	@Transactional
	public QuestionarioQuestaoForm questionarioQuestaoParametros(QuestionarioQuestaoForm questionarioQuestaoForm) {
	
		questionarioQuestaoForm.setQuestionarioQuestaoStatus(AtributoStatus.valueOf("ATIVO"));
		
	return questionarioQuestaoForm;
	}
	

}
