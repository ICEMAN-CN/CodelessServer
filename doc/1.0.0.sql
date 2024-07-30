
/*
表名：promotion_link
表备注：用于存储推广链接的信息
*/
CREATE TABLE `promotion_link` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `clid_b1` VARCHAR(255) COMMENT '推广链接的唯一标识，按钮 1 的 ID',
    `clid_b2` VARCHAR(255) COMMENT '推广链接的唯一标识，按钮 2 的 ID',
    `refid` VARCHAR(255) COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `customer_email` VARCHAR(255) COMMENT '客户邮件',
    `business_code` VARCHAR(255) COMMENT '业务码，用于标识业务',
    `status` INT DEFAULT 0 COMMENT '推广链接状态',
    `create_time` DATETIME COMMENT '创建时间',
     INDEX `idx_clid_b1` (`clid_b1`),
     INDEX `idx_clid_b2` (`clid_b2`),
     INDEX `idx_business_code` (`business_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


/*
表名：promotion_link_click
表备注：用于存储推广链接的点击信息
*/
CREATE TABLE `promotion_link_click` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY  COMMENT 'ID',
    `customer_email` VARCHAR(255) COMMENT '客户邮件',
    `clid` VARCHAR(255) COMMENT '推广链接的唯一标识',
    `refid` VARCHAR(255) COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `business_code` VARCHAR(255) COMMENT '业务码，用于标识业务',
    `ip_address` VARCHAR(255) COMMENT '客户IP地址',
    `create_time` DATETIME COMMENT '创建时间',
     INDEX `idx_customer_email` (`customer_email`),
     INDEX `idx_clid` (`clid`),
     INDEX `idx_refid` (`refid`),
     INDEX `idx_business_code` (`business_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;