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

 Date: 04/07/2019 02:46:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_total_stats
-- ----------------------------
DROP TABLE IF EXISTS `data_total_stats`;
CREATE TABLE `data_total_stats`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flowRate` float(10, 2) NULL DEFAULT 0.00,
  `flowMilliLiitres` float(10, 2) NULL DEFAULT 0.00,
  `totalMilliLitres` float(20, 2) NULL DEFAULT 0.00,
  `readingTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  INDEX `sensorName`(`sensorName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
