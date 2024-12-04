-- Drop database if it exists
DROP DATABASE IF EXISTS Library_Manager;

-- Create Database
CREATE DATABASE Library_Manager;

-- Switch to the newly created database
USE Library_Manager;

-- Create category table
CREATE TABLE category (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Create book table
CREATE TABLE book (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(300) NOT NULL,
    author VARCHAR(200),
    category INT NOT NULL,
    publisher VARCHAR(200),
    `language` VARCHAR(50),
    total INT,
    `current` INT,
    position VARCHAR(100),
    FOREIGN KEY (category) REFERENCES category(id)
);

-- Create user table
CREATE TABLE `user` (
    username VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(50) NOT NULL,
    role BIT NOT NULL, -- 0: user, 1: admin
    name VARCHAR(50),
    sex BIT, -- 0: female, 1: male
    datebirth DATE,
    phone VARCHAR(15),
    gmail VARCHAR(30)
);

-- Create borrower table
CREATE TABLE borrower (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    username VARCHAR(50) NOT NULL,
    book_id INT NOT NULL,
    `from` DATE,
    `to` DATE,
    `status` VARCHAR(20),
    FOREIGN KEY (username) REFERENCES `user`(username),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

-- Create feedback table
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    title VARCHAR(50),
    content TEXT,
    FOREIGN KEY (user_id) REFERENCES `user`(username)
);

-- Insert categories
INSERT INTO category (name) VALUES
('Computer Science'),
('Math'),
('Language'),
('Economic');

-- Insert books
INSERT INTO book (name, author, category, publisher, `language`, total, `current`, position) VALUES
('Code: The Hidden Language of Computer Hardware and Software', 'Charles Petzold', 1, 'Microsoft Press; 1st edition (October 11, 2000)', 'english', 5, 0, 'F34'),
('The Self-Taught Computer Scientist', 'Cory Althoff', 1, 'Wiley; 1st edition (October 1, 2021)', 'english', 4, 1, 'F36'),
('The Chip : How Two Americans Invented the Microchip and Launched a Revolution', 'T. R. Reid', 1, 'Random House Trade Paperbacks; Revised edition (October 9, 2001)', 'english', 3, 2, 'F35'),
('The Second Machine Age: Work, Progress, and Prosperity in a Time of Brilliant Technologies', 'Erik Brynjolfsson', 1, 'giáo dục', 'english', 2, 0, 'A37'),
('The Innovators: How a Group of Hackers, Geniuses, and Geeks Created the Digital Revolution', 'Walter Isaacson', 1, 'Walter Isaacson', 'english', 3, 2, 'E12'),
('A Programmers Guide to Computer Science: A virtual degree for the self-taught developer', 'Dr. William M Springer II', 1, 'Jaxson Media; Illustrated edition (July 28, 2019)', 'english', 3, 1, 'E54'),
('A handbook for teacher research: From design to implementation', 'Colin Lankshear, Michele Knobel', 2, 'Open University Press', 'english', 3, 2, 'B34'),
('A course in phonetics', 'Ladefoged, Peter', 2, 'Cengage Learning, 2011', 'english', 3, 2, 'G54'),
('Language: Its structure and use', 'Finegan, Edward', 2, 'Thomson Wadsworth, c2004,p', 'english', 3, 2, 'S34'),
('An introduction to linguistic theory and language acquisition', 'Crain, Stephen; Lillo-Martin, Diane C. (Diane Carolyn)', 2, 'Wiley, 1999', 'english', 3, 3, 'G54'),
('An introduction to functional grammar', 'Zoe Erotopoulos', 2, 'Arnold, 2004', 'english', 3, 1, 'C54'),
('An introduction to pragmatics: Social action for language teachers', 'Halliday, M. A. K. (Michael Alexander Kirkwood)', 2, 'University of Michigan Press, 2003', 'english', 4, 0, 'D31'),
('500 Activities for the Primary Classroom', 'LoCastro, Virginia.', 3, 'Macmillan Education', 'english', 3, 1, 'D43'),
('The Economics Book', 'Carol Read', 3, 'Dorling Kindersley, 2012', 'english', 3, 2, 'Q21');

-- Insert users
INSERT INTO `user` (username, password, role, name, sex, datebirth, phone, gmail) VALUES
('admin', 'admin', 1, NULL, NULL, NULL, NULL, NULL),
('user1', '123456', 0, 'alex1', 1, '2000-12-23', '0123443789', 'user1@gmail.com'),
('user2', '123456', 0, 'alex2', 1, '2000-12-23', '0195456789', 'user2@gmail.com'),
('user3', '123456', 0, 'alex3', 0, '2000-12-23', '0123493489', 'user3@gmail.com'),
('user4', '123456', 0, 'alex4', 1, '2000-12-23', '0193214789', 'user4@gmail.com');

-- Insert feedback
INSERT INTO feedback (user_id, title, content) VALUES
('user1', 'Đánh giá thư viện', 'Thư viện có rất nhiều đầu sách hay'),
('user2', 'sách thú vị', 'thư viện thiết kế đẹp'),
('user3', 'sách rất bổ ích', 'thư viện rất hữu ích cho sinh viên'),
('user1', 'Góp Ý', 'thư viện nên bổ sung thêm bàn ghế cho sinh viên tự học ạ');
