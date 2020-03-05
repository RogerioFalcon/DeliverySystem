-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 19, 2019 at 02:16 PM
-- Server version: 5.6.45
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qboxus_foodies`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `street` varchar(255) NOT NULL,
  `apartment` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `zip` varchar(11) NOT NULL,
  `country` varchar(45) NOT NULL,
  `lat` decimal(11,8) NOT NULL,
  `long` decimal(11,8) NOT NULL,
  `instructions` varchar(255) NOT NULL,
  `default` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `street`, `apartment`, `city`, `state`, `zip`, `country`, `lat`, `long`, `instructions`, `default`, `created`, `user_id`) VALUES
(1, 'gulbarg', '4ho', 'Faisalabad', 'State', '0', '0', 31.45585210, 73.12991045, '', 0, '2019-10-08 19:08:44', 3),
(2, 'gulbarg', '4ho', 'Faisalabad', 'State', '0', '0', 31.45585210, 73.12991045, '', 0, '2019-10-08 19:21:05', 448),
(3, 'gulbarg', '4ho', 'faisalabad', 'State', '0', '0', 31.45671383, 73.12997717, '', 0, '2019-10-08 19:47:40', 449),
(4, 'main street canal road', '4ho', 'Miami', 'State', '0', '0', 25.76103690, -80.19313138, 'don\'t press red button', 0, '2019-10-10 21:18:43', 3),
(5, 'canal road ', '4ho', 'Faisalabad', 'State', '0', '0', 31.45585897, 73.12990375, 'call me after reach on this address', 0, '2019-10-10 21:19:44', 3),
(6, '28 street jams road', '4th Avin', 'Miami', 'Florida', '38000', 'United States', 25.76210495, -80.19333515, 'dont call', 0, '2019-10-10 23:20:31', 3),
(7, 'E49', '4ho', 'Faisalabad', 'State', '0', '0', 31.45473553, 73.12894486, '', 0, '2019-10-14 17:17:24', 449),
(8, 'Eu labore veniam ve', 'Eum Nisi Dolores Par', 'Miami', 'Florida', '1561213', 'United States', 25.76167980, -80.19179020, 'Eius minima at non ex aliqua Voluptate', 0, '2019-10-14 18:29:58', 448),
(9, 'Mollit labore odio i', 'Proident Ipsam Maio', 'Miami', 'Florida', '4545312', 'United States', 25.79065400, -80.13004550, 'Est dolores in nemo optio', 0, '2019-10-14 18:30:43', 448),
(10, 'la rambla', '4ho', 'Caracas', 'State', '0', '0', 10.48059357, -66.90360647, '', 0, '2019-10-16 07:38:32', 464);

-- --------------------------------------------------------

--
-- Table structure for table `app_slider`
--

CREATE TABLE `app_slider` (
  `id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_slider`
--

INSERT INTO `app_slider` (`id`, `image`) VALUES
(9, 'app/webroot/uploads/2/5c5c8f64a9d39.png'),
(13, 'app/webroot/uploads/2/5c5c912e6d617.png');

-- --------------------------------------------------------

--
-- Table structure for table `banking_info`
--

CREATE TABLE `banking_info` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `transit_no` int(11) NOT NULL,
  `bank_no` int(11) NOT NULL,
  `account_no` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `banking_info`
--

INSERT INTO `banking_info` (`id`, `user_id`, `name`, `transit_no`, `bank_no`, `account_no`) VALUES
(1, 1, 'Acc Holder Name', 1, 123, 123456),
(2, 2, 'Bank Name', 135, 136, 0);

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `conversation_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `read` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `coupon_used`
--

CREATE TABLE `coupon_used` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `coupon_id` int(11) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `currency`
--

