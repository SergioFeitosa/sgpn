package br.com.j4business.saga.avaliacao.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.avaliacao.model.Avaliacao;
import br.com.j4business.saga.avaliacao.repository.AvaliacaoRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.avaliacao.model.AvaliacaoForm;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;

@Service("avaliacaoService")
public class AvaliacaoServiceImpl implements AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AvaliacaoService.class.getName());

	@Override
	public Page<Avaliacao> getAvaliacaoAll(Pageable pageable) {
		return avaliacaoRepository.findAll(pageable);
	}

	@Override
	public List<Avaliacao> getAvaliacaoAll() {
		return avaliacaoRepository.findAll();
	}

	@Override
	public Avaliacao getAvaliacaoByAvaliacaoPK(long avaliacaoPK) {
		
		Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(avaliacaoPK);

		return avaliacao.get();
	}

	@Transactional
	public Avaliacao create(AvaliacaoForm avaliacaoForm) {
		
		Avaliacao avaliacao = new Avaliacao();
		
		avaliacao = this.converteAvaliacaoForm(avaliacaoForm);
		
		avaliacao = entityManager.merge(avaliacao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Avaliacao Create " + "\n Usuário => " + username + 
										" // Id => "+avaliacao.getAvaliacaoPK() + 
										" // Avaliacao => "+avaliacao.getAvaliacaoNome() + 
										" // Descrição => "+ avaliacao.getAvaliacaoDescricao());
		
		return avaliacao;
	}

	@Transactional
	public Avaliacao save(AvaliacaoForm avaliacaoForm) {
		
		Avaliacao avaliacao = new Avaliacao();
		
		avaliacao = this.converteAvaliacaoForm(avaliacaoForm);
		
		avaliacao = entityManager.merge(avaliacao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Avaliacao Save " + "\n Usuário => " + username + 
				" // Id => "+avaliacao.getAvaliacaoPK() + 
				" // Avaliacao => "+avaliacao.getAvaliacaoNome() + 
				" // Descrição => "+ avaliacao.getAvaliacaoDescricao());
		
		return avaliacao;
	}

	@Transactional
	public void delete(Long avaliacaoId) {

		Avaliacao avaliacao = this.getAvaliacaoByAvaliacaoPK(avaliacaoId);
		
		avaliacaoRepository.delete(avaliacao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Avaliacao Delete " + "\n Usuário => " + username + 
										" // Id => "+avaliacao.getAvaliacaoPK() + 
										" // Avaliacao => "+avaliacao.getAvaliacaoNome() + 
										" // Descrição => "+ avaliacao.getAvaliacaoDescricao());
		

    }

	@Transactional
	public Avaliacao converteAvaliacaoForm(AvaliacaoForm avaliacaoForm) {

		Avaliacao avaliacao = new Avaliacao();
		
		if (avaliacaoForm.getAvaliacaoPK() > 0) {
			avaliacao = this.getAvaliacaoByAvaliacaoPK(avaliacaoForm.getAvaliacaoPK());
		}
		
		

		avaliacao.setAvaliacaoNome(avaliacaoForm.getAvaliacaoNome().replaceAll("\\s+"," "));
		avaliacao.setAvaliacaoDescricao(avaliacaoForm.getAvaliacaoDescricao().replaceAll("\\s+"," "));

		avaliacao.setAvaliacaoMotivoOperacao(avaliacaoForm.getAvaliacaoMotivoOperacao());
		avaliacao.setAvaliacaoStatus(avaliacaoForm.getAvaliacaoStatus()+"");

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(avaliacaoForm.getAvaliacaoResponsavel()));
		avaliacao.setColaboradorResponsavel(colaboradorResponsavel);

		return avaliacao;
	}

	@Transactional
	public AvaliacaoForm converteAvaliacao(Avaliacao avaliacao) {
	
		AvaliacaoForm avaliacaoForm = new AvaliacaoForm();
		
		avaliacaoForm.setAvaliacaoPK(avaliacao.getAvaliacaoPK());
		avaliacaoForm.setAvaliacaoNome(avaliacao.getAvaliacaoNome());
		avaliacaoForm.setAvaliacaoDescricao(avaliacao.getAvaliacaoDescricao());

		avaliacaoForm.setAvaliacaoMotivoOperacao(avaliacao.getAvaliacaoMotivoOperacao());
		avaliacaoForm.setAvaliacaoStatus(AtributoStatus.valueOf(avaliacao.getAvaliacaoStatus()));

		avaliacaoForm.setAvaliacaoResponsavel(avaliacao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return avaliacaoForm;
	}
	
	@Transactional
	public AvaliacaoForm converteAvaliacaoView(Avaliacao avaliacao) {
	
		AvaliacaoForm avaliacaoForm = new AvaliacaoForm();
		
		avaliacaoForm.setAvaliacaoPK(avaliacao.getAvaliacaoPK());
		avaliacaoForm.setAvaliacaoNome(avaliacao.getAvaliacaoNome());
		avaliacaoForm.setAvaliacaoDescricao(avaliacao.getAvaliacaoDescricao());
		avaliacaoForm.setAvaliacaoMotivoOperacao(avaliacao.getAvaliacaoMotivoOperacao());
		avaliacaoForm.setAvaliacaoStatus(AtributoStatus.valueOf(avaliacao.getAvaliacaoStatus()));

		avaliacaoForm.setAvaliacaoResponsavel(avaliacao.getColaboradorResponsavel().getPessoaNome());
		
	return avaliacaoForm;
	}
	

	@Transactional
	public AvaliacaoForm avaliacaoParametros(AvaliacaoForm avaliacaoForm) {
		avaliacaoForm.setAvaliacaoStatus(AtributoStatus.valueOf("ATIVO"));
	return avaliacaoForm;
	}

	@Override
	public List<Avaliacao> getByAvaliacaoDescricao(String avaliacaoDescricao,Pageable pageable) {
		return avaliacaoRepository.findByAvaliacaoDescricao(avaliacaoDescricao,pageable);
	}

	@Override
	public List<Avaliacao> getByAvaliacaoNome(String avaliacaoNome,Pageable pageable) {
		return avaliacaoRepository.findByAvaliacaoNome(avaliacaoNome,pageable);
	}


	@Override
	public List<Avaliacao> getByAvaliacaoDescricao(String avaliacaoDescricao) {
		return avaliacaoRepository.findByAvaliacaoDescricao(avaliacaoDescricao);
	}

	@Override
	public List<Avaliacao> getByAvaliacaoNome(String avaliacaoNome) {
		return avaliacaoRepository.findByAvaliacaoNome(avaliacaoNome);
	}



}
