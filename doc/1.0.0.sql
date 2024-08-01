/*
 Navicat Premium Data Transfer

 Source Server         : daqingniu
 Source Server Type    : MySQL
 Source Server Version : 80024 (8.0.24)
 Source Host           : 49.232.70.73:3306
 Source Schema         : codeless

 Target Server Type    : MySQL
 Target Server Version : 80024 (8.0.24)
 File Encoding         : 65001

 Date: 01/08/2024 16:45:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for click_statistics
-- ----------------------------
DROP TABLE IF EXISTS `click_statistics`;
CREATE TABLE `click_statistics` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '推广链接的唯一标识对应的按钮编号',
    `clid_button` int DEFAULT NULL COMMENT '推广链接的唯一标识对应的按钮编号',
    `refid` varchar(255) DEFAULT NULL COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `business_code` varchar(255) DEFAULT NULL COMMENT '业务码，用于标识业务',
    `pv` bigint DEFAULT NULL COMMENT 'pv',
    `uv` bigint DEFAULT NULL COMMENT 'uv',
    `customer_email` varchar(255) DEFAULT NULL COMMENT '客户邮箱',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `statistics_type` varchar(255) DEFAULT NULL COMMENT '统计类型，用于区分统计的类型',
     PRIMARY KEY (`id`),
    KEY `idx_cl_id_button` (`clid_button`),
    KEY `idx_refid` (`refid`),
    KEY `idx_business_code` (`business_code`),
    KEY `idx_customer_email` (`customer_email`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_statistics_type` (`statistics_type`)
) ENGINE=InnoDB AUTO_INCREMENT=735 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点击统计表';

-- ----------------------------
-- Table structure for promotion_link
-- ----------------------------
DROP TABLE IF EXISTS `promotion_link`;
CREATE TABLE `promotion_link` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `clid_b1` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广链接的唯一标识，按钮 1 的 ID',
    `clid_b2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广链接的唯一标识，按钮 2 的 ID',
    `refid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `customer_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户邮件',
    `business_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务码，用于标识业务',
    `status` int DEFAULT '0' COMMENT '推广链接状态',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY (`id`),
    KEY `idx_clid_b1` (`clid_b1`),
    KEY `idx_clid_b2` (`clid_b2`),
    KEY `idx_business_code` (`business_code`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for promotion_link_click
-- ----------------------------
DROP TABLE IF EXISTS `promotion_link_click`;
CREATE TABLE `promotion_link_click` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `customer_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户邮件',
    `clid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广链接的唯一标识',
    `clid_button` int DEFAULT NULL COMMENT '推广链接的唯一标识对应的按钮编号',
    `refid` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `business_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业务码，用于标识业务',
    `ip_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户IP地址',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY (`id`),
    KEY `idx_customer_email` (`customer_email`),
    KEY `idx_clid` (`clid`),
    KEY `idx_refid` (`refid`),
    KEY `idx_business_code` (`business_code`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- 重复打开 天粒度不同业务不同来源的按钮点击pv和uv
SELECT
    clid_button,
    refid,
    business_code,
    count( 1 ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
where create_time >= "2024-07-31 00:00:00" and create_time <= "2024-08-01 00:00:00"
GROUP BY
    clid_button,refid, business_code
ORDER BY business_code desc

-- 重复打开 天粒度不同业务不同来源的页面打开pc uv
SELECT
    refid,
    business_code,
    count( 1 ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
where create_time >= "2024-07-31 00:00:00" and create_time <= "2024-08-01 00:00:00"
GROUP BY
    refid, business_code
ORDER BY business_code desc

-- 重复打开 总不同业务不同来源的按钮点击pv和uv
SELECT
    clid_button,
    refid,
    business_code,
    count( 1 ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
GROUP BY
    clid_button,refid, business_code
ORDER BY business_code desc

-- 重复打开 总不同业务不同来源的页面打开pc uv
SELECT
    refid,
    business_code,
    count( 1 ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
GROUP BY
    refid, business_code
ORDER BY business_code desc


-----
-- 单次打开 天粒度不同业务不同来源的按钮点击pv和uv
SELECT
    clid_button,
    refid,
    business_code,
    count(DISTINCT( clid ) ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
where create_time >= "2024-07-31 00:00:00" and create_time <= "2024-08-01 00:00:00"
GROUP BY
    clid_button,refid, business_code
ORDER BY business_code desc

-- 单次打开 天粒度不同业务不同来源的页面打开pc uv
SELECT
    refid,
    business_code,
    count(DISTINCT( clid ) ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
where create_time >= "2024-07-31 00:00:00" and create_time <= "2024-08-01 00:00:00"
GROUP BY
    refid, business_code
ORDER BY business_code desc

-- 单次打开 总不同业务不同来源的按钮点击pv和uv
SELECT
    clid_button,
    refid,
    business_code,
    count(DISTINCT( clid ) ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
GROUP BY
    clid_button,refid, business_code
ORDER BY business_code desc

-- 单次打开 总不同业务不同来源的页面打开pc uv
SELECT
    refid,
    business_code,
    count(DISTINCT( clid ) ) AS pv,
    count(DISTINCT ( customer_email )) AS uv
FROM
    promotion_link_click
GROUP BY
    refid, business_code
ORDER BY business_code desc


----总 同一个邮箱打开多次   邮箱打开次数排名倒序和邮箱信息   按钮1 按钮2 总
----总打开
SELECT
    customer_email,
    refid,
    business_code,
    count(clid) AS pv
FROM
    promotion_link_click
GROUP BY
    refid, business_code, customer_email
ORDER BY pv desc

---- 分按钮
SELECT
    clid_button,
    customer_email,
    refid,
    business_code,
    count(clid) AS pv
FROM
    promotion_link_click
GROUP BY
    refid, business_code, customer_email,clid_button
ORDER BY pv desc
