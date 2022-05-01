package ru.otus.trim.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.trim.rest.dto.CommentDto;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class CommentController {
    private final LibraryService library;

    @GetMapping("/api/comments/book={book}")
    public List<CommentDto> getCommentsByBook (@PathVariable("book") long bookId) {
            return library.getComments(bookId).stream()
                    .map(CommentDto::toDto)
                    .collect(Collectors.toList());
    }

    @DeleteMapping("/api/comments/{id}")
    public void removeCommentById(@PathVariable long id) {
        library.removeComment(id);
    }

    @PostMapping("/api/comments")
    public CommentDto saveComment(@ModelAttribute("comment") CommentDto comment) {
        return CommentDto.toDto(comment.getId() == 0 ? library.addComment(comment.getBook(), comment.getText()) : library.changeComment(comment.getId(), comment.getText()));
    }
}
