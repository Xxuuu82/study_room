/*
 Navicat Premium Dump SQL

 Source Server         : connect
 Source Server Type    : MySQL
 Source Server Version : 80021 (8.0.21)
 Source Host           : localhost:3306
 Source Schema         : study_room

 Target Server Type    : MySQL
 Target Server Version : 80021 (8.0.21)
 File Encoding         : 65001

 Date: 20/12/2025 13:16:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `complaint_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '投诉编号(对外展示)',
  `user_id` bigint NOT NULL COMMENT '投诉人学号',
  `room_id` bigint NOT NULL COMMENT '自习室',
  `seat_id` bigint NOT NULL COMMENT '座位号',
  `category` tinyint NOT NULL COMMENT '类型:1乱吃乱喝2大声喧哗3随地大小便4其他',
  `evidence_urls` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '证据图片URL(JSON数组)',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '投诉详情',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '0待处理1处理中2已结案3已驳回',
  `is_processed` tinyint NOT NULL DEFAULT 0 COMMENT '是否已处理',
  `is_approved` tinyint NULL DEFAULT NULL COMMENT '是否通过(结案/驳回结果)',
  `handler_admin_id` bigint NULL DEFAULT NULL COMMENT '处理管理员ID',
  `handle_summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理结论摘要',
  `reject_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `handled_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `complaint_no`(`complaint_no` ASC) USING BTREE,
  INDEX `idx_complaint_user`(`user_id` ASC) USING BTREE,
  INDEX `idx_complaint_room_seat`(`room_id` ASC, `seat_id` ASC) USING BTREE,
  INDEX `idx_status_time`(`status` ASC, `created_at` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户投诉主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES (1, 'CP202512180003', 20230003, 2, 5, 2, '[]', '多次提醒仍持续喧哗，建议管理员处理。', 2, 1, 1, 1002, '已现场劝导并记录，后续再犯将加入黑名单。', NULL, '2025-12-15 23:30:02', '2025-12-15 23:30:02', '2025-12-15 23:30:02');
INSERT INTO `complaint` VALUES (2, 'CP202512180004', 20230004, 2, 9, 4, NULL, '怀疑有人占座，但未看到本人，只有空座位照片。', 3, 1, 0, 1002, NULL, '证据不足，无法确认占座人员，请补充清晰证据或时间段描述。', '2025-12-13 23:30:02', '2025-12-13 23:30:02', '2025-12-13 23:30:02');
INSERT INTO `complaint` VALUES (3, 'CP202512180005', 20230005, 2, 11, 2, '[\"http://localhost:8080/study_room/upload/evidence1.jpg\"]', '隔壁同学一直大声打电话影响学习', 0, 0, NULL, NULL, NULL, NULL, '2025-12-18 10:15:00', '2025-12-18 10:15:00', NULL);
INSERT INTO `complaint` VALUES (4, 'CP202512180006', 20230006, 1, 3, 1, '[]', '座位上吃泡面气味很重，且有洒落', 1, 0, NULL, 1, NULL, NULL, '2025-12-18 11:05:00', '2025-12-18 11:20:00', NULL);
INSERT INTO `complaint` VALUES (5, 'CP202512180007', 20230007, 3, 20, 4, '[\"http://localhost:8080/study_room/upload/evidence2.jpg\",\"http://localhost:8080/study_room/upload/evidence3.jpg\"]', '疑似占座，桌上放书包但人不在很久', 2, 1, 1, 1, '已提醒并清理占座物品，恢复座位使用', NULL, '2025-12-18 09:30:00', '2025-12-18 12:10:00', '2025-12-18 12:10:00');
INSERT INTO `complaint` VALUES (6, 'CP202512180008', 20230008, 2, 8, 3, '[\"http://localhost:8080/study_room/upload/evidence4.jpg\"]', '有人在自习室内做不文明行为（待核实）', 3, 1, 0, 1, NULL, '证据不清晰且无法核实具体座位/人员', '2025-12-17 20:30:00', '2025-12-18 08:40:00', '2025-12-18 08:40:00');
INSERT INTO `complaint` VALUES (7, 'CP202512180009', 20230009, 1, 15, 4, NULL, '座位附近有人频繁走动影响学习，希望管理员巡查', 0, 0, NULL, NULL, NULL, NULL, '2025-12-18 13:00:00', '2025-12-18 13:00:00', NULL);
INSERT INTO `complaint` VALUES (8, 'CP202512180010', 20230010, 3, 6, 2, '[]', '两人聊天声音很大，多次提醒无效', 1, 0, NULL, 1, NULL, NULL, '2025-12-18 14:25:00', '2025-12-18 14:30:00', NULL);

SET FOREIGN_KEY_CHECKS = 1;
