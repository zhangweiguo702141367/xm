/*
Navicat MySQL Data Transfer

Source Server         : 阿里云数据库
Source Server Version : 50552
Source Host           : 47.92.87.170:3306
Source Database       : zh_system

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-05-17 23:34:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('QuartzScheduler', 'com.zh.common.job.Minute2Job', 'Job_group', '10 0/2 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('QuartzScheduler', 'com.zh.common.job.MinuteJob', 'Job_group', '0 0/1 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('QuartzScheduler', 'com.zh.common.job.Minute2Job', 'Job_group', 'Test job Minute2Job', 'com.zh.common.job.Minute2Job', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('QuartzScheduler', 'com.zh.common.job.MinuteJob', 'Job_group', 'Test Job one', 'com.zh.common.job.MinuteJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);

-- ----------------------------
-- Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('QuartzScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('QuartzScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('QuartzScheduler', 'NON_CLUSTERED', '1493982421314', '7500');

-- ----------------------------
-- Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('QuartzScheduler', 'com.zh.common.job.Minute2Job', 'Job_group', 'com.zh.common.job.Minute2Job', 'Job_group', '2016-10-09 16:08:49', '1493982490000', '1493982370000', '5', 'WAITING', 'CRON', '1476000529000', '0', null, '2', '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('QuartzScheduler', 'com.zh.common.job.MinuteJob', 'Job_group', 'com.zh.common.job.MinuteJob', 'Job_group', '2016-10-09 15:48:03', '1493982480000', '1493982420000', '5', 'WAITING', 'CRON', '1475999283000', '0', null, '2', '');

-- ----------------------------
-- Table structure for `sys_baidu_conf`
-- ----------------------------
DROP TABLE IF EXISTS `sys_baidu_conf`;
CREATE TABLE `sys_baidu_conf` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` varchar(32) NOT NULL COMMENT '参数简称',
  `name` varchar(32) NOT NULL COMMENT '描述',
  `value` varchar(255) NOT NULL COMMENT '值',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='百度地图系统配置';

-- ----------------------------
-- Records of sys_baidu_conf
-- ----------------------------
INSERT INTO `sys_baidu_conf` VALUES ('1', 'map_ak', '百度地图密钥AK', '5wim9Xj10uGHkX2as8td5IcalCyGdmb2', '1494994506');
INSERT INTO `sys_baidu_conf` VALUES ('2', 'map_bdurl', '百度地图访问域名', 'https://api.map.baidu.com/location/ip', '1494994506');
INSERT INTO `sys_baidu_conf` VALUES ('3', 'map_shorturl', '百度地图地址', '/location/ip/?', '1494994506');

-- ----------------------------
-- Table structure for `sys_baidu_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_baidu_log`;
CREATE TABLE `sys_baidu_log` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(32) NOT NULL COMMENT 'IP地址',
  `resp` varchar(255) NOT NULL COMMENT '百度地图返回结果',
  `province` varchar(12) NOT NULL COMMENT '省份',
  `city` varchar(12) NOT NULL COMMENT '城市',
  `city_code` varchar(12) NOT NULL COMMENT '城市code',
  `point_x` varchar(12) NOT NULL COMMENT '所在点x坐标',
  `point_y` varchar(12) NOT NULL COMMENT '所在点y坐标',
  `create_time` int(12) NOT NULL COMMENT '添加时间',
  `member_id` int(12) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='百度获取用户所在地址表';

-- ----------------------------
-- Records of sys_baidu_log
-- ----------------------------
INSERT INTO `sys_baidu_log` VALUES ('1', '106.39.84.154', '{\"address\":\"CN|北京|北京|None|CHINANET|0|0\",\"content\":{\"address_detail\":{\"province\":\"北京市\",\"city\":\"北京市\",\"street\":\"\",\"district\":\"\",\"street_number\":\"\",\"city_code\":131},\"address\":\"北京市\",\"point\":{\"x\":\"12958160.97\",\"y\":\"4825907.72\"}},\"status\":0}', '北京市', '北京市', '131', '12958160.97', '4825907.72', '1495034751', '123');
INSERT INTO `sys_baidu_log` VALUES ('2', '106.39.84.154', '{\"address\":\"CN|北京|北京|None|CHINANET|0|0\",\"content\":{\"address_detail\":{\"province\":\"北京市\",\"city\":\"北京市\",\"street\":\"\",\"district\":\"\",\"street_number\":\"\",\"city_code\":131},\"address\":\"北京市\",\"point\":{\"x\":\"12958160.97\",\"y\":\"4825907.72\"}},\"status\":0}', '北京市', '北京市', '131', '12958160.97', '4825907.72', '1495034822', '123');
INSERT INTO `sys_baidu_log` VALUES ('3', '223.72.71.218', '{\"address\":\"CN|北京|北京|None|CMNET|0|0\",\"content\":{\"address_detail\":{\"province\":\"北京市\",\"city\":\"北京市\",\"street\":\"\",\"district\":\"\",\"street_number\":\"\",\"city_code\":131},\"address\":\"北京市\",\"point\":{\"x\":\"12958160.97\",\"y\":\"4825907.72\"}},\"status\":0}', '北京市', '北京市', '131', '12958160.97', '4825907.72', '1495034935', '123');
INSERT INTO `sys_baidu_log` VALUES ('4', '223.72.71.218', '{\"address\":\"CN|北京|北京|None|CMNET|0|0\",\"content\":{\"address_detail\":{\"province\":\"北京市\",\"city\":\"北京市\",\"street\":\"\",\"district\":\"\",\"street_number\":\"\",\"city_code\":131},\"address\":\"北京市\",\"point\":{\"x\":\"12958160.97\",\"y\":\"4825907.72\"}},\"status\":0}', '北京市', '北京市', '131', '12958160.97', '4825907.72', '1495035216', '123');

-- ----------------------------
-- Table structure for `sys_permission_init`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_init`;
CREATE TABLE `sys_permission_init` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `permission_init` varchar(255) DEFAULT NULL COMMENT '需要具备的权限',
  `sort` int(50) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='权限初始化表';

-- ----------------------------
-- Records of sys_permission_init
-- ----------------------------
INSERT INTO `sys_permission_init` VALUES ('1', '/static/**', 'anon,kickout', '1');
INSERT INTO `sys_permission_init` VALUES ('2', '/ajaxLogin', 'anon,kickout', '2');
INSERT INTO `sys_permission_init` VALUES ('3', '/logout', 'logout,kickout', '3');
INSERT INTO `sys_permission_init` VALUES ('4', '/add', 'perms[权限添加:权限删除],roles[100002],kickout', '4');
INSERT INTO `sys_permission_init` VALUES ('5', '/**', 'user,kickout', '5');
INSERT INTO `sys_permission_init` VALUES ('6', '/getGifCode', 'anon,kickout', '2');
INSERT INTO `sys_permission_init` VALUES ('7', '/kickout', 'anon', '2');

-- ----------------------------
-- Table structure for `sys_sms_conf`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_conf`;
CREATE TABLE `sys_sms_conf` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `facilitator` int(4) NOT NULL COMMENT '服务商(1、阿里大于，2、sendcloud)',
  `nid` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL COMMENT 'nid描述',
  `value` varchar(255) NOT NULL COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='短信配置表';

-- ----------------------------
-- Records of sys_sms_conf
-- ----------------------------
INSERT INTO `sys_sms_conf` VALUES ('1', '1', 'dev.appKey', '阿里大于appKey', '23617254');
INSERT INTO `sys_sms_conf` VALUES ('2', '1', 'dev.appSecret', '阿里大于secret', 'f4cd196ea0a1edfbe1357ea44c7d2fbb');
INSERT INTO `sys_sms_conf` VALUES ('3', '1', 'dev.ip', '阿里大于ip白名单(*表示支持所有ip)', '*');
INSERT INTO `sys_sms_conf` VALUES ('4', '1', 'dev.SmsFreeSignName', '阿里短信签名', '卡哇伊');
INSERT INTO `sys_sms_conf` VALUES ('5', '1', 'dev.url', '阿里大于请求url', 'https://gw.api.tbsandbox.com/router/rest');
INSERT INTO `sys_sms_conf` VALUES ('6', '1', 'product.appKey', '阿里大于appKey', '23617254');
INSERT INTO `sys_sms_conf` VALUES ('7', '1', 'product.appSecret', '阿里大于secret', 'f4cd196ea0a1edfbe1357ea44c7d2fbb');
INSERT INTO `sys_sms_conf` VALUES ('8', '1', 'product.ip', '阿里大于ip白名单(*表示支持所有ip)', '*');
INSERT INTO `sys_sms_conf` VALUES ('9', '1', 'product.SmsFreeSignName', '阿里短信签名', '卡哇伊');
INSERT INTO `sys_sms_conf` VALUES ('10', '1', 'product.url', '阿里大于请求url', 'https://eco.taobao.com/router/rest');

-- ----------------------------
-- Table structure for `sys_sms_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_log`;
CREATE TABLE `sys_sms_log` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '用户手机号',
  `msg` tinytext NOT NULL COMMENT '短信内容',
  `facilitator` int(4) NOT NULL COMMENT '服务商(1、阿里大于，2、sendCloud)',
  `env` varchar(12) NOT NULL DEFAULT '' COMMENT '发送环境(dev：测试环境，product:生产环境)',
  `create_time` varchar(32) NOT NULL COMMENT '添加时间',
  `status` int(4) NOT NULL COMMENT '状态(1、成功，2、失败)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='短信记录表';

-- ----------------------------
-- Records of sys_sms_log
-- ----------------------------
INSERT INTO `sys_sms_log` VALUES ('1', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493820076141', '2');
INSERT INTO `sys_sms_log` VALUES ('2', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493820292378', '2');
INSERT INTO `sys_sms_log` VALUES ('3', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493821238845', '1');
INSERT INTO `sys_sms_log` VALUES ('4', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493821273713', '2');
INSERT INTO `sys_sms_log` VALUES ('5', '18811328493', '尊敬的用户，您的本次验证码是：1314258，请勿将验证码转告他人。如非本人操作，请致电：18811328493', '1', 'product', '1493826062', '1');
INSERT INTO `sys_sms_log` VALUES ('6', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493826062', '1');
INSERT INTO `sys_sms_log` VALUES ('7', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493826062', '1');
INSERT INTO `sys_sms_log` VALUES ('8', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493826062', '1');
INSERT INTO `sys_sms_log` VALUES ('9', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'product', '1493826062', '1');
INSERT INTO `sys_sms_log` VALUES ('10', '18811328493', '尊敬的${mobilephone},恭喜您注册成为${commpany}的会员', '1', 'dev', '1493826062', '2');

-- ----------------------------
-- Table structure for `sys_sms_template`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_template`;
CREATE TABLE `sys_sms_template` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` varchar(32) NOT NULL COMMENT 'nid',
  `desc` varchar(32) NOT NULL COMMENT '字段描述',
  `msg` varchar(255) NOT NULL COMMENT '模版内容',
  `create_time` int(12) NOT NULL COMMENT '创建时间',
  `status` int(4) NOT NULL COMMENT '状态(1、开启，2、关闭)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_sms_template
-- ----------------------------
INSERT INTO `sys_sms_template` VALUES ('1', 'register', '注册成功后发送', '尊敬的用户，您的本次验证码是：${code}，请勿将验证码转告他人。如非本人操作，请致电：18811328493', '1493825028', '1');

DROP TABLE IF EXISTS 'sys_weather_code'
CREATE TABLE `sys_weather_code` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `citycode` varchar(32) NOT NULL COMMENT '城市代码',
  `cityname` varchar(32) NOT NULL COMMENT '城市名字',
  `pinyin` varchar(20) NOT NULL COMMENT '城市对应的拼音',
  `province` varchar(32) NOT NULL COMMENT '所在省份',
  `create_time` int(12) NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='天气预报城市编码';