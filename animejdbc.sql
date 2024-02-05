DROP DATABASE IF EXISTS animejdbc;
CREATE DATABASE IF NOT EXISTS animejdbc;
USE animejdbc;

-- Criando a tabela de animes;
CREATE TABLE IF NOT EXISTS animes (
anime_id INT AUTO_INCREMENT NOT NULL,
title VARCHAR(255) NOT NULL,
genres_id INT NOT NULL,
themes_id INT NOT NULL,
demographics_id INT NOT NULL,
studio_id INT NOT NULL,
PRIMARY KEY (anime_id)
); 

-- Criando a tabela de generos;
CREATE TABLE IF NOT EXISTS genres(
genres_id INT AUTO_INCREMENT NOT NULL,
genres VARCHAR(30) NOT NULL,
PRIMARY KEY (genres_id)
);

-- Criando a tabela de temas
CREATE TABLE IF NOT EXISTS themes(
themes_id INT AUTO_INCREMENT NOT NULL,
themes VARCHAR(255) NOT NULL,
PRIMARY KEY(themes_id)
);

-- Criando a tabela de público-alvo (infantil, adulto e entre outros)
CREATE TABLE IF NOT EXISTS demographics(
demographics_id INT AUTO_INCREMENT NOT NULL,
demographic VARCHAR(30) NOT NULL,
PRIMARY KEY (demographics_id)
);

-- Criando tabela de estúdios;
CREATE TABLE IF NOT EXISTS studio(
studio_id INT AUTO_INCREMENT NOT NULL,
studio_name VARCHAR(60) NOT NULL,
PRIMARY KEY (studio_id)
);

-- Relacionamento entre anime e gênero
CREATE TABLE IF NOT EXISTS anime_genres(
anime_id INT,
genres_id INT,
FOREIGN KEY (anime_id) REFERENCES animes (anime_id),
FOREIGN KEY (genres_id) REFERENCES genres (genres_id)
);

-- Relacionamento entre anime e público-alvo
CREATE TABLE IF NOT EXISTS anime_demographics(
anime_id INT,
demographics_id INT,
FOREIGN KEY (anime_id) REFERENCES animes (anime_id),
FOREIGN KEY (demographics_id) REFERENCES demographics (demographics_id)
);

-- Relacionamento entre anime e studio
CREATE TABLE IF NOT EXISTS anime_studio(
anime_id INT,
studio_id INT,
FOREIGN KEY (anime_id) REFERENCES animes (anime_id),
FOREIGN KEY (studio_id) REFERENCES studio (studio_id)
);

-- Relacionamento entre anime e temas
CREATE TABLE IF NOT EXISTS anime_themes(
anime_id INT,
themes_id INT,
FOREIGN KEY (anime_id) REFERENCES animes (anime_id),
FOREIGN KEY (themes_id) REFERENCES themes (themes_id)
);

-- Inserindo dados na tabela Gênero
INSERT INTO genres (genres) VALUES ('Action');
INSERT INTO genres (genres) VALUES ('Adventure');
INSERT INTO genres (genres) VALUES ('Comedy');
INSERT INTO genres (genres) VALUES ('Drama');
INSERT INTO genres (genres) VALUES ('Fantasy');
INSERT INTO genres (genres) VALUES ('Romance');
INSERT INTO genres (genres) VALUES ('Mystery');
INSERT INTO genres (genres) VALUES ('Supernatural');
INSERT INTO genres (genres) VALUES ('Slice of Life');
INSERT INTO genres (genres) VALUES ('Horror');
INSERT INTO genres (genres) VALUES ('Ecchi');

-- Inserindo dados na tabela Temas
INSERT INTO themes (themes) VALUES ('Anthropomorphic');
INSERT INTO themes (themes) VALUES ('Music');
INSERT INTO themes (themes) VALUES ('Isekai');
INSERT INTO themes (themes) VALUES ('Historical');
INSERT INTO themes (themes) VALUES ('Educational');
INSERT INTO themes (themes) VALUES ('Reincarnation');
INSERT INTO themes (themes) VALUES ('Space');
INSERT INTO themes (themes) VALUES ('Adult Cast');
INSERT INTO themes (themes) VALUES ('Psychological');
INSERT INTO themes (themes) VALUES ('Harem');
INSERT INTO themes (themes) VALUES ('School');
INSERT INTO themes (themes) VALUES ('Super Power');
INSERT INTO themes (themes) VALUES ('Survival');
INSERT INTO themes (themes) VALUES ('Otaku Culture');

-- Inserindo dados na tabela Público-Alvo
INSERT INTO demographics (demographic) VALUE ('Josei');
INSERT INTO demographics (demographic) VALUE ('Kids');
INSERT INTO demographics (demographic) VALUE ('Seinen');
INSERT INTO demographics (demographic) VALUE ('Shoujo');
INSERT INTO demographics (demographic) VALUE ('Shounen');

-- Inserindo dados na tabela Estudio
INSERT INTO studio (studio_name) VALUE ('Pierrot');
INSERT INTO studio (studio_name) VALUE ('Ufotable');
INSERT INTO studio (studio_name) VALUE ('MAPPA');
INSERT INTO studio (studio_name) VALUE ('Wit Studio');
INSERT INTO studio (studio_name) VALUE ('Toei Animation');
INSERT INTO studio (studio_name) VALUE ('A-1 Pictures');
INSERT INTO studio (studio_name) VALUE ('TMS Entertainment');
INSERT INTO studio (studio_name) VALUE ('Studio Deen');
INSERT INTO studio (studio_name) VALUE ('Kyoto Animation');
INSERT INTO studio (studio_name) VALUE ('Discotek Media');
INSERT INTO studio (studio_name) VALUE ('MadHouse');
INSERT INTO studio (studio_name) VALUE ('Bones');
INSERT INTO studio (studio_name) VALUE ('White Fox');
INSERT INTO studio (studio_name) VALUE ('CloverWorks');

-- Inserindo dados na tabela Anime
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('One Punch Man',1,8,3,11);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Boku No Hero Academia',1,11,5,12);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Kimetsu No Yaiba',1,4,5,2);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Kono Subarashii Sekai ni Shukufuku wo!',2,3,3,8);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Dr Stone',2,13,5,7);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Overlord',2,3,3,11);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Steins Gate',4,9,3,13);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Violet Evergarden',4,4,3,9);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Horimiya',6,11,5,14);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('Sono Bisque Doll wa Koi wo Suru',6,14,3,14);

-- Inserindo dados na tabela relacional anime e genero
INSERT INTO anime_genres (anime_id, genres_id) 
VALUE (1,1), (2,1), (3,1), (4,2), (5,2), (6,2), (7,4), (8,4), (9,6), (10,6);

-- Inserindo dados na tabela relacional anime e público-alvo
INSERT INTO anime_demographics (anime_id, demographics_id) 
VALUE (1,3), (2,5), (3,5), (4,3), (5,5), (6,3), (7,3), (8,3), (9,5), (10,3);

-- Inserindo dados na tabela relacional anime e studio
INSERT INTO anime_studio (anime_id, studio_id) 
VALUE (1,11), (2,12), (3,2), (4,8), (5,7), (6,11), (7,13), (8,9), (9,14), (10,14);

-- Inserindo dados na tabela relacional anime e temas
INSERT INTO anime_themes (anime_id, themes_id) 
VALUE (1,8), (2,11), (3,4), (4,3), (5,13), (6,3), (7,9), (8,4), (9,11), (10,14);

