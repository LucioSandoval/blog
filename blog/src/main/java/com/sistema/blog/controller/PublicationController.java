package com.sistema.blog.controller;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponDTO;
import com.sistema.blog.service.IPublicationService;
import com.sistema.blog.utils.AppConstan;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/publicaciones")
public class PublicationController {
  @Autowired
  IPublicationService publicationService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping()
  public ResponseEntity<PublicationDTO> save(@Valid @RequestBody PublicationDTO publicacionDTO){
    return new ResponseEntity<>(publicationService.createPublication(publicacionDTO), HttpStatus.CREATED);
  }
  @GetMapping()
  public PublicationResponDTO obtenerPublicaciones(
      @RequestParam(value = "numeroPagina", defaultValue = AppConstan.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
      @RequestParam(value = "cantidadPagina", defaultValue = AppConstan.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int cantidadPagina,
      @RequestParam(value = "sortby", defaultValue = AppConstan.ORDENAR_POR_DEFECTO, required = false) String sortby,
      @RequestParam(value = "sortdir", defaultValue = AppConstan.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortdir
    ){

   return publicationService.getPublicaciones(numeroPagina, cantidadPagina, sortby, sortdir);
  }
  @GetMapping("/{id}")
  public ResponseEntity<PublicationDTO> publicationFindById(@PathVariable(name = "id")long id){

    return ResponseEntity.ok(publicationService.publicationFindById(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<PublicationDTO> editPulication(
      @Valid  @RequestBody PublicationDTO publicationDTO,
      @PathVariable(name = "id") long id){
    return new ResponseEntity<>(publicationService.editPublication(publicationDTO, id), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id){
    publicationService.deletePublication(id);
    return new ResponseEntity<>("Publicación eliminada exitosamente", HttpStatus.OK);
  }
}
