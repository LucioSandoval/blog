package com.sistema.blog.service.implementation;

import com.sistema.blog.dto.ComentDTO;
import com.sistema.blog.exception.BlogAppException;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Coment;
import com.sistema.blog.model.Publication;
import com.sistema.blog.repository.IComentRepository;
import com.sistema.blog.repository.IPublicationRepository;
import com.sistema.blog.service.IComentService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ComentServiceImpl implements IComentService {
  @Autowired
  private IComentRepository comentRepository;

  @Autowired
  IPublicationRepository publicationRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public ComentDTO createComent(long publicationId, ComentDTO comentDTO) {
    Coment coment = mapearEntidad(comentDTO);

    Publication publication = publicationRepository.findById(publicationId)
        .orElseThrow( () -> new ResourceNotFoundException("Publicacion", "id", publicationId));

    coment.setPublication(publication);
    Coment nuevoComentario = comentRepository.save(coment);
    return mapearDTO(nuevoComentario);
  }

  @Override
  public List<ComentDTO> listComentPublicationById(long publidacionId) {
    List<Coment> cometarios = comentRepository.findByPublicationId(publidacionId);
    return cometarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
  }

  @Override
  public ComentDTO listComentById(long publicacionId, long comentarioId) {
    Publication publication = publicationRepository.findById(publicacionId)
        .orElseThrow( () -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

    Coment coment = comentRepository.findById(comentarioId)
        .orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", comentarioId));

    if(!coment.getPublication().getId().equals(publication.getId())){
      throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
    }
    return mapearDTO(coment);
  }

  @Override
  public ComentDTO updateComent(long publicacionId, long comentarioId, ComentDTO comentDTO) {
    Publication publication = publicationRepository.findById(publicacionId)
        .orElseThrow( () -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

    Coment coment = comentRepository.findById(comentarioId)
        .orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", comentarioId));

    if(!coment.getPublication().getId().equals(publication.getId())){
      throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
    }
    coment.setNombre(comentDTO.getNombre());
    coment.setEmail(comentDTO.getEmail());
    coment.setCuerpo(comentDTO.getCuerpo());
    Coment comentarioUpdate = comentRepository.save(coment);
    return mapearDTO(comentarioUpdate);
  }

  @Override
  public void deleteComent(long publicacionId, long comentarioId) {
    Publication publication = publicationRepository.findById(publicacionId)
        .orElseThrow( () -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

    Coment coment = comentRepository.findById(comentarioId)
        .orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", comentarioId));

    if(!coment.getPublication().getId().equals(publication.getId())){
      throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
    }
    comentRepository.delete(coment);
  }

  // Convertir Entidad en DTO
  private ComentDTO mapearDTO(Coment coment){
    ComentDTO comentDTO = modelMapper.map(coment, ComentDTO.class);
    return comentDTO;
  }

  // Convertir DTO en Entidad
  private Coment mapearEntidad(ComentDTO comentDTO){
    Coment coment = modelMapper.map(comentDTO, Coment.class);
    return coment;
  }
}
