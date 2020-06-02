package br.com.j4business.saga.usuario.service;

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
import br.com.j4business.saga.usuario.model.Usuario;
import br.com.j4business.saga.usuario.model.UsuarioForm;
import br.com.j4business.saga.usuario.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioSeguranca usuarioSeguranca;

	private static Logger logger = LogManager.getLogger(UsuarioService.class.getName());

	@Override
	public List<Usuario> getUsuarioAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public Page<Usuario> getUsuarioAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}


	@Transactional
	public Usuario create(UsuarioForm usuarioForm) {
		
		Usuario usuario = new Usuario();
		
		usuario = this.converteUsuarioForm(usuarioForm);
		
		usuario = entityManager.merge(usuario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Usuario Create " + "\n Usuário => " + username + 
										" // Usuario => "+usuario.getUsuarioNome() + 
										" // Descrição => "+ usuario.getUsuarioDescricao());
		
		return usuario;
	}

	@Transactional
	public Usuario save(UsuarioForm usuarioForm) {
		
		Usuario usuario = new Usuario();
		
		usuario = this.converteUsuarioForm(usuarioForm);
		
		usuario = entityManager.merge(usuario);
		
		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Usuario Save " + "\n Usuário => " + username + 
										" // Usuario => "+usuario.getUsuarioNome() + 
										" // Descrição => "+ usuario.getUsuarioDescricao());
		

		return usuario;
		
	}

	@Transactional
	public void delete(String usuarioNome) {

		Usuario usuario = this.getByUsuarioNome(usuarioNome);
		
		usuarioRepository.delete(usuarioNome);

		String username = usuarioSeguranca.getUsuarioLogado();
		logger.info("Usuario Delete " + "\n Usuário => " + username + 
										" // Usuario => "+usuario.getUsuarioNome() + 
										" // Descrição => "+ usuario.getUsuarioDescricao());
		

    }

	@Transactional
	public Usuario converteUsuarioForm(UsuarioForm usuarioForm) {
		
		Usuario usuario = new Usuario();
		
		usuario.setUsuarioNome(usuarioForm.getUsuarioNome().replaceAll("\\s+"," "));
		usuario.setUsuarioDescricao(usuarioForm.getUsuarioDescricao().replaceAll("\\s+"," "));

		usuario.setUsuarioStatus(1);

		return usuario;
	}

	@Transactional
	public UsuarioForm converteUsuario(Usuario usuario) {
	
		UsuarioForm usuarioForm = new UsuarioForm();
		
		usuarioForm.setUsuarioNome(usuario.getUsuarioNome());
		usuarioForm.setUsuarioDescricao(usuario.getUsuarioDescricao());

		usuarioForm.setUsuarioStatus(1);

	return usuarioForm;
	}
	
	@Transactional
	public UsuarioForm converteUsuarioView(Usuario usuario) {
	
		UsuarioForm usuarioForm = new UsuarioForm();
		
		usuarioForm.setUsuarioNome(usuario.getUsuarioNome());
		usuarioForm.setUsuarioDescricao(usuario.getUsuarioDescricao());

		usuarioForm.setUsuarioStatus(1);
		
	return usuarioForm;
	}
	

	@Transactional
	public UsuarioForm usuarioParametros(UsuarioForm usuarioForm) {
	
		
		usuarioForm.setUsuarioStatus(1);

		
	return usuarioForm;
	}


	@Override
	public Usuario getByUsuarioNome(String usuarioNome) {
		return usuarioRepository.findByUsuarioNome(usuarioNome);
	}

	@Override
	public List<Usuario> getByUsuarioNomeLike(String usuarioNome,Pageable pageable) {
		return usuarioRepository.findByUsuarioNomeLike(usuarioNome,pageable);
	}

	@Override
	public List<Usuario> getByUsuarioNomeLike(String usuarioNome) {
		return usuarioRepository.findByUsuarioNomeLike(usuarioNome);
	}



}
