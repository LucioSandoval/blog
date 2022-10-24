package com.sistema.blog.service.implementation;

import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.exception.BlogAppException;

import com.sistema.blog.exception.EmailOrNameAlreadyExistException;
import com.sistema.blog.model.Role;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.IRoleRepository;
import com.sistema.blog.repository.IUserRepository;
import com.sistema.blog.service.IRegisterService;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements IRegisterService {
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private IUserRepository userRepository;

  @Override
  public RegisterDTO save(RegisterDTO registerDTO) throws EmailOrNameAlreadyExistException {
    if(userRepository.existsByUsername(registerDTO.getUsername())) {
      throw new EmailOrNameAlreadyExistException("Ese nombre de usuario ya existe.");
    }

    if(userRepository.existsByEmail(registerDTO.getEmail())) {
      throw new EmailOrNameAlreadyExistException("Ese email de usuario ya existe.");
    }

    Usuario usuario = new Usuario();
    usuario.setNombre(registerDTO.getNombre());
    usuario.setUsername(registerDTO.getUsername());
    usuario.setEmail(registerDTO.getEmail());
    usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

    Role roles = roleRepository.findByRol("ROLE_ADMIN").get();
    usuario.setRoles(Collections.singleton(roles));

    Usuario newUsuario = userRepository.save(usuario);
    return mapeoDTO(newUsuario);

  }


  // Convertir Entidad en DTO

  private RegisterDTO mapeoDTO(Usuario usuario){
    RegisterDTO registerDTO = modelMapper.map(usuario, RegisterDTO.class);

    return registerDTO;
  }
  //Mapear entidad
  private Usuario mapeoEntidad(RegisterDTO registerDTO){
    Usuario usuario = modelMapper.map(registerDTO, Usuario.class);
    return usuario;
  }

}
