package br.com.practicalsolutions.monitortermico.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.practicalsolutions.monitortermico.model.Usuario;

@ManagedBean
@SessionScoped
public class ControladorUsuario implements Serializable {

	private static final long serialVersionUID = -7215754869138562227L;
	
	private Usuario usuario;
	
	public ControladorUsuario(){
		this.usuario = new Usuario();
		SecurityContext context = SecurityContextHolder.getContext();
		if(context instanceof SecurityContext){
			Authentication authentication = context.getAuthentication();
			if(authentication instanceof Authentication){
				usuario.setUsername( ((Usuario) authentication.getPrincipal()).getUsername());
			}
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
