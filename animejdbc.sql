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
INSERT INTO genres (genres) VALUES ('ACTION');
INSERT INTO genres (genres) VALUES ('ADVENTURE');
INSERT INTO genres (genres) VALUES ('COMEDY');
INSERT INTO genres (genres) VALUES ('DRAMA');
INSERT INTO genres (genres) VALUES ('FANTASY');
INSERT INTO genres (genres) VALUES ('ROMANCE');
INSERT INTO genres (genres) VALUES ('MYSTERY');
INSERT INTO genres (genres) VALUES ('SUPERNATURAL');
INSERT INTO genres (genres) VALUES ('SLICE OF LIFE');
INSERT INTO genres (genres) VALUES ('HORROR');
INSERT INTO genres (genres) VALUES ('ECCHI');

-- Inserindo dados na tabela Temas
INSERT INTO themes (themes) VALUES ('ANTHROPOMORPHIC');
INSERT INTO themes (themes) VALUES ('MUSIC');
INSERT INTO themes (themes) VALUES ('ISEKAI');
INSERT INTO themes (themes) VALUES ('HISTORICAL');
INSERT INTO themes (themes) VALUES ('EDUCATIONAL');
INSERT INTO themes (themes) VALUES ('REINCARNATION');
INSERT INTO themes (themes) VALUES ('SPACE');
INSERT INTO themes (themes) VALUES ('ADULT CAST');
INSERT INTO themes (themes) VALUES ('PSYCHOLOGICAL');
INSERT INTO themes (themes) VALUES ('HAREM');
INSERT INTO themes (themes) VALUES ('SCHOOL');
INSERT INTO themes (themes) VALUES ('SUPER POWER');
INSERT INTO themes (themes) VALUES ('SURVIVAL');
INSERT INTO themes (themes) VALUES ('OTAKU CULTURE');

-- Inserindo dados na tabela Público-Alvo
INSERT INTO demographics (demographic) VALUE ('JOSEI');
INSERT INTO demographics (demographic) VALUE ('KIDS');
INSERT INTO demographics (demographic) VALUE ('SEINEN');
INSERT INTO demographics (demographic) VALUE ('SHOUJO');
INSERT INTO demographics (demographic) VALUE ('SHOUNEN');

-- Inserindo dados na tabela Estudio
INSERT INTO studio (studio_name) VALUE ('PIERROT');
INSERT INTO studio (studio_name) VALUE ('UFOTABLE');
INSERT INTO studio (studio_name) VALUE ('MAPPA');
INSERT INTO studio (studio_name) VALUE ('WIT STUDIO');
INSERT INTO studio (studio_name) VALUE ('TOEI ANIMATION');
INSERT INTO studio (studio_name) VALUE ('A-1 PICTURES');
INSERT INTO studio (studio_name) VALUE ('TMS ENTERTAINMENT');
INSERT INTO studio (studio_name) VALUE ('STUDIO DEEN');
INSERT INTO studio (studio_name) VALUE ('KYOTO ANIMATION');
INSERT INTO studio (studio_name) VALUE ('DISCOTEK MEDIA');
INSERT INTO studio (studio_name) VALUE ('MADHOUSE');
INSERT INTO studio (studio_name) VALUE ('BONES');
INSERT INTO studio (studio_name) VALUE ('WHITE FOX');
INSERT INTO studio (studio_name) VALUE ('CLOVERWORKS');

-- Inserindo dados na tabela Anime
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('ONE PUNCH MAN',1,8,3,11);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('BOKU NO HERO ACADEMIA',1,11,5,12);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('KIMETSU NO YAIBA',1,4,5,2);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('KONO SUBARASHII SEKAI NI SHUKUFUKU WO!',2,3,3,8);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('DR STONE',2,13,5,7);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('OVERLORD',2,3,3,11);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('STEINS GATE',4,9,3,13);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('VIOLET EVERGARDEN',4,4,3,9);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('HORIMIYA',6,11,5,14);
INSERT INTO animes (title, genres_id, themes_id, demographics_id, studio_id) VALUE ('SONO BISQUE DOLL WA KOI WO SURU',6,14,3,14);

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

