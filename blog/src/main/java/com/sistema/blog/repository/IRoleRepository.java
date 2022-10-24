package com.sistema.blog.repository;

import com.sistema.blog.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
  public Optional<Role> findByRol(String rol);

}
