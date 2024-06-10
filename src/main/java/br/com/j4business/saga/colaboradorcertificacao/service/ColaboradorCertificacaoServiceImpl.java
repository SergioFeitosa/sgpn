package br.com.j4business.saga.colaboradorcertificacao.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.certificacao.service.CertificacaoService;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacaoForm;
import br.com.j4business.saga.colaboradorcertificacao.repository.ColaboradorCertificacaoRepository;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorCertificacaoService")
public class ColaboradorCertificacaoServiceImpl implements ColaboradorCertificacaoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorCertificacaoRepository colaboradorCertificacaoRepository;

	@Autowired
	private CertificacaoService certificacaoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorCertificacaoService.class.getName());


	@Override
	public List<ColaboradorCertificacao> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorCertificacaoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorCertificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable) {
		return colaboradorCertificacaoRepository.findByCertificacaoNome(certificacaoNome,pageable);
	}

	@Override
	public List<ColaboradorCertificacao> getByColaboradorNome(String colaboradorNome) {
		return colaboradorCertificacaoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorCertificacao> getByCertificacaoNome(String certificacaoNome) {
		return colaboradorCertificacaoRepository.findByCertificacaoNome(certificacaoNome);
	}

	@Override
	public List<ColaboradorCertificacao> getByCertificacaoPK(long certificacaoPK,Pageable pageable) {
		return colaboradorCertificacaoRepository.findByCertificacaoPK(certificacaoPK,pageable);
	}

	@Override
	public List<ColaboradorCertificacao> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorCertificacaoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorCertificacao> getColaboradorCertificacaoAll(Pageable pageable) {
		return colaboradorCertificacaoRepository.findColaboradorCertificacaoAll(pageable);
	}

	@Override
	public ColaboradorCertificacao getColaboradorCertificacaoByColaboradorCertificacaoPK(long colaboradorCertificacaoPK) {
		
		Optional<ColaboradorCertificacao> colaboradorCertificacao = colaboradorCertificacaoRepository.findById(colaboradorCertificacaoPK);
		return colaboradorCertificacao.get();
	}

	@Override
	public ColaboradorCertificacao getByColaboradorAndCertificacao (Colaborador colaborador,Certificacao certificacao) {
		
		return colaboradorCertificacaoRepository.findByColaboradorAndCertificacao(colaborador,certificacao);
	}

	@Transactional
	public ColaboradorCertificacao save(ColaboradorCertificacaoForm colaboradorCertificacaoForm) {

		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		
		colaboradorCertificacao = this.converteColaboradorCertificacaoForm(colaboradorCertificacaoForm);
		
		colaboradorCertificacao = entityManager.merge(colaboradorCertificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorCertificacao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorCertificacao.getColaboradorCertificacaoPK() + 
										" // Colaborador Id => "+colaboradorCertificacao.getColaborador().getPessoaPK() + 
										" // Certificacao Id => "+colaboradorCertificacao.getCertificacao().getCertificacaoPK());
		return colaboradorCertificacao;
	}

	@Transactional
	public void delete(Long colaboradorCertificacaoPK) {

		ColaboradorCertificacao colaboradorCertificacaoTemp = this.getColaboradorCertificacaoByColaboradorCertificacaoPK(colaboradorCertificacaoPK);

		colaboradorCertificacaoRepository.delete(colaboradorCertificacaoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorCertificacao Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorCertificacaoTemp.getColaboradorCertificacaoPK() + 
										" // Colaborador Id => "+colaboradorCertificacaoTemp.getColaborador().getPessoaPK() + 
										" // Certificacao Id => "+colaboradorCertificacaoTemp.getCertificacao().getCertificacaoPK()); 
    }

	@Transactional
	public void deleteByCertificacao(Certificacao certificacao) {
		
		List<ColaboradorCertificacao> colaboradorCertificacaoList = colaboradorCertificacaoRepository.findByCertificacao(certificacao);

		for (ColaboradorCertificacao colaboradorCertificacao2 : colaboradorCertificacaoList) {
			colaboradorCertificacaoRepository.delete(colaboradorCertificacao2);
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorCertificacaoList.forEach((ColaboradorCertificacao colaboradorCertificacao) -> {
			
			logger.info("ColaboradorCertificacao Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorCertificacao.getColaboradorCertificacaoPK() + 
											" // Colaborador Id => "+colaboradorCertificacao.getColaborador().getPessoaPK() + 
											" // Certificacao Id => "+colaboradorCertificacao.getCertificacao().getCertificacaoPK()); 
		});
    }

	@Transactional
	public ColaboradorCertificacao converteColaboradorCertificacaoForm(ColaboradorCertificacaoForm colaboradorCertificacaoForm) {
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		
		if (colaboradorCertificacaoForm.getColaboradorCertificacaoPK() > 0) {
			colaboradorCertificacao = this.getColaboradorCertificacaoByColaboradorCertificacaoPK(colaboradorCertificacaoForm.getColaboradorCertificacaoPK());
		}
		
		Certificacao certificacao = certificacaoService.getCertificacaoByCertificacaoPK(Long.parseLong(colaboradorCertificacaoForm.getCertificacaoNome()));
		colaboradorCertificacao.setCertificacao(certificacao);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorCertificacaoForm.getColaboradorNome()));
		colaboradorCertificacao.setColaborador(colaborador);

		Fornecedor capacitador = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(colaboradorCertificacaoForm.getCapacitador()));
		colaboradorCertificacao.setCapacitador(capacitador);
		
		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorCertificacaoForm.getColaboradorCertificacaoResponsavel()));
		colaboradorCertificacao.setColaboradorResponsavel(colaboradorResponsavel);

		colaboradorCertificacao.setColaboradorCertificacaoMotivoOperacao(colaboradorCertificacaoForm.getColaboradorCertificacaoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorCertificacao.setColaboradorCertificacaoStatus(colaboradorCertificacaoForm.getColaboradorCertificacaoStatus()+"".replaceAll("\\s+"," "));
		
		colaboradorCertificacao.setColaboradorcertificacaoDataInicio(colaboradorCertificacaoForm.getColaboradorCertificacaoDataInicio());
		colaboradorCertificacao.setColaboradorcertificacaoDataTermino(colaboradorCertificacaoForm.getColaboradorCertificacaoDataTermino());
		colaboradorCertificacao.setColaboradorcertificacaoDataValidade(colaboradorCertificacaoForm.getColaboradorCertificacaoDataValidade());

		return colaboradorCertificacao;
	}

	@Transactional
	public ColaboradorCertificacaoForm converteColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
	
		ColaboradorCertificacaoForm colaboradorCertificacaoForm = new ColaboradorCertificacaoForm();
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoPK(colaboradorCertificacao.getColaboradorCertificacaoPK());
		colaboradorCertificacaoForm.setColaboradorNome(colaboradorCertificacao.getColaborador().getPessoaPK()+"");
		colaboradorCertificacaoForm.setCertificacaoNome(colaboradorCertificacao.getCertificacao().getCertificacaoPK()+"");
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoResponsavel(colaboradorCertificacao.getColaborador().getPessoaPK()+"");
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoMotivoOperacao(colaboradorCertificacao.getColaboradorCertificacaoMotivoOperacao());
		colaboradorCertificacaoForm.setColaboradorCertificacaoStatus(AtributoStatus.valueOf(colaboradorCertificacao.getColaboradorCertificacaoStatus()));

		colaboradorCertificacaoForm.setCapacitador(colaboradorCertificacao.getCapacitador().getPessoaPK()+"");
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataInicio(colaboradorCertificacao.getColaboradorcertificacaoDataInicio());
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataTermino(colaboradorCertificacao.getColaboradorcertificacaoDataTermino());
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataValidade(colaboradorCertificacao.getColaboradorcertificacaoDataValidade());
		
	return colaboradorCertificacaoForm;
	}
	
	@Transactional
	public ColaboradorCertificacaoForm converteColaboradorCertificacaoView(ColaboradorCertificacao colaboradorCertificacao) {
	
		ColaboradorCertificacaoForm colaboradorCertificacaoForm = new ColaboradorCertificacaoForm();
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoPK(colaboradorCertificacao.getColaboradorCertificacaoPK());
		colaboradorCertificacaoForm.setColaboradorNome(colaboradorCertificacao.getColaborador().getPessoaNome());
		colaboradorCertificacaoForm.setCertificacaoNome(colaboradorCertificacao.getCertificacao().getCertificacaoNome());
		
		colaboradorCertificacaoForm.setCapacitador(colaboradorCertificacao.getCapacitador().getPessoaNome());
		colaboradorCertificacaoForm.setColaboradorCertificacaoResponsavel(colaboradorCertificacao.getColaborador().getPessoaNome());
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoMotivoOperacao(colaboradorCertificacao.getColaboradorCertificacaoMotivoOperacao());
		colaboradorCertificacaoForm.setColaboradorCertificacaoStatus(AtributoStatus.valueOf(colaboradorCertificacao.getColaboradorCertificacaoStatus()));

		colaboradorCertificacaoForm.setCapacitador(colaboradorCertificacao.getCapacitador().getPessoaNome()+"");
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataInicio(colaboradorCertificacao.getColaboradorcertificacaoDataInicio());
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataTermino(colaboradorCertificacao.getColaboradorcertificacaoDataTermino());
		colaboradorCertificacaoForm.setColaboradorCertificacaoDataValidade(colaboradorCertificacao.getColaboradorcertificacaoDataValidade());
		
	return colaboradorCertificacaoForm;
	}
	

	@Transactional
	public ColaboradorCertificacaoForm colaboradorCertificacaoParametros(ColaboradorCertificacaoForm colaboradorCertificacaoForm) {
	
		
		colaboradorCertificacaoForm.setColaboradorCertificacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorCertificacaoForm;
	}
	
	@Transactional
	public ColaboradorCertificacao create(ColaboradorCertificacaoForm colaboradorCertificacaoForm) {
		
		ColaboradorCertificacao colaboradorCertificacao = new ColaboradorCertificacao();
		
		colaboradorCertificacao = this.converteColaboradorCertificacaoForm(colaboradorCertificacaoForm);
		
		colaboradorCertificacao = entityManager.merge(colaboradorCertificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Certificacao Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorCertificacao.getColaboradorCertificacaoPK() + 
				" // Colaborador Id => "+colaboradorCertificacao.getColaborador().getPessoaPK() + 
				" // Certificacao Id => "+colaboradorCertificacao.getCertificacao().getCertificacaoPK()); 
		
		return colaboradorCertificacao;
	}


}
