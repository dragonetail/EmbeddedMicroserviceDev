## XAHI Spring Base Web Starter 示例启动项目

### 测试MySQL数据库初始化

#### Windows OS
```
DROP DATABASE IF EXISTS uaa;
CREATE DATABASE uaa DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP USER IF EXISTS uaa;
CREATE USER 'uaa'@'%' IDENTIFIED BY 'uaa';
GRANT all privileges ON uaa.* TO 'uaa'@'%';
flush privileges;
```

#### Mac OS
```
DROP DATABASE IF EXISTS uaa;
CREATE DATABASE uaa DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP USER IF EXISTS uaa;
CREATE USER uaa@'%' IDENTIFIED WITH mysql_native_password BY 'uaa';
GRANT ALL PRIVILEGES ON uaa.* TO 'uaa'@'%';
FLUSH PRIVILEGES;
```






 
 