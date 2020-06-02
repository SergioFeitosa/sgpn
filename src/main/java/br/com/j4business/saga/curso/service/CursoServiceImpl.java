package br.com.j4business.saga.curso.service;

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
import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.curso.model.CursoForm;
import br.com.j4business.saga.curso.repository.CursoRepository;

@Service("cursoService")
public class CursoServiceImpl implements CursoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(CursoService.class.getName());

	@Override
	public List<Curso> getCursoAll() {
		return cursoRepository.findAll();
	}

	@Override
	public Page<Curso> getCursoAll(Pageable pageable) {
		return cursoRepository.findAll(pageable);
	}

	@Override
	public Curso getCursoByCursoPK(long cursoPK) {
		
		return cursoRepository.findOne(cursoPK);
	}

	@Transactional
	public Curso create(CursoForm cursoForm) {
		
		Curso curso = new Curso();
		
		curso = this.converteCursoForm(cursoForm);
		
		curso = entityManager.merge(curso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Curso Create " + "\n Usuário => " + username + 
										" // Id => "+curso.getCursoPK() + 
										" // Curso => "+curso.getCursoNome() + 
										" // Descrição => "+ curso.getCursoDescricao());
		
		return curso;
	}

	@Transactional
	public Curso save(CursoForm cursoForm) {
		
		Curso curso = new Curso();
		
		curso = this.converteCursoForm(cursoForm);
		
		curso = entityManager.merge(curso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Curso Save " + "\n Usuário => " + username + 
										" // Id => "+curso.getCursoPK() + 
										" // Curso => "+curso.getCursoNome() + 
										" // Descrição => "+ curso.getCursoDescricao());
		

		return curso;
		
	}

	@Transactional
	public void delete(Long cursoId) {

		Curso curso = this.getCursoByCursoPK(cursoId);
		
		cursoRepository.delete(curso.getCursoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Curso Delete " + "\n Usuário => " + username + 
										" // Id => "+curso.getCursoPK() + 
										" // Curso => "+curso.getCursoNome() + 
										" // Descrição => "+ curso.getCursoDescricao());
		

    }

	@Transactional
	public Curso converteCursoForm(CursoForm cursoForm) {
		
		Curso curso = new Curso();
		
		if (cursoForm.getCursoPK() > 0) {
			curso = this.getCursoByCursoPK(cursoForm.getCursoPK());
		}

		curso.setCursoNome(cursoForm.getCursoNome().replaceAll("\\s+"," "));
		curso.setCursoDescricao(cursoForm.getCursoDescricao().replaceAll("\\s+"," "));

		curso.setCursoMotivoOperacao(cursoForm.getCursoMotivoOperacao().replaceAll("\\s+"," "));
		curso.setCursoStatus(cursoForm.getCursoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(cursoForm.getCursoResponsavel()));
		curso.setColaboradorResponsavel(colaborador);

		return curso;
	}

	@Transactional
	public CursoForm converteCurso(Curso curso) {
	
		CursoForm cursoForm = new CursoForm();
		
		cursoForm.setCursoPK(curso.getCursoPK());
		cursoForm.setCursoNome(curso.getCursoNome());
		cursoForm.setCursoDescricao(curso.getCursoDescricao());

		cursoForm.setCursoMotivoOperacao(curso.getCursoMotivoOperacao());
		cursoForm.setCursoStatus(AtributoStatus.valueOf(curso.getCursoStatus()));

		cursoForm.setCursoResponsavel(curso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return cursoForm;
	}
	
	@Transactional
	public CursoForm converteCursoView(Curso curso) {
	
		CursoForm cursoForm = new CursoForm();
		
		cursoForm.setCursoPK(curso.getCursoPK());
		cursoForm.setCursoNome(curso.getCursoNome());
		cursoForm.setCursoDescricao(curso.getCursoDescricao());

		cursoForm.setCursoMotivoOperacao(curso.getCursoMotivoOperacao());
		cursoForm.setCursoStatus(AtributoStatus.valueOf(curso.getCursoStatus()));

		cursoForm.setCursoResponsavel(curso.getColaboradorResponsavel().getPessoaNome());
		
	return cursoForm;
	}
	

	@Transactional
	public CursoForm cursoParametros(CursoForm cursoForm) {
	
		
		cursoForm.setCursoStatus(AtributoStatus.valueOf("ATIVO"));

		
	return cursoForm;
	}

	@Override
	public List<Curso> getByCursoDescricao(String cursoDescricao,Pageable pageable) {
		return cursoRepository.findByCursoDescricao(cursoDescricao,pageable);
	}

	@Override
	public List<Curso> getByCursoNome(String cursoNome,Pageable pageable) {
		return cursoRepository.findByCursoNome(cursoNome,pageable);
	}

	@Override
	public List<Curso> getByCursoDescricao(String cursoDescricao) {
		return cursoRepository.findByCursoDescricao(cursoDescricao);
	}

	@Override
	public List<Curso> getByCursoNome(String cursoNome) {
		return cursoRepository.findByCursoNome(cursoNome);
	}



}
