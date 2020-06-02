package br.com.j4business.saga.colaboradorfuncao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncao;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncaoForm;

@Service
public interface ColaboradorFuncaoService {
	
	public List<ColaboradorFuncao> getColaboradorFuncaoAll(Pageable pageable);
	public ColaboradorFuncao getColaboradorFuncaoByColaboradorFuncaoPK(long colaboradorFuncaoPK);
	public ColaboradorFuncao save(ColaboradorFuncaoForm colaboradorFuncaoForm);
	public void delete(Long colaboradorFuncaoPK);
	public void deleteByFuncao(Funcao funcao);
	public ColaboradorFuncao create(ColaboradorFuncaoForm colaboradorFuncaoForm);
	public ColaboradorFuncao getByColaboradorAndFuncao(Colaborador colaborador, Funcao funcao);

	public ColaboradorFuncao converteColaboradorFuncaoForm(ColaboradorFuncaoForm colaboradorFuncaoForm);
	public ColaboradorFuncaoForm converteColaboradorFuncao(ColaboradorFuncao colaboradorFuncao);
	public ColaboradorFuncaoForm converteColaboradorFuncaoView(ColaboradorFuncao colaboradorFuncao);

	public ColaboradorFuncaoForm colaboradorFuncaoParametros(ColaboradorFuncaoForm colaboradorFuncaoForm);

	public List<ColaboradorFuncao> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorFuncao> getByFuncaoPK(long funcaoPK,Pageable pageable);

	public List<ColaboradorFuncao> getByFuncaoNome(String funcaoNome,Pageable pageable);
	public List<ColaboradorFuncao> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorFuncao> getByFuncaoNome(String funcaoNome);
	public List<ColaboradorFuncao> getByColaboradorNome(String colaboradorNome);
	
}