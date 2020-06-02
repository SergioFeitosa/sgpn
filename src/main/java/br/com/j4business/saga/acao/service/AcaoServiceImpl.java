package br.com.j4business.saga.acao.service;

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
import br.com.j4business.saga.acao.model.Acao;
import br.com.j4business.saga.acao.repository.AcaoRepository;
import br.com.j4business.saga.atributo.enumeration.AtributoAprovacao;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.acao.model.AcaoForm;

@Service("acaoService")
public class AcaoServiceImpl implements AcaoService {

	@Autowired
	private AcaoRepository acaoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AcaoService.class.getName());

	@Override
	public Page<Acao> getAcaoAll(Pageable pageable) {
		return acaoRepository.findAll(pageable);
	}

	@Override
	public List<Acao> getAcaoAll() {
		return acaoRepository.findAll();
	}

	@Override
	public Acao getAcaoByAcaoPK(long acaoPK) {
		
		return acaoRepository.findOne(acaoPK);
	}

	@Transactional
	public Acao create(AcaoForm acaoForm) {
		
		Acao acao = new Acao();
		
		acao = this.converteAcaoForm(acaoForm);
		
		acao = entityManager.merge(acao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Acao Create " + "\n Usuário => " + username + 
										" // Id => "+acao.getAcaoPK() + 
										" // Acao => "+acao.getAcaoNome() + 
										" // Descrição => "+ acao.getAcaoDescricao());
		
		return acao;
	}

	@Transactional
	public Acao save(AcaoForm acaoForm) {
		
		Acao acao = new Acao();
		
		acao = this.converteAcaoForm(acaoForm);
		
		acao = entityManager.merge(acao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Acao Save " + "\n Usuário => " + username + 
										" // Id => "+acao.getAcaoPK() + 
										" // Acao => "+acao.getAcaoNome() + 
										" // Descrição => "+ acao.getAcaoDescricao());
		
		return acao;
		
	}

	@Transactional
	public void delete(Long acaoId) {

		Acao acao = this.getAcaoByAcaoPK(acaoId);
		
		acaoRepository.delete(acao.getAcaoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Acao Delete " + "\n Usuário => " + username + 
										" // Id => "+acao.getAcaoPK() + 
										" // Acao => "+acao.getAcaoNome() + 
										" // Descrição => "+ acao.getAcaoDescricao());
		

    }

	@Transactional
	public Acao converteAcaoForm(AcaoForm acaoForm) {
		
		Acao acao = new Acao();
		
		if (acaoForm.getAcaoPK() > 0) {
			acao = this.getAcaoByAcaoPK(acaoForm.getAcaoPK());
		}
		
		acao.setAcaoNome(acaoForm.getAcaoNome().replaceAll("\\s+"," "));
		acao.setAcaoDescricao(acaoForm.getAcaoDescricao().replaceAll("\\s+"," "));
		acao.setAcaoAprovacao(acaoForm.getAcaoAprovacao()+"");
		acao.setAcaoStatus(acaoForm.getAcaoStatus()+"");
		acao.setAcaoMotivoOperacao(acaoForm.getAcaoMotivoOperacao());

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(acaoForm.getAcaoGestor()));
		acao.setColaboradorGestor(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(acaoForm.getAcaoDono()));
		acao.setColaboradorDono(colaborador);

		colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(acaoForm.getAcaoResponsavel()));
		acao.setColaboradorResponsavel(colaborador);

		return acao;
	}

	@Transactional
	public AcaoForm converteAcao(Acao acao) {
	
		AcaoForm acaoForm = new AcaoForm();
		
		acaoForm.setAcaoPK(acao.getAcaoPK());
		acaoForm.setAcaoNome(acao.getAcaoNome());
		acaoForm.setAcaoDescricao(acao.getAcaoDescricao());
		acaoForm.setAcaoAprovacao(AtributoAprovacao.valueOf(acao.getAcaoAprovacao()));
		acaoForm.setAcaoStatus(AtributoStatus.valueOf(acao.getAcaoStatus()));
		acaoForm.setAcaoMotivoOperacao(acao.getAcaoMotivoOperacao());

		acaoForm.setAcaoGestor(acao.getColaboradorGestor().getPessoaPK()+"");
		acaoForm.setAcaoDono(acao.getColaboradorDono().getPessoaPK()+"");
		acaoForm.setAcaoResponsavel(acao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return acaoForm;
	}
	
	@Transactional
	public AcaoForm converteAcaoView(Acao acao) {
	 
		AcaoForm acaoForm = new AcaoForm();
		
		acaoForm.setAcaoPK(acao.getAcaoPK());
		acaoForm.setAcaoNome(acao.getAcaoNome());
		acaoForm.setAcaoDescricao(acao.getAcaoDescricao());
		acaoForm.setAcaoAprovacao(AtributoAprovacao.valueOf(acao.getAcaoAprovacao()));
		acaoForm.setAcaoStatus(AtributoStatus.valueOf(acao.getAcaoStatus()));

		acaoForm.setAcaoGestor(acao.getColaboradorGestor().getPessoaNome());
		acaoForm.setAcaoDono(acao.getColaboradorDono().getPessoaNome());
		acaoForm.setAcaoResponsavel(acao.getColaboradorResponsavel().getPessoaNome());
		
		acaoForm.setAcaoMotivoOperacao(acao.getAcaoMotivoOperacao()); 
		
	return acaoForm;
	}
	

	@Transactional
	public AcaoForm acaoParametros(AcaoForm acaoForm) {
	
		acaoForm.setAcaoStatus(AtributoStatus.valueOf("ATIVO"));
		acaoForm.setAcaoAprovacao(AtributoAprovacao.valueOf("EM_APROVAÇÃO"));
		
	return acaoForm;
	}

	@Override
	public List<Acao> getByAcaoDescricao(String acaoDescricao,Pageable pageable) {
		return acaoRepository.findByAcaoDescricao(acaoDescricao,pageable);
	}

	@Override
	public List<Acao> getByAcaoNome(String acaoNome,Pageable pageable) {
		return acaoRepository.findByAcaoNome(acaoNome,pageable);
	}


	@Override
	public List<Acao> getByAcaoDescricao(String acaoDescricao) {
		return acaoRepository.findByAcaoDescricao(acaoDescricao);
	}

	@Override
	public List<Acao> getByAcaoNome(String acaoNome) {
		return acaoRepository.findByAcaoNome(acaoNome);
	}



}
