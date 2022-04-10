package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.repository.BookRepository;
import ru.otus.trim.repository.CommentRepository;
import ru.otus.trim.rest.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping("/api/comments_book")
    public List<CommentDto> getCommentsByBook (@RequestParam("book") long bookId) {
            return commentRepository.findByBookId(bookId).stream()
                    .map(CommentDto::toDto)
                    .collect(Collectors.toList());
    }
    @DeleteMapping("/api/comment_remove")
    public boolean removeCommentById(@RequestParam(value = "id") long id) {
        commentRepository.deleteById(id);
        return true;
    }

    @PostMapping("/api/comment")
    public CommentDto saveComment(@ModelAttribute("comment") CommentDto comment) {
        return CommentDto.toDto(commentRepository.save(comment.toDomainObject(bookRepository.getById(comment.getBook()))));
    }
}
