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

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@SuppressWarnings("all")
public class BookDto {

    private long id;
    private String title;

//    public static Book toDomainObject(BookDto dto) {
//        return new Book(dto.getId(), dto.getTitle());
//    }
//
//    public static BookDto toDto(Book person) {
//        return new BookDto(person.getId(), person.getTitle());
//    }
}
