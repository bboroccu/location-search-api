CREATE TABLE users
(
    seq bigint NOT NULL auto_increment,
    user_id varchar(20) not null,
    password varchar(255) not null,
    user_role varchar(255) not null,
    create_at timestamp,
    update_at timestamp,
    primary key(seq),
    unique key uk_userid (user_id)
);

CREATE INDEX idx_user_id ON users(user_id);

INSERT INTO users(user_id, password, user_role) VALUES ('bboroccu', '81dc9bdb52d04dc20036dbd8313ed055', 'USER');

CREATE TABLE keyword_rank
(
    seq bigint NOT NULL auto_increment,
    keyword varchar(255) not null,
    counting integer,
    create_at timestamp,
    update_at timestamp,
    primary key(seq),
    unique key uk_keyword (keyword)
);

CREATE INDEX idx_counting_desc ON keyword_rank(counting DESC);