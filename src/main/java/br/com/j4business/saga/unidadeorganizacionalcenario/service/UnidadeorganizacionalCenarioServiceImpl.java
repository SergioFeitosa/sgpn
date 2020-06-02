package br.com.j4business.saga.unidadeorganizacionalcenario.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamentovideo.model.TreinamentoVideo;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenario;
import br.com.j4business.saga.cenario.model.Cenario;
import br.com.j4business.saga.cenario.service.CenarioService;
import br.com.j4business.saga.unidadeorganizacional.model.Unidadeorganizacional;
import br.com.j4business.saga.unidadeorganizacional.service.UnidadeorganizacionalService;
import br.com.j4business.saga.unidadeorganizacionalcenario.model.UnidadeorganizacionalCenarioForm;
import br.com.j4business.saga.unidadeorganizacionalcenario.repository.UnidadeorganizacionalCenarioRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("unidadeorganizacionalCenarioService")
public class UnidadeorganizacionalCenarioServiceImpl implements UnidadeorganizacionalCenarioService {

	@Autowired
	private UnidadeorganizacionalService unidadeorganizacionalService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UnidadeorganizacionalCenarioRepository unidadeorganizacionalCenarioRepository;

	@Autowired
	private CenarioService cenarioService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(UnidadeorganizacionalCenarioService.class.getName());


