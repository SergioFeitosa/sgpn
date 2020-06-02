package br.com.j4business.saga.processo.service;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcesso;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.model.ProcessoForm;
import br.com.j4business.saga.processo.repository.ProcessoRepository;
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processoatividade.service.ProcessoAtividadeService;

@Service("processoService")
public class ProcessoServiceImpl implements ProcessoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoAtividadeService processoAtividadeService;

	@Autowired
	private ProcessoRepository processoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoService.class.getName());

	@Override
	public List<Processo> getProcessoAll() {
		return processoRepository.findAll();
	}

	@Override
	public Page<Processo> getProcessoAll(Pageable pageable) {
		return processoRepository.findAll(pageable);
	}

	@Override
	public Processo getProcessoByProcessoPK(long processoPK) {
		
		return processoRepository.findOne(processoPK);
	}

	@Transactional
	public Processo create(ProcessoForm processoForm) {
		
		Processo processo = new Processo();
		
		processo = this.converteProcessoForm(processoForm);
		
		processo = entityManager.merge(processo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
										" // Id => "+processo.getProcessoPK() + 
										" // Processo => "+processo.getProcessoNome() + 
										" // Descrição => "+ processo.getProcessoDescricao());
		
		return processo;
	}

	@Transactional
	public Processo save(ProcessoForm processoForm) {
		
		Processo processo = new Processo();
		
		processo = this.converteProcessoForm(processoForm);
		
		processo = entityManager.merge(processo);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Save " + "\n Usuário => " + username + 
										" // Id => "+processo.getProcessoPK() + 
										" // Processo => "+processo.getProcessoNome() + 
										" // Descrição => "+ processo.getProcessoDescricao());
		

		return processo;
		
	}

	@Transactional
	public void delete(Long processoId) {

		Processo processo = this.getProcessoByProcessoPK(processoId);
		
		processoRepository.delete(processo.getProcessoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Delete " + "\n Usuário => " + username + 
										" // Id => "+processo.getProcessoPK() + 
										" // Processo => "+processo.getProcessoNome() + 
										" // Descrição => "+ processo.getProcessoDescricao());
		

    }

	@Transactional
	public Processo converteProcessoForm(ProcessoForm processoForm) {
		
		Processo processo = new Processo();
		
		if (processoForm.getProcessoPK() > 0) {
			processo = this.getProcessoByProcessoPK(processoForm.getProcessoPK());
		}
		processo.setProcessoNome(processoForm.getProcessoNome().replaceAll("\\s+"," "));
		processo.setProcessoDescricao(processoForm.getProcessoDescricao().replaceAll("\\s+"," "));

		processo.setProcessoStatus(processoForm.getProcessoStatus()+"".replaceAll("  "," "));
		processo.setProcessoMotivoOperacao(processoForm.getProcessoMotivoOperacao().replaceAll("  "," "));

		Colaborador colaboradorGestor = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoForm.getProcessoGestor()));
		processo.setColaboradorGestor(colaboradorGestor);

		Colaborador colaboradorDono = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoForm.getProcessoDono()));
		processo.setColaboradorDono(colaboradorDono);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoForm.getProcessoResponsavel()));
		processo.setColaboradorResponsavel(colaborador);

		return processo;
	}

	@Transactional
	public ProcessoForm converteProcesso(Processo processo) {
	
		ProcessoForm processoForm = new ProcessoForm();
		
		processoForm.setProcessoPK(processo.getProcessoPK());
		processoForm.setProcessoNome(processo.getProcessoNome());
		processoForm.setProcessoDescricao(processo.getProcessoDescricao());
		processoForm.setProcessoStatus(AtributoStatus.valueOf(processo.getProcessoStatus()));
		processoForm.setProcessoMotivoOperacao(processo.getProcessoMotivoOperacao());

		processoForm.setProcessoGestor(processo.getColaboradorGestor().getPessoaPK()+"");
		processoForm.setProcessoDono(processo.getColaboradorDono().getPessoaPK()+"");
		processoForm.setProcessoResponsavel(processo.getColaboradorResponsavel().getPessoaPK()+"");
		
		List<ProcessoAtividade> processoAtividadeList = processoAtividadeService.getByProcessoPK(processo.getProcessoPK());
		
		processoAtividadeList.forEach((ProcessoAtividade processoAtividade) -> 
			
			processoForm.setProcessoDuracao(processoForm.getProcessoDuracao() + processoAtividade.getAtividade().getAtividadeDuracao())

		);
		
	return processoForm;
	}
	
	@Transactional
	public ProcessoForm converteProcessoView(Processo processo) {
	
		ProcessoForm processoForm = new ProcessoForm();
		
		processoForm.setProcessoPK(processo.getProcessoPK());
		processoForm.setProcessoNome(processo.getProcessoNome());
		processoForm.setProcessoDescricao(processo.getProcessoDescricao());
		processoForm.setProcessoStatus(AtributoStatus.valueOf(processo.getProcessoStatus()));
		processoForm.setProcessoMotivoOperacao(processo.getProcessoMotivoOperacao());

		processoForm.setProcessoResponsavel(processo.getColaboradorResponsavel().getPessoaNome()+"");
		
		processoForm.setProcessoGestor(processo.getColaboradorGestor().getPessoaNome()+"");

		processoForm.setProcessoDono(processo.getColaboradorDono().getPessoaNome()+"");

		List<ProcessoAtividade> processoAtividadeList = processoAtividadeService.getByProcessoPK(processo.getProcessoPK());
		
		processoAtividadeList.forEach((ProcessoAtividade processoAtividade) -> 

			processoForm.setProcessoDuracao(processoForm.getProcessoDuracao() + processoAtividade.getAtividade().getAtividadeDuracao())
			
		);
		
	return processoForm;
	}
	

	@Transactional
	public ProcessoForm processoParametros(ProcessoForm processoForm) {
	
		
		processoForm.setProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return processoForm;
	}

	@Override
	public List<Processo> getByProcessoDescricao(String processoDescricao,Pageable pageable) {
		return processoRepository.findByProcessoDescricao(processoDescricao,pageable);
	}

	@Override
	public List<Processo> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<Processo> getByProcessoDescricao(String processoDescricao) {
		return processoRepository.findByProcessoDescricao(processoDescricao);
	}

	@Override
	public List<Processo> getByProcessoNome(String processoNome) {
		return processoRepository.findByProcessoNome(processoNome);
	}



}
