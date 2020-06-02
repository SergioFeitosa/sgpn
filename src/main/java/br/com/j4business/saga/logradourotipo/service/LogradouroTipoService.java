package br.com.j4business.saga.logradourotipo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.logradourotipo.model.LogradouroTipo;
import br.com.j4business.saga.logradourotipo.model.LogradouroTipoForm;

@Service
public interface LogradouroTipoService {
	
	public List<LogradouroTipo> getLogradouroTipoAll();
	public Page<LogradouroTipo> getLogradouroTipoAll(Pageable pageable);
	public LogradouroTipo getLogradouroTipoByLogradouroTipoPK(long logradouroTipoPK);
	public LogradouroTipo create(LogradouroTipoForm logradouroTipoForm);
	public LogradouroTipo save(LogradouroTipoForm logradouroTipoForm);
	public void delete(Long logradouroTipoPK);
	
	public LogradouroTipo converteLogradouroTipoForm(LogradouroTipoForm logradouroTipoForm);
	public LogradouroTipoForm converteLogradouroTipo(LogradouroTipo logradouroTipo);
	public LogradouroTipoForm converteLogradouroTipoView(LogradouroTipo logradouroTipo);

	public LogradouroTipoForm logradouroTipoParametros(LogradouroTipoForm logradouroTipoForm);

	public List<LogradouroTipo> getByLogradouroTipoNome(String logradouroTipoNome,Pageable pageable);
	public List<LogradouroTipo> getByLogradouroTipoDescricao(String logradouroTipoDescricao,Pageable pageable);

	public List<LogradouroTipo> getByLogradouroTipoNome(String logradouroTipoNome);
	public List<LogradouroTipo> getByLogradouroTipoDescricao(String logradouroTipoDescricao);

}