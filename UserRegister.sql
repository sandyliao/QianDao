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
CREATE TABLE `UserRegister` 
     (
  `Id` bigint(16) unsigned NOT NULL AUTO_INCREMENT,
          
        `RealName` varchar(50) DEFAULT NULL,
    
        `Email` varchar(50) DEFAULT NULL,
  
        `Mobile` varchar(50) DEFAULT NULL,
  
        `Address` varchar(50) DEFAULT NULL,
  
        `Sex` varchar(20) DEFAULT NULL,
 
        `AddDate` datetime DEFAULT NULL,
   
        `Status` varchar(20) DEFAULT NULL,
  
        `Role` varchar(20) DEFAULT NULL,
        `LogName` varchar(50) DEFAULT NULL,
        `Password` varchar(50) DEFAULT NULL,
        
 
         PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
