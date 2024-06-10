package br.com.j4business.saga.fornecedorprocesso.service;

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
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcesso;
import br.com.j4business.saga.fornecedorprocesso.model.FornecedorProcessoForm;
import br.com.j4business.saga.fornecedorprocesso.repository.FornecedorProcessoRepository;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;

@Service("fornecedorProcessoService")
public class FornecedorProcessoServiceImpl implements FornecedorProcessoService {

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private FornecedorProcessoRepository fornecedorProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(FornecedorProcessoService.class.getName());


	@Override
	public List<FornecedorProcesso> getByFornecedorNome(String fornecedorNome,Pageable pageable) {
		return fornecedorProcessoRepository.findByFornecedorNome(fornecedorNome,pageable);
	}

	@Override
	public List<FornecedorProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return fornecedorProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<FornecedorProcesso> getByFornecedorNome(String fornecedorNome) {
		return fornecedorProcessoRepository.findByFornecedorNome(fornecedorNome);
	}

	@Override
	public List<FornecedorProcesso> getByProcessoNome(String processoNome) {
		return fornecedorProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<FornecedorProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return fornecedorProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<FornecedorProcesso> getByFornecedorPK(long fornecedorPK,Pageable pageable) {
		return fornecedorProcessoRepository.findByFornecedorPK(fornecedorPK,pageable);
	}

	@Override
	public List<FornecedorProcesso> getFornecedorProcessoAll(Pageable pageable) {
		return fornecedorProcessoRepository.findFornecedorProcessoAll(pageable);
	}

	@Override
	public FornecedorProcesso getFornecedorProcessoByFornecedorProcessoPK(long fornecedorProcessoPK) {
		Optional<FornecedorProcesso> fornecedorProcesso = fornecedorProcessoRepository.findById(fornecedorProcessoPK);
		return fornecedorProcesso.get();
	}

	@Override
	public FornecedorProcesso getByFornecedorAndProcesso (Fornecedor fornecedor,Processo processo) {
		
		return fornecedorProcessoRepository.findByFornecedorAndProcesso(fornecedor,processo);
	}

	@Transactional
	public FornecedorProcesso create(FornecedorProcessoForm fornecedorProcessoForm) {
		
		FornecedorProcesso fornecedorProcesso = new FornecedorProcesso();
		
		fornecedorProcesso = this.converteFornecedorProcessoForm(fornecedorProcessoForm);
		
		fornecedorProcesso = entityManager.merge(fornecedorProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+fornecedorProcesso.getFornecedorProcessoPK() + 
				" // Fornecedor Id => "+fornecedorProcesso.getFornecedor().getPessoaPK() + 
				" // Processo Id => "+fornecedorProcesso.getProcesso().getProcessoPK()); 
		
		return fornecedorProcesso;
	}

	@Transactional
	public FornecedorProcesso save(FornecedorProcessoForm fornecedorProcessoForm) {

		FornecedorProcesso fornecedorProcesso = new FornecedorProcesso();
		
		fornecedorProcesso = this.converteFornecedorProcessoForm(fornecedorProcessoForm);
		
		fornecedorProcesso = entityManager.merge(fornecedorProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("FornecedorProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+fornecedorProcesso.getFornecedorProcessoPK() + 
										" // Fornecedor Id => "+fornecedorProcesso.getFornecedor().getPessoaPK() + 
										" // Processo Id => "+fornecedorProcesso.getProcesso().getProcessoPK());
		return fornecedorProcesso;
	}

	@Transactional
	public void delete(Long fornecedorProcessoPK) {

		FornecedorProcesso fornecedorProcessoTemp = this.getFornecedorProcessoByFornecedorProcessoPK(fornecedorProcessoPK);

		fornecedorProcessoRepository.delete(fornecedorProcessoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("FornecedorProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+fornecedorProcessoTemp.getFornecedorProcessoPK() + 
										" // Fornecedor Id => "+fornecedorProcessoTemp.getFornecedor().getPessoaPK() + 
										" // Processo Id => "+fornecedorProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<FornecedorProcesso> fornecedorProcessoList = fornecedorProcessoRepository.findByProcesso(processo);

		for (FornecedorProcesso fornecedorProcesso2 : fornecedorProcessoList) {
			fornecedorProcessoRepository.delete(fornecedorProcesso2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		fornecedorProcessoList.forEach((FornecedorProcesso fornecedorProcesso) -> {

			logger.info("FornecedorProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+fornecedorProcesso.getFornecedorProcessoPK() + 
											" // Fornecedor Id => "+fornecedorProcesso.getFornecedor().getPessoaPK() + 
											" // Processo Id => "+fornecedorProcesso.getProcesso().getProcessoPK()); 

		});
		
    }

	@Transactional
	public FornecedorProcesso converteFornecedorProcessoForm(FornecedorProcessoForm fornecedorProcessoForm) {
		
		FornecedorProcesso fornecedorProcesso = new FornecedorProcesso();
		
		if (fornecedorProcessoForm.getFornecedorProcessoPK() > 0) {
			fornecedorProcesso = this.getFornecedorProcessoByFornecedorProcessoPK(fornecedorProcessoForm.getFornecedorProcessoPK());
		}
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(fornecedorProcessoForm.getProcessoNome()));
		fornecedorProcesso.setProcesso(processo);

		Fornecedor fornecedor = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(fornecedorProcessoForm.getFornecedorNome()));
		fornecedorProcesso.setFornecedor(fornecedor);

		fornecedorProcesso.setFornecedorProcessoMotivoOperacao(fornecedorProcessoForm.getFornecedorProcessoMotivoOperacao().replaceAll("\\s+"," "));
		fornecedorProcesso.setFornecedorProcessoStatus(fornecedorProcessoForm.getFornecedorProcessoStatus()+"".replaceAll("\\s+"," "));
		
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(fornecedorProcessoForm.getFornecedorProcessoResponsavel()));
		fornecedorProcesso.setColaboradorResponsavel(colaborador);
		
		return fornecedorProcesso;
	}

	@Transactional
	public FornecedorProcessoForm converteFornecedorProcesso(FornecedorProcesso fornecedorProcesso) {
	
		FornecedorProcessoForm fornecedorProcessoForm = new FornecedorProcessoForm();
		
		fornecedorProcessoForm.setFornecedorProcessoPK(fornecedorProcesso.getFornecedorProcessoPK());
		fornecedorProcessoForm.setFornecedorNome(fornecedorProcesso.getFornecedor().getPessoaPK()+"");
		fornecedorProcessoForm.setProcessoNome(fornecedorProcesso.getProcesso().getProcessoPK()+"");

		fornecedorProcessoForm.setFornecedorProcessoMotivoOperacao(fornecedorProcesso.getFornecedorProcessoMotivoOperacao());
		fornecedorProcessoForm.setFornecedorProcessoStatus(AtributoStatus.valueOf(fornecedorProcesso.getFornecedorProcessoStatus()));
		fornecedorProcessoForm.setFornecedorProcessoResponsavel(fornecedorProcesso.getColaboradorResponsavel().getPessoaNome());
		
	return fornecedorProcessoForm;
	}
	
	@Transactional
	public FornecedorProcessoForm converteFornecedorProcessoView(FornecedorProcesso fornecedorProcesso) {
	
		FornecedorProcessoForm fornecedorProcessoForm = new FornecedorProcessoForm();
		
		fornecedorProcessoForm.setFornecedorProcessoPK(fornecedorProcesso.getFornecedorProcessoPK());
		fornecedorProcessoForm.setFornecedorNome(fornecedorProcesso.getFornecedor().getPessoaNome());
		fornecedorProcessoForm.setProcessoNome(fornecedorProcesso.getProcesso().getProcessoNome());

		fornecedorProcessoForm.setFornecedorProcessoMotivoOperacao(fornecedorProcesso.getFornecedorProcessoMotivoOperacao());
		fornecedorProcessoForm.setFornecedorProcessoStatus(AtributoStatus.valueOf(fornecedorProcesso.getFornecedorProcessoStatus()));
		fornecedorProcessoForm.setFornecedorProcessoResponsavel(fornecedorProcesso.getColaboradorResponsavel().getPessoaNome());
		
	return fornecedorProcessoForm;
	}
	

	@Transactional
	public FornecedorProcessoForm fornecedorProcessoParametros(FornecedorProcessoForm fornecedorProcessoForm) {
	
		
		fornecedorProcessoForm.setFornecedorProcessoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return fornecedorProcessoForm;
	}
	

}
