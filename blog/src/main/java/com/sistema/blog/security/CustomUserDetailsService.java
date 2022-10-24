package com.sistema.blog.security;

import com.sistema.blog.model.Role;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.IUserRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

  @Autowired
  private IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    Usuario usuario = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email : " + usernameOrEmail));

    return new User(usuario.getEmail(), usuario.getPassword(), mapearRoles(usuario.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapearRoles(Set<Role> roles){
    return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getRol())).collect(Collectors.toList());
  }
}
