package com.sistema.blog.service;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponDTO;

public interface IPublicationService {
  public PublicationDTO createPublication(PublicationDTO publicacionDTO);
  public PublicationResponDTO getPublicaciones(int numeroDePagina,int medidaDePagina,String ordenarPor,String sortDir);
  public PublicationDTO publicationFindById(long id);
  public PublicationDTO editPublication(PublicationDTO publicationDTO, long id);
  public void deletePublication(long id);



}
