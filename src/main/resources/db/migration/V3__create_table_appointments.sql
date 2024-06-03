CREATE TABLE appointments
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    enterprise_id    BIGINT   NOT NULL,
    user_id          BIGINT   NOT NULL,
    appointment_time DATETIME NOT NULL,
    CONSTRAINT fk_enterprise_appointment FOREIGN KEY (enterprise_id) REFERENCES enterprises (id),
    CONSTRAINT fk_user_appointment FOREIGN KEY (user_id) REFERENCES users (id),
    sys_user_insert  BIGINT,
    sys_date_insert  DATETIME,
    sys_user_update  BIGINT,
    sys_date_update  DATETIME,
    sys_active       BOOLEAN DEFAULT TRUE
);
