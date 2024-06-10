package br.com.j4business.saga.unidadeorganizacionalprocesso.service;

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
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcesso;
import br.com.j4business.saga.unidadeorganizacionalprocesso.model.UnidadeorganizacionalProcessoForm;
import br.com.j4business.saga.unidadeorganizacionalprocesso.repository.UnidadeorganizacionalProcessoRepository;

@Service("unidadeorganizacionalProcessoService")
public class UnidadeorganizacionalProcessoServiceImpl implements UnidadeorganizacionalProcessoService {

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UnidadeorganizacionalProcessoRepository unidadeorganizacionalProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(UnidadeorganizacionalProcessoService.class.getName());


	@Override
	public List<UnidadeorganizacionalProcesso> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable) {
		return unidadeorganizacionalProcessoRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return unidadeorganizacionalProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		return unidadeorganizacionalProcessoRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByProcessoNome(String processoNome) {
		return unidadeorganizacionalProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByProcessoPK(long processoPK) {
		return unidadeorganizacionalProcessoRepository.findByProcessoPK(processoPK);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return unidadeorganizacionalProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable) {
		return unidadeorganizacionalProcessoRepository.findByUnidadeorganizacionalPK(unidadeorganizacionalPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalProcesso> getUnidadeorganizacionalProcessoAll(Pageable pageable) {
		return unidadeorganizacionalProcessoRepository.findUnidadeorganizacionalProcessoAll(pageable);
	}

	@Override
	public UnidadeorganizacionalProcesso getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(long unidadeorganizacionalProcessoPK) {
		Optional<UnidadeorganizacionalProcesso> unidadeorganizacionalProcesso = unidadeorganizacionalProcessoRepository.findById(unidadeorganizacionalProcessoPK);
		return unidadeorganizacionalProcesso.get();
	}

	@Override
	public UnidadeorganizacionalProcesso getByUnidadeorganizacionalAndProcesso (Unidadeorganizacional unidadeorganizacional,Processo processo) {
		
		return unidadeorganizacionalProcessoRepository.findByUnidadeorganizacionalAndProcesso(unidadeorganizacional,processo);
	}

	@Transactional
	public UnidadeorganizacionalProcesso create(UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm) {
		
		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = new UnidadeorganizacionalProcesso();
		
		unidadeorganizacionalProcesso = this.converteUnidadeorganizacionalProcessoForm(unidadeorganizacionalProcessoForm);
		
		unidadeorganizacionalProcesso = entityManager.merge(unidadeorganizacionalProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoPK() + 
				" // Unidadeorganizacional Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
				" // Processo Id => "+unidadeorganizacionalProcesso.getProcesso().getProcessoPK()); 
		
		return unidadeorganizacionalProcesso;
	}

	
	@Transactional
	public UnidadeorganizacionalProcesso save(UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm) {

		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = new UnidadeorganizacionalProcesso();
		
		unidadeorganizacionalProcesso = this.converteUnidadeorganizacionalProcessoForm(unidadeorganizacionalProcessoForm);
		
		unidadeorganizacionalProcesso = entityManager.merge(unidadeorganizacionalProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Processo Id => "+unidadeorganizacionalProcesso.getProcesso().getProcessoPK());
		return unidadeorganizacionalProcesso;
	}

	@Transactional
	public void delete(Long unidadeorganizacionalProcessoPK) {

		UnidadeorganizacionalProcesso unidadeorganizacionalProcessoTemp = this.getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcessoPK);

		unidadeorganizacionalProcessoRepository.delete(unidadeorganizacionalProcessoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalProcessoTemp.getUnidadeorganizacionalProcessoPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalProcessoTemp.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Processo Id => "+unidadeorganizacionalProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<UnidadeorganizacionalProcesso> unidadeorganizacionalProcessoList = unidadeorganizacionalProcessoRepository.findByProcesso(processo);
		for (UnidadeorganizacionalProcesso unidadeorganizacionalProcesso2 : unidadeorganizacionalProcessoList) {
			unidadeorganizacionalProcessoRepository.delete(unidadeorganizacionalProcesso2);	
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		unidadeorganizacionalProcessoList.forEach((UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) -> {
			
			logger.info("UnidadeorganizacionalProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoPK() + 
											" // Unidadeorganizacional Id => "+unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
											" // Processo Id => "+unidadeorganizacionalProcesso.getProcesso().getProcessoPK()); 

		});
		
    }

	@Transactional
	public UnidadeorganizacionalProcesso converteUnidadeorganizacionalProcessoForm(UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm) {
		
		UnidadeorganizacionalProcesso unidadeorganizacionalProcesso = new UnidadeorganizacionalProcesso();
		
		if (unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoPK() > 0) {
			unidadeorganizacionalProcesso = this.getUnidadeorganizacionalProcessoByUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoPK());
		}
		
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(unidadeorganizacionalProcessoForm.getProcessoNome()));
		unidadeorganizacionalProcesso.setProcesso(processo);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(Long.parseLong(unidadeorganizacionalProcessoForm.getUnidadeorganizacionalNome()));
		unidadeorganizacionalProcesso.setUnidadeorganizacional(unidadeorganizacional);

		unidadeorganizacionalProcesso.setUnidadeorganizacionalProcessoMotivoOperacao(unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoMotivoOperacao().replaceAll("\\s+"," "));
		unidadeorganizacionalProcesso.setUnidadeorganizacionalProcessoStatus(unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(unidadeorganizacionalProcessoForm.getUnidadeorganizacionalProcessoResponsavel()));
		unidadeorganizacionalProcesso.setColaboradorResponsavel(colaborador);

		return unidadeorganizacionalProcesso;
	}

	@Transactional
	public UnidadeorganizacionalProcessoForm converteUnidadeorganizacionalProcesso(UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) {
	
		UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm = new UnidadeorganizacionalProcessoForm();
		
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoPK());
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalNome(unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalPK()+"");
		unidadeorganizacionalProcessoForm.setProcessoNome(unidadeorganizacionalProcesso.getProcesso().getProcessoPK()+"");

		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoMotivoOperacao(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoMotivoOperacao());
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoStatus(AtributoStatus.valueOf(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoStatus()));
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoResponsavel(unidadeorganizacionalProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return unidadeorganizacionalProcessoForm;
	}
	
	@Transactional
	public UnidadeorganizacionalProcessoForm converteUnidadeorganizacionalProcessoView(UnidadeorganizacionalProcesso unidadeorganizacionalProcesso) {
	
		UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm = new UnidadeorganizacionalProcessoForm();
		
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoPK(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoPK());
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalNome(unidadeorganizacionalProcesso.getUnidadeorganizacional().getUnidadeorganizacionalNome());
		unidadeorganizacionalProcessoForm.setProcessoNome(unidadeorganizacionalProcesso.getProcesso().getProcessoNome());

		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoMotivoOperacao(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoMotivoOperacao());
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoStatus(AtributoStatus.valueOf(unidadeorganizacionalProcesso.getUnidadeorganizacionalProcessoStatus()));
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoResponsavel(unidadeorganizacionalProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return unidadeorganizacionalProcessoForm;
	}
	

	@Transactional
	public UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoParametros(UnidadeorganizacionalProcessoForm unidadeorganizacionalProcessoForm) {
		unidadeorganizacionalProcessoForm.setUnidadeorganizacionalProcessoStatus(AtributoStatus.valueOf("ATIVO"));
	return unidadeorganizacionalProcessoForm;
	}

}
