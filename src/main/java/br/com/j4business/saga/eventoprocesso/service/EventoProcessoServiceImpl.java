package br.com.j4business.saga.eventoprocesso.service;

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
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.eventoprocesso.enumeration.EventoProcessoImpacto;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;
import br.com.j4business.saga.eventoprocesso.model.EventoProcessoForm;
import br.com.j4business.saga.eventoprocesso.repository.EventoProcessoRepository;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;

@Service("eventoProcessoService")
public class EventoProcessoServiceImpl implements EventoProcessoService {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EventoProcessoRepository eventoProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EventoProcessoService.class.getName());


	@Override
	public List<EventoProcesso> getByEventoNome(String eventoNome,Pageable pageable) {
		return eventoProcessoRepository.findByEventoNome(eventoNome,pageable);
	}

	@Override
	public List<EventoProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return eventoProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<EventoProcesso> getByEventoNome(String eventoNome) {
		return eventoProcessoRepository.findByEventoNome(eventoNome);
	}

	@Override
	public List<EventoProcesso> getByProcessoNome(String processoNome) {
		return eventoProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<EventoProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return eventoProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<EventoProcesso> getByEventoPK(long eventoPK) {
		return eventoProcessoRepository.findByEventoPK(eventoPK);
	}

	@Override
	public List<EventoProcesso> getByEventoPK(long eventoPK,Pageable pageable) {
		return eventoProcessoRepository.findByEventoPK(eventoPK,pageable);
	}

	@Override
	public List<EventoProcesso> getEventoProcessoAll(Pageable pageable) {
		return eventoProcessoRepository.findEventoProcessoAll(pageable);
	}

	@Override
	public EventoProcesso getEventoProcessoByEventoProcessoPK(long eventoProcessoPK) {
		Optional<EventoProcesso> eventoProcesso = eventoProcessoRepository.findById(eventoProcessoPK);
		return eventoProcesso.get();
	}

	@Override
	public EventoProcesso getByEventoAndProcesso (Evento evento,Processo processo) {
		
		return eventoProcessoRepository.findByEventoAndProcesso(evento,processo);
	}

	@Transactional
	public EventoProcesso create(EventoProcessoForm eventoProcessoForm) {
		
		EventoProcesso eventoProcesso = new EventoProcesso();
		
		eventoProcesso = this.converteEventoProcessoForm(eventoProcessoForm);
		
		eventoProcesso = entityManager.merge(eventoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+eventoProcesso.getEventoProcessoPK() + 
				" // Evento Id => "+eventoProcesso.getEvento().getEventoPK() + 
				" // Processo Id => "+eventoProcesso.getProcesso().getProcessoPK()); 
		
		return eventoProcesso;
	}

	@Transactional
	public EventoProcesso save(EventoProcessoForm eventoProcessoForm) {

		EventoProcesso eventoProcesso = new EventoProcesso();
		
		eventoProcesso = this.converteEventoProcessoForm(eventoProcessoForm);
		
		eventoProcesso = entityManager.merge(eventoProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EventoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+eventoProcesso.getEventoProcessoPK() + 
										" // Evento Id => "+eventoProcesso.getEvento().getEventoPK() + 
										" // Processo Id => "+eventoProcesso.getProcesso().getProcessoPK()); 
		return eventoProcesso;
	}

	@Transactional
	public void delete(Long eventoProcessoPK) {

		EventoProcesso eventoProcessoTemp = this.getEventoProcessoByEventoProcessoPK(eventoProcessoPK);

		eventoProcessoRepository.delete(eventoProcessoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EventoProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+eventoProcessoTemp.getEventoProcessoPK() + 
										" // Evento Id => "+eventoProcessoTemp.getEvento().getEventoPK() + 
										" // Processo Id => "+eventoProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<EventoProcesso> eventoProcessoList = eventoProcessoRepository.findByProcesso(processo);

		for (EventoProcesso eventoProcesso2 : eventoProcessoList) {
			eventoProcessoRepository.delete(eventoProcesso2);
			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> {
			
			logger.info("EventoProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+eventoProcesso.getEventoProcessoPK() + 
											" // Evento Id => "+eventoProcesso.getEvento().getEventoPK() + 
											" // Processo Id => "+eventoProcesso.getProcesso().getProcessoPK()); 
		});
    }

	@Transactional
	public EventoProcesso converteEventoProcessoForm(EventoProcessoForm eventoProcessoForm) {
		
		EventoProcesso eventoProcesso = new EventoProcesso();
		
		if (eventoProcessoForm.getEventoProcessoPK() > 0) {
			eventoProcesso = this.getEventoProcessoByEventoProcessoPK(eventoProcessoForm.getEventoProcessoPK());
		}
		
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(eventoProcessoForm.getProcessoNome()));
		eventoProcesso.setProcesso(processo);

		Evento evento = eventoService.getEventoByEventoPK(Long.parseLong(eventoProcessoForm.getEventoNome()));
		eventoProcesso.setEvento(evento);

		eventoProcesso.setEventoProcessoImpacto(eventoProcessoForm.getEventoProcessoImpacto()+"".replaceAll("\\s+"," "));

		eventoProcesso.setEventoProcessoMotivoOperacao(eventoProcessoForm.getEventoProcessoMotivoOperacao().replaceAll("\\s+"," "));
		eventoProcesso.setEventoProcessoStatus(eventoProcessoForm.getEventoProcessoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(eventoProcessoForm.getEventoProcessoResponsavel()));
		eventoProcesso.setColaboradorResponsavel(colaborador);
		
		return eventoProcesso;
	}

	@Transactional
	public EventoProcessoForm converteEventoProcesso(EventoProcesso eventoProcesso) {
	
		EventoProcessoForm eventoProcessoForm = new EventoProcessoForm();
		
		eventoProcessoForm.setEventoProcessoPK(eventoProcesso.getEventoProcessoPK());
		eventoProcessoForm.setEventoNome(eventoProcesso.getEvento().getEventoPK()+"");
		eventoProcessoForm.setProcessoNome(eventoProcesso.getProcesso().getProcessoPK()+"");

		eventoProcessoForm.setEventoProcessoImpacto(EventoProcessoImpacto.valueOf(eventoProcesso.getEventoProcessoImpacto()));
		
		eventoProcessoForm.setEventoProcessoResponsavel(eventoProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		eventoProcessoForm.setEventoProcessoMotivoOperacao(eventoProcesso.getEventoProcessoMotivoOperacao());
		eventoProcessoForm.setEventoProcessoStatus(AtributoStatus.valueOf(eventoProcesso.getEventoProcessoStatus()));

	return eventoProcessoForm;
	}
	
	@Transactional
	public EventoProcessoForm converteEventoProcessoView(EventoProcesso eventoProcesso) {
	
		EventoProcessoForm eventoProcessoForm = new EventoProcessoForm();
		
		eventoProcessoForm.setEventoProcessoPK(eventoProcesso.getEventoProcessoPK());
		eventoProcessoForm.setEventoNome(eventoProcesso.getEvento().getEventoNome());
		eventoProcessoForm.setProcessoNome(eventoProcesso.getProcesso().getProcessoNome());
		eventoProcessoForm.setEventoProcessoImpacto(EventoProcessoImpacto.valueOf(eventoProcesso.getEventoProcessoImpacto()));
		eventoProcessoForm.setEventoProcessoResponsavel(eventoProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		eventoProcessoForm.setEventoProcessoMotivoOperacao(eventoProcesso.getEventoProcessoMotivoOperacao());
		eventoProcessoForm.setEventoProcessoStatus(AtributoStatus.valueOf(eventoProcesso.getEventoProcessoStatus()));
		
	return eventoProcessoForm;
	}
	

	@Transactional
	public EventoProcessoForm eventoProcessoParametros(EventoProcessoForm eventoProcessoForm) {
		
		eventoProcessoForm.setEventoProcessoStatus(AtributoStatus.valueOf("ATIVO"));
		eventoProcessoForm.setEventoProcessoImpacto(EventoProcessoImpacto.valueOf("FRACO"));
		
	return eventoProcessoForm;
	}
	

}
