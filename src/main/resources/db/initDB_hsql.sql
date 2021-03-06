DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS game_x_genres;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE game
(
    id        INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    developer VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX game_unique_name_and_developer_idx ON game (name, developer);

CREATE TABLE genres
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX genre_unique_name_idx ON genres (name);

CREATE TABLE game_x_genres
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    game_id  INTEGER NOT NULL REFERENCES game (id),
    genre_id INTEGER NOT NULL REFERENCES genres (id)
);
CREATE UNIQUE INDEX game_x_genre_unique_game_id_and_genre_id_idx ON game_x_genres (game_id, genre_id);

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);