package br.com.j4business.saga.processocurso.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.processocertificacao.model.ProcessoCertificacao;
import br.com.j4business.saga.processocurso.model.ProcessoCurso;
import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.curso.service.CursoService;
import br.com.j4business.saga.processocurso.model.ProcessoCursoForm;
import br.com.j4business.saga.processocurso.repository.ProcessoCursoRepository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("processoCursoService")
public class ProcessoCursoServiceImpl implements ProcessoCursoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProcessoCursoRepository processoCursoRepository;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ProcessoCursoService.class.getName());


	@Override
	public List<ProcessoCurso> getByProcessoNome(String processoNome,Pageable pageable) {
		return processoCursoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<ProcessoCurso> getByCursoNome(String cursoNome,Pageable pageable) {
		return processoCursoRepository.findByCursoNome(cursoNome,pageable);
	}

	@Override
	public List<ProcessoCurso> getByProcessoNome(String processoNome) {
		return processoCursoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<ProcessoCurso> getByCursoNome(String cursoNome) {
		return processoCursoRepository.findByCursoNome(cursoNome);
	}

	@Override
	public List<ProcessoCurso> getByCursoPK(long cursoPK,Pageable pageable) {
		return processoCursoRepository.findByCursoPK(cursoPK,pageable);
	}

	@Override
	public List<ProcessoCurso> getByProcessoPK(long processoPK,Pageable pageable) {
		return processoCursoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<ProcessoCurso> getProcessoCursoAll(Pageable pageable) {
		return processoCursoRepository.findProcessoCursoAll(pageable);
	}

	@Override
	public ProcessoCurso getProcessoCursoByProcessoCursoPK(long processoCursoPK) {
		return processoCursoRepository.findOne(processoCursoPK);
	}

	@Override
	public ProcessoCurso getByProcessoAndCurso (Processo processo,Curso curso) {
		
		return processoCursoRepository.findByProcessoAndCurso(processo,curso);
	}

	@Transactional
	public ProcessoCurso create(ProcessoCursoForm processoCursoForm) {
		
		ProcessoCurso processoCurso = new ProcessoCurso();
		
		processoCurso = this.converteProcessoCursoForm(processoCursoForm);
		
		processoCurso = entityManager.merge(processoCurso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Curso Create " + "\n Usuário => " + username + 
				" // Id => "+processoCurso.getProcessoCursoPK() + 
				" // Processo Id => "+processoCurso.getProcesso().getProcessoPK() + 
				" // Curso Id => "+processoCurso.getCurso().getCursoPK()); 
		
		return processoCurso;
	}

	@Transactional
	public ProcessoCurso save(ProcessoCursoForm processoCursoForm) {

		ProcessoCurso processoCurso = new ProcessoCurso();
		
		processoCurso = this.converteProcessoCursoForm(processoCursoForm);
		
		processoCurso = entityManager.merge(processoCurso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoCurso Save " + "\n Usuário => " + username + 
										" // Id => "+processoCurso.getProcessoCursoPK() + 
										" // Processo Id => "+processoCurso.getProcesso().getProcessoPK() + 
										" // Curso Id => "+processoCurso.getCurso().getCursoPK());
		return processoCurso;
	}

	@Transactional
	public void delete(Long processoCursoPK) {

		ProcessoCurso processoCursoTemp = this.getProcessoCursoByProcessoCursoPK(processoCursoPK);

		processoCursoRepository.delete(processoCursoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ProcessoCurso Save " + "\n Usuário => " + username + 
										" // Id => "+processoCursoTemp.getProcessoCursoPK() + 
										" // Processo Id => "+processoCursoTemp.getProcesso().getProcessoPK() + 
										" // Curso Id => "+processoCursoTemp.getCurso().getCursoPK()); 
    }

	@Transactional
	public void deleteByCurso(Curso curso) {
		
		List<ProcessoCurso> processoCursoList = processoCursoRepository.findByCurso(curso);

		processoCursoRepository.delete(processoCursoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		processoCursoList.forEach((ProcessoCurso processoCurso) -> {
			
			logger.info("ProcessoCurso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+processoCurso.getProcessoCursoPK() + 
											" // Processo Id => "+processoCurso.getProcesso().getProcessoPK() + 
											" // Curso Id => "+processoCurso.getCurso().getCursoPK()); 
		});
    }

	@Transactional
	public ProcessoCurso converteProcessoCursoForm(ProcessoCursoForm processoCursoForm) {
		
		ProcessoCurso processoCurso = new ProcessoCurso();
		
		if (processoCursoForm.getProcessoCursoPK() > 0) {
			processoCurso = this.getProcessoCursoByProcessoCursoPK(processoCursoForm.getProcessoCursoPK());
		}
		
		
		Curso curso = cursoService.getCursoByCursoPK(Long.parseLong(processoCursoForm.getCursoNome()));
		processoCurso.setCurso(curso);

		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(processoCursoForm.getProcessoNome()));
		processoCurso.setProcesso(processo);

		processoCurso.setProcessoCursoMotivoOperacao(processoCursoForm.getProcessoCursoMotivoOperacao().replaceAll("\\s+"," "));
		processoCurso.setProcessoCursoStatus(processoCursoForm.getProcessoCursoStatus()+"".replaceAll("\\s+"," "));
		
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(processoCursoForm.getProcessoCursoResponsavel()));
		processoCurso.setColaboradorResponsavel(colaborador);

		return processoCurso;
	}

	@Transactional
	public ProcessoCursoForm converteProcessoCurso(ProcessoCurso processoCurso) {
	
		ProcessoCursoForm processoCursoForm = new ProcessoCursoForm();
		
		processoCursoForm.setProcessoCursoPK(processoCurso.getProcessoCursoPK());
		processoCursoForm.setProcessoNome(processoCurso.getProcesso().getProcessoPK()+"");
		processoCursoForm.setCursoNome(processoCurso.getCurso().getCursoPK()+"");

		processoCursoForm.setProcessoCursoMotivoOperacao(processoCurso.getProcessoCursoMotivoOperacao());
		processoCursoForm.setProcessoCursoStatus(AtributoStatus.valueOf(processoCurso.getProcessoCursoStatus()));
		processoCursoForm.setProcessoCursoResponsavel(processoCurso.getProcesso().getProcessoPK()+"");
		
	return processoCursoForm;
	}
	
	@Transactional
	public ProcessoCursoForm converteProcessoCursoView(ProcessoCurso processoCurso) {
	
		ProcessoCursoForm processoCursoForm = new ProcessoCursoForm();
		
		processoCursoForm.setProcessoCursoPK(processoCurso.getProcessoCursoPK());
		processoCursoForm.setProcessoNome(processoCurso.getProcesso().getProcessoNome());
		processoCursoForm.setCursoNome(processoCurso.getCurso().getCursoNome());

		processoCursoForm.setProcessoCursoMotivoOperacao(processoCurso.getProcessoCursoMotivoOperacao());
		processoCursoForm.setProcessoCursoStatus(AtributoStatus.valueOf(processoCurso.getProcessoCursoStatus()));
		processoCursoForm.setProcessoCursoResponsavel(processoCurso.getProcesso().getProcessoNome());
		
	return processoCursoForm;
	}
	

	@Transactional
	public ProcessoCursoForm processoCursoParametros(ProcessoCursoForm processoCursoForm) {
		processoCursoForm.setProcessoCursoStatus(AtributoStatus.valueOf("ATIVO"));
	return processoCursoForm;
	}
	
}
