package br.com.j4business.saga.processoimagem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processoimagem.model.ProcessoImagem;
import br.com.j4business.saga.processoimagem.model.ProcessoImagemForm;

@Service
public interface ProcessoImagemService {
	
	public List<ProcessoImagem> getProcessoImagemAll(Pageable pageable);
	public ProcessoImagem getProcessoImagemByProcessoImagemPK(long processoImagemPK);
	public ProcessoImagem save(ProcessoImagemForm processoImagemForm);
	public void delete(Long processoImagemPK);
	public void deleteByImagem(Imagem imagem);
	public ProcessoImagem create(ProcessoImagemForm processoImagemForm);
	public ProcessoImagem getByProcessoAndImagem(Processo processo, Imagem imagem);

	public ProcessoImagem converteProcessoImagemForm(ProcessoImagemForm processoImagemForm);
	public ProcessoImagemForm converteProcessoImagem(ProcessoImagem processoImagem);
	public ProcessoImagemForm converteProcessoImagemView(ProcessoImagem processoImagem);

	public ProcessoImagemForm processoImagemParametros(ProcessoImagemForm processoImagemForm);

	public List<ProcessoImagem> getByProcessoPK(long processoPK);
	public List<ProcessoImagem> getProcessoImagemAtivoByProcessoPK(long processoPK);
	public List<ProcessoImagem> getByProcessoPK(long processoPK,Pageable pageable);
	public List<ProcessoImagem> getByImagemPK(long imagemPK,Pageable pageable);

	public List<ProcessoImagem> getByImagemNome(String imagemNome,Pageable pageable);
	public List<ProcessoImagem> getByProcessoNome(String processoNome,Pageable pageable);
	public List<ProcessoImagem> getByImagemNome(String imagemNome);
	public List<ProcessoImagem> getByProcessoNome(String processoNome);
	
}