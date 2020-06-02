package br.com.j4business.saga.processoimagem.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.processo.model.Processo;
import br.com.j4business.saga.processoimagem.model.ProcessoImagem;

@Repository("processoImagemRepository")
public interface ProcessoImagemRepository extends PagingAndSortingRepository<ProcessoImagem, Long>{

/*	 @Query("SELECT ea FROM ProcessoImagem ea where ea.imagem.imagemPK = :id") 
	    List<ProcessoImagem> findByImagemPK(@Param("id") Long id);
*/	 
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.imagem p WHERE p = :imagem")
	public List<ProcessoImagem> findByImagem(@Param("imagem")Imagem imagem);

	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.imagem p INNER JOIN ep.processo e WHERE p = :imagem AND s = :processo")
	public ProcessoImagem findByProcessoAndImagem( @Param("processo") Processo processo, @Param("imagem")Imagem imagem);
	
	@Query("SELECT ep FROM ProcessoImagem ep")
	public List<ProcessoImagem> findProcessoImagemAll(Pageable pageable);

	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoImagem> findByProcessoPK(@Param("processoPK")long processoPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK")
	public List<ProcessoImagem> findByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.processo e WHERE e.processoPK = :processoPK and ep.processoImagemStatus = 'ATIVO'")
	public List<ProcessoImagem> findProcessoImagemAtivoByProcessoPK(@Param("processoPK")long processoPK);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.imagem p WHERE p.imagemPK = :imagemPK")
	public List<ProcessoImagem> findByImagemPK(@Param("imagemPK")long imagemPK,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoImagem> findByProcessoNome(@Param("processoNome")String processoNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<ProcessoImagem> findByImagemNome(@Param("imagemNome")String imagemNome,Pageable pageable);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.processo e WHERE e.processoNome like :processoNome%")
	public List<ProcessoImagem> findByProcessoNome(@Param("processoNome")String processoNome);
	
	@Query("SELECT ep FROM ProcessoImagem ep INNER JOIN ep.imagem p WHERE p.imagemNome like :imagemNome%")
	public List<ProcessoImagem> findByImagemNome(@Param("imagemNome")String imagemNome);
	

}