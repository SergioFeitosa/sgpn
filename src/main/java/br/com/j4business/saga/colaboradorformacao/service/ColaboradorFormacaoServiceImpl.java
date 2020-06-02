package br.com.j4business.saga.colaboradorformacao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.formacao.model.Formacao;
import br.com.j4business.saga.formacao.service.FormacaoService;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacao;
import br.com.j4business.saga.colaboradorformacao.model.ColaboradorFormacaoForm;
import br.com.j4business.saga.colaboradorformacao.repository.ColaboradorFormacaoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorFormacaoService")
public class ColaboradorFormacaoServiceImpl implements ColaboradorFormacaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorFormacaoRepository colaboradorFormacaoRepository;

	@Autowired
	private FormacaoService formacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorFormacaoService.class.getName());


	@Override
	public List<ColaboradorFormacao> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorFormacaoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorFormacao> getByFormacaoNome(String formacaoNome,Pageable pageable) {
		return colaboradorFormacaoRepository.findByFormacaoNome(formacaoNome,pageable);
	}

	@Override
	public List<ColaboradorFormacao> getByColaboradorNome(String colaboradorNome) {
		return colaboradorFormacaoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorFormacao> getByFormacaoNome(String formacaoNome) {
		return colaboradorFormacaoRepository.findByFormacaoNome(formacaoNome);
	}

	@Override
	public List<ColaboradorFormacao> getByFormacaoPK(long formacaoPK,Pageable pageable) {
		return colaboradorFormacaoRepository.findByFormacaoPK(formacaoPK,pageable);
	}

	@Override
	public List<ColaboradorFormacao> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorFormacaoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorFormacao> getColaboradorFormacaoAll(Pageable pageable) {
		return colaboradorFormacaoRepository.findColaboradorFormacaoAll(pageable);
	}

	@Override
	public ColaboradorFormacao getColaboradorFormacaoByColaboradorFormacaoPK(long colaboradorFormacaoPK) {
		return colaboradorFormacaoRepository.findOne(colaboradorFormacaoPK);
	}

	@Override
	public ColaboradorFormacao getByColaboradorAndFormacao (Colaborador colaborador,Formacao formacao) {
		
		return colaboradorFormacaoRepository.findByColaboradorAndFormacao(colaborador,formacao);
	}

	@Override
	public List<ColaboradorFormacao> getMaxNivelByColaboradorPK (long colaboradorPK,Pageable pageable) {
		
		return colaboradorFormacaoRepository.findMaxNivelByColaboradorPK(colaboradorPK,pageable);
	}

	
	@Transactional
	public ColaboradorFormacao save(ColaboradorFormacaoForm colaboradorFormacaoForm) {

		ColaboradorFormacao colaboradorFormacao = new ColaboradorFormacao();
		
		colaboradorFormacao = this.converteColaboradorFormacaoForm(colaboradorFormacaoForm);
		
		colaboradorFormacao = entityManager.merge(colaboradorFormacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorFormacao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorFormacao.getColaboradorFormacaoPK() + 
										" // Colaborador Id => "+colaboradorFormacao.getColaborador().getPessoaPK() + 
										" // Formacao Id => "+colaboradorFormacao.getFormacao().getFormacaoPK());
		return colaboradorFormacao;
	}

	@Transactional
	public void delete(Long colaboradorFormacaoPK) {

		ColaboradorFormacao colaboradorFormacaoTemp = this.getColaboradorFormacaoByColaboradorFormacaoPK(colaboradorFormacaoPK);

		colaboradorFormacaoRepository.delete(colaboradorFormacaoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorFormacao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorFormacaoTemp.getColaboradorFormacaoPK() + 
										" // Colaborador Id => "+colaboradorFormacaoTemp.getColaborador().getPessoaPK() + 
										" // Formacao Id => "+colaboradorFormacaoTemp.getFormacao().getFormacaoPK()); 
    }

	@Transactional
	public void deleteByFormacao(Formacao formacao) {
		
		List<ColaboradorFormacao> colaboradorFormacaoList = colaboradorFormacaoRepository.findByFormacao(formacao);

		colaboradorFormacaoRepository.delete(colaboradorFormacaoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorFormacaoList.forEach((ColaboradorFormacao colaboradorFormacao) -> {

			logger.info("ColaboradorFormacao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorFormacao.getColaboradorFormacaoPK() + 
											" // Colaborador Id => "+colaboradorFormacao.getColaborador().getPessoaPK() + 
											" // Formacao Id => "+colaboradorFormacao.getFormacao().getFormacaoPK()); 

		});
		
    }

	@Transactional
	public ColaboradorFormacao converteColaboradorFormacaoForm(ColaboradorFormacaoForm colaboradorFormacaoForm) {
		
		ColaboradorFormacao colaboradorFormacao = new ColaboradorFormacao();
		
		if (colaboradorFormacaoForm.getColaboradorFormacaoPK() > 0) {
			 colaboradorFormacao = this.getColaboradorFormacaoByColaboradorFormacaoPK(colaboradorFormacaoForm.getColaboradorFormacaoPK());
		}
		
		Formacao formacao = formacaoService.getFormacaoByFormacaoPK(Long.parseLong(colaboradorFormacaoForm.getFormacaoNome()));
		colaboradorFormacao.setFormacao(formacao);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorFormacaoForm.getColaboradorNome()));
		colaboradorFormacao.setColaborador(colaborador);

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorFormacaoForm.getColaboradorFormacaoResponsavel()));
		colaboradorFormacao.setColaboradorResponsavel(colaboradorResponsavel);
		
		Fornecedor capacitador = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(colaboradorFormacaoForm.getColaboradorFormacaoCapacitador()));
		colaboradorFormacao.setColaboradorFormacaoCapacitador(capacitador);
		
		colaboradorFormacao.setColaboradorFormacaoMotivoOperacao(colaboradorFormacaoForm.getColaboradorFormacaoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorFormacao.setColaboradorFormacaoStatus(colaboradorFormacaoForm.getColaboradorFormacaoStatus()+"".replaceAll("\\s+"," "));
		
		colaboradorFormacao.setColaboradorFormacaoDataInicio(colaboradorFormacaoForm.getColaboradorFormacaoDataInicio());
		colaboradorFormacao.setColaboradorFormacaoDataTermino(colaboradorFormacaoForm.getColaboradorFormacaoDataTermino());
		colaboradorFormacao.setColaboradorFormacaoDataValidade(colaboradorFormacaoForm.getColaboradorFormacaoDataValidade());

		return colaboradorFormacao;
	}

	@Transactional
	public ColaboradorFormacaoForm converteColaboradorFormacao(ColaboradorFormacao colaboradorFormacao) {
	
		ColaboradorFormacaoForm colaboradorFormacaoForm = new ColaboradorFormacaoForm();
		
		colaboradorFormacaoForm.setColaboradorFormacaoPK(colaboradorFormacao.getColaboradorFormacaoPK());
		colaboradorFormacaoForm.setColaboradorNome(colaboradorFormacao.getColaborador().getPessoaPK()+"");
		colaboradorFormacaoForm.setFormacaoNome(colaboradorFormacao.getFormacao().getFormacaoPK()+"");

		colaboradorFormacaoForm.setColaboradorFormacaoMotivoOperacao(colaboradorFormacao.getColaboradorFormacaoMotivoOperacao());
		colaboradorFormacaoForm.setColaboradorFormacaoStatus(AtributoStatus.valueOf(colaboradorFormacao.getColaboradorFormacaoStatus()));

		colaboradorFormacaoForm.setColaboradorFormacaoResponsavel(colaboradorFormacao.getColaborador().getPessoaPK()+"");
		
		colaboradorFormacaoForm.setColaboradorFormacaoCapacitador(colaboradorFormacao.getColaboradorFormacaoCapacitador().getPessoaPK()+"");
		colaboradorFormacaoForm.setColaboradorFormacaoDataInicio(colaboradorFormacao.getColaboradorFormacaoDataInicio());
		colaboradorFormacaoForm.setColaboradorFormacaoDataTermino(colaboradorFormacao.getColaboradorFormacaoDataTermino());
		colaboradorFormacaoForm.setColaboradorFormacaoDataValidade(colaboradorFormacao.getColaboradorFormacaoDataValidade());

	return colaboradorFormacaoForm;
	}
	
	@Transactional
	public ColaboradorFormacaoForm converteColaboradorFormacaoView(ColaboradorFormacao colaboradorFormacao) {
	
		ColaboradorFormacaoForm colaboradorFormacaoForm = new ColaboradorFormacaoForm();
		
		colaboradorFormacaoForm.setColaboradorFormacaoPK(colaboradorFormacao.getColaboradorFormacaoPK());
		colaboradorFormacaoForm.setColaboradorNome(colaboradorFormacao.getColaborador().getPessoaNome());
		colaboradorFormacaoForm.setFormacaoNome(colaboradorFormacao.getFormacao().getFormacaoNome());

		colaboradorFormacaoForm.setColaboradorFormacaoMotivoOperacao(colaboradorFormacao.getColaboradorFormacaoMotivoOperacao());
		colaboradorFormacaoForm.setColaboradorFormacaoStatus(AtributoStatus.valueOf(colaboradorFormacao.getColaboradorFormacaoStatus()));

		colaboradorFormacaoForm.setColaboradorFormacaoResponsavel(colaboradorFormacao.getColaborador().getPessoaNome());

		colaboradorFormacaoForm.setColaboradorFormacaoCapacitador(colaboradorFormacao.getColaboradorFormacaoCapacitador().getPessoaNome());
		colaboradorFormacaoForm.setColaboradorFormacaoDataInicio(colaboradorFormacao.getColaboradorFormacaoDataInicio());
		colaboradorFormacaoForm.setColaboradorFormacaoDataTermino(colaboradorFormacao.getColaboradorFormacaoDataTermino());
		colaboradorFormacaoForm.setColaboradorFormacaoDataValidade(colaboradorFormacao.getColaboradorFormacaoDataValidade());

	return colaboradorFormacaoForm;
	}
	

	@Transactional
	public ColaboradorFormacaoForm colaboradorFormacaoParametros(ColaboradorFormacaoForm colaboradorFormacaoForm) {
	
		
		colaboradorFormacaoForm.setColaboradorFormacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorFormacaoForm;
	}
	
	@Transactional
	public ColaboradorFormacao create(ColaboradorFormacaoForm colaboradorFormacaoForm) {
		
		ColaboradorFormacao colaboradorFormacao = new ColaboradorFormacao();
		
		colaboradorFormacao = this.converteColaboradorFormacaoForm(colaboradorFormacaoForm);
		
		colaboradorFormacao = entityManager.merge(colaboradorFormacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Formacao Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorFormacao.getColaboradorFormacaoPK() + 
				" // Colaborador Id => "+colaboradorFormacao.getColaborador().getPessoaPK() + 
				" // Formacao Id => "+colaboradorFormacao.getFormacao().getFormacaoPK()); 
		
		return colaboradorFormacao;
	}


}
