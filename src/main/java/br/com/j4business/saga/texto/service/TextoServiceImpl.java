package br.com.j4business.saga.texto.service;

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
import br.com.j4business.saga.texto.model.Texto;
import br.com.j4business.saga.texto.model.TextoForm;
import br.com.j4business.saga.texto.repository.TextoRepository;

@Service("textoService")
public class TextoServiceImpl implements TextoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TextoRepository textoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(TextoService.class.getName());

	@Override
	public List<Texto> getTextoAll() {
		return textoRepository.findAll();
	}

	@Override
	public Page<Texto> getTextoAll(Pageable pageable) {
		return textoRepository.findAll(pageable);
	}

	@Override
	public Texto getTextoByTextoPK(long textoPK) {
		
		Optional<Texto> texto = textoRepository.findById(textoPK);
		return texto.get();
	}

	@Transactional
	public Texto create(TextoForm textoForm) {
		
		Texto texto = new Texto();
		
		texto = this.converteTextoForm(textoForm);
		
		texto = entityManager.merge(texto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Create " + "\n Usuário => " + username + 
										" // Id => "+texto.getTextoPK() + 
										" // Texto => "+texto.getTextoNome() + 
										" // Descrição => "+ texto.getTextoDescricao());
		
		return texto;
	}

	@Transactional
	public Texto save(TextoForm textoForm) {
		
		Texto texto = new Texto();
		
		texto = this.converteTextoForm(textoForm);
		
		texto = entityManager.merge(texto);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Save " + "\n Usuário => " + username + 
										" // Id => "+texto.getTextoPK() + 
										" // Texto => "+texto.getTextoNome() + 
										" // Descrição => "+ texto.getTextoDescricao());
		

		return texto;
		
	}

	@Transactional
	public void delete(Long textoId) {

		Texto texto = this.getTextoByTextoPK(textoId);
		
		textoRepository.delete(texto);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Texto Delete " + "\n Usuário => " + username + 
										" // Id => "+texto.getTextoPK() + 
										" // Texto => "+texto.getTextoNome() + 
										" // Descrição => "+ texto.getTextoDescricao());
		

    }

	@Transactional
	public Texto converteTextoForm(TextoForm textoForm) {
		
		Texto texto = new Texto();
		
		if (textoForm.getTextoPK() > 0) {
			texto = this.getTextoByTextoPK(textoForm.getTextoPK());
		}
		texto.setTextoNome(textoForm.getTextoNome().replaceAll("\\s+"," "));
		texto.setTextoDescricao(textoForm.getTextoDescricao().replaceAll("\\s+"," "));

		texto.setTextoMotivoOperacao(textoForm.getTextoMotivoOperacao().replaceAll("\\s+"," "));
		texto.setTextoStatus(textoForm.getTextoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(textoForm.getTextoResponsavel()));
		texto.setColaboradorResponsavel(colaborador);
		
		texto.setTextoURL(textoForm.getTextoURL().replaceAll("\\s+"," "));
		texto.setTextoNomeBotao(textoForm.getTextoNomeBotao().replaceAll("\\s+"," "));
		
		
		return texto;
	}

	@Transactional
	public TextoForm converteTexto(Texto texto) {
	
		TextoForm textoForm = new TextoForm();
		
		textoForm.setTextoPK(texto.getTextoPK());
		textoForm.setTextoNome(texto.getTextoNome());
		textoForm.setTextoDescricao(texto.getTextoDescricao());

		textoForm.setTextoMotivoOperacao(texto.getTextoMotivoOperacao());
		textoForm.setTextoStatus(AtributoStatus.valueOf(texto.getTextoStatus()));

		textoForm.setTextoResponsavel(texto.getColaboradorResponsavel().getPessoaPK()+"");
		
		textoForm.setTextoURL(texto.getTextoURL());
		textoForm.setTextoNomeBotao(texto.getTextoNomeBotao());

		return textoForm;
	}
	
	@Transactional
	public TextoForm converteTextoView(Texto texto) {
	
		TextoForm textoForm = new TextoForm();
		
		textoForm.setTextoPK(texto.getTextoPK());
		textoForm.setTextoNome(texto.getTextoNome());
		textoForm.setTextoDescricao(texto.getTextoDescricao());

		textoForm.setTextoMotivoOperacao(texto.getTextoMotivoOperacao());
		textoForm.setTextoStatus(AtributoStatus.valueOf(texto.getTextoStatus()));

		textoForm.setTextoResponsavel(texto.getColaboradorResponsavel().getPessoaNome());
		
		textoForm.setTextoURL(texto.getTextoURL());
		textoForm.setTextoNomeBotao(texto.getTextoNomeBotao());
		
	return textoForm;
	}
	

	@Transactional
	public TextoForm textoParametros(TextoForm textoForm) {
	
		
		textoForm.setTextoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return textoForm;
	}

	@Override
	public List<Texto> getByTextoDescricao(String textoDescricao,Pageable pageable) {
		return textoRepository.findByTextoDescricao(textoDescricao,pageable);
	}

	@Override
	public List<Texto> getByTextoNome(String textoNome,Pageable pageable) {
		return textoRepository.findByTextoNome(textoNome,pageable);
	}

	@Override
	public List<Texto> getByTextoDescricao(String textoDescricao) {
		return textoRepository.findByTextoDescricao(textoDescricao);
	}

	@Override
	public List<Texto> getByTextoNome(String textoNome) {
		return textoRepository.findByTextoNome(textoNome);
	}



}
