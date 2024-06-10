package br.com.j4business.saga.planejamentoprocesso.service;

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
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcesso;
import br.com.j4business.saga.planejamentoprocesso.model.PlanejamentoProcessoForm;
import br.com.j4business.saga.planejamentoprocesso.repository.PlanejamentoProcessoRepository;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;

@Service("planejamentoProcessoService")
public class PlanejamentoProcessoServiceImpl implements PlanejamentoProcessoService {

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private PlanejamentoProcessoRepository planejamentoProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(PlanejamentoProcessoService.class.getName());


	@Override
	public List<PlanejamentoProcesso> getByPlanejamentoNome(String planejamentoNome,Pageable pageable) {
		return planejamentoProcessoRepository.findByPlanejamentoNome(planejamentoNome,pageable);
	}

	@Override
	public List<PlanejamentoProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return planejamentoProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<PlanejamentoProcesso> getByPlanejamentoNome(String planejamentoNome) {
		return planejamentoProcessoRepository.findByPlanejamentoNome(planejamentoNome);
	}

	@Override
	public List<PlanejamentoProcesso> getByProcessoNome(String processoNome) {
		return planejamentoProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<PlanejamentoProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return planejamentoProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<PlanejamentoProcesso> getByPlanejamentoPK(long planejamentoPK,Pageable pageable) {
		return planejamentoProcessoRepository.findByPlanejamentoPK(planejamentoPK,pageable);
	}

	@Override
	public List<PlanejamentoProcesso> getPlanejamentoProcessoAll(Pageable pageable) {
		return planejamentoProcessoRepository.findPlanejamentoProcessoAll(pageable);
	}

	@Override
	public PlanejamentoProcesso getPlanejamentoProcessoByPlanejamentoProcessoPK(long planejamentoProcessoPK) {
		Optional<PlanejamentoProcesso> planejamentoProcesso = planejamentoProcessoRepository.findById(planejamentoProcessoPK);
		return planejamentoProcesso.get();
	}

	@Override
	public PlanejamentoProcesso getByPlanejamentoAndProcesso (Planejamento planejamento,Processo processo) {
		
		return planejamentoProcessoRepository.findByPlanejamentoAndProcesso(planejamento,processo);
	}

	@Transactional
	public PlanejamentoProcesso save(PlanejamentoProcessoForm planejamentoProcessoForm) {

		PlanejamentoProcesso planejamentoProcesso = new PlanejamentoProcesso();
		
		planejamentoProcesso = this.convertePlanejamentoProcessoForm(planejamentoProcessoForm);
		
		planejamentoProcesso = entityManager.merge(planejamentoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("PlanejamentoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+planejamentoProcesso.getPlanejamentoProcessoPK() + 
										" // Planejamento Id => "+planejamentoProcesso.getPlanejamento().getPlanejamentoPK() + 
										" // Processo Id => "+planejamentoProcesso.getProcesso().getProcessoPK());
		return planejamentoProcesso;
	}

	@Transactional
	public void delete(Long planejamentoProcessoPK) {

		PlanejamentoProcesso planejamentoProcessoTemp = this.getPlanejamentoProcessoByPlanejamentoProcessoPK(planejamentoProcessoPK);

		planejamentoProcessoRepository.delete(planejamentoProcessoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("PlanejamentoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+planejamentoProcessoTemp.getPlanejamentoProcessoPK() + 
										" // Planejamento Id => "+planejamentoProcessoTemp.getPlanejamento().getPlanejamentoPK() + 
										" // Processo Id => "+planejamentoProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<PlanejamentoProcesso> planejamentoProcessoList = planejamentoProcessoRepository.findByProcesso(processo);

		for (PlanejamentoProcesso planejamentoProcesso2 : planejamentoProcessoList) {
			planejamentoProcessoRepository.delete(planejamentoProcesso2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		planejamentoProcessoList.forEach((PlanejamentoProcesso planejamentoProcesso) -> {
			
			logger.info("PlanejamentoProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+planejamentoProcesso.getPlanejamentoProcessoPK() + 
											" // Planejamento Id => "+planejamentoProcesso.getPlanejamento().getPlanejamentoPK() + 
											" // Processo Id => "+planejamentoProcesso.getProcesso().getProcessoPK()); 
		});
    }

	@Transactional
	public PlanejamentoProcesso convertePlanejamentoProcessoForm(PlanejamentoProcessoForm planejamentoProcessoForm) {
		
		PlanejamentoProcesso planejamentoProcesso = new PlanejamentoProcesso();
		
		if (planejamentoProcessoForm.getPlanejamentoProcessoPK() > 0) {
			planejamentoProcesso = this.getPlanejamentoProcessoByPlanejamentoProcessoPK(planejamentoProcessoForm.getPlanejamentoProcessoPK());
		}
		
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(planejamentoProcessoForm.getProcessoNome()));
		planejamentoProcesso.setProcesso(processo);

		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(Long.parseLong(planejamentoProcessoForm.getPlanejamentoNome()));
		planejamentoProcesso.setPlanejamento(planejamento);

		planejamentoProcesso.setPlanejamentoProcessoMotivoOperacao(planejamentoProcessoForm.getPlanejamentoProcessoMotivoOperacao().replaceAll("\\s+"," "));
		planejamentoProcesso.setPlanejamentoProcessoStatus(planejamentoProcessoForm.getPlanejamentoProcessoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(planejamentoProcessoForm.getPlanejamentoProcessoResponsavel()));
		planejamentoProcesso.setColaboradorResponsavel(colaborador);
		
		return planejamentoProcesso;
	}

	@Transactional
	public PlanejamentoProcessoForm convertePlanejamentoProcesso(PlanejamentoProcesso planejamentoProcesso) {
	
		PlanejamentoProcessoForm planejamentoProcessoForm = new PlanejamentoProcessoForm();
		
		planejamentoProcessoForm.setPlanejamentoProcessoPK(planejamentoProcesso.getPlanejamentoProcessoPK());
		planejamentoProcessoForm.setPlanejamentoNome(planejamentoProcesso.getPlanejamento().getPlanejamentoPK()+"");
		planejamentoProcessoForm.setProcessoNome(planejamentoProcesso.getProcesso().getProcessoPK()+"");

		planejamentoProcessoForm.setPlanejamentoProcessoMotivoOperacao(planejamentoProcesso.getPlanejamentoProcessoMotivoOperacao());
		planejamentoProcessoForm.setPlanejamentoProcessoStatus(AtributoStatus.valueOf(planejamentoProcesso.getPlanejamentoProcessoStatus()));

		planejamentoProcessoForm.setPlanejamentoProcessoResponsavel(planejamentoProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return planejamentoProcessoForm;
	}
	
	@Transactional
	public PlanejamentoProcessoForm convertePlanejamentoProcessoView(PlanejamentoProcesso planejamentoProcesso) {
	
		PlanejamentoProcessoForm planejamentoProcessoForm = new PlanejamentoProcessoForm();
		
		planejamentoProcessoForm.setPlanejamentoProcessoPK(planejamentoProcesso.getPlanejamentoProcessoPK());
		planejamentoProcessoForm.setPlanejamentoNome(planejamentoProcesso.getPlanejamento().getPlanejamentoNome());
		planejamentoProcessoForm.setProcessoNome(planejamentoProcesso.getProcesso().getProcessoNome());

		planejamentoProcessoForm.setPlanejamentoProcessoMotivoOperacao(planejamentoProcesso.getPlanejamentoProcessoMotivoOperacao());
		planejamentoProcessoForm.setPlanejamentoProcessoStatus(AtributoStatus.valueOf(planejamentoProcesso.getPlanejamentoProcessoStatus()));

		planejamentoProcessoForm.setPlanejamentoProcessoResponsavel(planejamentoProcesso.getColaboradorResponsavel().getPessoaNome());
		
	return planejamentoProcessoForm;
	}
	

	@Transactional
	public PlanejamentoProcessoForm planejamentoProcessoParametros(PlanejamentoProcessoForm planejamentoProcessoForm) {
	
		
		planejamentoProcessoForm.setPlanejamentoProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return planejamentoProcessoForm;
	}
	
	@Transactional
	public PlanejamentoProcesso create(PlanejamentoProcessoForm planejamentoProcessoForm) {
		
		PlanejamentoProcesso planejamentoProcesso = new PlanejamentoProcesso();
		
		planejamentoProcesso = this.convertePlanejamentoProcessoForm(planejamentoProcessoForm);
		
		planejamentoProcesso = entityManager.merge(planejamentoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+planejamentoProcesso.getPlanejamentoProcessoPK() + 
				" // Planejamento Id => "+planejamentoProcesso.getPlanejamento().getPlanejamentoPK() + 
				" // Processo Id => "+planejamentoProcesso.getProcesso().getProcessoPK()); 
		
		return planejamentoProcesso;
	}


}
