create table QRTZ_TRIGGERS
(
    SCHED_NAME     varchar(120) not null,
    TRIGGER_NAME   varchar(190) not null,
    TRIGGER_GROUP  varchar(190) not null,
    JOB_NAME       varchar(190) not null,
    JOB_GROUP      varchar(190) not null,
    DESCRIPTION    varchar(250) null,
    NEXT_FIRE_TIME bigint(13)   null,
    PREV_FIRE_TIME bigint(13)   null,
    PRIORITY       int          null,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint(13)   not null,
    END_TIME       bigint(13)   null,
    CALENDAR_NAME  varchar(190) null,
    MISFIRE_INSTR  smallint(2)  null,
    JOB_DATA       blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_TRIGGERS_ibfk_1
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create index IDX_QRTZ_T_C
    on QRTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);

create index IDX_QRTZ_T_G
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

create index IDX_QRTZ_T_J
    on QRTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index IDX_QRTZ_T_JG
    on QRTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);

create index IDX_QRTZ_T_NEXT_FIRE_TIME
    on QRTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_MISFIRE
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);

create index IDX_QRTZ_T_NFT_ST_MISFIRE
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);

create index IDX_QRTZ_T_NFT_ST_MISFIRE_GRP
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_G_STATE
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_N_STATE
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index IDX_QRTZ_T_STATE
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);

INSERT INTO `admin-system`.QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, JOB_NAME, JOB_GROUP, DESCRIPTION, NEXT_FIRE_TIME, PREV_FIRE_TIME, PRIORITY, TRIGGER_STATE, TRIGGER_TYPE, START_TIME, END_TIME, CALENDAR_NAME, MISFIRE_INSTR, JOB_DATA) VALUES ('adminSystemScheduler', 'com.yiur.admin.task.ValidMessageTaskc9ebef8a-64db-4c42-a89b-2cd27dd1e63e', 'message', 'com.yiur.admin.task.ValidMessageTask77fa2bd9-fb64-48e1-8d85-abf5fa211c5d', 'message', null, 1686844800000, -1, 5, 'WAITING', 'CRON', 1655359603000, 0, null, 0, 0x);
