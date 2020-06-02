package br.com.j4business.saga.funcao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.funcao.model.FuncaoForm;

@Service
public interface FuncaoService {
	
	public List<Funcao> getFuncaoAll();
	public Page<Funcao> getFuncaoAll(Pageable pageable);
	public Funcao getFuncaoByFuncaoPK(long funcaoPK);
	public Funcao create(FuncaoForm funcaoForm);
	public Funcao save(FuncaoForm funcaoForm);
	public void delete(Long funcaoPK);
	
	public Funcao converteFuncaoForm(FuncaoForm funcaoForm);
	public FuncaoForm converteFuncao(Funcao funcao);
	public FuncaoForm converteFuncaoView(Funcao funcao);

	public FuncaoForm funcaoParametros(FuncaoForm funcaoForm);

	public List<Funcao> getByFuncaoNome(String funcaoNome,Pageable pageable);
	public List<Funcao> getByFuncaoDescricao(String funcaoDescricao,Pageable pageable);
	public List<Funcao> getByFuncaoNome(String funcaoNome);
	public List<Funcao> getByFuncaoDescricao(String funcaoDescricao);

}