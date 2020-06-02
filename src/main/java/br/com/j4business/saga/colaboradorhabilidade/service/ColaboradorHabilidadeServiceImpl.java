package br.com.j4business.saga.colaboradorhabilidade.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.colaboradorfuncao.model.ColaboradorFuncao;
import br.com.j4business.saga.habilidade.model.Habilidade;
import br.com.j4business.saga.habilidade.service.HabilidadeService;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidade;
import br.com.j4business.saga.colaboradorhabilidade.model.ColaboradorHabilidadeForm;
import br.com.j4business.saga.colaboradorhabilidade.repository.ColaboradorHabilidadeRepository;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.service.FornecedorService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("colaboradorHabilidadeService")
public class ColaboradorHabilidadeServiceImpl implements ColaboradorHabilidadeService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	private FornecedorService fornecedorService;


	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ColaboradorHabilidadeRepository colaboradorHabilidadeRepository;

	@Autowired
	private HabilidadeService habilidadeService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(ColaboradorHabilidadeService.class.getName());


	@Override
	public List<ColaboradorHabilidade> getByColaboradorNome(String colaboradorNome,Pageable pageable) {
		return colaboradorHabilidadeRepository.findByColaboradorNome(colaboradorNome,pageable);
	}

	@Override
	public List<ColaboradorHabilidade> getByHabilidadeNome(String habilidadeNome,Pageable pageable) {
		return colaboradorHabilidadeRepository.findByHabilidadeNome(habilidadeNome,pageable);
	}

	@Override
	public List<ColaboradorHabilidade> getByColaboradorNome(String colaboradorNome) {
		return colaboradorHabilidadeRepository.findByColaboradorNome(colaboradorNome);
	}

	@Override
	public List<ColaboradorHabilidade> getByHabilidadeNome(String habilidadeNome) {
		return colaboradorHabilidadeRepository.findByHabilidadeNome(habilidadeNome);
	}

	@Override
	public List<ColaboradorHabilidade> getByHabilidadePK(long habilidadePK,Pageable pageable) {
		return colaboradorHabilidadeRepository.findByHabilidadePK(habilidadePK,pageable);
	}

	@Override
	public List<ColaboradorHabilidade> getByColaboradorPK(long colaboradorPK,Pageable pageable) {
		return colaboradorHabilidadeRepository.findByColaboradorPK(colaboradorPK,pageable);
	}

	@Override
	public List<ColaboradorHabilidade> getColaboradorHabilidadeAll(Pageable pageable) {
		return colaboradorHabilidadeRepository.findColaboradorHabilidadeAll(pageable);
	}

	@Override
	public ColaboradorHabilidade getColaboradorHabilidadeByColaboradorHabilidadePK(long colaboradorHabilidadePK) {
		return colaboradorHabilidadeRepository.findOne(colaboradorHabilidadePK);
	}

	@Override
	public ColaboradorHabilidade getByColaboradorAndHabilidade (Colaborador colaborador,Habilidade habilidade) {
		
		return colaboradorHabilidadeRepository.findByColaboradorAndHabilidade(colaborador,habilidade);
	}

	@Transactional
	public ColaboradorHabilidade save(ColaboradorHabilidadeForm colaboradorHabilidadeForm) {

		ColaboradorHabilidade colaboradorHabilidade = new ColaboradorHabilidade();
		
		colaboradorHabilidade = this.converteColaboradorHabilidadeForm(colaboradorHabilidadeForm);
		
		colaboradorHabilidade = entityManager.merge(colaboradorHabilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorHabilidade Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorHabilidade.getColaboradorHabilidadePK() + 
										" // Colaborador Id => "+colaboradorHabilidade.getColaborador().getPessoaPK() + 
										" // Habilidade Id => "+colaboradorHabilidade.getHabilidade().getHabilidadePK());
		return colaboradorHabilidade;
	}

	@Transactional
	public void delete(Long colaboradorHabilidadePK) {

		ColaboradorHabilidade colaboradorHabilidadeTemp = this.getColaboradorHabilidadeByColaboradorHabilidadePK(colaboradorHabilidadePK);

		colaboradorHabilidadeRepository.delete(colaboradorHabilidadePK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("ColaboradorHabilidade Save " + "\n Usuário => " + username + 
										" // Id => "+colaboradorHabilidadeTemp.getColaboradorHabilidadePK() + 
										" // Colaborador Id => "+colaboradorHabilidadeTemp.getColaborador().getPessoaPK() + 
										" // Habilidade Id => "+colaboradorHabilidadeTemp.getHabilidade().getHabilidadePK()); 
    }

	@Transactional
	public void deleteByHabilidade(Habilidade habilidade) {
		
		List<ColaboradorHabilidade> colaboradorHabilidadeList = colaboradorHabilidadeRepository.findByHabilidade(habilidade);

		colaboradorHabilidadeRepository.delete(colaboradorHabilidadeList);

		String username = usuarioSeguranca.getUsuarioLogado();

		colaboradorHabilidadeList.forEach((ColaboradorHabilidade colaboradorHabilidade) -> {

			logger.info("ColaboradorHabilidade Delete2 " + "\n Usuário => " + username + 
											" // Id => "+colaboradorHabilidade.getColaboradorHabilidadePK() + 
											" // Colaborador Id => "+colaboradorHabilidade.getColaborador().getPessoaPK() + 
											" // Habilidade Id => "+colaboradorHabilidade.getHabilidade().getHabilidadePK()); 
		});
		
    }

	@Transactional
	public ColaboradorHabilidade converteColaboradorHabilidadeForm(ColaboradorHabilidadeForm colaboradorHabilidadeForm) {
		
		ColaboradorHabilidade colaboradorHabilidade = new ColaboradorHabilidade();
		
		if (colaboradorHabilidadeForm.getColaboradorHabilidadePK() > 0) {
			colaboradorHabilidade = this.getColaboradorHabilidadeByColaboradorHabilidadePK(colaboradorHabilidadeForm.getColaboradorHabilidadePK());
		}
		
		
		Habilidade habilidade = habilidadeService.getHabilidadeByHabilidadePK(Long.parseLong(colaboradorHabilidadeForm.getHabilidadeNome()));
		colaboradorHabilidade.setHabilidade(habilidade);

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorHabilidadeForm.getColaboradorNome()));
		colaboradorHabilidade.setColaborador(colaborador);

		Colaborador colaboradorResponsavel = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(colaboradorHabilidadeForm.getColaboradorHabilidadeResponsavel()));
		colaboradorHabilidade.setColaboradorResponsavel(colaboradorResponsavel);

		Fornecedor capacitador = fornecedorService.getFornecedorByFornecedorPK(Long.parseLong(colaboradorHabilidadeForm.getColaboradorHabilidadeCapacitador()));
		colaboradorHabilidade.setColaboradorHabilidadeCapacitador(capacitador);

		colaboradorHabilidade.setColaboradorHabilidadeMotivoOperacao(colaboradorHabilidadeForm.getColaboradorHabilidadeMotivoOperacao().replaceAll("\\s+"," "));
		colaboradorHabilidade.setColaboradorHabilidadeStatus(colaboradorHabilidadeForm.getColaboradorHabilidadeStatus()+"".replaceAll("\\s+"," "));
		
		colaboradorHabilidade.setColaboradorHabilidadeDataInicio(colaboradorHabilidadeForm.getColaboradorHabilidadeDataInicio());
		colaboradorHabilidade.setColaboradorHabilidadeDataTermino(colaboradorHabilidadeForm.getColaboradorHabilidadeDataTermino());
		colaboradorHabilidade.setColaboradorHabilidadeDataValidade(colaboradorHabilidadeForm.getColaboradorHabilidadeDataValidade());
		
		return colaboradorHabilidade;
	}

	@Transactional
	public ColaboradorHabilidadeForm converteColaboradorHabilidade(ColaboradorHabilidade colaboradorHabilidade) {
	
		ColaboradorHabilidadeForm colaboradorHabilidadeForm = new ColaboradorHabilidadeForm();
		
		colaboradorHabilidadeForm.setColaboradorHabilidadePK(colaboradorHabilidade.getColaboradorHabilidadePK());
		colaboradorHabilidadeForm.setColaboradorNome(colaboradorHabilidade.getColaborador().getPessoaPK()+"");
		colaboradorHabilidadeForm.setHabilidadeNome(colaboradorHabilidade.getHabilidade().getHabilidadePK()+"");

		colaboradorHabilidadeForm.setColaboradorHabilidadeResponsavel(colaboradorHabilidade.getColaborador().getPessoaPK()+"");

		colaboradorHabilidadeForm.setColaboradorHabilidadeMotivoOperacao(colaboradorHabilidade.getColaboradorHabilidadeMotivoOperacao());
		colaboradorHabilidadeForm.setColaboradorHabilidadeStatus(AtributoStatus.valueOf(colaboradorHabilidade.getColaboradorHabilidadeStatus()));
		
		colaboradorHabilidadeForm.setColaboradorHabilidadeCapacitador(colaboradorHabilidade.getColaboradorHabilidadeCapacitador().getPessoaPK()+"");
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataInicio(colaboradorHabilidade.getColaboradorHabilidadeDataInicio());
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataTermino(colaboradorHabilidade.getColaboradorHabilidadeDataTermino());
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataValidade(colaboradorHabilidade.getColaboradorHabilidadeDataValidade());
		
	return colaboradorHabilidadeForm;
	}
	
	@Transactional
	public ColaboradorHabilidadeForm converteColaboradorHabilidadeView(ColaboradorHabilidade colaboradorHabilidade) {
	
		ColaboradorHabilidadeForm colaboradorHabilidadeForm = new ColaboradorHabilidadeForm();
		
		colaboradorHabilidadeForm.setColaboradorHabilidadePK(colaboradorHabilidade.getColaboradorHabilidadePK());
		colaboradorHabilidadeForm.setColaboradorNome(colaboradorHabilidade.getColaborador().getPessoaNome());
		colaboradorHabilidadeForm.setHabilidadeNome(colaboradorHabilidade.getHabilidade().getHabilidadeNome());

		colaboradorHabilidadeForm.setColaboradorHabilidadeMotivoOperacao(colaboradorHabilidade.getColaboradorHabilidadeMotivoOperacao());
		colaboradorHabilidadeForm.setColaboradorHabilidadeStatus(AtributoStatus.valueOf(colaboradorHabilidade.getColaboradorHabilidadeStatus()));

		colaboradorHabilidadeForm.setColaboradorHabilidadeResponsavel(colaboradorHabilidade.getColaborador().getPessoaNome());

		colaboradorHabilidadeForm.setColaboradorHabilidadeCapacitador(colaboradorHabilidade.getColaboradorHabilidadeCapacitador().getPessoaNome());
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataInicio(colaboradorHabilidade.getColaboradorHabilidadeDataInicio());
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataTermino(colaboradorHabilidade.getColaboradorHabilidadeDataTermino());
		colaboradorHabilidadeForm.setColaboradorHabilidadeDataValidade(colaboradorHabilidade.getColaboradorHabilidadeDataValidade());
		
	return colaboradorHabilidadeForm;
	}
	

	@Transactional
	public ColaboradorHabilidadeForm colaboradorHabilidadeParametros(ColaboradorHabilidadeForm colaboradorHabilidadeForm) {
	
		
		colaboradorHabilidadeForm.setColaboradorHabilidadeStatus(AtributoStatus.valueOf("ATIVO"));

		
	return colaboradorHabilidadeForm;
	}
	
	@Transactional
	public ColaboradorHabilidade create(ColaboradorHabilidadeForm colaboradorHabilidadeForm) {
		
		ColaboradorHabilidade colaboradorHabilidade = new ColaboradorHabilidade();
		
		colaboradorHabilidade = this.converteColaboradorHabilidadeForm(colaboradorHabilidadeForm);
		
		colaboradorHabilidade = entityManager.merge(colaboradorHabilidade);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Habilidade Create " + "\n Usuário => " + username + 
				" // Id => "+colaboradorHabilidade.getColaboradorHabilidadePK() + 
				" // Colaborador Id => "+colaboradorHabilidade.getColaborador().getPessoaPK() + 
				" // Habilidade Id => "+colaboradorHabilidade.getHabilidade().getHabilidadePK()); 
		
		return colaboradorHabilidade;
	}


}
