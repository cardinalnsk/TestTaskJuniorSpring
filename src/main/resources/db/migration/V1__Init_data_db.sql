CREATE TABLE IF NOT EXISTS payment (
    id           BIGSERIAL PRIMARY KEY,
    balance      NUMERIC(38, 2),
    date         TIMESTAMP(6),
    phone_number VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_entity (
    id           BIGSERIAL PRIMARY KEY,
    balance      NUMERIC(38, 2),
    password     VARCHAR(255),
    phone_number VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_role (
    id        BIGSERIAL PRIMARY KEY,
    authority VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_entity_role (
    user_entity_id BIGINT NOT NULL CONSTRAINT fk_user_entity REFERENCES user_entity,
    role_id        BIGINT NOT NULL CONSTRAINT uk_user_role_id UNIQUE CONSTRAINT fk_user_role REFERENCES user_role,
    primary key (user_entity_id, role_id)
);



CREATE TABLE IF NOT EXISTS user_entity_payment_history (
    user_entity_id     BIGINT NOT NULL CONSTRAINT fk_user_entity_payment REFERENCES user_entity,
    payment_history_id BIGINT NOT NULL CONSTRAINT uk_payment_history_id UNIQUE CONSTRAINT fk_payment REFERENCES payment
);