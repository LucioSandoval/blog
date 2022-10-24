package com.sistema.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "publicaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Publication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "titulo", nullable = false)
  private String titulo;
  @Column(name = "descripcion", nullable = false)
  private String descripcion;
  @Column(name = "contenido", nullable = false)
  private String contenido;

  @JsonBackReference
  @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Coment> comentarios = new HashSet<>();

  public Publication() {
  }

  public Publication(Long id, String titulo, String descripcion, String contenido) {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.contenido = contenido;
  }

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

  public Set<Coment> getComentarios() {
    return comentarios;
  }

  public void setComentarios(Set<Coment> comentarios) {
    this.comentarios = comentarios;
  }
}
