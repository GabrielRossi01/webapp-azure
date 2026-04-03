package br.com.fiap.escola.controller.api;

import br.com.fiap.escola.model.Aluno;
import br.com.fiap.escola.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoApiController {

    private final AlunoService service;

    @GetMapping
    public List<Aluno> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{idCurso}")
    public List<Aluno> porCurso(@PathVariable Long idCurso) {
        return service.listarPorCurso(idCurso);
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@Valid @RequestBody AlunoRequest req) {
        Aluno a = new Aluno();
        a.setNome(req.getNome());
        a.setEmail(req.getEmail());
        a.setMatricula(req.getMatricula());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(a, req.getIdCurso()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id,
            @Valid @RequestBody AlunoRequest req) {
        Aluno a = new Aluno();
        a.setNome(req.getNome());
        a.setEmail(req.getEmail());
        a.setMatricula(req.getMatricula());
        return ResponseEntity.ok(service.atualizar(id, a, req.getIdCurso()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class AlunoRequest {
        @NotNull
        private String nome;
        @NotNull
        private String email;
        @NotNull
        private String matricula;
        @NotNull
        private Long idCurso;
    }
}
