package br.com.j4business.saga.estruturafisica.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
import br.com.j4business.saga.estruturafisica.model.Estruturafisica;
import br.com.j4business.saga.estruturafisica.model.EstruturafisicaForm;
import br.com.j4business.saga.estruturafisica.repository.EstruturafisicaRepository;

@Service("estruturafisicaService")
public class EstruturafisicaServiceImpl implements EstruturafisicaService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private EstruturafisicaRepository estruturafisicaRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EstruturafisicaService.class.getName());

	@Override
	public List<Estruturafisica> getEstruturafisicaAll() {
		return estruturafisicaRepository.findAll();
	}

	@Override
	public Page<Estruturafisica> getEstruturafisicaAll(Pageable pageable) {
		return estruturafisicaRepository.findAll(pageable);
	}

	@Override
	public Estruturafisica getEstruturafisicaByEstruturafisicaPK(long estruturafisicaPK) {
		
		return estruturafisicaRepository.findOne(estruturafisicaPK);
	}

	@Transactional
	public Estruturafisica create(EstruturafisicaForm estruturafisicaForm) {
		
		Estruturafisica estruturafisica = new Estruturafisica();
		
		estruturafisica = this.converteEstruturafisicaForm(estruturafisicaForm);
		
		estruturafisica = entityManager.merge(estruturafisica);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estruturafisica Create " + "\n Usuário => " + username + 
										" // Id => "+estruturafisica.getEstruturafisicaPK() + 
										" // Estruturafisica => "+estruturafisica.getEstruturafisicaNome() + 
										" // Descrição => "+ estruturafisica.getEstruturafisicaDescricao());
		
		return estruturafisica;
	}

	@Transactional
	public Estruturafisica save(EstruturafisicaForm estruturafisicaForm) {
		
		Estruturafisica estruturafisica = new Estruturafisica();
		
		estruturafisica = this.converteEstruturafisicaForm(estruturafisicaForm);
		
		estruturafisica = entityManager.merge(estruturafisica);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estruturafisica Save " + "\n Usuário => " + username + 
										" // Id => "+estruturafisica.getEstruturafisicaPK() + 
										" // Estruturafisica => "+estruturafisica.getEstruturafisicaNome() + 
										" // Descrição => "+ estruturafisica.getEstruturafisicaDescricao());
		

		return estruturafisica;
		
	}

	@Transactional
	public void delete(Long estruturafisicaId) {

		Estruturafisica estruturafisica = this.getEstruturafisicaByEstruturafisicaPK(estruturafisicaId);
		
		estruturafisicaRepository.delete(estruturafisica.getEstruturafisicaPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Estruturafisica Delete " + "\n Usuário => " + username + 
										" // Id => "+estruturafisica.getEstruturafisicaPK() + 
										" // Estruturafisica => "+estruturafisica.getEstruturafisicaNome() + 
										" // Descrição => "+ estruturafisica.getEstruturafisicaDescricao());
		

    }

	@Transactional
	public Estruturafisica converteEstruturafisicaForm(EstruturafisicaForm estruturafisicaForm) {
		
		Estruturafisica estruturafisica = new Estruturafisica();
		
		if (estruturafisicaForm.getEstruturafisicaPK() > 0) {
			estruturafisica = this.getEstruturafisicaByEstruturafisicaPK(estruturafisicaForm.getEstruturafisicaPK());
		}
		estruturafisica.setEstruturafisicaNome(estruturafisicaForm.getEstruturafisicaNome().replaceAll("\\s+"," "));
		estruturafisica.setEstruturafisicaDescricao(estruturafisicaForm.getEstruturafisicaDescricao().replaceAll("\\s+"," "));

		estruturafisica.setEstruturafisicaMotivoOperacao(estruturafisicaForm.getEstruturafisicaMotivoOperacao().replaceAll("\\s+"," "));
		estruturafisica.setEstruturafisicaStatus(estruturafisicaForm.getEstruturafisicaStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(estruturafisicaForm.getEstruturafisicaResponsavel()));
		estruturafisica.setColaboradorResponsavel(colaborador);

		return estruturafisica;
	}

	@Transactional
	public EstruturafisicaForm converteEstruturafisica(Estruturafisica estruturafisica) {
	
		EstruturafisicaForm estruturafisicaForm = new EstruturafisicaForm();
		
		estruturafisicaForm.setEstruturafisicaPK(estruturafisica.getEstruturafisicaPK());
		estruturafisicaForm.setEstruturafisicaNome(estruturafisica.getEstruturafisicaNome());
		estruturafisicaForm.setEstruturafisicaDescricao(estruturafisica.getEstruturafisicaDescricao());

		estruturafisicaForm.setEstruturafisicaMotivoOperacao(estruturafisica.getEstruturafisicaMotivoOperacao());
		estruturafisicaForm.setEstruturafisicaStatus(AtributoStatus.valueOf(estruturafisica.getEstruturafisicaStatus()));

		estruturafisicaForm.setEstruturafisicaResponsavel(estruturafisica.getColaboradorResponsavel().getPessoaPK()+"");
		
	return estruturafisicaForm;
	}
	
	@Transactional
	public EstruturafisicaForm converteEstruturafisicaView(Estruturafisica estruturafisica) {
	
		EstruturafisicaForm estruturafisicaForm = new EstruturafisicaForm();
		
		estruturafisicaForm.setEstruturafisicaPK(estruturafisica.getEstruturafisicaPK());
		estruturafisicaForm.setEstruturafisicaNome(estruturafisica.getEstruturafisicaNome());
		estruturafisicaForm.setEstruturafisicaDescricao(estruturafisica.getEstruturafisicaDescricao());

		estruturafisicaForm.setEstruturafisicaMotivoOperacao(estruturafisica.getEstruturafisicaMotivoOperacao());
		estruturafisicaForm.setEstruturafisicaStatus(AtributoStatus.valueOf(estruturafisica.getEstruturafisicaStatus()));

		estruturafisicaForm.setEstruturafisicaResponsavel(estruturafisica.getColaboradorResponsavel().getPessoaNome());
		
	return estruturafisicaForm;
	}
	

	@Transactional
	public EstruturafisicaForm estruturafisicaParametros(EstruturafisicaForm estruturafisicaForm) {
	
		
		estruturafisicaForm.setEstruturafisicaStatus(AtributoStatus.valueOf("ATIVO"));

		
	return estruturafisicaForm;
	}

	@Override
	public List<Estruturafisica> getByEstruturafisicaDescricao(String estruturafisicaDescricao,Pageable pageable) {
		return estruturafisicaRepository.findByEstruturafisicaDescricao(estruturafisicaDescricao,pageable);
	}

	@Override
	public List<Estruturafisica> getByEstruturafisicaNome(String estruturafisicaNome,Pageable pageable) {
		return estruturafisicaRepository.findByEstruturafisicaNome(estruturafisicaNome,pageable);
	}

	@Override
	public List<Estruturafisica> getByEstruturafisicaDescricao(String estruturafisicaDescricao) {
		return estruturafisicaRepository.findByEstruturafisicaDescricao(estruturafisicaDescricao);
	}

	@Override
	public List<Estruturafisica> getByEstruturafisicaNome(String estruturafisicaNome) {
		return estruturafisicaRepository.findByEstruturafisicaNome(estruturafisicaNome);
	}



}
