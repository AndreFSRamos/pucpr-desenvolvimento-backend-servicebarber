CREATE TABLE enterprises
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    address         VARCHAR(255) NOT NULL,
    user_id         BIGINT       NOT NULL,
    CONSTRAINT fk_user_enterprise FOREIGN KEY (user_id) REFERENCES users (id),
    sys_user_insert BIGINT,
    sys_date_insert DATETIME,
    sys_user_update BIGINT,
    sys_date_update DATETIME,
    sys_active      BOOLEAN DEFAULT TRUE
);
