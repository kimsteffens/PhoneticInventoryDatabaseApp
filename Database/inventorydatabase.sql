--  
-- 
/* This file creates the Phonetic Inventory Database. It contains 5 tables. 
*/ 
-- 
-- Drop the tables in case they already exist.
-- 
DROP TABLE languages CASCADE; 

DROP TABLE vowel CASCADE; 

DROP TABLE consonant CASCADE; 

DROP TABLE language_vowel CASCADE; 

DROP TABLE language_consonant CASCADE; 
-- 
-- Create the tables 
-- 
CREATE TABLE languages 
  ( 
     name             VARCHAR(30) PRIMARY KEY, 
     iso639           CHAR(3), 
     family           VARCHAR(30), 
     region           VARCHAR(30)
  ); 
-- 
CREATE TABLE vowel 
  ( 
     vowel_unicode VARCHAR(30) PRIMARY KEY, 
     backness      VARCHAR(10) NOT NULL, 
     height        VARCHAR(10) NOT NULL, 
     rounded       BOOLEAN NOT NULL, 
     nasal         BOOLEAN NOT NULL,
     phonation     VARCHAR(30) NOT NULL,
     length        VARCHAR(10)
  ); 
-- 
CREATE TABLE consonant 
  ( 
     consonant_unicode      VARCHAR(30) PRIMARY KEY, 
     manner                 VARCHAR(30) NOT NULL, 
     place                  VARCHAR(30) NOT NULL, 
     phonation              VARCHAR(30) NOT NULL, 
     airstream_mechanism    VARCHAR(30) NOT NULL, 
     secondary_articulation VARCHAR(30), 
     aspirated              BOOLEAN NOT NULL, 
     length                 VARCHAR(10)
  ); 
-- 
CREATE TABLE language_vowel 
  ( 
     name          VARCHAR(30) REFERENCES languages (name), 
     vowel_unicode VARCHAR(30) REFERENCES vowel (vowel_unicode), 
     PRIMARY KEY (name, vowel_unicode) 
  ); 
-- 
CREATE TABLE language_consonant 
  ( 
     name              VARCHAR(30) REFERENCES languages (name), 
     consonant_unicode VARCHAR(30) REFERENCES consonant (consonant_unicode), 
     PRIMARY KEY (name, consonant_unicode) 
  ); 
-- 
COMMIT; 