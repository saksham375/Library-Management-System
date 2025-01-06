-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 23, 2024 at 12:43 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_ms`
--

-- --------------------------------------------------------

--
-- Table structure for table `book_details`
--

CREATE TABLE `book_details` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(250) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book_details`
--

INSERT INTO `book_details` (`book_id`, `book_name`, `author`, `quantity`) VALUES
(1, 'JAVA', 'WILLIAM STALLINGS', 4),
(2, 'PYTHON', 'JK.ROWLINGS', 0),
(3, 'PHP', 'J.K ROWLINGS', 9),
(4, 'HTML', 'J.K ROWLINGS', 9);

-- --------------------------------------------------------

--
-- Table structure for table `issue_book_details`
--

CREATE TABLE `issue_book_details` (
  `id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `book_name` varchar(150) NOT NULL,
  `student_name` varchar(50) NOT NULL,
  `issue_date` date NOT NULL,
  `due_date` date NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `issue_book_details`
--

INSERT INTO `issue_book_details` (`id`, `book_id`, `book_name`, `student_name`, `issue_date`, `due_date`, `status`) VALUES
(1, 1, 'JAVA', 'jofrey', '2023-05-01', '2023-05-24', 'returned'),
(1, 2, 'PYTHON', 'jofrey', '2023-05-01', '2023-05-24', 'pending'),
(1, 4, 'Data Structure with C++', 'jofrey', '2023-05-01', '2023-05-24', 'pending'),
(1, 4, 'Data Structure with C++', 'jofrey', '2023-05-10', '2023-05-31', 'pending'),
(1, 4, 'Data Structure with C++', 'jofrey', '2023-05-02', '2023-05-11', 'pending'),
(1, 3, 'PHP', 'jofrey', '2023-05-17', '2023-05-26', 'pending'),
(2, 1, 'JAVA', 'Neema', '2023-05-02', '2023-05-26', 'returned'),
(2, 1, 'JAVA', 'Neema', '2023-05-02', '2023-05-26', 'returned'),
(2, 1, 'JAVA', 'Neema', '2023-05-09', '2023-05-25', 'pending'),
(2, 1, 'JAVA', 'Neema', '2023-05-01', '2023-05-11', 'pending'),
(2, 4, 'HTML', 'Neema', '2023-05-01', '2023-05-11', 'pending'),
(2, 3, 'PHP', 'Neema', '2023-05-01', '2023-05-11', 'pending'),
(1, 2, 'PYTHON', 'jofrey', '2023-05-01', '2023-05-11', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `student_details`
--

CREATE TABLE `student_details` (
  `student_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `course` varchar(100) DEFAULT NULL,
  `branch` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_details`
--

INSERT INTO `student_details` (`student_id`, `name`, `course`, `branch`) VALUES
(1, 'jofrey', 'BSC', 'CS'),
(2, 'Neema', 'BSC', 'IT'),
(3, 'Michael', 'BSC', 'TAXATION');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(150) NOT NULL,
  `contact` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`, `contact`) VALUES
(1, 'joker', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'jbnyamasheki@gmail.com', '0767413968'),
(2, 'jofrey', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'slymack999@gmail.com', '0767413968'),
(3, 'mwinuka', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'mwinuka@gmail.com', '0712345673'),
(4, 'jerry', '3a5a2512949399115565867a73a413ec6ba215c8f2df385f78b33238a6639b7c', 'jerry@gmail.com', '0767413968');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book_details`
--
ALTER TABLE `book_details`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `student_details`
--
ALTER TABLE `student_details`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book_details`
--
ALTER TABLE `book_details`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `student_details`
--
ALTER TABLE `student_details`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
