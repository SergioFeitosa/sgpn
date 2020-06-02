package br.com.j4business.saga.estruturafisicaunidadeorganizacional.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacionalForm;

@Service
public interface EstruturafisicaUnidadeorganizacionalService {
	
	public Page<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaUnidadeorganizacionalAll(Pageable pageable);
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaUnidadeorganizacionalAll();
	public EstruturafisicaUnidadeorganizacional getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(long estruturafisicaUnidadeorganizacionalPK);
	public EstruturafisicaUnidadeorganizacional save(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm);
	public void delete(Long estruturafisicaUnidadeorganizacionalPK);
	public void deleteByUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional);
	public EstruturafisicaUnidadeorganizacional create(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm);
	public EstruturafisicaUnidadeorganizacional getByEstruturafisicaAndUnidadeorganizacional(Estruturafisica estruturafisica, Unidadeorganizacional unidadeorganizacional);

	public EstruturafisicaUnidadeorganizacional converteEstruturafisicaUnidadeorganizacionalForm(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm);
	public EstruturafisicaUnidadeorganizacionalForm converteEstruturafisicaUnidadeorganizacional(EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional);
	public EstruturafisicaUnidadeorganizacionalForm converteEstruturafisicaUnidadeorganizacionalView(EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional);

	public EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalParametros(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm);

	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaPK(long estruturafisicaPK,Pageable pageable);
	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK);
	public Page<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable);

	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable);
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaNome(String estruturafisicaNome,Pageable pageable);
	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome);
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaNome(String estruturafisicaNome);
	
}