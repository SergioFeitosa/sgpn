package br.com.j4business.saga.processohabilidade.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoformacao.model.ProcessoFormacao;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidade;
import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.habilidade.service.HabilidadeService;
import br.com.j4business.saga.processohabilidade.model.ProcessoHabilidadeForm;
import br.com.j4business.saga.processohabilidade.repository.ProcessoHabilidadeRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoHabilidadeService")
public class ProcessoHabilidadeServiceImpl implements ProcessoHabilidadeService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoHabilidadeRepository processoHabilidadeRepository;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoHabilidadeService.class.getName());


	@Override
	public List<ProcessoHabilidade> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoHabilidadeRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoHabilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable) {
		return processoHabilidadeRepository.findByHabilidadeNome(habilidadeNome,pageable);
	}

	@Override
	public List<ProcessoHabilidade> getByProcessoNome(String processoNome) {
		return processoHabilidadeRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoHabilidade> getByHabilidadeNome(String habilidadeNome) {
		return processoHabilidadeRepository.findByHabilidadeNome(habilidadeNome);
	}

	@Override
	public List<ProcessoHabilidade> getByHabilidadePK(long habilidadePK,Pageable pageable) {
		return processoHabilidadeRepository.findByHabilidadePK(habilidadePK,pageable);
	}

	@Override
	public List<ProcessoHabilidade> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoHabilidadeRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoHabilidade> getProcessoHabilidadeAll(Pageable pageable) {
		return processoHabilidadeRepository.findProcessoHabilidadeAll(pageable);
	}

	@Override
	public ProcessoHabilidade getProcessoHabilidadeByProcessoHabilidadePK(long processoHabilidadePK) {
		return processoHabilidadeRepository.findOne(processoHabilidadePK);
	}

	@Override
	public ProcessoHabilidade getByProcessoAndHabilidade (Processo processo,Habilidade habilidade) {
		
		return processoHabilidadeRepository.findByProcessoAndHabilidade(processo,habilidade);
	}

	@Transactional
	public ProcessoHabilidade create(ProcessoHabilidadeForm processoHabilidadeForm) {
		
		ProcessoHabilidade processoHabilidade = new ProcessoHabilidade();
		
		processoHabilidade = this.converteProcessoHabilidadeForm(processoHabilidadeForm);
		
		processoHabilidade = entityManager.merge(processoHabilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Habilidade Create " + "\n Usuário => " + username + 
				" // Id => "+processoHabilidade.getProcessoHabilidadePK() + 
				" // Processo Id => "+processoHabilidade.getProcesso().getProcessoPK() + 
				" // Habilidade Id => "+processoHabilidade.getHabilidade().getHabilidadePK()); 
		
		return processoHabilidade;
	}

	@Transactional
	public ProcessoHabilidade save(ProcessoHabilidadeForm processoHabilidadeForm) {

		ProcessoHabilidade processoHabilidade = new ProcessoHabilidade();
		
		processoHabilidade = this.converteProcessoHabilidadeForm(processoHabilidadeForm);
		
		processoHabilidade = entityManager.merge(processoHabilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoHabilidade Save " + "\n Usuário => " + username + 
										" // Id => "+processoHabilidade.getProcessoHabilidadePK() + 
										" // Processo Id => "+processoHabilidade.getProcesso().getProcessoPK() + 
										" // Habilidade Id => "+processoHabilidade.getHabilidade().getHabilidadePK());
		return processoHabilidade;
	}

	@Transactional
	public void delete(Long processoHabilidadePK) {

		ProcessoHabilidade processoHabilidadeTemp = this.getProcessoHabilidadeByProcessoHabilidadePK(processoHabilidadePK);

		processoHabilidadeRepository.delete(processoHabilidadePK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoHabilidade Save " + "\n Usuário => " + username + 
										" // Id => "+processoHabilidadeTemp.getProcessoHabilidadePK() + 
										" // Processo Id => "+processoHabilidadeTemp.getProcesso().getProcessoPK() + 
										" // Habilidade Id => "+processoHabilidadeTemp.getHabilidade().getHabilidadePK()); 
    }

	@Transactional
	public void deleteByHabilidade(Habilidade habilidade) {
		
		List<ProcessoHabilidade> processoHabilidadeList = processoHabilidadeRepository.findByHabilidade(habilidade);

		processoHabilidadeRepository.delete(processoHabilidadeList);

		String username = usuarioSeguranca.getUsuarioLogado();

		processoHabilidadeList.forEach((ProcessoHabilidade processoHabilidade) -> {

			logger.info("ProcessoHabilidade Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoHabilidade.getProcessoHabilidadePK() + 
											" // Processo Id => "+processoHabilidade.getProcesso().getProcessoPK() + 
											" // Habilidade Id => "+processoHabilidade.getHabilidade().getHabilidadePK()); 

		});
		
    }

	@Transactional
	public ProcessoHabilidade converteProcessoHabilidadeForm(ProcessoHabilidadeForm processoHabilidadeForm) {
		
		ProcessoHabilidade processoHabilidade = new ProcessoHabilidade();
		
		if (processoHabilidadeForm.getProcessoHabilidadePK() > 0) {
			processoHabilidade = this.getProcessoHabilidadeByProcessoHabilidadePK(processoHabilidadeForm.getProcessoHabilidadePK());
		}
		
		
		Habilidade habilidade = habilidadeService.getHabilidadeByHabilidadePK(Long.parseLong(processoHabilidadeForm.getHabilidadeNome()));
		processoHabilidade.setHabilidade(habilidade);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoHabilidadeForm.getProcessoNome()));
		processoHabilidade.setProcesso(processo);

		processoHabilidade.setProcessoHabilidadeMotivoOperacao(processoHabilidadeForm.getProcessoHabilidadeMotivoOperacao().replaceAll("\\s+"," "));
		processoHabilidade.setProcessoHabilidadeStatus(processoHabilidadeForm.getProcessoHabilidadeStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoHabilidadeForm.getProcessoHabilidadeResponsavel()));
		processoHabilidade.setColaboradorResponsavel(colaborador);
		
		
		return processoHabilidade;
	}

	@Transactional
	public ProcessoHabilidadeForm converteProcessoHabilidade(ProcessoHabilidade processoHabilidade) {
	
		ProcessoHabilidadeForm processoHabilidadeForm = new ProcessoHabilidadeForm();
		
		processoHabilidadeForm.setProcessoHabilidadePK(processoHabilidade.getProcessoHabilidadePK());
		processoHabilidadeForm.setProcessoNome(processoHabilidade.getProcesso().getProcessoPK()+"");
		processoHabilidadeForm.setHabilidadeNome(processoHabilidade.getHabilidade().getHabilidadePK()+"");

		processoHabilidadeForm.setProcessoHabilidadeMotivoOperacao(processoHabilidade.getProcessoHabilidadeMotivoOperacao());
		processoHabilidadeForm.setProcessoHabilidadeStatus(AtributoStatus.valueOf(processoHabilidade.getProcessoHabilidadeStatus()));
		processoHabilidadeForm.setProcessoHabilidadeResponsavel(processoHabilidade.getProcesso().getProcessoPK()+"");
		
	return processoHabilidadeForm;
	}
	
	@Transactional
	public ProcessoHabilidadeForm converteProcessoHabilidadeView(ProcessoHabilidade processoHabilidade) {
	
		ProcessoHabilidadeForm processoHabilidadeForm = new ProcessoHabilidadeForm();
		
		processoHabilidadeForm.setProcessoHabilidadePK(processoHabilidade.getProcessoHabilidadePK());
		processoHabilidadeForm.setProcessoNome(processoHabilidade.getProcesso().getProcessoNome());
		processoHabilidadeForm.setHabilidadeNome(processoHabilidade.getHabilidade().getHabilidadeNome());

		processoHabilidadeForm.setProcessoHabilidadeMotivoOperacao(processoHabilidade.getProcessoHabilidadeMotivoOperacao());
		processoHabilidadeForm.setProcessoHabilidadeStatus(AtributoStatus.valueOf(processoHabilidade.getProcessoHabilidadeStatus()));
		processoHabilidadeForm.setProcessoHabilidadeResponsavel(processoHabilidade.getProcesso().getProcessoNome());
		
	return processoHabilidadeForm;
	}
	

	@Transactional
	public ProcessoHabilidadeForm processoHabilidadeParametros(ProcessoHabilidadeForm processoHabilidadeForm) {
		processoHabilidadeForm.setProcessoHabilidadeStatus(AtributoStatus.valueOf("ATIVO"));
	return processoHabilidadeForm;
	}
	

}
