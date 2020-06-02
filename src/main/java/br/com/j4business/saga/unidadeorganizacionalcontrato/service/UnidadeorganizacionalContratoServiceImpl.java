package br.com.j4business.saga.unidadeorganizacionalcontrato.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContrato;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.unidadeorganizacionalcontrato.model.UnidadeorganizacionalContratoForm;
import br.com.j4business.saga.unidadeorganizacionalcontrato.repository.UnidadeorganizacionalContratoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("unidadeorganizacionalContratoService")
public class UnidadeorganizacionalContratoServiceImpl implements UnidadeorganizacionalContratoService {

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UnidadeorganizacionalContratoRepository unidadeorganizacionalContratoRepository;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(UnidadeorganizacionalContratoService.class.getName());


	@Override
	public List<UnidadeorganizacionalContrato> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable) {
		return unidadeorganizacionalContratoRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByContratoNome(String contratoNome,Pageable pageable) {
		return unidadeorganizacionalContratoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		return unidadeorganizacionalContratoRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByContratoNome(String contratoNome) {
		return unidadeorganizacionalContratoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByContratoPK(long contratoPK) {
		return unidadeorganizacionalContratoRepository.findByContratoPK(contratoPK);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByContratoPK(long contratoPK,Pageable pageable) {
		return unidadeorganizacionalContratoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable) {
		return unidadeorganizacionalContratoRepository.findByUnidadeorganizacionalPK(unidadeorganizacionalPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalContrato> getUnidadeorganizacionalContratoAll(Pageable pageable) {
		return unidadeorganizacionalContratoRepository.findUnidadeorganizacionalContratoAll(pageable);
	}

	@Override
	public UnidadeorganizacionalContrato getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(long unidadeorganizacionalContratoPK) {
		return unidadeorganizacionalContratoRepository.findOne(unidadeorganizacionalContratoPK);
	}

	@Override
	public UnidadeorganizacionalContrato getByUnidadeorganizacionalAndContrato (Unidadeorganizacional unidadeorganizacional,Contrato contrato) {
		
		return unidadeorganizacionalContratoRepository.findByUnidadeorganizacionalAndContrato(unidadeorganizacional,contrato);
	}

	@Transactional
	public UnidadeorganizacionalContrato create(UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm) {
		
		UnidadeorganizacionalContrato unidadeorganizacionalContrato = new UnidadeorganizacionalContrato();
		
		unidadeorganizacionalContrato = this.converteUnidadeorganizacionalContratoForm(unidadeorganizacionalContratoForm);
		
		unidadeorganizacionalContrato = entityManager.merge(unidadeorganizacionalContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Create " + "\n Usuário => " + username + 
				" // Id => "+unidadeorganizacionalContrato.getUnidadeorganizacionalContratoPK() + 
				" // Unidadeorganizacional Id => "+unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
				" // Contrato Id => "+unidadeorganizacionalContrato.getContrato().getContratoPK()); 
		
		return unidadeorganizacionalContrato;
	}

	
	@Transactional
	public UnidadeorganizacionalContrato save(UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm) {

		UnidadeorganizacionalContrato unidadeorganizacionalContrato = new UnidadeorganizacionalContrato();
		
		unidadeorganizacionalContrato = this.converteUnidadeorganizacionalContratoForm(unidadeorganizacionalContratoForm);
		
		unidadeorganizacionalContrato = entityManager.merge(unidadeorganizacionalContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalContrato Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalContrato.getUnidadeorganizacionalContratoPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Contrato Id => "+unidadeorganizacionalContrato.getContrato().getContratoPK());
		return unidadeorganizacionalContrato;
	}

	@Transactional
	public void delete(Long unidadeorganizacionalContratoPK) {

		UnidadeorganizacionalContrato unidadeorganizacionalContratoTemp = this.getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(unidadeorganizacionalContratoPK);

		unidadeorganizacionalContratoRepository.delete(unidadeorganizacionalContratoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalContrato Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalContratoTemp.getUnidadeorganizacionalContratoPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalContratoTemp.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Contrato Id => "+unidadeorganizacionalContratoTemp.getContrato().getContratoPK()); 
    }

	@Transactional
	public void deleteByContrato(Contrato contrato) {
		
		List<UnidadeorganizacionalContrato> unidadeorganizacionalContratoList = unidadeorganizacionalContratoRepository.findByContrato(contrato);

		unidadeorganizacionalContratoRepository.delete(unidadeorganizacionalContratoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		unidadeorganizacionalContratoList.forEach((UnidadeorganizacionalContrato unidadeorganizacionalContrato) -> {
			
			logger.info("UnidadeorganizacionalContrato Delete2 " + "\n Usuário => " + username + 
											" // Id => "+unidadeorganizacionalContrato.getUnidadeorganizacionalContratoPK() + 
											" // Unidadeorganizacional Id => "+unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
											" // Contrato Id => "+unidadeorganizacionalContrato.getContrato().getContratoPK()); 

		});
		
    }

	@Transactional
	public UnidadeorganizacionalContrato converteUnidadeorganizacionalContratoForm(UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm) {
		
		UnidadeorganizacionalContrato unidadeorganizacionalContrato = new UnidadeorganizacionalContrato();
		
		if (unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoPK() > 0) {
			unidadeorganizacionalContrato = this.getUnidadeorganizacionalContratoByUnidadeorganizacionalContratoPK(unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoPK());
		}
		
		
		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(unidadeorganizacionalContratoForm.getContratoNome()));
		unidadeorganizacionalContrato.setContrato(contrato);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(Long.parseLong(unidadeorganizacionalContratoForm.getUnidadeorganizacionalNome()));
		unidadeorganizacionalContrato.setUnidadeorganizacional(unidadeorganizacional);

		unidadeorganizacionalContrato.setUnidadeorganizacionalContratoMotivoOperacao(unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoMotivoOperacao().replaceAll("\\s+"," "));
		unidadeorganizacionalContrato.setUnidadeorganizacionalContratoStatus(unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(unidadeorganizacionalContratoForm.getUnidadeorganizacionalContratoResponsavel()));
		unidadeorganizacionalContrato.setColaboradorResponsavel(colaborador);

		return unidadeorganizacionalContrato;
	}

	@Transactional
	public UnidadeorganizacionalContratoForm converteUnidadeorganizacionalContrato(UnidadeorganizacionalContrato unidadeorganizacionalContrato) {
	
		UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm = new UnidadeorganizacionalContratoForm();
		
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoPK(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoPK());
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalNome(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalPK()+"");
		unidadeorganizacionalContratoForm.setContratoNome(unidadeorganizacionalContrato.getContrato().getContratoPK()+"");

		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoMotivoOperacao(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoMotivoOperacao());
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoStatus(AtributoStatus.valueOf(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoStatus()));
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoResponsavel(unidadeorganizacionalContrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return unidadeorganizacionalContratoForm;
	}
	
	@Transactional
	public UnidadeorganizacionalContratoForm converteUnidadeorganizacionalContratoView(UnidadeorganizacionalContrato unidadeorganizacionalContrato) {
	
		UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm = new UnidadeorganizacionalContratoForm();
		
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoPK(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoPK());
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalNome(unidadeorganizacionalContrato.getUnidadeorganizacional().getUnidadeorganizacionalNome());
		unidadeorganizacionalContratoForm.setContratoNome(unidadeorganizacionalContrato.getContrato().getContratoNome());

		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoMotivoOperacao(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoMotivoOperacao());
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoStatus(AtributoStatus.valueOf(unidadeorganizacionalContrato.getUnidadeorganizacionalContratoStatus()));
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoResponsavel(unidadeorganizacionalContrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return unidadeorganizacionalContratoForm;
	}
	

	@Transactional
	public UnidadeorganizacionalContratoForm unidadeorganizacionalContratoParametros(UnidadeorganizacionalContratoForm unidadeorganizacionalContratoForm) {
		unidadeorganizacionalContratoForm.setUnidadeorganizacionalContratoStatus(AtributoStatus.valueOf("ATIVO"));
	return unidadeorganizacionalContratoForm;
	}

}
