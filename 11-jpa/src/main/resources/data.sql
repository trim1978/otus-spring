insert into authors (id, `name`) values (1, 'Pushkin');
insert into authors (id, `name`) values (2, 'Lermontov');
insert into authors (id, `name`) values (3, 'Barto');

insert into genres (id, name) values (1, 'adventures');
insert into genres (id, name) values (2, 'horror');
insert into genres (id, name) values (3, 'fantasy');
insert into genres (id, name) values (4, 'lyric');
insert into genres (id, name) values (5, 'thriller');
insert into genres (id, name) values (6, 'drama');
insert into genres (id, name) values (7, 'comedy');

insert into books (id, title) values (1, 'Metel');
insert into books (id, title) values (2, 'Mciri');

insert into book_authors (book, author) values (1, 1);
insert into book_authors (book, author) values (2, 2);

insert into book_genres (book, genre) values (1, 4);
insert into book_genres (book, genre) values (2, 4);


insert into comments (id, text, book, datetime) values (1, 'wow', 2, CAST('2022-02-08 12:35:29' AS datetime));
insert into comments (id, text, book, datetime) values (2, 'super', 2, CAST('2022-02-08 12:36:29' AS datetime));
insert into comments (id, text, book, datetime) values (3, 'like', 1, CAST('2022-02-08 12:37:29' AS datetime));
