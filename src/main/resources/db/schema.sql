CREATE TABLE `category` (
  `id`       INT(11) NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(255)     DEFAULT NULL,
  `parentid` INT(11)          DEFAULT NULL,
  `remark`   VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `configure` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT,
  `name`   VARCHAR(255)     DEFAULT NULL,
  `val`    VARCHAR(255)     DEFAULT NULL,
  `remark` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `videostate` (
  `id`       INT(11) NOT NULL DEFAULT '0',
  `name`     VARCHAR(255)     DEFAULT NULL,
  `order`    INT(11)          DEFAULT NULL,
  `cssstyle` VARCHAR(255)     DEFAULT NULL,
  `remark`   VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `video` (
  `id`           INT(11) NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(255)     DEFAULT NULL,
  `intro`        VARCHAR(8192)    DEFAULT NULL,
  `edittime`     DATETIME         DEFAULT NULL,
  `categoryid`   INT(11)          DEFAULT NULL,
  `islive`       INT(11)          DEFAULT NULL,
  `url`          VARCHAR(255)     DEFAULT NULL,
  `oriurl`       VARCHAR(255)     DEFAULT NULL,
  `thumbnailurl` VARCHAR(255)     DEFAULT NULL,
  `videostateid` INT(11)          DEFAULT NULL,
  `remark`       VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_VIDEOSTATE` FOREIGN KEY (`videostateid`) REFERENCES `videostate` (`id`)
);