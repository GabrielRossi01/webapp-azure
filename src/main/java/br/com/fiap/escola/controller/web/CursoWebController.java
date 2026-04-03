package br.com.fiap.escola.controller.web;

import br.com.fiap.escola.model.Curso;
import br.com.fiap.escola.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoWebController {

    private final CursoService service;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("activeMenu", "cursos");
        model.addAttribute("cursos", service.listarTodos());
        return "curso/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("activeMenu", "cursos");
        model.addAttribute("curso", new Curso());
        model.addAttribute("titulo", "Novo Curso");
        return "curso/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("activeMenu", "cursos");
        Curso curso = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        model.addAttribute("curso", curso);
        model.addAttribute("titulo", "Editar Curso");
        return "curso/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Curso curso,
            BindingResult result,
            Model model,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", curso.getIdCurso() == null ? "Novo Curso" : "Editar Curso");
            return "curso/form";
        }
        if (curso.getIdCurso() == null) {
            service.salvar(curso);
            ra.addFlashAttribute("sucesso", "Curso criado com sucesso!");
        } else {
            service.atualizar(curso.getIdCurso(), curso);
            ra.addFlashAttribute("sucesso", "Curso atualizado com sucesso!");
        }
        return "redirect:/cursos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        service.deletar(id);
        ra.addFlashAttribute("sucesso", "Curso removido com sucesso!");
        return "redirect:/cursos";
    }
}
