package br.com.j4business.saga.resultado.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.resultado.model.ResultadoForm;

@Service
public interface ResultadoService {
	
	public List<Resultado> getResultadoAll();
	public Page<Resultado> getResultadoAll(Pageable pageable);
	public Resultado getResultadoByResultadoPK(long resultadoPK);
	public Resultado create(ResultadoForm resultadoForm);
	public Resultado save(ResultadoForm resultadoForm);
	public void delete(Long resultadoPK);
	
	public Resultado converteResultadoForm(ResultadoForm resultadoForm);
	public ResultadoForm converteResultado(Resultado resultado);
	public ResultadoForm converteResultadoView(Resultado resultado);

	public ResultadoForm resultadoParametros(ResultadoForm resultadoForm);

	public List<Resultado> getByResultadoNome(String resultadoNome,Pageable pageable);
	public List<Resultado> getByResultadoDescricao(String resultadoDescricao,Pageable pageable);
	public List<Resultado> getByResultadoNome(String resultadoNome);
	public List<Resultado> getByResultadoDescricao(String resultadoDescricao);

	public Resultado getByResultadoNomeCompleto(String resultadoNome);

}