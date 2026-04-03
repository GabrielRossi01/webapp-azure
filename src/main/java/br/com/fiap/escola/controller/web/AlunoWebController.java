package br.com.fiap.escola.controller.web;

import br.com.fiap.escola.model.Aluno;
import br.com.fiap.escola.service.AlunoService;
import br.com.fiap.escola.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoWebController {

    private final AlunoService alunoService;
    private final CursoService cursoService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("alunos", alunoService.listarTodos());
        return "aluno/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("titulo", "Novo Aluno");
        return "aluno/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("activeMenu", "alunos");
        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        model.addAttribute("aluno", aluno);
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("titulo", "Editar Aluno");
        return "aluno/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Aluno aluno,
            BindingResult result,
            @RequestParam Long idCurso,
            Model model,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoService.listarTodos());
            model.addAttribute("titulo", aluno.getIdAluno() == null ? "Novo Aluno" : "Editar Aluno");
            return "aluno/form";
        }
        if (aluno.getIdAluno() == null) {
            alunoService.salvar(aluno, idCurso);
            ra.addFlashAttribute("sucesso", "Aluno matriculado com sucesso!");
        } else {
            alunoService.atualizar(aluno.getIdAluno(), aluno, idCurso);
            ra.addFlashAttribute("sucesso", "Aluno atualizado com sucesso!");
        }
        return "redirect:/alunos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        alunoService.deletar(id);
        ra.addFlashAttribute("sucesso", "Aluno removido com sucesso!");
        return "redirect:/alunos";
    }
}
