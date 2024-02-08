package br.com.jgb.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jgb.api.dto.TarefaDTO;
import br.com.jgb.api.entity.rh.funcionario.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<TarefaDTO> findByUsuario_Id(Long id);

    // @Query(value = "SELECT t FROM Tarefa t WHERE t.user.id = :id")
    // List<Tarefa> findByUser_Id(@Param("id") Long id);

    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery =
    // true)
    // List<Tarefa> findByUser_Id(@Param("id") Long id);

}
