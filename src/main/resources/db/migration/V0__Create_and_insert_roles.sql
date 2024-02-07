create table role
(
    id        bigserial,
    authority varchar(255) not null,
    primary key (id)
);

INSERT INTO role(id, authority) VALUES (1,'ADMIN');
INSERT INTO role(id, authority) VALUES (2,'USER');


