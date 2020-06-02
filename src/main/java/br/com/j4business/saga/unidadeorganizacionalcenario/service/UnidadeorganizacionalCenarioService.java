package br.com.j4business.saga.unidadeorganizacionalcenario.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenarioForm;

@Service
public interface UnidadeorganizacionalCenarioService {
	
	public List<UnidadeorganizacionalCenario> getUnidadeorganizacionalCenarioAll(Pageable pageable);
	public UnidadeorganizacionalCenario getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(long unidadeorganizacionalCenarioPK);
	public UnidadeorganizacionalCenario save(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm);
	public void delete(Long unidadeorganizacionalCenarioPK);
	public void deleteByCenario(Cenario cenario);
	public UnidadeorganizacionalCenario create(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm);
	public UnidadeorganizacionalCenario getByUnidadeorganizacionalAndCenario(Unidadeorganizacional unidadeorganizacional, Cenario cenario);

	public UnidadeorganizacionalCenario converteUnidadeorganizacionalCenarioForm(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm);
	public UnidadeorganizacionalCenarioForm converteUnidadeorganizacionalCenario(UnidadeorganizacionalCenario unidadeorganizacionalCenario);
	public UnidadeorganizacionalCenarioForm converteUnidadeorganizacionalCenarioView(UnidadeorganizacionalCenario unidadeorganizacionalCenario);

	public UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioParametros(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm);

	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable);
	public List<UnidadeorganizacionalCenario> getByCenarioPK(long cenarioPK,Pageable pageable);

	public List<UnidadeorganizacionalCenario> getByCenarioNome(String cenarioNome,Pageable pageable);
	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable);
	public List<UnidadeorganizacionalCenario> getByCenarioNome(String cenarioNome);
	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome);
	
}