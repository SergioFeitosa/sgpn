package br.com.j4business.saga.usuario.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.usuario.model.Usuario;


@Repository("usuarioRepository")

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	@Query("SELECT p FROM Usuario p WHERE p.usuarioNome = :usuarioNome")
	public Usuario findByUsuarioNome(@Param("usuarioNome")String usuarioNome);

	@Query("SELECT p FROM Usuario p WHERE p.usuarioNome like :usuarioNome%")
	public List<Usuario> findByUsuarioNomeLike(@Param("usuarioNome")String usuarioNome,Pageable pageable);

	@Query("SELECT p FROM Usuario p WHERE p.usuarioNome like :usuarioNome%")
	public List<Usuario> findByUsuarioNomeLike(@Param("usuarioNome")String usuarioNome);

}