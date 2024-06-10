package br.com.j4business.saga.treinamentoimagem.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.service.ImagemService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagem;
import br.com.j4business.saga.treinamentoimagem.model.TreinamentoImagemForm;
import br.com.j4business.saga.treinamentoimagem.repository.TreinamentoImagemRepository;

@Service("treinamentoImagemService")
public class TreinamentoImagemServiceImpl implements TreinamentoImagemService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TreinamentoImagemRepository treinamentoImagemRepository;

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(TreinamentoImagemService.class.getName());


	@Override
	public List<TreinamentoImagem> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return treinamentoImagemRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<TreinamentoImagem> getByImagemNome(String imagemNome,Pageable pageable) {
		return treinamentoImagemRepository.findByImagemNome(imagemNome,pageable);
	}

	@Override
	public List<TreinamentoImagem> getByTreinamentoNome(String treinamentoNome) {
		return treinamentoImagemRepository.findByTreinamentoNome(treinamentoNome);
	}

	@Override
	public List<TreinamentoImagem> getByImagemNome(String imagemNome) {
		return treinamentoImagemRepository.findByImagemNome(imagemNome);
	}

	@Override
	public List<TreinamentoImagem> getByImagemPK(long imagemPK,Pageable pageable) {
		return treinamentoImagemRepository.findByImagemPK(imagemPK,pageable);
	}

	@Override
	public List<TreinamentoImagem> getByTreinamentoPK(long treinamentoPK,Pageable pageable) {
		return treinamentoImagemRepository.findByTreinamentoPK(treinamentoPK,pageable);
	}

	@Override
	public List<TreinamentoImagem> getByTreinamentoPK(long treinamentoPK) {
		return treinamentoImagemRepository.findByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoImagem> getTreinamentoImagemAtivoByTreinamentoPK(long treinamentoPK) {
		return treinamentoImagemRepository.findTreinamentoImagemAtivoByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<TreinamentoImagem> getTreinamentoImagemAll(Pageable pageable) {
		return treinamentoImagemRepository.findTreinamentoImagemAll(pageable);
	}

	@Override
	public TreinamentoImagem getTreinamentoImagemByTreinamentoImagemPK(long treinamentoImagemPK) {
		Optional<TreinamentoImagem> treinamentoImagem = treinamentoImagemRepository.findById(treinamentoImagemPK);
		return treinamentoImagem.get();
	}

	@Override
	public TreinamentoImagem getByTreinamentoAndImagem (Treinamento treinamento,Imagem imagem) {
		
		return treinamentoImagemRepository.findByTreinamentoAndImagem(treinamento,imagem);
	}

	@Transactional
	public TreinamentoImagem create(TreinamentoImagemForm treinamentoImagemForm) {
		
		TreinamentoImagem treinamentoImagem = new TreinamentoImagem();
		
		treinamentoImagem = this.converteTreinamentoImagemForm(treinamentoImagemForm);
		
		treinamentoImagem = entityManager.merge(treinamentoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Create " + "\n Usuário => " + username + 
				" // Id => "+treinamentoImagem.getTreinamentoImagemPK() + 
				" // Treinamento Id => "+treinamentoImagem.getTreinamento().getTreinamentoPK() + 
				" // Imagem Id => "+treinamentoImagem.getImagem().getImagemPK()); 
		
		return treinamentoImagem;
	}

	@Transactional
	public TreinamentoImagem save(TreinamentoImagemForm treinamentoImagemForm) {

		TreinamentoImagem treinamentoImagem = new TreinamentoImagem();
		
		treinamentoImagem = this.converteTreinamentoImagemForm(treinamentoImagemForm);
		
		treinamentoImagem = entityManager.merge(treinamentoImagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoImagem.getTreinamentoImagemPK() + 
										" // Treinamento Id => "+treinamentoImagem.getTreinamento().getTreinamentoPK() + 
										" // Imagem Id => "+treinamentoImagem.getImagem().getImagemPK());
		return treinamentoImagem;
	}

	@Transactional
	public void delete(Long treinamentoImagemPK) {

		TreinamentoImagem treinamentoImagemTemp = this.getTreinamentoImagemByTreinamentoImagemPK(treinamentoImagemPK);

		treinamentoImagemRepository.delete(treinamentoImagemTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("TreinamentoImagem Save " + "\n Usuário => " + username + 
										" // Id => "+treinamentoImagemTemp.getTreinamentoImagemPK() + 
										" // Treinamento Id => "+treinamentoImagemTemp.getTreinamento().getTreinamentoPK() + 
										" // Imagem Id => "+treinamentoImagemTemp.getImagem().getImagemPK()); 
    }

	@Transactional
	public void deleteByImagem(Imagem imagem) {
		
		List<TreinamentoImagem> treinamentoImagemList = treinamentoImagemRepository.findByImagem(imagem);

		for (TreinamentoImagem treinamentoImagem2 : treinamentoImagemList) {
			treinamentoImagemRepository.delete(treinamentoImagem2);	
		}
		

		String username = usuarioSeguranca.getUsuarioLogado();

		treinamentoImagemList.forEach((TreinamentoImagem treinamentoImagem) -> {
			
			logger.info("TreinamentoImagem Delete2 " + "\n Usuário => " + username + 
											" // Id => "+treinamentoImagem.getTreinamentoImagemPK() + 
											" // Treinamento Id => "+treinamentoImagem.getTreinamento().getTreinamentoPK() + 
											" // Imagem Id => "+treinamentoImagem.getImagem().getImagemPK()); 
		});
		
    }

	@Transactional
	public TreinamentoImagem converteTreinamentoImagemForm(TreinamentoImagemForm treinamentoImagemForm) {
		
		TreinamentoImagem treinamentoImagem = new TreinamentoImagem();
		
		if (treinamentoImagemForm.getTreinamentoImagemPK() > 0) {
			treinamentoImagem = this.getTreinamentoImagemByTreinamentoImagemPK(treinamentoImagemForm.getTreinamentoImagemPK());
		}
		
		
		Imagem imagem = imagemService.getImagemByImagemPK(Long.parseLong(treinamentoImagemForm.getImagemNome()));
		treinamentoImagem.setImagem(imagem);

		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(Long.parseLong(treinamentoImagemForm.getTreinamentoNome()));
		treinamentoImagem.setTreinamento(treinamento);

		treinamentoImagem.setTreinamentoImagemMotivoOperacao(treinamentoImagemForm.getTreinamentoImagemMotivoOperacao().replaceAll("\\s+"," "));
		treinamentoImagem.setTreinamentoImagemStatus(treinamentoImagemForm.getTreinamentoImagemStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(treinamentoImagemForm.getTreinamentoImagemResponsavel()));
		treinamentoImagem.setColaboradorResponsavel(colaborador);
		
		
		return treinamentoImagem;
	}

	@Transactional
	public TreinamentoImagemForm converteTreinamentoImagem(TreinamentoImagem treinamentoImagem) {
	
		TreinamentoImagemForm treinamentoImagemForm = new TreinamentoImagemForm();
		
		treinamentoImagemForm.setTreinamentoImagemPK(treinamentoImagem.getTreinamentoImagemPK());
		treinamentoImagemForm.setTreinamentoPK(treinamentoImagem.getTreinamento().getTreinamentoPK());
		treinamentoImagemForm.setImagemPK(treinamentoImagem.getImagem().getImagemPK());
		treinamentoImagemForm.setTreinamentoNome(treinamentoImagem.getTreinamento().getTreinamentoPK()+"");
		treinamentoImagemForm.setImagemNome(treinamentoImagem.getImagem().getImagemPK()+"");

		treinamentoImagemForm.setTreinamentoImagemMotivoOperacao(treinamentoImagem.getTreinamentoImagemMotivoOperacao());
		treinamentoImagemForm.setTreinamentoImagemStatus(AtributoStatus.valueOf(treinamentoImagem.getTreinamentoImagemStatus()));
		treinamentoImagemForm.setTreinamentoImagemResponsavel(treinamentoImagem.getTreinamento().getTreinamentoPK()+"");
		
	return treinamentoImagemForm;
	}
	
	@Transactional
	public TreinamentoImagemForm converteTreinamentoImagemView(TreinamentoImagem treinamentoImagem) {
	
		TreinamentoImagemForm treinamentoImagemForm = new TreinamentoImagemForm();
		
		treinamentoImagemForm.setTreinamentoImagemPK(treinamentoImagem.getTreinamentoImagemPK());
		treinamentoImagemForm.setTreinamentoPK(treinamentoImagem.getTreinamento().getTreinamentoPK());
		treinamentoImagemForm.setImagemPK(treinamentoImagem.getImagem().getImagemPK());
		treinamentoImagemForm.setTreinamentoNome(treinamentoImagem.getTreinamento().getTreinamentoNome());
		treinamentoImagemForm.setImagemNome(treinamentoImagem.getImagem().getImagemNome());

		treinamentoImagemForm.setTreinamentoImagemMotivoOperacao(treinamentoImagem.getTreinamentoImagemMotivoOperacao());
		treinamentoImagemForm.setTreinamentoImagemStatus(AtributoStatus.valueOf(treinamentoImagem.getTreinamentoImagemStatus()));
		treinamentoImagemForm.setTreinamentoImagemResponsavel(treinamentoImagem.getTreinamento().getTreinamentoNome());
		
	return treinamentoImagemForm;
	}
	

	@Transactional
	public TreinamentoImagemForm treinamentoImagemParametros(TreinamentoImagemForm treinamentoImagemForm) {
		treinamentoImagemForm.setTreinamentoImagemStatus(AtributoStatus.valueOf("ATIVO"));
	return treinamentoImagemForm;
	}
	

}
