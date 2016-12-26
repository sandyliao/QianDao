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
CREATE TABLE `CustomerInfo` 
     (
  `Id` bigint(16) unsigned NOT NULL AUTO_INCREMENT,
          
        `CustomerName` varchar(50) DEFAULT NULL,
    
        `Longitude` varchar(50) DEFAULT NULL,
  
        `Latitude` varchar(50) DEFAULT NULL,
  
        `Address` varchar(50) DEFAULT NULL,
  
        `Status` varchar(20) DEFAULT NULL,
  
         PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
