package br.com.fiap.escola.controller.web;

import br.com.fiap.escola.service.AlunoService;
import br.com.fiap.escola.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeWebController {

    private final CursoService cursoService;
    private final AlunoService alunoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("activeMenu", "home");
        model.addAttribute("totalCursos", cursoService.total());
        model.addAttribute("totalAlunos", alunoService.total());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "index";
    }
}
