package br.com.j4business.saga.contrato.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contrato.model.ContratoForm;
import br.com.j4business.saga.contrato.repository.ContratoRepository;

@Service("contratoService")
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ContratoService.class.getName());

	@Override
	public List<Contrato> getContratoAll() {
		return contratoRepository.findAll();
	}

	@Override
	public Page<Contrato> getContratoAll(Pageable pageable) {
		return contratoRepository.findAll(pageable);
	}

	@Override
	public Contrato getContratoByContratoPK(long contratoPK) {
		
		return contratoRepository.findOne(contratoPK);
	}

	@Transactional
	public Contrato create(ContratoForm contratoForm) {
		
		Contrato contrato = new Contrato();
		
		contrato = this.converteContratoForm(contratoForm);
		
		contrato = entityManager.merge(contrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Create " + "\n Usuário => " + username + 
										" // Id => "+contrato.getContratoPK() + 
										" // Contrato => "+contrato.getContratoNome() + 
										" // Descrição => "+ contrato.getContratoDescricao());
		
		return contrato;
	}

	@Transactional
	public Contrato save(ContratoForm contratoForm) {
		
		Contrato contrato = new Contrato();
		
		contrato = this.converteContratoForm(contratoForm);
		
		contrato = entityManager.merge(contrato);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Save " + "\n Usuário => " + username + 
										" // Id => "+contrato.getContratoPK() + 
										" // Contrato => "+contrato.getContratoNome() + 
										" // Descrição => "+ contrato.getContratoDescricao());
		

		return contrato;
		
	}

	@Transactional
	public void delete(Long contratoId) {

		Contrato contrato = this.getContratoByContratoPK(contratoId);
		
		contratoRepository.delete(contrato.getContratoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Contrato Delete " + "\n Usuário => " + username + 
										" // Id => "+contrato.getContratoPK() + 
										" // Contrato => "+contrato.getContratoNome() + 
										" // Descrição => "+ contrato.getContratoDescricao());
		

    }

	@Transactional
	public Contrato converteContratoForm(ContratoForm contratoForm) {
		
		Contrato contrato = new Contrato();
		
		if (contratoForm.getContratoPK() > 0) {
			contrato = this.getContratoByContratoPK(contratoForm.getContratoPK());
		}
		
		contrato.setContratoNome(contratoForm.getContratoNome().replaceAll("\\s+"," "));
		contrato.setContratoDescricao(contratoForm.getContratoDescricao().replaceAll("\\s+"," "));

		contrato.setContratoDataInicio(contratoForm.getContratoDataInicio().replaceAll("\\s+"," "));
		contrato.setContratoDataTermino(contratoForm.getContratoDataTermino().replaceAll("\\s+"," "));
		contrato.setContratoDuracao(contratoForm.getContratoDuracao().replaceAll("\\s+"," "));
		
		contrato.setContratoMotivoOperacao(contratoForm.getContratoMotivoOperacao().replaceAll("\\s+"," "));
		contrato.setContratoStatus(contratoForm.getContratoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaboradorGestor = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoForm.getContratoGestor()));
		contrato.setColaboradorGestor(colaboradorGestor);

		Colaborador colaboradorDono = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoForm.getContratoDono()));
		contrato.setColaboradorDono(colaboradorDono);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(contratoForm.getContratoResponsavel()));
		contrato.setColaboradorResponsavel(colaborador);

		return contrato;
	}

	@Transactional
	public ContratoForm converteContrato(Contrato contrato) {
	
		ContratoForm contratoForm = new ContratoForm();
		
		contratoForm.setContratoPK(contrato.getContratoPK());
		contratoForm.setContratoNome(contrato.getContratoNome());
		contratoForm.setContratoDescricao(contrato.getContratoDescricao());

		contratoForm.setContratoDataInicio(contrato.getContratoDataInicio());
		contratoForm.setContratoDataTermino(contrato.getContratoDataTermino());
		contratoForm.setContratoDuracao(contrato.getContratoDuracao());
		
		contratoForm.setContratoMotivoOperacao(contrato.getContratoMotivoOperacao());
		contratoForm.setContratoStatus(AtributoStatus.valueOf(contrato.getContratoStatus()));

		contratoForm.setContratoGestor(contrato.getColaboradorGestor().getPessoaPK()+"");
		contratoForm.setContratoDono(contrato.getColaboradorDono().getPessoaPK()+"");
		contratoForm.setContratoResponsavel(contrato.getColaboradorResponsavel().getPessoaPK()+"");
		
	return contratoForm;
	}
	
	@Transactional
	public ContratoForm converteContratoView(Contrato contrato) {
	
		ContratoForm contratoForm = new ContratoForm();
		
		contratoForm.setContratoPK(contrato.getContratoPK());
		contratoForm.setContratoNome(contrato.getContratoNome());
		contratoForm.setContratoDescricao(contrato.getContratoDescricao());

		contratoForm.setContratoDataInicio(contrato.getContratoDataInicio());
		contratoForm.setContratoDataTermino(contrato.getContratoDataTermino());
		contratoForm.setContratoDuracao(contrato.getContratoDuracao());
		
		contratoForm.setContratoMotivoOperacao(contrato.getContratoMotivoOperacao());
		contratoForm.setContratoStatus(AtributoStatus.valueOf(contrato.getContratoStatus()));

		contratoForm.setContratoGestor(contrato.getColaboradorGestor().getPessoaPK()+"");
		contratoForm.setContratoDono(contrato.getColaboradorDono().getPessoaPK()+"");
		contratoForm.setContratoResponsavel(contrato.getColaboradorResponsavel().getPessoaNome());
		
		contratoForm.setContratoGestor(contrato.getColaboradorGestor().getPessoaNome()+"");

		contratoForm.setContratoDono(contrato.getColaboradorDono().getPessoaNome()+"");

		
	return contratoForm;
	}
	

	@Transactional
	public ContratoForm contratoParametros(ContratoForm contratoForm) {
	
		
		contratoForm.setContratoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return contratoForm;
	}

	@Override
	public List<Contrato> getByContratoDescricao(String contratoDescricao,Pageable pageable) {
		return contratoRepository.findByContratoDescricao(contratoDescricao,pageable);
	}

	@Override
	public List<Contrato> getByContratoNome(String contratoNome,Pageable pageable) {
		return contratoRepository.findByContratoNome(contratoNome,pageable);
	}

	@Override
	public List<Contrato> getByContratoDescricao(String contratoDescricao) {
		return contratoRepository.findByContratoDescricao(contratoDescricao);
	}

	@Override
	public List<Contrato> getByContratoNome(String contratoNome) {
		return contratoRepository.findByContratoNome(contratoNome);
	}



}
