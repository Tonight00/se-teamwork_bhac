/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : bhac

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 11/05/2020 15:48:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bhac_activity
-- ----------------------------
DROP TABLE IF EXISTS `bhac_activity`;
CREATE TABLE `bhac_activity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `category` int(0) NOT NULL,
  `uid` int(0) NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ddl` datetime(0) NULL DEFAULT NULL,
  `begin` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `end` datetime(0) NULL DEFAULT NULL,
  `brief` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `isOpen` int(0) NULL DEFAULT 0,
  `limitPeopleNum` int(0) NULL DEFAULT -1,
  `state` int(0) NULL DEFAULT 0,
  `extra` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_activity
-- ----------------------------
INSERT INTO `bhac_activity` VALUES (1, 1, 3, '北航博雅大讲堂——大学生如何成功创业', '主M201', '2020-04-30 14:00:00', '2020-05-01 19:00:00', '2020-05-01 21:00:00', '这是一堂讲授创业知识的课。', 0, 50, 0, NULL);
INSERT INTO `bhac_activity` VALUES (2, 2, 3, '用美的声音为您赋能', '主M202', '2020-04-30 14:00:00', '2020-05-01 19:00:00', '2020-05-01 21:00:00', '讲授声音的技巧，体验声音的魅力。', 0, 100, 0, NULL);
INSERT INTO `bhac_activity` VALUES (3, 1, 3, '2019年秋季学期第四次电子创新大赛培训', '主127', '2020-05-01 14:00:00', '2020-05-02 19:00:00', '2020-05-02 21:00:00', '这是第四次电子创新大赛培训。', 0, -1, 0, NULL);
INSERT INTO `bhac_activity` VALUES (4, 1, 3, '知行书院辩论赛', '人文大讲堂', '2020-05-02 14:00:00', '2020-05-03 19:00:00', '2020-05-03 21:00:00', '这是知行书院辩论赛。', 0, 200, 0, NULL);
INSERT INTO `bhac_activity` VALUES (5, 3, 3, '航天科普志愿行动', '中国科学馆1层报告厅', '2020-05-03 14:00:00', '2020-05-04 19:00:00', '2020-05-04 21:00:00', '这是航天科普志愿行动。', 0, 50, 0, NULL);
INSERT INTO `bhac_activity` VALUES (6, 3, 3, '航拍中国展览', '沙河艺文空间', '2020-05-04 14:00:00', '2020-05-05 09:00:00', '2020-05-05 11:00:00', '这是航拍中国展览。', 0, -1, 0, NULL);
INSERT INTO `bhac_activity` VALUES (7, 4, 3, '无人驾驶行业初探', '实4-205', '2020-05-05 14:00:00', '2020-05-06 19:00:00', '2020-05-06 21:00:00', '这是无人驾驶行业的初步介绍。', 0, 150, 0, NULL);
INSERT INTO `bhac_activity` VALUES (8, 6, 3, '指挥家于海《我们的祖国》主题讲座', '北航沙河校区咏曼剧场', '2020-05-06 14:00:00', '2020-05-07 19:00:00', '2020-05-07 21:00:00', '这是指挥家于海《我们的祖国》主题讲座。', 0, 75, 0, NULL);
INSERT INTO `bhac_activity` VALUES (9, 2, 3, '爱情心理学——最美时光遇见你', '实4-205', '2020-05-08 14:00:00', '2020-05-09 16:00:00', '2020-05-09 18:00:00', '这是爱情心理学——最美时光遇见你。', 0, 80, 0, NULL);
INSERT INTO `bhac_activity` VALUES (10, 2, 3, '雅思考官 & 环球名师，带你玩转口语', '沙河校区S4-205', '2020-05-10 14:00:00', '2020-05-11 19:00:00', '2020-05-11 21:00:00', '这是雅思考官 & 环球名师，带你玩转口语。', 0, 90, 0, NULL);
INSERT INTO `bhac_activity` VALUES (11, 3, 4, '北京高校书法作品展', '沙河艺文空间', '2020-05-11 14:00:00', '2020-05-12 08:55:00', '2020-05-12 10:55:00', '这是北京高校书法作品展。', 1, -1, 0, NULL);
INSERT INTO `bhac_activity` VALUES (12, 7, 4, '“榜样之光”——计算机学院2018年度表彰大会', '实4-205', '2020-05-11 14:00:00', '2020-05-13 16:30:00', '2020-05-13 18:30:00', '这是“榜样之光”——计算机学院2018年度表彰大会。', 0, 140, 0, NULL);
INSERT INTO `bhac_activity` VALUES (13, 2, 4, '逻辑与人工语言', 'J3-311', '2020-05-12 14:00:00', '2020-05-13 14:30:00', '2020-05-13 16:30:00', '这是逻辑与人工语言。', 0, 150, 0, NULL);
INSERT INTO `bhac_activity` VALUES (14, 7, 4, '“三人行”学术讲座|CV&VR专题', 'J0-003', '2020-05-13 14:00:00', '2020-05-14 19:00:00', '2020-05-14 21:00:00', '这是“三人行”学术讲座|CV&VR专题。', 0, 185, 0, NULL);
INSERT INTO `bhac_activity` VALUES (15, 4, 4, '\"三人行\"学术讲座——大数据', 'J0-001', '2020-05-14 14:00:00', '2020-05-15 19:00:00', '2020-05-15 21:00:00', '这是\"三人行\"学术讲座——大数据。', 0, 50, 0, NULL);
INSERT INTO `bhac_activity` VALUES (16, 2, 4, '相声艺术进校园——曹云金专场', '沙河咏曼剧场', '2020-05-15 14:00:00', '2020-05-16 19:00:00', '2020-05-16 21:00:00', '这是相声艺术进校园——曹云金专场。', 0, 60, 0, NULL);
INSERT INTO `bhac_activity` VALUES (17, 2, 4, '“青春·奋斗”主题团日活动', '沙河咏曼剧场', '2020-05-16 14:00:00', '2020-05-17 19:15:00', '2020-05-17 21:15:00', '这是“青春·奋斗”主题团日活动。', 0, 250, 0, NULL);
INSERT INTO `bhac_activity` VALUES (18, 1, 4, '首都百万师生同上一堂课', 'J0-001、J0-002', '2020-05-17 14:00:00', '2020-05-18 19:30:00', '2020-05-18 21:30:00', '这是首都百万师生同上一堂课。', 0, 250, 0, NULL);
INSERT INTO `bhac_activity` VALUES (19, 5, 4, '软件学院专业宣讲暨开放日', '沙河咏曼剧场', '2020-05-17 14:00:00', '2020-05-18 08:30:00', '2020-05-18 12:00:00', '这是软件学院专业宣讲暨开放日。', 0, -1, 0, NULL);
INSERT INTO `bhac_activity` VALUES (20, 5, 4, '世界那么大，我想去看看-电子信息工程专业宣讲报告', '沙河咏曼剧场', '2020-05-18 14:00:00', '2020-05-19 09:30:00', '2020-05-19 11:30:00', '这是世界那么大，我想去看看-电子信息工程专业宣讲报告。', 0, 240, 0, NULL);
INSERT INTO `bhac_activity` VALUES (21, 5, 4, '科幻成真的时代-信息科学技术的发展与未来', '实4-205', '2020-05-19 14:00:00', '2020-05-20 14:00:00', '2020-05-20 16:00:00', '这是科幻成真的时代-信息科学技术的发展与未来。', 0, 200, 0, NULL);

