/*
 Navicat Premium Dump SQL

 Source Server         : aaa
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : oms

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 10/12/2025 15:50:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '科室ID',
  `dept_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科室编码',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科室名称',
  `category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '科室分类(内科/外科/医技...)',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:启用 0:停用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dept_id`) USING BTREE,
  UNIQUE INDEX `uk_dept_code`(`dept_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '科室信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '001', '皮肤科', '外科', 1, '2025-12-03 18:03:36', '2025-12-03 18:03:36');
INSERT INTO `department` VALUES (2, '002', '消化科', '内科', 1, '2025-12-08 19:14:18', '2025-12-08 19:14:18');
INSERT INTO `department` VALUES (3, '003', '神经科', '脑科', 1, '2025-12-08 20:25:25', '2025-12-08 20:25:25');
INSERT INTO `department` VALUES (4, '004', '骨科', '外科', 1, '2025-12-08 20:26:53', '2025-12-08 20:26:53');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `doctor_id` bigint NOT NULL AUTO_INCREMENT COMMENT '医生ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '医生姓名',
  `dept_id` bigint NOT NULL COMMENT '所属科室ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职称(主任医师/主治医师)',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `scheduling_status` tinyint NULL DEFAULT 1 COMMENT '排班状态(1:出诊 0:休息)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint NULL DEFAULT NULL COMMENT '关联用户ID',
  PRIMARY KEY (`doctor_id`) USING BTREE,
  INDEX `idx_dept_id`(`dept_id` ASC) USING BTREE,
  INDEX `fk_doctor_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_doctor_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '医生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (1, '东北雨姐', 1, '主治医师', '10086', 1, '2025-12-03 18:06:45', NULL);
INSERT INTO `doctor` VALUES (2, '宇将军', 1, '主任医师', '10010', 1, '2025-12-03 18:07:43', NULL);
INSERT INTO `doctor` VALUES (3, '陈泽', 1, '普通医师', '10011', 1, '2025-12-03 18:08:18', NULL);
INSERT INTO `doctor` VALUES (4, '芙宁娜', 2, '主治医师', '1234567', 1, '2025-12-08 19:15:16', 2);

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `eq_id` bigint NOT NULL AUTO_INCREMENT,
  `eq_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
  `eq_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备编号',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备类型',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:正常 2:维修 3:报废)',
  `purchase_time` date NULL DEFAULT NULL COMMENT '采购时间',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '所属科室',
  PRIMARY KEY (`eq_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '医院设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipment
-- ----------------------------

-- ----------------------------
-- Table structure for medical_record
-- ----------------------------
DROP TABLE IF EXISTS `medical_record`;
CREATE TABLE `medical_record`  (
  `record_id` bigint NOT NULL AUTO_INCREMENT,
  `reg_id` bigint NOT NULL COMMENT '关联挂号单ID',
  `patient_id` bigint NOT NULL,
  `doctor_id` bigint NOT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主诉(患者描述)',
  `diagnosis` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '诊断结果',
  `advice` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '医嘱',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE,
  UNIQUE INDEX `uk_reg_id`(`reg_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '门诊病历表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medical_record
-- ----------------------------

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `med_id` bigint NOT NULL AUTO_INCREMENT COMMENT '药品ID',
  `med_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '药品编码',
  `med_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '药品名称',
  `format` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格(如: 0.25g*12片/盒)',
  `price` decimal(10, 2) NOT NULL COMMENT '单价',
  `stock` int NULL DEFAULT 0 COMMENT '库存数量',
  `category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '药品分类',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:在售 0:停售)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`med_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '药品信息库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (1, NULL, '阿莫西林胶囊', '0.25g*24粒', 12.50, 100, '西药', 1, '2025-12-02 22:34:58');
INSERT INTO `medicine` VALUES (2, NULL, '布洛芬缓释胶囊', '0.3g*20粒', 25.00, 50, '西药', 1, '2025-12-02 22:34:58');
INSERT INTO `medicine` VALUES (3, NULL, '板蓝根颗粒', '10g*20袋', 15.00, 200, '中成药', 1, '2025-12-02 22:34:58');
INSERT INTO `medicine` VALUES (4, 'MED004', '磷酸奥司他韦', '0.5克', 40.00, 90, '西药', 1, '2025-12-03 00:43:16');

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `patient_id` bigint NOT NULL AUTO_INCREMENT COMMENT '患者ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '患者姓名',
  `id_card` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别(M/F)',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`patient_id`) USING BTREE,
  UNIQUE INDEX `uk_id_card`(`id_card` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '患者信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (1, 'syc', '123', '男', 21, '123456', 'LiYue', '2021-02-02', '2025-12-03 14:33:47');
INSERT INTO `patient` VALUES (2, 'ccc', '410', '男', 18, '123', 'liyue', '2025-12-01', '2025-12-03 17:43:30');
INSERT INTO `patient` VALUES (3, 'siyongchen', '234804426', '男', 21, '234804426', 'LiYue', '2025-12-01', '2025-12-03 17:58:41');
INSERT INTO `patient` VALUES (4, 'changyanbin', '234804428', '男', 21, '123', '123', '2025-12-01', '2025-12-03 18:04:23');
INSERT INTO `patient` VALUES (5, 'ccc', '234804422', '男', 21, '123456', 'liyue', '2025-12-01', '2025-12-03 18:10:19');
INSERT INTO `patient` VALUES (6, 'lzh', '234804433', '男', 23, '123456', '璃月', NULL, '2025-12-09 21:10:11');

-- ----------------------------
-- Table structure for prescription
-- ----------------------------
DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription`  (
  `pres_id` bigint NOT NULL AUTO_INCREMENT,
  `record_id` bigint NOT NULL COMMENT '关联病历ID',
  `reg_id` bigint NOT NULL,
  `total_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `status` tinyint NULL DEFAULT 0 COMMENT '0:未缴费 1:已缴费 2:已发药',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pres_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '处方主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prescription
-- ----------------------------

-- ----------------------------
-- Table structure for prescription_item
-- ----------------------------
DROP TABLE IF EXISTS `prescription_item`;
CREATE TABLE `prescription_item`  (
  `item_id` bigint NOT NULL AUTO_INCREMENT,
  `pres_id` bigint NOT NULL,
  `med_id` bigint NOT NULL COMMENT '药品ID',
  `med_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `quantity` int NOT NULL COMMENT '开药数量',
  `usage_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用法用量',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '处方明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prescription_item
-- ----------------------------

-- ----------------------------
-- Table structure for registration
-- ----------------------------
DROP TABLE IF EXISTS `registration`;
CREATE TABLE `registration`  (
  `reg_id` bigint NOT NULL AUTO_INCREMENT COMMENT '挂号单ID',
  `patient_id` bigint NOT NULL COMMENT '患者ID',
  `doctor_id` bigint NOT NULL COMMENT '医生ID',
  `dept_id` bigint NOT NULL COMMENT '科室ID',
  `level_id` bigint NOT NULL COMMENT '挂号级别ID',
  `settlement_id` bigint NOT NULL COMMENT '结算类别ID',
  `reg_date` date NOT NULL COMMENT '看诊日期',
  `reg_time_slot` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '午别(上午/下午)',
  `fee` decimal(10, 2) NOT NULL COMMENT '实收费用',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:已挂号 2:已问诊 3:已退号 4:已作废)',
  `user_id` bigint NULL DEFAULT NULL COMMENT '操作员ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '挂号时间',
  PRIMARY KEY (`reg_id`) USING BTREE,
  INDEX `idx_patient_id`(`patient_id` ASC) USING BTREE,
  INDEX `idx_doctor_date`(`doctor_id` ASC, `reg_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '挂号记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of registration
-- ----------------------------
INSERT INTO `registration` VALUES (1, 5, 2, 1, 2, 1, '2025-12-03', '上午', 5.00, 1, NULL, '2025-12-03 18:10:25');
INSERT INTO `registration` VALUES (2, 5, 1, 1, 1, 1, '2025-12-03', '上午', 10.00, 1, NULL, '2025-12-03 18:12:17');
INSERT INTO `registration` VALUES (3, 3, 1, 1, 1, 1, '2025-12-03', '上午', 10.00, 1, NULL, '2025-12-03 18:16:18');
INSERT INTO `registration` VALUES (4, 6, 4, 2, 1, 1, '2025-12-09', '上午', 10.00, 1, NULL, '2025-12-09 21:10:19');

-- ----------------------------
-- Table structure for registration_level
-- ----------------------------
DROP TABLE IF EXISTS `registration_level`;
CREATE TABLE `registration_level`  (
  `level_id` bigint NOT NULL AUTO_INCREMENT,
  `level_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '号别名称(专家号/普通号)',
  `fee` decimal(10, 2) NOT NULL COMMENT '挂号费',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '是否默认',
  `status` tinyint NULL DEFAULT 1,
  PRIMARY KEY (`level_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '挂号级别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of registration_level
-- ----------------------------
INSERT INTO `registration_level` VALUES (1, '专家号', 10.00, 0, 1);
INSERT INTO `registration_level` VALUES (2, '普通号', 5.00, 0, 1);

-- ----------------------------
-- Table structure for settlement_category
-- ----------------------------
DROP TABLE IF EXISTS `settlement_category`;
CREATE TABLE `settlement_category`  (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名称(自费/医保/新农合)',
  `ratio` decimal(5, 2) NULL DEFAULT 1.00 COMMENT '报销比例(1.00代表不报销)',
  `status` tinyint NULL DEFAULT 1,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '结算类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of settlement_category
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码(加密存储)',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色(admin, doctor, cashier, pharmacy)',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:正常 0:禁用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', '系统管理员', 'admin', 1, '2025-12-02 21:27:25');
INSERT INTO `sys_user` VALUES (2, 'furina', '123456', '芙宁娜', 'doctor', 1, '2025-12-03 12:12:00');
INSERT INTO `sys_user` VALUES (3, 'lzh', '123456', '李志豪', 'patient', 1, '2025-12-09 21:09:08');

SET FOREIGN_KEY_CHECKS = 1;
