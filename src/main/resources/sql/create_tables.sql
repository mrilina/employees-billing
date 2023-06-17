DROP DATABASE IF EXISTS billing_db;
CREATE DATABASE billing_db CHARSET = utf8 COLLATE = utf8_general_ci;
USE billing_db;

/** Сведения о служащем. */
CREATE TABLE employees (
                           EMP_ID          BIGINT NOT NULL AUTO_INCREMENT,
                           EMP_USERNAME    VARCHAR(252) NOT NULL,
                           PRIMARY KEY (EMP_ID)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/** Сведения о балльном счете. */
CREATE TABLE accounts (
                          ACC_ID           BIGINT NOT NULL AUTO_INCREMENT,
                          VERSION          BIGINT NOT NULL DEFAULT 0,
                          ACC_BALANCE      DOUBLE NOT NULL,
                          PRIMARY KEY (ACC_ID),
                          FOREIGN KEY (ACC_ID) REFERENCES employees(EMP_ID)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

/** История операций. */
CREATE TABLE history (
                         HST_ID                BIGINT NOT NULL AUTO_INCREMENT,
                         HST_ACC_ID            BIGINT NOT NULL,
                         HST_TYPE              VARCHAR(252) NOT NULL,
                         HST_STATUS            VARCHAR(252) NOT NULL,
                         HST_INITIAL_AMOUNT    DOUBLE NOT NULL,
                         HST_FINAL_AMOUNT      DOUBLE NOT NULL,
                         HST_DATE              TIMESTAMP NOT NULL,
                         PRIMARY KEY(HST_ID),
                         FOREIGN KEY(HST_ACC_ID) REFERENCES accounts(ACC_ID)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

insert into employees(EMP_USERNAME) values ('Иванов Иван');
insert into employees(EMP_USERNAME) values ('Петрова Мария');
insert into employees(EMP_USERNAME) values ('Сидорова Ольга');
insert into employees(EMP_USERNAME) values ('Смирнов Георгий');
insert into employees(EMP_USERNAME) values ('Павлова Оксана');
insert into employees(EMP_USERNAME) values ('Кириллов Роман');

insert into accounts(ACC_ID, ACC_BALANCE) values (1, 100);
insert into accounts(ACC_ID, ACC_BALANCE) values (2, 200);
insert into accounts(ACC_ID, ACC_BALANCE) values (3, 300);
insert into accounts(ACC_ID, ACC_BALANCE) values (4, 400);
insert into accounts(ACC_ID, ACC_BALANCE) values (5, 500);
insert into accounts(ACC_ID, ACC_BALANCE) values (6, 600);