INSERT INTO users
    (username, password, full_name, email, role, account_non_expired, account_non_locked, credentials_non_expired, sys_user_insert, sys_date_insert, sys_active)
VALUES
    ("admin", "$2a$10$foKH744fVkEwvsEeS6bHIeKuaaSMg31QkANklu20MGXQL/eAKNy1m", "Usuário Admin", "admin@gmail.com", "ADMIN", b'1', b'1', b'1', 1, NOW(), b'1')