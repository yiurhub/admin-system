create table QRTZ_LOCKS
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
);

INSERT INTO `admin-system`.QRTZ_LOCKS (SCHED_NAME, LOCK_NAME) VALUES ('adminSystemScheduler', 'TRIGGER_ACCESS');
