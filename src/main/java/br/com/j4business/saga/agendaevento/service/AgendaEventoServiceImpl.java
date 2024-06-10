package br.com.j4business.saga.agendaevento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.email.Mensagem;
import br.com.j4business.saga.evento.model.Evento;
import br.com.j4business.saga.evento.service.EventoService;
import br.com.j4business.saga.eventoprocesso.model.EventoProcesso;
import br.com.j4business.saga.eventoprocesso.service.EventoProcessoService;
import jakarta.transaction.Transactional;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.agendaevento.enumeration.AgendaEventoEnvio;
import br.com.j4business.saga.agendaevento.model.AgendaEvento;
import br.com.j4business.saga.agendaevento.model.AgendaEventoForm;
import br.com.j4business.saga.agendaevento.repository.AgendaEventoRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("agendaEventoService")
public class AgendaEventoServiceImpl implements AgendaEventoService {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	jakarta.persistence.EntityManager entityManager;
	
	@Autowired
	private AgendaEventoRepository agendaEventoRepository;

	@Autowired
	private EventoProcessoService eventoProcessoService;

	@Autowired
	private EventoService eventoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AgendaEventoService.class.getName());


	@Override
	public List<AgendaEvento> getByAgendaNome(String agendaNome,Pageable pageable) {
		return agendaEventoRepository.findByAgendaNome(agendaNome,pageable);
	}

	@Override
	public List<AgendaEvento> getByEventoNome(String eventoNome,Pageable pageable) {
		return agendaEventoRepository.findByEventoNome(eventoNome,pageable);
	}

	@Override
	public List<AgendaEvento> getByAgendaNome(String agendaNome) {
		return agendaEventoRepository.findByAgendaNome(agendaNome);
	}

	@Override
	public List<AgendaEvento> getByEventoNome(String eventoNome) {
		return agendaEventoRepository.findByEventoNome(eventoNome);
	}

	@Override
	public List<AgendaEvento> getByEventoPK(long eventoPK,Pageable pageable) {
		return agendaEventoRepository.findByEventoPK(eventoPK,pageable);
	}

	@Override
	public List<AgendaEvento> getByAgendaPK(long agendaPK,Pageable pageable) {
		return agendaEventoRepository.findByAgendaPK(agendaPK,pageable);
	}

	@Override
	public List<AgendaEvento> getAgendaEventoAll(Pageable pageable) {
		return agendaEventoRepository.findAgendaEventoAll(pageable);
	}

	@Override
	public AgendaEvento getAgendaEventoByAgendaEventoPK(long agendaEventoPK) {
		return agendaEventoRepository.findByAgendaEventoPK(agendaEventoPK);
	}

	@Override
	public AgendaEvento getByAgendaAndEvento (Agenda agenda,Evento evento) {
		
		return agendaEventoRepository.findByAgendaAndEvento(agenda,evento);
	}

	@Transactional
	public AgendaEvento save(AgendaEventoForm agendaEventoForm) {

		AgendaEvento agendaEvento = new AgendaEvento();
		
		agendaEvento = this.converteAgendaEventoForm(agendaEventoForm);
		
		agendaEvento = entityManager.merge(agendaEvento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("AgendaEvento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaEvento.getAgendaEventoPK() + 
										" // Agenda Id => "+agendaEvento.getAgenda().getAgendaPK() + 
										" // Evento Id => "+agendaEvento.getEvento().getEventoPK());
		return agendaEvento;
	}

	@Transactional
	public void delete(Long agendaEventoPK) {


		Optional<AgendaEvento> agendaEventoSalvo = agendaEventoRepository.findById(agendaEventoPK);

		if (agendaEventoSalvo.isPresent()) {
			agendaEventoRepository.delete(agendaEventoSalvo.get());
		}

		

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AgendaEvento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaEventoSalvo.get().getAgendaEventoPK() + 
										" // Agenda Id => "+agendaEventoSalvo.get().getAgenda().getAgendaPK() + 
										" // Evento Id => "+agendaEventoSalvo.get().getEvento().getEventoPK()); 
    }


	@Transactional
	public void deleteByEvento(Evento evento) {
		
		List<AgendaEvento> agendaEventoList = agendaEventoRepository.findByEvento(evento);


		for (AgendaEvento agendaEvento2 : agendaEventoList) {

			agendaEventoRepository.deleteById(agendaEvento2.getAgendaEventoPK());

		}

		String username = usuarioSeguranca.getUsuarioLogado();

		agendaEventoList.forEach((AgendaEvento agendaEvento) -> {
			
			logger.info("AgendaEvento Delete2 " + "\n Usuário => " + username + 
			" // Id => "+agendaEvento.getAgendaEventoPK() + 
			" // Agenda Id => "+agendaEvento.getAgenda().getAgendaPK() + 
			" // Evento Id => "+agendaEvento.getEvento().getEventoPK());
		}
		);


	}

	@Transactional
	public AgendaEvento converteAgendaEventoForm(AgendaEventoForm agendaEventoForm) {
		
		AgendaEvento agendaEvento = new AgendaEvento();
		
		if (agendaEventoForm.getAgendaEventoPK() > 0) {

		Evento evento = eventoService.getEventoByEventoPK(Long.parseLong(agendaEventoForm.getEventoNome()));
		agendaEvento.setEvento(evento);

		Agenda agenda = agendaService.getAgendaByAgendaPK(Long.parseLong(agendaEventoForm.getAgendaNome()));
		agendaEvento.setAgenda(agenda);

		agendaEvento.setAgendaEventoMotivoOperacao(agendaEventoForm.getAgendaEventoMotivoOperacao().replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoStatus(agendaEventoForm.getAgendaEventoStatus()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoAlertaSuperiorPrimeiroEnvio(agendaEventoForm.getAgendaEventoAlertaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaSuperiorSegundoEnvio(agendaEventoForm.getAgendaEventoAlertaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaSuperiorTerceiroEnvio(agendaEventoForm.getAgendaEventoAlertaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoCobrancaSuperiorPrimeiroEnvio(agendaEventoForm.getAgendaEventoCobrancaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaSuperiorSegundoEnvio(agendaEventoForm.getAgendaEventoCobrancaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaSuperiorTerceiroEnvio(agendaEventoForm.getAgendaEventoCobrancaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoAlertaPrimeiroEnvio(agendaEventoForm.getAgendaEventoAlertaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaSegundoEnvio(agendaEventoForm.getAgendaEventoAlertaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaTerceiroEnvio(agendaEventoForm.getAgendaEventoAlertaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoCobrancaPrimeiroEnvio(agendaEventoForm.getAgendaEventoCobrancaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaSegundoEnvio(agendaEventoForm.getAgendaEventoCobrancaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaTerceiroEnvio(agendaEventoForm.getAgendaEventoCobrancaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoAlertaMensagemPrimeiroEnvio(agendaEventoForm.getAgendaEventoAlertaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaMensagemSegundoEnvio(agendaEventoForm.getAgendaEventoAlertaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaMensagemTerceiroEnvio(agendaEventoForm.getAgendaEventoAlertaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoCobrancaMensagemPrimeiroEnvio(agendaEventoForm.getAgendaEventoCobrancaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaMensagemSegundoEnvio(agendaEventoForm.getAgendaEventoCobrancaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaMensagemTerceiroEnvio(agendaEventoForm.getAgendaEventoCobrancaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoAlertaDiaPrimeiroEnvio(agendaEventoForm.getAgendaEventoAlertaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaDiaSegundoEnvio(agendaEventoForm.getAgendaEventoAlertaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoAlertaDiaTerceiroEnvio(agendaEventoForm.getAgendaEventoAlertaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaEvento.setAgendaEventoCobrancaDiaPrimeiroEnvio(agendaEventoForm.getAgendaEventoCobrancaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaDiaSegundoEnvio(agendaEventoForm.getAgendaEventoCobrancaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaEvento.setAgendaEventoCobrancaDiaTerceiroEnvio(agendaEventoForm.getAgendaEventoCobrancaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(agendaEventoForm.getAgendaEventoResponsavel()));
		agendaEvento.setColaboradorResponsavel(colaborador);
		
		}

		return agendaEvento;

	}

	@Transactional
	public AgendaEventoForm converteAgendaEvento(AgendaEvento agendaEvento) {
	
		AgendaEventoForm agendaEventoForm = new AgendaEventoForm();
		
		agendaEventoForm.setAgendaEventoPK(agendaEvento.getAgendaEventoPK());
		agendaEventoForm.setAgendaNome(agendaEvento.getAgenda().getAgendaPK()+"");
		agendaEventoForm.setEventoNome(agendaEvento.getEvento().getEventoPK()+"");

		agendaEventoForm.setAgendaEventoMotivoOperacao(agendaEvento.getAgendaEventoMotivoOperacao());
		agendaEventoForm.setAgendaEventoStatus(AtributoStatus.valueOf(agendaEvento.getAgendaEventoStatus()));

		agendaEventoForm.setAgendaEventoAlertaMensagemPrimeiroEnvio(agendaEvento.getAgendaEventoAlertaMensagemPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoAlertaMensagemSegundoEnvio(agendaEvento.getAgendaEventoAlertaMensagemSegundoEnvio());
		agendaEventoForm.setAgendaEventoAlertaMensagemTerceiroEnvio(agendaEvento.getAgendaEventoAlertaMensagemTerceiroEnvio());

		agendaEventoForm.setAgendaEventoCobrancaMensagemPrimeiroEnvio(agendaEvento.getAgendaEventoCobrancaMensagemPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoCobrancaMensagemSegundoEnvio(agendaEvento.getAgendaEventoCobrancaMensagemSegundoEnvio());
		agendaEventoForm.setAgendaEventoCobrancaMensagemTerceiroEnvio(agendaEvento.getAgendaEventoCobrancaMensagemTerceiroEnvio());

		agendaEventoForm.setAgendaEventoAlertaDiaPrimeiroEnvio(agendaEvento.getAgendaEventoAlertaDiaPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoAlertaDiaSegundoEnvio(agendaEvento.getAgendaEventoAlertaDiaSegundoEnvio());
		agendaEventoForm.setAgendaEventoAlertaDiaTerceiroEnvio(agendaEvento.getAgendaEventoAlertaDiaTerceiroEnvio());

		agendaEventoForm.setAgendaEventoCobrancaDiaPrimeiroEnvio(agendaEvento.getAgendaEventoCobrancaDiaPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoCobrancaDiaSegundoEnvio(agendaEvento.getAgendaEventoCobrancaDiaSegundoEnvio());
		agendaEventoForm.setAgendaEventoCobrancaDiaTerceiroEnvio(agendaEvento.getAgendaEventoCobrancaDiaTerceiroEnvio());

		agendaEventoForm.setAgendaEventoAlertaPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSegundoEnvio()));
		agendaEventoForm.setAgendaEventoAlertaTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoAlertaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorSegundoEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoCobrancaPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSegundoEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoCobrancaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorSegundoEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoResponsavel(agendaEvento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return agendaEventoForm;
	}
	
	@Transactional
	public AgendaEventoForm converteAgendaEventoView(AgendaEvento agendaEvento) {
	
		AgendaEventoForm agendaEventoForm = new AgendaEventoForm();
		
		agendaEventoForm.setAgendaEventoPK(agendaEvento.getAgendaEventoPK());
		agendaEventoForm.setAgendaNome(agendaEvento.getAgenda().getAgendaNome());
		agendaEventoForm.setEventoNome(agendaEvento.getEvento().getEventoNome());

		agendaEventoForm.setAgendaEventoMotivoOperacao(agendaEvento.getAgendaEventoMotivoOperacao());
		agendaEventoForm.setAgendaEventoStatus(AtributoStatus.valueOf(agendaEvento.getAgendaEventoStatus()));

		agendaEventoForm.setAgendaEventoResponsavel(agendaEvento.getColaboradorResponsavel().getPessoaNome());

		agendaEventoForm.setAgendaEventoAlertaMensagemPrimeiroEnvio(agendaEvento.getAgendaEventoAlertaMensagemPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoAlertaMensagemSegundoEnvio(agendaEvento.getAgendaEventoAlertaMensagemSegundoEnvio());
		agendaEventoForm.setAgendaEventoAlertaMensagemTerceiroEnvio(agendaEvento.getAgendaEventoAlertaMensagemTerceiroEnvio());

		agendaEventoForm.setAgendaEventoCobrancaMensagemPrimeiroEnvio(agendaEvento.getAgendaEventoCobrancaMensagemPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoCobrancaMensagemSegundoEnvio(agendaEvento.getAgendaEventoCobrancaMensagemSegundoEnvio());
		agendaEventoForm.setAgendaEventoCobrancaMensagemTerceiroEnvio(agendaEvento.getAgendaEventoCobrancaMensagemTerceiroEnvio());

		agendaEventoForm.setAgendaEventoAlertaDiaPrimeiroEnvio(agendaEvento.getAgendaEventoAlertaDiaPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoAlertaDiaSegundoEnvio(agendaEvento.getAgendaEventoAlertaDiaSegundoEnvio());
		agendaEventoForm.setAgendaEventoAlertaDiaTerceiroEnvio(agendaEvento.getAgendaEventoAlertaDiaTerceiroEnvio());

		agendaEventoForm.setAgendaEventoCobrancaDiaPrimeiroEnvio(agendaEvento.getAgendaEventoCobrancaDiaPrimeiroEnvio());
		agendaEventoForm.setAgendaEventoCobrancaDiaSegundoEnvio(agendaEvento.getAgendaEventoCobrancaDiaSegundoEnvio());
		agendaEventoForm.setAgendaEventoCobrancaDiaTerceiroEnvio(agendaEvento.getAgendaEventoCobrancaDiaTerceiroEnvio());

		agendaEventoForm.setAgendaEventoAlertaPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSegundoEnvio()));
		agendaEventoForm.setAgendaEventoAlertaTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoAlertaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorSegundoEnvio()));
		agendaEventoForm.setAgendaEventoAlertaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoAlertaSuperiorTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoCobrancaPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSegundoEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaTerceiroEnvio()));

		agendaEventoForm.setAgendaEventoCobrancaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorPrimeiroEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorSegundoEnvio()));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf(agendaEvento.getAgendaEventoCobrancaSuperiorTerceiroEnvio()));

		
	return agendaEventoForm;
	}
	

	@Transactional
	public AgendaEventoForm agendaEventoParametros(AgendaEventoForm agendaEventoForm) {
	
		
		agendaEventoForm.setAgendaEventoStatus(AtributoStatus.valueOf("ATIVO"));
		agendaEventoForm.setAgendaEventoAlertaPrimeiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaSegundoEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaTerceiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaPrimeiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaSegundoEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaTerceiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorPrimeiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorSegundoEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoCobrancaSuperiorTerceiroEnvio(AgendaEventoEnvio.valueOf("SIM"));
		agendaEventoForm.setAgendaEventoAlertaDiaPrimeiroEnvio("30");
		agendaEventoForm.setAgendaEventoAlertaDiaSegundoEnvio("15");
		agendaEventoForm.setAgendaEventoAlertaDiaTerceiroEnvio("7");
		agendaEventoForm.setAgendaEventoCobrancaDiaPrimeiroEnvio("10");
		agendaEventoForm.setAgendaEventoCobrancaDiaSegundoEnvio("20");
		agendaEventoForm.setAgendaEventoCobrancaDiaTerceiroEnvio("30");

		
	return agendaEventoForm;
	}
	
	@Transactional
	public AgendaEvento create(AgendaEventoForm agendaEventoForm) {
		
		AgendaEvento agendaEvento = new AgendaEvento();
		
		agendaEvento = this.converteAgendaEventoForm(agendaEventoForm);
		
		agendaEvento = entityManager.merge(agendaEvento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Evento Create " + "\n Usuário => " + username + 
				" // Id => "+agendaEvento.getAgendaEventoPK() + 
				" // Agenda Id => "+agendaEvento.getAgenda().getAgendaPK() + 
				" // Evento Id => "+agendaEvento.getEvento().getEventoPK()); 
		
		return agendaEvento;
	}

	@Override
	public List<Mensagem> getAgendaEventoMensagemAll() {
		int MILLIS_IN_DAY = 86400000;
	
	List<Mensagem> listMensagem = new ArrayList<Mensagem>();
		
		List<AgendaEvento> agendaEventoList = agendaEventoRepository.findAgendaEventoAll();
		
		agendaEventoList.forEach((AgendaEvento agendaEvento) -> { 

			Evento evento = eventoService.getEventoByEventoPK(agendaEvento.getEvento().getEventoPK());
	
			Calendar dataAtual = Calendar.getInstance();			
	
			Calendar eventoDataInicio = Calendar.getInstance();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				eventoDataInicio.setTime(sdf.parse(evento.getEventoDataInicio()));
			} catch (ParseException e) {
				e.printStackTrace();
			}// all done
			
		
			if (dataAtual.before(eventoDataInicio)) {
				
				int numeroDiasParaInicio = (int) ((eventoDataInicio.getTimeInMillis() - dataAtual.getTimeInMillis()) / MILLIS_IN_DAY);
				
				if (Integer.parseInt(agendaEvento.getAgendaEventoAlertaDiaTerceiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaEvento.getAgendaEventoAlertaTerceiroEnvio().equalsIgnoreCase("SIM")) {
						
						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Alerta: Terceiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
							
						});
						
					}
					if (agendaEvento.getAgendaEventoAlertaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Alerta: Terceiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaEvento.getAgendaEventoAlertaDiaSegundoEnvio()) >= numeroDiasParaInicio) {
					if (agendaEvento.getAgendaEventoAlertaSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Alerta: Segundo Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaEvento.getAgendaEventoAlertaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Alerta: Segundo Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaEvento.getAgendaEventoAlertaDiaPrimeiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaEvento.getAgendaEventoAlertaPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Alerta: Primeiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaEvento.getAgendaEventoAlertaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Alerta: Primeiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoAlertaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				}
			} else {
				int numeroDiasAposInicio = (int) ((dataAtual.getTimeInMillis() - eventoDataInicio.getTimeInMillis()) / MILLIS_IN_DAY);
			
				if (Integer.parseInt(agendaEvento.getAgendaEventoCobrancaDiaTerceiroEnvio()) <= numeroDiasAposInicio) {

					if (agendaEvento.getAgendaEventoCobrancaTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Cobrança: Terceiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaEvento.getAgendaEventoCobrancaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Cobrança: Terceiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaEvento.getAgendaEventoCobrancaDiaSegundoEnvio()) <= numeroDiasAposInicio) {
					if (agendaEvento.getAgendaEventoCobrancaSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Cobrança: Segundo Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaEvento.getAgendaEventoCobrancaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Cobrança: Segundo Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaEvento.getAgendaEventoCobrancaDiaPrimeiroEnvio()) <= numeroDiasAposInicio) {

					if (agendaEvento.getAgendaEventoCobrancaPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorGestor().getEmailNome())
									, "Cobrança: Primeiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaEvento.getAgendaEventoCobrancaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<EventoProcesso> eventoProcessoList = eventoProcessoService.getByEventoPK(evento.getEventoPK());
						
						eventoProcessoList.forEach((EventoProcesso eventoProcesso) -> { 
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(eventoProcesso.getProcesso().getColaboradorDono().getEmailNome())
									, "Cobrança: Primeiro Aviso", evento.getEventoNome() + " : " + agendaEvento.getAgendaEventoCobrancaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				}
			} 
		});
		
		return listMensagem;
	}
}
