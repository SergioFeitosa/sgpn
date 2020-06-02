package br.com.j4business.saga.empresaprocesso.service;

import br.com.j4business.saga.UsuarioSeguranca;
import br.com.j4business.saga.atributo.enumeration.AtributoStatus;
import br.com.j4business.saga.colaborador.model.Colaborador;
import br.com.j4business.saga.colaborador.service.ColaboradorService;
import br.com.j4business.saga.email.Mensagem;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processo.service.ProcessoService;
import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresa.service.EmpresaService;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcesso;
import br.com.j4business.saga.empresaprocesso.model.EmpresaProcessoForm;
import br.com.j4business.saga.empresaprocesso.repository.EmpresaProcessoRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("empresaProcessoService")
public class EmpresaProcessoServiceImpl implements EmpresaProcessoService {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EmpresaProcessoRepository empresaProcessoRepository;

	@Autowired
	private ProcessoService processoService;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EmpresaProcessoService.class.getName());


	@Override
	public List<EmpresaProcesso> getByEmpresaNome(String empresaNome,Pageable pageable) {
		return empresaProcessoRepository.findByEmpresaNome(empresaNome,pageable);
	}

	@Override
	public List<EmpresaProcesso> getByProcessoNome(String processoNome,Pageable pageable) {
		return empresaProcessoRepository.findByProcessoNome(processoNome,pageable);
	}

	@Override
	public List<EmpresaProcesso> getByEmpresaNome(String empresaNome) {
		return empresaProcessoRepository.findByEmpresaNome(empresaNome);
	}

	@Override
	public List<EmpresaProcesso> getByProcessoNome(String processoNome) {
		return empresaProcessoRepository.findByProcessoNome(processoNome);
	}

	@Override
	public List<EmpresaProcesso> getByProcessoPK(long processoPK,Pageable pageable) {
		return empresaProcessoRepository.findByProcessoPK(processoPK,pageable);
	}

	@Override
	public List<EmpresaProcesso> getByEmpresaPK(long empresaPK,Pageable pageable) {
		return empresaProcessoRepository.findByEmpresaPK(empresaPK,pageable);
	}

	@Override
	public List<EmpresaProcesso> getEmpresaProcessoAll(Pageable pageable) {
		return empresaProcessoRepository.findEmpresaProcessoAll(pageable);
	}

	@Override
	public EmpresaProcesso getEmpresaProcessoByEmpresaProcessoPK(long empresaProcessoPK) {
		return empresaProcessoRepository.findOne(empresaProcessoPK);
	}

	@Override
	public EmpresaProcesso getByEmpresaAndProcesso (Empresa empresa,Processo processo) {
		
		return empresaProcessoRepository.findByEmpresaAndProcesso(empresa,processo);
	}

	@Transactional
	public EmpresaProcesso save(EmpresaProcessoForm empresaProcessoForm) {

		EmpresaProcesso empresaProcesso = new EmpresaProcesso();
		
		empresaProcesso = this.converteEmpresaProcessoForm(empresaProcessoForm);
		
		empresaProcesso = entityManager.merge(empresaProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EmpresaProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+empresaProcesso.getEmpresaProcessoPK() + 
										" // Empresa Id => "+empresaProcesso.getEmpresa().getPessoaPK() + 
										" // Processo Id => "+empresaProcesso.getProcesso().getProcessoPK());
		return empresaProcesso;
	}

	@Transactional
	public void delete(Long empresaProcessoPK) {

		EmpresaProcesso empresaProcessoTemp = this.getEmpresaProcessoByEmpresaProcessoPK(empresaProcessoPK);

		empresaProcessoRepository.delete(empresaProcessoPK);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("EmpresaProcesso Save " + "\n Usuário => " + username + 
										" // Id => "+empresaProcessoTemp.getEmpresaProcessoPK() + 
										" // Empresa Id => "+empresaProcessoTemp.getEmpresa().getPessoaPK() + 
										" // Processo Id => "+empresaProcessoTemp.getProcesso().getProcessoPK()); 
    }

	@Transactional
	public void deleteByProcesso(Processo processo) {
		
		List<EmpresaProcesso> empresaProcessoList = empresaProcessoRepository.findByProcesso(processo);

		empresaProcessoRepository.delete(empresaProcessoList);

		String username = usuarioSeguranca.getUsuarioLogado();

		empresaProcessoList.forEach((EmpresaProcesso empresaProcesso) -> {
						
			logger.info("EmpresaProcesso Delete2 " + "\n Usuário => " + username + 
											" // Id => "+empresaProcesso.getEmpresaProcessoPK() + 
											" // Empresa Id => "+empresaProcesso.getEmpresa().getPessoaPK() + 
											" // Processo Id => "+empresaProcesso.getProcesso().getProcessoPK()); 

		});
		
    }

	@Transactional
	public EmpresaProcesso converteEmpresaProcessoForm(EmpresaProcessoForm empresaProcessoForm) {
		
		EmpresaProcesso empresaProcesso = new EmpresaProcesso();
		
		if (empresaProcessoForm.getEmpresaProcessoPK() > 0) {
			empresaProcesso = this.getEmpresaProcessoByEmpresaProcessoPK(empresaProcessoForm.getEmpresaProcessoPK());
		}
		
		Processo processo = processoService.getProcessoByProcessoPK(Long.parseLong(empresaProcessoForm.getProcessoNome()));
		empresaProcesso.setProcesso(processo);

		Empresa empresa = empresaService.getEmpresaByEmpresaPK(Long.parseLong(empresaProcessoForm.getEmpresaNome()));
		empresaProcesso.setEmpresa(empresa);

		empresaProcesso.setEmpresaProcessoMotivoOperacao(empresaProcessoForm.getEmpresaProcessoMotivoOperacao().replaceAll("\\s+"," "));
		empresaProcesso.setEmpresaProcessoStatus(empresaProcessoForm.getEmpresaProcessoStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(empresaProcessoForm.getEmpresaProcessoResponsavel()));
		empresaProcesso.setColaboradorResponsavel(colaborador);
		
		return empresaProcesso;
	}

	@Transactional
	public EmpresaProcessoForm converteEmpresaProcesso(EmpresaProcesso empresaProcesso) {
	
		EmpresaProcessoForm empresaProcessoForm = new EmpresaProcessoForm();
		
		empresaProcessoForm.setEmpresaProcessoPK(empresaProcesso.getEmpresaProcessoPK());
		empresaProcessoForm.setEmpresaNome(empresaProcesso.getEmpresa().getPessoaPK()+"");
		empresaProcessoForm.setProcessoNome(empresaProcesso.getProcesso().getProcessoPK()+"");

		empresaProcessoForm.setEmpresaProcessoMotivoOperacao(empresaProcesso.getEmpresaProcessoMotivoOperacao());
		empresaProcessoForm.setEmpresaProcessoStatus(AtributoStatus.valueOf(empresaProcesso.getEmpresaProcessoStatus()));

		empresaProcessoForm.setEmpresaProcessoResponsavel(empresaProcesso.getColaboradorResponsavel().getPessoaPK()+"");
		
	return empresaProcessoForm;
	}
	
	@Transactional
	public EmpresaProcessoForm converteEmpresaProcessoView(EmpresaProcesso empresaProcesso) {
	
		EmpresaProcessoForm empresaProcessoForm = new EmpresaProcessoForm();
		
		empresaProcessoForm.setEmpresaProcessoPK(empresaProcesso.getEmpresaProcessoPK());
		empresaProcessoForm.setEmpresaNome(empresaProcesso.getEmpresa().getPessoaNome());
		empresaProcessoForm.setProcessoNome(empresaProcesso.getProcesso().getProcessoNome());

		empresaProcessoForm.setEmpresaProcessoMotivoOperacao(empresaProcesso.getEmpresaProcessoMotivoOperacao());
		empresaProcessoForm.setEmpresaProcessoStatus(AtributoStatus.valueOf(empresaProcesso.getEmpresaProcessoStatus()));

		empresaProcessoForm.setEmpresaProcessoResponsavel(empresaProcesso.getColaboradorResponsavel().getPessoaNome());
		
	return empresaProcessoForm;
	}
	

	@Transactional
	public EmpresaProcessoForm empresaProcessoParametros(EmpresaProcessoForm empresaProcessoForm) {
	
		empresaProcessoForm.setEmpresaProcessoStatus(AtributoStatus.valueOf("ATIVO"));
		
	return empresaProcessoForm;
	}
	
	@Transactional
	public EmpresaProcesso create(EmpresaProcessoForm empresaProcessoForm) {
		
		EmpresaProcesso empresaProcesso = new EmpresaProcesso();
		
		empresaProcesso = this.converteEmpresaProcessoForm(empresaProcessoForm);
		
		empresaProcesso = entityManager.merge(empresaProcesso);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Processo Create " + "\n Usuário => " + username + 
				" // Id => "+empresaProcesso.getEmpresaProcessoPK() + 
				" // Empresa Id => "+empresaProcesso.getEmpresa().getPessoaPK() + 
				" // Processo Id => "+empresaProcesso.getProcesso().getProcessoPK()); 
		
		return empresaProcesso;
	}


}
