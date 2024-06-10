package br.com.j4business.saga.colaboradorfuncao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.funcao.model.Funcao;
import br.com.j4business.saga.funcao.service.FuncaoService;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncao;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncaoForm;
import br.com.j4business.saga.colaboradorfuncao.repository.ColaboradorFuncaoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorFuncaoService")
public class ColaboradorFuncaoServiceImpl implements ColaboradorFuncaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorFuncaoRepository colaboradorFuncaoRepository;

	@Autowired
	private FuncaoService funcaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorFuncaoService.class.getName());


	@Override
	public List<ColaboradorFuncao> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorFuncaoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorFuncao> getByFuncaoNome(String funcaoNome,Pageable pageable) {
		return colaboradorFuncaoRepository.findByFuncaoNome(funcaoNome,pageable);
	}

	@Override
	public List<ColaboradorFuncao> getByColaboradorNome(String colaboradorNome) {
		return colaboradorFuncaoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorFuncao> getByFuncaoNome(String funcaoNome) {
		return colaboradorFuncaoRepository.findByFuncaoNome(funcaoNome);
	}

	@Override
	public List<ColaboradorFuncao> getByFuncaoPK(long funcaoPK,Pageable pageable) {
		return colaboradorFuncaoRepository.findByFuncaoPK(funcaoPK,pageable);
	}

	@Override
	public List<ColaboradorFuncao> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorFuncaoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorFuncao> getColaboradorFuncaoAll(Pageable pageable) {
		return colaboradorFuncaoRepository.findColaboradorFuncaoAll(pageable);
	}

	@Override
	public ColaboradorFuncao getColaboradorFuncaoByColaboradorFuncaoPK(long colaboradorFuncaoPK) {
		Optional<ColaboradorFuncao> colaboradorFuncao = colaboradorFuncaoRepository.findById(colaboradorFuncaoPK);
		return colaboradorFuncao.get();
	}

	@Override
	public ColaboradorFuncao getByColaboradorAndFuncao (Colaborador colaborador,Funcao funcao) {
		
		return colaboradorFuncaoRepository.findByColaboradorAndFuncao(colaborador,funcao);
	}

	@Transactional
	public ColaboradorFuncao save(ColaboradorFuncaoForm colaboradorFuncaoForm) {

		ColaboradorFuncao colaboradorFuncao = new ColaboradorFuncao();
		
		colaboradorFuncao = this.converteColaboradorFuncaoForm(colaboradorFuncaoForm);
		
		colaboradorFuncao = entityManager.merge(colaboradorFuncao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorFuncao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorFuncao.getColaboradorFuncaoPK() + 
										" // Colaborador Id => "+colaboradorFuncao.getColaborador().getPessoaPK() + 
										" // Funcao Id => "+colaboradorFuncao.getFuncao().getFuncaoPK());
		return colaboradorFuncao;
	}

	@Transactional
	public void delete(Long colaboradorFuncaoPK) {

		ColaboradorFuncao colaboradorFuncaoTemp = this.getColaboradorFuncaoByColaboradorFuncaoPK(colaboradorFuncaoPK);

		colaboradorFuncaoRepository.delete(colaboradorFuncaoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorFuncao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorFuncaoTemp.getColaboradorFuncaoPK() + 
										" // Colaborador Id => "+colaboradorFuncaoTemp.getColaborador().getPessoaPK() + 
										" // Funcao Id => "+colaboradorFuncaoTemp.getFuncao().getFuncaoPK()); 
    }

	@Transactional
	public void deleteByFuncao(Funcao funcao) {
		
		List<ColaboradorFuncao> colaboradorFuncaoList = colaboradorFuncaoRepository.findByFuncao(funcao);

		for (ColaboradorFuncao colaboradorFuncao2 : colaboradorFuncaoList) {
			colaboradorFuncaoRepository.delete(colaboradorFuncao2);			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorFuncaoList.forEach((ColaboradorFuncao colaboradorFuncao) -> {

			logger.info("ColaboradorFuncao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorFuncao.getColaboradorFuncaoPK() + 
											" // Colaborador Id => "+colaboradorFuncao.getColaborador().getPessoaPK() + 
											" // Funcao Id => "+colaboradorFuncao.getFuncao().getFuncaoPK()); 
		});
		
    }

	@Transactional
	public ColaboradorFuncao converteColaboradorFuncaoForm(ColaboradorFuncaoForm colaboradorFuncaoForm) {
		
		ColaboradorFuncao colaboradorFuncao = new ColaboradorFuncao();
		
		if (colaboradorFuncaoForm.getColaboradorFuncaoPK() > 0) {
			colaboradorFuncao = this.getColaboradorFuncaoByColaboradorFuncaoPK(colaboradorFuncaoForm.getColaboradorFuncaoPK());
		}
		
		
		Funcao funcao = funcaoService.getFuncaoByFuncaoPK(Long.parseLong(colaboradorFuncaoForm.getFuncaoNome()));
		colaboradorFuncao.setFuncao(funcao);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorFuncaoForm.getColaboradorNome()));
		colaboradorFuncao.setColaborador(colaborador);

		colaboradorFuncao.setColaboradorFuncaoMotivoOperacao(colaboradorFuncaoForm.getColaboradorFuncaoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorFuncao.setColaboradorFuncaoStatus(colaboradorFuncaoForm.getColaboradorFuncaoStatus()+"".replaceAll("\\s+"," "));
		
		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorFuncaoForm.getColaboradorFuncaoResponsavel()));
		colaboradorFuncao.setColaboradorResponsavel(colaboradorResponsavel);
		
		return colaboradorFuncao;
	}

	@Transactional
	public ColaboradorFuncaoForm converteColaboradorFuncao(ColaboradorFuncao colaboradorFuncao) {
	
		ColaboradorFuncaoForm colaboradorFuncaoForm = new ColaboradorFuncaoForm();
		
		colaboradorFuncaoForm.setColaboradorFuncaoPK(colaboradorFuncao.getColaboradorFuncaoPK());
		colaboradorFuncaoForm.setColaboradorNome(colaboradorFuncao.getColaborador().getPessoaPK()+"");
		colaboradorFuncaoForm.setFuncaoNome(colaboradorFuncao.getFuncao().getFuncaoPK()+"");

		colaboradorFuncaoForm.setColaboradorFuncaoMotivoOperacao(colaboradorFuncao.getColaboradorFuncaoMotivoOperacao());
		colaboradorFuncaoForm.setColaboradorFuncaoStatus(AtributoStatus.valueOf(colaboradorFuncao.getColaboradorFuncaoStatus()));

		colaboradorFuncaoForm.setColaboradorFuncaoResponsavel(colaboradorFuncao.getColaborador().getPessoaPK()+"");
		
	return colaboradorFuncaoForm;
	}
	
	@Transactional
	public ColaboradorFuncaoForm converteColaboradorFuncaoView(ColaboradorFuncao colaboradorFuncao) {
	
		ColaboradorFuncaoForm colaboradorFuncaoForm = new ColaboradorFuncaoForm();
		
		colaboradorFuncaoForm.setColaboradorFuncaoPK(colaboradorFuncao.getColaboradorFuncaoPK());
		colaboradorFuncaoForm.setColaboradorNome(colaboradorFuncao.getColaborador().getPessoaNome());
		colaboradorFuncaoForm.setFuncaoNome(colaboradorFuncao.getFuncao().getFuncaoNome());

		colaboradorFuncaoForm.setColaboradorFuncaoMotivoOperacao(colaboradorFuncao.getColaboradorFuncaoMotivoOperacao());
		colaboradorFuncaoForm.setColaboradorFuncaoStatus(AtributoStatus.valueOf(colaboradorFuncao.getColaboradorFuncaoStatus()));
		
		colaboradorFuncaoForm.setColaboradorFuncaoResponsavel(colaboradorFuncao.getColaborador().getPessoaNome());
		
	return colaboradorFuncaoForm;
	}
	

	@Transactional
	public ColaboradorFuncaoForm colaboradorFuncaoParametros(ColaboradorFuncaoForm colaboradorFuncaoForm) {
	
		
		colaboradorFuncaoForm.setColaboradorFuncaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorFuncaoForm;
	}
	
	@Transactional
	public ColaboradorFuncao create(ColaboradorFuncaoForm colaboradorFuncaoForm) {
		
		ColaboradorFuncao colaboradorFuncao = new ColaboradorFuncao();
		
		colaboradorFuncao = this.converteColaboradorFuncaoForm(colaboradorFuncaoForm);
		
		colaboradorFuncao = entityManager.merge(colaboradorFuncao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Funcao Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorFuncao.getColaboradorFuncaoPK() + 
				" // Colaborador Id => "+colaboradorFuncao.getColaborador().getPessoaPK() + 
				" // Funcao Id => "+colaboradorFuncao.getFuncao().getFuncaoPK()); 
		
		return colaboradorFuncao;
	}


}
