package br.com.j4business.saga.agenda.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.agenda.model.Agenda;
import br.com.j4business.saga.agenda.repository.AgendaRepository;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.agenda.model.AgendaForm;

@Service("agendaService")
public class AgendaServiceImpl implements AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AgendaService.class.getName());

	@Override
	public Page<Agenda> getAgendaAll(Pageable pageable) {
		return agendaRepository.findAll(pageable);
	}

	@Override
	public List<Agenda> getAgendaAll() {
		return agendaRepository.findAll();
	}

	@Override
	public Agenda getAgendaByAgendaPK(long agendaPK) {
		
		return agendaRepository.findOne(agendaPK);
	}

	@Transactional
	public Agenda create(AgendaForm agendaForm) {
		
		Agenda agenda = new Agenda();
		
		agenda = this.converteAgendaForm(agendaForm);
		
		agenda = entityManager.merge(agenda);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Agenda Create " + "\n Usuário => " + username + 
										" // Id => "+agenda.getAgendaPK() + 
										" // Agenda => "+agenda.getAgendaNome() + 
										" // Descrição => "+ agenda.getAgendaDescricao());
		
		return agenda;
	}

	@Transactional
	public Agenda save(AgendaForm agendaForm) {
		
		Agenda agenda = new Agenda();
		
		agenda = this.converteAgendaForm(agendaForm);
		
		agenda = entityManager.merge(agenda);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Agenda Save " + "\n Usuário => " + username + 
										" // Id => "+agenda.getAgendaPK() + 
										" // Agenda => "+agenda.getAgendaNome() + 
										" // Descrição => "+ agenda.getAgendaDescricao());
		

		return agenda;
		
	}

	@Transactional
	public void delete(Long agendaId) {

		Agenda agenda = this.getAgendaByAgendaPK(agendaId);
		
		agendaRepository.delete(agenda.getAgendaPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Agenda Delete " + "\n Usuário => " + username + 
										" // Id => "+agenda.getAgendaPK() + 
										" // Agenda => "+agenda.getAgendaNome() + 
										" // Descrição => "+ agenda.getAgendaDescricao());
		

    }

	@Transactional
	public Agenda converteAgendaForm(AgendaForm agendaForm) {
		
		Agenda agenda = new Agenda();
		
		if (agendaForm.getAgendaPK() > 0) {
			agenda = this.getAgendaByAgendaPK(agendaForm.getAgendaPK());
		}
		
		agenda.setAgendaNome(agendaForm.getAgendaNome().replaceAll("\\s+"," "));
		agenda.setAgendaDescricao(agendaForm.getAgendaDescricao().replaceAll("\\s+"," "));
		agenda.setAgendaStatus(agendaForm.getAgendaStatus()+"");
		agenda.setAgendaMotivoOperacao(agendaForm.getAgendaMotivoOperacao().replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(agendaForm.getAgendaResponsavel()));
		agenda.setColaboradorResponsavel(colaborador);
		
		return agenda;
	}

	@Transactional
	public AgendaForm converteAgenda(Agenda agenda) {
	
		AgendaForm agendaForm = new AgendaForm();
		
		agendaForm.setAgendaPK(agenda.getAgendaPK());
		agendaForm.setAgendaNome(agenda.getAgendaNome());
		agendaForm.setAgendaDescricao(agenda.getAgendaDescricao());
		agendaForm.setAgendaStatus(AtributoStatus.valueOf(agenda.getAgendaStatus()));
		agendaForm.setAgendaMotivoOperacao(agenda.getAgendaMotivoOperacao());

		agendaForm.setAgendaResponsavel(agenda.getColaboradorResponsavel().getPessoaPK()+"");
		
	return agendaForm;
	}
	
	@Transactional
	public AgendaForm converteAgendaView(Agenda agenda) {
	
		AgendaForm agendaForm = new AgendaForm();
		
		agendaForm.setAgendaPK(agenda.getAgendaPK());
		agendaForm.setAgendaNome(agenda.getAgendaNome());
		agendaForm.setAgendaDescricao(agenda.getAgendaDescricao());
		agendaForm.setAgendaStatus(AtributoStatus.valueOf(agenda.getAgendaStatus()));
		agendaForm.setAgendaMotivoOperacao(agenda.getAgendaMotivoOperacao());

		agendaForm.setAgendaResponsavel(agenda.getColaboradorResponsavel().getPessoaNome());
		
	return agendaForm;
	}
	

	@Transactional
	public AgendaForm agendaParametros(AgendaForm agendaForm) {
		
		agendaForm.setAgendaStatus(AtributoStatus.valueOf("ATIVO"));
		
	return agendaForm;
	}

	@Override
	public List<Agenda> getByAgendaDescricao(String agendaDescricao,Pageable pageable) {
		return agendaRepository.findByAgendaDescricao(agendaDescricao,pageable);
	}

	@Override
	public List<Agenda> getByAgendaNome(String agendaNome,Pageable pageable) {
		return agendaRepository.findByAgendaNome(agendaNome,pageable);
	}


	@Override
	public List<Agenda> getByAgendaDescricao(String agendaDescricao) {
		return agendaRepository.findByAgendaDescricao(agendaDescricao);
	}

	@Override
	public List<Agenda> getByAgendaNome(String agendaNome) {
		return agendaRepository.findByAgendaNome(agendaNome);
	}



}
