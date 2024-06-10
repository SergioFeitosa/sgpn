package br.com.j4business.saga.estruturafisicaunidadeorganizacional.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisica.service.EstruturafisicaService;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacional;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.model.EstruturafisicaUnidadeorganizacionalForm;
import br.com.j4business.saga.estruturafisicaunidadeorganizacional.repository.EstruturafisicaUnidadeorganizacionalRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("estruturafisicaUnidadeorganizacionalService")
public class EstruturafisicaUnidadeorganizacionalServiceImpl implements EstruturafisicaUnidadeorganizacionalService {

	@Autowired
	private EstruturafisicaService estruturafisicaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EstruturafisicaUnidadeorganizacionalRepository estruturafisicaUnidadeorganizacionalRepository;

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EstruturafisicaUnidadeorganizacionalService.class.getName());


	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaNome(String estruturafisicaNome,Pageable pageable) {
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaNome(estruturafisicaNome,pageable);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable) {
		return estruturafisicaUnidadeorganizacionalRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome,pageable);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaNome(String estruturafisicaNome) {
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaNome(estruturafisicaNome);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		return estruturafisicaUnidadeorganizacionalRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK) {
		return estruturafisicaUnidadeorganizacionalRepository.findByUnidadeorganizacionalPK(unidadeorganizacionalPK);
	}

	@Override
	public Page<EstruturafisicaUnidadeorganizacional> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable) {
		return estruturafisicaUnidadeorganizacionalRepository.findByUnidadeorganizacionalPK(unidadeorganizacionalPK,pageable);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaPK(long estruturafisicaPK,Pageable pageable) {
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaPK(estruturafisicaPK,pageable);
	}

	@Override
	public List<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaUnidadeorganizacionalAll() {
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaUnidadeorganizacionalAll();
	}

	@Override
	public Page<EstruturafisicaUnidadeorganizacional> getByEstruturafisicaUnidadeorganizacionalAll(Pageable pageable) {
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaUnidadeorganizacionalAll(pageable);
	}

	@Override
	public EstruturafisicaUnidadeorganizacional getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(long estruturafisicaUnidadeorganizacionalPK) {
		Optional<EstruturafisicaUnidadeorganizacional>  estruturafisicaUnidadeorganizacional = estruturafisicaUnidadeorganizacionalRepository.findById(estruturafisicaUnidadeorganizacionalPK);
		return estruturafisicaUnidadeorganizacional.get();
	}

	@Override
	public EstruturafisicaUnidadeorganizacional getByEstruturafisicaAndUnidadeorganizacional (Estruturafisica estruturafisica,Unidadeorganizacional unidadeorganizacional) {
		
		return estruturafisicaUnidadeorganizacionalRepository.findByEstruturafisicaAndUnidadeorganizacional(estruturafisica,unidadeorganizacional);
	}

	@Transactional
	public EstruturafisicaUnidadeorganizacional save(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm) {

		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = new EstruturafisicaUnidadeorganizacional();
		
		estruturafisicaUnidadeorganizacional = this.converteEstruturafisicaUnidadeorganizacionalForm(estruturafisicaUnidadeorganizacionalForm);
		
		estruturafisicaUnidadeorganizacional = entityManager.merge(estruturafisicaUnidadeorganizacional);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EstruturafisicaUnidadeorganizacional Save " + "\n Usuário => " + username + 
										" // Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalPK() + 
										" // Estruturafisica Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaPK() + 
										" // Unidadeorganizacional Id => "+estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalPK());
		return estruturafisicaUnidadeorganizacional;
	}

	@Transactional
	public void delete(Long estruturafisicaUnidadeorganizacionalPK) {

		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacionalTemp = this.getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacionalPK);

		estruturafisicaUnidadeorganizacionalRepository.delete(estruturafisicaUnidadeorganizacionalTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EstruturafisicaUnidadeorganizacional Save " + "\n Usuário => " + username + 
										" // Id => "+estruturafisicaUnidadeorganizacionalTemp.getEstruturafisicaUnidadeorganizacionalPK() + 
										" // Estruturafisica Id => "+estruturafisicaUnidadeorganizacionalTemp.getEstruturafisica().getEstruturafisicaPK() + 
										" // Unidadeorganizacional Id => "+estruturafisicaUnidadeorganizacionalTemp.getUnidadeorganizacional().getUnidadeorganizacionalPK()); 
    }

	@Transactional
	public void deleteByUnidadeorganizacional(Unidadeorganizacional unidadeorganizacional) {
		
		List<EstruturafisicaUnidadeorganizacional> estruturafisicaUnidadeorganizacionalList = estruturafisicaUnidadeorganizacionalRepository.findByUnidadeorganizacional(unidadeorganizacional);

		for (EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional2 : estruturafisicaUnidadeorganizacionalList) {

			estruturafisicaUnidadeorganizacionalRepository.delete(estruturafisicaUnidadeorganizacional2);
			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		estruturafisicaUnidadeorganizacionalList.forEach((EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional) -> {
			
			logger.info("EstruturafisicaUnidadeorganizacional Delete2 " + "\n Usuário => " + username + 
											" // Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalPK() + 
											" // Estruturafisica Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaPK() + 
											" // Unidadeorganizacional Id => "+estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalPK()); 

		});
		
    }

	@Transactional
	public EstruturafisicaUnidadeorganizacional converteEstruturafisicaUnidadeorganizacionalForm(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm) {
		
		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = new EstruturafisicaUnidadeorganizacional();
		
		if (estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalPK() > 0) {
			estruturafisicaUnidadeorganizacional = this.getEstruturafisicaUnidadeorganizacionalByEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalPK());
		}
		
		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(Long.parseLong(estruturafisicaUnidadeorganizacionalForm.getUnidadeorganizacionalNome()));
		estruturafisicaUnidadeorganizacional.setUnidadeorganizacional(unidadeorganizacional);

		Estruturafisica estruturafisica = estruturafisicaService.getEstruturafisicaByEstruturafisicaPK(Long.parseLong(estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaNome()));
		estruturafisicaUnidadeorganizacional.setEstruturafisica(estruturafisica);

		estruturafisicaUnidadeorganizacional.setEstruturafisicaUnidadeorganizacionalMotivoOperacao(estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalMotivoOperacao().replaceAll("\\s+"," "));
		estruturafisicaUnidadeorganizacional.setEstruturafisicaUnidadeorganizacionalStatus(estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(estruturafisicaUnidadeorganizacionalForm.getEstruturafisicaUnidadeorganizacionalResponsavel()));
		estruturafisicaUnidadeorganizacional.setColaboradorResponsavel(colaborador);
		
		return estruturafisicaUnidadeorganizacional;
	}

	@Transactional
	public EstruturafisicaUnidadeorganizacionalForm converteEstruturafisicaUnidadeorganizacional(EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional) {
	
		EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm = new EstruturafisicaUnidadeorganizacionalForm();
		
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalPK());
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaNome(estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaPK()+"");
		estruturafisicaUnidadeorganizacionalForm.setUnidadeorganizacionalNome(estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalPK()+"");

		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalMotivoOperacao(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalMotivoOperacao());
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalStatus(AtributoStatus.valueOf(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalStatus()));
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalResponsavel(estruturafisicaUnidadeorganizacional.getColaboradorResponsavel().getPessoaPK()+"");

		return estruturafisicaUnidadeorganizacionalForm;
	}
	
	@Transactional
	public EstruturafisicaUnidadeorganizacionalForm converteEstruturafisicaUnidadeorganizacionalView(EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional) {
	
		EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm = new EstruturafisicaUnidadeorganizacionalForm();
		
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalPK(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalPK());
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaNome(estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaNome());
		estruturafisicaUnidadeorganizacionalForm.setUnidadeorganizacionalNome(estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalNome());

		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalMotivoOperacao(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalMotivoOperacao());
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalStatus(AtributoStatus.valueOf(estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalStatus()));
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalResponsavel(estruturafisicaUnidadeorganizacional.getColaboradorResponsavel().getPessoaPK()+"");
		
	return estruturafisicaUnidadeorganizacionalForm;
	}
	

	@Transactional
	public EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalParametros(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm) {
	
		
		estruturafisicaUnidadeorganizacionalForm.setEstruturafisicaUnidadeorganizacionalStatus(AtributoStatus.valueOf("ATIVO"));

		
	return estruturafisicaUnidadeorganizacionalForm;
	}
	
	@Transactional
	public EstruturafisicaUnidadeorganizacional create(EstruturafisicaUnidadeorganizacionalForm estruturafisicaUnidadeorganizacionalForm) {
		
		EstruturafisicaUnidadeorganizacional estruturafisicaUnidadeorganizacional = new EstruturafisicaUnidadeorganizacional();
		
		estruturafisicaUnidadeorganizacional = this.converteEstruturafisicaUnidadeorganizacionalForm(estruturafisicaUnidadeorganizacionalForm);
		
		estruturafisicaUnidadeorganizacional = entityManager.merge(estruturafisicaUnidadeorganizacional);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Unidadeorganizacional Create " + "\n Usuário => " + username + 
				" // Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisicaUnidadeorganizacionalPK() + 
				" // Estruturafisica Id => "+estruturafisicaUnidadeorganizacional.getEstruturafisica().getEstruturafisicaPK() + 
				" // Unidadeorganizacional Id => "+estruturafisicaUnidadeorganizacional.getUnidadeorganizacional().getUnidadeorganizacionalPK()); 
		
		return estruturafisicaUnidadeorganizacional;
	}


}
