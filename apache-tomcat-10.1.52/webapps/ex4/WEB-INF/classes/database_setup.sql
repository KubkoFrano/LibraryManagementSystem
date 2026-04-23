CREATE DATABASE school;
USE school;

CREATE TABLE student (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100)
);

INSERT INTO student VALUES (1, 'Alice'), (2, 'Bob'), (3, 'Charlie'), (4, 'Diana'), (5, 'Edward');