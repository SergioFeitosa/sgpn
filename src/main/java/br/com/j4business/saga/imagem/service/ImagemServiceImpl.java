package br.com.j4business.saga.imagem.service;

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
import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.model.ImagemForm;
import br.com.j4business.saga.imagem.repository.ImagemRepository;

@Service("imagemService")
public class ImagemServiceImpl implements ImagemService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ImagemRepository imagemRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ImagemService.class.getName());

	@Override
	public List<Imagem> getImagemAll() {
		return imagemRepository.findAll();
	}

	@Override
	public Page<Imagem> getImagemAll(Pageable pageable) {
		return imagemRepository.findAll(pageable);
	}

	@Override
	public Imagem getImagemByImagemPK(long imagemPK) {
		
		Optional<Imagem> imagem = imagemRepository.findById(imagemPK);
		return imagem.get();
	}

	@Transactional
	public Imagem create(ImagemForm imagemForm) {
		
		Imagem imagem = new Imagem();
		
		imagem = this.converteImagemForm(imagemForm);
		
		imagem = entityManager.merge(imagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Create " + "\n Usuário => " + username + 
										" // Id => "+imagem.getImagemPK() + 
										" // Imagem => "+imagem.getImagemNome() + 
										" // Descrição => "+ imagem.getImagemDescricao());
		
		return imagem;
	}

	@Transactional
	public Imagem save(ImagemForm imagemForm) {
		
		Imagem imagem = new Imagem();
		
		imagem = this.converteImagemForm(imagemForm);
		
		imagem = entityManager.merge(imagem);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Save " + "\n Usuário => " + username + 
										" // Id => "+imagem.getImagemPK() + 
										" // Imagem => "+imagem.getImagemNome() + 
										" // Descrição => "+ imagem.getImagemDescricao());
		

		return imagem;
		
	}

	@Transactional
	public void delete(Long imagemId) {

		Imagem imagem = this.getImagemByImagemPK(imagemId);
		
		imagemRepository.delete(imagem);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Imagem Delete " + "\n Usuário => " + username + 
										" // Id => "+imagem.getImagemPK() + 
										" // Imagem => "+imagem.getImagemNome() + 
										" // Descrição => "+ imagem.getImagemDescricao());
		

    }

	@Transactional
	public Imagem converteImagemForm(ImagemForm imagemForm) {
		
		Imagem imagem = new Imagem();
		
		if (imagemForm.getImagemPK() > 0) {
			imagem = this.getImagemByImagemPK(imagemForm.getImagemPK());
		}
		imagem.setImagemNome(imagemForm.getImagemNome().replaceAll("\\s+"," "));
		imagem.setImagemDescricao(imagemForm.getImagemDescricao().replaceAll("\\s+"," "));

		imagem.setImagemMotivoOperacao(imagemForm.getImagemMotivoOperacao().replaceAll("\\s+"," "));
		imagem.setImagemStatus(imagemForm.getImagemStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(imagemForm.getImagemResponsavel()));
		imagem.setColaboradorResponsavel(colaborador);
		
		imagem.setImagemURL(imagemForm.getImagemURL().replaceAll("\\s+"," "));
		imagem.setImagemNomeBotao(imagemForm.getImagemNomeBotao().replaceAll("\\s+"," "));
		
		
		return imagem;
	}

	@Transactional
	public ImagemForm converteImagem(Imagem imagem) {
	
		ImagemForm imagemForm = new ImagemForm();
		
		imagemForm.setImagemPK(imagem.getImagemPK());
		imagemForm.setImagemNome(imagem.getImagemNome());
		imagemForm.setImagemDescricao(imagem.getImagemDescricao());

		imagemForm.setImagemMotivoOperacao(imagem.getImagemMotivoOperacao());
		imagemForm.setImagemStatus(AtributoStatus.valueOf(imagem.getImagemStatus()));

		imagemForm.setImagemResponsavel(imagem.getColaboradorResponsavel().getPessoaPK()+"");
		
		imagemForm.setImagemURL(imagem.getImagemURL());
		imagemForm.setImagemNomeBotao(imagem.getImagemNomeBotao());

		return imagemForm;
	}
	
	@Transactional
	public ImagemForm converteImagemView(Imagem imagem) {
	
		ImagemForm imagemForm = new ImagemForm();
		
		imagemForm.setImagemPK(imagem.getImagemPK());
		imagemForm.setImagemNome(imagem.getImagemNome());
		imagemForm.setImagemDescricao(imagem.getImagemDescricao());

		imagemForm.setImagemMotivoOperacao(imagem.getImagemMotivoOperacao());
		imagemForm.setImagemStatus(AtributoStatus.valueOf(imagem.getImagemStatus()));

		imagemForm.setImagemResponsavel(imagem.getColaboradorResponsavel().getPessoaNome());
		
		imagemForm.setImagemURL(imagem.getImagemURL());
		imagemForm.setImagemNomeBotao(imagem.getImagemNomeBotao());
		
	return imagemForm;
	}
	

	@Transactional
	public ImagemForm imagemParametros(ImagemForm imagemForm) {
	
		
		imagemForm.setImagemStatus(AtributoStatus.valueOf("ATIVO"));

		
	return imagemForm;
	}

	@Override
	public List<Imagem> getByImagemDescricao(String imagemDescricao,Pageable pageable) {
		return imagemRepository.findByImagemDescricao(imagemDescricao,pageable);
	}

	@Override
	public List<Imagem> getByImagemNome(String imagemNome,Pageable pageable) {
		return imagemRepository.findByImagemNome(imagemNome,pageable);
	}

	@Override
	public List<Imagem> getByImagemDescricao(String imagemDescricao) {
		return imagemRepository.findByImagemDescricao(imagemDescricao);
	}

	@Override
	public List<Imagem> getByImagemNome(String imagemNome) {
		return imagemRepository.findByImagemNome(imagemNome);
	}



}
