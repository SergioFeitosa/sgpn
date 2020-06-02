package br.com.j4business.saga.questionarioquestao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.questao.model.Questao;
import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestao;
import br.com.j4business.saga.questionarioquestao.model.QuestionarioQuestaoForm;

@Service
public interface QuestionarioQuestaoService {
	
	public List<QuestionarioQuestao> getQuestionarioQuestaoAll(Pageable pageable);
	public QuestionarioQuestao getQuestionarioQuestaoByQuestionarioQuestaoPK(long questionarioQuestaoPK);
	public QuestionarioQuestao save(QuestionarioQuestaoForm questionarioQuestaoForm);
	public void delete(Long questionarioQuestaoPK);
	public void deleteByQuestao(Questao questao);
	public QuestionarioQuestao create(QuestionarioQuestaoForm questionarioQuestaoForm);
	public QuestionarioQuestao getByQuestionarioAndQuestao(Questionario questionario, Questao questao);

	public QuestionarioQuestao converteQuestionarioQuestaoForm(QuestionarioQuestaoForm questionarioQuestaoForm);
	public QuestionarioQuestaoForm converteQuestionarioQuestao(QuestionarioQuestao questionarioQuestao);
	public QuestionarioQuestaoForm converteQuestionarioQuestaoView(QuestionarioQuestao questionarioQuestao);

	public QuestionarioQuestaoForm questionarioQuestaoParametros(QuestionarioQuestaoForm questionarioQuestaoForm);

	public List<QuestionarioQuestao> getByQuestaoNome(String questaoNome,Pageable pageable);
	public List<QuestionarioQuestao> getByQuestionarioNome(String questionarioNome,Pageable pageable);
	public List<QuestionarioQuestao> getByQuestaoNome(String questaoNome);
	public List<QuestionarioQuestao> getByQuestionarioNome(String questionarioNome);
	public List<QuestionarioQuestao> getByQuestionarioPK(long questionarioPK);
	public List<QuestionarioQuestao> getByQuestionarioPK(long questionarioPK,Pageable pageable);
	public List<QuestionarioQuestao> getByQuestaoPK(long questaoPK,Pageable pageable);
}