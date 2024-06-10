package br.com.j4business.saga.evento.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoPrioridade;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.evento.model.EventoForm;
import br.com.j4business.saga.evento.repository.EventoRepository;

@Service("eventoService")
public class EventoServiceImpl implements EventoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EventoService.class.getName());

	@Override
	public List<Evento> getEventoAll() {
		return eventoRepository.findAll();
	}

	@Override
	public Page<Evento> getEventoAll(Pageable pageable) {
		return eventoRepository.findAll(pageable);
	}

	@Override
	public Evento getEventoByEventoPK(long eventoPK) {
		
		Optional<Evento> evento = eventoRepository.findById(eventoPK);
		return evento.get();
	}

	@Transactional
	public Evento create(EventoForm eventoForm) {
		
		Evento evento = new Evento();
		
		evento = this.converteEventoForm(eventoForm);
		
		evento = entityManager.merge(evento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Evento Create " + "\n Usuário => " + username + 
										" // Id => "+evento.getEventoPK() + 
										" // Evento => "+evento.getEventoNome() + 
										" // Descrição => "+ evento.getEventoDescricao());
		
		return evento;
	}

	@Transactional
	public Evento save(EventoForm eventoForm) {
		
		Evento evento = new Evento();
		
		evento = this.converteEventoForm(eventoForm);
		
		evento = entityManager.merge(evento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Evento Save " + "\n Usuário => " + username + 
										" // Id => "+evento.getEventoPK() + 
										" // Evento => "+evento.getEventoNome() + 
										" // Descrição => "+ evento.getEventoDescricao());
		

		return evento;
		
	}

	@Transactional
	public void delete(Long eventoId) {

		Evento evento = this.getEventoByEventoPK(eventoId);
		
		eventoRepository.delete(evento);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Evento Delete " + "\n Usuário => " + username + 
										" // Id => "+evento.getEventoPK() + 
										" // Evento => "+evento.getEventoNome() + 
										" // Descrição => "+ evento.getEventoDescricao());
		

    }

	@Transactional
	public Evento converteEventoForm(EventoForm eventoForm) {
		
		Evento evento = new Evento();
		
		if (eventoForm.getEventoPK() > 0) {
			evento = this.getEventoByEventoPK(eventoForm.getEventoPK());
		}
		evento.setEventoNome(eventoForm.getEventoNome().replaceAll("\\s+"," "));
		evento.setEventoDescricao(eventoForm.getEventoDescricao().replaceAll("\\s+"," "));

		evento.setEventoDataInicio(eventoForm.getEventoDataInicio().replaceAll("\\s+"," "));
		evento.setEventoDataTermino(eventoForm.getEventoDataTermino().replaceAll("\\s+"," "));
		evento.setEventoFonte(eventoForm.getEventoFonte().replaceAll("\\s+"," "));
		evento.setEventoPrioridade(eventoForm.getEventoPrioridade()+"".replaceAll("\\s+"," "));

		evento.setEventoMotivoOperacao(eventoForm.getEventoMotivoOperacao().replaceAll("\\s+"," "));
		evento.setEventoStatus(eventoForm.getEventoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(eventoForm.getEventoResponsavel()));
		evento.setColaboradorResponsavel(colaborador);

		return evento;
	}

	@Transactional
	public EventoForm converteEvento(Evento evento) {
	
		EventoForm eventoForm = new EventoForm();
		
		eventoForm.setEventoPK(evento.getEventoPK());
		eventoForm.setEventoNome(evento.getEventoNome());
		eventoForm.setEventoDescricao(evento.getEventoDescricao());

		eventoForm.setEventoDataInicio(evento.getEventoDataInicio());
		eventoForm.setEventoDataTermino(evento.getEventoDataTermino());
		eventoForm.setEventoFonte(evento.getEventoFonte());
		eventoForm.setEventoPrioridade(AtributoPrioridade.valueOf(evento.getEventoPrioridade()));
		eventoForm.setEventoResponsavel(evento.getColaboradorResponsavel().getPessoaPK()+"");
		
		eventoForm.setEventoMotivoOperacao(evento.getEventoMotivoOperacao());
		eventoForm.setEventoStatus(AtributoStatus.valueOf(evento.getEventoStatus()));

	return eventoForm;
	}
	
	@Transactional
	public EventoForm converteEventoView(Evento evento) {
	
		EventoForm eventoForm = new EventoForm();
		
		eventoForm.setEventoPK(evento.getEventoPK());
		eventoForm.setEventoNome(evento.getEventoNome());
		eventoForm.setEventoDescricao(evento.getEventoDescricao());

		eventoForm.setEventoDataInicio(evento.getEventoDataInicio());
		eventoForm.setEventoDataTermino(evento.getEventoDataTermino());
		eventoForm.setEventoFonte(evento.getEventoFonte());
		eventoForm.setEventoPrioridade(AtributoPrioridade.valueOf(evento.getEventoPrioridade()));
		
		eventoForm.setEventoResponsavel(evento.getColaboradorResponsavel().getPessoaNome());
		
		eventoForm.setEventoMotivoOperacao(evento.getEventoMotivoOperacao());
		eventoForm.setEventoStatus(AtributoStatus.valueOf(evento.getEventoStatus()));

		return eventoForm;
	}
	

	@Transactional
	public EventoForm eventoParametros(EventoForm eventoForm) {
	
		
		eventoForm.setEventoStatus(AtributoStatus.valueOf("ATIVO"));
		eventoForm.setEventoPrioridade(AtributoPrioridade.valueOf("BAIXA"));

		
	return eventoForm;
	}

	@Override
	public List<Evento> getByEventoDescricao(String eventoDescricao,Pageable pageable) {
		return eventoRepository.findByEventoDescricao(eventoDescricao,pageable);
	}

	@Override
	public List<Evento> getByEventoNome(String eventoNome,Pageable pageable) {
		return eventoRepository.findByEventoNome(eventoNome,pageable);
	}


	@Override
	public List<Evento> getByEventoDescricao(String eventoDescricao) {
		return eventoRepository.findByEventoDescricao(eventoDescricao);
	}

	@Override
	public List<Evento> getByEventoNome(String eventoNome) {
		return eventoRepository.findByEventoNome(eventoNome);
	}



}
