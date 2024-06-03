CREATE TABLE users
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    username                VARCHAR(255) NOT NULL,
    password                VARCHAR(255) NOT NULL,
    full_name               VARCHAR(255) NOT NULL,
    email                   VARCHAR(255) NOT NULL,
    role                    ENUM('COMMON','MANAGER','ADMIN')  NOT NULL DEFAULT 'COMMON',
    account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    sys_user_insert         BIGINT,
    sys_date_insert         DATETIME,
    sys_user_update         BIGINT,
    sys_date_update         DATETIME,
    sys_active              BOOLEAN     NOT NULL DEFAULT TRUE,
    UNIQUE KEY unique_username (username, email)
);
