package br.com.j4business.saga.colaboradortreinamento.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.treinamento.model.Treinamento;
import br.com.j4business.saga.treinamento.service.TreinamentoService;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamento;
import br.com.j4business.saga.colaboradortreinamento.model.ColaboradorTreinamentoForm;
import br.com.j4business.saga.colaboradortreinamento.repository.ColaboradorTreinamentoRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorTreinamentoService")
public class ColaboradorTreinamentoServiceImpl implements ColaboradorTreinamentoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorTreinamentoRepository colaboradorTreinamentoRepository;

	@Autowired
	private TreinamentoService treinamentoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorTreinamentoService.class.getName());


	@Override
	public List<ColaboradorTreinamento> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorTreinamentoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorTreinamento> getByTreinamentoNome(String treinamentoNome,Pageable pageable) {
		return colaboradorTreinamentoRepository.findByTreinamentoNome(treinamentoNome,pageable);
	}

	@Override
	public List<ColaboradorTreinamento> getByColaboradorNome(String colaboradorNome) {
		return colaboradorTreinamentoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorTreinamento> getByTreinamentoNome(String treinamentoNome) {
		return colaboradorTreinamentoRepository.findByTreinamentoNome(treinamentoNome);
	}

	@Override
	public List<ColaboradorTreinamento> getByTreinamentoPK(long treinamentoPK) {
		return colaboradorTreinamentoRepository.findByTreinamentoPK(treinamentoPK);
	}

	@Override
	public List<ColaboradorTreinamento> getByTreinamentoPK(long treinamentoPK,Pageable pageable) {
		return colaboradorTreinamentoRepository.findByTreinamentoPK(treinamentoPK,pageable);
	}

	@Override
	public List<ColaboradorTreinamento> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorTreinamentoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorTreinamento> getColaboradorTreinamentoAll(Pageable pageable) {
		return colaboradorTreinamentoRepository.findColaboradorTreinamentoAll(pageable);
	}

	@Override
	public ColaboradorTreinamento getColaboradorTreinamentoByColaboradorTreinamentoPK(long colaboradorTreinamentoPK) {
		Optional<ColaboradorTreinamento> colaboradorTreinamento =  colaboradorTreinamentoRepository.findById(colaboradorTreinamentoPK);
		return colaboradorTreinamento.get();
	}

	@Override
	public ColaboradorTreinamento getByColaboradorAndTreinamento (Colaborador colaborador,Treinamento treinamento) {
		
		return colaboradorTreinamentoRepository.findByColaboradorAndTreinamento(colaborador,treinamento);
	}

	@Transactional
	public ColaboradorTreinamento save(ColaboradorTreinamentoForm colaboradorTreinamentoForm) {

		ColaboradorTreinamento colaboradorTreinamento = new ColaboradorTreinamento();
		
		colaboradorTreinamento = this.converteColaboradorTreinamentoForm(colaboradorTreinamentoForm);
		
		colaboradorTreinamento = entityManager.merge(colaboradorTreinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorTreinamento Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorTreinamento.getColaboradorTreinamentoPK() + 
										" // Colaborador Id => "+colaboradorTreinamento.getColaborador().getPessoaPK() + 
										" // Treinamento Id => "+colaboradorTreinamento.getTreinamento().getTreinamentoPK());
		return colaboradorTreinamento;
	}

	@Transactional
	public void delete(Long colaboradorTreinamentoPK) {

		ColaboradorTreinamento colaboradorTreinamentoTemp = this.getColaboradorTreinamentoByColaboradorTreinamentoPK(colaboradorTreinamentoPK);

		colaboradorTreinamentoRepository.delete(colaboradorTreinamentoTemp);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorTreinamento Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorTreinamentoTemp.getColaboradorTreinamentoPK() + 
										" // Colaborador Id => "+colaboradorTreinamentoTemp.getColaborador().getPessoaPK() + 
										" // Treinamento Id => "+colaboradorTreinamentoTemp.getTreinamento().getTreinamentoPK()); 
    }

	@Transactional
	public void deleteByTreinamento(Treinamento treinamento) {
		
		List<ColaboradorTreinamento> colaboradorTreinamentoList = colaboradorTreinamentoRepository.findByTreinamento(treinamento);

		for (ColaboradorTreinamento colaboradorTreinamento2 : colaboradorTreinamentoList) {

			colaboradorTreinamentoRepository.delete(colaboradorTreinamento2);
			
		}

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorTreinamentoList.forEach((ColaboradorTreinamento colaboradorTreinamento) -> {
			
			logger.info("ColaboradorTreinamento Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorTreinamento.getColaboradorTreinamentoPK() + 
											" // Colaborador Id => "+colaboradorTreinamento.getColaborador().getPessoaPK() + 
											" // Treinamento Id => "+colaboradorTreinamento.getTreinamento().getTreinamentoPK()); 

		});
		
    }

	@Transactional
	public ColaboradorTreinamento converteColaboradorTreinamentoForm(ColaboradorTreinamentoForm colaboradorTreinamentoForm) {
		
		ColaboradorTreinamento colaboradorTreinamento = new ColaboradorTreinamento();
		
		if (colaboradorTreinamentoForm.getColaboradorTreinamentoPK() > 0) {
			colaboradorTreinamento = this.getColaboradorTreinamentoByColaboradorTreinamentoPK(colaboradorTreinamentoForm.getColaboradorTreinamentoPK());
		}
		
		Treinamento treinamento = treinamentoService.getTreinamentoByTreinamentoPK(Long.parseLong(colaboradorTreinamentoForm.getTreinamentoNome()));
		colaboradorTreinamento.setTreinamento(treinamento);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorTreinamentoForm.getColaboradorNome()));
		colaboradorTreinamento.setColaborador(colaborador);

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorTreinamentoForm.getColaboradorTreinamentoResponsavel()));
		colaboradorTreinamento.setColaboradorResponsavel(colaboradorResponsavel);

		colaboradorTreinamento.setColaboradorTreinamentoMotivoOperacao(colaboradorTreinamentoForm.getColaboradorTreinamentoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorTreinamento.setColaboradorTreinamentoStatus(colaboradorTreinamentoForm.getColaboradorTreinamentoStatus()+"".replaceAll("\\s+"," "));
		
		return colaboradorTreinamento;
	}

	@Transactional
	public ColaboradorTreinamentoForm converteColaboradorTreinamento(ColaboradorTreinamento colaboradorTreinamento) {
	
		ColaboradorTreinamentoForm colaboradorTreinamentoForm = new ColaboradorTreinamentoForm();
		
		colaboradorTreinamentoForm.setColaboradorTreinamentoPK(colaboradorTreinamento.getColaboradorTreinamentoPK());
		colaboradorTreinamentoForm.setColaboradorNome(colaboradorTreinamento.getColaborador().getPessoaPK()+"");
		colaboradorTreinamentoForm.setTreinamentoNome(colaboradorTreinamento.getTreinamento().getTreinamentoPK()+"");

		colaboradorTreinamentoForm.setColaboradorTreinamentoResponsavel(colaboradorTreinamento.getColaborador().getPessoaPK()+"");

		colaboradorTreinamentoForm.setColaboradorTreinamentoMotivoOperacao(colaboradorTreinamento.getColaboradorTreinamentoMotivoOperacao());
		colaboradorTreinamentoForm.setColaboradorTreinamentoStatus(AtributoStatus.valueOf(colaboradorTreinamento.getColaboradorTreinamentoStatus()));
		
	return colaboradorTreinamentoForm;
	}
	
	@Transactional
	public ColaboradorTreinamentoForm converteColaboradorTreinamentoView(ColaboradorTreinamento colaboradorTreinamento) {
	
		ColaboradorTreinamentoForm colaboradorTreinamentoForm = new ColaboradorTreinamentoForm();
		
		colaboradorTreinamentoForm.setColaboradorTreinamentoPK(colaboradorTreinamento.getColaboradorTreinamentoPK());
		colaboradorTreinamentoForm.setColaboradorNome(colaboradorTreinamento.getColaborador().getPessoaNome());
		colaboradorTreinamentoForm.setTreinamentoNome(colaboradorTreinamento.getTreinamento().getTreinamentoNome());

		colaboradorTreinamentoForm.setColaboradorTreinamentoMotivoOperacao(colaboradorTreinamento.getColaboradorTreinamentoMotivoOperacao());
		colaboradorTreinamentoForm.setColaboradorTreinamentoStatus(AtributoStatus.valueOf(colaboradorTreinamento.getColaboradorTreinamentoStatus()));

		colaboradorTreinamentoForm.setColaboradorTreinamentoResponsavel(colaboradorTreinamento.getColaborador().getPessoaNome());

	return colaboradorTreinamentoForm;
	}
	

	@Transactional
	public ColaboradorTreinamentoForm colaboradorTreinamentoParametros(ColaboradorTreinamentoForm colaboradorTreinamentoForm) {
	
		
		colaboradorTreinamentoForm.setColaboradorTreinamentoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorTreinamentoForm;
	}
	
	@Transactional
	public ColaboradorTreinamento create(ColaboradorTreinamentoForm colaboradorTreinamentoForm) {
		
		ColaboradorTreinamento colaboradorTreinamento = new ColaboradorTreinamento();
		
		colaboradorTreinamento = this.converteColaboradorTreinamentoForm(colaboradorTreinamentoForm);
		
		colaboradorTreinamento = entityManager.merge(colaboradorTreinamento);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Treinamento Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorTreinamento.getColaboradorTreinamentoPK() + 
				" // Colaborador Id => "+colaboradorTreinamento.getColaborador().getPessoaPK() + 
				" // Treinamento Id => "+colaboradorTreinamento.getTreinamento().getTreinamentoPK()); 
		
		return colaboradorTreinamento;
	}


}
