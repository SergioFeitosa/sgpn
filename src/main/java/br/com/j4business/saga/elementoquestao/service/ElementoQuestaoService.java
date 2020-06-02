package br.com.j4business.saga.elementoquestao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestao;
import br.com.j4business.saga.elementoquestao.model.ElementoQuestaoForm;

@Service
public interface ElementoQuestaoService {
	
	public List<ElementoQuestao> getElementoQuestaoAll(Pageable pageable);
	public ElementoQuestao getElementoQuestaoByElementoQuestaoPK(long elementoQuestaoPK);
	public ElementoQuestao save(ElementoQuestaoForm elementoQuestaoForm);
	public void delete(Long elementoQuestaoPK);
	public void deleteByQuestao(Questao questao);
	public ElementoQuestao create(ElementoQuestaoForm elementoQuestaoForm);
	public ElementoQuestao getByElementoAndQuestao(Elemento elemento, Questao questao);

	public ElementoQuestao converteElementoQuestaoForm(ElementoQuestaoForm elementoQuestaoForm);
	public ElementoQuestaoForm converteElementoQuestao(ElementoQuestao elementoQuestao);
	public ElementoQuestaoForm converteElementoQuestaoView(ElementoQuestao elementoQuestao);

	public ElementoQuestaoForm elementoQuestaoParametros(ElementoQuestaoForm elementoQuestaoForm);

	public List<ElementoQuestao> getByElementoPK(long elementoPK,Pageable pageable);
	public List<ElementoQuestao> getByQuestaoPK(long questaoPK,Pageable pageable);

	public List<ElementoQuestao> getByQuestaoNome(String questaoNome,Pageable pageable);
	public List<ElementoQuestao> getByElementoNome(String elementoNome,Pageable pageable);
	public List<ElementoQuestao> getByQuestaoNome(String questaoNome);
	public List<ElementoQuestao> getByElementoNome(String elementoNome);
	
}