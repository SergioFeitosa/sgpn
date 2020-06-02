package br.com.j4business.saga.colaboradorprocesso.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcesso;
import br.com.j4business.saga.colaboradorprocesso.model.ColaboradorProcessoForm;
import br.com.j4business.saga.colaboradorprocesso.repository.ColaboradorProcessoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorProcessoService")
public class ColaboradorProcessoServiceImpl implements ColaboradorProcessoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorProcessoRepository colaboradorProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorProcessoService.class.getName());


	@Override
	public List<ColaboradorProcesso> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorProcessoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return colaboradorProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ColaboradorProcesso> getByColaboradorNome(String colaboradorNome) {
		return colaboradorProcessoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorProcesso> getByProcessoNome(String processoNome) {
		return colaboradorProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ColaboradorProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return colaboradorProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ColaboradorProcesso> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorProcessoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorProcesso> getColaboradorProcessoAll(Pageable pageable) {
		return colaboradorProcessoRepository.findColaboradorProcessoAll(pageable);
	}

	@Override
	public ColaboradorProcesso getColaboradorProcessoByColaboradorProcessoPK(long colaboradorProcessoPK) {
		return colaboradorProcessoRepository.findOne(colaboradorProcessoPK);
	}

	@Override
	public ColaboradorProcesso getByColaboradorAndProcesso (Colaborador colaborador,Processo processo) {
		
		return colaboradorProcessoRepository.findByColaboradorAndProcesso(colaborador,processo);
	}

	@Transactional
	public ColaboradorProcesso save(ColaboradorProcessoForm colaboradorProcessoForm) {

		ColaboradorProcesso colaboradorProcesso = new ColaboradorProcesso();
		
		colaboradorProcesso = this.converteColaboradorProcessoForm(colaboradorProcessoForm);
		
		colaboradorProcesso = entityManager.merge(colaboradorProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorProcesso.getColaboradorProcessoPK() + 
										" // Colaborador Id => "+colaboradorProcesso.getColaborador().getPessoaPK() + 
										" // Processo Id => "+colaboradorProcesso.getProcesso().getProcessoPK());
		return colaboradorProcesso;
	}

	@Transactional
	public void delete(Long colaboradorProcessoPK) {

		ColaboradorProcesso colaboradorProcessoTemp = this.getColaboradorProcessoByColaboradorProcessoPK(colaboradorProcessoPK);

		colaboradorProcessoRepository.delete(colaboradorProcessoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorProcessoTemp.getColaboradorProcessoPK() + 
										" // Colaborador Id => "+colaboradorProcessoTemp.getColaborador().getPessoaPK() + 
										" // Processo Id => "+colaboradorProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<ColaboradorProcesso> colaboradorProcessoList = colaboradorProcessoRepository.findByProcesso(processo);

		colaboradorProcessoRepository.delete(colaboradorProcessoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorProcessoList.forEach((ColaboradorProcesso colaboradorProcesso) -> {

			logger.info("ColaboradorProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorProcesso.getColaboradorProcessoPK() + 
											" // Colaborador Id => "+colaboradorProcesso.getColaborador().getPessoaPK() + 
											" // Processo Id => "+colaboradorProcesso.getProcesso().getProcessoPK()); 
		});
		
    }

	@Transactional
	public ColaboradorProcesso converteColaboradorProcessoForm(ColaboradorProcessoForm colaboradorProcessoForm) {
		
		ColaboradorProcesso colaboradorProcesso = new ColaboradorProcesso();
		
		if (colaboradorProcessoForm.getColaboradorProcessoPK() > 0) {
			colaboradorProcesso = this.getColaboradorProcessoByColaboradorProcessoPK(colaboradorProcessoForm.getColaboradorProcessoPK());
		}
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(colaboradorProcessoForm.getProcessoNome()));
		colaboradorProcesso.setProcesso(processo);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorProcessoForm.getColaboradorNome()));
		colaboradorProcesso.setColaborador(colaborador);

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorProcessoForm.getColaboradorProcessoResponsavel()));
		colaboradorProcesso.setColaboradorResponsavel(colaboradorResponsavel);

		colaboradorProcesso.setColaboradorProcessoMotivoOperacao(colaboradorProcessoForm.getColaboradorProcessoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorProcesso.setColaboradorProcessoStatus(colaboradorProcessoForm.getColaboradorProcessoStatus()+"".replaceAll("\\s+"," "));
		
		colaboradorProcesso.setColaboradorProcessoDataInicio(colaboradorProcessoForm.getColaboradorProcessoDataInicio());
		colaboradorProcesso.setColaboradorProcessoDataTermino(colaboradorProcessoForm.getColaboradorProcessoDataTermino());
		
		return colaboradorProcesso;
	}

	@Transactional
	public ColaboradorProcessoForm converteColaboradorProcesso(ColaboradorProcesso colaboradorProcesso) {
	
		ColaboradorProcessoForm colaboradorProcessoForm = new ColaboradorProcessoForm();
		
		colaboradorProcessoForm.setColaboradorProcessoPK(colaboradorProcesso.getColaboradorProcessoPK());
		colaboradorProcessoForm.setColaboradorNome(colaboradorProcesso.getColaborador().getPessoaPK()+"");
		colaboradorProcessoForm.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoPK()+"");

		colaboradorProcessoForm.setColaboradorProcessoResponsavel(colaboradorProcesso.getColaborador().getPessoaPK()+"");

		colaboradorProcessoForm.setColaboradorProcessoMotivoOperacao(colaboradorProcesso.getColaboradorProcessoMotivoOperacao());
		colaboradorProcessoForm.setColaboradorProcessoStatus(AtributoStatus.valueOf(colaboradorProcesso.getColaboradorProcessoStatus()));
		
		colaboradorProcessoForm.setColaboradorProcessoDataInicio(colaboradorProcesso.getColaboradorProcessoDataInicio());
		colaboradorProcessoForm.setColaboradorProcessoDataTermino(colaboradorProcesso.getColaboradorProcessoDataTermino());
		
	return colaboradorProcessoForm;
	}
	
	@Transactional
	public ColaboradorProcessoForm converteColaboradorProcessoView(ColaboradorProcesso colaboradorProcesso) {
	
		ColaboradorProcessoForm colaboradorProcessoForm = new ColaboradorProcessoForm();
		
		colaboradorProcessoForm.setColaboradorProcessoPK(colaboradorProcesso.getColaboradorProcessoPK());
		colaboradorProcessoForm.setColaboradorNome(colaboradorProcesso.getColaborador().getPessoaNome());
		colaboradorProcessoForm.setProcessoNome(colaboradorProcesso.getProcesso().getProcessoNome());

		colaboradorProcessoForm.setColaboradorProcessoMotivoOperacao(colaboradorProcesso.getColaboradorProcessoMotivoOperacao());
		colaboradorProcessoForm.setColaboradorProcessoStatus(AtributoStatus.valueOf(colaboradorProcesso.getColaboradorProcessoStatus()));

		colaboradorProcessoForm.setColaboradorProcessoResponsavel(colaboradorProcesso.getColaborador().getPessoaNome());

		colaboradorProcessoForm.setColaboradorProcessoDataInicio(colaboradorProcesso.getColaboradorProcessoDataInicio());
		colaboradorProcessoForm.setColaboradorProcessoDataTermino(colaboradorProcesso.getColaboradorProcessoDataTermino());
		
	return colaboradorProcessoForm;
	}
	

	@Transactional
	public ColaboradorProcessoForm colaboradorProcessoParametros(ColaboradorProcessoForm colaboradorProcessoForm) {
	
		
		colaboradorProcessoForm.setColaboradorProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorProcessoForm;
	}
	
	@Transactional
	public ColaboradorProcesso create(ColaboradorProcessoForm colaboradorProcessoForm) {
		
		ColaboradorProcesso colaboradorProcesso = new ColaboradorProcesso();
		
		colaboradorProcesso = this.converteColaboradorProcessoForm(colaboradorProcessoForm);
		
		colaboradorProcesso = entityManager.merge(colaboradorProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorProcesso.getColaboradorProcessoPK() + 
				" // Colaborador Id => "+colaboradorProcesso.getColaborador().getPessoaPK() + 
				" // Processo Id => "+colaboradorProcesso.getProcesso().getProcessoPK()); 
		
		return colaboradorProcesso;
	}


}
