package br.com.j4business.saga.contratoimagem.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.contratoimagem.model.ContratoImagem;
import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.contratoimagem.model.ContratoImagemForm;
import br.com.j4business.saga.contratoimagem.repository.ContratoImagemRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("contratoImagemService")
public class ContratoImagemServiceImpl implements ContratoImagemService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ContratoImagemRepository contratoImagemRepository;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ContratoImagemService.class.getName());


	@Override
	public List<ContratoImagem> getByContratoNome(String contratoNome,Pageable pageable) {
		return contratoImagemRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<ContratoImagem> getByImagemNome(String imagemNome,Pageable pageable) {
		return contratoImagemRepository.findByImagemNome(imagemNome,pageable);
	}

	@Override
	public List<ContratoImagem> getByContratoNome(String contratoNome) {
		return contratoImagemRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<ContratoImagem> getByImagemNome(String imagemNome) {
		return contratoImagemRepository.findByImagemNome(imagemNome);
	}

	@Override
	public List<ContratoImagem> getByImagemPK(long imagemPK,Pageable pageable) {
		return contratoImagemRepository.findByImagemPK(imagemPK,pageable);
	}

	@Override
	public List<ContratoImagem> getByContratoPK(long contratoPK,Pageable pageable) {
		return contratoImagemRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<ContratoImagem> getByContratoPK(long contratoPK) {
		return contratoImagemRepository.findByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoImagem> getContratoImagemAtivoByContratoPK(long contratoPK) {
		return contratoImagemRepository.findContratoImagemAtivoByContratoPK(contratoPK);
	}

	@Override
	public List<ContratoImagem> getContratoImagemAll(Pageable pageable) {
		return contratoImagemRepository.findContratoImagemAll(pageable);
	}

	@Override
	public ContratoImagem getContratoImagemByContratoImagemPK(long contratoImagemPK) {
		return contratoImagemRepository.findOne(contratoImagemPK);
	}

	@Override
	public ContratoImagem getByContratoAndImagem (Contrato contrato,Imagem imagem) {
		
		return contratoImagemRepository.findByContratoAndImagem(contrato,imagem);
	}

	@Transactional
	public ContratoImagem create(ContratoImagemForm contratoImagemForm) {
		
		ContratoImagem contratoImagem = new ContratoImagem();
		
		contratoImagem = this.converteContratoImagemForm(contratoImagemForm);
		
		contratoImagem = entityManager.merge(contratoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Create " + "\n Usuário => " + username + 
				" // Id => "+contratoImagem.getContratoImagemPK() + 
				" // Contrato Id => "+contratoImagem.getContrato().getContratoPK() + 
				" // Imagem Id => "+contratoImagem.getImagem().getImagemPK()); 
		
		return contratoImagem;
	}

	@Transactional
	public ContratoImagem save(ContratoImagemForm contratoImagemForm) {

		ContratoImagem contratoImagem = new ContratoImagem();
		
		contratoImagem = this.converteContratoImagemForm(contratoImagemForm);
		
		contratoImagem = entityManager.merge(contratoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+contratoImagem.getContratoImagemPK() + 
										" // Contrato Id => "+contratoImagem.getContrato().getContratoPK() + 
										" // Imagem Id => "+contratoImagem.getImagem().getImagemPK());
		return contratoImagem;
	}

	@Transactional
	public void delete(Long contratoImagemPK) {

		ContratoImagem contratoImagemTemp = this.getContratoImagemByContratoImagemPK(contratoImagemPK);

		contratoImagemRepository.delete(contratoImagemPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ContratoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+contratoImagemTemp.getContratoImagemPK() + 
										" // Contrato Id => "+contratoImagemTemp.getContrato().getContratoPK() + 
										" // Imagem Id => "+contratoImagemTemp.getImagem().getImagemPK()); 
    }

	@Transactional
	public void deleteByImagem(Imagem imagem) {
		
		List<ContratoImagem> contratoImagemList = contratoImagemRepository.findByImagem(imagem);

		contratoImagemRepository.delete(contratoImagemList);

		String username = usuarioSeguranca.getUsuarioLogado();

		contratoImagemList.forEach((ContratoImagem contratoImagem) -> {
			
			logger.info("ContratoImagem Delete2 " + "\n Usuário => " + username + 
											" // Id => "+contratoImagem.getContratoImagemPK() + 
											" // Contrato Id => "+contratoImagem.getContrato().getContratoPK() + 
											" // Imagem Id => "+contratoImagem.getImagem().getImagemPK()); 
		});
    }

	@Transactional
	public ContratoImagem converteContratoImagemForm(ContratoImagemForm contratoImagemForm) {
		
		ContratoImagem contratoImagem = new ContratoImagem();
		
		if (contratoImagemForm.getContratoImagemPK() > 0) {
			contratoImagem = this.getContratoImagemByContratoImagemPK(contratoImagemForm.getContratoImagemPK());
		}
		
		
		Imagem imagem = imagemService.getImagemByImagemPK(Long.parseLong(contratoImagemForm.getImagemNome()));
		contratoImagem.setImagem(imagem);

		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(contratoImagemForm.getContratoNome()));
		contratoImagem.setContrato(contrato);

		contratoImagem.setContratoImagemMotivoOperacao(contratoImagemForm.getContratoImagemMotivoOperacao().replaceAll("\\s+"," "));
		contratoImagem.setContratoImagemStatus(contratoImagemForm.getContratoImagemStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoImagemForm.getContratoImagemResponsavel()));
		contratoImagem.setColaboradorResponsavel(colaborador);
		
		
		return contratoImagem;
	}

	@Transactional
	public ContratoImagemForm converteContratoImagem(ContratoImagem contratoImagem) {
	
		ContratoImagemForm contratoImagemForm = new ContratoImagemForm();
		
		contratoImagemForm.setContratoImagemPK(contratoImagem.getContratoImagemPK());
		contratoImagemForm.setContratoPK(contratoImagem.getContrato().getContratoPK());
		contratoImagemForm.setImagemPK(contratoImagem.getImagem().getImagemPK());
		contratoImagemForm.setContratoNome(contratoImagem.getContrato().getContratoPK()+"");
		contratoImagemForm.setImagemNome(contratoImagem.getImagem().getImagemPK()+"");

		contratoImagemForm.setContratoImagemMotivoOperacao(contratoImagem.getContratoImagemMotivoOperacao());
		contratoImagemForm.setContratoImagemStatus(AtributoStatus.valueOf(contratoImagem.getContratoImagemStatus()));
		contratoImagemForm.setContratoImagemResponsavel(contratoImagem.getContrato().getContratoPK()+"");
		
	return contratoImagemForm;
	}
	
	@Transactional
	public ContratoImagemForm converteContratoImagemView(ContratoImagem contratoImagem) {
	
		ContratoImagemForm contratoImagemForm = new ContratoImagemForm();
		
		contratoImagemForm.setContratoImagemPK(contratoImagem.getContratoImagemPK());
		contratoImagemForm.setContratoPK(contratoImagem.getContrato().getContratoPK());
		contratoImagemForm.setImagemPK(contratoImagem.getImagem().getImagemPK());
		contratoImagemForm.setContratoNome(contratoImagem.getContrato().getContratoNome());
		contratoImagemForm.setImagemNome(contratoImagem.getImagem().getImagemNome());

		contratoImagemForm.setContratoImagemMotivoOperacao(contratoImagem.getContratoImagemMotivoOperacao());
		contratoImagemForm.setContratoImagemStatus(AtributoStatus.valueOf(contratoImagem.getContratoImagemStatus()));
		contratoImagemForm.setContratoImagemResponsavel(contratoImagem.getContrato().getContratoNome());
		
	return contratoImagemForm;
	}
	

	@Transactional
	public ContratoImagemForm contratoImagemParametros(ContratoImagemForm contratoImagemForm) {
		contratoImagemForm.setContratoImagemStatus(AtributoStatus.valueOf("ATIVO"));
	return contratoImagemForm;
	}
	

}
