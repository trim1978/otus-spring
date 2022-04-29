package ru.otus.trim.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.service.LibraryOutService;
import ru.otus.trim.service.LibraryService;

import java.util.List;
import java.util.UUID;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommandComponent {

    private final LibraryService library;
    private final LibraryOutService out;

    @ShellMethod(value = "Add author", key = {"ins_author","ia","aa"})
    public String addAuthor(String name) {
        return out.getAuthorString(library.getAuthor(name));
    }

    @ShellMethod(value = "Remove book", key = {"remove_book","rb","db"})
    public String removeBook(String bookId) {
        return String.format("Removed book %s", bookId);
    }

    @ShellMethod(value = "Remove author", key = {"remove_author","ra","da"})
    public String removeAuthor(String author) {
        return String.format("Removed author %s", out.getAuthorString(library.removeAuthor(author)));
    }

    @ShellMethod(value = "Get book", key = {"get_book", "gb"})
    public String getBook(String bookId) {
        Book book = library.getBookById(bookId);
        if (book != null) {
            return out.getBookString(book);
        }
        return "";
    }

    @ShellMethod(value = "Add book", key = {"add_book", "ab"})
    public String addBook(String title, String author, String genre) {
        return out.getBookString(library.addBook(title, author, genre));
    }

    @ShellMethod(value = "Change book", key = {"change_book", "cb"})
    public String changeBook(String bookId, String title, String author, String genre) {
        return out.getBookString(library.changeBook(bookId, title, author, List.of(genre)));
    }

    @ShellMethod(value = "Get all authors", key = {"get_authors", "gaa"})
    public StringBuilder getAuthors() {
        StringBuilder l = new StringBuilder();
        for (Author author : library.getAuthors()){
            if (l.length() > 0) l.append('\n');
            l.append(out.getAuthorString(author));
        }
        return l;
    }

    @ShellMethod(value = "Get all genres", key = {"get_genres","gag"})
    public StringBuilder getGenres() {
        StringBuilder l = new StringBuilder();
        for (String genre : library.getGenres()){
            if (l.length() > 0) l.append('\n');
            l.append(out.getGenreString(genre));
        }
        return l;
    }

    @ShellMethod(value = "Get all books", key = {"get_books","gab"})
    public StringBuilder getBooks() {
        StringBuilder l = new StringBuilder();
        for (Book book : library.getBooks()){
            if (l.length() > 0) l.append('\n');
            l.append(out.getBookString(book));
        }
        return l;
    }

    @ShellMethod(value = "Get all comments", key = {"get_comments","gac"})
    public StringBuilder getComments(String bookId) {
        StringBuilder l = new StringBuilder();
        for (Comment comment : library.getComments(bookId)){
            if (l.length() > 0) l.append('\n');
            l.append(out.getCommentString(comment));
        }
        return l;
    }

    @ShellMethod(value = "Add comment", key = {"add_comment","ac"})
    public String addComment(String bookId, String text) {
        return out.getCommentString(library.addComment(bookId, text));
    }

    @ShellMethod(value = "Remove comment", key = {"remove_comment","rc"})
    public String removeComment(String bookId, String commentId) {
        library.removeComment(bookId, commentId);
        return String.format("Removed comment id = %s", commentId);
    }

    @ShellMethod(value = "Change comment", key = {"change_comment","cc"})
    public String changeComment(String bookId, String commentId, String text) {
        Comment comment = library.changeComment(bookId, commentId, text);
        return out.getCommentString(comment);
    }

    @ShellMethod(value = "Generate books", key = {"generate","gen"})
    public String generateBooks(int count) {
        List<String> genres = library.getGenres();
        if (genres.size() == 0){
            genres.add("drama");
        }
        List<Author> authors = library.getAuthors();
        if (authors.size() == 0){
            authors.add(new Author("Pushkin"));
        }
        for (int i=0; i<count; i++) {
            Book book = library.addBook(UUID.randomUUID().toString(), authors.get((int)(authors.size()*Math.random())).getName(), genres.get((int)(genres.size()*Math.random())));
            int random = (int)(10*Math.random());
            for (int j = 0; j < random; j++){
                library.addComment(book.getId(), "C_"+j+"_"+ UUID.randomUUID());
            }
        }
        return "added " + count + " books";
    }

    @ShellMethod(value = "Get books by genre", key = {"get_genre","gbg"})
    public StringBuilder getBooksByGenre(String genre) {
        StringBuilder l = new StringBuilder();
        for (Book book : library.getBooksByGenre(genre)){
            if (l.length() > 0) l.append('\n');
            l.append(out.getBookString(book));
        }
        return l;
    }
    @ShellMethod(value = "Get books by author", key = {"get_author","gba"})
    public StringBuilder getBooksByAuthor(String author) {
        StringBuilder l = new StringBuilder();
        for (Book book : library.getBooksByAuthor(author)){
            if (l.length() > 0) l.append('\n');
            l.append(out.getBookString(book));
        }
        return l;
    }
}
