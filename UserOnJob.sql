/*
MySQL Data Transfer
Source Host: 192.168.1.16
Source Database: test
Target Host: 192.168.1.16
Target Database: test
Date: 2016/12/8 9:56:41
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for UserRegister
-- ----------------------------
CREATE TABLE `UserOnJob` 
     (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `userId` bigint(16) DEFAULT NULL,
  `customerId` bigint(16) DEFAULT NULL,

  `longitude` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `addDate` datetime DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `imageUrl` varchar(100) DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