CREATE TABLE `currency` (
  `id` int(11) NOT NULL,
  `country` varchar(100) DEFAULT NULL,
  `currency` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL COMMENT 'PKR,USD,CAD',
  `symbol` varchar(100) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `currency`
--

INSERT INTO `currency` (`id`, `country`, `currency`, `code`, `symbol`) VALUES
(1, 'United States', 'INR', 'Rupay', '$');

-- --------------------------------------------------------

--
-- Table structure for table `deal`
--

CREATE TABLE `deal` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float(10,2) NOT NULL,
  `description` text NOT NULL,
  `image` varchar(255) NOT NULL,
  `cover_image` varchar(255) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `starting_time` datetime NOT NULL,
  `ending_time` datetime NOT NULL,
  `promoted` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `deal`
--

INSERT INTO `deal` (`id`, `name`, `price`, `description`, `image`, `cover_image`, `restaurant_id`, `starting_time`, `ending_time`, `promoted`) VALUES
(1, 'Ahmad', 21.00, 'Hsh', 'app/webroot/uploads/2/5da815d028f74.png', 'app/webroot/uploads/2/5da815d0292ff.png', 1, '2019-10-09 00:00:00', '2019-10-24 00:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `facebook`
--

CREATE TABLE `facebook` (
  `id` int(11) NOT NULL,
  `facebook_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `google`
--

CREATE TABLE `google` (
  `id` int(11) NOT NULL,
  `google_id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `open_shift`
--

CREATE TABLE `open_shift` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `starting_time` time NOT NULL,
  `ending_time` time NOT NULL,
  `rider_user_id` int(11) NOT NULL COMMENT 'this column is only for to check if the shift has been assigned to anyone or not',
  `shift` int(2) NOT NULL COMMENT '0 - open, 1- busy',
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `price` float(10,2) NOT NULL,
  `status` int(11) NOT NULL COMMENT '1- active  , 2-completed ,3 accepted , 4 rejected',
  `delivery_fee` float(10,2) NOT NULL,
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `payment_method_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `delivery` int(11) NOT NULL COMMENT '0 - pickup, 1 - delivery',
  `rider_tip` int(11) NOT NULL,
  `tax` float(10,2) NOT NULL,
  `sub_total` float(10,2) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `instructions` text NOT NULL,
  `accepted_reason` text NOT NULL,
  `hotel_accepted` int(2) NOT NULL,
  `cod` int(2) NOT NULL,
  `notification` int(11) NOT NULL,
  `rejected_reason` text NOT NULL,
  `restaurant_delivery_fee` int(11) NOT NULL COMMENT 'restaurent will pay us this delivery fee',
  `total_distance_between_user_and_restaurant` int(11) NOT NULL COMMENT 'total distance between user address and hotel address',
  `delivery_fee_per_km` int(11) NOT NULL COMMENT 'resturent delivery fee per km',
  `delivery_free_range` int(11) NOT NULL COMMENT 'resturent free delivery range',
  `tracking` int(11) NOT NULL,
  `stripe_charge` varchar(200) NOT NULL,
  `device` varchar(10) NOT NULL,
  `version` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id`, `deal_id`, `created`, `price`, `status`, `delivery_fee`, `user_id`, `address_id`, `payment_method_id`, `quantity`, `delivery`, `rider_tip`, `tax`, `sub_total`, `restaurant_id`, `instructions`, `accepted_reason`, `hotel_accepted`, `cod`, `notification`, `rejected_reason`, `restaurant_delivery_fee`, `total_distance_between_user_and_restaurant`, `delivery_fee_per_km`, `delivery_free_range`, `tracking`, `stripe_charge`, `device`, `version`) VALUES
(2, 0, '2019-10-08 19:53:18', 114.98, 2, 0.00, 449, 3, 0, 2, 1, 5, 15.98, 94.00, 1, 'Hxhxb ncjcjx', 'gahha', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(3, 0, '2019-10-08 20:00:18', 114.98, 2, 0.00, 449, 3, 0, 2, 1, 5, 15.98, 94.00, 1, 'Tgjkgdf gh', 'done', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(4, 0, '2019-10-08 20:07:51', 169.12, 2, 0.00, 449, 3, 0, 2, 1, 10, 23.12, 136.00, 1, 'Chinnes', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(5, 0, '2019-10-08 20:12:56', 159.12, 2, 0.00, 449, 3, 0, 2, 1, 0, 23.12, 136.00, 1, '', 'thdhd', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(6, 0, '2019-10-08 20:15:39', 238.68, 2, 0.00, 449, 3, 0, 3, 1, 0, 34.68, 204.00, 1, '', 'vsvs', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(7, 0, '2019-10-08 20:40:31', 766.04, 2, 0.00, 449, 3, 0, 9, 1, 50, 104.04, 612.00, 1, '', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(8, 0, '2019-10-09 12:20:43', 104.28, 2, 0.00, 449, 3, 0, 2, 1, 6, 14.28, 84.00, 1, '', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(9, 0, '2019-10-09 12:33:26', 164.12, 2, 0.00, 449, 3, 0, 2, 1, 5, 23.12, 136.00, 1, '', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(10, 0, '2019-10-09 13:11:34', 91.58, 2, 0.00, 449, 3, 0, 2, 1, 5, 12.58, 74.00, 1, '', 'b nvjvjc', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(11, 0, '2019-10-09 14:10:25', 91.58, 2, 0.00, 449, 3, 0, 2, 1, 5, 12.58, 74.00, 1, '', 'o', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1.0'),
(12, 0, '2019-10-09 14:55:40', 112.64, 2, 0.00, 448, 2, 0, 2, 1, 5, 15.64, 92.00, 1, '', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '2.0.0'),
(13, 0, '2019-10-09 16:47:45', 79.88, 1, 0.00, 448, 2, 0, 2, 1, 5, 10.88, 64.00, 1, '', 'ht', 1, 1, 0, '', 0, 0, 10, 5, 0, '', 'android', '2.0.0'),
(14, 0, '2019-10-09 17:14:02', 76.88, 1, 0.00, 449, 3, 0, 2, 1, 2, 10.88, 64.00, 1, 'Make it spicy', 'trtyghjk', 1, 1, 0, '', 0, 0, 10, 5, 0, '', 'android', '2.0.0'),
(15, 0, '2019-10-10 11:11:23', 79.88, 1, 0.00, 448, 2, 0, 2, 1, 5, 10.88, 64.00, 1, '', 'Will be ready soon', 1, 1, 0, '', 0, 0, 10, 5, 0, '', 'android', '2.0.0'),
(16, 0, '2019-10-10 21:22:31', 72.88, 1, 0.00, 3, 1, 2, 2, 1, 4, 3.90, 64.98, 1, '', 'itreetghj', 1, 0, 0, '', 0, 0, 10, 5, 0, 'ch_1FS4PMJasbCynyt3xmpePVy0', 'android', '2.0.0'),
(17, 0, '2019-10-10 23:09:21', 74.88, 1, 0.00, 3, 0, 0, 2, 0, 0, 10.88, 64.00, 1, '', 'utryetrghj', 1, 1, 0, '', 0, 0, 0, 0, 0, '', 'android', '2.0.0'),
(18, 0, '2019-10-11 02:59:09', 116.00, 1, 0.00, 3, 6, 2, 1, 1, 20, 0.00, 96.00, 1, '', 'sfsdf', 1, 0, 0, '', 0, 0, 10, 5, 0, 'ch_1FS9fHJasbCynyt3Uf3sPYnR', 'website', '0'),
(19, 0, '2019-10-11 12:13:28', 166.46, 1, 0.00, 3, 1, 0, 2, 1, 5, 23.46, 138.00, 1, '', '', 1, 1, 0, '', 0, 0, 10, 5, 0, '', 'android', '2.0.0'),
(20, 0, '2019-10-14 17:16:17', 13.19, 2, 5.00, 449, 3, 0, 1, 1, 0, 1.19, 7.00, 1, '', 'ok', 1, 1, 1, '', 0, 0, 10, 5, 0, '', 'android', '1.1'),
(21, 0, '2019-10-14 18:08:37', 45.95, 2, 5.00, 449, 7, 1, 5, 1, 0, 5.95, 35.00, 1, '', 'ok', 1, 0, 1, '', 0, 0, 10, 5, 0, 'ch_1FTTHuJasbCynyt3Fe94qgYT', 'android', '1.1'),
(22, 0, '2019-10-17 18:16:22', 40.10, 1, 0.00, 3, 6, 0, 2, 1, 5, 5.10, 30.00, 1, '', 'ok', 1, 1, 0, '', 0, 0, 10, 5, 0, '', 'android', '2.0.1'),
(23, 0, '2019-10-17 18:16:57', 159.12, 1, 0.00, 3, 0, 0, 2, 0, 0, 23.12, 136.00, 1, '', '', 1, 1, 0, '', 0, 0, 0, 0, 0, '', 'android', '2.0.1'),
(24, 0, '2019-10-17 22:11:38', 117.00, 1, 0.00, 3, 6, 2, 2, 1, 0, 17.00, 100.00, 1, '', '', 1, 0, 0, '', 0, 0, 10, 5, 0, 'ch_1FUcVkJasbCynyt3MLI2fUBL', 'android', '2.0.1'),
(26, 0, '2019-10-18 15:21:04', 4.68, 1, 0.00, 3, 0, 2, 2, 0, 0, 0.68, 4.00, 8, '', 'test', 1, 0, 0, '', 0, 0, 0, 0, 0, 'ch_1FUsZzJasbCynyt3gcC5Ivjg', 'android', '2.0.2'),
(29, 0, '2019-10-19 14:07:22', 16.38, 1, 0.00, 461, 0, 0, 2, 0, 0, 2.38, 14.00, 1, '', '', 0, 1, 0, '', 0, 0, 0, 0, 0, '', 'android', '1.1');

-- --------------------------------------------------------

--
-- Table structure for table `order_deal`
--

CREATE TABLE `order_deal` (
  `id` int(11) NOT NULL,
  `restaurant_id` int(245) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `instructions` text NOT NULL,
  `price` float(10,2) NOT NULL,
  `delivery_fee` int(11) NOT NULL,
  `rider_tip` int(11) NOT NULL,
  `payment_method_id` int(11) NOT NULL,
  `cod` int(11) NOT NULL,
  `status` int(2) NOT NULL COMMENT '1 - active, 2 - completed',
  `delivery` int(11) NOT NULL COMMENT '0-pickup 1 - delivery',
  `quantity` float(10,2) NOT NULL,
  `hotel_accepted` int(11) NOT NULL,
  `tax` float(10,2) NOT NULL,
  `sub_total` float(10,2) NOT NULL,
  `address_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `order_menu_extra_item`
--

CREATE TABLE `order_menu_extra_item` (
  `id` int(11) NOT NULL,
  `order_menu_item_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_menu_extra_item`
--

INSERT INTO `order_menu_extra_item` (`id`, `order_menu_item_id`, `name`, `quantity`, `price`) VALUES
(1, 1, 'Coke', 1, 1.00),
(2, 1, 'Test 2', 1, 1.00),
(3, 1, 'Hell', 1, 1.00),
(4, 1, 'Hahahaah', 1, 1.00),
(5, 2, '7 Inch - 4 Slices', 1, 10.00),
(6, 2, 'Coke', 1, 5.00),
(7, 3, '7 Inch - 4 Slices', 1, 10.00),
(8, 3, 'Coke', 1, 5.00),
(9, 4, 'Full', 1, 20.00),
(10, 5, 'Full', 1, 20.00),
(11, 6, 'Full', 1, 20.00),
(12, 7, 'Full', 1, 20.00),
(13, 8, '10 Inch - 6 Slices', 1, 5.00),
(14, 8, 'Coke', 1, 5.00),
(15, 9, 'Full', 1, 20.00),
(16, 10, 'Full', 1, 5.00),
(17, 11, 'Full', 2, 5.00),
(18, 12, '10 Inch - 6 Slices', 1, 5.00),
(19, 12, 'Sprite', 1, 9.00),
(20, 13, 'Half', 1, 0.00),
(21, 14, 'Half', 1, 0.00),
(22, 15, 'Half', 2, 0.00),
(23, 16, '13 Inch - 8 Slices', 1, 8.00),
(24, 16, 'Sprite', 1, 9.00),
(25, 18, 'Half', 1, 0.00),
(26, 19, 'Half', 3, 0.00),
(27, 21, 'Full', 1, 5.00),
(28, 22, 'Half', 1, 0.00),
(29, 23, 'Pepsi', 1, 0.00),
(30, 23, 'Coca Cola', 1, 0.00),
(31, 24, 'Pepsi', 1, 0.00),
(32, 24, '7up', 1, 0.00),
(33, 25, 'Small', 1, 3.00),
(34, 25, 'Soft Drink', 1, 3.00),
(35, 26, 'Full', 1, 20.00),
(36, 27, '16 Inch - 12 Slices', 1, 9.00),
(37, 27, 'Sprite', 1, 9.00),
(38, 28, 'Coca Cola', 1, 0.00),
(39, 29, 'Pepsi', 1, 0.00),
(40, 29, '7up', 1, 0.00),
(41, 29, 'Coca Cola', 1, 0.00);

-- --------------------------------------------------------

--
-- Table structure for table `order_menu_item`
--

CREATE TABLE `order_menu_item` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float(10,2) NOT NULL,
  `deal_description` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_menu_item`
--

INSERT INTO `order_menu_item` (`id`, `order_id`, `name`, `quantity`, `price`, `deal_description`) VALUES
(1, 1, 'Quarter Chicken', 2, 14.00, 0),
(2, 2, 'Tandoori Chicken Pizza', 2, 32.00, 0),
(3, 3, 'Tandoori Chicken Pizza', 2, 32.00, 0),
(4, 4, 'Chicken Chili Dry &amp; Rice', 2, 48.00, 0),
(5, 5, 'Chicken Chili Dry &amp; Rice', 2, 48.00, 0),
(6, 6, 'Chicken Chili Dry &amp; Rice', 3, 48.00, 0),
(7, 7, 'Chicken Chili Dry &amp; Rice', 9, 48.00, 0),
(8, 8, 'Tandoori Chicken Pizza', 2, 32.00, 0),
(9, 9, 'Chicken Chili Dry &amp; Rice', 2, 48.00, 0),
(10, 10, 'Chicken Chowmein', 2, 32.00, 0),
(11, 11, 'Chicken Chowmein', 2, 32.00, 0),
(12, 12, 'Tandoori Chicken Pizza', 2, 32.00, 0),
(13, 13, 'Chicken Chowmein', 2, 32.00, 0),
(14, 14, 'Chicken Chowmein', 2, 32.00, 0),
(15, 15, 'Chicken Chowmein', 2, 32.00, 0),
(16, 16, 'Tandoori Chicken Pizza', 1, 32.00, 0),
(17, 16, 'Tamales', 2, 7.99, 0),
(18, 17, 'Masala Rice &amp; Shashlik', 2, 32.00, 0),
(19, 18, 'Chicken Chowmein', 3, 32.00, 0),
(20, 19, 'Chicken Chili Dry &amp; Rice', 1, 48.00, 0),
(21, 19, 'Chicken Chowmein', 2, 32.00, 0),
(22, 19, 'Chicken Chowmein', 2, 32.00, 0),
(23, 20, 'Banana Walnut Bread', 1, 7.00, 0),
(24, 21, 'Banana Walnut Bread', 5, 7.00, 0),
(25, 22, 'Chicken Fajita Pizza', 2, 9.00, 0),
(26, 23, 'Chicken Chili Dry &amp; Rice', 2, 48.00, 0),
(27, 24, 'Tandoori Chicken Pizza', 2, 32.00, 0),
(28, 26, 'Chicken Egg Fried Rice', 2, 2.00, 0),
(29, 29, 'Banana Walnut Bread', 2, 7.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `stripe` varchar(255) NOT NULL,
  `paypal` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `default` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `payment_method`
--

INSERT INTO `payment_method` (`id`, `stripe`, `paypal`, `created`, `user_id`, `default`) VALUES
(1, 'cus_FxEOVwyR09gUtZ', '', '2019-10-08 19:45:13', 449, 1),
(2, 'cus_FxbBskZUaW28Yu', '', '2019-10-09 19:17:24', 3, 0),
(3, 'cus_Fy0HSW3TgNm6xe', '', '2019-10-10 21:14:06', 450, 0),
(8, 'cus_FzRBT69RRk3rR2', '', '2019-10-14 17:06:04', 1, 1),
(9, 'cus_FzSWUTxTijQqLS', '', '2019-10-14 18:28:35', 448, 0),
(10, 'cus_FzSWJUEM16jRqg', '', '2019-10-14 18:28:59', 448, 0),
(11, 'cus_G02Phz3CxauJ7d', '', '2019-10-16 07:33:59', 464, 1);

-- --------------------------------------------------------

--
-- Table structure for table `phone_no_verification`
--

CREATE TABLE `phone_no_verification` (
  `id` int(11) NOT NULL,
  `phone_no` varchar(20) NOT NULL,
  `code` int(4) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `restaurant`
--

CREATE TABLE `restaurant` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `slogan` varchar(255) NOT NULL,
  `about` varchar(500) NOT NULL,
  `speciality` varchar(200) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `timezone` varchar(100) NOT NULL,
  `menu_style` int(11) NOT NULL,
  `promoted` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `preparation_time` tinyint(3) NOT NULL COMMENT 'minutes',
  `min_order_price` decimal(10,2) NOT NULL,
  `delivery_free_range` int(11) NOT NULL COMMENT 'in km',
  `currency_id` int(11) NOT NULL,
  `tax_id` int(11) NOT NULL,
  `tax_free` int(3) NOT NULL COMMENT '1 - tax free',
  `cover_image` varchar(255) NOT NULL,
  `notes` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `added_by` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `google_analytics` int(20) NOT NULL,
  `block` int(11) NOT NULL,
  `single_restaurant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant`
--

INSERT INTO `restaurant` (`id`, `name`, `slogan`, `about`, `speciality`, `phone`, `timezone`, `menu_style`, `promoted`, `image`, `preparation_time`, `min_order_price`, `delivery_free_range`, `currency_id`, `tax_id`, `tax_free`, `cover_image`, `notes`, `user_id`, `added_by`, `created`, `updated`, `google_analytics`, `block`, `single_restaurant`) VALUES
(1, 'Scoma Pizza', 'House of taste', 'Don\'t Cook, Foodpanda Karo! This Month Enjoy Up To 70% Off On 1000+ Deals With Free Delivery! Popular Restaurants', 'Seafood', '+12225559988', '-05:00', 1, 1, 'app/webroot/uploads/1/5d9f71804449c.png', 40, 51.00, 5, 1, 21, 0, 'app/webroot/uploads/1/5d9f7180445a5.png', '', 2, 2, '2019-02-07 12:14:29', '2019-10-19 11:33:24', 21, 0, 0),
(8, 'Howdy\r\n', 'House of taste', 'Howdy About Us', 'Fast Food', '+12225559988', '+03:00', 1, 0, 'app/webroot/uploads/8/5d9f6673d7d27.png', 40, 20.00, 5, 1, 21, 0, 'app/webroot/uploads/8/5d9f66c0a1f38.png', '', 451, 2, '2019-02-07 12:14:29', '2019-10-18 16:13:15', 21, 0, 0),
(9, 'Masooms Coffee & Bake Shop\r\n', 'House of taste', 'Howdy About Us', 'Coffee', '+12225559988', '+03:00', 1, 0, 'app/webroot/uploads/9/5d9f67731425a.png', 40, 20.00, 5, 1, 21, 0, 'app/webroot/uploads/9/5d9f677314409.png', '', 452, 2, '2019-02-07 12:14:29', '2019-10-10 19:15:08', 21, 0, 1),
(10, 'Yellow Plate\r\n', 'House of taste', 'Howdy About Us', 'Pizza', '+12225559988', '+03:00', 1, 0, 'app/webroot/uploads/10/5d9f67d9b65b0.png', 40, 20.00, 5, 1, 21, 0, 'app/webroot/uploads/10/5d9f67d9b66ed.png', '', 453, 2, '2019-02-07 12:14:29', '2019-10-10 18:18:17', 21, 0, 1),
(11, 'Frendy\'s Food', 'House of taste', 'Howdy About Us', 'Pizza', '+12225559988', '+03:00', 1, 0, 'app/webroot/uploads/11/5d9f684e7be22.png', 40, 20.00, 5, 1, 21, 0, 'app/webroot/uploads/11/5d9f684e7c350.png', '', 454, 2, '2019-02-07 12:14:29', '2019-10-11 15:58:24', 21, 1, 1),
(12, 'Downtown China', 'House of taste', 'Howdy About Us', 'Pizza', '+12225559988', '+03:00', 1, 0, 'app/webroot/uploads/12/5d9f68ab99f18.png', 40, 20.00, 5, 1, 21, 0, 'app/webroot/uploads/12/5d9f68ab99fce.png', '', 455, 2, '2019-02-07 12:14:29', '2019-10-11 15:58:21', 21, 1, 1),
(18, 'Xandras Foreman', 'Est duis est suscip', 'Ipsum Dolorum Volupt', 'Et molestiae aut dis', '+11383464612', '-05:00', 0, 0, 'app/webroot/uploads/18/5da9ac92251c2.png', 40, 912.00, 33, 1, 21, 0, 'app/webroot/uploads/18/5da9ac9cdb7f3.png', '', 0, 16, '2019-10-17 12:23:19', '2019-10-18 17:14:20', 0, 1, 0),
(19, 'Xandras Foreman 1', 'tea 2', 'Ipsum Dolorum Volupt 3', 'Et molestiae aut dis 4', '+113834646125', '-05:00', 0, 0, 'app/webroot/uploads/19/5da9a57b7f4e0.png', 40, 990.00, 49, 1, 21, 1, 'app/webroot/uploads/19/5da9c36985010.png', '', 0, 16, '2019-10-18 12:14:45', '2019-10-18 19:01:39', 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_coupon`
--

CREATE TABLE `restaurant_coupon` (
  `id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `coupon_code` varchar(255) NOT NULL,
  `discount` int(11) NOT NULL COMMENT 'value would be in percentage',
  `expire_date` date NOT NULL,
  `type` varchar(11) NOT NULL,
  `limit_users` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_coupon`
--

INSERT INTO `restaurant_coupon` (`id`, `restaurant_id`, `coupon_code`, `discount`, `expire_date`, `type`, `limit_users`) VALUES
(1, 1, '10percent', 10, '2020-02-27', 'android', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_favourite`
--

CREATE TABLE `restaurant_favourite` (
  `id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `favourite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_favourite`
--

INSERT INTO `restaurant_favourite` (`id`, `restaurant_id`, `user_id`, `favourite`) VALUES
(1, 1, 3, 1),
(2, 2, 3, 1),
(4, 1, 449, 1),
(5, 1, 448, 1),
(6, 13, 464, 1),
(7, 8, 465, 1),
(8, 8, 3, 1),
(9, 10, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_location`
--

CREATE TABLE `restaurant_location` (
  `restaurant_id` int(11) NOT NULL,
  `lat` decimal(11,8) NOT NULL,
  `long` decimal(11,8) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `zip` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_location`
--

INSERT INTO `restaurant_location` (`restaurant_id`, `lat`, `long`, `city`, `state`, `country`, `street`, `zip`) VALUES
(1, 31.43827100, 73.13097900, 'Miami', 'Florida', 'United States', '', 94133),
(8, 31.42305500, 73.11059400, '', 'Pakistan', 'United States', '', 94133),
(9, 31.41228800, 73.11441400, '', 'Pakistan', 'United States', '', 94133),
(10, 31.43744700, 73.13964800, '', 'Pakistan', 'United States', '', 94133),
(11, 31.42507000, 73.12080800, '', 'Pakistan', 'United States', '', 94133),
(12, 31.45565900, 73.12342200, '', 'Pakistan', 'United States', '', 94133),
(13, 0.00000000, 0.00000000, 'Miami', 'Florida', 'United States', '', 57709),
(14, 31.22000000, 22.54000000, 'Miami', 'Florida', 'United States', '', 12737),
(15, 234.25000000, 175.21000000, 'Miami', 'Florida', 'United States', '', 70871),
(16, 0.00000000, 0.00000000, 'Miami', 'Florida', 'United States', '', 78648),
(17, 0.00000000, 0.00000000, 'Miami', 'Florida', 'United States', '', 25421),
(18, 0.00000000, 0.00000000, 'Nywork', 'Florida', 'United States', '', 40549),
(19, 7.00000000, 8.00000000, 'Miami', 'Florida', 'United States', '', 6);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_menu`
--

CREATE TABLE `restaurant_menu` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `created` datetime NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '1',
  `has_menu_item` int(11) NOT NULL,
  `index` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_menu`
--

INSERT INTO `restaurant_menu` (`id`, `name`, `description`, `created`, `restaurant_id`, `image`, `active`, `has_menu_item`, `index`) VALUES
(1, 'Pizza', 'Pizza makes anything possible', '2019-10-16 17:19:59', 1, '', 1, 1, 0),
(2, 'Chinies', 'Pizza makes anything possible', '2019-10-08 16:08:10', 1, '', 1, 1, 0),
(3, 'Rice Bowls', 'Rice is great if you really hungary', '2019-10-11 08:52:32', 8, '', 1, 1, 0),
(4, 'Fast Food', 'Life Is better With Burger ', '2019-10-11 10:26:13', 8, '', 1, 1, 0),
(5, 'Bread  Crossiants', 'Baking is love made visible ', '2019-10-11 10:58:59', 9, '', 1, 1, 0),
(6, 'Fries Bucket', 'Fries Before Guys', '2019-10-11 11:23:56', 8, '', 1, 1, 0),
(7, 'Pastries  Brownies', 'Eat more pastries ', '2019-10-11 11:28:27', 9, '', 1, 1, 0),
(8, 'Biscotti  Cookies ', 'Life is short EAT COOKIES for breakfast', '2019-10-11 11:31:18', 9, '', 1, 1, 0),
(9, 'Shawarma', 'Keep Calm &amp; Eat Shawarma', '2019-10-11 11:47:46', 8, '', 1, 1, 0),
(10, 'BURGERS', 'life is better with BURGERS', '2019-10-11 12:03:58', 10, '', 1, 1, 0),
(11, 'SANDWICHES', 'Sandwiches are the toast with the most', '2019-10-11 14:02:04', 10, '', 1, 1, 0),
(12, 'Pizza', 'Pizza makes anything possible', '2019-10-11 14:26:32', 10, '', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_menu_extra_item`
--

CREATE TABLE `restaurant_menu_extra_item` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `created` datetime NOT NULL,
  `active` int(11) NOT NULL DEFAULT '1',
  `restaurant_menu_extra_section_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_menu_extra_item`
--

INSERT INTO `restaurant_menu_extra_item` (`id`, `name`, `price`, `created`, `active`, `restaurant_menu_extra_section_id`) VALUES
(1, '7 Inch - 4 Slices', 10, '2019-10-08 15:49:50', 1, 1),
(2, '10 Inch - 6 Slices', 5, '2019-10-08 15:50:01', 1, 1),
(3, '13 Inch - 8 Slices', 8, '2019-10-08 15:50:13', 1, 1),
(4, '16 Inch - 12 Slices', 9, '2019-10-08 15:50:23', 1, 1),
(5, 'Coke', 5, '2019-10-08 15:50:39', 1, 2),
(6, 'Sprite', 9, '2019-10-08 15:50:45', 1, 2),
(7, 'Fanta', 5, '2019-10-08 15:50:54', 1, 2),
(8, 'full', 20, '2019-10-08 16:06:46', 1, 3),
(9, 'half', 0, '2019-10-08 16:06:37', 1, 3),
(10, 'Half', 0, '2019-10-08 17:07:03', 1, 4),
(11, 'Full', 5, '2019-10-08 17:06:54', 1, 4),
(12, 'Half', 0, '2019-10-08 17:07:54', 1, 5),
(13, 'Full', 5, '2019-10-08 17:08:01', 1, 5),
(14, 'Half', 0, '2019-10-08 17:08:56', 1, 6),
(15, 'Full', 9, '2019-10-08 17:09:12', 1, 6),
(16, 'Small', 3, '2019-10-11 08:40:49', 1, 8),
(17, 'Medium', 5, '2019-10-11 08:41:09', 1, 8),
(18, 'Large', 7, '2019-10-11 08:41:26', 1, 8),
(19, 'Extra Large', 9, '2019-10-11 08:43:10', 1, 8),
(20, 'Soft Drink', 3, '2019-10-11 08:44:44', 1, 9),
(21, 'Mineral Water', 2, '2019-10-11 08:45:09', 1, 9),
(22, 'Pepsi', 0, '2019-10-11 08:56:31', 1, 10),
(23, '7up', 0, '2019-10-11 08:56:46', 1, 10),
(24, 'Coca Cola', 0, '2019-10-11 08:57:05', 1, 10),
(25, 'Pepsi', 0, '2019-10-11 09:01:39', 1, 11),
(26, '7up', 0, '2019-10-11 09:01:53', 1, 11),
(27, 'Coca Cola', 0, '2019-10-11 10:14:03', 1, 11),
(28, 'Pepsi', 0, '2019-10-11 10:16:52', 1, 12),
(29, '7up', 0, '2019-10-11 10:17:21', 1, 12),
(30, 'Coca Cola', 0, '2019-10-11 10:17:51', 1, 12),
(31, 'Pepsi', 0, '2019-10-11 10:19:57', 1, 13),
(32, '7up', 0, '2019-10-11 10:20:09', 1, 13),
(33, 'Coca Cola', 0, '2019-10-11 10:20:47', 1, 13),
(34, 'Pepsi', 0, '2019-10-11 10:28:08', 1, 14),
(35, '7up', 0, '2019-10-11 10:28:20', 1, 14),
(36, 'Coca Cola', 0, '2019-10-11 10:28:30', 1, 14),
(37, 'Pepsi', 0, '2019-10-11 10:30:08', 1, 15),
(38, '7up', 0, '2019-10-11 10:30:22', 1, 15),
(39, 'Coca Cola', 0, '2019-10-11 10:30:32', 1, 15),
(40, 'Pepsi', 0, '2019-10-11 10:42:17', 1, 16),
(41, '7up', 0, '2019-10-11 10:42:26', 1, 16),
(42, 'Coca Cola', 0, '2019-10-11 10:42:51', 1, 16),
(43, 'Pepsi', 0, '2019-10-11 10:45:35', 1, 17),
(44, '7up', 0, '2019-10-11 10:45:45', 1, 17),
(45, 'Coca Cola', 0, '2019-10-11 10:46:20', 1, 17),
(46, 'Pepsi', 0, '2019-10-11 10:48:03', 1, 18),
(47, '7up', 0, '2019-10-11 10:49:22', 1, 18),
(48, 'Coca Cola', 0, '2019-10-11 10:49:33', 1, 18),
(49, 'Per Piece', 1, '2019-10-11 11:08:32', 1, 19),
(50, '6 Pieces', 5, '2019-10-11 11:11:18', 1, 19),
(51, '5 Piece', 1, '2019-10-11 11:14:40', 1, 20),
(52, '10 Pieces', 2, '2019-10-11 11:15:22', 1, 20),
(53, '5 Piece', 2, '2019-10-11 11:17:17', 1, 21),
(54, '10 Pieces', 3, '2019-10-11 11:17:56', 1, 21),
(55, 'Small', 1, '2019-10-11 11:28:27', 1, 22),
(56, 'Large', 2, '2019-10-11 11:28:40', 1, 22),
(57, 'Small', 1, '2019-10-11 11:31:18', 1, 23),
(58, 'Large', 2, '2019-10-11 11:31:30', 1, 23),
(59, 'Small', 1, '2019-10-11 11:33:34', 1, 24),
(60, 'Large', 2, '2019-10-11 11:33:49', 1, 24),
(61, 'Pepsi', 0, '2019-10-11 11:36:11', 1, 25),
(62, '7up', 0, '2019-10-11 11:36:25', 1, 25),
(63, 'Coca Cola', 0, '2019-10-11 11:36:38', 1, 25),
(64, 'Pepsi', 0, '2019-10-11 11:37:09', 1, 26),
(65, '7up', 0, '2019-10-11 11:37:30', 1, 26),
(66, 'Coca Cola', 0, '2019-10-11 11:37:54', 1, 26),
(67, 'Pepsi', 0, '2019-10-11 11:38:29', 1, 27),
(68, '7up', 0, '2019-10-11 11:38:49', 1, 27),
(69, 'Coca Cola', 0, '2019-10-11 11:39:06', 1, 27),
(70, 'Small', 1, '2019-10-11 11:41:02', 1, 28),
(71, 'Large', 2, '2019-10-11 11:41:19', 1, 28),
(72, 'Pepsi', 0, '2019-10-11 11:42:16', 1, 29),
(73, '7up', 0, '2019-10-11 11:42:31', 1, 29),
(74, 'Coca Cola', 0, '2019-10-11 11:42:44', 1, 29),
(75, 'Pepsi', 0, '2019-10-11 11:49:58', 1, 30),
(76, '7up', 0, '2019-10-11 11:50:10', 1, 30),
(77, 'Coca Cola', 0, '2019-10-11 11:50:24', 1, 30),
(78, 'Pepsi', 0, '2019-10-11 11:53:29', 1, 31),
(79, '7up', 0, '2019-10-11 11:53:41', 1, 31),
(80, 'Coca Cola', 0, '2019-10-11 11:53:55', 1, 31),
(81, 'Pepsi', 0, '2019-10-11 11:56:57', 1, 32),
(82, '7up', 0, '2019-10-11 11:57:08', 1, 32),
(83, 'Coca Cola', 0, '2019-10-11 11:57:20', 1, 32),
(84, ' Chicken Zinger', 2, '2019-10-11 12:07:56', 1, 33),
(85, 'Chicken Fillet', 2, '2019-10-11 12:08:31', 1, 33),
(86, 'Grill', 3, '2019-10-11 12:09:03', 1, 33),
(87, ' Beef', 0, '2019-10-11 12:14:17', 1, 34),
(88, 'Chicken', 0, '2019-10-11 12:14:33', 1, 34),
(89, '1000 Island', 0, '2019-10-11 12:13:37', 1, 35),
(90, 'Jalapeno', 0, '2019-10-11 12:14:53', 1, 35),
(91, 'Sriracha', 0, '2019-10-11 12:15:10', 1, 35),
(92, 'Honey Mustard', 0, '2019-10-11 12:15:26', 1, 35),
(93, 'Mayo Mustard', 0, '2019-10-11 12:15:47', 1, 35),
(94, 'Jalapeno', 2, '2019-10-11 12:16:42', 1, 36),
(95, 'Cheese', 2, '2019-10-11 12:17:04', 1, 36),
(96, 'Mushroom', 2, '2019-10-11 12:17:22', 1, 36),
(97, 'Gicker Pickle', 2, '2019-10-11 12:19:45', 1, 36),
(98, 'extra Patty', 2, '2019-10-11 12:20:15', 1, 36),
(99, 'Coke with Fries', 2, '2019-10-11 12:21:54', 1, 37),
(100, 'Sprite with Fries', 2, '2019-10-11 12:22:17', 1, 37),
(101, 'Fanta with Fries', 2, '2019-10-11 12:22:42', 1, 37),
(102, '1000 Island', 0, '2019-10-11 13:10:44', 1, 38),
(103, 'Jalapeno', 0, '2019-10-11 13:11:03', 1, 38),
(104, 'Sriracha', 0, '2019-10-11 13:11:22', 1, 38),
(105, 'Honey Mustard', 0, '2019-10-11 13:11:35', 1, 38),
(106, 'Mayo Mustard', 0, '2019-10-11 13:11:54', 1, 38),
(107, 'Roasted', 0, '2019-10-11 13:12:06', 1, 38),
(108, 'BBQ', 0, '2019-10-11 13:12:23', 1, 38),
(109, 'Wehshi Sauce', 0, '2019-10-11 13:12:39', 1, 38),
(110, 'Jalapeno', 3, '2019-10-11 13:08:23', 1, 39),
(111, 'Cheese', 2, '2019-10-11 13:08:47', 1, 39),
(112, 'Mushroom', 2, '2019-10-11 13:13:14', 1, 39),
(113, 'Gicker Pickle', 2, '2019-10-11 13:13:48', 1, 39),
(114, 'Extra Patty', 2, '2019-10-11 13:14:13', 1, 39),
(115, 'Coke with Fries', 2, '2019-10-11 13:16:21', 1, 40),
(116, 'Sprite with Fries', 2, '2019-10-11 13:16:43', 1, 40),
(117, 'Fanta with Fries', 2, '2019-10-11 13:17:11', 1, 40),
(118, '1000 Island', 0, '2019-10-11 13:40:46', 1, 41),
(119, 'Jalapeno', 0, '2019-10-11 13:41:03', 1, 41),
(120, 'Sriracha', 0, '2019-10-11 13:41:29', 1, 41),
(121, 'Honey Mustard', 0, '2019-10-11 13:41:58', 1, 41),
(122, 'Mayo Mustard', 0, '2019-10-11 13:42:54', 1, 41),
(123, 'Roasted', 0, '2019-10-11 13:43:28', 1, 41),
(124, 'BBQ', 0, '2019-10-11 13:43:50', 1, 41),
(125, 'Wehshi Sauce', 0, '2019-10-11 13:44:10', 1, 41),
(126, 'Jalapeno', 0.4, '2019-10-11 13:46:58', 1, 42),
(127, 'Cheese', 0.4, '2019-10-11 13:47:27', 1, 42),
(128, 'Mashroom', 0.4, '2019-10-11 13:54:54', 1, 42),
(129, 'Gicker Pickle', 0.4, '2019-10-11 13:55:20', 1, 42),
(130, 'Extra Patty', 1, '2019-10-11 13:55:55', 1, 42),
(131, 'Coke with Fries', 1, '2019-10-11 13:57:56', 1, 43),
(132, 'Sprite with Fries', 1, '2019-10-11 13:58:14', 1, 43),
(133, 'Fanta with Fries', 1, '2019-10-11 13:58:42', 1, 43),
(134, 'Regular', 3, '2019-10-11 14:04:00', 1, 44),
(135, 'Giant', 5, '2019-10-11 14:04:41', 1, 44),
(136, '7 inch - 4 slices', 4, '2019-10-11 14:12:45', 1, 45),
(137, '10 inch - 6 slices', 7, '2019-10-11 14:14:02', 1, 45),
(138, '13 inch - 8 Slices', 10, '2019-10-11 14:13:50', 1, 45),
(139, 'BBQ', 1, '2019-10-11 14:15:48', 1, 46),
(140, 'Mild n Wild', 1, '2019-10-11 14:16:19', 1, 46),
(141, 'Mac n Cheese', 1, '2019-10-11 14:16:47', 1, 46),
(142, 'Yellow Plate Special', 1, '2019-10-11 14:17:16', 1, 46),
(143, 'Deep Pan', 0, '2019-10-11 14:18:29', 1, 47),
(144, 'Thin Crust', 0, '2019-10-11 14:18:50', 1, 47),
(145, '7 inch - 4 slices', 3, '2019-10-11 14:20:42', 1, 48),
(146, '10 inch - 6 slices', 6, '2019-10-11 14:21:02', 1, 48),
(147, '13 inch - 8 Slices', 9, '2019-10-11 14:21:18', 1, 48),
(148, 'Chicken Tikka', 0, '2019-10-11 14:22:34', 1, 49),
(149, 'Fajita', 0, '2019-10-11 14:23:12', 1, 49),
(150, 'Supreme', 0, '2019-10-11 14:23:30', 1, 49),
(151, 'Sicilian', 0, '2019-10-11 14:23:47', 1, 49),
(152, 'Deep Pan', 0, '2019-10-11 14:24:21', 1, 50),
(153, 'Thin Crust', 0, '2019-10-11 14:24:35', 1, 50),
(154, 'Jalapeno', 0.4, '2019-10-11 14:25:25', 1, 51),
(155, 'Santa Fe', 0.4, '2019-10-11 14:25:52', 1, 51),
(156, 'Mayo Mustard', 0.4, '2019-10-11 14:26:12', 1, 51),
(157, '7 Inch - 4 Slices', 5, '2019-10-16 17:22:04', 1, 7),
(158, '10 Inch - 6 Slices', 15, '2019-10-16 17:22:19', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_menu_extra_section`
--

CREATE TABLE `restaurant_menu_extra_section` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '1',
  `restaurant_menu_item_id` int(12) NOT NULL,
  `required` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_menu_extra_section`
--

INSERT INTO `restaurant_menu_extra_section` (`id`, `name`, `restaurant_id`, `active`, `restaurant_menu_item_id`, `required`) VALUES
(1, 'select variation', 1, 1, 1, 1),
(2, 'Choose Your Soft Drink', 1, 1, 1, 1),
(3, 'select variation', 1, 1, 2, 1),
(4, 'select variation', 1, 1, 3, 1),
(5, 'select variation', 1, 1, 4, 1),
(6, 'select variation', 1, 1, 5, 1),
(7, 'select variation', 1, 1, 6, 1),
(8, 'select Variation', 1, 1, 7, 1),
(9, 'Beverages', 1, 1, 7, 0),
(10, 'Choose your Soft Drink', 8, 1, 8, 1),
(11, 'Choose your Soft Drink', 8, 1, 9, 1),
(12, 'Choose Your Soft Drink', 8, 1, 10, 1),
(13, 'Choose Your Soft Drink', 8, 1, 11, 1),
(14, 'Choose Your Soft Drink', 8, 1, 12, 1),
(15, 'Choose Your Soft Drink', 8, 1, 13, 1),
(16, 'Choose Your Soft Drink', 8, 1, 14, 1),
(17, 'Choose Your Soft Drink', 8, 1, 15, 1),
(18, 'Choose Your Soft Drink', 1, 1, 21, 0),
(19, 'Select Variation', 8, 1, 18, 1),
(20, 'Select Variation', 8, 1, 19, 1),
(21, 'Select Variation', 8, 1, 20, 1),
(22, 'Select Variation', 8, 1, 22, 1),
(23, 'Select Variation', 8, 1, 23, 1),
(24, 'Select Variation', 8, 1, 24, 1),
(25, 'Choose Your Soft Drink', 8, 1, 22, 1),
(26, 'Choose Your Soft Drink', 8, 1, 23, 1),
(27, 'Choose Your Soft Drink', 8, 1, 24, 1),
(28, 'Select Variation', 8, 1, 28, 1),
(29, 'Choose Your Soft Drink', 8, 1, 28, 1),
(30, 'Select Variation', 8, 1, 39, 1),
(31, 'Select Variation', 8, 1, 40, 1),
(32, 'Select Variation', 8, 1, 41, 1),
(33, 'select variation', 10, 1, 42, 1),
(34, 'Choose Your Meat', 10, 1, 42, 1),
(35, 'choose Your Sauce', 10, 1, 42, 1),
(36, 'Addon', 10, 1, 42, 1),
(37, 'Make It A Meal', 10, 1, 42, 0),
(38, 'Choose Your Sauce', 10, 1, 43, 1),
(39, 'Addon', 10, 1, 43, 0),
(40, 'Make It A Meal', 10, 1, 43, 0),
(41, 'Choose Your Sauce', 10, 1, 44, 1),
(42, 'Addon', 10, 1, 44, 0),
(43, 'Make It A Meal', 10, 1, 44, 0),
(44, 'select variation', 10, 1, 45, 1),
(45, 'Select Variation', 10, 1, 46, 1),
(46, 'Choose Your Special Flavor', 10, 1, 46, 1),
(47, 'Choose Your Crust', 10, 1, 46, 0),
(48, 'Select Variation', 10, 1, 47, 1),
(49, 'Choose Your Flavor', 10, 1, 47, 1),
(50, 'Choose Your Crust', 10, 1, 47, 0),
(51, 'choose Your Dip', 10, 1, 47, 0);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_menu_item`
--

CREATE TABLE `restaurant_menu_item` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `price` float NOT NULL,
  `image` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `active` int(11) NOT NULL DEFAULT '1',
  `restaurant_menu_id` int(11) NOT NULL,
  `out_of_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_menu_item`
--

INSERT INTO `restaurant_menu_item` (`id`, `name`, `description`, `price`, `image`, `created`, `active`, `restaurant_menu_id`, `out_of_order`) VALUES
(1, 'Tandoori Chicken Pizza', 'Tandoori chicken, cheese, bell pepper, onion, pickle &amp; black olives', 32, '', '2019-10-08 15:46:53', 1, 1, 0),
(2, 'Chicken Chili Dry &amp; Rice', 'only taste', 48, '', '2019-10-08 16:06:07', 1, 2, 0),
(3, 'Chicken Chowmein', 'only taste', 32, '', '2019-10-08 17:06:15', 1, 2, 0),
(4, 'Chicken Black Pepper &amp; Rice', 'Fried Rice', 32, '', '2019-10-08 17:07:26', 1, 2, 0),
(5, 'Masala Rice &amp; Shashlik', 'Fried ', 32, '', '2019-10-08 17:08:35', 1, 2, 0),
(6, 'Italian Delight Pizza', 'Chicken, beef sausages, mushroom, olives, sweet corn', 5, '', '2019-10-08 17:09:41', 1, 1, 0),
(7, 'Chicken Fajita Pizza', 'ChickenFajitamushroomsonionPickle  black olives', 9, '', '2019-10-11 08:42:21', 1, 1, 0),
(8, 'Chicken Egg Fried Rice', 'Chicken Egg Fried Rice', 2, '', '2019-10-11 08:54:07', 1, 3, 0),
(9, 'Vegetable Rices', 'VegetablesRices', 2, '', '2019-10-11 09:01:04', 1, 3, 0),
(10, 'Grill Chicken Fried Rice', 'Grill Chicken Fried Rice', 3, '', '2019-10-11 10:14:45', 1, 3, 0),
(11, 'Chilli Crispy Rice', 'Chilli Crispy Rice', 3, '', '2019-10-11 10:19:27', 1, 3, 0),
(12, 'Chicken Patty Burger', 'Chicken Patty Burger  with fries', 2, '', '2019-10-11 10:38:13', 1, 4, 0),
(13, 'Tikka Burger', 'Tikka Burger with fries', 2, '', '2019-10-11 10:38:39', 1, 4, 0),
(14, 'BBQ Burger', 'BBQ Burger with Fries', 2, '', '2019-10-11 10:41:50', 1, 4, 0),
(15, 'Crunch Burger', 'Crunch Burger with Fries', 2, '', '2019-10-11 10:45:11', 1, 4, 0),
(16, 'Krizma Burger', 'Krizma Burger with Soft Drink', 2, '', '2019-10-11 10:54:27', 1, 4, 0),
(17, 'Reggy Burger', 'Reggy Burger', 1, '', '2019-10-11 10:50:58', 1, 4, 0),
(18, 'Chicken Piece', 'chicken Piece', 1, '', '2019-10-11 10:57:49', 1, 4, 0),
(19, 'Nuggets', 'Nuggets', 1, '', '2019-10-11 11:13:29', 1, 4, 0),
(20, 'Hot Wings', 'Hot Wings', 2, '', '2019-10-11 11:16:25', 1, 4, 0),
(21, 'Banana Walnut Bread', 'banana walnut bread ', 7, '', '2019-10-11 11:24:04', 1, 5, 0),
(22, 'Masala Fries', 'Masala Fries with Soft Drink', 1, '', '2019-10-11 11:28:52', 1, 6, 0),
(23, 'Garlic Mayo Fries', 'Garlic Mayo Fries with Soft Drink', 1, '', '2019-10-11 11:30:40', 1, 6, 0),
(24, 'BBQ Fries', 'BBQ Fries with Soft Drink', 1, '', '2019-10-11 11:32:38', 1, 6, 0),
(25, 'Plain Brownie', 'Plain brownie', 2, '', '2019-10-11 11:32:40', 1, 7, 0),
(26, 'Fudge Pastry', 'Fudge Pastry', 2, '', '2019-10-11 11:37:02', 1, 7, 0),
(27, 'Banoffee Crumble', 'Banoffee Crumble', 3, '', '2019-10-11 11:38:03', 1, 7, 0),
(28, 'Jalapeno Fries', 'Jalapeno Fries with Soft Drink', 1, '', '2019-10-11 11:40:23', 1, 6, 0),
(29, 'Mud Slide', 'Mud Slide', 2, '', '2019-10-11 11:42:10', 1, 7, 0),
(30, 'Berry Twirl', 'Berry Twirl', 3, '', '2019-10-11 11:42:41', 1, 7, 0),
(31, 'Apple Crumble', 'Apple Crumble', 3, '', '2019-10-11 11:43:38', 1, 7, 0),
(32, 'Vanilla Pastry', 'Vanilla Pastry', 2, '', '2019-10-11 11:44:04', 1, 7, 0),
(33, 'Blue Berry Muffin', 'Blue Berry Muffin', 3, '', '2019-10-11 11:44:23', 1, 7, 0),
(34, 'Cinnabon', 'Cinnabon', 2, '', '2019-10-11 11:44:47', 1, 7, 0),
(35, 'Cinnabon Roll', 'Cinnabon Roll', 3, '', '2019-10-11 11:45:24', 1, 7, 0),
(36, 'Chocolate Chip Cookie', 'Chocolate Chip Cookie', 2, '', '2019-10-11 11:45:59', 1, 8, 0),
(37, 'White Chocolate Chip Cookie', 'White Chocolate Chip Cookie', 3, '', '2019-10-11 11:46:21', 1, 8, 0),
(38, 'Corporate Cake', 'Corporate Cake', 2, '', '2019-10-11 11:46:39', 1, 8, 0),
(39, 'Shawarma', 'Shawarma with fries &amp; soft drink ', 1, '', '2019-10-11 11:54:26', 1, 9, 0),
(40, 'Grill Shawarma', 'Grill Shawarma with fries &amp; soft drink ', 2, '', '2019-10-11 11:55:05', 1, 9, 0),
(41, 'Crunch Paratha', 'Crunch Paratha with fries &amp; soft drink', 2, '', '2019-10-11 11:56:16', 1, 9, 0),
(42, 'Burger Making Zone', 'Burger Making Zone', 3, '', '2019-10-11 12:06:16', 1, 10, 0),
(43, 'Beef Grill Burger', '8 mistakes youre probably making with burger', 3, '', '2019-10-11 12:56:08', 1, 10, 0),
(44, 'wrap', 'Keep calm and wrap on', 3, '', '2019-10-11 13:38:47', 1, 10, 0),
(45, 'Chicken Grilled Sandwich', 'Chicken Grilled Sandwich', 3, '', '2019-10-11 14:02:48', 1, 11, 0),
(46, 'Yellow Plate Special Pizza', 'Yellow Plate Special Pizza', 4, '', '2019-10-11 14:11:45', 1, 12, 0),
(47, 'Make Your Own Pizza', 'Make Your Own Pizza', 3, '', '2019-10-11 14:19:56', 1, 12, 0),
(48, 'Calzone', 'Calzone', 4, '', '2019-10-11 14:28:08', 1, 12, 0);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_rating`
--

CREATE TABLE `restaurant_rating` (
  `id` int(11) NOT NULL,
  `star` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_rating`
--

INSERT INTO `restaurant_rating` (`id`, `star`, `comment`, `created`, `user_id`, `restaurant_id`) VALUES
(1, 5, 'fhjjj', '2019-10-09 07:55:56', 449, 1),
(2, 5, '6ydychx', '2019-10-09 08:05:41', 449, 0),
(3, 5, 'bhyg', '2019-10-09 09:13:12', 449, 1),
(4, 5, 'good top', '2019-10-09 10:58:57', 448, 1),
(5, 5, '', '2019-10-19 07:21:52', 449, 1);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_request`
--

CREATE TABLE `restaurant_request` (
  `id` int(11) NOT NULL,
  `restaurant_name` varchar(255) NOT NULL,
  `contact_name` varchar(255) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_timing`
--

CREATE TABLE `restaurant_timing` (
  `id` int(11) NOT NULL,
  `day` varchar(45) NOT NULL,
  `opening_time` time DEFAULT NULL,
  `closing_time` time DEFAULT NULL,
  `restaurant_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `restaurant_timing`
--

INSERT INTO `restaurant_timing` (`id`, `day`, `opening_time`, `closing_time`, `restaurant_id`) VALUES
(22, 'Sunday', '00:00:00', '23:00:00', 1),
(23, 'Monday', '00:00:00', '23:00:00', 1),
(24, 'Tuesday', '00:00:00', '23:00:00', 1),
(25, 'Wednessday', '00:00:00', '23:00:00', 1),
(26, 'Thursday', '00:00:00', '23:00:00', 1),
(27, 'Friday', '00:00:00', '23:00:00', 1),
(28, 'Saturday', '00:00:00', '23:00:00', 1),
(29, 'Sunday', '00:00:00', '23:00:00', 8),
(30, 'Monday', '00:00:00', '23:00:00', 8),
(31, 'Tuesday', '00:00:00', '23:00:00', 8),
(32, 'Wednessday', '00:00:00', '23:00:00', 8),
(33, 'Thursday', '00:00:00', '23:00:00', 8),
(34, 'Friday', '00:00:00', '23:00:00', 8),
(35, 'Saturday', '00:00:00', '23:00:00', 8),
(36, 'Sunday', '00:00:00', '23:00:00', 9),
(37, 'Monday', '00:00:00', '23:00:00', 9),
(38, 'Tuesday', '00:00:00', '23:00:00', 9),
(39, 'Wednessday', '00:00:00', '23:00:00', 9),
(40, 'Thursday', '00:00:00', '23:00:00', 9),
(41, 'Friday', '00:00:00', '23:00:00', 9),
(42, 'Saturday', '00:00:00', '23:00:00', 9),
(43, 'Sunday', '00:00:00', '12:00:00', 10),
(44, 'Monday', '00:00:00', '12:00:00', 10),
(45, 'Tuesday', '00:00:00', '12:00:00', 10),
(46, 'Wednessday', '00:00:00', '12:00:00', 10),
(47, 'Thursday', '00:00:00', '12:00:00', 10),
(48, 'Friday', '00:00:00', '12:00:00', 10),
(49, 'Saturday', '00:00:00', '12:00:00', 10),
(50, 'Sunday', '00:00:00', '23:00:00', 11),
(51, 'Monday', '00:00:00', '23:00:00', 11),
(52, 'Tuesday', '00:00:00', '23:00:00', 11),
(53, 'Wednessday', '00:00:00', '23:00:00', 11),
(54, 'Thursday', '00:00:00', '23:00:00', 11),
(55, 'Friday', '00:00:00', '23:00:00', 11),
(56, 'Saturday', '00:00:00', '23:00:00', 11),
(57, 'Sunday', '00:00:00', '23:00:00', 12),
(58, 'Monday', '00:00:00', '23:00:00', 12),
(59, 'Tuesday', '00:00:00', '23:00:00', 12),
(60, 'Wednessday', '00:00:00', '23:00:00', 12),
(61, 'Thursday', '00:00:00', '23:00:00', 12),
(62, 'Friday', '00:00:00', '23:00:00', 12),
(63, 'Saturday', '00:00:00', '23:00:00', 12);

-- --------------------------------------------------------

--
-- Table structure for table `rider_location`
--

CREATE TABLE `rider_location` (
  `id` int(11) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `lat` decimal(20,18) NOT NULL,
  `long` decimal(20,18) NOT NULL,
  `city` varchar(45) NOT NULL,
  `country` varchar(255) NOT NULL,
  `address_to_start_shift` varchar(255) NOT NULL,
  `previous_lat` decimal(20,18) NOT NULL,
  `previous_long` decimal(20,18) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rider_location`
--

INSERT INTO `rider_location` (`id`, `user_id`, `lat`, `long`, `city`, `country`, `address_to_start_shift`, `previous_lat`, `previous_long`) VALUES
(1, '1', 17.498484800000000000, 78.396203500000000000, '', '', '', 0.000000000000000000, 0.000000000000000000),
(2, '460', 0.000000000000000000, 0.000000000000000000, 'Miami', 'United States', '&lt;script&gt;alert(&#039;XSS&#039;);&lt;/script&gt; ', 0.000000000000000000, 0.000000000000000000);

-- --------------------------------------------------------

--
-- Table structure for table `rider_order`
--

CREATE TABLE `rider_order` (
  `id` int(11) NOT NULL,
  `rider_user_id` int(11) NOT NULL,
  `assigner_user_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `assign_date_time` datetime NOT NULL,
  `accept_reject_status` int(11) NOT NULL COMMENT '1 - accept, 2 - reject, 3 - completed',
  `accept_reject_dateTime` datetime NOT NULL,
  `snap` varchar(254) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rider_order`
--

INSERT INTO `rider_order` (`id`, `rider_user_id`, `assigner_user_id`, `order_id`, `assign_date_time`, `accept_reject_status`, `accept_reject_dateTime`, `snap`, `created`) VALUES
(1, 1, 4, 6, '2019-10-08 16:21:32', 3, '0000-00-00 00:00:00', '-LqfxW1nK7GebTLWqpRz', '2019-10-08 20:21:32'),
(2, 1, 4, 5, '2019-10-08 16:21:45', 3, '0000-00-00 00:00:00', '-LqfxZ1t6S6zpZmSmzCk', '2019-10-08 20:21:45'),
(3, 1, 4, 3, '2019-10-09 07:52:00', 3, '0000-00-00 00:00:00', '-LqjHTqowFA01GNexokb', '2019-10-09 11:52:00'),
(4, 1, 4, 2, '2019-10-09 08:03:54', 3, '0000-00-00 00:00:00', '-LqjKCHtLNyPh7KMrPD-', '2019-10-09 12:03:54'),
(5, 1, 4, 9, '2019-10-09 08:47:16', 3, '0000-00-00 00:00:00', '-LqjU7R5uIgrXQQpmW52', '2019-10-09 12:47:16'),
(6, 1, 4, 8, '2019-10-09 08:50:01', 3, '0000-00-00 00:00:00', '-LqjUkf22LTYOGOGT1DR', '2019-10-09 12:50:01'),
(7, 1, 4, 7, '2019-10-09 08:58:55', 3, '0000-00-00 00:00:00', '-LqjWn3LoJNU-wcWuHbl', '2019-10-09 12:58:55'),
(8, 1, 4, 4, '2019-10-09 09:08:03', 3, '0000-00-00 00:00:00', '-LqjYt3x-VIIxQUBKkpC', '2019-10-09 13:08:03'),
(9, 1, 4, 10, '2019-10-09 09:12:21', 3, '0000-00-00 00:00:00', '-LqjZrszUKPD7C0COu4T', '2019-10-09 13:12:21'),
(10, 1, 4, 11, '2019-10-09 10:11:35', 3, '0000-00-00 00:00:00', '-LqjmQe9RfhKgPNFexkp', '2019-10-09 14:11:35'),
(11, 1, 4, 12, '2019-10-09 10:57:08', 3, '0000-00-00 00:00:00', '-Lqjwqs1wcXxGNlyxPJ9', '2019-10-09 14:57:08'),
(12, 1, 4, 20, '2019-10-14 14:03:15', 3, '0000-00-00 00:00:00', '-Lr9MOru1xQDZBKZObR_', '2019-10-14 18:03:15'),
(13, 1, 4, 21, '2019-10-14 14:10:31', 3, '0000-00-00 00:00:00', '-Lr9O3OR4eHoGtTyypvk', '2019-10-14 18:10:31'),
(14, 1, 4, 13, '2019-10-15 19:31:48', 1, '0000-00-00 00:00:00', '-LrFgBSnBWvOa_y1ELoF', '2019-10-15 23:31:48'),
(15, 1, 4, 15, '2019-10-15 20:23:48', 1, '0000-00-00 00:00:00', '-LrFs59u0xKz1KcA9EQ7', '2019-10-16 00:23:48'),
(16, 1, 4, 19, '2019-10-16 01:59:47', 1, '0000-00-00 00:00:00', '-LrH3zqvMbEPMG4P_ZBG', '2019-10-16 05:59:48'),
(17, 1, 4, 18, '2019-10-16 09:52:42', 1, '0000-00-00 00:00:00', '-LrIlEFdZuAdUuTiycWx', '2019-10-16 13:52:42'),
(18, 1, 1, 16, '2019-10-16 09:54:53', 1, '0000-00-00 00:00:00', '-LrIljHB15bVthj0fHH8', '2019-10-16 13:54:53'),
(19, 1, 4, 14, '2019-10-16 15:19:27', 1, '0000-00-00 00:00:00', '-LrJw0YLUhnHSkt1C-OI', '2019-10-16 19:19:27'),
(20, 1, 16, 24, '2019-10-18 11:56:47', 0, '0000-00-00 00:00:00', '-LrTVoGxEErPZev4bzh5', '2019-10-18 15:56:47'),
(21, 1, 16, 22, '2019-10-18 16:56:13', 0, '0000-00-00 00:00:00', '-LrU_LVGOXdenAw9KwtX', '2019-10-18 20:56:13');

-- --------------------------------------------------------

--
-- Table structure for table `rider_rating`
--

CREATE TABLE `rider_rating` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rider_user_id` int(11) NOT NULL,
  `star` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rider_rating`
--

INSERT INTO `rider_rating` (`id`, `order_id`, `user_id`, `rider_user_id`, `star`, `comment`, `created`) VALUES
(1, 6, 449, 1, 5, 'good service', '2019-10-08 16:44:20'),
(2, 5, 449, 1, 5, 'good', '2019-10-08 17:03:32'),
(3, 3, 449, 1, 5, 'good', '2019-10-09 07:54:58'),
(4, 2, 449, 1, 5, 'ujcjcjdud', '2019-10-09 08:05:10'),
(5, 9, 449, 1, 5, 'jcncn', '2019-10-09 08:48:48'),
(6, 8, 449, 1, 5, 'nchch', '2019-10-09 08:50:35'),
(7, 7, 449, 1, 5, 'bxbxhx', '2019-10-09 09:07:06'),
(8, 4, 449, 1, 5, 'xhxhx', '2019-10-09 09:08:55'),
(9, 10, 449, 1, 5, 'gyuj', '2019-10-09 09:13:00'),
(10, 12, 448, 1, 5, 'ok', '2019-10-09 10:58:40'),
(11, 20, 449, 1, 5, '', '2019-10-19 07:21:48');

-- --------------------------------------------------------

--
-- Table structure for table `rider_request`
--

CREATE TABLE `rider_request` (
  `id` int(11) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(200) NOT NULL,
  `city` varchar(200) NOT NULL,
  `state` varchar(200) NOT NULL,
  `country` varchar(200) NOT NULL,
  `address` varchar(200) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `rider_timing`
--

CREATE TABLE `rider_timing` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `starting_time` time NOT NULL,
  `ending_time` time NOT NULL,
  `confirm` tinyint(2) NOT NULL,
  `admin_confirm` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `rider_track_order`
--

CREATE TABLE `rider_track_order` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `pickup_time` datetime NOT NULL,
  `delivery_time` datetime NOT NULL,
  `on_my_way_to_hotel_time` datetime NOT NULL,
  `on_my_way_to_user_time` datetime NOT NULL,
  `notification` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rider_track_order`
--

INSERT INTO `rider_track_order` (`id`, `order_id`, `pickup_time`, `delivery_time`, `on_my_way_to_hotel_time`, `on_my_way_to_user_time`, `notification`) VALUES
(1, 6, '2019-10-08 20:42:23', '2019-10-08 20:42:40', '2019-10-08 20:22:22', '2019-10-08 20:42:32', 1),
(2, 5, '2019-10-08 21:02:19', '2019-10-08 21:02:45', '2019-10-08 21:02:15', '2019-10-08 21:02:24', 1),
(3, 3, '2019-10-09 11:53:00', '2019-10-09 11:53:07', '2019-10-09 11:52:56', '2019-10-09 11:53:03', 1),
(4, 2, '2019-10-09 12:04:27', '2019-10-09 12:04:33', '2019-10-09 12:04:26', '2019-10-09 12:04:30', 1),
(5, 9, '2019-10-09 12:48:16', '2019-10-09 12:48:20', '2019-10-09 12:48:13', '2019-10-09 12:48:18', 1),
(6, 8, '2019-10-09 12:50:08', '2019-10-09 12:50:12', '2019-10-09 12:50:05', '2019-10-09 12:50:10', 1),
(7, 7, '2019-10-09 12:59:07', '2019-10-09 12:59:20', '2019-10-09 12:59:05', '2019-10-09 12:59:18', 1),
(8, 4, '2019-10-09 13:08:32', '2019-10-09 13:08:35', '2019-10-09 13:08:31', '2019-10-09 13:08:34', 1),
(9, 10, '2019-10-09 13:12:32', '2019-10-09 13:12:35', '2019-10-09 13:12:31', '2019-10-09 13:12:34', 1),
(10, 11, '2019-10-09 14:11:52', '2019-10-09 14:11:54', '2019-10-09 14:11:51', '2019-10-09 14:11:53', 1),
(11, 12, '2019-10-09 14:57:19', '2019-10-09 14:57:25', '2019-10-09 14:57:17', '2019-10-09 14:57:22', 1),
(12, 20, '2019-10-14 13:03:57', '2019-10-14 18:04:04', '2019-10-14 18:03:53', '2019-10-14 18:03:59', 1),
(13, 21, '2019-10-14 20:39:35', '2019-10-14 20:39:44', '2019-10-14 20:39:30', '2019-10-14 20:39:39', 1),
(14, 13, '2019-10-18 15:43:23', '0000-00-00 00:00:00', '2019-10-18 01:29:32', '0000-00-00 00:00:00', 0),
(15, 690, '2019-10-18 15:42:35', '0000-00-00 00:00:00', '2019-10-18 15:42:21', '2019-10-18 15:42:40', 0);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'Super admin'),
(2, 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `subscriber`
--

CREATE TABLE `subscriber` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `city` varchar(200) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tax`
--

CREATE TABLE `tax` (
  `id` int(11) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `tax` float NOT NULL,
  `delivery_fee_per_km` int(11) NOT NULL,
  `country_code` varchar(10) NOT NULL,
  `delivery_time` int(11) NOT NULL COMMENT 'in minutes'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tax`
--

INSERT INTO `tax` (`id`, `city`, `state`, `country`, `tax`, `delivery_fee_per_km`, `country_code`, `delivery_time`) VALUES
(21, 'Miami', 'Florida', 'United States', 17, 10, '+1', 55);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `active` int(11) NOT NULL,
  `block` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL COMMENT 'user,admin,hotel,rider'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `salt`, `active`, `block`, `created`, `token`, `role`) VALUES
(1, 'rider@gmail.com', '$2a$10$PsjDxsDL1w1C8SuAyEDcEe.UX7OFYtZYsIN.L.h0/EHrNUcsq2tS2', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-06 19:52:36', '', 'rider'),
(2, 'scoma@gmail.com', '$2a$10$UDePOBzDYNC4JvSG/OVXCe68p/OuioHdRWjah03GcPIaA9lRuS2V2', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(3, 'user@gmail.com', '$2a$10$baUKepE9VM8/HuWBf6psguy45R41tVduJaVjUx6jAupsNTJcM6GyS', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 21:37:22', '', 'user'),
(448, 'adnan.dtrack@gmail.com', '$2a$10$HrzLgjWmmiL5w2vU.C8gtu/I5RKaA2abYUSaFjw5wApsLMhauGDbW', '89916c6f94e6c1005215a396b94d3c46ca43cc5df29ad7cb082fc7bea9ffa741', 1, 0, '2019-10-08 15:20:45', '', 'user'),
(449, 'bringthings22@gmail.com', '$2a$10$aD0WgBGxxzXFQE6CDky3kOlXDSI8uwLz5NpmdY3CFEE3lWwXn9PPO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-10-08 15:34:52', '', 'user'),
(450, 'hamzasimt@yahoo.com', '$2a$10$kVAd9p0eoqV3sZec2eKg9.oAsam0D5aqQbsLtI96Z2W8zjTt4Ekcm', '1f55c8e2e5de7cfdb9a16b310360a260ea1c1a66a7803994c0858fca5c346aa3', 1, 0, '2019-10-08 17:32:55', NULL, 'user'),
(451, 'howdy@gmail.com', '$2a$10$5SS6/YBJDgwP3jduCgql4O5K7hqbE.kqpZlp2WpNC0PFvXk7Xhm8q', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(452, 'masooms@gmail.com', '$2a$10$zj2Bqk4PIKZFgb7eL2SmS.jUYrRfXFUEsX2tHh0NscPF4Yy58IZlO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(453, 'yellowplate@gmail.com', '$2a$10$zj2Bqk4PIKZFgb7eL2SmS.jUYrRfXFUEsX2tHh0NscPF4Yy58IZlO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(454, 'frendy@gmail.com', '$2a$10$zj2Bqk4PIKZFgb7eL2SmS.jUYrRfXFUEsX2tHh0NscPF4Yy58IZlO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(455, 'downtown@gmail.com', '$2a$10$zj2Bqk4PIKZFgb7eL2SmS.jUYrRfXFUEsX2tHh0NscPF4Yy58IZlO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-02-07 12:14:29', '', 'hotel'),
(456, 'sepe@mailinator.com', '$2a$10$u54rl6qa0QUswr33RhME0OnDNM5Vgndc6BIbjJ4GGzod0erk2Vsl.', '0a81cc87b8862a2fff2fecc740e620cf06628180a8852a1c1811f7338000f6c0', 1, 0, '2019-10-11 09:16:21', NULL, 'hotel'),
(457, 'wyzorofil@mailinator.net', '$2a$10$4ZoRzRacyY2WHQiMdefunuQzwj0G44ctUDHWcrv3Urljj9RRHQCiu', 'c17ae605efcf2c4d31fd736faf2508d66671ad7f103adf695ad83c4af7f20ea2', 1, 0, '2019-10-11 09:22:40', NULL, 'hotel'),
(458, 'huxe@mailinator.com', '$2a$10$28f6RETvkPaxVLpTlbqTVODhhMBQccSXjNz4IrdSBJBC.eWqQff4C', '53d97a14cf6247ef95bbbc176f39cf1b56c90a91b4448325e31535456fbd004c', 1, 0, '2019-10-11 09:36:11', NULL, 'hotel'),
(459, 'gibovaju@mailinator.com', '$2a$10$A6a9Yomkk5FI5T0.tC5hre9OOT40q.VZ7kMY0pTelAfWax8Rgkrym', '22da981f74c1fab26618a6a0f26ed0107528424cc7e045e99794a37671baa758', 1, 0, '2019-10-11 11:37:07', NULL, 'hotel'),
(460, 'test@test.com', '$2a$10$uslu9yK6G5ziwNKXdtuowOtWDZkiCF8Yo.6.ZxHZEt3gJ7CBtrafy', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 1, '2019-10-12 08:43:38', NULL, 'rider'),
(461, 'mian.aqeel@rocketmail.com', '$2a$10$9NB2/9KG1j2vHbbo5EY.mOCMTKmyyIbiRnbaVzzrqwX0xaGMIww4G', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-10-14 13:09:37', NULL, 'user'),
(462, 'bezawadabunny@gmail.com', '$2a$10$VQMNvDXHfypsSbY4qmYUrug3shfD7i5Vudgw2JSkFjNcoIyEeToZG', 'd6dbc99dfe79de8ce53638a372e4c880959aa170c8dc6c8c74c1428b17db2347', 1, 0, '2019-10-15 18:25:54', NULL, 'user'),
(463, 'touya.ra@gmail.com', '$2a$10$eE1uIGF6TiDLSdfuobTRtuGlgPzTrB8pNF8wCMQ/KSN7.u8TxdvUi', '538f2738873eb00901cfb1f0b30a16197d239c5a0070ac5f936e387464ddd05d', 1, 0, '2019-10-16 01:41:18', NULL, 'user'),
(464, 'pnficucf@gmail.com', '$2a$10$62oGjqy.8TkwHbUg8wSSD.GRoMh9FHDTO6ARY.0U4lASKdQtRQRdy', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-10-16 03:32:30', NULL, 'user'),
(465, 'test11@gmail.com', '$2a$10$YaVRCWzi3eX10gTltRbLKuwAzqNqJMF.klYxUtQllCYeBb95kRT0O', '0897de5557e37f84d1f172de9b063ab88ba9833977c7faa7f9502e350bfb0216', 1, 0, '2019-10-16 11:53:15', NULL, 'user'),
(466, 'papipappu@mail.com', '$2a$10$ty2GEdTP.DPusPYc0DbReOubc6nnQZFVyTW8iUrJMh09ElFrAyAqO', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-10-16 12:04:29', NULL, 'user'),
(467, 'siryt@mailinator.net', '$2a$10$.kKPNCOdELAOfaihr5Ztr.ic1V6dpHoYvT.O8WL4XmnRYz4RCXglS', 'e4889da9e6c391be1e6487920389ef889be43c22024f98aeb4ea1a98e2de0d94', 1, 0, '2019-10-17 10:45:33', NULL, 'hotel'),
(468, 'mepepejiqi@mailinator.com', '$2a$10$jjMDG2TYzG5XKxKCQP5BRO6mAJcf6EpwwCSS.PJVKtbQmhI4dNEIq', '6b103c41ee4909d9fbd5b3ff13dd3390ff044f43acc3d1748a446aff4afdb75c', 1, 0, '2019-10-17 10:54:17', NULL, 'hotel'),
(469, 'tyhudexo@mailinator.net', '$2a$10$7D6AIrn5r1RCe8A6i3yXX.5ys/4wGIkFi8nu6O4UuYw8/CgxcFr4e', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 1, 0, '2019-10-17 12:23:19', NULL, 'hotel'),
(470, 'laxofep@mailinator.com', '$2a$10$OZwnSuUFZHFl6t.hhz6gWO85uWitBG9rmKppXxhSoVOPMKowH5L1.', 'ad42fb21dd65d746dc1f9e3d30fcf05eb30448f46a0068d22243f9e7a3aae586', 1, 0, '2019-10-17 14:13:39', NULL, 'hotel');

-- --------------------------------------------------------

--
-- Table structure for table `user_admin`
--

CREATE TABLE `user_admin` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `role` int(11) NOT NULL,
  `role_name` varchar(100) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '1' COMMENT '1 = active    0= block',
  `last_login` datetime NOT NULL,
  `created` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_admin`
--

INSERT INTO `user_admin` (`id`, `email`, `password`, `salt`, `first_name`, `last_name`, `phone`, `role`, `role_name`, `active`, `last_login`, `created`) VALUES
(16, 'admin@admin.com', '$2a$10$2cbbDDxTQ7yNbQ8DWV8lXOkFQ7Eh6cY54y10DyN86G1Of08ZUwqHK', 'b05a792a5132644a9f624d09769139b600f04950e0a37c7dd0368cbe316f1881', 'admin', 'account', '03111222733', 0, 'Super admin', 1, '0000-00-00 00:00:00', '2019-10-16 20:48:34');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `user_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `profile_img` varchar(255) NOT NULL,
  `note` text NOT NULL,
  `device_token` varchar(255) NOT NULL,
  `online` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_id`, `first_name`, `last_name`, `phone`, `profile_img`, `note`, `device_token`, `online`) VALUES
(1, 'Rider', 'One', '03111222733', '', '', 'fs1wfDFzhlg:APA91bGFptH7Vnur0RaZC8WFaQwat9zd0goiWVNbvzjv7KqwpNPcgp_REHrFZvd1OSM7lmBXcTucaEBz-ZrPcuVaS3AyPcfuF91UgConEns7vFhU-BPQ2de5R4FMdvz3zRTG2l68a3jz', 1),
(2, 'Scoma', 'Pizza', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(3, 'Test', 'User', '+923137370772', '', '', 'deWCcN0kZ40:APA91bEh_SfM39Ehcanr6Kj7qFGuK8yKdHC6InH1qGjlbeEySAlsKN6h83FD-ce34roo-nOBiY4YGy4sqp9Fb9yMy_3xpYjVTyv_CDDCcVQdDjYDjzGkA2rX1Mp0EfTxv9pTu0qYevSp', 1),
(448, 'Test', 'Jakson', '+13037616341', '', '', 'dxEbJwMdBBQ:APA91bHdliOzKyGq5qDpQK6ze8eicAhD0ee-DU0HuKk3TI54bmF4BKPuV2A_uZrH9IsiQrC2gIuFFHsgo2C6iNTTXFhtxzSjNRuMElj3CNHA9f29vryzN28dN8sDPLNuABYm2aBN1-9j', 0),
(449, 'Bringthings', 'Inc.', '+13037616341', '', '', 'ftKm8H5IZWI:APA91bEi910uc2jnGsqX7D7ml8fFfcJ9L4FIx_-oyT6rspyfmAh8AITfXv0neECn5L3kw7ulGcdlMcPJHkFeTjGHlx_IKsp66dr1hZ8b4b0MEfhtfZFLvISMKu6LANWhO5ggG2M4IaC-', 0),
(450, 'Hamza', 'Smith', '+912555555555', '', '', '', 0),
(451, 'Howdy', 'hotel', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(452, 'masooms', 'hotel', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(453, 'yellowplate', 'hotel', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(454, 'frendy', 'hotel', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(455, 'downtown', 'hotel', '4157714383', '', '', 'fqWHOUVd7TI:APA91bHggMSiY1Fi7tgbsezMik48FhXhQuQ28A_fzHMl_9yDHmCrDmjo3HulgQRLkAmhwYu-6NMz0yXdghNCIQW4MptUToe59GLFwLEVYRF0r1MxLyoeu-fIinD_8D8PplGYsmLs4ND8', 1),
(456, 'Deirdre', 'Davenport', '+15495396364', '', '', '', 0),
(457, 'Marny', 'Frost', '+11216018491', '', '', '', 0),
(458, 'Ross', 'Jordan', '+15975142006', '', '', '', 0),
(459, 'Xyla', 'Graham', '+13613071531', '', '', '', 0),
(460, 'Test', 'Test', '1234567890', '', '&lt;script&gt;alert(&#039;XSS&#039;);&lt;/script&gt; ', '', 0),
(461, 'Muhammad', 'Aqeel', '+13037616341', '', '', 'cEHlGX5bDzM:APA91bFdBu4HzLBkqWqBp0-d9DpC8t92PRaECZxXa0ux_vT6b6UU3uLcTPFYmQZIRbXwHViMgTjdwP3Qu0LMY3t8o2biTZ0lenrJD0RgG86wzjBGICclxyTa7Qt8XCU6IH3Dt9wpE-E1', 0),
(462, 'Bunny', 'Naidu', '+18142067', '', '', 'dskeI8UBsEo:APA91bGvVxDyOqn4O8zMnRj0UjCZRogMW4PM9ENi3Dg8BRbU-p8hiEYhz5oJU0hgwCtE8dZ668N4ij8zFX0NGwwmg1Kk__OsCOpRJIWNXMpwsl4ELF7tipiGqKXRbpRCNg6_oL-gE9UP', 0),
(463, 'Anousone', 'Rabounthunh', '+19999999999', '', '', 'cUQ7h1mEvtE:APA91bEYJ3OPxdyPdWHoPfbNSGRcQE05I63oJB73dHKCtHD_9Xt2mF5rTfACLo3RBHA9rfrsvi-APOz7tv5fGPL3rFh2jCcpD0ACOhXIxJMd4OPr_5M0xXU5XwoIdf3RSPE_k1N4d7vN', 0),
(464, 'Pnfi', 'Cucf', '+17832456780', '', '', 'fXlHkIy2H8o:APA91bEpmkvPlJIiC9zsVqEG-hIWwU-vBtQWTNbFT1-DtTQ0BGxhohSlNyW5kGVDgEHQAjYLrtgRkBjrR3PtBa0M83evQFytrDvmHhEjK_bPmno4nPa5QmvKTkxqsGOmjOJA6DFeDPlp', 0),
(465, 'Test', 'Test', '+11111111111', '', '', 'dUKit4UgQvs:APA91bH6EzSRpuTaCBOLdjiZgfI2kkpyCJE3CAqToDZRLpdKrlL8mO_PACwtgOnpJqxd_AGprBDq_AsQdUSwlcY2m248UWKafHvUe6R8i1bRKS0qDfEULWQRzMf3zvXC-DAva300ydX6', 0),
(466, 'Papi', 'Pappu', '+17373747474', '', '', 'fy4wyOQnO5A:APA91bHy1uhWt1hGH8x6SsoqBtUX-HTz9KVclvfDm8376CYNsZwAHfFPeQBLEIZIla45wI_PsLnKKhe8HkCi5i9Be9XBh7rWJ0REAW2dwyYnMuNUGg91SioQIJUkwNIWEpFl7kF8__Fm', 0),
(467, 'Charles', 'Melendez', '+19349582316', '', '', '', 0),
(468, 'Martena', 'Leon', '+15492979685', '', '', '', 0),
(469, 'Paki', 'Larson', '+11632068963', '', '', '', 0),
(470, 'Travis', 'Freeman', '+11383464612', '', '', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `verification_document`
--

CREATE TABLE `verification_document` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `file` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` int(10) NOT NULL COMMENT '1-accepted, 2- rejected'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `verification_document`
--

INSERT INTO `verification_document` (`id`, `user_id`, `file`, `description`, `status`) VALUES
(1, 1, 'app/webroot/uploads/verification_documents/1/5da5c6e585123.png', 'Passport', 1),
(2, 1, 'app/webroot/uploads/verification_documents/1/5da5c716874ba.png', 'vehicle registration', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_address_user1_idx` (`user_id`);

--
-- Indexes for table `app_slider`
--
ALTER TABLE `app_slider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `banking_info`
--
ALTER TABLE `banking_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `coupon_used`
--
ALTER TABLE `coupon_used`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `currency`
--
ALTER TABLE `currency`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `deal`
--
ALTER TABLE `deal`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `facebook`
--
ALTER TABLE `facebook`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_facebook_user1_idx` (`user_id`);

--
-- Indexes for table `google`
--
ALTER TABLE `google`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_google_user1_idx` (`user_id`);

--
-- Indexes for table `open_shift`
--
ALTER TABLE `open_shift`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Order_user1_idx` (`user_id`);

--
-- Indexes for table `order_deal`
--
ALTER TABLE `order_deal`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_menu_extra_item`
--
ALTER TABLE `order_menu_extra_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_menu_extra_item_order_menu_item1_idx` (`order_menu_item_id`);

--
-- Indexes for table `order_menu_item`
--
ALTER TABLE `order_menu_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order_menu_item_Order1_idx` (`order_id`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Card_user1_idx` (`user_id`);

--
-- Indexes for table `phone_no_verification`
--
ALTER TABLE `phone_no_verification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_restaurant_user1_idx` (`user_id`);

--
-- Indexes for table `restaurant_coupon`
--
ALTER TABLE `restaurant_coupon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_favourite`
--
ALTER TABLE `restaurant_favourite`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_restaurant_favourite_restaurant1_idx` (`restaurant_id`),
  ADD KEY `fk_restaurant_favourite_user1_idx` (`user_id`);

--
-- Indexes for table `restaurant_location`
--
ALTER TABLE `restaurant_location`
  ADD PRIMARY KEY (`restaurant_id`),
  ADD KEY `fk_restaurant_location_restaurant1_idx` (`restaurant_id`);

--
-- Indexes for table `restaurant_menu`
--
ALTER TABLE `restaurant_menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_hotel_menu_restaurant1_idx` (`restaurant_id`);

--
-- Indexes for table `restaurant_menu_extra_item`
--
ALTER TABLE `restaurant_menu_extra_item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_menu_extra_section`
--
ALTER TABLE `restaurant_menu_extra_section`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_menu_item`
--
ALTER TABLE `restaurant_menu_item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_rating`
--
ALTER TABLE `restaurant_rating`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_rating_restaurant_user1_idx` (`user_id`),
  ADD KEY `fk_rating_restaurant_restaurant1_idx` (`restaurant_id`);

--
-- Indexes for table `restaurant_request`
--
ALTER TABLE `restaurant_request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_timing`
--
ALTER TABLE `restaurant_timing`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_hotel_timing_restaurant1_idx` (`restaurant_id`);

--
-- Indexes for table `rider_location`
--
ALTER TABLE `rider_location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rider_order`
--
ALTER TABLE `rider_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rider_rating`
--
ALTER TABLE `rider_rating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rider_request`
--
ALTER TABLE `rider_request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rider_timing`
--
ALTER TABLE `rider_timing`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rider_track_order`
--
ALTER TABLE `rider_track_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subscriber`
--
ALTER TABLE `subscriber`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tax`
--
ALTER TABLE `tax`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_admin`
--
ALTER TABLE `user_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `fk_user_info_user_idx` (`user_id`);

--
-- Indexes for table `verification_document`
--
ALTER TABLE `verification_document`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `app_slider`
--
ALTER TABLE `app_slider`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `banking_info`
--
ALTER TABLE `banking_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `coupon_used`
--
ALTER TABLE `coupon_used`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `currency`
--
ALTER TABLE `currency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `deal`
--
ALTER TABLE `deal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `facebook`
--
ALTER TABLE `facebook`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `google`
--
ALTER TABLE `google`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `open_shift`
--
ALTER TABLE `open_shift`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `order_deal`
--
ALTER TABLE `order_deal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_menu_extra_item`
--
ALTER TABLE `order_menu_extra_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `order_menu_item`
--
ALTER TABLE `order_menu_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `phone_no_verification`
--
ALTER TABLE `phone_no_verification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `restaurant_coupon`
--
ALTER TABLE `restaurant_coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `restaurant_favourite`
--
ALTER TABLE `restaurant_favourite`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `restaurant_menu`
--
ALTER TABLE `restaurant_menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `restaurant_menu_extra_item`
--
ALTER TABLE `restaurant_menu_extra_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=159;

--
-- AUTO_INCREMENT for table `restaurant_menu_extra_section`
--
ALTER TABLE `restaurant_menu_extra_section`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `restaurant_menu_item`
--
ALTER TABLE `restaurant_menu_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `restaurant_rating`
--
ALTER TABLE `restaurant_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `restaurant_request`
--
ALTER TABLE `restaurant_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `restaurant_timing`
--
ALTER TABLE `restaurant_timing`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `rider_location`
--
ALTER TABLE `rider_location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `rider_order`
--
ALTER TABLE `rider_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `rider_rating`
--
ALTER TABLE `rider_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `rider_request`
--
ALTER TABLE `rider_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rider_timing`
--
ALTER TABLE `rider_timing`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rider_track_order`
--
ALTER TABLE `rider_track_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `subscriber`
--
ALTER TABLE `subscriber`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tax`
--
ALTER TABLE `tax`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=471;

--
-- AUTO_INCREMENT for table `user_admin`
--
ALTER TABLE `user_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `verification_document`
--
ALTER TABLE `verification_document`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `fk_address_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `facebook`
--
ALTER TABLE `facebook`
  ADD CONSTRAINT `fk_facebook_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `google`
--
ALTER TABLE `google`
  ADD CONSTRAINT `fk_google_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_Order_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `order_menu_extra_item`
--
ALTER TABLE `order_menu_extra_item`
  ADD CONSTRAINT `fk_order_menu_extra_item_order_menu_item1` FOREIGN KEY (`order_menu_item_id`) REFERENCES `order_menu_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD CONSTRAINT `fk_Card_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
