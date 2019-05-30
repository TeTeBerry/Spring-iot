/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : iot

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 30/05/2019 01:30:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_meter
-- ----------------------------
DROP TABLE IF EXISTS `user_meter`;
CREATE TABLE `user_meter`  (
  `uid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`uid`, `mid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `mid`(`mid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