	@Override
	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome,Pageable pageable) {
		return unidadeorganizacionalCenarioRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getByCenarioNome(String cenarioNome,Pageable pageable) {
		return unidadeorganizacionalCenarioRepository.findByCenarioNome(cenarioNome,pageable);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalNome(String unidadeorganizacionalNome) {
		return unidadeorganizacionalCenarioRepository.findByUnidadeorganizacionalNome(unidadeorganizacionalNome);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getByCenarioNome(String cenarioNome) {
		return unidadeorganizacionalCenarioRepository.findByCenarioNome(cenarioNome);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getByCenarioPK(long cenarioPK,Pageable pageable) {
		return unidadeorganizacionalCenarioRepository.findByCenarioPK(cenarioPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getByUnidadeorganizacionalPK(long unidadeorganizacionalPK,Pageable pageable) {
		return unidadeorganizacionalCenarioRepository.findByUnidadeorganizacionalPK(unidadeorganizacionalPK,pageable);
	}

	@Override
	public List<UnidadeorganizacionalCenario> getUnidadeorganizacionalCenarioAll(Pageable pageable) {
		return unidadeorganizacionalCenarioRepository.findUnidadeorganizacionalCenarioAll(pageable);
	}

	@Override
	public UnidadeorganizacionalCenario getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(long unidadeorganizacionalCenarioPK) {
		return unidadeorganizacionalCenarioRepository.findOne(unidadeorganizacionalCenarioPK);
	}

	@Override
	public UnidadeorganizacionalCenario getByUnidadeorganizacionalAndCenario (Unidadeorganizacional unidadeorganizacional,Cenario cenario) {
		
		return unidadeorganizacionalCenarioRepository.findByUnidadeorganizacionalAndCenario(unidadeorganizacional,cenario);
	}

	@Transactional
	public UnidadeorganizacionalCenario create(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm) {
		
		UnidadeorganizacionalCenario unidadeorganizacionalCenario = new UnidadeorganizacionalCenario();
		
		unidadeorganizacionalCenario = this.converteUnidadeorganizacionalCenarioForm(unidadeorganizacionalCenarioForm);
		
		unidadeorganizacionalCenario = entityManager.merge(unidadeorganizacionalCenario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Cenario Create " + "\n Usuário => " + username + 
				" // Id => "+unidadeorganizacionalCenario.getUnidadeorganizacionalCenarioPK() + 
				" // Unidadeorganizacional Id => "+unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
				" // Cenario Id => "+unidadeorganizacionalCenario.getCenario().getCenarioPK()); 
		
		return unidadeorganizacionalCenario;
	}


	@Transactional
	public UnidadeorganizacionalCenario save(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm) {

		UnidadeorganizacionalCenario unidadeorganizacionalCenario = new UnidadeorganizacionalCenario();
		
		unidadeorganizacionalCenario = this.converteUnidadeorganizacionalCenarioForm(unidadeorganizacionalCenarioForm);
		
		unidadeorganizacionalCenario = entityManager.merge(unidadeorganizacionalCenario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalCenario Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalCenario.getUnidadeorganizacionalCenarioPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Cenario Id => "+unidadeorganizacionalCenario.getCenario().getCenarioPK());
		return unidadeorganizacionalCenario;
	}

	@Transactional
	public void delete(Long unidadeorganizacionalCenarioPK) {

		UnidadeorganizacionalCenario unidadeorganizacionalCenarioTemp = this.getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenarioPK);

		unidadeorganizacionalCenarioRepository.delete(unidadeorganizacionalCenarioPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("UnidadeorganizacionalCenario Save " + "\n Usuário => " + username + 
										" // Id => "+unidadeorganizacionalCenarioTemp.getUnidadeorganizacionalCenarioPK() + 
										" // Unidadeorganizacional Id => "+unidadeorganizacionalCenarioTemp.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
										" // Cenario Id => "+unidadeorganizacionalCenarioTemp.getCenario().getCenarioPK()); 
    }

	@Transactional
	public void deleteByCenario(Cenario cenario) {
		
		List<UnidadeorganizacionalCenario> unidadeorganizacionalCenarioList = unidadeorganizacionalCenarioRepository.findByCenario(cenario);

		unidadeorganizacionalCenarioRepository.delete(unidadeorganizacionalCenarioList);

		String username = usuarioSeguranca.getUsuarioLogado();

		unidadeorganizacionalCenarioList.forEach((UnidadeorganizacionalCenario unidadeorganizacionalCenario) -> {
			
			logger.info("UnidadeorganizacionalCenario Delete2 " + "\n Usuário => " + username + 
											" // Id => "+unidadeorganizacionalCenario.getUnidadeorganizacionalCenarioPK() + 
											" // Unidadeorganizacional Id => "+unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalPK() + 
											" // Cenario Id => "+unidadeorganizacionalCenario.getCenario().getCenarioPK()); 

		});
		
    }

	@Transactional
	public UnidadeorganizacionalCenario converteUnidadeorganizacionalCenarioForm(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm) {
		
		UnidadeorganizacionalCenario unidadeorganizacionalCenario = new UnidadeorganizacionalCenario();
		
		if (unidadeorganizacionalCenarioForm.getUnidadeorganizacionalCenarioPK() > 0) {
			unidadeorganizacionalCenario = this.getUnidadeorganizacionalCenarioByUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenarioForm.getUnidadeorganizacionalCenarioPK());
		}
		
		
		Cenario cenario = cenarioService.getCenarioByCenarioPK(Long.parseLong(unidadeorganizacionalCenarioForm.getCenarioNome()));
		unidadeorganizacionalCenario.setCenario(cenario);

		Unidadeorganizacional unidadeorganizacional = unidadeorganizacionalService.getUnidadeorganizacionalByUnidadeorganizacionalPK(Long.parseLong(unidadeorganizacionalCenarioForm.getUnidadeorganizacionalNome()));
		unidadeorganizacionalCenario.setUnidadeorganizacional(unidadeorganizacional);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(unidadeorganizacionalCenarioForm.getUnidadeorganizacionalCenarioResponsavel()));

		unidadeorganizacionalCenario.setColaborador(colaborador);
		
		return unidadeorganizacionalCenario;
	}

	@Transactional
	public UnidadeorganizacionalCenarioForm converteUnidadeorganizacionalCenario(UnidadeorganizacionalCenario unidadeorganizacionalCenario) {
	
		UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm = new UnidadeorganizacionalCenarioForm();
		
		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenario.getUnidadeorganizacionalCenarioPK());
		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalNome(unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalPK()+"");
		unidadeorganizacionalCenarioForm.setCenarioNome(unidadeorganizacionalCenario.getCenario().getCenarioPK()+"");

		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalCenarioResponsavel(unidadeorganizacionalCenario.getColaborador().getPessoaPK()+"");
		
	return unidadeorganizacionalCenarioForm;
	}
	
	@Transactional
	public UnidadeorganizacionalCenarioForm converteUnidadeorganizacionalCenarioView(UnidadeorganizacionalCenario unidadeorganizacionalCenario) {
	
		UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm = new UnidadeorganizacionalCenarioForm();
		
		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalCenarioPK(unidadeorganizacionalCenario.getUnidadeorganizacionalCenarioPK());
		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalNome(unidadeorganizacionalCenario.getUnidadeorganizacional().getUnidadeorganizacionalNome());
		unidadeorganizacionalCenarioForm.setCenarioNome(unidadeorganizacionalCenario.getCenario().getCenarioNome());

		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalCenarioResponsavel(unidadeorganizacionalCenario.getColaborador().getPessoaNome());
		
	return unidadeorganizacionalCenarioForm;
	}
	

	@Transactional
	public UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioParametros(UnidadeorganizacionalCenarioForm unidadeorganizacionalCenarioForm) {
		unidadeorganizacionalCenarioForm.setUnidadeorganizacionalCenarioStatus(AtributoStatus.valueOf("ATIVO"));
	return unidadeorganizacionalCenarioForm;
	}
	
}
