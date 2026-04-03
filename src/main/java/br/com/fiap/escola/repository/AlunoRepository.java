package br.com.fiap.escola.repository;

import br.com.fiap.escola.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByCurso_IdCurso(Long idCurso);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
}
