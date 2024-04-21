CREATE DATABASE IF NOT EXISTS Friends_Of_Human;

CREATE TABLE Zoo (
    animal_id INT AUTO_INCREMENT PRIMARY KEY,
    animal_type VARCHAR(20), 
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE
);

CREATE TABLE Pets (
    pet_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE
);

CREATE TABLE Pack_Animals (
    pack_animal_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE
);

INSERT INTO Pets (name, command, birth_date) VALUES
('Rex', 'Fetch', '2019-05-12'),
('Whiskers', 'Play with toy mouse', '2020-01-03'),
('Fluffy', 'Run on wheel', '2018-11-20');


INSERT INTO Pack_Animals (name, command, birth_date) VALUES
('Thunder', 'Carry loads', '2017-08-25'),
('Bella', 'Transport goods', '2016-12-10'),
('Max', 'Assist with farming', '2015-10-15');

DELETE FROM Pack_Animals WHERE name = 'Camels';

CREATE TABLE Equines (
    equine_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE
);

INSERT INTO Equines (name, command, birth_date)
SELECT name, command, birth_date FROM Pack_Animals WHERE name IN ('Horses', 'Donkeys');

DELETE FROM Pack_Animals WHERE name IN ('Horses', 'Donkeys');

CREATE TABLE Young_Animals (
    young_animal_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE,
    age_at_registration VARCHAR(20)
);

INSERT INTO Young_Animals (name, command, birth_date, age_at_registration)
SELECT name, command, birth_date, CONCAT(TIMESTAMPDIFF(YEAR, birth_date, CURDATE()), ' years ', 
                                        TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) % 12, ' months') AS age
FROM Pets
WHERE birth_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 YEAR) AND DATE_SUB(CURDATE(), INTERVAL 1 YEAR);

CREATE TABLE All_Animals (
    animal_id INT AUTO_INCREMENT PRIMARY KEY,
    animal_type VARCHAR(20), 
    name VARCHAR(50),
    command VARCHAR(100),
    birth_date DATE,
    age_at_registration VARCHAR(20)
);

INSERT INTO All_Animals (animal_type, name, command, birth_date, age_at_registration)
SELECT 'Pet', name, command, birth_date, '' FROM Pets;

INSERT INTO All_Animals (animal_type, name, command, birth_date, age_at_registration)
SELECT 'Pack Animal', name, command, birth_date, '' FROM Pack_Animals;

INSERT INTO All_Animals (animal_type, name, command, birth_date, age_at_registration)
SELECT 'Young Animal', name, command, birth_date, age_at_registration FROM Young_Animals;
