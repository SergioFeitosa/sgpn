package br.com.j4business.saga.contratoimagem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.contrato.model.Contrato;
import br.com.j4business.saga.contratoimagem.model.ContratoImagem;
import br.com.j4business.saga.contratoimagem.model.ContratoImagemForm;

@Service
public interface ContratoImagemService {
	
	public List<ContratoImagem> getContratoImagemAll(Pageable pageable);
	public ContratoImagem getContratoImagemByContratoImagemPK(long contratoImagemPK);
	public ContratoImagem save(ContratoImagemForm contratoImagemForm);
	public void delete(Long contratoImagemPK);
	public void deleteByImagem(Imagem imagem);
	public ContratoImagem create(ContratoImagemForm contratoImagemForm);
	public ContratoImagem getByContratoAndImagem(Contrato contrato, Imagem imagem);

	public ContratoImagem converteContratoImagemForm(ContratoImagemForm contratoImagemForm);
	public ContratoImagemForm converteContratoImagem(ContratoImagem contratoImagem);
	public ContratoImagemForm converteContratoImagemView(ContratoImagem contratoImagem);

	public ContratoImagemForm contratoImagemParametros(ContratoImagemForm contratoImagemForm);

	public List<ContratoImagem> getByContratoPK(long contratoPK);
	public List<ContratoImagem> getContratoImagemAtivoByContratoPK(long contratoPK);
	public List<ContratoImagem> getByContratoPK(long contratoPK,Pageable pageable);
	public List<ContratoImagem> getByImagemPK(long imagemPK,Pageable pageable);

	public List<ContratoImagem> getByImagemNome(String imagemNome,Pageable pageable);
	public List<ContratoImagem> getByContratoNome(String contratoNome,Pageable pageable);
	public List<ContratoImagem> getByImagemNome(String imagemNome);
	public List<ContratoImagem> getByContratoNome(String contratoNome);
	
}