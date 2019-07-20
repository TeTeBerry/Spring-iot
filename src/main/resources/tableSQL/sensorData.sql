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
DROP TABLE IF EXISTS `sensorData`;
CREATE TABLE `sensorData`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flowRate` int(11) NULL DEFAULT 0,
  `flowMilliLiitres` bigint(30) NULL DEFAULT 0,
  `totalMilliLitres` bigint(30) NULL DEFAULT 0,
  `readingTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  INDEX `sensorName`(`sensorName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
