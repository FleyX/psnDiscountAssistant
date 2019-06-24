/*
 Navicat Premium Data Transfer

 Source Server         : 10.82.27.177_3311
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 10.82.27.177:3311
 Source Schema         : psn

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 29/03/2019 09:35:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attention_price
-- ----------------------------
DROP TABLE IF EXISTS `attention_price`;
CREATE TABLE `attention_price`  (
  `gameId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `type` enum('once_cut','lowest','lower') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'once_cut',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`gameId`, `userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cut_message
-- ----------------------------
DROP TABLE IF EXISTS `cut_message`;
CREATE TABLE `cut_message`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gameId` int(10) UNSIGNED NOT NULL,
  `isOver` tinyint(255) NOT NULL,
  `createTime` bigint(255) UNSIGNED NOT NULL,
  `overTime` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for flyway_schema_history
-- ----------------------------
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history`  (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `checksum` int(11) NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `installed_on` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `flyway_schema_history_s_idx`(`success`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `serverId` int(10) UNSIGNED NOT NULL,
  `storeId` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '在游戏商店的id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '游戏名称',
  `platform` enum('PS4','PS3','PSV') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'PS4' COMMENT '游戏平台:PS4,PS3,PSV',
  `gameContent` enum('套装','游戏') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '游戏' COMMENT '游戏类别',
  `zhName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '中文名',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '游戏描述',
  `language` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '支持语言：中,日,韩 等',
  `publishTime` bigint(20) NOT NULL COMMENT '发售时间',
  `type` enum('动作','冒险','街机','桌上游戏','大脑训练游戏','休闲','家庭','格斗','恐怖','音乐&节奏','排队','益智','问答游戏','赛车','角色扮演游戏','射击','模拟','运动','战略','独特游戏','MUSIC/RHYTHM','其他','派对') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '其他',
  `currentPrice` mediumint(8) UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前价格*100',
  `plusPrice` mediumint(9) NOT NULL DEFAULT -1 COMMENT '-1表示无plus折扣,0表示plus价格*100',
  `originPrice` mediumint(8) UNSIGNED NOT NULL DEFAULT 0 COMMENT '原价*100',
  `cutPercent` tinyint(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '折扣',
  `cutOverTime` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `isLowest` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否史低2:持平史低，1：是，0：否',
  `psnScore` smallint(5) UNSIGNED NOT NULL COMMENT 'psn商店评分',
  `psnScoreNum` mediumint(8) UNSIGNED NOT NULL DEFAULT 0 COMMENT '评分人数',
  `mediaScore` smallint(5) UNSIGNED NOT NULL COMMENT '媒体评分*100',
  `createTime` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `updateTime` bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `parentStoreId` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父storeId',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `name_desc_full_index`(`name`, `description`) WITH PARSER `ngram`,
  FULLTEXT INDEX `asdf`(`name`) WITH PARSER `ngram`
) ENGINE = InnoDB AUTO_INCREMENT = 0 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for language
-- ----------------------------
DROP TABLE IF EXISTS `language`;
CREATE TABLE `language`  (
  `gId` int(10) UNSIGNED NOT NULL COMMENT 'game ID',
  `language` enum('英','日','中','韩','简中','繁中') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '英',
  PRIMARY KEY (`gId`, `language`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '游戏支持语言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for price_history
-- ----------------------------
DROP TABLE IF EXISTS `price_history`;
CREATE TABLE `price_history`  (
  `date` bigint(20) UNSIGNED NOT NULL COMMENT '时间',
  `gId` int(10) UNSIGNED NOT NULL COMMENT '游戏id',
  `price` mediumint(8) UNSIGNED NOT NULL COMMENT '当时价格*100',
  `plusPrice` mediumint(10) NOT NULL DEFAULT 0 COMMENT '会员价格*100',
  PRIMARY KEY (`gId`, `date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '游戏价格历史表\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for server
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server`  (
  `id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` enum('美服','日服','台服','港服','国服') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exchangeRate` double NOT NULL DEFAULT 0 COMMENT '对于人民币的汇率，1当前货币对于的人民币',
  `moneySymbol` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `listApi` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ps4游戏列表api\r\n[{\r\n  platform:\"PS4\",\r\n  api:\"\"\r\n}]\r\n\r\nps4 api:https://store.playstation.com/valkyrie-api/zh/HK/999/container/STORE-MSF86012-PS4TITLES?size=30&bucket=games&start=0\r\n\r\n详情api：https://store.playstation.com/valkyrie-api/zh/HK/999/resolve/EP4008-CUSA12718_00-ASIAFULLGAME0000',
  `gameDetailApi` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '游戏详情api',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `server` VALUES (1, '港服', 0.86, 'HK$', '[{\"platform\":\"PS4\",\"api\":\"https://store.playstation.com/valkyrie-api/zh/HK/999/container/STORE-MSF86012-PS4TITLES?size=30&bucket=games&start=****\"}]', 'https://store.playstation.com/valkyrie-api/zh/HK/999/resolve/****');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wxId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '微信id',
  `username` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '6-16位字母，数字组合',
  `password` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '加密后密码\r\n加密方案：前台计算一次sha1\r\n后台加盐计算一次sha1',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
