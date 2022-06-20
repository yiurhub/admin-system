create table QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(190) not null,
    TRIGGER_GROUP   varchar(190) not null,
    CRON_EXPRESSION varchar(120) not null,
    TIME_ZONE_ID    varchar(80)  null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_CRON_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

INSERT INTO `admin-system`.QRTZ_CRON_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, CRON_EXPRESSION, TIME_ZONE_ID) VALUES ('adminSystemScheduler', 'com.yiur.admin.task.ValidMessageTaskc9ebef8a-64db-4c42-a89b-2cd27dd1e63e', 'message', '0 0 16 15 6 ? 2023', 'UTC');
