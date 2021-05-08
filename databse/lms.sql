-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2021 at 08:58 PM
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
(3, 'kalu', 'rathnayaka', '2021-04-29 14:47:39.587747'),
(4, 'kadu', 'disa sekara', '2021-04-29 14:47:56.580731'),
(5, 'kaluwara', 'mudi', '2021-04-29 14:48:10.983094'),
(6, 'hima wessa', 'mahagama', '2021-04-29 14:49:09.935426'),
(7, '121212', 'manjula', '2021-04-30 06:07:42.754954');

-- --------------------------------------------------------

--
-- Table structure for table `email`
--

CREATE TABLE `email` (
  `date` date NOT NULL DEFAULT current_timestamp(),
  `email` varchar(50) NOT NULL,
  `book_id` int(10) NOT NULL,
  `email_send_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `getbook`
--

CREATE TABLE `getbook` (
  `getbook_id` int(10) NOT NULL,
  `book_id` int(10) NOT NULL,
  `person_id` int(10) NOT NULL,
  `date` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hand_over`
--

CREATE TABLE `hand_over` (
  `return_id` int(10) NOT NULL,
  `get_id` int(10) NOT NULL,
  `p_id` int(10) DEFAULT NULL,
  `date` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hand_over`
--

INSERT INTO `hand_over` (`return_id`, `get_id`, `p_id`, `date`) VALUES
(66, 10, 2, '2021-05-06 16:28:40.842856'),
(67, 12, 2, '2021-05-06 16:31:23.204094'),
(68, 15, 1, '2021-05-07 19:27:43.222424'),
(69, 16, 1, '2021-05-07 19:27:47.576246'),
(70, 1, 3, '2021-05-08 18:36:55.202831'),
(71, 18, NULL, '2021-05-08 18:50:30.735628'),
(72, 6, NULL, '2021-05-08 18:50:43.926750');

-- --------------------------------------------------------

--
-- Table structure for table `password`
--

CREATE TABLE `password` (
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `password`
--

INSERT INTO `password` (`user_name`, `password`) VALUES
('user', '1234');

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
  `date` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `first_name`, `last_name`, `email`, `password`, `date`) VALUES
(1, 'sunethma', 'sethmini', 's', '1', '2021-04-29'),
(2, 'upali', 'werathunga', '', '123', '2021-04-29'),
(3, 'rathna', 'werasekara', 'gakifi1424@animex98.com', '456', '2021-04-29'),
(4, 'amali', 'mudi', 'amali@gmail.com', 'dswa', '2021-04-29'),
(5, 'amara', 'hhh', 'tdmjerwxluppbvajsk@twzhhq.online', 'asdeee', '2021-04-30'),
(6, 'kumudu', 'kumar', 'kumudu@hotmail.com', '123', '2021-05-06'),
(7, 'mali', 'mal', 'mal@gmail.com', '1234', '2021-05-06'),
(8, 'mm', 'mmm', 'nn', '123', '2021-05-06');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookstore`
--
ALTER TABLE `bookstore`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`email_send_id`),
  ADD KEY `email` (`email`) USING BTREE;

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
  ADD PRIMARY KEY (`return_id`),
  ADD KEY `p_id` (`p_id`);

--
-- Indexes for table `password`
--
ALTER TABLE `password`
  ADD PRIMARY KEY (`user_name`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookstore`
--
ALTER TABLE `bookstore`
  MODIFY `book_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `email`
--
ALTER TABLE `email`
  MODIFY `email_send_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `getbook`
--
ALTER TABLE `getbook`
  MODIFY `getbook_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `hand_over`
--
ALTER TABLE `hand_over`
  MODIFY `return_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `getbook`
--
ALTER TABLE `getbook`
  ADD CONSTRAINT `getbook_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `getbook_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `bookstore` (`book_id`);

--
-- Constraints for table `hand_over`
--
ALTER TABLE `hand_over`
  ADD CONSTRAINT `hand_over_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `person` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
