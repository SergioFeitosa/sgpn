package br.com.j4business.saga.elemento.service;

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
import br.com.j4business.saga.elemento.model.Elemento;
import br.com.j4business.saga.elemento.model.ElementoForm;
import br.com.j4business.saga.elemento.repository.ElementoRepository;

@Service("elementoService")
public class ElementoServiceImpl implements ElementoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ElementoRepository elementoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ElementoService.class.getName());

	@Override
	public List<Elemento> getElementoAll() {
		return elementoRepository.findAll();
	}

	@Override
	public Page<Elemento> getElementoAll(Pageable pageable) {
		return elementoRepository.findAll(pageable);
	}

	@Override
	public Elemento getElementoByElementoPK(long elementoPK) {
		Optional<Elemento> elemento = elementoRepository.findById(elementoPK);
		return elemento.get();
	}

	@Transactional
	public Elemento create(ElementoForm elementoForm) {
		
		Elemento elemento = new Elemento();
		
		elemento = this.converteElementoForm(elementoForm);
		
		elemento = entityManager.merge(elemento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Elemento Create " + "\n Usuário => " + username + 
										" // Id => "+elemento.getElementoPK() + 
										" // Elemento => "+elemento.getElementoNome() + 
										" // Descrição => "+ elemento.getElementoDescricao());
		
		return elemento;
	}

	@Transactional
	public Elemento save(ElementoForm elementoForm) {
		
		Elemento elemento = new Elemento();
		
		elemento = this.converteElementoForm(elementoForm);
		
		elemento = entityManager.merge(elemento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Elemento Save " + "\n Usuário => " + username + 
										" // Id => "+elemento.getElementoPK() + 
										" // Elemento => "+elemento.getElementoNome() + 
										" // Descrição => "+ elemento.getElementoDescricao());
		

		return elemento;
		
	}

	@Transactional
	public void delete(Long elementoId) {

		Elemento elemento = this.getElementoByElementoPK(elementoId);
		
		elementoRepository.delete(elemento);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Elemento Delete " + "\n Usuário => " + username + 
										" // Id => "+elemento.getElementoPK() + 
										" // Elemento => "+elemento.getElementoNome() + 
										" // Descrição => "+ elemento.getElementoDescricao());
		

    }

	@Transactional
	public Elemento converteElementoForm(ElementoForm elementoForm) {
		
		Elemento elemento = new Elemento();
		
		if (elementoForm.getElementoPK() > 0) {
			elemento = this.getElementoByElementoPK(elementoForm.getElementoPK());
		}
		elemento.setElementoNome(elementoForm.getElementoNome().replaceAll("\\s+"," "));
		elemento.setElementoDescricao(elementoForm.getElementoDescricao().replaceAll("\\s+"," "));

		elemento.setElementoMotivoOperacao(elementoForm.getElementoMotivoOperacao().replaceAll("\\s+"," "));
		elemento.setElementoStatus(elementoForm.getElementoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(elementoForm.getElementoResponsavel()));
		elemento.setColaboradorResponsavel(colaborador);
		
		return elemento;
	}

	@Transactional
	public ElementoForm converteElemento(Elemento elemento) {
	
		ElementoForm elementoForm = new ElementoForm();
		
		elementoForm.setElementoPK(elemento.getElementoPK());
		elementoForm.setElementoNome(elemento.getElementoNome());
		elementoForm.setElementoDescricao(elemento.getElementoDescricao());

		elementoForm.setElementoMotivoOperacao(elemento.getElementoMotivoOperacao());
		elementoForm.setElementoStatus(AtributoStatus.valueOf(elemento.getElementoStatus()));

		elementoForm.setElementoResponsavel(elemento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return elementoForm;
	}
	
	@Transactional
	public ElementoForm converteElementoView(Elemento elemento) {
	
		ElementoForm elementoForm = new ElementoForm();
		
		elementoForm.setElementoPK(elemento.getElementoPK());
		elementoForm.setElementoNome(elemento.getElementoNome());
		elementoForm.setElementoDescricao(elemento.getElementoDescricao());

		elementoForm.setElementoMotivoOperacao(elemento.getElementoMotivoOperacao());
		elementoForm.setElementoStatus(AtributoStatus.valueOf(elemento.getElementoStatus()));

		elementoForm.setElementoResponsavel(elemento.getColaboradorResponsavel().getPessoaNome());
		
	return elementoForm;
	}
	

	@Transactional
	public ElementoForm elementoParametros(ElementoForm elementoForm) {
	
		
		elementoForm.setElementoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return elementoForm;
	}

	@Override
	public Elemento getByElementoNomeCompleto(String elementoNome) {
		return elementoRepository.findByElementoNomeCompleto(elementoNome);
	}

	@Override
	public Elemento getByElementoDescricaoCompleto(String elementoDescricao) {
		return elementoRepository.findByElementoDescricaoCompleto(elementoDescricao);
	}

	@Override
	public List<Elemento> getByElementoDescricao(String elementoDescricao,Pageable pageable) {
		return elementoRepository.findByElementoDescricao(elementoDescricao,pageable);
	}

	@Override
	public List<Elemento> getByElementoNome(String elementoNome,Pageable pageable) {
		return elementoRepository.findByElementoNome(elementoNome,pageable);
	}

	@Override
	public List<Elemento> getByElementoDescricao(String elementoDescricao) {
		return elementoRepository.findByElementoDescricao(elementoDescricao);
	}

	@Override
	public List<Elemento> getByElementoNome(String elementoNome) {
		return elementoRepository.findByElementoNome(elementoNome);
	}



}
