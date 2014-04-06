-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 07, 2014 at 12:24 AM
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
('8105581711', 'Arpith', '2014-03-30 15:38:58', 'This is a test', 340);

-- --------------------------------------------------------

--
-- Table structure for table `donate_item`
--

CREATE TABLE IF NOT EXISTS `donate_item` (
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `description` varchar(280) NOT NULL,
  `value` int(11) NOT NULL
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
  `lng` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `donate_location`
--

INSERT INTO `donate_location` (`phone`, `name`, `address`, `lat`, `lng`) VALUES
('', 'Arpith', 'Boys Hostel, PESIT', 0, 0),
('8105581711', 'null', 'null', 12.863089612940941, 77.66621857881546),
('8105581711', 'Arpith ', 'test2', 12.862268206613386, 77.66635939478874),
('8105581711', '', 'test', 12.853343043353787, 77.67760757356882);

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
('8105581711', '8105581711', 4);

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
  `registered_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `points` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='list of all users';

--
-- Dumping data for table `main_details`
--

INSERT INTO `main_details` (`phone`, `password`, `name`, `dob`, `email`, `registered_on`, `points`) VALUES
('7795613565', 2131231, 'Abiskek', '2014-04-02', 'test@test.com', '2014-04-05 05:15:08', 0),
('8105581711', 3556498, 'Arpith', '2013-11-04', 'arpith@null.net', '0000-00-00 00:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `missing`
--

CREATE TABLE IF NOT EXISTS `missing` (
  `Name` varchar(30) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `dress` varchar(30) NOT NULL,
  `description` varchar(250) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `reportedon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `found` tinyint(4) NOT NULL DEFAULT '0',
  `found_by` varchar(15) NOT NULL,
  `f_lat` double NOT NULL DEFAULT '0',
  `f_lng` double NOT NULL DEFAULT '0',
  `more_info` varchar(280) NOT NULL DEFAULT 'Not Found Yet'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE IF NOT EXISTS `report` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL,
  `incident` varchar(30) NOT NULL,
  `large` int(1) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `damage` int(3) NOT NULL,
  `no_casualty` int(10) NOT NULL DEFAULT '-1',
  `you` tinyint(1) NOT NULL,
  `comments` varchar(140) NOT NULL,
  `done` tinyint(1) NOT NULL,
  `report_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` varchar(30) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`uid`, `phone`, `incident`, `large`, `lat`, `lng`, `damage`, `no_casualty`, `you`, `comments`, `done`, `report_time`, `modified_time`) VALUES
(2, '9818000236', 'Fire', 0, 88.4, 88.4, 0, 0, 0, 'Big Fire', 0, '2014-04-01 13:30:23', ''),
(3, '8105581711', 'fire', 0, 0, 0, 0, 7, 0, 'test', 0, '2014-04-06 17:45:00', ''),
(4, '8105581711', 'fire', 0, 0, 0, 0, 7, 0, 'test', 0, '2014-04-06 17:47:49', ''),
(5, '8105581711', 'zombie apocalyp', 0, 12.870342582731183, 77.67681799829006, 0, 200, 0, 'this is only a test', 0, '2014-04-06 17:55:40', ''),
(6, '8105581711', '', 0, 12.8616471654243, 77.67590839415789, 0, -1, 1, '', 0, '2014-04-06 18:15:45', ''),
(7, '8105581711', '', 0, 12.8616471654243, 77.67590839415789, 0, -1, 1, '', 0, '2014-04-06 18:16:53', ''),
(8, '8105581711', '', 0, 12.8616471654243, 77.67590839415789, 0, -1, 1, '', 0, '2014-04-06 18:16:58', ''),
(9, '8105581711', '', 0, 0, 0, 0, -1, 1, '', 0, '2014-04-06 18:24:21', ''),
(10, '8105581711', '', 0, 0, 0, 0, -1, 1, '', 0, '2014-04-06 18:26:52', ''),
(11, '8105581711', '', 0, 0, 0, 0, -1, 1, '', 0, '2014-04-06 18:28:38', ''),
(12, '8105581711', '', 0, 0, 0, 0, -1, 1, '', 0, '2014-04-06 18:30:11', ''),
(13, '8105581711', 'fire', 0, 12.877834592001411, 77.6654776185751, 0, 200, 0, 'this is a test', 0, '2014-04-06 18:30:32', ''),
(14, '8105581711', 'fire', 0, 12.877834592001411, 77.6654776185751, 0, 200, 0, 'this is a test', 0, '2014-04-06 18:30:48', ''),
(15, '8105581711', 'fire', 0, 12.877834592001411, 77.6654776185751, 0, 200, 0, 'this is a test3', 0, '2014-04-06 18:30:59', ''),
(16, '8105581711', 'zombie apocalypse', 0, 0, 0, 0, 200, 1, 'people! ', 0, '2014-04-06 18:32:18', ''),
(17, '8105581711', '', 0, 0, 0, 0, -1, 1, '', 0, '2014-04-06 18:32:41', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
