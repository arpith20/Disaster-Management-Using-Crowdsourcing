-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2014 at 02:46 PM
-- Server version: 5.5.37-ndb-7.2.16-cluster-gpl
-- PHP Version: 5.5.9-1ubuntu4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dmucs`
--
CREATE DATABASE IF NOT EXISTS `dmucs` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dmucs`;

-- --------------------------------------------------------

--
-- Table structure for table `donate`
--

CREATE TABLE IF NOT EXISTS `donate` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uniqueid` varchar(15) NOT NULL,
  `name` varchar(30) NOT NULL,
  `createdon` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(280) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `donate_item`
--

CREATE TABLE IF NOT EXISTS `donate_item` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `description` varchar(280) NOT NULL,
  `value` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `donate_money`
--

CREATE TABLE IF NOT EXISTS `donate_money` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) NOT NULL,
  `phone_by` varchar(15) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `missing`
--

CREATE TABLE IF NOT EXISTS `missing` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
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
  `more_info` varchar(280) NOT NULL DEFAULT 'Not Found Yet',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

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
  `vote` int(11) NOT NULL DEFAULT '0',
  `report_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` varchar(30) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

-- --------------------------------------------------------

--
-- Table structure for table `report_nodup`
--

CREATE TABLE IF NOT EXISTS `report_nodup` (
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
  `vote` int(11) NOT NULL DEFAULT '0',
  `report_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` varchar(30) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey`
--

CREATE TABLE IF NOT EXISTS `survey` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(400) NOT NULL,
  `yes` int(11) NOT NULL DEFAULT '0',
  `no` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
