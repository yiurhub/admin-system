create table message
(
    mid       int auto_increment comment '唯一id'
        primary key,
    identity  varchar(50)                         not null comment '身份',
    target    varchar(50)                         not null comment '消息目标',
    title     varchar(100)                        not null comment '消息标题',
    content   longtext                            not null comment '消息内容',
    sendDate  timestamp default CURRENT_TIMESTAMP not null comment '消息发送时间',
    validDate timestamp default CURRENT_TIMESTAMP not null comment '有效时间',
    level     int                                 not null comment '消息等级'
)
    comment '消息表' auto_increment = 2;

INSERT INTO `admin-system`.message (mid, identity, target, title, content, sendDate, validDate, level) VALUES (1, '45be3cee-1e24-49ba-ba55-dec505836723', 'system', 'hello!!!', '# 系统消息
``` java
public class Index {
  public static void main(String[] args) {
    System.out.printf("hello %s!%n", "${user}");
  }
}
```', '2022-06-16 06:11:27', '2023-06-15 16:00:00', 1);
