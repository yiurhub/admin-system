create table mail
(
    mailId int auto_increment comment '唯一id'
        primary key,
    uid    int           not null comment '用户id',
    mid    int           not null comment '消息id',
    state  int default 0 not null comment '邮件状态{0:未读,1:已读,2:回收}',
    constraint mail_ibfk_1
        foreign key (uid) references user (uid)
            on delete cascade,
    constraint mail_ibfk_2
        foreign key (mid) references message (mid)
            on delete cascade
)
    comment '用户邮件' auto_increment = 3;

create index mid
    on mail (mid);

create index uid
    on mail (uid);

INSERT INTO `admin-system`.mail (mailId, uid, mid, state) VALUES (1, 1, 1, 0);
INSERT INTO `admin-system`.mail (mailId, uid, mid, state) VALUES (2, 2, 1, 0);
