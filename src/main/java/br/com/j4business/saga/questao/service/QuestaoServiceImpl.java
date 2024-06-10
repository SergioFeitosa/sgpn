package br.com.j4business.saga.questao.service;

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
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questao.repository.QuestaoRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.questao.model.QuestaoForm;

@Service("questaoService")
public class QuestaoServiceImpl implements QuestaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ElementoService elementoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private QuestaoRepository questaoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(QuestaoService.class.getName());

	@Override
	public Page<Questao> getQuestaoAll(Pageable pageable) {
		return questaoRepository.findAll(pageable);
	}

	@Override
	public List<Questao> getQuestaoAll() {
		return questaoRepository.findAll();
	}

	@Override
	public Questao getQuestaoByQuestaoPK(long questaoPK) {
		
		Optional<Questao> questao = questaoRepository.findById(questaoPK);
		return questao.get();
	}

	@Transactional
	public Questao create(QuestaoForm questaoForm) {
		
		Questao questao = new Questao();
		
		questao = this.converteQuestaoForm(questaoForm);
		
		questao = entityManager.merge(questao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questao Create " + "\n Usuário => " + username + 
										" // Id => "+questao.getQuestaoPK() + 
										" // Questao => "+questao.getQuestaoNome() + 
										" // Descrição => "+ questao.getQuestaoDescricao());
		
		return questao;
	}

	@Transactional
	public Questao save(QuestaoForm questaoForm) {
		
		Questao questao = new Questao();
		
		questao = this.converteQuestaoForm(questaoForm);
		
		questao = entityManager.merge(questao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questao Save " + "\n Usuário => " + username + 
										" // Id => "+questao.getQuestaoPK() + 
										" // Questao => "+questao.getQuestaoNome() + 
										" // Descrição => "+ questao.getQuestaoDescricao());
		

		return questao;
		
	}

	@Transactional
	public void delete(Long questaoId) {

		Questao questao = this.getQuestaoByQuestaoPK(questaoId);
		
		questaoRepository.delete(questao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questao Delete " + "\n Usuário => " + username + 
										" // Id => "+questao.getQuestaoPK() + 
										" // Questao => "+questao.getQuestaoNome() + 
										" // Descrição => "+ questao.getQuestaoDescricao());
		

    }

	@Transactional
	public Questao converteQuestaoForm(QuestaoForm questaoForm) {
		
		Questao questao = new Questao();
		
		if (questaoForm.getQuestaoPK() > 0) {
			questao = this.getQuestaoByQuestaoPK(questaoForm.getQuestaoPK());
		}
		questao.setQuestaoNome(questaoForm.getQuestaoNome().replaceAll("\\s+"," "));
		questao.setQuestaoDescricao(questaoForm.getQuestaoDescricao().replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(questaoForm.getQuestaoResponsavel()));
		questao.setColaboradorResponsavel(colaborador);
		
		Elemento elemento = elementoService.getElementoByElementoPK(Long.parseLong(questaoForm.getQuestaoElemento()));
		questao.setElemento(elemento);
		
		questao.setQuestaoMotivoOperacao(questaoForm.getQuestaoMotivoOperacao().replaceAll("\\s+"," "));
		questao.setQuestaoStatus(questaoForm.getQuestaoStatus()+"".replaceAll("\\s+"," "));
		
		return questao;
	}

	@Transactional
	public QuestaoForm converteQuestao(Questao questao) {
	
		QuestaoForm questaoForm = new QuestaoForm();
		
		questaoForm.setQuestaoPK(questao.getQuestaoPK());
		questaoForm.setQuestaoNome(questao.getQuestaoNome());
		questaoForm.setQuestaoDescricao(questao.getQuestaoDescricao());

		questaoForm.setQuestaoResponsavel(questao.getColaboradorResponsavel().getPessoaPK()+"");
		questaoForm.setQuestaoElemento(questao.getElemento().getElementoPK()+"");
		
		questaoForm.setQuestaoMotivoOperacao(questao.getQuestaoMotivoOperacao());
		questaoForm.setQuestaoStatus(AtributoStatus.valueOf(questao.getQuestaoStatus()));

		return questaoForm;
	}
	
	@Transactional
	public QuestaoForm converteQuestaoView(Questao questao) {
	
		QuestaoForm questaoForm = new QuestaoForm();
		
		questaoForm.setQuestaoPK(questao.getQuestaoPK());
		questaoForm.setQuestaoNome(questao.getQuestaoNome());
		questaoForm.setQuestaoDescricao(questao.getQuestaoDescricao());

		questaoForm.setQuestaoResponsavel(questao.getColaboradorResponsavel().getPessoaNome());
		questaoForm.setQuestaoElemento(questao.getElemento().getElementoNome());

		questaoForm.setQuestaoMotivoOperacao(questao.getQuestaoMotivoOperacao());
		questaoForm.setQuestaoStatus(AtributoStatus.valueOf(questao.getQuestaoStatus()));
		
	return questaoForm;
	}
	

	@Transactional
	public QuestaoForm questaoParametros(QuestaoForm questaoForm) {
	
		
		questaoForm.setQuestaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return questaoForm;
	}

	@Override
	public List<Questao> getByQuestaoDescricao(String questaoDescricao,Pageable pageable) {
		return questaoRepository.findByQuestaoDescricao(questaoDescricao,pageable);
	}

	@Override
	public List<Questao> getByQuestaoNome(String questaoNome,Pageable pageable) {
		return questaoRepository.findByQuestaoNome(questaoNome,pageable);
	}


	@Override
	public List<Questao> getByQuestaoDescricao(String questaoDescricao) {
		return questaoRepository.findByQuestaoDescricao(questaoDescricao);
	}

	@Override
	public List<Questao> getByQuestaoNome(String questaoNome) {
		return questaoRepository.findByQuestaoNome(questaoNome);
	}



}
