package com.residencia.comercio.services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.residencia.comercio.entities.Usuario;
import com.residencia.comercio.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImplService implements UserDetailsService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(usuario.isPresent()) {
			return usuario.get();
		}
		
		/*
		if(usuario.isPresent()) {
			Usuario user = usuario.get();
	        return new org.springframework.security.core.userdetails.User(
	                email,
	                user.getPassword(),
	                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
		}
		*/
		throw new UsernameNotFoundException("Usuário Não Encontrado");
	}	
}
