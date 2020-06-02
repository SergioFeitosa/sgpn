package br.com.j4business.saga.atendimento.service;

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
import br.com.j4business.saga.atendimento.model.Atendimento;
import br.com.j4business.saga.atendimento.repository.AtendimentoRepository;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.atendimento.model.AtendimentoForm;

@Service("atendimentoService")
public class AtendimentoServiceImpl implements AtendimentoService {

	@Autowired
	private AtendimentoRepository atendimentoRepository;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(AtendimentoService.class.getName());

	@Override
	public Page<Atendimento> getAtendimentoAll(Pageable pageable) {
		return atendimentoRepository.findAll(pageable);
	}

	@Override
	public List<Atendimento> getAtendimentoAll() {
		return atendimentoRepository.findAll();
	}

	@Override
	public Atendimento getAtendimentoByAtendimentoPK(long atendimentoPK) {
		
		return atendimentoRepository.findOne(atendimentoPK);
	}

	@Transactional
	public Atendimento create(AtendimentoForm atendimentoForm) {
		
		Atendimento atendimento = new Atendimento();
		
		atendimento = this.converteAtendimentoForm(atendimentoForm);

		atendimento = entityManager.merge(atendimento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atendimento Create " + "\n Usuário => " + username + 
										" // Id => "+atendimento.getAtendimentoPK() + 
										" // Atendimento => "+atendimento.getAtendimentoNome() + 
										" // Descrição => "+ atendimento.getAtendimentoDescricao());
		
		return atendimento;
	}

	@Transactional
	public Atendimento save(AtendimentoForm atendimentoForm) {
		
		Atendimento atendimento = new Atendimento();

		atendimento = this.converteAtendimentoForm(atendimentoForm);
		
		atendimento = entityManager.merge(atendimento);
		String username = usuarioSeguranca.getUsuarioLogado();
		
		logger.info("Atendimento Save " + "\n Usuário => " + username + 
										" // Id => "+atendimento.getAtendimentoPK() + 
										" // Atendimento => "+atendimento.getAtendimentoNome() + 
										" // Descrição => "+ atendimento.getAtendimentoDescricao());
		

		return atendimento;
		
	}

	@Transactional
	public void delete(Long atendimentoId) {

		Atendimento atendimento = this.getAtendimentoByAtendimentoPK(atendimentoId);
		
		atendimentoRepository.delete(atendimento.getAtendimentoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Atendimento Delete " + "\n Usuário => " + username + 
										" // Id => "+atendimento.getAtendimentoPK() + 
										" // Atendimento => "+atendimento.getAtendimentoNome() + 
										" // Descrição => "+ atendimento.getAtendimentoDescricao());
		

    }

	@Transactional
	public Atendimento converteAtendimentoForm(AtendimentoForm atendimentoForm) {
		
		Atendimento atendimento = new Atendimento();
		
		if (atendimentoForm.getAtendimentoPK() > 0) {
			atendimento = this.getAtendimentoByAtendimentoPK(atendimentoForm.getAtendimentoPK());
		}
		
		atendimento.setAtendimentoNome(atendimentoForm.getAtendimentoNome().replaceAll("\\s+"," "));
		atendimento.setAtendimentoDescricao(atendimentoForm.getAtendimentoDescricao().replaceAll("\\s+"," "));
		atendimento.setAtendimentoStatus(atendimentoForm.getAtendimentoStatus()+"");
		atendimento.setAtendimentoMotivoOperacao(atendimentoForm.getAtendimentoMotivoOperacao());

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(atendimentoForm.getAtendimentoResponsavel()));
		atendimento.setColaboradorResponsavel(colaborador);

		return atendimento;
	}

	@Transactional
	public AtendimentoForm converteAtendimento(Atendimento atendimento) {
	
		AtendimentoForm atendimentoForm = new AtendimentoForm();
		
		atendimentoForm.setAtendimentoPK(atendimento.getAtendimentoPK());
		atendimentoForm.setAtendimentoNome(atendimento.getAtendimentoNome());
		atendimentoForm.setAtendimentoDescricao(atendimento.getAtendimentoDescricao());
		atendimentoForm.setAtendimentoStatus(AtributoStatus.valueOf(atendimento.getAtendimentoStatus()+""));
		atendimentoForm.setAtendimentoMotivoOperacao(atendimento.getAtendimentoMotivoOperacao());

		atendimentoForm.setAtendimentoResponsavel(atendimento.getColaboradorResponsavel().getPessoaPK()+"");
		
	return atendimentoForm;
	}
	
	@Transactional
	public AtendimentoForm converteAtendimentoView(Atendimento atendimento) {
	
		AtendimentoForm atendimentoForm = new AtendimentoForm();
		
		atendimentoForm.setAtendimentoPK(atendimento.getAtendimentoPK());
		atendimentoForm.setAtendimentoNome(atendimento.getAtendimentoNome());
		atendimentoForm.setAtendimentoDescricao(atendimento.getAtendimentoDescricao());
		atendimentoForm.setAtendimentoStatus(AtributoStatus.valueOf(atendimento.getAtendimentoStatus()+""));
		atendimentoForm.setAtendimentoMotivoOperacao(atendimento.getAtendimentoMotivoOperacao());

		atendimentoForm.setAtendimentoResponsavel(atendimento.getColaboradorResponsavel().getPessoaNome());

		return atendimentoForm;
	}
	

	@Transactional
	public AtendimentoForm atendimentoParametros(AtendimentoForm atendimentoForm) {
		atendimentoForm.setAtendimentoStatus(AtributoStatus.valueOf("ATIVO"));
	return atendimentoForm;
	}

	@Override
	public List<Atendimento> getByAtendimentoDescricao(String atendimentoDescricao,Pageable pageable) {
		return atendimentoRepository.findByAtendimentoDescricao(atendimentoDescricao,pageable);
	}

	@Override
	public List<Atendimento> getByAtendimentoNome(String atendimentoNome,Pageable pageable) {
		return atendimentoRepository.findByAtendimentoNome(atendimentoNome,pageable);
	}


	@Override
	public List<Atendimento> getByAtendimentoDescricao(String atendimentoDescricao) {
		return atendimentoRepository.findByAtendimentoDescricao(atendimentoDescricao);
	}

	@Override
	public List<Atendimento> getByAtendimentoNome(String atendimentoNome) {
		return atendimentoRepository.findByAtendimentoNome(atendimentoNome);
	}



}
