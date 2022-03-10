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

import ru.otus.trim.model.Author;

/**
 * DTO that represents Author
 */
@SuppressWarnings("all")
public class AuthorDto {

    private int id;
    private String name;

    public AuthorDto() {
    }

    public AuthorDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Author toDomainObject(AuthorDto dto) {
        return new Author(dto.getId(), dto.getName());
    }

    public static AuthorDto toDto(Author person) {
        return new AuthorDto(person.getId(), person.getName());
    }
}
