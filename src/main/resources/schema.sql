CREATE TABLE users
(
    seq bigint NOT NULL auto_increment,
    user_id varchar(100) not null,
    passwd varchar(255) not null,
    user_role varchar(255) not null,
    create_date timestamp,
    update_date timestamp,
    primary key(seq)
);

INSERT INTO users(user_id, passwd, user_role) VALUES ('bboroccu', 'd404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db', 'USER');