/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right name only.
 */
package ru.otus.trim.rest.dto;

import lombok.*;
import ru.otus.trim.model.Book;
import ru.otus.trim.model.Comment;

import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@ToString
@Getter
@Setter
@SuppressWarnings("all")
public class CommentDto {

    private long id;
    private long book;
    private String text;
    private Date datetime;

    public CommentDto(long id, long book, String text, Date datetime) {
        this.id = id;
        this.book = book;
        this.text = text;
        this.datetime = datetime;
    }

    public CommentDto(long book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return id == that.id && book == that.book;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book);
    }

    public Comment toDomainObject(Book book) {
        return new Comment(getId(), getText(), book, new Date ());
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook().getId(), comment.getText(), comment.getDatetime());
    }
}
