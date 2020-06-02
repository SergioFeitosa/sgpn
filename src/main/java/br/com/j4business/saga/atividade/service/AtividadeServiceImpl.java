package br.com.j4business.saga.atividade.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
import br.com.j4business.saga.atividade.model.Atividade;
import br.com.j4business.saga.atividade.model.AtividadeForm;
import br.com.j4business.saga.atividade.repository.AtividadeRepository;

@Service("atividadeService")
public class AtividadeServiceImpl implements AtividadeService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AtividadeService.class.getName());

	@Override
	public List<Atividade> getAtividadeAll() {
		return atividadeRepository.findAll();
	}

	@Override
	public Page<Atividade> getAtividadeAll(Pageable pageable) {
		return atividadeRepository.findAll(pageable);
	}

	@Override
	public Atividade getAtividadeByAtividadePK(long atividadePK) {
		
		return atividadeRepository.findOne(atividadePK);
	}

	@Transactional
	public Atividade create(AtividadeForm atividadeForm) throws  PersistenceException{
		
		Atividade atividade = new Atividade();

		atividade = this.converteAtividadeForm(atividadeForm);
		
		atividade = entityManager.merge(atividade);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atividade Create " + "\n Usuário => " + username + 
										" // Id => "+atividade.getAtividadePK() + 
										" // Atividade => "+atividade.getAtividadeNome() + 
										" // Descrição => "+ atividade.getAtividadeDescricao());
		
		return atividade;
	}

	@Transactional
	public Atividade save(AtividadeForm atividadeForm) {
		
		Atividade atividade = new Atividade();

		atividade = this.converteAtividadeForm(atividadeForm);
		
		atividade = entityManager.merge(atividade);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atividade Save " + "\n Usuário => " + username + 
										" // Id => "+atividade.getAtividadePK() + 
										" // Atividade => "+atividade.getAtividadeNome() + 
										" // Descrição => "+ atividade.getAtividadeDescricao());
		

		return atividade;
		
	}

	@Transactional
	public void delete(Long atividadeId) {

		Atividade atividade = this.getAtividadeByAtividadePK(atividadeId);
		
		atividadeRepository.delete(atividade.getAtividadePK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atividade Delete " + "\n Usuário => " + username + 
										" // Id => "+atividade.getAtividadePK() + 
										" // Atividade => "+atividade.getAtividadeNome() + 
										" // Descrição => "+ atividade.getAtividadeDescricao());
		

    }

	@Transactional
	public Atividade converteAtividadeForm(AtividadeForm atividadeForm) throws PersistenceException{
		
		Atividade atividade = new Atividade();
		
		if (atividadeForm.getAtividadePK() > 0) {
			atividade = this.getAtividadeByAtividadePK(atividadeForm.getAtividadePK());
		}
		
		atividade.setAtividadeNome(atividadeForm.getAtividadeNome().replaceAll("\\s+"," "));
		atividade.setAtividadeDescricao(atividadeForm.getAtividadeDescricao().replaceAll("\\s+"," "));
		atividade.setAtividadeMotivoOperacao(atividadeForm.getAtividadeMotivoOperacao());
		atividade.setAtividadeStatus(atividadeForm.getAtividadeStatus()+"");

		Colaborador colaboradorGestor = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(atividadeForm.getAtividadeGestor()));
		atividade.setColaboradorGestor(colaboradorGestor);

		Colaborador colaboradorDono = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(atividadeForm.getAtividadeDono()));
		atividade.setColaboradorDono(colaboradorDono);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(atividadeForm.getAtividadeResponsavel()));
		atividade.setColaboradorResponsavel(colaborador);
		
		atividade.setAtividadeDuracao(atividadeForm.getAtividadeDuracao());

		return atividade;
	}

	@Transactional
	public AtividadeForm converteAtividade(Atividade atividade) {
	
		AtividadeForm atividadeForm = new AtividadeForm();
		
		atividadeForm.setAtividadePK(atividade.getAtividadePK());
		atividadeForm.setAtividadeNome(atividade.getAtividadeNome());
		atividadeForm.setAtividadeDescricao(atividade.getAtividadeDescricao());
		atividadeForm.setAtividadeMotivoOperacao(atividade.getAtividadeMotivoOperacao());
		atividadeForm.setAtividadeStatus(AtributoStatus.valueOf(atividade.getAtividadeStatus()));

		atividadeForm.setAtividadeGestor(atividade.getColaboradorGestor().getPessoaPK()+"");
		atividadeForm.setAtividadeDono(atividade.getColaboradorDono().getPessoaPK()+"");
		atividadeForm.setAtividadeResponsavel(atividade.getColaboradorResponsavel().getPessoaPK()+"");

		atividadeForm.setAtividadeDuracao(atividade.getAtividadeDuracao());
		
	return atividadeForm;
	}
	

	@Transactional
	public AtividadeForm converteAtividadeView(Atividade atividade) {
	
		AtividadeForm atividadeForm = new AtividadeForm();
		
		atividadeForm.setAtividadePK(atividade.getAtividadePK());
		atividadeForm.setAtividadeNome(atividade.getAtividadeNome());
		atividadeForm.setAtividadeDescricao(atividade.getAtividadeDescricao());
		atividadeForm.setAtividadeMotivoOperacao(atividade.getAtividadeMotivoOperacao());
		atividadeForm.setAtividadeStatus(AtributoStatus.valueOf(atividade.getAtividadeStatus()));

		atividadeForm.setAtividadeGestor(atividade.getColaboradorGestor().getPessoaNome());
		atividadeForm.setAtividadeDono(atividade.getColaboradorDono().getPessoaNome());
		atividadeForm.setAtividadeResponsavel(atividade.getColaboradorResponsavel().getPessoaNome());

		atividadeForm.setAtividadeDuracao(atividade.getAtividadeDuracao());

	return atividadeForm;
	}
	

	@Transactional
	public AtividadeForm atividadeParametros(AtividadeForm atividadeForm) {
		atividadeForm.setAtividadeStatus(AtributoStatus.valueOf("ATIVO"));
	return atividadeForm;
	}

	@Override
	public List<Atividade> getByAtividadeDescricao(String atividadeDescricao,Pageable pageable) {
		return atividadeRepository.findByAtividadeDescricao(atividadeDescricao,pageable);
	}

	@Override
	public List<Atividade> getByAtividadeNome(String atividadeNome,Pageable pageable) {
		return atividadeRepository.findByAtividadeNome(atividadeNome,pageable);
	}


	@Override
	public List<Atividade> getByAtividadeDescricao(String atividadeDescricao) {
		return atividadeRepository.findByAtividadeDescricao(atividadeDescricao);
	} 

	@Override
	public List<Atividade> getByAtividadeNome(String atividadeNome) {
		return atividadeRepository.findByAtividadeNome(atividadeNome);
	}



}
