package br.com.j4business.saga.certificacao.service;

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
import br.com.j4business.saga.certificacao.model.Certificacao;
import br.com.j4business.saga.certificacao.repository.CertificacaoRepository;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.certificacao.model.CertificacaoForm;

@Service("certificacaoService")
public class CertificacaoServiceImpl implements CertificacaoService {

	@Autowired
	private CertificacaoRepository certificacaoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(CertificacaoService.class.getName());

	@Override
	public Page<Certificacao> getCertificacaoAll(Pageable pageable) {
		return certificacaoRepository.findAll(pageable);
	}

	@Override
	public List<Certificacao> getCertificacaoAll() {
		return certificacaoRepository.findAll();
	}

	@Override
	public Certificacao getCertificacaoByCertificacaoPK(long certificacaoPK) {
		
		Optional<Certificacao> certificacao = certificacaoRepository.findById(certificacaoPK);

		return certificacao.get();
	}

	@Transactional
	public Certificacao create(CertificacaoForm certificacaoForm) {
		
		Certificacao certificacao = new Certificacao();
		
		certificacao = this.converteCertificacaoForm(certificacaoForm);
		
		certificacao = entityManager.merge(certificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Certificacao Create " + "\n Usuário => " + username + 
										" // Id => "+certificacao.getCertificacaoPK() + 
										" // Certificacao => "+certificacao.getCertificacaoNome() + 
										" // Descrição => "+ certificacao.getCertificacaoDescricao());
		
		return certificacao;
	}

	@Transactional
	public Certificacao save(CertificacaoForm certificacaoForm) {
		
		Certificacao certificacao = new Certificacao();
		
		certificacao = this.converteCertificacaoForm(certificacaoForm);
		
		certificacao = entityManager.merge(certificacao);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Certificacao Save " + "\n Usuário => " + username + 
										" // Id => "+certificacao.getCertificacaoPK() + 
										" // Certificacao => "+certificacao.getCertificacaoNome() + 
										" // Descrição => "+ certificacao.getCertificacaoDescricao());
		

		return certificacao;
		
	}

	@Transactional
	public void delete(Long certificacaoId) {

		Certificacao certificacao = this.getCertificacaoByCertificacaoPK(certificacaoId);
		
		certificacaoRepository.delete(certificacao);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Certificacao Delete " + "\n Usuário => " + username + 
										" // Id => "+certificacao.getCertificacaoPK() + 
										" // Certificacao => "+certificacao.getCertificacaoNome() + 
										" // Descrição => "+ certificacao.getCertificacaoDescricao());
		

    }

	@Transactional
	public Certificacao converteCertificacaoForm(CertificacaoForm certificacaoForm) {
		
		Certificacao certificacao = new Certificacao();
		
		if (certificacaoForm.getCertificacaoPK() > 0) {
			certificacao = this.getCertificacaoByCertificacaoPK(certificacaoForm.getCertificacaoPK());
		}

		certificacao.setCertificacaoNome(certificacaoForm.getCertificacaoNome().replaceAll("\\s+"," "));
		certificacao.setCertificacaoDescricao(certificacaoForm.getCertificacaoDescricao().replaceAll("\\s+"," "));

		certificacao.setCertificacaoMotivoOperacao(certificacaoForm.getCertificacaoMotivoOperacao().replaceAll("\\s+"," "));
		certificacao.setCertificacaoStatus(certificacaoForm.getCertificacaoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(certificacaoForm.getCertificacaoResponsavel()));
		certificacao.setColaboradorResponsavel(colaborador);

		return certificacao;
	}

	@Transactional
	public CertificacaoForm converteCertificacao(Certificacao certificacao) {
	
		CertificacaoForm certificacaoForm = new CertificacaoForm();
		
		certificacaoForm.setCertificacaoPK(certificacao.getCertificacaoPK());
		certificacaoForm.setCertificacaoNome(certificacao.getCertificacaoNome());
		certificacaoForm.setCertificacaoDescricao(certificacao.getCertificacaoDescricao());

		certificacaoForm.setCertificacaoMotivoOperacao(certificacao.getCertificacaoMotivoOperacao());
		certificacaoForm.setCertificacaoStatus(AtributoStatus.valueOf(certificacao.getCertificacaoStatus()));

		certificacaoForm.setCertificacaoResponsavel(certificacao.getColaboradorResponsavel().getPessoaPK()+"");
		
	return certificacaoForm;
	}
	
	@Transactional
	public CertificacaoForm converteCertificacaoView(Certificacao certificacao) {
	
		CertificacaoForm certificacaoForm = new CertificacaoForm();
		
		certificacaoForm.setCertificacaoPK(certificacao.getCertificacaoPK());
		certificacaoForm.setCertificacaoNome(certificacao.getCertificacaoNome());
		certificacaoForm.setCertificacaoDescricao(certificacao.getCertificacaoDescricao());

		certificacaoForm.setCertificacaoMotivoOperacao(certificacao.getCertificacaoMotivoOperacao());
		certificacaoForm.setCertificacaoStatus(AtributoStatus.valueOf(certificacao.getCertificacaoStatus()));

		certificacaoForm.setCertificacaoResponsavel(certificacao.getColaboradorResponsavel().getPessoaNome());
		
	return certificacaoForm;
	}
	

	@Transactional
	public CertificacaoForm certificacaoParametros(CertificacaoForm certificacaoForm) {
	
		
		certificacaoForm.setCertificacaoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return certificacaoForm;
	}

	@Override
	public List<Certificacao> getByCertificacaoDescricao(String certificacaoDescricao,Pageable pageable) {
		return certificacaoRepository.findByCertificacaoDescricao(certificacaoDescricao,pageable);
	}

	@Override
	public List<Certificacao> getByCertificacaoNome(String certificacaoNome,Pageable pageable) {
		return certificacaoRepository.findByCertificacaoNome(certificacaoNome,pageable);
	}


	@Override
	public List<Certificacao> getByCertificacaoDescricao(String certificacaoDescricao) {
		return certificacaoRepository.findByCertificacaoDescricao(certificacaoDescricao);
	}

	@Override
	public List<Certificacao> getByCertificacaoNome(String certificacaoNome) {
		return certificacaoRepository.findByCertificacaoNome(certificacaoNome);
	}



}
