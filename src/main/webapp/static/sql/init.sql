CREATE TABLE t_location(
  id INT PRIMARY KEY AUTO_INCREMENT,
  tagId VARCHAR(20) NOT NULL COMMENT '标签ID',
  positionX DOUBLE NOT NULL COMMENT '横坐标',
  positionY DOUBLE NOT NULL COMMENT '纵坐标',
  positionZ DOUBLE NOT NULL COMMENT '',
  smoothedPositionX DOUBLE NOT NULL COMMENT '滤波后的定位横坐标',
  smoothedPositionY DOUBLE NOT NULL COMMENT '滤波后的定位纵坐标',
  smoothedPositionZ DOUBLE NOT NULL COMMENT '',
  responseTimestampEpoch LONG NOT NULL COMMENT '标签位置的请求时间',
  positionTimestampEpoch LONG NOT NULL COMMENT '标签位置的读取时间'
) COMMENT '位置信息表';

CREATE TABLE t_quartz(
  id INT PRIMARY KEY AUTO_INCREMENT,
  time INT DEFAULT 100 COMMENT '定时时间',
  maxAge INT DEFAULT 100
) COMMENT '定时器设置';

INSERT INTO t_quartz VALUES (1, 100, 100);