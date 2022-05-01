package ru.otus.trim.page;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.rest.CommentController;
import ru.otus.trim.rest.dto.CommentDto;
import ru.otus.trim.rest.exceptions.NotFoundException;
import ru.otus.trim.service.LibraryService;

@AllArgsConstructor
@Controller
public class CommentPageController {
    private final LibraryService library;
    private final CommentController commentController;

    @GetMapping("/comments/book={book}")
    public String getCommentsByBook(@PathVariable("book") long bookId, Model model) {
        model.addAttribute("book", bookId);
        Book book = library.getBookById(bookId);
        if (book == null) {
            throw new NotFoundException();
        }
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor().getName());
        model.addAttribute("genre", book.getGenre().getName());
        return "comment_list";
    }

    @GetMapping("/comments/{id}")
    public String getCommentById(@PathVariable long id, Model model) {
        Comment comment = library.getComment(id);

        model.addAttribute("book_title", comment.getBook().getTitle());
        model.addAttribute("book_author", comment.getBook().getAuthor().getName());
        model.addAttribute("comment", CommentDto.toDto (comment));
        return "comment_edit";
    }

    @GetMapping("/comments/add/{book}")
    public String getCommentByBook(@PathVariable long bookId, Model model) {
        Book book = library.getBookById(bookId);

        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("book_author", book.getAuthor().getName());
        model.addAttribute("comment", new CommentDto (bookId));
        return "comment_edit";
    }

    @PostMapping("/comments")
    public String saveComment(@ModelAttribute("comment") CommentDto comment) {
        commentController.saveComment(comment);
        return "redirect:/comments/book="+comment.getBook();
    }

}
