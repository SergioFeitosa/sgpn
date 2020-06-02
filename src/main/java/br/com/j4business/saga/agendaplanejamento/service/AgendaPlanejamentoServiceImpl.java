package br.com.j4business.saga.agendaplanejamento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.email.Mensagem;
import br.com.j4business.saga.planejamento.model.Planejamento;
import br.com.j4business.saga.planejamento.service.PlanejamentoService;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.agendaplanejamento.enumeration.AgendaPlanejamentoEnvio;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamento;
import br.com.j4business.saga.agendaplanejamento.model.AgendaPlanejamentoForm;
import br.com.j4business.saga.agendaplanejamento.repository.AgendaPlanejamentoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("agendaPlanejamentoService")
public class AgendaPlanejamentoServiceImpl implements AgendaPlanejamentoService {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AgendaPlanejamentoRepository agendaPlanejamentoRepository;

	@Autowired
	private PlanejamentoService planejamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AgendaPlanejamentoService.class.getName());


	@Override
	public List<AgendaPlanejamento> getByAgendaNome(String agendaNome,Pageable pageable) {
		return agendaPlanejamentoRepository.findByAgendaNome(agendaNome,pageable);
	}

	@Override
	public List<AgendaPlanejamento> getByPlanejamentoNome(String planejamentoNome,Pageable pageable) {
		return agendaPlanejamentoRepository.findByPlanejamentoNome(planejamentoNome,pageable);
	}

	@Override
	public List<AgendaPlanejamento> getByAgendaNome(String agendaNome) {
		return agendaPlanejamentoRepository.findByAgendaNome(agendaNome);
	}

	@Override
	public List<AgendaPlanejamento> getByPlanejamentoNome(String planejamentoNome) {
		return agendaPlanejamentoRepository.findByPlanejamentoNome(planejamentoNome);
	}

	@Override
	public List<AgendaPlanejamento> getByPlanejamentoPK(long planejamentoPK,Pageable pageable) {
		return agendaPlanejamentoRepository.findByPlanejamentoPK(planejamentoPK,pageable);
	}

	@Override
	public List<AgendaPlanejamento> getByAgendaPK(long agendaPK,Pageable pageable) {
		return agendaPlanejamentoRepository.findByAgendaPK(agendaPK,pageable);
	}

	@Override
	public List<AgendaPlanejamento> getAgendaPlanejamentoAll(Pageable pageable) {
		return agendaPlanejamentoRepository.findAgendaPlanejamentoAll(pageable);
	}

	@Override
	public AgendaPlanejamento getAgendaPlanejamentoByAgendaPlanejamentoPK(long agendaPlanejamentoPK) {
		return agendaPlanejamentoRepository.findOne(agendaPlanejamentoPK);
	}

	@Override
	public AgendaPlanejamento getByAgendaAndPlanejamento (Agenda agenda,Planejamento planejamento) {
		
		return agendaPlanejamentoRepository.findByAgendaAndPlanejamento(agenda,planejamento);
	}

	@Transactional
	public AgendaPlanejamento save(AgendaPlanejamentoForm agendaPlanejamentoForm) {

		AgendaPlanejamento agendaPlanejamento = new AgendaPlanejamento();
		
		agendaPlanejamento = this.converteAgendaPlanejamentoForm(agendaPlanejamentoForm);
		
		agendaPlanejamento = entityManager.merge(agendaPlanejamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("AgendaPlanejamento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaPlanejamento.getAgendaPlanejamentoPK() + 
										" // Agenda Id => "+agendaPlanejamento.getAgenda().getAgendaPK() + 
										" // Planejamento Id => "+agendaPlanejamento.getPlanejamento().getPlanejamentoPK());
		return agendaPlanejamento;
	}

	@Transactional
	public void delete(Long agendaPlanejamentoPK) {

		AgendaPlanejamento agendaPlanejamentoTemp = this.getAgendaPlanejamentoByAgendaPlanejamentoPK(agendaPlanejamentoPK);

		agendaPlanejamentoRepository.delete(agendaPlanejamentoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AgendaPlanejamento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaPlanejamentoTemp.getAgendaPlanejamentoPK() + 
										" // Agenda Id => "+agendaPlanejamentoTemp.getAgenda().getAgendaPK() + 
										" // Planejamento Id => "+agendaPlanejamentoTemp.getPlanejamento().getPlanejamentoPK()); 
    }

	@Transactional
	public void deleteByPlanejamento(Planejamento planejamento) {
		
		List<AgendaPlanejamento> agendaPlanejamentoList = agendaPlanejamentoRepository.findByPlanejamento(planejamento);

		agendaPlanejamentoRepository.delete(agendaPlanejamentoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		agendaPlanejamentoList.forEach((AgendaPlanejamento agendaPlanejamento) -> 
			
			logger.info("AgendaPlanejamento Delete2 " + "\n Usuário => " + username + 
											" // Id => "+agendaPlanejamento.getAgendaPlanejamentoPK() + 
											" // Agenda Id => "+agendaPlanejamento.getAgenda().getAgendaPK() + 
											" // Planejamento Id => "+agendaPlanejamento.getPlanejamento().getPlanejamentoPK()) 

		);
		
    }

	@Transactional
	public AgendaPlanejamento converteAgendaPlanejamentoForm(AgendaPlanejamentoForm agendaPlanejamentoForm) {
		
		AgendaPlanejamento agendaPlanejamento = new AgendaPlanejamento();
		
		if (agendaPlanejamentoForm.getAgendaPlanejamentoPK() > 0) {
			agendaPlanejamento = this.getAgendaPlanejamentoByAgendaPlanejamentoPK(agendaPlanejamentoForm.getAgendaPlanejamentoPK());
		}
		
		Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(Long.parseLong(agendaPlanejamentoForm.getPlanejamentoNome()));
		agendaPlanejamento.setPlanejamento(planejamento);

		Agenda agenda = agendaService.getAgendaByAgendaPK(Long.parseLong(agendaPlanejamentoForm.getAgendaNome()));
		agendaPlanejamento.setAgenda(agenda);

		agendaPlanejamento.setAgendaPlanejamentoMotivoOperacao(agendaPlanejamentoForm.getAgendaPlanejamentoMotivoOperacao().replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoStatus(agendaPlanejamentoForm.getAgendaPlanejamentoStatus()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoAlertaPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoCobrancaPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoAlertaMensagemPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaMensagemSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaMensagemTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaMensagemSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaMensagemTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoAlertaDiaPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaDiaSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoAlertaDiaTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoAlertaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaPlanejamento.setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaDiaSegundoEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaPlanejamento.setAgendaPlanejamentoCobrancaDiaTerceiroEnvio(agendaPlanejamentoForm.getAgendaPlanejamentoCobrancaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(agendaPlanejamentoForm.getAgendaPlanejamentoResponsavel()));
		agendaPlanejamento.setColaboradorResponsavel(colaborador);
		
		return agendaPlanejamento;
	}

	@Transactional
	public AgendaPlanejamentoForm converteAgendaPlanejamento(AgendaPlanejamento agendaPlanejamento) {
	
		AgendaPlanejamentoForm agendaPlanejamentoForm = new AgendaPlanejamentoForm();
		
		agendaPlanejamentoForm.setAgendaPlanejamentoPK(agendaPlanejamento.getAgendaPlanejamentoPK());
		agendaPlanejamentoForm.setAgendaNome(agendaPlanejamento.getAgenda().getAgendaPK()+"");
		agendaPlanejamentoForm.setPlanejamentoNome(agendaPlanejamento.getPlanejamento().getPlanejamentoPK()+"");

		agendaPlanejamentoForm.setAgendaPlanejamentoMotivoOperacao(agendaPlanejamento.getAgendaPlanejamentoMotivoOperacao());
		agendaPlanejamentoForm.setAgendaPlanejamentoStatus(AtributoStatus.valueOf(agendaPlanejamento.getAgendaPlanejamentoStatus()));

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoResponsavel(agendaPlanejamento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return agendaPlanejamentoForm;
	}
	
	@Transactional
	public AgendaPlanejamentoForm converteAgendaPlanejamentoView(AgendaPlanejamento agendaPlanejamento) {
	
		AgendaPlanejamentoForm agendaPlanejamentoForm = new AgendaPlanejamentoForm();
		
		agendaPlanejamentoForm.setAgendaPlanejamentoPK(agendaPlanejamento.getAgendaPlanejamentoPK());
		agendaPlanejamentoForm.setAgendaNome(agendaPlanejamento.getAgenda().getAgendaNome());
		agendaPlanejamentoForm.setPlanejamentoNome(agendaPlanejamento.getPlanejamento().getPlanejamentoNome());

		agendaPlanejamentoForm.setAgendaPlanejamentoMotivoOperacao(agendaPlanejamento.getAgendaPlanejamentoMotivoOperacao());
		agendaPlanejamentoForm.setAgendaPlanejamentoStatus(AtributoStatus.valueOf(agendaPlanejamento.getAgendaPlanejamentoStatus()));

		agendaPlanejamentoForm.setAgendaPlanejamentoResponsavel(agendaPlanejamento.getColaboradorResponsavel().getPessoaNome());

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaMensagemTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaMensagemTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaSegundoEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaSegundoEnvio());
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaTerceiroEnvio(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaTerceiroEnvio());

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaTerceiroEnvio()));

		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio()));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf(agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio()));

		
	return agendaPlanejamentoForm;
	}
	

	@Transactional
	public AgendaPlanejamentoForm agendaPlanejamentoParametros(AgendaPlanejamentoForm agendaPlanejamentoForm) {
	
		
		agendaPlanejamentoForm.setAgendaPlanejamentoStatus(AtributoStatus.valueOf("ATIVO"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSegundoEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorSegundoEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio(AgendaPlanejamentoEnvio.valueOf("SIM"));
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaPrimeiroEnvio("30");
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaSegundoEnvio("15");
		agendaPlanejamentoForm.setAgendaPlanejamentoAlertaDiaTerceiroEnvio("7");
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaPrimeiroEnvio("10");
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaSegundoEnvio("20");
		agendaPlanejamentoForm.setAgendaPlanejamentoCobrancaDiaTerceiroEnvio("30");

		
	return agendaPlanejamentoForm;
	}
	
	@Transactional
	public AgendaPlanejamento create(AgendaPlanejamentoForm agendaPlanejamentoForm) {
		
		AgendaPlanejamento agendaPlanejamento = new AgendaPlanejamento();
		
		agendaPlanejamento = this.converteAgendaPlanejamentoForm(agendaPlanejamentoForm);
		
		agendaPlanejamento = entityManager.merge(agendaPlanejamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Planejamento Create " + "\n Usuário => " + username + 
				" // Id => "+agendaPlanejamento.getAgendaPlanejamentoPK() + 
				" // Agenda Id => "+agendaPlanejamento.getAgenda().getAgendaPK() + 
				" // Planejamento Id => "+agendaPlanejamento.getPlanejamento().getPlanejamentoPK()); 
		
		return agendaPlanejamento;
	}

	@Override
	public List<Mensagem> getAgendaPlanejamentoMensagemAll() {
		int MILLIS_IN_DAY = 86400000;
	
	List<Mensagem> listMensagem = new ArrayList<Mensagem>();
		
		List<AgendaPlanejamento> agendaPlanejamentoList = agendaPlanejamentoRepository.findAgendaPlanejamentoAll();
		
		agendaPlanejamentoList.forEach((AgendaPlanejamento agendaPlanejamento) -> { 
			
			Planejamento planejamento = planejamentoService.getPlanejamentoByPlanejamentoPK(agendaPlanejamento.getPlanejamento().getPlanejamentoPK());
	
			Calendar dataAtual = Calendar.getInstance();			
	
			Calendar planejamentoDataInicio = Calendar.getInstance();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				planejamentoDataInicio.setTime(sdf.parse(planejamento.getPlanejamentoDataPrevistaInicio()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// all done
			
		
			if (dataAtual.before(planejamentoDataInicio)) {
				
				int numeroDiasParaInicio = (int) ((planejamentoDataInicio.getTimeInMillis() - dataAtual.getTimeInMillis()) / MILLIS_IN_DAY);
				
				if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaTerceiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaTerceiroEnvio().equalsIgnoreCase("SIM")) {
						
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Terceiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
						
					}
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Alerta: Terceiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaSegundoEnvio()) >= numeroDiasParaInicio) {
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Segundo Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Alerta: Segundo Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoAlertaDiaPrimeiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Primeiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaPlanejamento.getAgendaPlanejamentoAlertaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Alerta: Primeiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoAlertaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
				}
			} else {
				int numeroDiasAposInicio = (int) ((dataAtual.getTimeInMillis() - planejamentoDataInicio.getTimeInMillis()) / MILLIS_IN_DAY);
			
				if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaTerceiroEnvio()) <= numeroDiasAposInicio) {
	
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Terceiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Terceiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaSegundoEnvio()) <= numeroDiasAposInicio) {
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Segundo Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Segundo Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaPlanejamento.getAgendaPlanejamentoCobrancaDiaPrimeiroEnvio()) <= numeroDiasAposInicio) {
	
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Primeiro Aviso",  planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaPlanejamento.getAgendaPlanejamentoCobrancaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(planejamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Primeiro Aviso", planejamento.getPlanejamentoNome() + " : " + agendaPlanejamento.getAgendaPlanejamentoCobrancaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
				}
			} 
		});
		
		return listMensagem;
	}


}
