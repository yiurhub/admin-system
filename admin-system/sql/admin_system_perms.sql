create table perms
(
    pid  int auto_increment comment '唯一id'
        primary key,
    perm varchar(50) not null comment '权限'
)
    comment '权限表' auto_increment = 7;

INSERT INTO `admin-system`.perms (pid, perm) VALUES (4, 'root');
INSERT INTO `admin-system`.perms (pid, perm) VALUES (5, 'admin');
INSERT INTO `admin-system`.perms (pid, perm) VALUES (6, 'user');
