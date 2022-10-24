package com.sistema.blog.repository;

import com.sistema.blog.model.Coment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentRepository extends JpaRepository<Coment, Long> {
  public List<Coment> findByPublicationId(long publicacionId);
}
