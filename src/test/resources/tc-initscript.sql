drop table IF exists document;
create table document (
  id         SERIAL PRIMARY KEY,
  title      VARCHAR(30)
);

insert into document(id,title) values (1,'A');
insert into document(id,title) values (2,'B');
insert into document(id,title) values (3,'C');
insert into document(id,title) values (4,'D');
insert into document(id,title) values (5,'E');
insert into document(id,title) values (6,'F');
insert into document(id,title) values (7,'G');
insert into document(id,title) values (8,'H');
