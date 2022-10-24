package com.sistema.blog.controller;

import com.sistema.blog.dto.ComentDTO;
import com.sistema.blog.service.IComentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ComentController {
  @Autowired
  IComentService comentService;


  @GetMapping("publicaciones/{publicaionId}/comentarios")
  public List<ComentDTO> listComentPublicationId(
      @PathVariable( value = "publicaionId") Long publicaionId){
    return comentService.listComentPublicationById(publicaionId);
  }

  @GetMapping("publicaciones/{publicaionId}/comentarios/{comentarioId}")
  public ResponseEntity<ComentDTO> comentFindById(
      @PathVariable(value = "publicaionId") Long publicaionId,
      @PathVariable(value = "comentarioId") Long comentarioId){
    ComentDTO comentDTO = comentService.listComentById(publicaionId, comentarioId);
    return new ResponseEntity<>(comentDTO, HttpStatus.OK);


  }

  @PostMapping("publicaciones/{publicaionId}/comentarios")
  public ResponseEntity <ComentDTO> createComent(
       @PathVariable( value = "publicaionId") long publicaionId,
       @Valid @RequestBody() ComentDTO comentDTO

  ){
  return new ResponseEntity<>(comentService.createComent(publicaionId, comentDTO), HttpStatus.CREATED);
  }

  @PutMapping("publicaciones/{publicaionId}/comentarios/{comentarioId}")
  public ResponseEntity <ComentDTO> updateComent(
      @PathVariable( value = "publicaionId") long publicaionId,
      @PathVariable(value = "comentarioId") long comentarioId,
      @Valid @RequestBody() ComentDTO comentDTO ){
    ComentDTO comentarioUpdate = comentService.updateComent(publicaionId, comentarioId, comentDTO );
    return new ResponseEntity<>(comentarioUpdate, HttpStatus.OK);
  }
  @DeleteMapping("publicaciones/{publicaionId}/comentarios/{comentarioId}")
  public ResponseEntity<String> deleteComent(
      @PathVariable(value = "publicaionId") long publicaionId,
      @PathVariable(value = "comentarioId") long comentarioId){
    comentService.deleteComent(publicaionId, comentarioId);
    return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);

  }
}
