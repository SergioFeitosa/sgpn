package br.com.j4business.saga.cenario.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.model.CenarioForm;

@Service
public interface CenarioService {
	
	public List<Cenario> getCenarioAll();
	public Page<Cenario> getCenarioAll(Pageable pageable);
	public Cenario getCenarioByCenarioPK(long cenarioPK);
	public Cenario create(CenarioForm cenarioForm);
	public Cenario save(CenarioForm cenarioForm);
	public void delete(Long cenarioPK);
	
	public Cenario converteCenarioForm(CenarioForm cenarioForm);
	public CenarioForm converteCenario(Cenario cenario);
	public CenarioForm converteCenarioView(Cenario cenario);

	public CenarioForm cenarioParametros(CenarioForm cenarioForm);

	public List<Cenario> getByCenarioNome(String cenarioNome,Pageable pageable);
	public List<Cenario> getByCenarioDescricao(String cenarioDescricao,Pageable pageable);
	public List<Cenario> getByCenarioNome(String cenarioNome);
	public List<Cenario> getByCenarioDescricao(String cenarioDescricao);

}