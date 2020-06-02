package br.com.j4business.saga.empresa.service;

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
import br.com.j4business.saga.empresa.enumeration.EmpresaRamo;
import br.com.j4business.saga.empresa.model.Empresa;
import br.com.j4business.saga.empresa.model.EmpresaForm;
import br.com.j4business.saga.empresa.repository.EmpresaRepository;

@Service("empresaService")
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(EmpresaService.class.getName());

	@Override
	public List<Empresa> getEmpresaAll() {
		return empresaRepository.findAll();
	}

	@Override
	public Page<Empresa> getEmpresaAll(Pageable pageable) {
		return empresaRepository.findAll(pageable);
	}

	@Override
	public Empresa getEmpresaByEmpresaPK(long empresaPK) {
		
		return empresaRepository.findOne(empresaPK);
	}

	@Transactional
	public Empresa create(EmpresaForm empresaForm) {
		
		Empresa empresa = new Empresa();
		
		empresa = this.converteEmpresaForm(empresaForm);
		
		empresa = entityManager.merge(empresa);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Empresa Create " + "\n Usuário => " + username + 
										" // Id => "+empresa.getPessoaPK() + 
										" // Empresa => "+empresa.getPessoaNome() + 
										" // Nome Fantasia => "+ empresa.getEmpresaNomeFantasia());
		
		return empresa;
	}

	@Transactional
	public Empresa save(EmpresaForm empresaForm) {
		
		Empresa empresa = new Empresa();
		
		empresa = this.converteEmpresaForm(empresaForm);
		
		empresa = entityManager.merge(empresa);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Empresa Save " + "\n Usuário => " + username + 
										" // Id => "+empresa.getPessoaPK() + 
										" // Empresa => "+empresa.getPessoaNome() + 
										" // Nome Fantasia => "+ empresa.getEmpresaNomeFantasia());
		

		return empresa;
		
	}

	@Transactional
	public void delete(Long empresaId) {

		Empresa empresa = this.getEmpresaByEmpresaPK(empresaId);
		
		empresaRepository.delete(empresa.getPessoaPK());

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Empresa Delete " + "\n Usuário => " + username + 
										" // Id => "+empresa.getPessoaPK() + 
										" // Empresa => "+empresa.getPessoaNome() + 
										" // Nome Fantasia => "+ empresa.getEmpresaNomeFantasia());

    }
	
	@Transactional
	public Empresa converteEmpresaForm(EmpresaForm empresaForm) {
		
		Empresa empresa = new Empresa();
		
		if (empresaForm.getEmpresaPK() > 0) {
			empresa = this.getEmpresaByEmpresaPK(empresaForm.getEmpresaPK());
		}
		
		
		empresa.setPessoaNome(empresaForm.getEmpresaNome().replaceAll("\\s+"," "));
		empresa.setEmpresaNomeFantasia(empresaForm.getEmpresaNomeFantasia().replaceAll("\\s+"," "));

		empresa.setEmpresaRamo(empresaForm.getEmpresaRamo()+"");
		empresa.setCnpj(empresaForm.getEmpresaCNPJ().replaceAll("  "," "));

		empresa.setEnderecoCEP(empresaForm.getEnderecoCEP().replaceAll("\\s+"," "));
		empresa.setEnderecoLogradouro(empresaForm.getEnderecoLogradouro().replaceAll("\\s+"," "));
		empresa.setEnderecoBairro(empresaForm.getEnderecoBairro().replaceAll("\\s+"," "));
		empresa.setEnderecoMunicipio(empresaForm.getEnderecoMunicipio().replaceAll("\\s+"," "));
		empresa.setEnderecoUF(empresaForm.getEnderecoUF().replaceAll("\\s+"," "));

		empresa.setTelefoneNumero(empresaForm.getTelefoneNumero().replaceAll("\\s+"," "));
		empresa.setTelefoneOperadora(empresaForm.getTelefoneOperadora().replaceAll("\\s+"," "));

		empresa.setEmailNome(empresaForm.getEmailNome().replaceAll("\\s+"," "));

		empresa.setRedeSocialNome(empresaForm.getRedeSocialNome().replaceAll("\\s+"," "));
		empresa.setRedeSocialIdentificacao(empresaForm.getRedeSocialIdentificacao().replaceAll("\\s+"," "));
		
		empresa.setEmpresaMotivoOperacao(empresaForm.getEmpresaMotivoOperacao().replaceAll("\\s+"," "));
		empresa.setEmpresaStatus(empresaForm.getEmpresaStatus()+"".replaceAll("\\s+"," "));
		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(empresaForm.getEmpresaResponsavel()));

		empresa.setColaboradorResponsavel(colaborador);
		
		return empresa;
	}

	@Transactional
	public EmpresaForm converteEmpresa(Empresa empresa) {
	
		EmpresaForm empresaForm = new EmpresaForm();
		
		empresaForm.setEmpresaPK(empresa.getPessoaPK());
		empresaForm.setEmpresaNome(empresa.getPessoaNome());
		empresaForm.setEmpresaNomeFantasia(empresa.getEmpresaNomeFantasia());

		empresaForm.setEmpresaRamo(EmpresaRamo.valueOf(empresa.getEmpresaRamo()));
		empresaForm.setEmpresaCNPJ(empresa.getCnpj().replaceAll("  "," "));

		empresaForm.setEnderecoCEP(empresa.getEnderecoCEP());
		empresaForm.setEnderecoLogradouro(empresa.getEnderecoLogradouro());
		empresaForm.setEnderecoBairro(empresa.getEnderecoBairro());
		empresaForm.setEnderecoMunicipio(empresa.getEnderecoMunicipio());
		empresaForm.setEnderecoUF(empresa.getEnderecoUF());
		
		empresaForm.setTelefoneNumero(empresa.getTelefoneNumero());
		empresaForm.setTelefoneOperadora(empresa.getTelefoneOperadora());

		empresaForm.setEmailNome(empresa.getEmailNome());

		empresaForm.setRedeSocialNome(empresa.getRedeSocialNome());
		empresaForm.setRedeSocialIdentificacao(empresa.getRedeSocialIdentificacao());

		empresaForm.setEmpresaMotivoOperacao(empresa.getEmpresaMotivoOperacao());
		empresaForm.setEmpresaStatus(AtributoStatus.valueOf(empresa.getEmpresaStatus()+""));

		empresaForm.setEmpresaResponsavel(empresa.getColaboradorResponsavel().getPessoaPK()+"");
		
	return empresaForm;
	}
	
	@Transactional
	public EmpresaForm converteEmpresaView(Empresa empresa) {
	
		EmpresaForm empresaForm = new EmpresaForm();
		
		empresaForm.setEmpresaPK(empresa.getPessoaPK());
		empresaForm.setEmpresaNome(empresa.getPessoaNome());
		empresaForm.setEmpresaNomeFantasia(empresa.getEmpresaNomeFantasia());

		empresaForm.setEmpresaRamo(EmpresaRamo.valueOf(empresa.getEmpresaRamo()));
		empresaForm.setEmpresaCNPJ(empresa.getCnpj().replaceAll("  "," "));

		empresaForm.setEnderecoCEP(empresa.getEnderecoCEP());
		empresaForm.setEnderecoLogradouro(empresa.getEnderecoLogradouro());
		empresaForm.setEnderecoBairro(empresa.getEnderecoBairro());
		empresaForm.setEnderecoMunicipio(empresa.getEnderecoMunicipio());
		empresaForm.setEnderecoUF(empresa.getEnderecoUF());
		
		empresaForm.setTelefoneNumero(empresa.getTelefoneNumero());
		empresaForm.setTelefoneOperadora(empresa.getTelefoneOperadora());

		empresaForm.setEmailNome(empresa.getEmailNome());

		empresaForm.setRedeSocialNome(empresa.getRedeSocialNome());
		empresaForm.setRedeSocialIdentificacao(empresa.getRedeSocialIdentificacao());

		empresaForm.setEmpresaMotivoOperacao(empresa.getEmpresaMotivoOperacao());
		empresaForm.setEmpresaStatus(AtributoStatus.valueOf(empresa.getEmpresaStatus()+""));

		empresaForm.setEmpresaResponsavel(empresa.getColaboradorResponsavel().getPessoaNome());
		
	return empresaForm;
	}
	

	@Transactional
	public EmpresaForm empresaParametros(EmpresaForm empresaForm) {
	
		
		empresaForm.setEmpresaStatus(AtributoStatus.valueOf("ATIVO"));

		
	return empresaForm;
	}

	@Override
	public List<Empresa> getByEmpresaNomeFantasia(String empresaNomeFantasia,Pageable pageable) {
		return empresaRepository.findByEmpresaNomeFantasia(empresaNomeFantasia,pageable);
	}

	@Override
	public List<Empresa> getByEmpresaNome(String empresaNome,Pageable pageable) {
		return empresaRepository.findByEmpresaNome(empresaNome,pageable);
	}

	@Override
	public List<Empresa> getByEmpresaNomeFantasia(String empresaNomeFantasia) {
		return empresaRepository.findByEmpresaNomeFantasia(empresaNomeFantasia);
	}

	@Override
	public List<Empresa> getByEmpresaNome(String empresaNome) {
		return empresaRepository.findByEmpresaNome(empresaNome);
	}



}
