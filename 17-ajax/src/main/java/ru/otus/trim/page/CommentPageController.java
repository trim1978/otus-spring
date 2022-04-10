package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.rest.dto.CommentDto;

@AllArgsConstructor
@Controller
public class CommentPageController {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping("/comments_book")
    public String getCommentsByBook(@RequestParam("book") long bookId, Model model) {
        model.addAttribute("book", bookId);
        Book book = bookRepository.findById(bookId).orElseThrow();
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor().getName());
        model.addAttribute("genre", book.getGenre().getName());
        return "comment_list";
    }

    @GetMapping("/comments_all")
    public String getCommentsByBook(Model model) {
        return "comment_all";
    }

    @GetMapping("/comment_edit")
    public String getCommentById(@RequestParam("id") long id, Model model) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        model.addAttribute("book_title", comment.getBook().getTitle());
        model.addAttribute("book_author", comment.getBook().getAuthor().getName());
        model.addAttribute("comment", CommentDto.toDto (comment));
        return "comment_edit";
    }

    @GetMapping("/comment_add")
    public String getCommentByBook(@RequestParam("book") long bookId, Model model) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("book_author", book.getAuthor().getName());
        model.addAttribute("comment", new CommentDto (bookId));
        return "comment_edit";
    }

    @PostMapping("/comment")
    public String saveComment(@ModelAttribute("comment") CommentDto comment,
                             BindingResult bindingResult, Model model) {
        commentRepository.save(comment.toDomainObject(bookRepository.getById(comment.getBook())));
        return "redirect:/comments_book/?book="+comment.getBook();
    }

}
