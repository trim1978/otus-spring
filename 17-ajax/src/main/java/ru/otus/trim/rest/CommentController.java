package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.rest.dto.BookDto;
import ru.otus.trim.rest.dto.CommentDto;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping("/api/comments_book")
    public List<CommentDto> getCommentsByBook (@RequestParam("book") long bookId, Model model) {
            model.addAttribute("book", bookId);
            Book book = bookRepository.findById(bookId).orElseThrow();
            model.addAttribute("title", book.getTitle());
            model.addAttribute("author", book.getAuthor().getName());
            model.addAttribute("genre", book.getGenre().getName());
            return commentRepository.findByBookId(bookId).stream()
                    .map(CommentDto::toDto)
                    .collect(Collectors.toList());
    }
    @DeleteMapping("/api/comment_remove")
    public boolean removeCommentById(@RequestParam(value = "id") long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        //long book = comment.getBook().getId();
        commentRepository.deleteById(id);
        return true;
    }

}
