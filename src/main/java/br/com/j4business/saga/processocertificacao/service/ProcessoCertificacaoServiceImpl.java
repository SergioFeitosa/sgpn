package br.com.j4business.saga.processocertificacao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.certificacao.service.CertificacaoService;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacaoForm;
import br.com.j4business.saga.processocertificacao.repository.ProcessoCertificacaoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoCertificacaoService")
public class ProcessoCertificacaoServiceImpl implements ProcessoCertificacaoService {


	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoCertificacaoRepository processoCertificacaoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoCertificacaoService.class.getName());


	@Override
	public List<ProcessoCertificacao> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoCertificacaoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoCertificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable) {
		return processoCertificacaoRepository.findByCertificacaoNome(certificacaoNome,pageable);
	}

	@Override
	public List<ProcessoCertificacao> getByProcessoNome(String processoNome) {
		return processoCertificacaoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoCertificacao> getByCertificacaoNome(String certificacaoNome) {
		return processoCertificacaoRepository.findByCertificacaoNome(certificacaoNome);
	}

	@Override
	public List<ProcessoCertificacao> getByCertificacaoPK(long certificacaoPK,Pageable pageable) {
		return processoCertificacaoRepository.findByCertificacaoPK(certificacaoPK,pageable);
	}

	@Override
	public List<ProcessoCertificacao> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoCertificacaoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoCertificacao> getProcessoCertificacaoAll(Pageable pageable) {
		return processoCertificacaoRepository.findProcessoCertificacaoAll(pageable);
	}

	@Override
	public ProcessoCertificacao getProcessoCertificacaoByProcessoCertificacaoPK(long processoCertificacaoPK) {
		return processoCertificacaoRepository.findOne(processoCertificacaoPK);
	}

	@Override
	public ProcessoCertificacao getByProcessoAndCertificacao (Processo processo,Certificacao certificacao) {
		
		return processoCertificacaoRepository.findByProcessoAndCertificacao(processo,certificacao);
	}

	@Transactional
	public ProcessoCertificacao create(ProcessoCertificacaoForm processoCertificacaoForm) {
		
		ProcessoCertificacao processoCertificacao = new ProcessoCertificacao();
		
		processoCertificacao = this.converteProcessoCertificacaoForm(processoCertificacaoForm);
		
		processoCertificacao = entityManager.merge(processoCertificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Certificacao Create " + "\n Usuário => " + username + 
				" // Id => "+processoCertificacao.getProcessoCertificacaoPK() + 
				" // Processo Id => "+processoCertificacao.getProcesso().getProcessoPK() + 
				" // Certificacao Id => "+processoCertificacao.getCertificacao().getCertificacaoPK()); 
		
		return processoCertificacao;
	}


	@Transactional
	public ProcessoCertificacao save(ProcessoCertificacaoForm processoCertificacaoForm) {

		ProcessoCertificacao processoCertificacao = new ProcessoCertificacao();
		
		processoCertificacao = this.converteProcessoCertificacaoForm(processoCertificacaoForm);
		
		processoCertificacao = entityManager.merge(processoCertificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoCertificacao Save " + "\n Usuário => " + username + 
										" // Id => "+processoCertificacao.getProcessoCertificacaoPK() + 
										" // Processo Id => "+processoCertificacao.getProcesso().getProcessoPK() + 
										" // Certificacao Id => "+processoCertificacao.getCertificacao().getCertificacaoPK());
		return processoCertificacao;
	}

	@Transactional
	public void delete(Long processoCertificacaoPK) {

		ProcessoCertificacao processoCertificacaoTemp = this.getProcessoCertificacaoByProcessoCertificacaoPK(processoCertificacaoPK);

		processoCertificacaoRepository.delete(processoCertificacaoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoCertificacao Save " + "\n Usuário => " + username + 
										" // Id => "+processoCertificacaoTemp.getProcessoCertificacaoPK() + 
										" // Processo Id => "+processoCertificacaoTemp.getProcesso().getProcessoPK() + 
										" // Certificacao Id => "+processoCertificacaoTemp.getCertificacao().getCertificacaoPK()); 
    }

	@Transactional
	public void deleteByCertificacao(Certificacao certificacao) {
		
		List<ProcessoCertificacao> processoCertificacaoList = processoCertificacaoRepository.findByCertificacao(certificacao);

		processoCertificacaoRepository.delete(processoCertificacaoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		processoCertificacaoList.forEach((ProcessoCertificacao processoCertificacao) -> {
			
			logger.info("ProcessoCertificacao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoCertificacao.getProcessoCertificacaoPK() + 
											" // Processo Id => "+processoCertificacao.getProcesso().getProcessoPK() + 
											" // Certificacao Id => "+processoCertificacao.getCertificacao().getCertificacaoPK()); 

		});
		
    }

	@Transactional
	public ProcessoCertificacao converteProcessoCertificacaoForm(ProcessoCertificacaoForm processoCertificacaoForm) {
		
		ProcessoCertificacao processoCertificacao = new ProcessoCertificacao();
		
		if (processoCertificacaoForm.getProcessoCertificacaoPK() > 0) {
			processoCertificacao = this.getProcessoCertificacaoByProcessoCertificacaoPK(processoCertificacaoForm.getProcessoCertificacaoPK());
		}
		
		
		Certificacao certificacao = certificacaoService.getCertificacaoByCertificacaoPK(Long.parseLong(processoCertificacaoForm.getCertificacaoNome()));
		processoCertificacao.setCertificacao(certificacao);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoCertificacaoForm.getProcessoNome()));
		processoCertificacao.setProcesso(processo);

		processoCertificacao.setProcessoCertificacaoMotivoOperacao(processoCertificacaoForm.getProcessoCertificacaoMotivoOperacao().replaceAll("\\s+"," "));
		processoCertificacao.setProcessoCertificacaoStatus(processoCertificacaoForm.getProcessoCertificacaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoCertificacaoForm.getProcessoCertificacaoResponsavel()));
		processoCertificacao.setColaboradorResponsavel(colaborador);
		
		return processoCertificacao;
	}

	@Transactional
	public ProcessoCertificacaoForm converteProcessoCertificacao(ProcessoCertificacao processoCertificacao) {
	
		ProcessoCertificacaoForm processoCertificacaoForm = new ProcessoCertificacaoForm();
		
		processoCertificacaoForm.setProcessoCertificacaoPK(processoCertificacao.getProcessoCertificacaoPK());
		processoCertificacaoForm.setProcessoNome(processoCertificacao.getProcesso().getProcessoPK()+"");
		processoCertificacaoForm.setCertificacaoNome(processoCertificacao.getCertificacao().getCertificacaoPK()+"");

		processoCertificacaoForm.setProcessoCertificacaoMotivoOperacao(processoCertificacao.getProcessoCertificacaoMotivoOperacao());
		processoCertificacaoForm.setProcessoCertificacaoStatus(AtributoStatus.valueOf(processoCertificacao.getProcessoCertificacaoStatus()));
		
		processoCertificacaoForm.setProcessoCertificacaoResponsavel(processoCertificacao.getProcesso().getProcessoPK()+"");
		
	return processoCertificacaoForm;
	}
	
	@Transactional
	public ProcessoCertificacaoForm converteProcessoCertificacaoView(ProcessoCertificacao processoCertificacao) {
	
		ProcessoCertificacaoForm processoCertificacaoForm = new ProcessoCertificacaoForm();
		
		processoCertificacaoForm.setProcessoCertificacaoPK(processoCertificacao.getProcessoCertificacaoPK());
		processoCertificacaoForm.setProcessoNome(processoCertificacao.getProcesso().getProcessoNome());
		processoCertificacaoForm.setCertificacaoNome(processoCertificacao.getCertificacao().getCertificacaoNome());

		processoCertificacaoForm.setProcessoCertificacaoMotivoOperacao(processoCertificacao.getProcessoCertificacaoMotivoOperacao());
		processoCertificacaoForm.setProcessoCertificacaoStatus(AtributoStatus.valueOf(processoCertificacao.getProcessoCertificacaoStatus()));

		processoCertificacaoForm.setProcessoCertificacaoResponsavel(processoCertificacao.getProcesso().getProcessoNome());
		
	return processoCertificacaoForm;
	}
	

	@Transactional
	public ProcessoCertificacaoForm processoCertificacaoParametros(ProcessoCertificacaoForm processoCertificacaoForm) {
	
		
		processoCertificacaoForm.setProcessoCertificacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return processoCertificacaoForm;
	}
	
}
