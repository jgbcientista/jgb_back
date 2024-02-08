package br.com.jgb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.entity.cidade.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
