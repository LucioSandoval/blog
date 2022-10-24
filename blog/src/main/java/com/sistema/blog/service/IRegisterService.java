package com.sistema.blog.service;

import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.exception.EmailOrNameAlreadyExistException;

public interface IRegisterService {
  public RegisterDTO save(RegisterDTO registerDTO) throws EmailOrNameAlreadyExistException;

}
