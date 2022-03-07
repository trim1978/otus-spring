insert into author (id, `name`) values (1, 'Pushkin');
insert into author (id, `name`) values (2, 'Lermontov');
insert into author (id, `name`) values (3, 'Barto');

insert into GENRE (id, name) values (1, 'adventures');
insert into GENRE (id, name) values (2, 'horror');
insert into GENRE (id, name) values (3, 'fantasy');
insert into GENRE (id, name) values (4, 'lyric');
insert into GENRE (id, name) values (5, 'thriller');
insert into GENRE (id, name) values (6, 'drama');
insert into GENRE (id, name) values (7, 'comedy');

insert into Book (id, title, author, genre) values (1, 'Metel', 1, 4);
insert into Book (id, title, author, genre) values (2, 'Mciri', 2, 4);

insert into COMMENT (id, text, book, datetime) values (1, 'wow', 2, CAST('2022-02-08 12:35:29' AS datetime));
insert into COMMENT (id, text, book, datetime) values (2, 'super', 2, CAST('2022-02-08 12:36:29' AS datetime));
insert into COMMENT (id, text, book, datetime) values (3, 'like', 1, CAST('2022-02-08 12:37:29' AS datetime));
