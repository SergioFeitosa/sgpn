package br.com.j4business.saga.colaboradorprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcesso;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcessoForm;

@Service
public interface ColaboradorProcessoService {
	
	public List<ColaboradorProcesso> getColaboradorProcessoAll(Pageable pageable);
	public ColaboradorProcesso getColaboradorProcessoByColaboradorProcessoPK(long colaboradorProcessoPK);
	public ColaboradorProcesso save(ColaboradorProcessoForm colaboradorProcessoForm);
	public void delete(Long colaboradorProcessoPK);
	public void deleteByProcesso(Processo processo);
	public ColaboradorProcesso create(ColaboradorProcessoForm colaboradorProcessoForm);
	public ColaboradorProcesso getByColaboradorAndProcesso(Colaborador colaborador, Processo processo);

	public ColaboradorProcesso converteColaboradorProcessoForm(ColaboradorProcessoForm colaboradorProcessoForm);
	public ColaboradorProcessoForm converteColaboradorProcesso(ColaboradorProcesso colaboradorProcesso);
	public ColaboradorProcessoForm converteColaboradorProcessoView(ColaboradorProcesso colaboradorProcesso);

	public ColaboradorProcessoForm colaboradorProcessoParametros(ColaboradorProcessoForm colaboradorProcessoForm);

	public List<ColaboradorProcesso> getByColaboradorPK(long colaboradorPK,Pageable pageable);
	public List<ColaboradorProcesso> getByProcessoPK(long processoPK,Pageable pageable);

	public List<ColaboradorProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ColaboradorProcesso> getByColaboradorNome(String colaboradorNome,Pageable pageable);
	public List<ColaboradorProcesso> getByProcessoNome(String processoNome);
	public List<ColaboradorProcesso> getByColaboradorNome(String colaboradorNome);
	
}