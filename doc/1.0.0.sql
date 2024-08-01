
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
    `clid_button` INT COMMENT '推广链接的唯一标识对应的按钮编号',
    `refid` VARCHAR(255) COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
    `business_code` VARCHAR(255) COMMENT '业务码，用于标识业务',
    `ip_address` VARCHAR(255) COMMENT '客户IP地址',
    `create_time` DATETIME COMMENT '创建时间',
     INDEX `idx_customer_email` (`customer_email`),
     INDEX `idx_clid` (`clid`),
     INDEX `idx_refid` (`refid`),
     INDEX `idx_business_code` (`business_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
