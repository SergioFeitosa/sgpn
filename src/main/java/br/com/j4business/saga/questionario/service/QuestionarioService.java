package br.com.j4business.saga.questionario.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.questionario.model.Questionario;
import br.com.j4business.saga.questionario.model.QuestionarioForm;

@Service
public interface QuestionarioService {
	
	public List<Questionario> getQuestionarioAll();
	public Page<Questionario> getQuestionarioAll(Pageable pageable);
	public Questionario getQuestionarioByQuestionarioPK(long questionarioPK);
	public Questionario create(QuestionarioForm questionarioForm);
	public Questionario save(QuestionarioForm questionarioForm);
	public void delete(Long questionarioPK);
	
	public Questionario converteQuestionarioForm(QuestionarioForm questionarioForm);
	public QuestionarioForm converteQuestionario(Questionario questionario);
	public QuestionarioForm converteQuestionarioView(Questionario questionario);

	public QuestionarioForm questionarioParametros(QuestionarioForm questionarioForm);

	public List<Questionario> getByQuestionarioNome(String questionarioNome,Pageable pageable);
	public List<Questionario> getByQuestionarioDescricao(String questionarioDescricao,Pageable pageable);

	public List<Questionario> getByQuestionarioNome(String questionarioNome);
	public List<Questionario> getByQuestionarioDescricao(String questionarioDescricao);

	public List<Questionario> getByCenarioNome(String cenarioNome,Pageable pageable);
	public List<Questionario> getByCenarioNome(String cenarioNome);

}