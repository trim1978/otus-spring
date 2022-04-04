package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.exceptions.NotFoundException;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class AuthorPageController {
    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public String listPage(Model model) {
        //model.addAttribute("keywords", "list users in Omsk, omsk, list users, list users free");
        return "author_list";
    }

    @GetMapping("/author")
    public String getAuthor(@RequestParam("id") int id, Model model) {
        model.addAttribute("author",id > 0 ? AuthorDto.toDto (authorRepository.findById(id).orElseThrow(NotFoundException::new)): new AuthorDto (0, ""));
        return "author_edit";
    }

    @PostMapping("/author")
    public String saveAuthor(@ModelAttribute("author") AuthorDto author,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author_edit";
        }
        authorRepository.save(author.toDomainObject());
        return "redirect:/authors";
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Такого автора нет");
    }

}
