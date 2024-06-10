package br.com.j4business.saga.servico.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

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
import br.com.j4business.saga.processoatividade.model.ProcessoAtividade;
import br.com.j4business.saga.processoatividade.service.ProcessoAtividadeService;
import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servico.model.ServicoForm;
import br.com.j4business.saga.servico.repository.ServicoRepository;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;
import br.com.j4business.saga.servicoprocesso.service.ServicoProcessoService;

@Service("servicoService")
public class ServicoServiceImpl implements ServicoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoAtividadeService processoAtividadeService;

	@Autowired
	private ServicoProcessoService servicoProcessoService;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ServicoService.class.getName());

	@Override
	public List<Servico> getServicoAll() {
		return servicoRepository.findAll();
	}

	@Override
	public Page<Servico> getServicoAll(Pageable pageable) {
		return servicoRepository.findAll(pageable);
	}

	@Override
	public Servico getServicoByServicoPK(long servicoPK) {
		
		Optional<Servico> servico = servicoRepository.findById(servicoPK);
		return servico.get();
	}

	@Transactional
	public Servico create(ServicoForm servicoForm) {
		
		Servico servico = new Servico();
		
		servico = this.converteServicoForm(servicoForm);
		
		servico = entityManager.merge(servico);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Servico Create " + "\n Usuário => " + username + 
										" // Id => "+servico.getServicoPK() + 
										" // Servico => "+servico.getServicoNome() + 
										" // Descrição => "+ servico.getServicoDescricao());
		
		return servico;
	}

	@Transactional
	public Servico save(ServicoForm servicoForm) {
		
		Servico servico = new Servico();
		
		servico = this.converteServicoForm(servicoForm);
		
		servico = entityManager.merge(servico);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Servico Save " + "\n Usuário => " + username + 
										" // Id => "+servico.getServicoPK() + 
										" // Servico => "+servico.getServicoNome() + 
										" // Descrição => "+ servico.getServicoDescricao());
		

		return servico;
		
	}

	@Transactional
	public void delete(Long servicoId) {

		Servico servico = this.getServicoByServicoPK(servicoId);
		
		servicoRepository.delete(servico);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Servico Delete " + "\n Usuário => " + username + 
										" // Id => "+servico.getServicoPK() + 
										" // Servico => "+servico.getServicoNome() + 
										" // Descrição => "+ servico.getServicoDescricao());
		

    }

	@Transactional
	public Servico converteServicoForm(ServicoForm servicoForm) {
		
		Servico servico = new Servico();
		
		if (servicoForm.getServicoPK() > 0) {
			servico = this.getServicoByServicoPK(servicoForm.getServicoPK());
		}
		servico.setServicoNome(servicoForm.getServicoNome().replaceAll("\\s+"," "));
		servico.setServicoDescricao(servicoForm.getServicoDescricao().replaceAll("\\s+"," "));

		servico.setServicoStatus(servicoForm.getServicoStatus()+"");
		servico.setServicoMotivoOperacao(servicoForm.getServicoMotivoOperacao().replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(servicoForm.getServicoGestor()));
		servico.setColaboradorGestor(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(servicoForm.getServicoDono()));
		servico.setColaboradorDono(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(servicoForm.getServicoResponsavel()));
		servico.setColaboradorResponsavel(colaborador);
		
		return servico;
	}

	@Transactional
	public ServicoForm converteServico(Servico servico) {
	
		ServicoForm servicoForm = new ServicoForm();
		
		servicoForm.setServicoPK(servico.getServicoPK());
		servicoForm.setServicoNome(servico.getServicoNome());
		servicoForm.setServicoDescricao(servico.getServicoDescricao());
		servicoForm.setServicoGestor(servico.getColaboradorGestor().getPessoaPK()+"");
		servicoForm.setServicoDono(servico.getColaboradorDono().getPessoaPK()+"");
		servicoForm.setServicoResponsavel(servico.getColaboradorResponsavel().getPessoaPK()+"");
		servicoForm.setServicoMotivoOperacao(servico.getServicoMotivoOperacao());
		servicoForm.setServicoStatus(AtributoStatus.valueOf(servico.getServicoStatus()));

		
		List<ServicoProcesso> servicoProcessoList = servicoProcessoService.getByServicoPK(servico.getServicoPK());
		
		servicoProcessoList.forEach((ServicoProcesso servicoProcesso) -> {						
			
			List<ProcessoAtividade> processoAtividadeList = processoAtividadeService.getByProcessoPK(servicoProcesso.getProcesso().getProcessoPK());
			
			processoAtividadeList.forEach((ProcessoAtividade processoAtividade) -> { 
				servicoForm.setServicoDuracao(servicoForm.getServicoDuracao() + processoAtividade.getAtividade().getAtividadeDuracao());
				
			});
						
		});
		
	return servicoForm;
	}
	
	@Transactional
	public ServicoForm converteServicoView(Servico servico) {
	
		ServicoForm servicoForm = new ServicoForm();
		
		servicoForm.setServicoPK(servico.getServicoPK());
		servicoForm.setServicoNome(servico.getServicoNome());
		servicoForm.setServicoDescricao(servico.getServicoDescricao());
		servicoForm.setServicoGestor(servico.getColaboradorGestor().getPessoaNome()+"");
		servicoForm.setServicoDono(servico.getColaboradorDono().getPessoaNome()+"");
		servicoForm.setServicoResponsavel(servico.getColaboradorResponsavel().getPessoaNome()+"");
		servicoForm.setServicoMotivoOperacao(servico.getServicoMotivoOperacao());
		servicoForm.setServicoStatus(AtributoStatus.valueOf(servico.getServicoStatus()));
		
	return servicoForm;
	}
	

	@Transactional
	public ServicoForm servicoParametros(ServicoForm servicoForm) {
		servicoForm.setServicoStatus(AtributoStatus.valueOf("ATIVO"));
	return servicoForm;
	}

	@Override
	public List<Servico> getByServicoDescricao(String servicoDescricao,Pageable pageable) {
		return servicoRepository.findByServicoDescricao(servicoDescricao,pageable);
	}

	@Override
	public List<Servico> getByServicoNome(String servicoNome,Pageable pageable) {
		return servicoRepository.findByServicoNome(servicoNome,pageable);
	}


	@Override
	public List<Servico> getByServicoDescricao(String servicoDescricao) {
		return servicoRepository.findByServicoDescricao(servicoDescricao);
	}

	@Override
	public List<Servico> getByServicoNome(String servicoNome) {
		return servicoRepository.findByServicoNome(servicoNome);
	}



}
