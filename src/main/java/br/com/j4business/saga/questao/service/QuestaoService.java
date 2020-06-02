package br.com.j4business.saga.questao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questao.model.QuestaoForm;

@Service
public interface QuestaoService {
	
	public List<Questao> getQuestaoAll();
	public Page<Questao> getQuestaoAll(Pageable pageable);
	public Questao getQuestaoByQuestaoPK(long questaoPK);
	public Questao create(QuestaoForm questaoForm);
	public Questao save(QuestaoForm questaoForm);
	public void delete(Long questaoPK);
	
	public Questao converteQuestaoForm(QuestaoForm questaoForm);
	public QuestaoForm converteQuestao(Questao questao);
	public QuestaoForm converteQuestaoView(Questao questao);

	public QuestaoForm questaoParametros(QuestaoForm questaoForm);

	public List<Questao> getByQuestaoNome(String questaoNome,Pageable pageable);
	public List<Questao> getByQuestaoDescricao(String questaoDescricao,Pageable pageable);

	public List<Questao> getByQuestaoNome(String questaoNome);
	public List<Questao> getByQuestaoDescricao(String questaoDescricao);

}