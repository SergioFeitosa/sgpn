package br.com.j4business.saga.agendatreinamento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamento;
import br.com.j4business.saga.colaboradortreinamento.service.ColaboradorTreinamentoService;
import br.com.j4business.saga.email.Mensagem;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamento;
import br.com.j4business.saga.agendatreinamento.enumeration.AgendaTreinamentoEnvio;
import br.com.j4business.saga.agendatreinamento.model.AgendaTreinamentoForm;
import br.com.j4business.saga.agendatreinamento.repository.AgendaTreinamentoRepository;

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

@Service("agendaTreinamentoService")
public class AgendaTreinamentoServiceImpl implements AgendaTreinamentoService {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ColaboradorTreinamentoService colaboradorTreinamentoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AgendaTreinamentoRepository agendaTreinamentoRepository;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AgendaTreinamentoService.class.getName());


	@Override
	public List<AgendaTreinamento> getByAgendaNome(String agendaNome,Pageable pageable) {
		return agendaTreinamentoRepository.findByAgendaNome(agendaNome,pageable);
	}

	@Override
	public List<AgendaTreinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return agendaTreinamentoRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<AgendaTreinamento> getByAgendaNome(String agendaNome) {
		return agendaTreinamentoRepository.findByAgendaNome(agendaNome);
	}

	@Override
	public List<AgendaTreinamento> getByTreinamentoNome(String treinamentoNome) {
		return agendaTreinamentoRepository.findByTreinamentoNome(treinamentoNome);
	}

	@Override
	public List<AgendaTreinamento> getByTreinamentoPK(long treinamentoPK,Pageable pageable) {
		return agendaTreinamentoRepository.findByTreinamentoPK(treinamentoPK,pageable);
	}

	@Override
	public List<AgendaTreinamento> getByTreinamentoPK(long treinamentoPK) {
		return agendaTreinamentoRepository.findByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<AgendaTreinamento> getByAgendaPK(long agendaPK,Pageable pageable) {
		return agendaTreinamentoRepository.findByAgendaPK(agendaPK,pageable);
	}

	@Override
	public List<AgendaTreinamento> getAgendaTreinamentoAll(Pageable pageable) {
		return agendaTreinamentoRepository.findAgendaTreinamentoAll(pageable);
	}

	@Override
	public AgendaTreinamento getAgendaTreinamentoByAgendaTreinamentoPK(long agendaTreinamentoPK) {
		return agendaTreinamentoRepository.findOne(agendaTreinamentoPK);
	}

	@Override
	public AgendaTreinamento getByAgendaAndTreinamento (Agenda agenda,Treinamento treinamento) {
		
		return agendaTreinamentoRepository.findByAgendaAndTreinamento(agenda,treinamento);
	}

	@Transactional
	public AgendaTreinamento save(AgendaTreinamentoForm agendaTreinamentoForm) {

		AgendaTreinamento agendaTreinamento = new AgendaTreinamento();
		
		agendaTreinamento = this.converteAgendaTreinamentoForm(agendaTreinamentoForm);
		
		agendaTreinamento = entityManager.merge(agendaTreinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("AgendaTreinamento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaTreinamento.getAgendaTreinamentoPK() + 
										" // Agenda Id => "+agendaTreinamento.getAgenda().getAgendaPK() + 
										" // Treinamento Id => "+agendaTreinamento.getTreinamento().getTreinamentoPK());
		return agendaTreinamento;
	}

	@Transactional
	public void delete(Long agendaTreinamentoPK) {

		AgendaTreinamento agendaTreinamentoTemp = this.getAgendaTreinamentoByAgendaTreinamentoPK(agendaTreinamentoPK);

		agendaTreinamentoRepository.delete(agendaTreinamentoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AgendaTreinamento Save " + "\n Usuário => " + username + 
										" // Id => "+agendaTreinamentoTemp.getAgendaTreinamentoPK() + 
										" // Agenda Id => "+agendaTreinamentoTemp.getAgenda().getAgendaPK() + 
										" // Treinamento Id => "+agendaTreinamentoTemp.getTreinamento().getTreinamentoPK()); 
    }

	@Transactional
	public void deleteByTreinamento(Treinamento treinamento) {
		
		List<AgendaTreinamento> agendaTreinamentoList = agendaTreinamentoRepository.findByTreinamento(treinamento);

		agendaTreinamentoRepository.delete(agendaTreinamentoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		agendaTreinamentoList.forEach((AgendaTreinamento agendaTreinamento) ->  
			
			logger.info("AgendaTreinamento Delete2 " + "\n Usuário => " + username + 
											" // Id => "+agendaTreinamento.getAgendaTreinamentoPK() + 
											" // Agenda Id => "+agendaTreinamento.getAgenda().getAgendaPK() + 
											" // Treinamento Id => "+agendaTreinamento.getTreinamento().getTreinamentoPK())

		);
    }

	@Transactional
	public AgendaTreinamento converteAgendaTreinamentoForm(AgendaTreinamentoForm agendaTreinamentoForm) {
		
		AgendaTreinamento agendaTreinamento = new AgendaTreinamento();
		
		if (agendaTreinamentoForm.getAgendaTreinamentoPK() > 0) {
			agendaTreinamento = this.getAgendaTreinamentoByAgendaTreinamentoPK(agendaTreinamentoForm.getAgendaTreinamentoPK());
		}
		
		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(Long.parseLong(agendaTreinamentoForm.getTreinamentoNome()));
		agendaTreinamento.setTreinamento(treinamento);

		Agenda agenda = agendaService.getAgendaByAgendaPK(Long.parseLong(agendaTreinamentoForm.getAgendaNome()));
		agendaTreinamento.setAgenda(agenda);

		agendaTreinamento.setAgendaTreinamentoMotivoOperacao(agendaTreinamentoForm.getAgendaTreinamentoMotivoOperacao().replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoStatus(agendaTreinamentoForm.getAgendaTreinamentoStatus()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaSuperiorSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoAlertaPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoCobrancaPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoAlertaMensagemPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaMensagemSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaMensagemTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoCobrancaMensagemPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaMensagemSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaMensagemTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoAlertaDiaPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaDiaSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoAlertaDiaTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoAlertaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaTreinamento.setAgendaTreinamentoCobrancaDiaPrimeiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaDiaSegundoEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaTreinamento.setAgendaTreinamentoCobrancaDiaTerceiroEnvio(agendaTreinamentoForm.getAgendaTreinamentoCobrancaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(agendaTreinamentoForm.getAgendaTreinamentoResponsavel()));
		agendaTreinamento.setColaboradorResponsavel(colaborador);
		
		return agendaTreinamento;
	}

	@Transactional
	public AgendaTreinamentoForm converteAgendaTreinamento(AgendaTreinamento agendaTreinamento) {
	
		AgendaTreinamentoForm agendaTreinamentoForm = new AgendaTreinamentoForm();
		
		agendaTreinamentoForm.setAgendaTreinamentoPK(agendaTreinamento.getAgendaTreinamentoPK());
		agendaTreinamentoForm.setAgendaNome(agendaTreinamento.getAgenda().getAgendaPK()+"");
		agendaTreinamentoForm.setTreinamentoNome(agendaTreinamento.getTreinamento().getTreinamentoPK()+"");

		agendaTreinamentoForm.setAgendaTreinamentoMotivoOperacao(agendaTreinamento.getAgendaTreinamentoMotivoOperacao());
		agendaTreinamentoForm.setAgendaTreinamentoStatus(AtributoStatus.valueOf(agendaTreinamento.getAgendaTreinamentoStatus()));

		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemSegundoEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemSegundoEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaSegundoEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaSegundoEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoAlertaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoResponsavel(agendaTreinamento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return agendaTreinamentoForm;
	}
	
	@Transactional
	public AgendaTreinamentoForm converteAgendaTreinamentoView(AgendaTreinamento agendaTreinamento) {
	
		AgendaTreinamentoForm agendaTreinamentoForm = new AgendaTreinamentoForm();
		
		agendaTreinamentoForm.setAgendaTreinamentoPK(agendaTreinamento.getAgendaTreinamentoPK());
		agendaTreinamentoForm.setAgendaNome(agendaTreinamento.getAgenda().getAgendaNome());
		agendaTreinamentoForm.setTreinamentoNome(agendaTreinamento.getTreinamento().getTreinamentoNome());

		agendaTreinamentoForm.setAgendaTreinamentoMotivoOperacao(agendaTreinamento.getAgendaTreinamentoMotivoOperacao());
		agendaTreinamentoForm.setAgendaTreinamentoStatus(AtributoStatus.valueOf(agendaTreinamento.getAgendaTreinamentoStatus()));

		agendaTreinamentoForm.setAgendaTreinamentoResponsavel(agendaTreinamento.getColaboradorResponsavel().getPessoaNome());

		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemSegundoEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaMensagemTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaMensagemTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemSegundoEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaMensagemTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaSegundoEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoAlertaDiaTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaPrimeiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaPrimeiroEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaSegundoEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaSegundoEnvio());
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaTerceiroEnvio(agendaTreinamento.getAgendaTreinamentoCobrancaDiaTerceiroEnvio());

		agendaTreinamentoForm.setAgendaTreinamentoAlertaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoAlertaSuperiorTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaTerceiroEnvio()));

		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorSegundoEnvio()));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf(agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio()));

		
	return agendaTreinamentoForm;
	}
	

	@Transactional
	public AgendaTreinamentoForm agendaTreinamentoParametros(AgendaTreinamentoForm agendaTreinamentoForm) {
	
		
		agendaTreinamentoForm.setAgendaTreinamentoStatus(AtributoStatus.valueOf("ATIVO"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSegundoEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSegundoEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaTerceiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorSegundoEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaSuperiorTerceiroEnvio(AgendaTreinamentoEnvio.valueOf("SIM"));
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaPrimeiroEnvio("30");
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaSegundoEnvio("15");
		agendaTreinamentoForm.setAgendaTreinamentoAlertaDiaTerceiroEnvio("7");
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaPrimeiroEnvio("10");
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaSegundoEnvio("20");
		agendaTreinamentoForm.setAgendaTreinamentoCobrancaDiaTerceiroEnvio("30");

		
	return agendaTreinamentoForm;
	}
	
	@Transactional
	public AgendaTreinamento create(AgendaTreinamentoForm agendaTreinamentoForm) {
		
		AgendaTreinamento agendaTreinamento = new AgendaTreinamento();
		
		agendaTreinamento = this.converteAgendaTreinamentoForm(agendaTreinamentoForm);
		
		agendaTreinamento = entityManager.merge(agendaTreinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Treinamento Create " + "\n Usuário => " + username + 
				" // Id => "+agendaTreinamento.getAgendaTreinamentoPK() + 
				" // Agenda Id => "+agendaTreinamento.getAgenda().getAgendaPK() + 
				" // Treinamento Id => "+agendaTreinamento.getTreinamento().getTreinamentoPK()); 
		
		return agendaTreinamento;
	}

	@Override
	public List<Mensagem> getAgendaTreinamentoMensagemAll() {
		int MILLIS_IN_DAY = 86400000;
	
	List<Mensagem> listMensagem = new ArrayList<Mensagem>();
		
		List<AgendaTreinamento> agendaTreinamentoList = agendaTreinamentoRepository.findAgendaTreinamentoAll();
		
		agendaTreinamentoList.forEach((AgendaTreinamento agendaTreinamento) -> { 

			Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(agendaTreinamento.getTreinamento().getTreinamentoPK());
	
			Calendar dataAtual = Calendar.getInstance();			
	
			Calendar treinamentoDataInicio = Calendar.getInstance();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				treinamentoDataInicio.setTime(sdf.parse(treinamento.getTreinamentoDataPrevistaInicio()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// all done
			
		
			if (dataAtual.before(treinamentoDataInicio)) {
				
				int numeroDiasParaInicio = (int) ((treinamentoDataInicio.getTimeInMillis() - dataAtual.getTimeInMillis()) / MILLIS_IN_DAY);
				
				if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaTerceiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaTreinamento.getAgendaTreinamentoAlertaTerceiroEnvio().equalsIgnoreCase("SIM")) {
						
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
						
					}
					if (agendaTreinamento.getAgendaTreinamentoAlertaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Alerta: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaSegundoEnvio()) >= numeroDiasParaInicio) {
					if (agendaTreinamento.getAgendaTreinamentoAlertaSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaTreinamento.getAgendaTreinamentoAlertaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Alerta: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaPrimeiroEnvio()) >= numeroDiasParaInicio) {
					if (agendaTreinamento.getAgendaTreinamentoAlertaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Alerta: Primeiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaTreinamento.getAgendaTreinamentoAlertaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Alerta: Primeiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
				}
			} else {
				int numeroDiasAposInicio = (int) ((dataAtual.getTimeInMillis() - treinamentoDataInicio.getTimeInMillis()) / MILLIS_IN_DAY);
			
				if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaTerceiroEnvio()) <= numeroDiasAposInicio) {
	
					if (agendaTreinamento.getAgendaTreinamentoCobrancaTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaSegundoEnvio()) <= numeroDiasAposInicio) {
					if (agendaTreinamento.getAgendaTreinamentoCobrancaSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemSegundoEnvio());
						listMensagem.add(mensagem);
					}
				} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaPrimeiroEnvio()) <= numeroDiasAposInicio) {
	
					if (agendaTreinamento.getAgendaTreinamentoCobrancaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorGestor().getEmailNome())
								, "Cobrança: Primeiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
					if (agendaTreinamento.getAgendaTreinamentoCobrancaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {
	
						Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
								Arrays.asList(treinamento.getColaboradorDono().getEmailNome())
								, "Cobrança: Primeiro Aviso", treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio());
						listMensagem.add(mensagem);
					}
				}
			} 
		});
		
		return listMensagem;
	}

	@Override
	public List<Mensagem> getAgendaTreinamentoMensagemColaboradorAll() {
		int MILLIS_IN_DAY = 86400000;
	
	List<Mensagem> listMensagem = new ArrayList<Mensagem>();
		
		List<AgendaTreinamento> agendaTreinamentoList = agendaTreinamentoRepository.findAgendaTreinamentoAll();
		
		agendaTreinamentoList.forEach((AgendaTreinamento agendaTreinamento) -> { 

			Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(agendaTreinamento.getTreinamento().getTreinamentoPK());
	
			Calendar dataAtual = Calendar.getInstance();			
	
			Calendar treinamentoDataInicio = Calendar.getInstance();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				treinamentoDataInicio.setTime(sdf.parse(treinamento.getTreinamentoDataPrevistaInicio()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// all done
			
			List<ColaboradorTreinamento> colaboradorTreinamentoList = colaboradorTreinamentoService.getByTreinamentoPK(treinamento.getTreinamentoPK());
		
			colaboradorTreinamentoList.forEach((ColaboradorTreinamento colaboradorTreinamento) -> { 
				
				
				if (dataAtual.before(treinamentoDataInicio)) {
				
					int numeroDiasParaInicio = (int) ((treinamentoDataInicio.getTimeInMillis() - dataAtual.getTimeInMillis()) / MILLIS_IN_DAY);
					
					if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaTerceiroEnvio()) >= numeroDiasParaInicio) {
						if (agendaTreinamento.getAgendaTreinamentoAlertaTerceiroEnvio().equalsIgnoreCase("SIM")) {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Alerta: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
							
						}
					} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaSegundoEnvio()) >= numeroDiasParaInicio) {
						if (agendaTreinamento.getAgendaTreinamentoAlertaSegundoEnvio().equalsIgnoreCase("SIM")) {
		
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Alerta: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						}
					} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoAlertaDiaPrimeiroEnvio()) >= numeroDiasParaInicio) {
						if (agendaTreinamento.getAgendaTreinamentoAlertaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
		
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Alerta: Primeiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoAlertaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						}
					}
			} else {
				
					int numeroDiasAposInicio = (int) ((dataAtual.getTimeInMillis() - treinamentoDataInicio.getTimeInMillis()) / MILLIS_IN_DAY);
				
					if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaTerceiroEnvio()) <= numeroDiasAposInicio) {
		
						if (agendaTreinamento.getAgendaTreinamentoCobrancaTerceiroEnvio().equalsIgnoreCase("SIM")) {
		
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Cobrança: Terceiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						}
					} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaSegundoEnvio()) <= numeroDiasAposInicio) {
						if (agendaTreinamento.getAgendaTreinamentoCobrancaSegundoEnvio().equalsIgnoreCase("SIM")) {
		
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Cobrança: Segundo Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						}
					} else	if (Integer.parseInt(agendaTreinamento.getAgendaTreinamentoCobrancaDiaPrimeiroEnvio()) <= numeroDiasAposInicio) {
		
						if (agendaTreinamento.getAgendaTreinamentoCobrancaPrimeiroEnvio().equalsIgnoreCase("SIM")) {
		
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(colaboradorTreinamento.getColaborador().getEmailNome())
									, "Cobrança: Primeiro Aviso",  treinamento.getTreinamentoNome() + " : " + agendaTreinamento.getAgendaTreinamentoCobrancaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						}
					}
				} 
			}); 
		});
		
		return listMensagem;
	}


}
