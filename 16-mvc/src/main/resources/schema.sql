drop table if exists authors CASCADE;
drop table if exists book_authors CASCADE;
drop table if exists book_genres CASCADE;
drop table if exists books CASCADE;
drop table if exists comments CASCADE;
drop table if exists genres CASCADE;
create table authors (id integer generated by default as identity, name varchar(255), primary key (id));
create table book_authors (book bigint not null, author integer not null);
create table book_genres (book bigint not null, genre integer not null);
create table books (id bigint generated by default as identity, title varchar(255), primary key (id));
create table comments (id bigint generated by default as identity, datetime timestamp, text varchar(255), book bigint not null, primary key (id));
create table genres (id integer generated by default as identity, name varchar(255), primary key (id));
alter table book_authors add constraint FK7y3ox8jn2kngle9eyqxv63ebc foreign key (author) references authors;
alter table book_authors add constraint FKbnp728yyt9yns05hw9wpmccdc foreign key (book) references books;
alter table book_genres add constraint FK6jua1v8ctvomiff1ngyhikus6 foreign key (genre) references genres;
alter table book_genres add constraint FK3f58hp5a75fkpucknjs9pmjuc foreign key (book) references books;
alter table comments add constraint FKp69twy011g2ar5scn9kfhhqg4 foreign key (book) references books;
