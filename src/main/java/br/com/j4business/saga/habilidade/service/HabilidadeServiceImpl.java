package br.com.j4business.saga.habilidade.service;

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
import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.habilidade.model.HabilidadeForm;
import br.com.j4business.saga.habilidade.repository.HabilidadeRepository;

@Service("habilidadeService")
public class HabilidadeServiceImpl implements HabilidadeService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(HabilidadeService.class.getName());

	@Override
	public List<Habilidade> getHabilidadeAll() {
		return habilidadeRepository.findAll();
	}

	@Override
	public Page<Habilidade> getHabilidadeAll(Pageable pageable) {
		return habilidadeRepository.findAll(pageable);
	}

	@Override
	public Habilidade getHabilidadeByHabilidadePK(long habilidadePK) {
		
		return habilidadeRepository.findOne(habilidadePK);
	}

	@Transactional
	public Habilidade create(HabilidadeForm habilidadeForm) {
		
		Habilidade habilidade = new Habilidade();
		
		habilidade = this.converteHabilidadeForm(habilidadeForm);
		
		habilidade = entityManager.merge(habilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Habilidade Create " + "\n Usuário => " + username + 
										" // Id => "+habilidade.getHabilidadePK() + 
										" // Habilidade => "+habilidade.getHabilidadeNome() + 
										" // Descrição => "+ habilidade.getHabilidadeDescricao());
		
		return habilidade;
	}

	@Transactional
	public Habilidade save(HabilidadeForm habilidadeForm) {
		
		Habilidade habilidade = new Habilidade();
		
		habilidade = this.converteHabilidadeForm(habilidadeForm);
		
		habilidade = entityManager.merge(habilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Habilidade Save " + "\n Usuário => " + username + 
										" // Id => "+habilidade.getHabilidadePK() + 
										" // Habilidade => "+habilidade.getHabilidadeNome() + 
										" // Descrição => "+ habilidade.getHabilidadeDescricao());
		

		return habilidade;
		
	}

	@Transactional
	public void delete(Long habilidadeId) {

		Habilidade habilidade = this.getHabilidadeByHabilidadePK(habilidadeId);
		
		habilidadeRepository.delete(habilidade.getHabilidadePK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Habilidade Delete " + "\n Usuário => " + username + 
										" // Id => "+habilidade.getHabilidadePK() + 
										" // Habilidade => "+habilidade.getHabilidadeNome() + 
										" // Descrição => "+ habilidade.getHabilidadeDescricao());
		

    }

	@Transactional
	public Habilidade converteHabilidadeForm(HabilidadeForm habilidadeForm) {
		
		Habilidade habilidade = new Habilidade();
		
		if (habilidadeForm.getHabilidadePK() > 0) {
			habilidade = this.getHabilidadeByHabilidadePK(habilidadeForm.getHabilidadePK());
		}
		habilidade.setHabilidadeNome(habilidadeForm.getHabilidadeNome().replaceAll("\\s+"," "));
		habilidade.setHabilidadeDescricao(habilidadeForm.getHabilidadeDescricao().replaceAll("\\s+"," "));

		habilidade.setHabilidadeMotivoOperacao(habilidadeForm.getHabilidadeMotivoOperacao().replaceAll("\\s+"," "));
		habilidade.setHabilidadeStatus(habilidadeForm.getHabilidadeStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(habilidadeForm.getHabilidadeResponsavel()));
		habilidade.setColaboradorResponsavel(colaborador);
		
		return habilidade;
	}

	@Transactional
	public HabilidadeForm converteHabilidade(Habilidade habilidade) {
	
		HabilidadeForm habilidadeForm = new HabilidadeForm();
		
		habilidadeForm.setHabilidadePK(habilidade.getHabilidadePK());
		habilidadeForm.setHabilidadeNome(habilidade.getHabilidadeNome());
		habilidadeForm.setHabilidadeDescricao(habilidade.getHabilidadeDescricao());

		habilidadeForm.setHabilidadeMotivoOperacao(habilidade.getHabilidadeMotivoOperacao());
		habilidadeForm.setHabilidadeStatus(AtributoStatus.valueOf(habilidade.getHabilidadeStatus()));

		habilidadeForm.setHabilidadeResponsavel(habilidade.getColaboradorResponsavel().getPessoaPK()+"");
		
	return habilidadeForm;
	}
	
	@Transactional
	public HabilidadeForm converteHabilidadeView(Habilidade habilidade) {
	
		HabilidadeForm habilidadeForm = new HabilidadeForm();
		
		habilidadeForm.setHabilidadePK(habilidade.getHabilidadePK());
		habilidadeForm.setHabilidadeNome(habilidade.getHabilidadeNome());
		habilidadeForm.setHabilidadeDescricao(habilidade.getHabilidadeDescricao());

		habilidadeForm.setHabilidadeMotivoOperacao(habilidade.getHabilidadeMotivoOperacao());
		habilidadeForm.setHabilidadeStatus(AtributoStatus.valueOf(habilidade.getHabilidadeStatus()));

		habilidadeForm.setHabilidadeResponsavel(habilidade.getColaboradorResponsavel().getPessoaNome());
		
	return habilidadeForm;
	}
	

	@Transactional
	public HabilidadeForm habilidadeParametros(HabilidadeForm habilidadeForm) {
	
		
		habilidadeForm.setHabilidadeStatus(AtributoStatus.valueOf("ATIVO"));

		
	return habilidadeForm;
	}

	@Override
	public List<Habilidade> getByHabilidadeDescricao(String habilidadeDescricao,Pageable pageable) {
		return habilidadeRepository.findByHabilidadeDescricao(habilidadeDescricao,pageable);
	}

	@Override
	public List<Habilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable) {
		return habilidadeRepository.findByHabilidadeNome(habilidadeNome,pageable);
	}

	@Override
	public List<Habilidade> getByHabilidadeDescricao(String habilidadeDescricao) {
		return habilidadeRepository.findByHabilidadeDescricao(habilidadeDescricao);
	}

	@Override
	public List<Habilidade> getByHabilidadeNome(String habilidadeNome) {
		return habilidadeRepository.findByHabilidadeNome(habilidadeNome);
	}



}
