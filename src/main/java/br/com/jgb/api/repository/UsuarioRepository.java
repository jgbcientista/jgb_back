package br.com.jgb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.jgb.api.entity.rh.pessoa.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional(readOnly = true)
    Usuario findByLogin(String login);
    
	@Query(value = "SELECT * FROM TB_USUARIO u WHERE u.cpf = :cpf", nativeQuery = true)
	Usuario findByCpf(@Param("cpf") String cpf);

}