-- ----------------------------
-- Table structure for bhac_actuserrole
-- ----------------------------
DROP TABLE IF EXISTS `bhac_actuserrole`;
CREATE TABLE `bhac_actuserrole`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `rid` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_actuserrole
-- ----------------------------
INSERT INTO `bhac_actuserrole` VALUES (1, 1, 1);
INSERT INTO `bhac_actuserrole` VALUES (2, 1, 2);
INSERT INTO `bhac_actuserrole` VALUES (3, 1, 3);
INSERT INTO `bhac_actuserrole` VALUES (4, 1, 4);
INSERT INTO `bhac_actuserrole` VALUES (5, 1, 5);
INSERT INTO `bhac_actuserrole` VALUES (6, 1, 6);
INSERT INTO `bhac_actuserrole` VALUES (7, 1, 7);
INSERT INTO `bhac_actuserrole` VALUES (8, 2, 1);
INSERT INTO `bhac_actuserrole` VALUES (9, 2, 3);
INSERT INTO `bhac_actuserrole` VALUES (10, 2, 4);
INSERT INTO `bhac_actuserrole` VALUES (11, 2, 6);
INSERT INTO `bhac_actuserrole` VALUES (12, 2, 7);

-- ----------------------------
-- Table structure for bhac_belongactivitytag
-- ----------------------------
DROP TABLE IF EXISTS `bhac_belongactivitytag`;
CREATE TABLE `bhac_belongactivitytag`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `aid` int(0) NOT NULL,
  `tid` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_belongactivitytag
