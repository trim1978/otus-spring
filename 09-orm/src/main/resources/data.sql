insert into authors (id, `name`) values (1, 'Pushkin');
insert into authors (id, `name`) values (2, 'Lermontov');
insert into authors (id, `name`) values (3, 'Barto');

insert into GENRES (id, name) values (1, 'adventures');
insert into GENRES (id, name) values (2, 'horror');
insert into GENRES (id, name) values (3, 'fantasy');
insert into GENRES (id, name) values (4, 'lyric');
insert into GENRES (id, name) values (5, 'thriller');
insert into GENRES (id, name) values (6, 'drama');
insert into GENRES (id, name) values (7, 'comedy');

insert into Books (id, title, author, genre) values (1, 'Metel', 1, 6);
insert into Books (id, title, author, genre) values (2, 'Mciri', 2, 4);

insert into COMMENTS (id, text, book, datetime) values (1, 'wow', 2, CAST('2022-02-08 12:35:29' AS datetime));
insert into COMMENTS (id, text, book, datetime) values (2, 'super', 2, CAST('2022-02-08 12:36:29' AS datetime));
insert into COMMENTS (id, text, book, datetime) values (3, 'like', 1, CAST('2022-02-08 12:37:29' AS datetime));
