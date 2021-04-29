-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 29, 2021 at 04:53 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lms`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookstore`
--

CREATE TABLE `bookstore` (
  `book_id` int(10) NOT NULL,
  `book_name` varchar(50) NOT NULL,
  `book_author` varchar(50) NOT NULL,
  `date` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookstore`
--

INSERT INTO `bookstore` (`book_id`, `book_name`, `book_author`, `date`) VALUES
(1, 'madolduwa', 'matin', '2021-04-29 14:30:02.015307'),
(2, 'wessa', 'sarath', '2021-04-29 14:47:12.338899'),
(3, 'kaluwara', 'rathnayaka', '2021-04-29 14:47:39.587747'),
(4, 'kadu', 'disa sekara', '2021-04-29 14:47:56.580731'),
(5, 'kaluwara', 'mudi', '2021-04-29 14:48:10.983094'),
(6, 'hima wessa', 'mahagama', '2021-04-29 14:49:09.935426');

-- --------------------------------------------------------

--
-- Table structure for table `getbook`
--

CREATE TABLE `getbook` (
  `getbook_id` int(10) NOT NULL,
  `book_id` int(10) NOT NULL,
  `person_id` int(10) NOT NULL,
  `date` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `getbook`
--

INSERT INTO `getbook` (`getbook_id`, `book_id`, `person_id`, `date`) VALUES
(1, 1, 1, '2021-04-29 14:37:47.834464'),
(2, 3, 4, '2021-04-29 14:51:00.205691'),
(3, 6, 1, '2021-04-29 14:51:08.073522'),
(4, 1, 3, '2021-04-29 14:51:14.302364');

-- --------------------------------------------------------

--
-- Table structure for table `hand_over`
--

CREATE TABLE `hand_over` (
  `return_id` int(10) NOT NULL,
  `get_id` int(10) NOT NULL,
  `date` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hand_over`
--

INSERT INTO `hand_over` (`return_id`, `get_id`, `date`) VALUES
(1, 1, '2021-04-29 14:40:11.299969'),
(2, 2, '2021-04-29 14:51:43.894988'),
(3, 4, '2021-04-29 14:51:51.658235'),
(4, 3, '2021-04-29 14:52:05.820055');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` int(10) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `date` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `first_name`, `last_name`, `email`, `password`, `date`) VALUES
(1, 'sunethma', 'sethmini', 'su@gmal.com', '1234', '2021-04-29 14:34:24.257738'),
(2, 'upali', 'werasekara', 'upali@wirasekara', '4561', '2021-04-29 14:49:43.003904'),
(3, 'rathna', 'werasekara', 'rathna@gmail.com', '4561', '2021-04-29 14:50:13.051930'),
(4, 'amali', 'mudi', 'amali@gmail.com', 'dswa', '2021-04-29 14:50:36.646912');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookstore`
--
ALTER TABLE `bookstore`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `getbook`
--
ALTER TABLE `getbook`
  ADD PRIMARY KEY (`getbook_id`),
  ADD KEY `person_id` (`person_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `hand_over`
--
ALTER TABLE `hand_over`
  ADD PRIMARY KEY (`return_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookstore`
--
ALTER TABLE `bookstore`
  MODIFY `book_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `getbook`
--
ALTER TABLE `getbook`
  MODIFY `getbook_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `hand_over`
--
ALTER TABLE `hand_over`
  MODIFY `return_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `getbook`
--
ALTER TABLE `getbook`
  ADD CONSTRAINT `getbook_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `getbook_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `bookstore` (`book_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
