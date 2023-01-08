/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : store_user

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 07/01/2023 23:42:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址主键',
  `linkman` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人',
  `phone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '何超', '18773283353', '湖南软件职业技术大学\n', 12);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PASSWORD` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_phonenumber` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin111', '8348898D532023C994920A16074C8387', NULL);
INSERT INTO `user` VALUES (3, 'admin222', 'DC483E80A7A0BD9EF71D8CF973673924', NULL);
INSERT INTO `user` VALUES (4, 'admin3322', 'DC483E80A7A0BD9EF71D8CF973673924', NULL);
INSERT INTO `user` VALUES (5, 'admin332244', '9701C3182D0AAC2E089BB6665FC430A5', NULL);
INSERT INTO `user` VALUES (6, 'zhao11111', '8348898D532023C994920A16074C8387', NULL);
INSERT INTO `user` VALUES (7, 'zhaoweifeng1111', '47200157CE5E1B5EFA5258250680C708', NULL);
INSERT INTO `user` VALUES (9, 'admin321', '47200157CE5E1B5EFA5258250680C708', NULL);
INSERT INTO `user` VALUES (10, 'admin1111', '33E8299D8A8E65A8B3D67C1E9F4C8B5', NULL);
INSERT INTO `user` VALUES (11, 'admin321321', '9701C3182D0AAC2E089BB6665FC430A5', NULL);
INSERT INTO `user` VALUES (12, 'hechao', '8A2AA989F4DA745D32949A5C46833118', '18773283353');

SET FOREIGN_KEY_CHECKS = 1;
