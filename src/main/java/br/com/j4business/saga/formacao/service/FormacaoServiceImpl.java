package br.com.j4business.saga.formacao.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.formacao.repository.FormacaoRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.formacao.model.FormacaoForm;

@Service("formacaoService")
public class FormacaoServiceImpl implements FormacaoService {

	@Autowired
	private FormacaoRepository formacaoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(FormacaoService.class.getName());

	@Override
	public Page<Formacao> getFormacaoAll(Pageable pageable) {
		return formacaoRepository.findAll(pageable);
	}

	@Override
	public List<Formacao> getFormacaoAll() {
		return formacaoRepository.findAll();
	}

	@Override
	public Formacao getFormacaoByFormacaoPK(long formacaoPK) {
		
		Optional<Formacao> formacao = formacaoRepository.findById(formacaoPK);
		return formacao.get();
	}

	@Transactional
	public Formacao create(FormacaoForm formacaoForm) {
		
		Formacao formacao = new Formacao();
		
		formacao = this.converteFormacaoForm(formacaoForm);
		
		formacao = entityManager.merge(formacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Formacao Create " + "\n Usuário => " + username + 
										" // Id => "+formacao.getFormacaoPK() + 
										" // Formacao => "+formacao.getFormacaoNome() + 
										" // Descrição => "+ formacao.getFormacaoDescricao());
		
		return formacao;
	}

	@Transactional
	public Formacao save(FormacaoForm formacaoForm) {
		
		Formacao formacao = new Formacao();
		
		formacao = this.converteFormacaoForm(formacaoForm);
		
		formacao = entityManager.merge(formacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Formacao Save " + "\n Usuário => " + username + 
										" // Id => "+formacao.getFormacaoPK() + 
										" // Formacao => "+formacao.getFormacaoNome() + 
										" // Descrição => "+ formacao.getFormacaoDescricao());
		

		return formacao;
		
	}

	@Transactional
	public void delete(Long formacaoId) {

		Formacao formacao = this.getFormacaoByFormacaoPK(formacaoId);
		
		formacaoRepository.delete(formacao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Formacao Delete " + "\n Usuário => " + username + 
										" // Id => "+formacao.getFormacaoPK() + 
										" // Formacao => "+formacao.getFormacaoNome() + 
										" // Descrição => "+ formacao.getFormacaoDescricao());
		

    }

	@Transactional
	public Formacao converteFormacaoForm(FormacaoForm formacaoForm) {
		
		Formacao formacao = new Formacao();
		
		if (formacaoForm.getFormacaoPK() > 0) {
			formacao = this.getFormacaoByFormacaoPK(formacaoForm.getFormacaoPK());
		}
		
		formacao.setFormacaoNome(formacaoForm.getFormacaoNome().replaceAll("\\s+"," "));
		formacao.setFormacaoDescricao(formacaoForm.getFormacaoDescricao().replaceAll("\\s+"," "));

		formacao.setFormacaoMotivoOperacao(formacaoForm.getFormacaoMotivoOperacao().replaceAll("\\s+"," "));
		formacao.setFormacaoStatus(formacaoForm.getFormacaoStatus()+"".replaceAll("\\s+"," "));

		formacao.setFormacaoNivel(Integer.parseInt(formacaoForm.getFormacaoNivel()+"".replaceAll("\\s+"," ")));
		
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(formacaoForm.getFormacaoResponsavel()));
		formacao.setColaboradorResponsavel(colaborador);

		return formacao;
	}

	@Transactional
	public FormacaoForm converteFormacao(Formacao formacao) {
	
		FormacaoForm formacaoForm = new FormacaoForm();
		
		formacaoForm.setFormacaoPK(formacao.getFormacaoPK());
		formacaoForm.setFormacaoNome(formacao.getFormacaoNome());
		formacaoForm.setFormacaoDescricao(formacao.getFormacaoDescricao());

		formacaoForm.setFormacaoMotivoOperacao(formacao.getFormacaoMotivoOperacao());
		formacaoForm.setFormacaoStatus(AtributoStatus.valueOf(formacao.getFormacaoStatus()));

		formacaoForm.setFormacaoNivel(formacao.getFormacaoNivel()+"");

		formacaoForm.setFormacaoResponsavel(formacao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return formacaoForm;
	}
	
	@Transactional
	public FormacaoForm converteFormacaoView(Formacao formacao) {
	
		FormacaoForm formacaoForm = new FormacaoForm();
		
		formacaoForm.setFormacaoPK(formacao.getFormacaoPK());
		formacaoForm.setFormacaoNome(formacao.getFormacaoNome());
		formacaoForm.setFormacaoDescricao(formacao.getFormacaoDescricao());

		formacaoForm.setFormacaoMotivoOperacao(formacao.getFormacaoMotivoOperacao());
		formacaoForm.setFormacaoStatus(AtributoStatus.valueOf(formacao.getFormacaoStatus()));

		formacaoForm.setFormacaoNivel(formacao.getFormacaoNivel()+"");

		formacaoForm.setFormacaoResponsavel(formacao.getColaboradorResponsavel().getPessoaNome());
		
	return formacaoForm;
	}
	

	@Transactional
	public FormacaoForm formacaoParametros(FormacaoForm formacaoForm) {
	
		
		formacaoForm.setFormacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return formacaoForm;
	}

	@Override
	public List<Formacao> getByFormacaoDescricao(String formacaoDescricao,Pageable pageable) {
		return formacaoRepository.findByFormacaoDescricao(formacaoDescricao,pageable);
	}

	@Override
	public List<Formacao> getByFormacaoNome(String formacaoNome,Pageable pageable) {
		return formacaoRepository.findByFormacaoNome(formacaoNome,pageable);
	}


	@Override
	public List<Formacao> getByFormacaoDescricao(String formacaoDescricao) {
		return formacaoRepository.findByFormacaoDescricao(formacaoDescricao);
	}

	@Override
	public List<Formacao> getByFormacaoNome(String formacaoNome) {
		return formacaoRepository.findByFormacaoNome(formacaoNome);
	}



}
