package br.com.j4business.saga.processoformacao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.formacao.service.FormacaoService;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacaoForm;
import br.com.j4business.saga.processoformacao.repository.ProcessoFormacaoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoFormacaoService")
public class ProcessoFormacaoServiceImpl implements ProcessoFormacaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoFormacaoRepository processoFormacaoRepository;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoFormacaoService.class.getName());


	@Override
	public List<ProcessoFormacao> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoFormacaoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoFormacao> getByFormacaoNome(String formacaoNome,Pageable pageable) {
		return processoFormacaoRepository.findByFormacaoNome(formacaoNome,pageable);
	}

	@Override
	public List<ProcessoFormacao> getByProcessoNome(String processoNome) {
		return processoFormacaoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoFormacao> getByFormacaoNome(String formacaoNome) {
		return processoFormacaoRepository.findByFormacaoNome(formacaoNome);
	}

	@Override
	public List<ProcessoFormacao> getByFormacaoPK(long formacaoPK,Pageable pageable) {
		return processoFormacaoRepository.findByFormacaoPK(formacaoPK,pageable);
	}

	@Override
	public List<ProcessoFormacao> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoFormacaoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoFormacao> getProcessoFormacaoAll(Pageable pageable) {
		return processoFormacaoRepository.findProcessoFormacaoAll(pageable);
	}

	@Override
	public ProcessoFormacao getProcessoFormacaoByProcessoFormacaoPK(long processoFormacaoPK) {
		return processoFormacaoRepository.findOne(processoFormacaoPK);
	}

	@Override
	public ProcessoFormacao getByProcessoAndFormacao (Processo processo,Formacao formacao) {
		
		return processoFormacaoRepository.findByProcessoAndFormacao(processo,formacao);
	}

	@Override
	public List<ProcessoFormacao> getMaxNivelByProcessoPK (long processoPK,Pageable pageable) {
		
		return processoFormacaoRepository.findMaxNivelByProcessoPK(processoPK,pageable);
	}

	@Transactional
	public ProcessoFormacao create(ProcessoFormacaoForm processoFormacaoForm) {
		
		ProcessoFormacao processoFormacao = new ProcessoFormacao();
		
		processoFormacao = this.converteProcessoFormacaoForm(processoFormacaoForm);
		
		processoFormacao = entityManager.merge(processoFormacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Formacao Create " + "\n Usuário => " + username + 
				" // Id => "+processoFormacao.getProcessoFormacaoPK() + 
				" // Processo Id => "+processoFormacao.getProcesso().getProcessoPK() + 
				" // Formacao Id => "+processoFormacao.getFormacao().getFormacaoPK()); 
		
		return processoFormacao;
	}

	@Transactional
	public ProcessoFormacao save(ProcessoFormacaoForm processoFormacaoForm) {

		ProcessoFormacao processoFormacao = new ProcessoFormacao();
		
		processoFormacao = this.converteProcessoFormacaoForm(processoFormacaoForm);
		
		processoFormacao = entityManager.merge(processoFormacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoFormacao Save " + "\n Usuário => " + username + 
										" // Id => "+processoFormacao.getProcessoFormacaoPK() + 
										" // Processo Id => "+processoFormacao.getProcesso().getProcessoPK() + 
										" // Formacao Id => "+processoFormacao.getFormacao().getFormacaoPK());
		return processoFormacao;
	}

	@Transactional
	public void delete(Long processoFormacaoPK) {

		ProcessoFormacao processoFormacaoTemp = this.getProcessoFormacaoByProcessoFormacaoPK(processoFormacaoPK);

		processoFormacaoRepository.delete(processoFormacaoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoFormacao Save " + "\n Usuário => " + username + 
										" // Id => "+processoFormacaoTemp.getProcessoFormacaoPK() + 
										" // Processo Id => "+processoFormacaoTemp.getProcesso().getProcessoPK() + 
										" // Formacao Id => "+processoFormacaoTemp.getFormacao().getFormacaoPK()); 
    }

	@Transactional
	public void deleteByFormacao(Formacao formacao) {
		
		List<ProcessoFormacao> processoFormacaoList = processoFormacaoRepository.findByFormacao(formacao);

		processoFormacaoRepository.delete(processoFormacaoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		processoFormacaoList.forEach((ProcessoFormacao processoFormacao) -> {
			
			logger.info("ProcessoFormacao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoFormacao.getProcessoFormacaoPK() + 
											" // Processo Id => "+processoFormacao.getProcesso().getProcessoPK() + 
											" // Formacao Id => "+processoFormacao.getFormacao().getFormacaoPK()); 

		});
		
    }

	@Transactional
	public ProcessoFormacao converteProcessoFormacaoForm(ProcessoFormacaoForm processoFormacaoForm) {
		
		ProcessoFormacao processoFormacao = new ProcessoFormacao();
		
		if (processoFormacaoForm.getProcessoFormacaoPK() > 0) {
			processoFormacao = this.getProcessoFormacaoByProcessoFormacaoPK(processoFormacaoForm.getProcessoFormacaoPK());
		}
		
		Formacao formacao = formacaoService.getFormacaoByFormacaoPK(Long.parseLong(processoFormacaoForm.getFormacaoNome()));
		processoFormacao.setFormacao(formacao);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoFormacaoForm.getProcessoNome()));
		processoFormacao.setProcesso(processo);

		processoFormacao.setProcessoFormacaoMotivoOperacao(processoFormacaoForm.getProcessoFormacaoMotivoOperacao().replaceAll("\\s+"," "));
		processoFormacao.setProcessoFormacaoStatus(processoFormacaoForm.getProcessoFormacaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoFormacaoForm.getProcessoFormacaoResponsavel()));
		processoFormacao.setColaboradorResponsavel(colaborador);

		return processoFormacao;
	}

	@Transactional
	public ProcessoFormacaoForm converteProcessoFormacao(ProcessoFormacao processoFormacao) {
	
		ProcessoFormacaoForm processoFormacaoForm = new ProcessoFormacaoForm();
		
		processoFormacaoForm.setProcessoFormacaoPK(processoFormacao.getProcessoFormacaoPK());
		processoFormacaoForm.setProcessoNome(processoFormacao.getProcesso().getProcessoPK()+"");
		processoFormacaoForm.setFormacaoNome(processoFormacao.getFormacao().getFormacaoPK()+"");

		processoFormacaoForm.setProcessoFormacaoMotivoOperacao(processoFormacao.getProcessoFormacaoMotivoOperacao());
		processoFormacaoForm.setProcessoFormacaoStatus(AtributoStatus.valueOf(processoFormacao.getProcessoFormacaoStatus()));
		
		processoFormacaoForm.setProcessoFormacaoResponsavel(processoFormacao.getProcesso().getProcessoPK()+"");
		
		
	return processoFormacaoForm;
	}
	
	@Transactional
	public ProcessoFormacaoForm converteProcessoFormacaoView(ProcessoFormacao processoFormacao) {
	
		ProcessoFormacaoForm processoFormacaoForm = new ProcessoFormacaoForm();
		
		processoFormacaoForm.setProcessoFormacaoPK(processoFormacao.getProcessoFormacaoPK());
		processoFormacaoForm.setProcessoNome(processoFormacao.getProcesso().getProcessoNome());
		processoFormacaoForm.setFormacaoNome(processoFormacao.getFormacao().getFormacaoNome());

		processoFormacaoForm.setProcessoFormacaoMotivoOperacao(processoFormacao.getProcessoFormacaoMotivoOperacao());
		processoFormacaoForm.setProcessoFormacaoStatus(AtributoStatus.valueOf(processoFormacao.getProcessoFormacaoStatus()));

		processoFormacaoForm.setProcessoFormacaoResponsavel(processoFormacao.getProcesso().getProcessoNome());
		
	return processoFormacaoForm;
	}
	

	@Transactional
	public ProcessoFormacaoForm processoFormacaoParametros(ProcessoFormacaoForm processoFormacaoForm) {
	
		
		processoFormacaoForm.setProcessoFormacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return processoFormacaoForm;
	}
	
}
