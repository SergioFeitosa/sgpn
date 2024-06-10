package br.com.j4business.saga.funcao.service;

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
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.funcao.model.FuncaoForm;
import br.com.j4business.saga.funcao.repository.FuncaoRepository;

@Service("funcaoService")
public class FuncaoServiceImpl implements FuncaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(FuncaoService.class.getName());

	@Override
	public List<Funcao> getFuncaoAll() {
		return funcaoRepository.findAll();
	}

	@Override
	public Page<Funcao> getFuncaoAll(Pageable pageable) {
		return funcaoRepository.findAll(pageable);
	}

	@Override
	public Funcao getFuncaoByFuncaoPK(long funcaoPK) {
		
		Optional<Funcao> funcao = funcaoRepository.findById(funcaoPK);
		return funcao.get();
	}

	@Transactional
	public Funcao create(FuncaoForm funcaoForm) {
		
		Funcao funcao = new Funcao();
		
		funcao = this.converteFuncaoForm(funcaoForm);
		
		funcao = entityManager.merge(funcao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Funcao Create " + "\n Usuário => " + username + 
										" // Id => "+funcao.getFuncaoPK() + 
										" // Funcao => "+funcao.getFuncaoNome() + 
										" // Descrição => "+ funcao.getFuncaoDescricao());
		
		return funcao;
	}

	@Transactional
	public Funcao save(FuncaoForm funcaoForm) {
		
		Funcao funcao = new Funcao();
		
		funcao = this.converteFuncaoForm(funcaoForm);
		
		funcao = entityManager.merge(funcao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Funcao Save " + "\n Usuário => " + username + 
										" // Id => "+funcao.getFuncaoPK() + 
										" // Funcao => "+funcao.getFuncaoNome() + 
										" // Descrição => "+ funcao.getFuncaoDescricao());
		

		return funcao;
		
	}

	@Transactional
	public void delete(Long funcaoId) {

		Funcao funcao = this.getFuncaoByFuncaoPK(funcaoId);
		
		funcaoRepository.delete(funcao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Funcao Delete " + "\n Usuário => " + username + 
										" // Id => "+funcao.getFuncaoPK() + 
										" // Funcao => "+funcao.getFuncaoNome() + 
										" // Descrição => "+ funcao.getFuncaoDescricao());
		

    }

	@Transactional
	public Funcao converteFuncaoForm(FuncaoForm funcaoForm) {
		
		Funcao funcao = new Funcao();
		
		if (funcaoForm.getFuncaoPK() > 0) {
			funcao = this.getFuncaoByFuncaoPK(funcaoForm.getFuncaoPK());
		}

		funcao.setFuncaoNome(funcaoForm.getFuncaoNome().replaceAll("\\s+"," "));
		funcao.setFuncaoDescricao(funcaoForm.getFuncaoDescricao().replaceAll("\\s+"," "));

		funcao.setFuncaoMotivoOperacao(funcaoForm.getFuncaoMotivoOperacao().replaceAll("\\s+"," "));
		funcao.setFuncaoStatus(funcaoForm.getFuncaoStatus()+"".replaceAll("\\s+"," "));
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(funcaoForm.getFuncaoResponsavel()));
		funcao.setColaboradorResponsavel(colaborador);
		
		return funcao;
	}

	@Transactional
	public FuncaoForm converteFuncao(Funcao funcao) {
	
		FuncaoForm funcaoForm = new FuncaoForm();
		
		funcaoForm.setFuncaoPK(funcao.getFuncaoPK());
		funcaoForm.setFuncaoNome(funcao.getFuncaoNome());
		funcaoForm.setFuncaoDescricao(funcao.getFuncaoDescricao());

		funcaoForm.setFuncaoMotivoOperacao(funcao.getFuncaoMotivoOperacao());
		funcaoForm.setFuncaoStatus(AtributoStatus.valueOf(funcao.getFuncaoStatus()));

		funcaoForm.setFuncaoResponsavel(funcao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return funcaoForm;
	}
	
	@Transactional
	public FuncaoForm converteFuncaoView(Funcao funcao) {
	
		FuncaoForm funcaoForm = new FuncaoForm();
		
		funcaoForm.setFuncaoPK(funcao.getFuncaoPK());
		funcaoForm.setFuncaoNome(funcao.getFuncaoNome());
		funcaoForm.setFuncaoDescricao(funcao.getFuncaoDescricao());

		funcaoForm.setFuncaoMotivoOperacao(funcao.getFuncaoMotivoOperacao());
		funcaoForm.setFuncaoStatus(AtributoStatus.valueOf(funcao.getFuncaoStatus()));

		funcaoForm.setFuncaoResponsavel(funcao.getColaboradorResponsavel().getPessoaNome());
		
	return funcaoForm;
	}
	

	@Transactional
	public FuncaoForm funcaoParametros(FuncaoForm funcaoForm) {
	
		
		funcaoForm.setFuncaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return funcaoForm;
	}

	@Override
	public List<Funcao> getByFuncaoDescricao(String funcaoDescricao,Pageable pageable) {
		return funcaoRepository.findByFuncaoDescricao(funcaoDescricao,pageable);
	}

	@Override
	public List<Funcao> getByFuncaoNome(String funcaoNome,Pageable pageable) {
		return funcaoRepository.findByFuncaoNome(funcaoNome,pageable);
	}

	@Override
	public List<Funcao> getByFuncaoDescricao(String funcaoDescricao) {
		return funcaoRepository.findByFuncaoDescricao(funcaoDescricao);
	}

	@Override
	public List<Funcao> getByFuncaoNome(String funcaoNome) {
		return funcaoRepository.findByFuncaoNome(funcaoNome);
	}



}
