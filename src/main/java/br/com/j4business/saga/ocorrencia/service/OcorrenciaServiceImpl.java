package br.com.j4business.saga.ocorrencia.service;

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
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.ocorrencia.model.Ocorrencia;
import br.com.j4business.saga.ocorrencia.model.OcorrenciaForm;
import br.com.j4business.saga.ocorrencia.repository.OcorrenciaRepository;

@Service("ocorrenciaService")
public class OcorrenciaServiceImpl implements OcorrenciaService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private OcorrenciaRepository ocorrenciaRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(OcorrenciaService.class.getName());

	@Override
	public List<Ocorrencia> getOcorrenciaAll() {
		return ocorrenciaRepository.findAll();
	}

	@Override
	public Page<Ocorrencia> getOcorrenciaAll(Pageable pageable) {
		return ocorrenciaRepository.findAll(pageable);
	}

	@Override
	public Ocorrencia getOcorrenciaByOcorrenciaPK(long ocorrenciaPK) {
		
		Optional<Ocorrencia> ocorrencia = ocorrenciaRepository.findById(ocorrenciaPK);
		return ocorrencia.get();
	}

	@Transactional
	public Ocorrencia create(OcorrenciaForm ocorrenciaForm) {
		
		Ocorrencia ocorrencia = new Ocorrencia();
		
		ocorrencia = this.converteOcorrenciaForm(ocorrenciaForm);
		
		ocorrencia = entityManager.merge(ocorrencia);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Ocorrencia Create " + "\n Usuário => " + username + 
										" // Id => "+ocorrencia.getOcorrenciaPK() + 
										" // Ocorrencia => "+ocorrencia.getOcorrenciaNome() + 
										" // Descrição => "+ ocorrencia.getOcorrenciaDescricao());
		
		return ocorrencia;
	}

	@Transactional
	public Ocorrencia save(OcorrenciaForm ocorrenciaForm) {
		
		Ocorrencia ocorrencia = new Ocorrencia();
		
		ocorrencia = this.converteOcorrenciaForm(ocorrenciaForm);
		
		ocorrencia = entityManager.merge(ocorrencia);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Ocorrencia Save " + "\n Usuário => " + username + 
										" // Id => "+ocorrencia.getOcorrenciaPK() + 
										" // Ocorrencia => "+ocorrencia.getOcorrenciaNome() + 
										" // Descrição => "+ ocorrencia.getOcorrenciaDescricao());
		

		return ocorrencia;
		
	}

	@Transactional
	public void delete(Long ocorrenciaId) {

		Ocorrencia ocorrencia = this.getOcorrenciaByOcorrenciaPK(ocorrenciaId);
		
		ocorrenciaRepository.delete(ocorrencia);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Ocorrencia Delete " + "\n Usuário => " + username + 
										" // Id => "+ocorrencia.getOcorrenciaPK() + 
										" // Ocorrencia => "+ocorrencia.getOcorrenciaNome() + 
										" // Descrição => "+ ocorrencia.getOcorrenciaDescricao());
		

    }

	@Transactional
	public Ocorrencia converteOcorrenciaForm(OcorrenciaForm ocorrenciaForm) {
		
		Ocorrencia ocorrencia = new Ocorrencia();
		
		if (ocorrenciaForm.getOcorrenciaPK() > 0) {
			ocorrencia = this.getOcorrenciaByOcorrenciaPK(ocorrenciaForm.getOcorrenciaPK());
		}
		ocorrencia.setOcorrenciaNome(ocorrenciaForm.getOcorrenciaNome().replaceAll("\\s+"," "));
		ocorrencia.setOcorrenciaDescricao(ocorrenciaForm.getOcorrenciaDescricao().replaceAll("\\s+"," "));

		ocorrencia.setOcorrenciaMotivoOperacao(ocorrenciaForm.getOcorrenciaMotivoOperacao().replaceAll("\\s+"," "));
		ocorrencia.setOcorrenciaStatus(ocorrenciaForm.getOcorrenciaStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(ocorrenciaForm.getOcorrenciaResponsavel()));
		ocorrencia.setColaboradorResponsavel(colaborador);
		
		return ocorrencia;
	}

	@Transactional
	public OcorrenciaForm converteOcorrencia(Ocorrencia ocorrencia) {
	
		OcorrenciaForm ocorrenciaForm = new OcorrenciaForm();
		
		ocorrenciaForm.setOcorrenciaPK(ocorrencia.getOcorrenciaPK());
		ocorrenciaForm.setOcorrenciaNome(ocorrencia.getOcorrenciaNome());
		ocorrenciaForm.setOcorrenciaDescricao(ocorrencia.getOcorrenciaDescricao());

		ocorrenciaForm.setOcorrenciaMotivoOperacao(ocorrencia.getOcorrenciaMotivoOperacao());
		ocorrenciaForm.setOcorrenciaStatus(AtributoStatus.valueOf(ocorrencia.getOcorrenciaStatus()));

		ocorrenciaForm.setOcorrenciaResponsavel(ocorrencia.getColaboradorResponsavel().getPessoaPK()+"");
		
	return ocorrenciaForm;
	}
	
	@Transactional
	public OcorrenciaForm converteOcorrenciaView(Ocorrencia ocorrencia) {
	
		OcorrenciaForm ocorrenciaForm = new OcorrenciaForm();
		
		ocorrenciaForm.setOcorrenciaPK(ocorrencia.getOcorrenciaPK());
		ocorrenciaForm.setOcorrenciaNome(ocorrencia.getOcorrenciaNome());
		ocorrenciaForm.setOcorrenciaDescricao(ocorrencia.getOcorrenciaDescricao());

		ocorrenciaForm.setOcorrenciaMotivoOperacao(ocorrencia.getOcorrenciaMotivoOperacao());
		ocorrenciaForm.setOcorrenciaStatus(AtributoStatus.valueOf(ocorrencia.getOcorrenciaStatus()));

		ocorrenciaForm.setOcorrenciaResponsavel(ocorrencia.getColaboradorResponsavel().getPessoaNome());
		
	return ocorrenciaForm;
	}
	

	@Transactional
	public OcorrenciaForm ocorrenciaParametros(OcorrenciaForm ocorrenciaForm) {
	
		ocorrenciaForm.setOcorrenciaStatus(AtributoStatus.valueOf("ATIVO"));
		
	return ocorrenciaForm;
	}

	@Override
	public List<Ocorrencia> getByOcorrenciaDescricao(String ocorrenciaDescricao,Pageable pageable) {
		return ocorrenciaRepository.findByOcorrenciaDescricao(ocorrenciaDescricao,pageable);
	}

	@Override
	public List<Ocorrencia> getByOcorrenciaNome(String ocorrenciaNome,Pageable pageable) {
		return ocorrenciaRepository.findByOcorrenciaNome(ocorrenciaNome,pageable);
	}

	@Override
	public List<Ocorrencia> getByOcorrenciaDescricao(String ocorrenciaDescricao) {
		return ocorrenciaRepository.findByOcorrenciaDescricao(ocorrenciaDescricao);
	}

	@Override
	public List<Ocorrencia> getByOcorrenciaNome(String ocorrenciaNome) {
		return ocorrenciaRepository.findByOcorrenciaNome(ocorrenciaNome);
	}



}
