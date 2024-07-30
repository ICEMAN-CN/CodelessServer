
CREATE TABLE business_info (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
                               name VARCHAR(255) NOT NULL COMMENT '业务名称',
                               description TEXT COMMENT '客户事件描述信息',
                               create_time DATETIME COMMENT '创建时间',
                               update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务信息';

CREATE TABLE ip_address (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
                            content VARCHAR(255) NOT NULL COMMENT '字符串内容',
                            create_time DATETIME COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IP地址表';

CREATE TABLE promotion_link (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
                               clid VARCHAR(255) NOT NULL COMMENT '推广链接的唯一标识',
                               refid VARCHAR(255) COMMENT '推广链接来源标识，用于标记链接来源渠道：邮件、网页',
                               customer_id BIGINT COMMENT '客户标识',
                               business_code VARCHAR(255) DEFAULT 'abc' COMMENT '业务码，用于标识业务',
                               status INT DEFAULT 0 COMMENT '推广链接状态',
                               create_time DATETIME COMMENT '创建时间',

                               UNIQUE INDEX idx_clid (clid),
                               INDEX idx_refid (refid),
                               INDEX idx_business_code (business_code),
                               INDEX idx_customer_id (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推广链接基本信息';
CREATE TABLE promotion_link_click_event (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
                                           customer_id BIGINT COMMENT '客户标识',
                                           anonymous_customer_id BIGINT COMMENT '匿名客户ID',
                                           promotion_link_id BIGINT COMMENT '推广链接ID',
                                           ip_address_id BIGINT COMMENT '客户IP地址ID',
                                           business_code VARCHAR(255) DEFAULT 'abc' COMMENT '业务码，用于标识业务',
                                           create_time DATETIME COMMENT '创建时间',

                                           INDEX idx_promotion_link_id (promotion_link_id),
                                           INDEX idx_customer_id (customer_id),
                                           INDEX idx_business_code (business_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推广链接点击信息';

CREATE TABLE `customer` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
                            `status` int(11) DEFAULT '1' COMMENT '客户状态', -- 假设NORMAL.getValue()返回1
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='客户基本信息';