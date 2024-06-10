package br.com.j4business.saga.agendacontrato.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.email.Mensagem;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.service.ContratoService;
import br.com.j4business.saga.fornecedorcontrato.model.FornecedorContrato;
import br.com.j4business.saga.fornecedorcontrato.service.FornecedorContratoService;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.service.AgendaService;
import br.com.j4business.saga.agendacontrato.enumeration.AgendaContratoEnvio;
import br.com.j4business.saga.agendacontrato.model.AgendaContrato;
import br.com.j4business.saga.agendacontrato.model.AgendaContratoForm;
import br.com.j4business.saga.agendacontrato.repository.AgendaContratoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

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

@Service("agendaContratoService")
public class AgendaContratoServiceImpl implements AgendaContratoService {

	@Autowired
	private AgendaService agendaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AgendaContratoRepository agendaContratoRepository;

	@Autowired
	private FornecedorContratoService contratoFornecedorService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AgendaContratoService.class.getName());


	@Override
	public List<AgendaContrato> getByAgendaNome(String agendaNome,Pageable pageable) {
		return agendaContratoRepository.findByAgendaNome(agendaNome,pageable);
	}

	@Override
	public List<AgendaContrato> getByContratoNome(String contratoNome,Pageable pageable) {
		return agendaContratoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<AgendaContrato> getByAgendaNome(String agendaNome) {
		return agendaContratoRepository.findByAgendaNome(agendaNome);
	}

	@Override
	public List<AgendaContrato> getByContratoNome(String contratoNome) {
		return agendaContratoRepository.findByContratoNome(contratoNome);
	}

	@Override
	public List<AgendaContrato> getByContratoPK(long contratoPK,Pageable pageable) {
		return agendaContratoRepository.findByContratoPK(contratoPK,pageable);
	}

	@Override
	public List<AgendaContrato> getByAgendaPK(long agendaPK,Pageable pageable) {
		return agendaContratoRepository.findByAgendaPK(agendaPK,pageable);
	}

	@Override
	public List<AgendaContrato> getAgendaContratoAll(Pageable pageable) {
		return agendaContratoRepository.findAgendaContratoAll(pageable);
	}

	@Override
	public AgendaContrato getAgendaContratoByAgendaContratoPK(long agendaContratoPK) {
		return agendaContratoRepository.findByAgendaContratoPK(agendaContratoPK);
	}

	@Override
	public AgendaContrato getByAgendaAndContrato (Agenda agenda,Contrato contrato) {
		
		return agendaContratoRepository.findByAgendaAndContrato(agenda,contrato);
	}

	@Transactional
	public AgendaContrato save(AgendaContratoForm agendaContratoForm) {

		AgendaContrato agendaContrato = new AgendaContrato();
		
		agendaContrato = this.converteAgendaContratoForm(agendaContratoForm);
		
		agendaContrato = entityManager.merge(agendaContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("AgendaContrato Save " + "\n Usuário => " + username + 
										" // Id => "+agendaContrato.getAgendaContratoPK() + 
										" // Agenda Id => "+agendaContrato.getAgenda().getAgendaPK() + 
										" // Contrato Id => "+agendaContrato.getContrato().getContratoPK());
		return agendaContrato;
	}

	@Transactional
	public void delete(Long agendaContratoPK) {

		Optional<AgendaContrato> agendaContratoSalvo = agendaContratoRepository.findById(agendaContratoPK);

		if (agendaContratoSalvo.isPresent()) {
			agendaContratoRepository.delete(agendaContratoSalvo.get());
		}

		

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("AgendaContrato Save " + "\n Usuário => " + username + 
										" // Id => "+agendaContratoSalvo.get().getAgendaContratoPK() + 
										" // Agenda Id => "+agendaContratoSalvo.get().getAgenda().getAgendaPK() + 
										" // Contrato Id => "+agendaContratoSalvo.get().getContrato().getContratoPK()); 
    }

	@Transactional
	public void deleteByContrato(Contrato contrato) {
		
		List<AgendaContrato> agendaContratos = agendaContratoRepository.findByContrato(contrato);

		for (AgendaContrato agendaContrato2 : agendaContratos) {

			agendaContratoRepository.deleteById(agendaContrato2.getAgendaContratoPK());
			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		agendaContratos.forEach((AgendaContrato agendaContrato) -> {
			
			logger.info("AgendaContrato Delete2 " + "\n Usuário => " + username + 
					" // Id => "+agendaContrato.getAgendaContratoPK() + 
					" // Agenda Id => "+agendaContrato.getAgenda().getAgendaPK() + 
					" // Contrato Id => "+agendaContrato.getContrato().getContratoPK()); 
			
			
		});
		
	
	
	
	}

	@Transactional
	public AgendaContrato converteAgendaContratoForm(AgendaContratoForm agendaContratoForm) {
		
		AgendaContrato agendaContrato = new AgendaContrato();
		
		if (agendaContratoForm.getAgendaContratoPK() > 0) {
			agendaContrato = this.getAgendaContratoByAgendaContratoPK(agendaContratoForm.getAgendaContratoPK());
		}
		
		Contrato contrato = contratoService.getContratoByContratoPK(Long.parseLong(agendaContratoForm.getContratoNome()));
		agendaContrato.setContrato(contrato);

		Agenda agenda = agendaService.getAgendaByAgendaPK(Long.parseLong(agendaContratoForm.getAgendaNome()));
		agendaContrato.setAgenda(agenda);

		agendaContrato.setAgendaContratoMotivoOperacao(agendaContratoForm.getAgendaContratoMotivoOperacao().replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoStatus(agendaContratoForm.getAgendaContratoStatus()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoAlertaSuperiorPrimeiroEnvio(agendaContratoForm.getAgendaContratoAlertaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaSuperiorSegundoEnvio(agendaContratoForm.getAgendaContratoAlertaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaSuperiorTerceiroEnvio(agendaContratoForm.getAgendaContratoAlertaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoCobrancaSuperiorPrimeiroEnvio(agendaContratoForm.getAgendaContratoCobrancaSuperiorPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaSuperiorSegundoEnvio(agendaContratoForm.getAgendaContratoCobrancaSuperiorSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaSuperiorTerceiroEnvio(agendaContratoForm.getAgendaContratoCobrancaSuperiorTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoAlertaPrimeiroEnvio(agendaContratoForm.getAgendaContratoAlertaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaSegundoEnvio(agendaContratoForm.getAgendaContratoAlertaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaTerceiroEnvio(agendaContratoForm.getAgendaContratoAlertaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoCobrancaPrimeiroEnvio(agendaContratoForm.getAgendaContratoCobrancaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaSegundoEnvio(agendaContratoForm.getAgendaContratoCobrancaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaTerceiroEnvio(agendaContratoForm.getAgendaContratoCobrancaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoAlertaMensagemPrimeiroEnvio(agendaContratoForm.getAgendaContratoAlertaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaMensagemSegundoEnvio(agendaContratoForm.getAgendaContratoAlertaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaMensagemTerceiroEnvio(agendaContratoForm.getAgendaContratoAlertaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoCobrancaMensagemPrimeiroEnvio(agendaContratoForm.getAgendaContratoCobrancaMensagemPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaMensagemSegundoEnvio(agendaContratoForm.getAgendaContratoCobrancaMensagemSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaMensagemTerceiroEnvio(agendaContratoForm.getAgendaContratoCobrancaMensagemTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoAlertaDiaPrimeiroEnvio(agendaContratoForm.getAgendaContratoAlertaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaDiaSegundoEnvio(agendaContratoForm.getAgendaContratoAlertaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoAlertaDiaTerceiroEnvio(agendaContratoForm.getAgendaContratoAlertaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		agendaContrato.setAgendaContratoCobrancaDiaPrimeiroEnvio(agendaContratoForm.getAgendaContratoCobrancaDiaPrimeiroEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaDiaSegundoEnvio(agendaContratoForm.getAgendaContratoCobrancaDiaSegundoEnvio()+"".replaceAll("\\s+"," "));
		agendaContrato.setAgendaContratoCobrancaDiaTerceiroEnvio(agendaContratoForm.getAgendaContratoCobrancaDiaTerceiroEnvio()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(agendaContratoForm.getAgendaContratoResponsavel()));
		agendaContrato.setColaboradorResponsavel(colaborador);
		
		return agendaContrato;
	}

	@Transactional
	public AgendaContratoForm converteAgendaContrato(AgendaContrato agendaContrato) {
	
		AgendaContratoForm agendaContratoForm = new AgendaContratoForm();
		
		agendaContratoForm.setAgendaContratoPK(agendaContrato.getAgendaContratoPK());
		agendaContratoForm.setAgendaNome(agendaContrato.getAgenda().getAgendaPK()+"");
		agendaContratoForm.setContratoNome(agendaContrato.getContrato().getContratoPK()+"");

		agendaContratoForm.setAgendaContratoMotivoOperacao(agendaContrato.getAgendaContratoMotivoOperacao());
		agendaContratoForm.setAgendaContratoStatus(AtributoStatus.valueOf(agendaContrato.getAgendaContratoStatus()));

		agendaContratoForm.setAgendaContratoAlertaMensagemPrimeiroEnvio(agendaContrato.getAgendaContratoAlertaMensagemPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoAlertaMensagemSegundoEnvio(agendaContrato.getAgendaContratoAlertaMensagemSegundoEnvio());
		agendaContratoForm.setAgendaContratoAlertaMensagemTerceiroEnvio(agendaContrato.getAgendaContratoAlertaMensagemTerceiroEnvio());

		agendaContratoForm.setAgendaContratoCobrancaMensagemPrimeiroEnvio(agendaContrato.getAgendaContratoCobrancaMensagemPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoCobrancaMensagemSegundoEnvio(agendaContrato.getAgendaContratoCobrancaMensagemSegundoEnvio());
		agendaContratoForm.setAgendaContratoCobrancaMensagemTerceiroEnvio(agendaContrato.getAgendaContratoCobrancaMensagemTerceiroEnvio());

		agendaContratoForm.setAgendaContratoAlertaDiaPrimeiroEnvio(agendaContrato.getAgendaContratoAlertaDiaPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoAlertaDiaSegundoEnvio(agendaContrato.getAgendaContratoAlertaDiaSegundoEnvio());
		agendaContratoForm.setAgendaContratoAlertaDiaTerceiroEnvio(agendaContrato.getAgendaContratoAlertaDiaTerceiroEnvio());

		agendaContratoForm.setAgendaContratoCobrancaDiaPrimeiroEnvio(agendaContrato.getAgendaContratoCobrancaDiaPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoCobrancaDiaSegundoEnvio(agendaContrato.getAgendaContratoCobrancaDiaSegundoEnvio());
		agendaContratoForm.setAgendaContratoCobrancaDiaTerceiroEnvio(agendaContrato.getAgendaContratoCobrancaDiaTerceiroEnvio());

		agendaContratoForm.setAgendaContratoAlertaPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSegundoEnvio()));
		agendaContratoForm.setAgendaContratoAlertaTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoAlertaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorSegundoEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoCobrancaPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSegundoEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoCobrancaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorSegundoEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoResponsavel(agendaContrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return agendaContratoForm;
	}
	
	@Transactional
	public AgendaContratoForm converteAgendaContratoView(AgendaContrato agendaContrato) {
	
		AgendaContratoForm agendaContratoForm = new AgendaContratoForm();
		
		agendaContratoForm.setAgendaContratoPK(agendaContrato.getAgendaContratoPK());
		agendaContratoForm.setAgendaNome(agendaContrato.getAgenda().getAgendaNome());
		agendaContratoForm.setContratoNome(agendaContrato.getContrato().getContratoNome());

		agendaContratoForm.setAgendaContratoMotivoOperacao(agendaContrato.getAgendaContratoMotivoOperacao());
		agendaContratoForm.setAgendaContratoStatus(AtributoStatus.valueOf(agendaContrato.getAgendaContratoStatus()));

		agendaContratoForm.setAgendaContratoResponsavel(agendaContrato.getColaboradorResponsavel().getPessoaNome());

		agendaContratoForm.setAgendaContratoAlertaMensagemPrimeiroEnvio(agendaContrato.getAgendaContratoAlertaMensagemPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoAlertaMensagemSegundoEnvio(agendaContrato.getAgendaContratoAlertaMensagemSegundoEnvio());
		agendaContratoForm.setAgendaContratoAlertaMensagemTerceiroEnvio(agendaContrato.getAgendaContratoAlertaMensagemTerceiroEnvio());

		agendaContratoForm.setAgendaContratoCobrancaMensagemPrimeiroEnvio(agendaContrato.getAgendaContratoCobrancaMensagemPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoCobrancaMensagemSegundoEnvio(agendaContrato.getAgendaContratoCobrancaMensagemSegundoEnvio());
		agendaContratoForm.setAgendaContratoCobrancaMensagemTerceiroEnvio(agendaContrato.getAgendaContratoCobrancaMensagemTerceiroEnvio());

		agendaContratoForm.setAgendaContratoAlertaDiaPrimeiroEnvio(agendaContrato.getAgendaContratoAlertaDiaPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoAlertaDiaSegundoEnvio(agendaContrato.getAgendaContratoAlertaDiaSegundoEnvio());
		agendaContratoForm.setAgendaContratoAlertaDiaTerceiroEnvio(agendaContrato.getAgendaContratoAlertaDiaTerceiroEnvio());

		agendaContratoForm.setAgendaContratoCobrancaDiaPrimeiroEnvio(agendaContrato.getAgendaContratoCobrancaDiaPrimeiroEnvio());
		agendaContratoForm.setAgendaContratoCobrancaDiaSegundoEnvio(agendaContrato.getAgendaContratoCobrancaDiaSegundoEnvio());
		agendaContratoForm.setAgendaContratoCobrancaDiaTerceiroEnvio(agendaContrato.getAgendaContratoCobrancaDiaTerceiroEnvio());

		agendaContratoForm.setAgendaContratoAlertaPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSegundoEnvio()));
		agendaContratoForm.setAgendaContratoAlertaTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoAlertaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorSegundoEnvio()));
		agendaContratoForm.setAgendaContratoAlertaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoAlertaSuperiorTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoCobrancaPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSegundoEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaTerceiroEnvio()));

		agendaContratoForm.setAgendaContratoCobrancaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorPrimeiroEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorSegundoEnvio()));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf(agendaContrato.getAgendaContratoCobrancaSuperiorTerceiroEnvio()));

		
	return agendaContratoForm;
	}
	

	@Transactional
	public AgendaContratoForm agendaContratoParametros(AgendaContratoForm agendaContratoForm) {
	
		
		agendaContratoForm.setAgendaContratoStatus(AtributoStatus.valueOf("ATIVO"));
		agendaContratoForm.setAgendaContratoAlertaPrimeiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaSegundoEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaTerceiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaPrimeiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaSegundoEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaTerceiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorPrimeiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorSegundoEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoCobrancaSuperiorTerceiroEnvio(AgendaContratoEnvio.valueOf("SIM"));
		agendaContratoForm.setAgendaContratoAlertaDiaPrimeiroEnvio("30");
		agendaContratoForm.setAgendaContratoAlertaDiaSegundoEnvio("15");
		agendaContratoForm.setAgendaContratoAlertaDiaTerceiroEnvio("7");
		agendaContratoForm.setAgendaContratoCobrancaDiaPrimeiroEnvio("10");
		agendaContratoForm.setAgendaContratoCobrancaDiaSegundoEnvio("20");
		agendaContratoForm.setAgendaContratoCobrancaDiaTerceiroEnvio("30");

		
	return agendaContratoForm;
	}
	
	@Transactional
	public AgendaContrato create(AgendaContratoForm agendaContratoForm) {
		
		AgendaContrato agendaContrato = new AgendaContrato();
		
		agendaContrato = this.converteAgendaContratoForm(agendaContratoForm);
		
		agendaContrato = entityManager.merge(agendaContrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Contrato Create " + "\n Usuário => " + username + 
				" // Id => "+agendaContrato.getAgendaContratoPK() + 
				" // Agenda Id => "+agendaContrato.getAgenda().getAgendaPK() + 
				" // Contrato Id => "+agendaContrato.getContrato().getContratoPK()); 
		
		return agendaContrato;
	}

	@Override
	public List<Mensagem> getAgendaContratoMensagemAll() {
		int MILLIS_IN_DAY = 86400000;
	
	List<Mensagem> listMensagem = new ArrayList<Mensagem>();
		
		List<AgendaContrato> agendaContratoList = agendaContratoRepository.findAgendaContratoAll();
		
		agendaContratoList.forEach((AgendaContrato agendaContrato) -> {
	
			Contrato contrato = contratoService.getContratoByContratoPK(agendaContrato.getContrato().getContratoPK());
	
			Calendar dataAtual = Calendar.getInstance();			
	
			Calendar contratoDataTermino = Calendar.getInstance();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				contratoDataTermino.setTime(sdf.parse(contrato.getContratoDataTermino()));
			} catch (ParseException e) {
				e.printStackTrace();
			}// all done
			
		
			if (dataAtual.before(contratoDataTermino)) {
				
				int numeroDiasParaTermino = (int) ((contratoDataTermino.getTimeInMillis() - dataAtual.getTimeInMillis()) / MILLIS_IN_DAY);
				
				if (Integer.parseInt(agendaContrato.getAgendaContratoAlertaDiaTerceiroEnvio()) >= numeroDiasParaTermino) {
					if (agendaContrato.getAgendaContratoAlertaTerceiroEnvio().equalsIgnoreCase("SIM")) {
						
						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
							contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Alerta: Terceiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
							
						});
						
					}
					if (agendaContrato.getAgendaContratoAlertaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Alerta: Terceiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaContrato.getAgendaContratoAlertaDiaSegundoEnvio()) >= numeroDiasParaTermino) {
					if (agendaContrato.getAgendaContratoAlertaSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Alerta: Segundo Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaContrato.getAgendaContratoAlertaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Alerta: Segundo Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaContrato.getAgendaContratoAlertaDiaPrimeiroEnvio()) >= numeroDiasParaTermino) {
					if (agendaContrato.getAgendaContratoAlertaPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Alerta: Primeiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaContrato.getAgendaContratoAlertaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Alerta: Primeiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoAlertaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				}
			} else {
				int numeroDiasAposTermino = (int) ((dataAtual.getTimeInMillis() - contratoDataTermino.getTimeInMillis()) / MILLIS_IN_DAY);
			
				if (Integer.parseInt(agendaContrato.getAgendaContratoCobrancaDiaTerceiroEnvio()) <= numeroDiasAposTermino) {

					if (agendaContrato.getAgendaContratoCobrancaTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Cobrança: Terceiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaContrato.getAgendaContratoCobrancaSuperiorTerceiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Cobrança: Terceiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemTerceiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaContrato.getAgendaContratoCobrancaDiaSegundoEnvio()) <= numeroDiasAposTermino) {
					if (agendaContrato.getAgendaContratoCobrancaSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Cobrança: Segundo Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaContrato.getAgendaContratoCobrancaSuperiorSegundoEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Cobrança: Segundo Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemSegundoEnvio());
							listMensagem.add(mensagem);
						});
					}
				} else	if (Integer.parseInt(agendaContrato.getAgendaContratoCobrancaDiaPrimeiroEnvio()) <= numeroDiasAposTermino) {

					if (agendaContrato.getAgendaContratoCobrancaPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorGestor().getEmailNome())
									, "Cobrança: Primeiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
					if (agendaContrato.getAgendaContratoCobrancaSuperiorPrimeiroEnvio().equalsIgnoreCase("SIM")) {

						List<FornecedorContrato> contratoFornecedorList = contratoFornecedorService.getByContratoPK(contrato.getContratoPK());
						
						contratoFornecedorList.forEach((FornecedorContrato contratoFornecedor) -> {
							
							Mensagem mensagem = new Mensagem("J4Business <j4businessbr@gmail.com>", 
									Arrays.asList(contratoFornecedor.getContrato().getColaboradorDono().getEmailNome())
									, "Cobrança: Primeiro Aviso", contrato.getContratoNome() + " : " + agendaContrato.getAgendaContratoCobrancaMensagemPrimeiroEnvio());
							listMensagem.add(mensagem);
						});
					}
				}
			} 
		});
		
		return listMensagem;
	}
}
