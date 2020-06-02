package br.com.j4business.saga.servico.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servico.model.ServicoForm;

@Service
public interface ServicoService {
	
	public List<Servico> getServicoAll();
	public Page<Servico> getServicoAll(Pageable pageable);
	public Servico getServicoByServicoPK(long servicoPK);
	public Servico create(ServicoForm servicoForm);
	public Servico save(ServicoForm servicoForm);
	public void delete(Long servicoPK);
	
	public Servico converteServicoForm(ServicoForm servicoForm);
	public ServicoForm converteServico(Servico servico);
	public ServicoForm converteServicoView(Servico servico);

	public ServicoForm servicoParametros(ServicoForm servicoForm);

	public List<Servico> getByServicoNome(String servicoNome,Pageable pageable);
	public List<Servico> getByServicoDescricao(String servicoDescricao,Pageable pageable);
	public List<Servico> getByServicoNome(String servicoNome);
	public List<Servico> getByServicoDescricao(String servicoDescricao);

}