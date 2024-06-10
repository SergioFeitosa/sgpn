package br.com.j4business.saga.servicoprocesso.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.servico.model.Servico;
import br.com.j4business.saga.servico.service.ServicoService;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcesso;
import br.com.j4business.saga.servicoprocesso.model.ServicoProcessoForm;
import br.com.j4business.saga.servicoprocesso.repository.ServicoProcessoRepository;

@Service("servicoProcessoService")
public class ServicoProcessoServiceImpl implements ServicoProcessoService {

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ServicoProcessoRepository servicoProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ServicoProcessoService.class.getName());

	@Override
	public List<ServicoProcesso> getByServicoNome(String servicoNome,Pageable pageable) {
		return servicoProcessoRepository.findByServicoNome(servicoNome,pageable);
	}

	@Override
	public List<ServicoProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return servicoProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ServicoProcesso> getByServicoNome(String servicoNome) {
		return servicoProcessoRepository.findByServicoNome(servicoNome);
	}

	@Override
	public List<ServicoProcesso> getByProcessoNome(String processoNome) {
		return servicoProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ServicoProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return servicoProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ServicoProcesso> getByServicoPK(long servicoPK,Pageable pageable) {
		return servicoProcessoRepository.findByServicoPK(servicoPK,pageable);
	}

	@Override
	public List<ServicoProcesso> getByServicoPK(long servicoPK) {
		return servicoProcessoRepository.findByServicoPK(servicoPK);
	}

	@Override
	public List<ServicoProcesso> getServicoProcessoAll(Pageable pageable) {
		return servicoProcessoRepository.findServicoProcessoAll(pageable);
	}

	@Override
	public ServicoProcesso getServicoProcessoByServicoProcessoPK(long servicoProcessoPK) {
		Optional<ServicoProcesso> servicoProcesso = servicoProcessoRepository.findById(servicoProcessoPK);
		return servicoProcesso.get();
	}

	@Override
	public ServicoProcesso getByServicoAndProcesso (Servico servico,Processo processo) {
		
		return servicoProcessoRepository.findByServicoAndProcesso(servico,processo);
	}

	@Transactional
	public ServicoProcesso create(ServicoProcessoForm servicoProcessoForm) {
		
		ServicoProcesso servicoProcesso = new ServicoProcesso();
		
		servicoProcesso = this.converteServicoProcessoForm(servicoProcessoForm);
		
		servicoProcesso = entityManager.merge(servicoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+servicoProcesso.getServicoProcessoPK() + 
				" // Servico Id => "+servicoProcesso.getServico().getServicoPK() + 
				" // Processo Id => "+servicoProcesso.getProcesso().getProcessoPK() + 
				" // Valor => "+servicoProcesso.getServicoProcessoSequencia()); 
		
		return servicoProcesso;
	}


	@Transactional
	public ServicoProcesso save(ServicoProcessoForm servicoProcessoForm) {

		ServicoProcesso servicoProcesso = new ServicoProcesso();
		
		servicoProcesso = this.converteServicoProcessoForm(servicoProcessoForm);
		
		servicoProcesso = entityManager.merge(servicoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ServicoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+servicoProcesso.getServicoProcessoPK() + 
										" // Servico Id => "+servicoProcesso.getServico().getServicoPK() + 
										" // Processo Id => "+servicoProcesso.getProcesso().getProcessoPK() + 
										" // Valor => "+servicoProcesso.getServicoProcessoSequencia()); 
		return servicoProcesso;
	}

	@Transactional
	public void delete(Long servicoProcessoPK) {

		ServicoProcesso servicoProcessoTemp = this.getServicoProcessoByServicoProcessoPK(servicoProcessoPK);

		servicoProcessoRepository.delete(servicoProcessoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ServicoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+servicoProcessoTemp.getServicoProcessoPK() + 
										" // Servico Id => "+servicoProcessoTemp.getServico().getServicoPK() + 
										" // Processo Id => "+servicoProcessoTemp.getProcesso().getProcessoPK() + 
										" // Valor => "+servicoProcessoTemp.getServicoProcessoSequencia()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<ServicoProcesso> servicoProcessoList = servicoProcessoRepository.findByProcesso(processo);

		for (ServicoProcesso servicoProcesso2 : servicoProcessoList) {
			servicoProcessoRepository.delete(servicoProcesso2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		servicoProcessoList.forEach((ServicoProcesso servicoProcesso) -> {
			
			logger.info("ServicoProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+servicoProcesso.getServicoProcessoPK() + 
											" // Servico Id => "+servicoProcesso.getServico().getServicoPK() + 
											" // Processo Id => "+servicoProcesso.getProcesso().getProcessoPK() + 
											" // Valor => "+servicoProcesso.getServicoProcessoSequencia()); 
		});
		
    }

	@Transactional
	public ServicoProcesso converteServicoProcessoForm(ServicoProcessoForm servicoProcessoForm) {
		
		ServicoProcesso servicoProcesso = new ServicoProcesso();
		
		if (servicoProcessoForm.getServicoProcessoPK() > 0) {
			servicoProcesso = this.getServicoProcessoByServicoProcessoPK(servicoProcessoForm.getServicoProcessoPK());
		}
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(servicoProcessoForm.getProcessoNome()));
		servicoProcesso.setProcesso(processo);
		Servico servico = servicoService.getServicoByServicoPK(Long.parseLong(servicoProcessoForm.getServicoNome()));
		servicoProcesso.setServico(servico);
		servicoProcesso.setServicoProcessoSequencia(Integer.parseInt(servicoProcessoForm.getServicoProcessoSequencia()+"".replaceAll("\\s+"," ")));
		servicoProcesso.setServicoProcessoStatus(servicoProcessoForm.getServicoProcessoStatus()+"".replaceAll("\\s+"," "));
		servicoProcesso.setServicoProcessoMotivoOperacao(servicoProcessoForm.getServicoProcessoMotivoOperacao()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(servicoProcessoForm.getServicoProcessoResponsavel()));
		servicoProcesso.setColaboradorResponsavel(colaborador);

		return servicoProcesso;
	}

	@Transactional
	public ServicoProcessoForm converteServicoProcesso(ServicoProcesso servicoProcesso) {
	
		ServicoProcessoForm servicoProcessoForm = new ServicoProcessoForm();
		
		servicoProcessoForm.setServicoProcessoPK(servicoProcesso.getServicoProcessoPK());
		servicoProcessoForm.setServicoNome(servicoProcesso.getServico().getServicoPK()+"");
		servicoProcessoForm.setProcessoNome(servicoProcesso.getProcesso().getProcessoPK()+"");
		servicoProcessoForm.setServicoProcessoSequencia(servicoProcesso.getServicoProcessoSequencia()+"");

		servicoProcessoForm.setServicoProcessoStatus(AtributoStatus.valueOf(servicoProcesso.getServicoProcessoStatus()+""));
		servicoProcessoForm.setServicoProcessoMotivoOperacao(servicoProcesso.getServicoProcessoMotivoOperacao()+"");

		servicoProcessoForm.setServicoProcessoResponsavel(servicoProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return servicoProcessoForm;
	}
	
	@Transactional
	public ServicoProcessoForm converteServicoProcessoView(ServicoProcesso servicoProcesso) {
	
		ServicoProcessoForm servicoProcessoForm = new ServicoProcessoForm();
		
		servicoProcessoForm.setServicoProcessoPK(servicoProcesso.getServicoProcessoPK());
		servicoProcessoForm.setServicoNome(servicoProcesso.getServico().getServicoNome());
		servicoProcessoForm.setProcessoNome(servicoProcesso.getProcesso().getProcessoNome());
		servicoProcessoForm.setServicoProcessoSequencia(servicoProcesso.getServicoProcessoSequencia()+"");

		servicoProcessoForm.setServicoProcessoStatus(AtributoStatus.valueOf(servicoProcesso.getServicoProcessoStatus()+""));
		servicoProcessoForm.setServicoProcessoMotivoOperacao(servicoProcesso.getServicoProcessoMotivoOperacao()+"");

		servicoProcessoForm.setServicoProcessoResponsavel(servicoProcesso.getColaboradorResponsavel().getPessoaNome());
		
	return servicoProcessoForm;
	}
	

	@Transactional
	public ServicoProcessoForm servicoProcessoParametros(ServicoProcessoForm servicoProcessoForm) {
	
		
		servicoProcessoForm.setServicoProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return servicoProcessoForm;
	}
	
}
