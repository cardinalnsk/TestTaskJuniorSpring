CREATE TABLE IF NOT EXISTS payment
(
    id           BIGSERIAL PRIMARY KEY,
    balance      NUMERIC(38, 2),
    date         TIMESTAMP,
    phone_number VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_entity
(
    id           BIGSERIAL PRIMARY KEY,
    username     VARCHAR(50),
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    email        VARCHAR(100),
    gender       VARCHAR(10),
    birth_date   DATE,
    balance      NUMERIC(38, 2),
    password     VARCHAR(255),
    phone_number VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_entity_payment_history
(
    user_entity_id     BIGINT NOT NULL
        CONSTRAINT fk_user_entity_payment REFERENCES user_entity,
    payment_history_id BIGINT NOT NULL
        CONSTRAINT uk_payment_history_id UNIQUE
        CONSTRAINT fk_payment REFERENCES payment
);


CREATE TABLE IF NOT EXISTS user_role
(
    id        BIGSERIAL PRIMARY KEY,
    authority VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS user_entity_role
(
    user_entity_id integer not null,
    role_id        integer not null
);


ALTER TABLE user_entity_role
    ADD CONSTRAINT user_accounts_roles__user_roles__fk
        FOREIGN KEY (role_id) REFERENCES user_role (id);

ALTER TABLE user_entity_role
    ADD CONSTRAINT user_accounts_roles__user_accounts__fk
        FOREIGN KEY (user_entity_id) REFERENCES user_entity (id);

ALTER TABLE user_entity_role
    ADD CONSTRAINT user_accounts_roles_unique
        UNIQUE (user_entity_id, role_id);