-- ----------------------------
INSERT INTO `bhac_belongactivitytag` VALUES (1, 1, 1);
INSERT INTO `bhac_belongactivitytag` VALUES (2, 2, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (3, 3, 1);
INSERT INTO `bhac_belongactivitytag` VALUES (4, 4, 1);
INSERT INTO `bhac_belongactivitytag` VALUES (5, 5, 3);
INSERT INTO `bhac_belongactivitytag` VALUES (6, 6, 3);
INSERT INTO `bhac_belongactivitytag` VALUES (7, 7, 4);
INSERT INTO `bhac_belongactivitytag` VALUES (8, 8, 6);
INSERT INTO `bhac_belongactivitytag` VALUES (9, 9, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (10, 10, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (11, 11, 3);
INSERT INTO `bhac_belongactivitytag` VALUES (12, 12, 7);
INSERT INTO `bhac_belongactivitytag` VALUES (13, 13, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (14, 14, 7);
INSERT INTO `bhac_belongactivitytag` VALUES (15, 15, 4);
INSERT INTO `bhac_belongactivitytag` VALUES (16, 16, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (17, 17, 2);
INSERT INTO `bhac_belongactivitytag` VALUES (18, 18, 1);
INSERT INTO `bhac_belongactivitytag` VALUES (19, 19, 5);
INSERT INTO `bhac_belongactivitytag` VALUES (20, 20, 5);
INSERT INTO `bhac_belongactivitytag` VALUES (21, 21, 5);

-- ----------------------------
-- Table structure for bhac_comment
-- ----------------------------
DROP TABLE IF EXISTS `bhac_comment`;
CREATE TABLE `bhac_comment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pid` int(0) NOT NULL,
  `seqNum` int(0) NOT NULL,
  `postedBy` int(0) NOT NULL,
  `parentId` int(0) NULL DEFAULT NULL,
  `content` blob NULL,
  `date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_comment
-- ----------------------------
INSERT INTO `bhac_comment` VALUES (1, 1, 1, 1, NULL, 0x66697273745F636F6D6D656E74, '2020-04-30 18:06:28');
INSERT INTO `bhac_comment` VALUES (2, 2, 2, 1, NULL, 0x7365636F6E645F636F6D6D656E74, '2020-04-30 18:06:28');
INSERT INTO `bhac_comment` VALUES (3, 3, 3, 1, NULL, 0x74686972645F636F6D6D656E74, '2020-04-30 18:06:28');

-- ----------------------------
-- Table structure for bhac_joinuseractivity
-- ----------------------------
DROP TABLE IF EXISTS `bhac_joinuseractivity`;
CREATE TABLE `bhac_joinuseractivity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `aid` int(0) NOT NULL,
  `state` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_joinuseractivity
-- ----------------------------
INSERT INTO `bhac_joinuseractivity` VALUES (1, 1, 1, 0);
INSERT INTO `bhac_joinuseractivity` VALUES (2, 1, 2, 0);
INSERT INTO `bhac_joinuseractivity` VALUES (3, 1, 3, 1);
INSERT INTO `bhac_joinuseractivity` VALUES (4, 2, 2, 1);
INSERT INTO `bhac_joinuseractivity` VALUES (5, 2, 3, 1);
INSERT INTO `bhac_joinuseractivity` VALUES (6, 3, 3, 1);

-- ----------------------------
-- Table structure for bhac_manageuseractivity
-- ----------------------------
DROP TABLE IF EXISTS `bhac_manageuseractivity`;
CREATE TABLE `bhac_manageuseractivity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uid` int(0) NOT NULL,
  `aid` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_manageuseractivity
-- ----------------------------
INSERT INTO `bhac_manageuseractivity` VALUES (1, 1, 1);
INSERT INTO `bhac_manageuseractivity` VALUES (2, 1, 2);
INSERT INTO `bhac_manageuseractivity` VALUES (3, 1, 3);
INSERT INTO `bhac_manageuseractivity` VALUES (4, 1, 4);
INSERT INTO `bhac_manageuseractivity` VALUES (5, 1, 5);
INSERT INTO `bhac_manageuseractivity` VALUES (6, 1, 6);
INSERT INTO `bhac_manageuseractivity` VALUES (7, 1, 7);
INSERT INTO `bhac_manageuseractivity` VALUES (8, 1, 8);
INSERT INTO `bhac_manageuseractivity` VALUES (9, 1, 9);
INSERT INTO `bhac_manageuseractivity` VALUES (10, 1, 10);
INSERT INTO `bhac_manageuseractivity` VALUES (11, 1, 11);
INSERT INTO `bhac_manageuseractivity` VALUES (12, 1, 12);
INSERT INTO `bhac_manageuseractivity` VALUES (13, 1, 13);
INSERT INTO `bhac_manageuseractivity` VALUES (14, 1, 14);
INSERT INTO `bhac_manageuseractivity` VALUES (15, 1, 15);
INSERT INTO `bhac_manageuseractivity` VALUES (16, 1, 16);
INSERT INTO `bhac_manageuseractivity` VALUES (17, 1, 17);
INSERT INTO `bhac_manageuseractivity` VALUES (18, 1, 18);
INSERT INTO `bhac_manageuseractivity` VALUES (19, 1, 19);
INSERT INTO `bhac_manageuseractivity` VALUES (20, 1, 20);
INSERT INTO `bhac_manageuseractivity` VALUES (21, 1, 21);
INSERT INTO `bhac_manageuseractivity` VALUES (22, 2, 1);
INSERT INTO `bhac_manageuseractivity` VALUES (23, 2, 2);
INSERT INTO `bhac_manageuseractivity` VALUES (24, 2, 3);
INSERT INTO `bhac_manageuseractivity` VALUES (25, 2, 4);
INSERT INTO `bhac_manageuseractivity` VALUES (26, 2, 5);
INSERT INTO `bhac_manageuseractivity` VALUES (27, 2, 6);
INSERT INTO `bhac_manageuseractivity` VALUES (28, 2, 7);
INSERT INTO `bhac_manageuseractivity` VALUES (29, 2, 8);
INSERT INTO `bhac_manageuseractivity` VALUES (30, 2, 9);
INSERT INTO `bhac_manageuseractivity` VALUES (31, 2, 10);
INSERT INTO `bhac_manageuseractivity` VALUES (32, 2, 11);
INSERT INTO `bhac_manageuseractivity` VALUES (33, 2, 12);
INSERT INTO `bhac_manageuseractivity` VALUES (34, 2, 13);
INSERT INTO `bhac_manageuseractivity` VALUES (35, 2, 14);
INSERT INTO `bhac_manageuseractivity` VALUES (36, 2, 15);
INSERT INTO `bhac_manageuseractivity` VALUES (37, 2, 16);
INSERT INTO `bhac_manageuseractivity` VALUES (38, 2, 17);
INSERT INTO `bhac_manageuseractivity` VALUES (39, 2, 18);
INSERT INTO `bhac_manageuseractivity` VALUES (40, 2, 19);
INSERT INTO `bhac_manageuseractivity` VALUES (41, 2, 20);
INSERT INTO `bhac_manageuseractivity` VALUES (42, 2, 21);
INSERT INTO `bhac_manageuseractivity` VALUES (43, 3, 1);
INSERT INTO `bhac_manageuseractivity` VALUES (44, 3, 2);
INSERT INTO `bhac_manageuseractivity` VALUES (45, 3, 3);
INSERT INTO `bhac_manageuseractivity` VALUES (46, 3, 4);
INSERT INTO `bhac_manageuseractivity` VALUES (47, 3, 5);
INSERT INTO `bhac_manageuseractivity` VALUES (48, 3, 6);
INSERT INTO `bhac_manageuseractivity` VALUES (49, 3, 7);
INSERT INTO `bhac_manageuseractivity` VALUES (50, 3, 8);
INSERT INTO `bhac_manageuseractivity` VALUES (51, 3, 9);
INSERT INTO `bhac_manageuseractivity` VALUES (52, 3, 10);
INSERT INTO `bhac_manageuseractivity` VALUES (53, 4, 11);
INSERT INTO `bhac_manageuseractivity` VALUES (54, 4, 12);
INSERT INTO `bhac_manageuseractivity` VALUES (55, 4, 13);
INSERT INTO `bhac_manageuseractivity` VALUES (56, 4, 14);
INSERT INTO `bhac_manageuseractivity` VALUES (57, 4, 15);
INSERT INTO `bhac_manageuseractivity` VALUES (58, 4, 16);
INSERT INTO `bhac_manageuseractivity` VALUES (59, 4, 17);
INSERT INTO `bhac_manageuseractivity` VALUES (60, 4, 18);
INSERT INTO `bhac_manageuseractivity` VALUES (61, 4, 19);
INSERT INTO `bhac_manageuseractivity` VALUES (62, 4, 20);
INSERT INTO `bhac_manageuseractivity` VALUES (63, 4, 21);

-- ----------------------------
-- Table structure for bhac_post
-- ----------------------------
DROP TABLE IF EXISTS `bhac_post`;
CREATE TABLE `bhac_post`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `aid` int(0) NULL DEFAULT NULL,
  `tid` int(0) NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `postedBy` int(0) NOT NULL,
  `numOfComment` int(0) NULL DEFAULT NULL,
  `lastEdited` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `type` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_post
-- ----------------------------
INSERT INTO `bhac_post` VALUES (1, 1, 1, '', 1, 1, '2020-04-30 17:10:28', 0);
INSERT INTO `bhac_post` VALUES (2, 1, 2, '', 1, 2, '2020-04-30 17:10:29', 0);

-- ----------------------------
-- Table structure for bhac_role
-- ----------------------------
DROP TABLE IF EXISTS `bhac_role`;
CREATE TABLE `bhac_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `tid` int(0) NOT NULL,
  `state` int(0) NULL DEFAULT -1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_role
-- ----------------------------
INSERT INTO `bhac_role` VALUES (1, 1, 0);
INSERT INTO `bhac_role` VALUES (2, 2, 0);
INSERT INTO `bhac_role` VALUES (3, 3, 0);
INSERT INTO `bhac_role` VALUES (4, 4, 0);
INSERT INTO `bhac_role` VALUES (5, 5, 0);
INSERT INTO `bhac_role` VALUES (6, 6, 0);
INSERT INTO `bhac_role` VALUES (7, 7, 0);

-- ----------------------------
-- Table structure for bhac_tag
-- ----------------------------
DROP TABLE IF EXISTS `bhac_tag`;
CREATE TABLE `bhac_tag`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` int(0) NULL DEFAULT 0,
  `parent_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_tag
-- ----------------------------
INSERT INTO `bhac_tag` VALUES (1, '博雅课程-讲座-综合', 0, NULL);
INSERT INTO `bhac_tag` VALUES (2, '博雅课程-讲座-人文', 0, NULL);
INSERT INTO `bhac_tag` VALUES (3, '博雅课程-参观', 0, NULL);
INSERT INTO `bhac_tag` VALUES (4, '博雅课程-讲座-科技', 0, NULL);
INSERT INTO `bhac_tag` VALUES (5, '博雅课程-讲座-信息', 0, NULL);
INSERT INTO `bhac_tag` VALUES (6, '博雅课程-讲座-文艺', 0, NULL);
INSERT INTO `bhac_tag` VALUES (7, '博雅课程-学校/院文化素质教育活动', 0, NULL);

-- ----------------------------
-- Table structure for bhac_user
-- ----------------------------
DROP TABLE IF EXISTS `bhac_user`;
CREATE TABLE `bhac_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phoneNum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` int(0) NULL DEFAULT 0,
  `firstName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `lastName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `studentId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatarUrl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `phoneNum`(`phoneNum`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bhac_user
-- ----------------------------
INSERT INTO `bhac_user` VALUES (1, 'system1', '4646466@qq.com', '13886454565', '123456', 1, '郑', '熙悦', '17890255', NULL, 1);
INSERT INTO `bhac_user` VALUES (2, 'manager1', '55646456@qq.com', '18100011007', '123456', 0, '许', '立国', '17896522', NULL, 0);
INSERT INTO `bhac_user` VALUES (3, 'user1', '54547545@qq.com', '13456854595', '123456', 0, '关', '天伟', '17895685', NULL, 0);
INSERT INTO `bhac_user` VALUES (4, 'user2', '3564744545@qq.com', '17548575749', '123456', 0, '褚', '岩', '17896523', NULL, 0);
INSERT INTO `bhac_user` VALUES (5, 'user3', '65885455@qq.com', '17555421245', '123456', 0, '李', '长水', '17892145', NULL, 2);

SET FOREIGN_KEY_CHECKS = 1;
