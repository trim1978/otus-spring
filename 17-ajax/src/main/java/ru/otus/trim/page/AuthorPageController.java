package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.trim.rest.AuthorController;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.exceptions.NotFoundException;

@AllArgsConstructor
@Controller
public class AuthorPageController {
    private final AuthorController authorController;

    @GetMapping("/authors")
    public String listPage() {
        return "author_list";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam("id") int id, Model model) {
        model.addAttribute("author",id > 0 ? authorController.getAuthorByIdInRequest(id) : new AuthorDto (0, ""));
        return "author_edit";
    }

    @PostMapping("/author")
    public String saveAuthor(@ModelAttribute("author") AuthorDto author) {
        authorController.saveAuthor(author);
        return "redirect:/authors";
    }
}
