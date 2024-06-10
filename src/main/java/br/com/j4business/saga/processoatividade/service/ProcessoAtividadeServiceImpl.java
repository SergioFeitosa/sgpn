package br.com.j4business.saga.processoatividade.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.atividade.service.AtividadeService;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividadeForm;
import br.com.j4business.saga.processoatividade.repository.ProcessoAtividadeRepository;

@Service("processoAtividadeService")
public class ProcessoAtividadeServiceImpl implements ProcessoAtividadeService {

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoAtividadeRepository processoAtividadeRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoAtividadeService.class.getName());


	@Override
	public List<ProcessoAtividade> getProcessoAtividadeAll(Pageable pageable) {
		return processoAtividadeRepository.findProcessoAtividadeAll(pageable);
	}

	@Override
	public List<ProcessoAtividade> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoAtividadeRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoAtividade> getByProcessoPK(long processoPK) {
		return processoAtividadeRepository.findByProcessoPK(processoPK);
	}

	@Override
	public List<ProcessoAtividade> getByAtividadeNome(String atividadeNome,Pageable pageable) {
		return processoAtividadeRepository.findByAtividadeNome(atividadeNome,pageable);
	}

	@Override
	public List<ProcessoAtividade> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoAtividadeRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public ProcessoAtividade getProcessoAtividadeByProcessoAtividadePK(long processoAtividadePK) {
		Optional<ProcessoAtividade> processoAtividade =  processoAtividadeRepository.findById(processoAtividadePK);
		return processoAtividade.get();
	}

	@Override
	public ProcessoAtividade getByProcessoAndAtividade(Processo processo,Atividade atividade) {
		
		return processoAtividadeRepository.findByProcessoAndAtividade(processo,atividade);
	}

	@Transactional
	public ProcessoAtividade create(ProcessoAtividadeForm processoAtividadeForm) {
		
		ProcessoAtividade processoAtividade = new ProcessoAtividade();
		
		processoAtividade = this.converteProcessoAtividadeForm(processoAtividadeForm);
		
		processoAtividade = entityManager.merge(processoAtividade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+processoAtividade.getProcessoAtividadePK() + 
				" // Processo Id => "+processoAtividade.getProcesso().getProcessoPK() + 
				" // Atividade Id => "+processoAtividade.getAtividade().getAtividadePK() + 
				" // Valor => "+processoAtividade.getProcessoAtividadeSequencia()); 
		
		return processoAtividade;
	}

	@Transactional
	public ProcessoAtividade save(ProcessoAtividadeForm processoAtividadeForm) {

		ProcessoAtividade processoAtividade = new ProcessoAtividade();
		
		processoAtividade = this.converteProcessoAtividadeForm(processoAtividadeForm);
		
		processoAtividade = entityManager.merge(processoAtividade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoAtividade Save " + "\n Usuário => " + username + 
										" // Id => "+processoAtividade.getProcessoAtividadePK() + 
										" // Processo Id => "+processoAtividade.getProcesso().getProcessoPK() + 
										" // Atividade Id => "+processoAtividade.getAtividade().getAtividadePK() + 
										" // Valor => "+processoAtividade.getProcessoAtividadeSequencia()); 
		return processoAtividade;
	}

	@Transactional
	public void delete(Long processoAtividadePK) {

		ProcessoAtividade processoAtividadeTemp = this.getProcessoAtividadeByProcessoAtividadePK(processoAtividadePK);

		processoAtividadeRepository.delete(processoAtividadeTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoAtividade Save " + "\n Usuário => " + username + 
										" // Id => "+processoAtividadeTemp.getProcessoAtividadePK() + 
										" // Processo Id => "+processoAtividadeTemp.getProcesso().getProcessoPK() + 
										" // Atividade Id => "+processoAtividadeTemp.getAtividade().getAtividadePK() + 
										" // Valor => "+processoAtividadeTemp.getProcessoAtividadeSequencia()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<ProcessoAtividade> processoAtividadeList = processoAtividadeRepository.findByProcesso(processo);

		for (ProcessoAtividade processoAtividade2 : processoAtividadeList) {
			processoAtividadeRepository.delete(processoAtividade2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		processoAtividadeList.forEach((ProcessoAtividade processoAtividade) -> {
			
			logger.info("ProcessoAtividade Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoAtividade.getProcessoAtividadePK() + 
											" // Processo Id => "+processoAtividade.getProcesso().getProcessoPK() + 
											" // Atividade Id => "+processoAtividade.getAtividade().getAtividadePK() + 
											" // Valor => "+processoAtividade.getProcessoAtividadeSequencia()); 

		});
		
    }

	@Transactional
	public ProcessoAtividade converteProcessoAtividadeForm(ProcessoAtividadeForm processoAtividadeForm) {
		
		ProcessoAtividade processoAtividade = new ProcessoAtividade();
		
		if (processoAtividadeForm.getProcessoAtividadePK() > 0) {
			processoAtividade = this.getProcessoAtividadeByProcessoAtividadePK(processoAtividadeForm.getProcessoAtividadePK());
		}
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoAtividadeForm.getProcessoNome()));
		processoAtividade.setProcesso(processo);

		Atividade atividade = atividadeService.getAtividadeByAtividadePK(Long.parseLong(processoAtividadeForm.getAtividadeNome()));
		processoAtividade.setAtividade(atividade);

		processoAtividade.setProcessoAtividadeSequencia(processoAtividadeForm.getProcessoAtividadeSequencia().replaceAll("\\s+"," "));
		processoAtividade.setProcessoAtividadeMotivoOperacao(processoAtividadeForm.getProcessoAtividadeMotivoOperacao().replaceAll("\\s+"," "));
		processoAtividade.setProcessoAtividadeStatus(processoAtividadeForm.getProcessoAtividadeStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoAtividadeForm.getProcessoAtividadeResponsavel()));
		processoAtividade.setColaboradorResponsavel(colaborador);
		
		return processoAtividade;
	}

	@Transactional
	public ProcessoAtividadeForm converteProcessoAtividade(ProcessoAtividade processoAtividade) {
	
		ProcessoAtividadeForm processoAtividadeForm = new ProcessoAtividadeForm();
		
		processoAtividadeForm.setProcessoAtividadePK(processoAtividade.getProcessoAtividadePK());
		processoAtividadeForm.setProcessoNome(processoAtividade.getProcesso().getProcessoPK()+"");
		processoAtividadeForm.setAtividadeNome(processoAtividade.getAtividade().getAtividadePK()+"");
		processoAtividadeForm.setProcessoAtividadeSequencia(processoAtividade.getProcessoAtividadeSequencia());

		processoAtividadeForm.setProcessoAtividadeMotivoOperacao(processoAtividade.getProcessoAtividadeMotivoOperacao());
		processoAtividadeForm.setProcessoAtividadeStatus(AtributoStatus.valueOf(processoAtividade.getProcessoAtividadeStatus()));

		processoAtividadeForm.setProcessoAtividadeResponsavel(processoAtividade.getColaboradorResponsavel().getPessoaPK()+"");
		
	return processoAtividadeForm;
	}
	
	@Transactional
	public ProcessoAtividadeForm converteProcessoAtividadeView(ProcessoAtividade processoAtividade) {
	
		ProcessoAtividadeForm processoAtividadeForm = new ProcessoAtividadeForm();
		
		processoAtividadeForm.setProcessoAtividadePK(processoAtividade.getProcessoAtividadePK());
		processoAtividadeForm.setProcessoNome(processoAtividade.getProcesso().getProcessoNome());
		processoAtividadeForm.setAtividadeNome(processoAtividade.getAtividade().getAtividadeNome());
		processoAtividadeForm.setProcessoAtividadeSequencia(processoAtividade.getProcessoAtividadeSequencia());

		processoAtividadeForm.setProcessoAtividadeMotivoOperacao(processoAtividade.getProcessoAtividadeMotivoOperacao());
		processoAtividadeForm.setProcessoAtividadeStatus(AtributoStatus.valueOf(processoAtividade.getProcessoAtividadeStatus()));

		processoAtividadeForm.setProcessoAtividadeResponsavel(processoAtividade.getColaboradorResponsavel().getPessoaNome());
		
	return processoAtividadeForm;
	}
	

	@Transactional
	public ProcessoAtividadeForm processoAtividadeParametros(ProcessoAtividadeForm processoAtividadeForm) {
	
		
		processoAtividadeForm.setProcessoAtividadeStatus(AtributoStatus.valueOf("ATIVO"));

		
	return processoAtividadeForm;
	}
	
}
