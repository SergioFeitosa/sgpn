package br.com.j4business.saga.elementoquestao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questao.service.QuestaoService;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.service.ElementoService;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestao;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestaoForm;
import br.com.j4business.saga.elementoquestao.repository.ElementoQuestaoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("elementoQuestaoService")
public class ElementoQuestaoServiceImpl implements ElementoQuestaoService {

	@Autowired
	private ElementoService elementoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ElementoQuestaoRepository elementoQuestaoRepository;

	@Autowired
	private QuestaoService questaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ElementoQuestaoService.class.getName());


	@Override
	public List<ElementoQuestao> getByElementoNome(String elementoNome,Pageable pageable) {
		return elementoQuestaoRepository.findByElementoNome(elementoNome,pageable);
	}

	@Override
	public List<ElementoQuestao> getByQuestaoNome(String questaoNome,Pageable pageable) {
		return elementoQuestaoRepository.findByQuestaoNome(questaoNome,pageable);
	}

	@Override
	public List<ElementoQuestao> getByElementoNome(String elementoNome) {
		return elementoQuestaoRepository.findByElementoNome(elementoNome);
	}

	@Override
	public List<ElementoQuestao> getByQuestaoNome(String questaoNome) {
		return elementoQuestaoRepository.findByQuestaoNome(questaoNome);
	}

	@Override
	public List<ElementoQuestao> getByQuestaoPK(long questaoPK,Pageable pageable) {
		return elementoQuestaoRepository.findByQuestaoPK(questaoPK,pageable);
	}

	@Override
	public List<ElementoQuestao> getByElementoPK(long elementoPK,Pageable pageable) {
		return elementoQuestaoRepository.findByElementoPK(elementoPK,pageable);
	}

	@Override
	public List<ElementoQuestao> getElementoQuestaoAll(Pageable pageable) {
		return elementoQuestaoRepository.findElementoQuestaoAll(pageable);
	}

	@Override
	public ElementoQuestao getElementoQuestaoByElementoQuestaoPK(long elementoQuestaoPK) {
		
		Optional<ElementoQuestao> elementoQuestao = elementoQuestaoRepository.findById(elementoQuestaoPK);
		return elementoQuestao.get();
	}

	@Override
	public ElementoQuestao getByElementoAndQuestao (Elemento elemento,Questao questao) {
		
		return elementoQuestaoRepository.findByElementoAndQuestao(elemento,questao);
	}

	@Transactional
	public ElementoQuestao save(ElementoQuestaoForm elementoQuestaoForm) {

		ElementoQuestao elementoQuestao = new ElementoQuestao();
		
		elementoQuestao = this.converteElementoQuestaoForm(elementoQuestaoForm);
		
		elementoQuestao = entityManager.merge(elementoQuestao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ElementoQuestao Save " + "\n Usuário => " + username + 
										" // Id => "+elementoQuestao.getElementoQuestaoPK() + 
										" // Elemento Id => "+elementoQuestao.getElemento().getElementoPK() + 
										" // Questao Id => "+elementoQuestao.getQuestao().getQuestaoPK());
		return elementoQuestao;
	}

	@Transactional
	public void delete(Long elementoQuestaoPK) {

		ElementoQuestao elementoQuestaoTemp = this.getElementoQuestaoByElementoQuestaoPK(elementoQuestaoPK);

		elementoQuestaoRepository.delete(elementoQuestaoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ElementoQuestao Save " + "\n Usuário => " + username + 
										" // Id => "+elementoQuestaoTemp.getElementoQuestaoPK() + 
										" // Elemento Id => "+elementoQuestaoTemp.getElemento().getElementoPK() + 
										" // Questao Id => "+elementoQuestaoTemp.getQuestao().getQuestaoPK()); 
    }

	@Transactional
	public void deleteByQuestao(Questao questao) {
		
		List<ElementoQuestao> elementoQuestaoList = elementoQuestaoRepository.findByQuestao(questao);

		for (ElementoQuestao elementoQuestao2 : elementoQuestaoList) {
			elementoQuestaoRepository.delete(elementoQuestao2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		elementoQuestaoList.forEach((ElementoQuestao elementoQuestao) -> {
			
			logger.info("ElementoQuestao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+elementoQuestao.getElementoQuestaoPK() + 
											" // Elemento Id => "+elementoQuestao.getElemento().getElementoPK() + 
											" // Questao Id => "+elementoQuestao.getQuestao().getQuestaoPK()); 

		});
		
    }

	@Transactional
	public ElementoQuestao converteElementoQuestaoForm(ElementoQuestaoForm elementoQuestaoForm) {
		
		ElementoQuestao elementoQuestao = new ElementoQuestao();
		
		if (elementoQuestaoForm.getElementoQuestaoPK() > 0) {
			elementoQuestao = this.getElementoQuestaoByElementoQuestaoPK(elementoQuestaoForm.getElementoQuestaoPK());
		}

		Questao questao = questaoService.getQuestaoByQuestaoPK(Long.parseLong(elementoQuestaoForm.getQuestaoNome()));
		elementoQuestao.setQuestao(questao);

		Elemento elemento = elementoService.getElementoByElementoPK(Long.parseLong(elementoQuestaoForm.getElementoNome()));
		elementoQuestao.setElemento(elemento);

		elementoQuestao.setElementoQuestaoMotivoOperacao(elementoQuestaoForm.getElementoQuestaoMotivoOperacao().replaceAll("\\s+"," "));
		elementoQuestao.setElementoQuestaoStatus(elementoQuestaoForm.getElementoQuestaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(elementoQuestaoForm.getElementoQuestaoResponsavel()));
		elementoQuestao.setColaboradorResponsavel(colaborador);
		
		return elementoQuestao;
	}

	@Transactional
	public ElementoQuestaoForm converteElementoQuestao(ElementoQuestao elementoQuestao) {
	
		ElementoQuestaoForm elementoQuestaoForm = new ElementoQuestaoForm();
		
		elementoQuestaoForm.setElementoQuestaoPK(elementoQuestao.getElementoQuestaoPK());
		elementoQuestaoForm.setElementoNome(elementoQuestao.getElemento().getElementoPK()+"");
		elementoQuestaoForm.setQuestaoNome(elementoQuestao.getQuestao().getQuestaoPK()+"");

		elementoQuestaoForm.setElementoQuestaoMotivoOperacao(elementoQuestao.getElementoQuestaoMotivoOperacao());
		elementoQuestaoForm.setElementoQuestaoStatus(AtributoStatus.valueOf(elementoQuestao.getElementoQuestaoStatus()));

		elementoQuestaoForm.setElementoQuestaoResponsavel(elementoQuestao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return elementoQuestaoForm;
	}
	
	@Transactional
	public ElementoQuestaoForm converteElementoQuestaoView(ElementoQuestao elementoQuestao) {
	
		ElementoQuestaoForm elementoQuestaoForm = new ElementoQuestaoForm();
		
		elementoQuestaoForm.setElementoQuestaoPK(elementoQuestao.getElementoQuestaoPK());
		elementoQuestaoForm.setElementoNome(elementoQuestao.getElemento().getElementoNome());
		elementoQuestaoForm.setQuestaoNome(elementoQuestao.getQuestao().getQuestaoNome());

		elementoQuestaoForm.setElementoQuestaoMotivoOperacao(elementoQuestao.getElementoQuestaoMotivoOperacao());
		elementoQuestaoForm.setElementoQuestaoStatus(AtributoStatus.valueOf(elementoQuestao.getElementoQuestaoStatus()));

		elementoQuestaoForm.setElementoQuestaoResponsavel(elementoQuestao.getColaboradorResponsavel().getPessoaNome());
		
	return elementoQuestaoForm;
	}
	

	@Transactional
	public ElementoQuestaoForm elementoQuestaoParametros(ElementoQuestaoForm elementoQuestaoForm) {
	
		
		elementoQuestaoForm.setElementoQuestaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return elementoQuestaoForm;
	}
	
	@Transactional
	public ElementoQuestao create(ElementoQuestaoForm elementoQuestaoForm) {
		
		ElementoQuestao elementoQuestao = new ElementoQuestao();
		
		elementoQuestao = this.converteElementoQuestaoForm(elementoQuestaoForm);
		
		elementoQuestao = entityManager.merge(elementoQuestao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Questao Create " + "\n Usuário => " + username + 
				" // Id => "+elementoQuestao.getElementoQuestaoPK() + 
				" // Elemento Id => "+elementoQuestao.getElemento().getElementoPK() + 
				" // Questao Id => "+elementoQuestao.getQuestao().getQuestaoPK()); 
		
		return elementoQuestao;
	}


}
