package br.com.j4business.saga;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSeguranca {

	public String getUsuarioLogado() {
	
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (usuarioLogado  instanceof UserDetails ) {
		   username= ( (UserDetails)usuarioLogado).getUsername();
		} else {
		   username = usuarioLogado .toString();
		}
		return username;
	}

}
