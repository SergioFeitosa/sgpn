package br.com.j4business.saga.fornecedorcontrato.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContratoForm;
import br.com.j4business.saga.fornecedorcontrato.repository.FornecedorContratoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("fornecedorContratoService")
public class FornecedorContratoServiceImpl implements FornecedorContratoService {

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private FornecedorContratoRepository fornecedorContratoRepository;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(FornecedorContratoService.class.getName());


	@Override
	public List<FornecedorContrato> getByFornecedorNome(String fornecedorNome,Pageable pageable) {
		return fornecedorContratoRepository.findByFornecedorNome(fornecedorNome,pageable);
	}

	@Override
	public List<FornecedorContrato> getByContratoNome(String contratoNome,Pageable pageable) {
		return fornecedorContratoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<FornecedorContrato> getByFornecedorNome(String fornecedorNome) {
		return fornecedorContratoRepository.findByFornecedorNome(fornecedorNome);
	}

	@Override
	public List<FornecedorContrato> getByContratoNome(String contratoNome) {
		return fornecedorContratoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<FornecedorContrato> getByContratoPK(long contratoPK,Pageable pageable) {
		return fornecedorContratoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<FornecedorContrato> getByContratoPK(long contratoPK) {
		return fornecedorContratoRepository.findByContratoPK(contratoPK);
	}

	@Override
	public List<FornecedorContrato> getByFornecedorPK(long fornecedorPK,Pageable pageable) {
		return fornecedorContratoRepository.findByFornecedorPK(fornecedorPK,pageable);
	}

	@Override
	public List<FornecedorContrato> getByFornecedorPK(long fornecedorPK) {
		return fornecedorContratoRepository.findByFornecedorPK(fornecedorPK);
	}

	@Override
	public List<FornecedorContrato> getFornecedorContratoAll(Pageable pageable) {
		return fornecedorContratoRepository.findFornecedorContratoAll(pageable);
	}

	@Override
	public FornecedorContrato getFornecedorContratoByFornecedorContratoPK(long fornecedorContratoPK) {
		return fornecedorContratoRepository.findOne(fornecedorContratoPK);
	}

	@Override
	public FornecedorContrato getByFornecedorAndContrato (Fornecedor fornecedor,Contrato contrato) {
		
		return fornecedorContratoRepository.findByFornecedorAndContrato(fornecedor,contrato);
	}

	@Transactional
	public FornecedorContrato create(FornecedorContratoForm fornecedorContratoForm) {
		
		FornecedorContrato fornecedorContrato = new FornecedorContrato();
		
		fornecedorContrato = this.converteFornecedorContratoForm(fornecedorContratoForm);
		
		fornecedorContrato = entityManager.merge(fornecedorContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Create " + "\n Usuário => " + username + 
				" // Id => "+fornecedorContrato.getFornecedorContratoPK() + 
				" // Fornecedor Id => "+fornecedorContrato.getFornecedor().getPessoaPK() + 
				" // Contrato Id => "+fornecedorContrato.getContrato().getContratoPK()); 
		
		return fornecedorContrato;
	}


	@Transactional
	public FornecedorContrato save(FornecedorContratoForm fornecedorContratoForm) {

		FornecedorContrato fornecedorContrato = new FornecedorContrato();
		
		fornecedorContrato = this.converteFornecedorContratoForm(fornecedorContratoForm);
		
		fornecedorContrato = entityManager.merge(fornecedorContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("FornecedorContrato Save " + "\n Usuário => " + username + 
										" // Id => "+fornecedorContrato.getFornecedorContratoPK() + 
										" // Fornecedor Id => "+fornecedorContrato.getFornecedor().getPessoaPK() + 
										" // Contrato Id => "+fornecedorContrato.getContrato().getContratoPK());
		return fornecedorContrato;
	}

	@Transactional
	public void delete(Long fornecedorContratoPK) {

		FornecedorContrato fornecedorContratoTemp = this.getFornecedorContratoByFornecedorContratoPK(fornecedorContratoPK);

		fornecedorContratoRepository.delete(fornecedorContratoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("FornecedorContrato Save " + "\n Usuário => " + username + 
										" // Id => "+fornecedorContratoTemp.getFornecedorContratoPK() + 
										" // Fornecedor Id => "+fornecedorContratoTemp.getFornecedor().getPessoaPK() + 
										" // Contrato Id => "+fornecedorContratoTemp.getContrato().getContratoPK()); 
    }

	@Transactional
	public void deleteByContrato(Contrato contrato) {
		
		List<FornecedorContrato> fornecedorContratoList = fornecedorContratoRepository.findByContrato(contrato);

		fornecedorContratoRepository.delete(fornecedorContratoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		fornecedorContratoList.forEach((FornecedorContrato fornecedorContrato) -> {
			
			logger.info("FornecedorContrato Delete2 " + "\n Usuário => " + username + 
											" // Id => "+fornecedorContrato.getFornecedorContratoPK() + 
											" // Fornecedor Id => "+fornecedorContrato.getFornecedor().getPessoaPK() + 
											" // Contrato Id => "+fornecedorContrato.getContrato().getContratoPK()); 

		});
		
    }

	@Transactional
	public FornecedorContrato converteFornecedorContratoForm(FornecedorContratoForm fornecedorContratoForm) {
		
		FornecedorContrato fornecedorContrato = new FornecedorContrato();
		
		if (fornecedorContratoForm.getFornecedorContratoPK() > 0) {
			fornecedorContrato = this.getFornecedorContratoByFornecedorContratoPK(fornecedorContratoForm.getFornecedorContratoPK());
		}
		
		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(fornecedorContratoForm.getContratoNome()));
		fornecedorContrato.setContrato(contrato);

		Fornecedor fornecedor = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(fornecedorContratoForm.getFornecedorNome()));
		fornecedorContrato.setFornecedor(fornecedor);

		fornecedorContrato.setFornecedorContratoMotivoOperacao(fornecedorContratoForm.getFornecedorContratoMotivoOperacao().replaceAll("\\s+"," "));
		fornecedorContrato.setFornecedorContratoStatus(fornecedorContratoForm.getFornecedorContratoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(fornecedorContratoForm.getFornecedorContratoResponsavel()));
		fornecedorContrato.setColaboradorResponsavel(colaborador);
		
		return fornecedorContrato;
	}

	@Transactional
	public FornecedorContratoForm converteFornecedorContrato(FornecedorContrato fornecedorContrato) {
	
		FornecedorContratoForm fornecedorContratoForm = new FornecedorContratoForm();
		
		fornecedorContratoForm.setFornecedorContratoPK(fornecedorContrato.getFornecedorContratoPK());
		fornecedorContratoForm.setFornecedorNome(fornecedorContrato.getFornecedor().getPessoaPK()+"");
		fornecedorContratoForm.setContratoNome(fornecedorContrato.getContrato().getContratoPK()+"");

		fornecedorContratoForm.setFornecedorContratoMotivoOperacao(fornecedorContrato.getFornecedorContratoMotivoOperacao());
		fornecedorContratoForm.setFornecedorContratoStatus(AtributoStatus.valueOf(fornecedorContrato.getFornecedorContratoStatus()));
		fornecedorContratoForm.setFornecedorContratoResponsavel(fornecedorContrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return fornecedorContratoForm;
	}
	
	@Transactional
	public FornecedorContratoForm converteFornecedorContratoView(FornecedorContrato fornecedorContrato) {
	
		FornecedorContratoForm fornecedorContratoForm = new FornecedorContratoForm();
		
		fornecedorContratoForm.setFornecedorContratoPK(fornecedorContrato.getFornecedorContratoPK());
		fornecedorContratoForm.setFornecedorNome(fornecedorContrato.getFornecedor().getPessoaNome());
		fornecedorContratoForm.setContratoNome(fornecedorContrato.getContrato().getContratoNome());

		fornecedorContratoForm.setFornecedorContratoMotivoOperacao(fornecedorContrato.getFornecedorContratoMotivoOperacao());
		fornecedorContratoForm.setFornecedorContratoStatus(AtributoStatus.valueOf(fornecedorContrato.getFornecedorContratoStatus()));
		fornecedorContratoForm.setFornecedorContratoResponsavel(fornecedorContrato.getColaboradorResponsavel().getPessoaNome());
		
	return fornecedorContratoForm;
	}
	

	@Transactional
	public FornecedorContratoForm fornecedorContratoParametros(FornecedorContratoForm fornecedorContratoForm) {
	
		
		fornecedorContratoForm.setFornecedorContratoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return fornecedorContratoForm;
	}
	

}
