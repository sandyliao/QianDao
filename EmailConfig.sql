/*
MySQL Data Transfer
Source Host: 192.168.6.234
Source Database: gelan
Target Host: 192.168.6.234
Target Database: gelan
Date: 2017/1/4 12:20:28
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for EmailConfig
-- ----------------------------
CREATE TABLE `EmailConfig` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `customerId` bigint(16) DEFAULT NULL,
  `mailServerHost` varchar(100) DEFAULT NULL,
  `mailServerPort` varchar(100) DEFAULT NULL,
  `mailUserName` varchar(100) DEFAULT NULL,
  `mailPassword` varchar(100) DEFAULT NULL,
  `mailFromAddress` varchar(100) DEFAULT NULL,
  `mailType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------

