package br.com.j4business.saga.fornecedor.service;

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
import br.com.j4business.saga.fornecedor.enumeration.FornecedorRamo;
import br.com.j4business.saga.fornecedor.model.Fornecedor;
import br.com.j4business.saga.fornecedor.model.FornecedorForm;
import br.com.j4business.saga.fornecedor.repository.FornecedorRepository;

@Service("fornecedorService")
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	private ColaboradorService colaboradorService;

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(FornecedorService.class.getName());

	@Override
	public List<Fornecedor> getFornecedorAll() {
		return fornecedorRepository.findAll();
	}

	@Override
	public Page<Fornecedor> getFornecedorAll(Pageable pageable) {
		return fornecedorRepository.findAll(pageable);
	}

	@Override
	public Fornecedor getFornecedorByFornecedorPK(long fornecedorPK) {
		
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(fornecedorPK);
		return fornecedor.get();
	}

	@Transactional
	public Fornecedor create(FornecedorForm fornecedorForm) {
		
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor = this.converteFornecedorForm(fornecedorForm);
		
		fornecedor = entityManager.merge(fornecedor);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Fornecedor Create " + "\n Usuário => " + username + 
										" // Id => "+fornecedor.getPessoaPK() + 
										" // Fornecedor => "+fornecedor.getPessoaNome() + 
										" // Nome Fantasia => "+ fornecedor.getFornecedorNomeFantasia());
		
		return fornecedor;
	}

	@Transactional
	public Fornecedor save(FornecedorForm fornecedorForm) {
		
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor = this.converteFornecedorForm(fornecedorForm);
		
		fornecedor = entityManager.merge(fornecedor);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Fornecedor Save " + "\n Usuário => " + username + 
										" // Id => "+fornecedor.getPessoaPK() + 
										" // Fornecedor => "+fornecedor.getPessoaNome() + 
										" // Nome Fantasia => "+ fornecedor.getFornecedorNomeFantasia());
		

		return fornecedor;
		
	}

	@Transactional
	public void delete(Long fornecedorId) {

		Fornecedor fornecedor = this.getFornecedorByFornecedorPK(fornecedorId);
		
		fornecedorRepository.delete(fornecedor);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Fornecedor Delete " + "\n Usuário => " + username + 
										" // Id => "+fornecedor.getPessoaPK() + 
										" // Fornecedor => "+fornecedor.getPessoaNome() + 
										" // Nome Fantasia => "+ fornecedor.getFornecedorNomeFantasia());

    }
	
	@Transactional
	public Fornecedor converteFornecedorForm(FornecedorForm fornecedorForm) {
		
		Fornecedor fornecedor = new Fornecedor();
		
		if (fornecedorForm.getFornecedorPK() > 0) {
			fornecedor = this.getFornecedorByFornecedorPK(fornecedorForm.getFornecedorPK());
		}
		
		fornecedor.setPessoaNome(fornecedorForm.getFornecedorNome().replaceAll("\\s+"," "));
		fornecedor.setFornecedorNomeFantasia(fornecedorForm.getFornecedorNomeFantasia().replaceAll("\\s+"," "));

		fornecedor.setFornecedorRamo(fornecedorForm.getFornecedorRamo()+"");
		fornecedor.setCnpj(fornecedorForm.getFornecedorCNPJ().replaceAll("  "," "));

		fornecedor.setEnderecoCEP(fornecedorForm.getEnderecoCEP().replaceAll("\\s+"," "));
		fornecedor.setEnderecoLogradouro(fornecedorForm.getEnderecoLogradouro().replaceAll("\\s+"," "));
		fornecedor.setEnderecoBairro(fornecedorForm.getEnderecoBairro().replaceAll("\\s+"," "));
		fornecedor.setEnderecoMunicipio(fornecedorForm.getEnderecoMunicipio().replaceAll("\\s+"," "));
		fornecedor.setEnderecoUF(fornecedorForm.getEnderecoUF().replaceAll("\\s+"," "));

		fornecedor.setTelefoneNumero(fornecedorForm.getTelefoneNumero().replaceAll("\\s+"," "));
		fornecedor.setTelefoneOperadora(fornecedorForm.getTelefoneOperadora().replaceAll("\\s+"," "));

		fornecedor.setEmailNome(fornecedorForm.getEmailNome().replaceAll("\\s+"," "));

		fornecedor.setRedeSocialNome(fornecedorForm.getRedeSocialNome().replaceAll("\\s+"," "));
		fornecedor.setRedeSocialIdentificacao(fornecedorForm.getRedeSocialIdentificacao().replaceAll("\\s+"," "));
		
		fornecedor.setFornecedorMotivoOperacao(fornecedorForm.getFornecedorMotivoOperacao().replaceAll("\\s+"," "));
		fornecedor.setFornecedorStatus(fornecedorForm.getFornecedorStatus()+"".replaceAll("\\s+"," "));

		Colaborador colaborador = colaboradorService.getColaboradorByColaboradorPK(Long.parseLong(fornecedorForm.getFornecedorResponsavel()));

		fornecedor.setColaboradorResponsavel(colaborador);
		
		return fornecedor;
	}

	@Transactional
	public FornecedorForm converteFornecedor(Fornecedor fornecedor) {
	
		FornecedorForm fornecedorForm = new FornecedorForm();
		
		fornecedorForm.setFornecedorPK(fornecedor.getPessoaPK());
		fornecedorForm.setFornecedorNome(fornecedor.getPessoaNome());
		fornecedorForm.setFornecedorNomeFantasia(fornecedor.getFornecedorNomeFantasia());

		fornecedorForm.setFornecedorRamo(FornecedorRamo.valueOf(fornecedor.getFornecedorRamo()));
		fornecedorForm.setFornecedorCNPJ(fornecedor.getCnpj().replaceAll("  "," "));

		fornecedorForm.setEnderecoCEP(fornecedor.getEnderecoCEP());
		fornecedorForm.setEnderecoLogradouro(fornecedor.getEnderecoLogradouro());
		fornecedorForm.setEnderecoBairro(fornecedor.getEnderecoBairro());
		fornecedorForm.setEnderecoMunicipio(fornecedor.getEnderecoMunicipio());
		fornecedorForm.setEnderecoUF(fornecedor.getEnderecoUF());
		
		fornecedorForm.setTelefoneNumero(fornecedor.getTelefoneNumero());
		fornecedorForm.setTelefoneOperadora(fornecedor.getTelefoneOperadora());

		fornecedorForm.setEmailNome(fornecedor.getEmailNome());

		fornecedorForm.setRedeSocialNome(fornecedor.getRedeSocialNome());
		fornecedorForm.setRedeSocialIdentificacao(fornecedor.getRedeSocialIdentificacao());

		fornecedorForm.setFornecedorMotivoOperacao(fornecedor.getFornecedorMotivoOperacao());
		fornecedorForm.setFornecedorStatus(AtributoStatus.valueOf(fornecedor.getFornecedorStatus()));

		fornecedorForm.setFornecedorResponsavel(fornecedor.getColaboradorResponsavel().getPessoaPK()+"");
		
	return fornecedorForm;
	}
	
	@Transactional
	public FornecedorForm converteFornecedorView(Fornecedor fornecedor) {
	
		FornecedorForm fornecedorForm = new FornecedorForm();
		
		fornecedorForm.setFornecedorPK(fornecedor.getPessoaPK());
		fornecedorForm.setFornecedorNome(fornecedor.getPessoaNome());
		fornecedorForm.setFornecedorNomeFantasia(fornecedor.getFornecedorNomeFantasia());

		fornecedorForm.setFornecedorRamo(FornecedorRamo.valueOf(fornecedor.getFornecedorRamo()));
		fornecedorForm.setFornecedorCNPJ(fornecedor.getCnpj().replaceAll("  "," "));

		fornecedorForm.setEnderecoCEP(fornecedor.getEnderecoCEP());
		fornecedorForm.setEnderecoLogradouro(fornecedor.getEnderecoLogradouro());
		fornecedorForm.setEnderecoBairro(fornecedor.getEnderecoBairro());
		fornecedorForm.setEnderecoMunicipio(fornecedor.getEnderecoMunicipio());
		fornecedorForm.setEnderecoUF(fornecedor.getEnderecoUF());
		
		fornecedorForm.setTelefoneNumero(fornecedor.getTelefoneNumero());
		fornecedorForm.setTelefoneOperadora(fornecedor.getTelefoneOperadora());

		fornecedorForm.setEmailNome(fornecedor.getEmailNome());

		fornecedorForm.setRedeSocialNome(fornecedor.getRedeSocialNome());
		fornecedorForm.setRedeSocialIdentificacao(fornecedor.getRedeSocialIdentificacao());

		fornecedorForm.setFornecedorMotivoOperacao(fornecedor.getFornecedorMotivoOperacao());
		fornecedorForm.setFornecedorStatus(AtributoStatus.valueOf(fornecedor.getFornecedorStatus()));

		fornecedorForm.setFornecedorResponsavel(fornecedor.getColaboradorResponsavel().getPessoaNome());
		
	return fornecedorForm;
	}
	

	@Transactional
	public FornecedorForm fornecedorParametros(FornecedorForm fornecedorForm) {
		fornecedorForm.setFornecedorStatus(AtributoStatus.valueOf("ATIVO"));
	return fornecedorForm;
	}

	@Override
	public List<Fornecedor> getByFornecedorNomeFantasia(String fornecedorNomeFantasia,Pageable pageable) {
		return fornecedorRepository.findByFornecedorNomeFantasia(fornecedorNomeFantasia,pageable);
	}

	@Override
	public List<Fornecedor> getByFornecedorNome(String fornecedorNome,Pageable pageable) {
		return fornecedorRepository.findByFornecedorNome(fornecedorNome,pageable);
	}

	@Override
	public List<Fornecedor> getByFornecedorNomeFantasia(String fornecedorNomeFantasia) {
		return fornecedorRepository.findByFornecedorNomeFantasia(fornecedorNomeFantasia);
	}

	@Override
	public List<Fornecedor> getByFornecedorNome(String fornecedorNome) {
		return fornecedorRepository.findByFornecedorNome(fornecedorNome);
	}



}
