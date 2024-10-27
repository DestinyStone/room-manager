/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : p1135

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 27/10/2024 14:55:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bus_group
-- ----------------------------
DROP TABLE IF EXISTS `bus_group`;
CREATE TABLE `bus_group`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_user` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_group
-- ----------------------------
INSERT INTO `bus_group` VALUES (1709255575250501633, '大房间', '大房间', -1, '2023-10-11 01:16:10');
INSERT INTO `bus_group` VALUES (1709255641465978881, '中等房', '中等房', -1, '2023-10-11 01:16:10');
INSERT INTO `bus_group` VALUES (1709255666480807937, '小房', '小房', -1, '2023-10-11 01:16:10');

-- ----------------------------
-- Table structure for bus_room
-- ----------------------------
DROP TABLE IF EXISTS `bus_room`;
CREATE TABLE `bus_room`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `convers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `start_money` decimal(10, 0) NULL DEFAULT NULL,
  `money_day` decimal(10, 0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT NULL COMMENT '状态 0 空闲 1入住中',
  `room_status` int(0) NULL DEFAULT NULL COMMENT '房间状态 0 正常 1 清洁中 2 维修中',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `group_id` bigint(0) NULL DEFAULT NULL,
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_user` bigint(0) NULL DEFAULT NULL,
  `number` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bus_user
-- ----------------------------
DROP TABLE IF EXISTS `bus_user`;
CREATE TABLE `bus_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role` varchar(244) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bus_user
-- ----------------------------
INSERT INTO `bus_user` VALUES (1836640910067154946, '$2a$10$CLy37BpwZkkrL8NCu4ZBb.B0bU6bHYNfJhti.y71X/c4DQfmannNu', '$2a$10$eZ84iEhj3Xtqh14ihWwY7.GvpKQJHIvASY1mAxxzT/zc6P52UlJyO', '管理员', '2024-09-19 13:38:10', 'admin', '1', './api/blade-bus/file/download?uuid=ead8e80d-d420-4497-8db2-58113f1b51cd', 'admin', 'admin@qq.com', 'admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
