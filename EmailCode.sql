/*
MySQL Data Transfer
Source Host: 192.168.6.234
Source Database: gelan
Target Host: 192.168.6.234
Target Database: gelan
Date: 2016/12/23 12:20:36
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for EmailCode
-- ----------------------------
CREATE TABLE `EmailCode` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `addDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------

