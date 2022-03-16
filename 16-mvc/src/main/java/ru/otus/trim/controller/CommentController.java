package ru.otus.trim.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.trim.model.Book;
import ru.otus.trim.repository.AuthorRepository;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.rest.dto.AuthorDto;
import ru.otus.trim.rest.dto.CommentDto;

import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class CommentController {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping("/comments_book")
    public String getCommentsByBook(@RequestParam("book") long bookId, Model model) {
        model.addAttribute("book", bookId);
        Book book = bookRepository.findById(bookId).orElseThrow();
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor().getName());
        model.addAttribute("genre", book.getGenre().getName());
        model.addAttribute("comments",commentRepository.findByBookId(bookId).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList()));
        return "comment_list";
    }

    @GetMapping("/comments_all")
    public String getCommentsByBook(Model model) {
        //model.addAttribute("book", book);
        model.addAttribute("comments",commentRepository.findAll ().stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList()));
        return "comment_all";
    }

    @GetMapping("/comment_edit")
    public String getCommentById(@RequestParam("id") long id, Model model) {
        model.addAttribute("comment",CommentDto.toDto (commentRepository.findById(id).orElseThrow()));
        return "comment_edit";
    }

    @GetMapping("/comment_add")
    public String getCommentByBook(@RequestParam("book") long book, Model model) {
        model.addAttribute("comment", new CommentDto (book));
        return "comment_edit";
    }

    @PostMapping("/comment")
    public String saveComment(@ModelAttribute("comment") CommentDto comment,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "comment_edit";
        }
        commentRepository.save(comment.toDomainObject(bookRepository.getById(comment.getBook())));
        return "redirect:/comments_book/?book="+comment.getBook();
    }

}
