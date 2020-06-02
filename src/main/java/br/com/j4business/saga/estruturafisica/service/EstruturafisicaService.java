package br.com.j4business.saga.estruturafisica.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisica.model.EstruturafisicaForm;

@Service
public interface EstruturafisicaService {
	
	public List<Estruturafisica> getEstruturafisicaAll();
	public Page<Estruturafisica> getEstruturafisicaAll(Pageable pageable);
	public Estruturafisica getEstruturafisicaByEstruturafisicaPK(long estruturafisicaPK);
	public Estruturafisica create(EstruturafisicaForm estruturafisicaForm);
	public Estruturafisica save(EstruturafisicaForm estruturafisicaForm);
	public void delete(Long estruturafisicaPK);
	
	public Estruturafisica converteEstruturafisicaForm(EstruturafisicaForm estruturafisicaForm);
	public EstruturafisicaForm converteEstruturafisica(Estruturafisica estruturafisica);
	public EstruturafisicaForm converteEstruturafisicaView(Estruturafisica estruturafisica);

	public EstruturafisicaForm estruturafisicaParametros(EstruturafisicaForm estruturafisicaForm);

	public List<Estruturafisica> getByEstruturafisicaNome(String estruturafisicaNome,Pageable pageable);
	public List<Estruturafisica> getByEstruturafisicaDescricao(String estruturafisicaDescricao,Pageable pageable);
	public List<Estruturafisica> getByEstruturafisicaNome(String estruturafisicaNome);
	public List<Estruturafisica> getByEstruturafisicaDescricao(String estruturafisicaDescricao);

}