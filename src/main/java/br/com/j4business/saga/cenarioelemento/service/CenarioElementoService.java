package br.com.j4business.saga.cenarioelemento.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenarioelemento.model.CenarioElemento;
import br.com.j4business.saga.cenarioelemento.model.CenarioElementoForm;

@Service
public interface CenarioElementoService {
	
	public List<CenarioElemento> getCenarioElementoAll(Pageable pageable);
	public CenarioElemento getCenarioElementoByCenarioElementoPK(long cenarioElementoPK);
	public CenarioElemento save(CenarioElementoForm cenarioElementoForm);
	public void delete(Long cenarioElementoPK);
	public void deleteByElemento(Elemento elemento);
	public CenarioElemento create(CenarioElementoForm cenarioElementoForm);
	public CenarioElemento getByCenarioAndElemento(Cenario cenario, Elemento elemento);

	public CenarioElemento converteCenarioElementoForm(CenarioElementoForm cenarioElementoForm);
	public CenarioElementoForm converteCenarioElemento(CenarioElemento cenarioElemento);
	public CenarioElementoForm converteCenarioElementoView(CenarioElemento cenarioElemento);

	public CenarioElementoForm cenarioElementoParametros(CenarioElementoForm cenarioElementoForm);

	public List<CenarioElemento> getByCenarioPK(long cenarioPK,Pageable pageable);
	public List<CenarioElemento> getByElementoPK(long elementoPK,Pageable pageable);

	public List<CenarioElemento> getByElementoNome(String elementoNome,Pageable pageable);
	public List<CenarioElemento> getByCenarioNome(String cenarioNome,Pageable pageable);
	public List<CenarioElemento> getByElementoNome(String elementoNome);
	public List<CenarioElemento> getByCenarioNome(String cenarioNome);
	
}