package br.com.j4business.saga.colaboradorcurso.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorcertificacao.model.ColaboradorCertificacao;
import br.com.j4business.saga.curso.model.Curso;
import br.com.j4business.saga.curso.service.CursoService;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCurso;
import br.com.j4business.saga.colaboradorcurso.model.ColaboradorCursoForm;
import br.com.j4business.saga.colaboradorcurso.repository.ColaboradorCursoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorCursoService")
public class ColaboradorCursoServiceImpl implements ColaboradorCursoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorCursoRepository colaboradorCursoRepository;

	@Autowired
	private CursoService cursoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorCursoService.class.getName());


	@Override
	public List<ColaboradorCurso> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorCursoRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorCurso> getByCursoNome(String cursoNome,Pageable pageable) {
		return colaboradorCursoRepository.findByCursoNome(cursoNome,pageable);
	}

	@Override
	public List<ColaboradorCurso> getByColaboradorNome(String colaboradorNome) {
		return colaboradorCursoRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorCurso> getByCursoNome(String cursoNome) {
		return colaboradorCursoRepository.findByCursoNome(cursoNome);
	}

	@Override
	public List<ColaboradorCurso> getByCursoPK(long cursoPK,Pageable pageable) {
		return colaboradorCursoRepository.findByCursoPK(cursoPK,pageable);
	}

	@Override
	public List<ColaboradorCurso> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorCursoRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorCurso> getColaboradorCursoAll(Pageable pageable) {
		return colaboradorCursoRepository.findColaboradorCursoAll(pageable);
	}

	@Override
	public ColaboradorCurso getColaboradorCursoByColaboradorCursoPK(long colaboradorCursoPK) {
		return colaboradorCursoRepository.findOne(colaboradorCursoPK);
	}

	@Override
	public ColaboradorCurso getByColaboradorAndCurso (Colaborador colaborador,Curso curso) {
		
		return colaboradorCursoRepository.findByColaboradorAndCurso(colaborador,curso);
	}

	@Transactional
	public ColaboradorCurso save(ColaboradorCursoForm colaboradorCursoForm) {

		ColaboradorCurso colaboradorCurso = new ColaboradorCurso();
		
		colaboradorCurso = this.converteColaboradorCursoForm(colaboradorCursoForm);
		
		colaboradorCurso = entityManager.merge(colaboradorCurso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorCurso Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorCurso.getColaboradorCursoPK() + 
										" // Colaborador Id => "+colaboradorCurso.getColaborador().getPessoaPK() + 
										" // Curso Id => "+colaboradorCurso.getCurso().getCursoPK());
		return colaboradorCurso;
	}

	@Transactional
	public void delete(Long colaboradorCursoPK) {

		ColaboradorCurso colaboradorCursoTemp = this.getColaboradorCursoByColaboradorCursoPK(colaboradorCursoPK);

		colaboradorCursoRepository.delete(colaboradorCursoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorCurso Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorCursoTemp.getColaboradorCursoPK() + 
										" // Colaborador Id => "+colaboradorCursoTemp.getColaborador().getPessoaPK() + 
										" // Curso Id => "+colaboradorCursoTemp.getCurso().getCursoPK()); 
    }

	@Transactional
	public void deleteByCurso(Curso curso) {
		
		List<ColaboradorCurso> colaboradorCursoList = colaboradorCursoRepository.findByCurso(curso);

		colaboradorCursoRepository.delete(colaboradorCursoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorCursoList.forEach((ColaboradorCurso colaboradorCurso) -> {

			logger.info("ColaboradorCurso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorCurso.getColaboradorCursoPK() + 
											" // Colaborador Id => "+colaboradorCurso.getColaborador().getPessoaPK() + 
											" // Curso Id => "+colaboradorCurso.getCurso().getCursoPK()); 
		});
		
    }

	@Transactional
	public ColaboradorCurso converteColaboradorCursoForm(ColaboradorCursoForm colaboradorCursoForm) {
		
		ColaboradorCurso colaboradorCurso = new ColaboradorCurso();
		
		if (colaboradorCursoForm.getColaboradorCursoPK() > 0) {
			colaboradorCurso = this.getColaboradorCursoByColaboradorCursoPK(colaboradorCursoForm.getColaboradorCursoPK());
		}
		
		Curso curso = cursoService.getCursoByCursoPK(Long.parseLong(colaboradorCursoForm.getCursoNome()));
		colaboradorCurso.setCurso(curso);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorCursoForm.getColaboradorNome()));
		colaboradorCurso.setColaborador(colaborador);

		colaboradorCurso.setColaboradorCursoMotivoOperacao(colaboradorCursoForm.getColaboradorCursoMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorCurso.setColaboradorCursoStatus(colaboradorCursoForm.getColaboradorCursoStatus()+"".replaceAll("\\s+"," "));
		

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorCursoForm.getColaboradorCursoResponsavel()));
		colaboradorCurso.setColaboradorResponsavel(colaboradorResponsavel);
		
		Fornecedor capacitador = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(colaboradorCursoForm.getCapacitador()));
		colaboradorCurso.setCapacitador(capacitador);
		
		colaboradorCurso.setColaboradorcursoDataInicio(colaboradorCursoForm.getColaboradorCursoDataInicio());
		colaboradorCurso.setColaboradorcursoDataTermino(colaboradorCursoForm.getColaboradorCursoDataTermino());
		colaboradorCurso.setColaboradorcursoDataValidade(colaboradorCursoForm.getColaboradorCursoDataValidade());
		
		return colaboradorCurso;
	}

	@Transactional
	public ColaboradorCursoForm converteColaboradorCurso(ColaboradorCurso colaboradorCurso) {
	
		ColaboradorCursoForm colaboradorCursoForm = new ColaboradorCursoForm();
		
		colaboradorCursoForm.setColaboradorCursoPK(colaboradorCurso.getColaboradorCursoPK());
		colaboradorCursoForm.setColaboradorNome(colaboradorCurso.getColaborador().getPessoaPK()+"");
		colaboradorCursoForm.setCursoNome(colaboradorCurso.getCurso().getCursoPK()+"");

		colaboradorCursoForm.setColaboradorCursoMotivoOperacao(colaboradorCurso.getColaboradorCursoMotivoOperacao());
		colaboradorCursoForm.setColaboradorCursoStatus(AtributoStatus.valueOf(colaboradorCurso.getColaboradorCursoStatus()));

		colaboradorCursoForm.setColaboradorCursoResponsavel(colaboradorCurso.getColaborador().getPessoaPK()+"");
		
		colaboradorCursoForm.setCapacitador(colaboradorCurso.getCapacitador().getPessoaPK()+"");
		colaboradorCursoForm.setColaboradorCursoDataInicio(colaboradorCurso.getColaboradorcursoDataInicio());
		colaboradorCursoForm.setColaboradorCursoDataTermino(colaboradorCurso.getColaboradorcursoDataTermino());
		colaboradorCursoForm.setColaboradorCursoDataValidade(colaboradorCurso.getColaboradorcursoDataValidade());

	return colaboradorCursoForm;
	}
	
	@Transactional
	public ColaboradorCursoForm converteColaboradorCursoView(ColaboradorCurso colaboradorCurso) {
	
		ColaboradorCursoForm colaboradorCursoForm = new ColaboradorCursoForm();
		
		colaboradorCursoForm.setColaboradorCursoPK(colaboradorCurso.getColaboradorCursoPK());
		colaboradorCursoForm.setColaboradorNome(colaboradorCurso.getColaborador().getPessoaNome());
		colaboradorCursoForm.setCursoNome(colaboradorCurso.getCurso().getCursoNome());

		colaboradorCursoForm.setColaboradorCursoMotivoOperacao(colaboradorCurso.getColaboradorCursoMotivoOperacao());
		colaboradorCursoForm.setColaboradorCursoStatus(AtributoStatus.valueOf(colaboradorCurso.getColaboradorCursoStatus()));

		colaboradorCursoForm.setColaboradorCursoResponsavel(colaboradorCurso.getColaborador().getPessoaNome());

		colaboradorCursoForm.setCapacitador(colaboradorCurso.getCapacitador().getPessoaNome());
		colaboradorCursoForm.setColaboradorCursoDataInicio(colaboradorCurso.getColaboradorcursoDataInicio());
		colaboradorCursoForm.setColaboradorCursoDataTermino(colaboradorCurso.getColaboradorcursoDataTermino());
		colaboradorCursoForm.setColaboradorCursoDataValidade(colaboradorCurso.getColaboradorcursoDataValidade());

	return colaboradorCursoForm;
	}
	

	@Transactional
	public ColaboradorCursoForm colaboradorCursoParametros(ColaboradorCursoForm colaboradorCursoForm) {
		colaboradorCursoForm.setColaboradorCursoStatus(AtributoStatus.valueOf("ATIVO"));
	return colaboradorCursoForm;
	}
	
	@Transactional
	public ColaboradorCurso create(ColaboradorCursoForm colaboradorCursoForm) {
		
		ColaboradorCurso colaboradorCurso = new ColaboradorCurso();
		
		colaboradorCurso = this.converteColaboradorCursoForm(colaboradorCursoForm);
		
		colaboradorCurso = entityManager.merge(colaboradorCurso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Curso Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorCurso.getColaboradorCursoPK() + 
				" // Colaborador Id => "+colaboradorCurso.getColaborador().getPessoaPK() + 
				" // Curso Id => "+colaboradorCurso.getCurso().getCursoPK()); 
		
		return colaboradorCurso;
	}


}
