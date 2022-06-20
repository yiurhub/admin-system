create table user
(
    uid              int auto_increment comment '唯一id'
        primary key,
    username         varchar(80)                             not null comment '用户名',
    password         varchar(80)                             not null comment '密码',
    name             varchar(50)                             not null comment '名称',
    face             varchar(150) default 'noface.jpg'       not null comment '头像',
    address          varchar(150)                            not null comment '地址',
    `desc`           varchar(255) default '这个人很懒！什么都没有留下...' not null comment '个人简介',
    registerDate     timestamp    default CURRENT_TIMESTAMP  not null comment '注册时间',
    lastLoginDate    timestamp                               null comment '上一次登陆时间',
    lastLoginAddress varchar(150)                            null comment '上一次登陆地址',
    perms            varchar(150)                            not null comment '用户权限',
    deleted          tinyint(1)   default 0                  not null comment '是否删除'
)
    comment '用户表' auto_increment = 6;

INSERT INTO `admin-system`.user (uid, username, password, name, face, address, `desc`, registerDate, lastLoginDate, lastLoginAddress, perms, deleted) VALUES (1, 'root', 'cj123', 'root', '89ca6002-d0c2-4bf5-aa3c-3c54984f6ba4.png', '湖南省长沙市', '这个人很懒！什么都没有留下...', '2022-06-07 16:54:36', '2022-06-18 19:34:08', '内网IP内网IP', 'root,admin,user', 0);
INSERT INTO `admin-system`.user (uid, username, password, name, face, address, `desc`, registerDate, lastLoginDate, lastLoginAddress, perms, deleted) VALUES (2, 'admin', '123', 'admin', 'noface.jpg', '浙江省杭州市', '这个人很懒！什么都没有留下...', '2022-06-10 00:32:12', '2022-06-16 06:21:42', '中国浙江省杭州市电信', 'user,admin', 0);
INSERT INTO `admin-system`.user (uid, username, password, name, face, address, `desc`, registerDate, lastLoginDate, lastLoginAddress, perms, deleted) VALUES (3, 'zhangsan', '123', '张三', 'noface.jpg', '浙江省杭州市', '这个人很懒！什么都没有留下...', '2022-06-10 00:32:41', null, null, 'user', 1);
INSERT INTO `admin-system`.user (uid, username, password, name, face, address, `desc`, registerDate, lastLoginDate, lastLoginAddress, perms, deleted) VALUES (4, 'lisi', '123', '李四', 'noface.jpg', '浙江省杭州市', '这个人很懒！什么都没有留下...', '2022-06-10 00:32:55', null, null, 'user', 1);
INSERT INTO `admin-system`.user (uid, username, password, name, face, address, `desc`, registerDate, lastLoginDate, lastLoginAddress, perms, deleted) VALUES (5, 'wamgwu', '123', '王五', 'noface.jpg', '浙江省杭州市', '这个人很懒！什么都没有留下...', '2022-06-10 00:33:30', null, null, 'user', 1);
