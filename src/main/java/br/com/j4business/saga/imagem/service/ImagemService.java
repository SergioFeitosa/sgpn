package br.com.j4business.saga.imagem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.j4business.saga.imagem.model.Imagem;
import br.com.j4business.saga.imagem.model.ImagemForm;

@Service
public interface ImagemService {
	
	public List<Imagem> getImagemAll();
	public Page<Imagem> getImagemAll(Pageable pageable);
	public Imagem getImagemByImagemPK(long imagemPK);
	public Imagem create(ImagemForm imagemForm);
	public Imagem save(ImagemForm imagemForm);
	public void delete(Long imagemPK);
	
	public Imagem converteImagemForm(ImagemForm imagemForm);
	public ImagemForm converteImagem(Imagem imagem);
	public ImagemForm converteImagemView(Imagem imagem);

	public ImagemForm imagemParametros(ImagemForm imagemForm);

	public List<Imagem> getByImagemNome(String imagemNome,Pageable pageable);
	public List<Imagem> getByImagemDescricao(String imagemDescricao,Pageable pageable);
	public List<Imagem> getByImagemNome(String imagemNome);
	public List<Imagem> getByImagemDescricao(String imagemDescricao);

}