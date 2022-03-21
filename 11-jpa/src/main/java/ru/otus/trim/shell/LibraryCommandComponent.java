package ru.otus.trim.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.trim.model.Author;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;
import ru.otus.trim.model.Genre;
import ru.otus.trim.service.LibraryOutService;
import ru.otus.trim.service.LibraryService;

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
    public void removeBook(long bookID) {
        library.removeBookById(bookID);
    }

    @ShellMethod(value = "Get book", key = {"get_book", "gb"})
    public String getBook(long bookID) {
        Book book = library.getBookById(bookID);
        if (book != null) {
            return out.getBookString(book);
        }
        return "";
    }

    @ShellMethod(value = "Add book", key = {"add_book", "ab"})
    public String addBook(String title, String author, String genre) {
        return out.getBookString(library.addBook(title, author, genre));
    }

    @ShellMethod(value = "Get all authors", key = {"get_authors", "gaa"})
    public StringBuilder getAuthors() {
        StringBuilder l = new StringBuilder();
        for (Author author : library.getAuthors()){
            l.append(out.getAuthorString(author));
            l.append('\n');
        }
        return l;
    }

    @ShellMethod(value = "Get all genres", key = {"get_genres","gag"})
    public StringBuilder getGenres() {
        StringBuilder l = new StringBuilder();
        for (Genre genre : library.getGenres()){
            l.append(out.getGenreString(genre));
            l.append('\n');
        }
        return l;
    }

    @ShellMethod(value = "Get all books", key = {"get_books","gab"})
    public StringBuilder getBooks() {
        StringBuilder l = new StringBuilder();
        for (Book book : library.getBooks()){
            l.append(out.getBookString(book));
            l.append('\n');
        }
        return l;
    }

    @ShellMethod(value = "Get all comments", key = {"get_comments","gac"})
    public StringBuilder getComments(long bookID) {
        StringBuilder l = new StringBuilder();
        for (Comment comment : library.getComments(bookID)){
            l.append(out.getCommentString(comment));
            l.append('\n');
        }
        return l;
    }

    @ShellMethod(value = "Add comment", key = {"add_comment","ac"})
    public String addComment(long bookID, String text) {
        return out.getCommentString(library.addComment(bookID, text));
    }

    @ShellMethod(value = "Remove comment", key = {"remove_comment","rc"})
    public String removeComment(long commentID) {
        library.removeComment(commentID);
        return String.format("Removed comment id = %d", commentID);
    }

    @ShellMethod(value = "Change comment", key = {"change_comment","cc"})
    public String changeComment(long commentID, String text) {
        Comment comment = library.changeComment(commentID, text);
        return out.getCommentString(comment);
    }

}
