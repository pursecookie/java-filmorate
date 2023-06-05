DROP TABLE IF EXISTS ratings CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS films_genres CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS friendships CASCADE;


CREATE TABLE ratings(
    rating_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name VARCHAR(50) NOT NULL
);

CREATE TABLE films(
    film_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(200) NOT NULL,
    release_date DATE NOT NULL,
    duration BIGINT NOT NULL,
    rating_id BIGINT NOT NULL REFERENCES ratings(rating_id)
);

CREATE TABLE genres(
    genre_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL
);

CREATE TABLE films_genres(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films(film_id),
    genre_id BIGINT NOT NULL REFERENCES genres(genre_id)
);

CREATE TABLE users(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE likes(
    like_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films(film_id),
    user_id BIGINT NOT NULL REFERENCES users(user_id)
);

CREATE TABLE friendships(
    friendship_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_from BIGINT NOT NULL REFERENCES users(user_id),
    user_to BIGINT NOT NULL REFERENCES users(user_id),
    status BOOLEAN NOT NULL
);