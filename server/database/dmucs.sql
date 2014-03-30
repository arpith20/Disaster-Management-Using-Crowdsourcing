-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 30, 2014 at 11:50 PM
-- Server version: 5.5.35-1ubuntu1
-- PHP Version: 5.5.9-1ubuntu2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dmucs`
--

-- --------------------------------------------------------

--
-- Table structure for table `donate`
--

CREATE TABLE IF NOT EXISTS `donate` (
  `uniqueid` varchar(15) NOT NULL,
  `name` varchar(30) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(280) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donate`
--

INSERT INTO `donate` (`uniqueid`, `name`, `createdon`, `description`, `amount`) VALUES
('8105581711', 'Arpith', '2014-03-30 15:38:58', 'This is a test', 322);

-- --------------------------------------------------------

--
-- Table structure for table `donate_item`
--

CREATE TABLE IF NOT EXISTS `donate_item` (
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `description` varchar(280) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `donate_location`
--

CREATE TABLE IF NOT EXISTS `donate_location` (
  `phone` varchar(15) NOT NULL,
  `name` varchar(30) NOT NULL,
  `address` varchar(140) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `donate_money`
--

CREATE TABLE IF NOT EXISTS `donate_money` (
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donate_money`
--

INSERT INTO `donate_money` (`phone`, `phone_by`, `amount`) VALUES
('8105581711', '9818000236', 20),
('8105581711', '9818000236', 20),
('8105581711', '9818000236', 20),
('8105581711', '9818000236', 20),
('8105581711', '9818000236', 20),
('8105581711', '9818000236', 20),
('8105581712', '9818000236', 20),
('8105581712', '9818000236', 20),
('8105581712', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('8105581713', '9818000236', 20),
('4646464', 'null', 20),
('', '8105581711', 20),
('rffg', '8105581711', 20),
('sgshjs', '8105581711', 6),
('sgshjs', '8105581711', 6),
('8105581711', '8105581711', 6),
('8105581711', '8105581711', 6),
('8105581711', '8105581711', 6),
('8105581711', '8105581711', 2);

-- --------------------------------------------------------

--
-- Table structure for table `incident_hash`
--

CREATE TABLE IF NOT EXISTS `incident_hash` (
  `incident` int(3) NOT NULL,
  `incident_char` varchar(30) NOT NULL,
  PRIMARY KEY (`incident`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `phone` varchar(15) NOT NULL,
  `h_lat` double NOT NULL,
  `h_lng` double NOT NULL,
  `c_lat` double NOT NULL,
  `c_lng` double NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `main_details`
--

CREATE TABLE IF NOT EXISTS `main_details` (
  `phone` varchar(15) NOT NULL,
  `password` int(33) NOT NULL,
  `name` varchar(30) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='list of all users';

--
-- Dumping data for table `main_details`
--

INSERT INTO `main_details` (`phone`, `password`, `name`, `dob`, `email`) VALUES
('8105581711', 3556498, 'Arpith', '2013-11-04', 'arpith@null.net');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE IF NOT EXISTS `report` (
  `phone` varchar(15) NOT NULL,
  `incident` int(10) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `damage` int(3) NOT NULL,
  `no_casualty` int(10) NOT NULL DEFAULT '-1',
  `you` tinyint(1) NOT NULL,
  `comments` varchar(140) NOT NULL,
  `done` tinyint(1) NOT NULL,
  `report_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` varchar(30) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
