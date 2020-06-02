package br.com.j4business.saga.atendimento.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.atendimento.model.AtendimentoForm;

@Service
public interface AtendimentoService {
	
	public List<Atendimento> getAtendimentoAll();
	public Page<Atendimento> getAtendimentoAll(Pageable pageable);
	public Atendimento getAtendimentoByAtendimentoPK(long atendimentoPK);
	public Atendimento create(AtendimentoForm atendimentoForm);
	public Atendimento save(AtendimentoForm atendimentoForm);
	public void delete(Long atendimentoPK);
	
	public Atendimento converteAtendimentoForm(AtendimentoForm atendimentoForm);
	public AtendimentoForm converteAtendimento(Atendimento atendimento);
	public AtendimentoForm converteAtendimentoView(Atendimento atendimento);

	public AtendimentoForm atendimentoParametros(AtendimentoForm atendimentoForm);

	public List<Atendimento> getByAtendimentoNome(String atendimentoNome,Pageable pageable);
	public List<Atendimento> getByAtendimentoDescricao(String atendimentoDescricao,Pageable pageable);

	public List<Atendimento> getByAtendimentoNome(String atendimentoNome);
	public List<Atendimento> getByAtendimentoDescricao(String atendimentoDescricao);

}