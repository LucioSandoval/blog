package com.sistema.blog.controller;

import com.sistema.blog.dto.JwtAuthResponseDTO;
import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.exception.BlogAppException;
import com.sistema.blog.exception.EmailOrNameAlreadyExistException;
import com.sistema.blog.model.Role;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.IRoleRepository;
import com.sistema.blog.repository.IUserRepository;
import com.sistema.blog.security.JwtTokenProvider;
import com.sistema.blog.service.IRegisterService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private IRegisterService registerService;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword() ));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //Obtener el token y el jwtTokenProvider
    String token = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthResponseDTO(token));
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterDTO> createRegister(@RequestBody RegisterDTO registerDTO)
      throws BlogAppException, EmailOrNameAlreadyExistException {

    return new ResponseEntity<>(registerService.save(registerDTO), HttpStatus.CREATED);
  }



 /* @PostMapping("register")
  public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
    if(userRepository.existsByUsername(registerDTO.getUsername())) {
      return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
    }

    if(userRepository.existsByEmail(registerDTO.getEmail())) {
      return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
    }

    Usuario usuario = new Usuario();
    usuario.setNombre(registerDTO.getNombre());
    usuario.setUsername(registerDTO.getUsername());
    usuario.setEmail(registerDTO.getEmail());
    usuario.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

    Role roles = roleRepository.findByRol("ROLE_ADMIN").get();
    usuario.setRoles(Collections.singleton(roles));

    userRepository.save(usuario);
    return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
  }*/

}
