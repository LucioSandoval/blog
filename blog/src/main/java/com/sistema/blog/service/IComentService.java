package com.sistema.blog.service;

import com.sistema.blog.dto.ComentDTO;
import java.util.List;

public interface IComentService {
  public ComentDTO createComent(long publicationId, ComentDTO comentDTO);

  // Listar comentarios por id publicacion
  public List<ComentDTO> listComentPublicationById(long publicacionId);

  // Listar comentario por idcomentario
  public ComentDTO listComentById(long publicacionId, long comentarioId);
  // Actualizar comentario
  public ComentDTO updateComent(long publicacionId, long comentarioId, ComentDTO comentDTO);

  // Eliminar comentario
  public void deleteComent(long publicacionId, long comentarioId);
}
