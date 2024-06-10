-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 30, 2024 at 11:08 PM
-- Server version: 8.0.36-0ubuntu0.20.04.1
-- PHP Version: 7.4.3-4ubuntu2.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hopitale`
--

-- --------------------------------------------------------

--
-- Table structure for table `medicament`
--

CREATE TABLE `medicament` (
  `ref` int NOT NULL,
  `libelle` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicament`
--

INSERT INTO `medicament` (`ref`, `libelle`, `prix`) VALUES
(1234, 'Head', 34),
(4321, 'Hand', 78),
(321, 'fdd', 65);

-- --------------------------------------------------------

--
-- Table structure for table `patientmed`
--

CREATE TABLE `patientmed` (
  `ref` int NOT NULL,
  `cin` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patientmed`
--

INSERT INTO `patientmed` (`ref`, `cin`) VALUES
(1234, 12345678);

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `cin` int NOT NULL,
  `nom` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `prenom` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `genre` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `telephone` int NOT NULL,
  `adresse` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`cin`, `nom`, `prenom`, `genre`, `telephone`, `adresse`) VALUES
(12345678, 'benHamed', 'Tasnim', 'Tunisia', 231234123, 'Femme'),
(87654321, 'benSalem', 'Sana', 'Femme', 23456789, 'Femme');

-- --------------------------------------------------------

--
-- Table structure for table `personnel`
--

CREATE TABLE `personnel` (
  `cin` int NOT NULL,
  `nom` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `prenom` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `login` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `fonction` varchar(100) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `personnel`
--

INSERT INTO `personnel` (`cin`, `nom`, `prenom`, `login`, `password`, `fonction`) VALUES
(12345678, 'admin', 'admin', 'admin@gmail.com', 'admin123', 'Manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `patientmed`
--
ALTER TABLE `patientmed`
  ADD PRIMARY KEY (`ref`,`cin`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`cin`);

--
-- Indexes for table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`cin`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
