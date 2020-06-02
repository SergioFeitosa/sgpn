package br.com.j4business.saga.servicoprocesso.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcessoForm;

@Service
public interface ServicoProcessoService {
	
	public List<ServicoProcesso> getServicoProcessoAll(Pageable pageable);
	public ServicoProcesso getServicoProcessoByServicoProcessoPK(long servicoProcessoPK);
	public ServicoProcesso save(ServicoProcessoForm servicoProcessoForm);
	public void delete(Long servicoProcessoPK);
	public void deleteByProcesso(Processo processo);
	public ServicoProcesso create(ServicoProcessoForm servicoProcessoForm);
	public ServicoProcesso getByServicoAndProcesso(Servico servico, Processo processo);

	public ServicoProcesso converteServicoProcessoForm(ServicoProcessoForm servicoProcessoForm);
	public ServicoProcessoForm converteServicoProcesso(ServicoProcesso servicoProcesso);
	public ServicoProcessoForm converteServicoProcessoView(ServicoProcesso servicoProcesso);

	public ServicoProcessoForm servicoProcessoParametros(ServicoProcessoForm servicoProcessoForm);

	public List<ServicoProcesso> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ServicoProcesso> getByServicoNome(String servicoNome,Pageable pageable);
	public List<ServicoProcesso> getByProcessoNome(String processoNome);
	public List<ServicoProcesso> getByServicoNome(String servicoNome);
	public List<ServicoProcesso> getByServicoPK(long servicoPK,Pageable pageable);
	public List<ServicoProcesso> getByServicoPK(long servicoPK);
	public List<ServicoProcesso> getByProcessoPK(long processoPK,Pageable pageable);
}