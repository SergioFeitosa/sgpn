package br.com.j4business.saga.processo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.model.ProcessoForm;

@Service
public interface ProcessoService {
	
	public List<Processo> getProcessoAll();
	public Page<Processo> getProcessoAll(Pageable pageable);
	public Processo getProcessoByProcessoPK(long processoPK);
	public Processo create(ProcessoForm processoForm);
	public Processo save(ProcessoForm processoForm);
	public void delete(Long processoPK);
	
	public Processo converteProcessoForm(ProcessoForm processoForm);
	public ProcessoForm converteProcesso(Processo processo);
	public ProcessoForm converteProcessoView(Processo processo);

	public ProcessoForm processoParametros(ProcessoForm processoForm);

	public List<Processo> getByProcessoNome(String processoNome,Pageable pageable);
	public List<Processo> getByProcessoDescricao(String processoDescricao,Pageable pageable);
	public List<Processo> getByProcessoNome(String processoNome);
	public List<Processo> getByProcessoDescricao(String processoDescricao);

}