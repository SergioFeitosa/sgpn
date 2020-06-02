package br.com.j4business.saga.usuario.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.usuario.model.Usuario;
import br.com.j4business.saga.usuario.model.UsuarioForm;

@Service
public interface UsuarioService {
	
	public List<Usuario> getUsuarioAll();
	public Page<Usuario> getUsuarioAll(Pageable pageable);
	public Usuario create(UsuarioForm usuarioForm);
	public Usuario save(UsuarioForm usuarioForm);
	public void delete(String usuarioNome);
	
	public Usuario converteUsuarioForm(UsuarioForm usuarioForm);
	public UsuarioForm converteUsuario(Usuario usuario);
	public UsuarioForm converteUsuarioView(Usuario usuario);

	public UsuarioForm usuarioParametros(UsuarioForm usuarioForm);

	public Usuario getByUsuarioNome(String usuarioNome);

	public List<Usuario> getByUsuarioNomeLike(String usuarioNome,Pageable pageable);
	public List<Usuario> getByUsuarioNomeLike(String usuarioNome);

}