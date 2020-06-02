package br.com.j4business.saga.resultado.service;

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
import br.com.j4business.saga.resultado.model.Resultado;
import br.com.j4business.saga.resultado.model.ResultadoForm;
import br.com.j4business.saga.resultado.repository.ResultadoRepository;

@Service("resultadoService")
public class ResultadoServiceImpl implements ResultadoService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ResultadoRepository resultadoRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ResultadoService.class.getName());

	@Override
	public List<Resultado> getResultadoAll() {
		return resultadoRepository.findAll();
	}

	@Override
	public Page<Resultado> getResultadoAll(Pageable pageable) {
		return resultadoRepository.findAll(pageable);
	}

	@Override
	public Resultado getResultadoByResultadoPK(long resultadoPK) {
		
		return resultadoRepository.findOne(resultadoPK);
	}

	@Transactional
	public Resultado create(ResultadoForm resultadoForm) {
		
		Resultado resultado = new Resultado();
		
		resultado = this.converteResultadoForm(resultadoForm);
		
		resultado = entityManager.merge(resultado);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Resultado Create " + "\n Usuário => " + username + 
										" // Id => "+resultado.getResultadoPK() + 
										" // Resultado => "+resultado.getResultadoNome() + 
										" // Descrição => "+ resultado.getResultadoDescricao());
		
		return resultado;
	}

	@Transactional
	public Resultado save(ResultadoForm resultadoForm) {
		
		Resultado resultado = new Resultado();
		
		resultado = this.converteResultadoForm(resultadoForm);
		
		resultado = entityManager.merge(resultado);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Resultado Save " + "\n Usuário => " + username + 
										" // Id => "+resultado.getResultadoPK() + 
										" // Resultado => "+resultado.getResultadoNome() + 
										" // Descrição => "+ resultado.getResultadoDescricao());
		

		return resultado;
		
	}

	@Transactional
	public void delete(Long resultadoId) {

		Resultado resultado = this.getResultadoByResultadoPK(resultadoId);
		
		resultadoRepository.delete(resultado.getResultadoPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Resultado Delete " + "\n Usuário => " + username + 
										" // Id => "+resultado.getResultadoPK() + 
										" // Resultado => "+resultado.getResultadoNome() + 
										" // Descrição => "+ resultado.getResultadoDescricao());
		

    }

	@Transactional
	public Resultado converteResultadoForm(ResultadoForm resultadoForm) {
		
		Resultado resultado = new Resultado();
		
		if (resultadoForm.getResultadoPK() > 0) {
			resultado = this.getResultadoByResultadoPK(resultadoForm.getResultadoPK());
		}
		resultado.setResultadoNome(resultadoForm.getResultadoNome().replaceAll("\\s+"," "));
		resultado.setResultadoDescricao(resultadoForm.getResultadoDescricao().replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(resultadoForm.getResultadoResponsavel()));

		resultado.setColaboradorResponsavel(colaborador);

		resultado.setResultadoStatus(resultadoForm.getResultadoStatus()+"");
		resultado.setResultadoMotivoOperacao(resultadoForm.getResultadoMotivoOperacao());
		return resultado;
	}

	@Transactional
	public ResultadoForm converteResultado(Resultado resultado) {
	
		ResultadoForm resultadoForm = new ResultadoForm();
		
		resultadoForm.setResultadoPK(resultado.getResultadoPK());
		resultadoForm.setResultadoNome(resultado.getResultadoNome());
		resultadoForm.setResultadoDescricao(resultado.getResultadoDescricao());

		resultadoForm.setResultadoResponsavel(resultado.getColaboradorResponsavel().getPessoaPK()+"");

		resultadoForm.setResultadoStatus(AtributoStatus.valueOf(resultado.getResultadoStatus()));
		resultadoForm.setResultadoMotivoOperacao(resultado.getResultadoMotivoOperacao());

	return resultadoForm;
	}
	
	@Transactional
	public ResultadoForm converteResultadoView(Resultado resultado) {
	
		ResultadoForm resultadoForm = new ResultadoForm();
		
		resultadoForm.setResultadoPK(resultado.getResultadoPK());
		resultadoForm.setResultadoNome(resultado.getResultadoNome());
		resultadoForm.setResultadoDescricao(resultado.getResultadoDescricao());

		resultadoForm.setResultadoResponsavel(resultado.getColaboradorResponsavel().getPessoaNome());
		resultadoForm.setResultadoStatus(AtributoStatus.valueOf(resultado.getResultadoStatus()));
		resultadoForm.setResultadoMotivoOperacao(resultado.getResultadoMotivoOperacao());
		
	return resultadoForm;
	}
	

	@Transactional
	public ResultadoForm resultadoParametros(ResultadoForm resultadoForm) {
		resultadoForm.setResultadoStatus(AtributoStatus.valueOf("ATIVO"));
	return resultadoForm;
	}

	@Override
	public List<Resultado> getByResultadoDescricao(String resultadoDescricao,Pageable pageable) {
		return resultadoRepository.findByResultadoDescricao(resultadoDescricao,pageable);
	}

	@Override
	public List<Resultado> getByResultadoNome(String resultadoNome,Pageable pageable) {
		return resultadoRepository.findByResultadoNome(resultadoNome,pageable);
	}

	@Override
	public List<Resultado> getByResultadoDescricao(String resultadoDescricao) {
		return resultadoRepository.findByResultadoDescricao(resultadoDescricao);
	}

	@Override
	public List<Resultado> getByResultadoNome(String resultadoNome) {
		return resultadoRepository.findByResultadoNome(resultadoNome);
	}

	@Override
	public Resultado getByResultadoNomeCompleto(String resultadoNome) {
		return resultadoRepository.findByResultadoNomeCompleto(resultadoNome);
	}



}
