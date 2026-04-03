package br.com.fiap.escola.service;

import br.com.fiap.escola.model.Aluno;
import br.com.fiap.escola.model.Curso;
import br.com.fiap.escola.repository.AlunoRepository;
import br.com.fiap.escola.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> listarPorCurso(Long idCurso) {
        return alunoRepository.findByCurso_IdCurso(idCurso);
    }

    public Aluno salvar(Aluno aluno, Long idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + idCurso));
        aluno.setCurso(curso);
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno dados, Long idCurso) {
        Aluno existente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + id));
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + idCurso));
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setMatricula(dados.getMatricula());
        existente.setCurso(curso);
        return alunoRepository.save(existente);
    }

    public void deletar(Long id) {
        if (!alunoRepository.existsById(id))
            throw new RuntimeException("Aluno não encontrado: " + id);
        alunoRepository.deleteById(id);
    }

    public long total() {
        return alunoRepository.count();
    }
}
