DELETE FROM game_x_genre;
DELETE FROM game;
DELETE FROM genre;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, registered)
VALUES  ('admin', 'admin@gmail.com', 'admin', '2021-12-02 18:00:00'),              --100000
        ('user', 'user@gmail.com', 'user', '2021-12-02 18:01:00'),                 --100001
        ('second_user', 'second@gmail.com', 'second_pass', '2021-12-01 18:02:00'), --100002
        ('third_user', 'third@gmail.com', 'third_pass', '2021-12-01 18:03:00');    --100003

INSERT INTO genre (name)
VALUES  ('action'),              --100004
        ('rts'),                 --100005
        ('adventure'),           --100006
        ('horror');              --100007

INSERT INTO game (name, developer)
VALUES  ('half-life', 'valve'),      --100008
        ('silent hill', 'konami'),   --100009
        ('warcraft', 'blizzard'),    --100010
        ('starcraft', 'blizzard');   --100011

INSERT INTO game_x_genre (game_id, genre_id)
VALUES  (100008, 100004),       --100012
        (100008, 100007),       --100013
        (100009, 100006),       --100014
        (100009, 100007),       --100015
        (100010, 100005),       --100016
        (100011, 100005);       --100017