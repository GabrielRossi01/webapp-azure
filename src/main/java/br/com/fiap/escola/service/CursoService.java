package br.com.fiap.escola.service;

import br.com.fiap.escola.model.Curso;
import br.com.fiap.escola.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository repository;

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizar(Long id, Curso dados) {
        Curso existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + id));
        existente.setNome(dados.getNome());
        existente.setDescricao(dados.getDescricao());
        existente.setCargaHoraria(dados.getCargaHoraria());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id))
            throw new RuntimeException("Curso não encontrado: " + id);
        repository.deleteById(id);
    }

    public long total() {
        return repository.count();
    }
}
