package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.rest.AuthorController;
import ru.otus.trim.rest.dto.AuthorDto;

@AllArgsConstructor
@Controller
public class AuthorPageController {
    private final AuthorController authorController;

    @GetMapping("/authors/")
    public String listPage() {
        return "author_list";
    }

    @GetMapping("/authors/{id}")
    public String getAuthor(@PathVariable int id, Model model) {
        model.addAttribute("author",id > 0 ? authorController.getAuthorByIdInRequest(id) : new AuthorDto (0, ""));
        return "author_edit";
    }

    @PostMapping("/authors/")
    public String saveAuthor(@ModelAttribute("author") AuthorDto author) {
        authorController.saveAuthor(author);
        return "redirect:/authors/";
    }
}
