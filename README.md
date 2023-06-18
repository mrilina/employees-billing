# Сервис реализации рестов для работы с балльным счетом служащего
Разработанный сервис моделирует процесс по обработке балльного счета служащего.
Поддерживаются такие операции как: 
 - создание служащего, получение информации о служащем, удаление сведений о служащем.
 - получение баланса с балльного счета, начисление средств, списание баллов, удаление балльного счета, получение информаци о балльном счете.
У одного служащего может быть только один балльный счет, количество баллов на нем не может быть отрицательным. 
При попытке списать большее количество баллов, чем имеется на счет, выдается сообщение и операция не проводится.
Для достижения атомарности проводимых операций над балльным счетом используется стратегия оптимистической блокировки.
Для каждой успешной операции ведется запись в таблицу историй, где видно когда, во сколько и сколько баллов было начислено или списано.


## Общая информация и структура проекта

Внешние проектные ресурсы:

- пакет библиотек фреймворка Spring Boot библиотеки spring-boot-starter-data-jpa, spring-boot-starter-web;
- библиотека для тестирования Mockito: 
  - Mockito представляет собой фреймворк для автоматического юнит-тестирования программ;
- lombok - библиотека для сокращения кода в классах и расширения функциональности языка Java;
- spring-boot-starter-test - билиотека для тестирования;
- mysql-connector-j - библиотека для работы с базой данных MySQL;
- validation-api - бибилотека для вализации (javax.validation)

Сервис построен на Java SE 17.

Структурно, проект разделен на следующие пакеты:
- aspect - пакет, реализующий сквозную функциональность, выполняет логирование начала и окончания работы методов репозитория;
- controller - пакет, содержащий ресты для обработки приложения;
- entity - пакет, содержащий описание сущностей приложения;
- exception - пакет для обработки исключительных ситуаций;
- repository - пакет, содержащий методы для работы с репозиторием;
- service - пакет, содержащий сервисные методы;
- validation - пакет содержит собственные аннотации для валидации значений полей;
- тестирование - пакет содержит набор покрывающих приложение тестов.

Описание интерфейса рестов:
- Служащий 
  - GET             /api/employees/{id}                  Получение информации о служащем по его идентификатору
  - POST            /api/employees                       Создание служащего
  - DELETE          /api/employees/{id}                  Удаление информации о служащем по его идентификатору
- Балльный счет
  - GET             /api/accounts/{id}/balance           Получение баланса с балльного счета по его идентификатору
  - GET             /api/accounts/{id}                   Получение информации о балльном счете 
  - PUT             /api/accounts/{id}/deposit/{amount}  Начисление средств на балльный счет (указываются идентификатор и количество баллов)
  - PUT             /api/accounts/{id}/withdraw/{amount} Списание баллов со счета (указываются идентификатор и количество баллов)
  - DELETE          /api/accounts/{id}                   Удаление сведений о балльном счете по его идентификатору
- История
  - GET             /api/accounts/{id}/history           Просмотр истории операций

## Сборка сервиса
### Используемое ПО
Для развертывания и сборки компонент сервиса на компьютере должны быть предустановлены:
1. Java SDK ver. 17+
2. maven ver. 3.8+
3. База данных MySQL, скрипты для создания таблиц приведены ниже.

Перед сборкой убедитесь что переменная окружения JAVA_HOME указывает на корневой каталог Java SDK 17-й версии.

Параметры подключения к базе данных
url=jdbc:mysql://localhost:3306/billing_db?useSSL=false
username=billing_user
password=billingPassword@270

Скрипты для создания таблиц БД:

```shell
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


/** Добавление тестовых значений. */
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
```