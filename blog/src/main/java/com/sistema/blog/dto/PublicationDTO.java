package com.sistema.blog.dto;

import com.sistema.blog.model.Coment;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PublicationDTO {
  private Long id;

  @NotEmpty
  @Size(min = 2, message = "El titulo de la publicacion debe tener minimo 2 caracteres")
  private String titulo;

  @NotEmpty
  @Size(min = 10, message = "El titulo de la publicacion debe tener minimo 10 caracteres")
  private String descripcion;

  @NotEmpty
  private String contenido;

  private Set<Coment> coments;
  public PublicationDTO() {
  }

/*  public PublicationDTO(Long id, String titulo, String descripcion, String contenido) {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.contenido = contenido;
  }*/

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public Set<Coment> getComents() {
    return coments;
  }

  public void setComents(Set<Coment> coments) {
    this.coments = coments;
  }
}
