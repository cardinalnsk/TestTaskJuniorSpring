-- password=password
INSERT INTO user_entity(id, username, balance, password, phone_number)
VALUES (1, 'Admin', 1000.00, '$2a$10$ceE4QQYDhI7v3mLuoWTOLOWxE3bVX7M9VsgSmDFK/XNh9LcTz7qvC',
        '+7912345678');

INSERT INTO user_entity_role(user_entity_id, role_id)
VALUES (1, 1);

DO
$$
    DECLARE
        i              INT := 0;
        user_balance   NUMERIC(38, 2);
        payment_amount NUMERIC(38, 2);
    BEGIN
        -- Получаем текущий баланс пользователя
        SELECT balance INTO user_balance FROM user_entity WHERE id = 1;

        FOR i IN 1..50
            LOOP
                -- Генерируем случайную сумму оплаты от 1 до 50, чтобы не уйти в минус
                payment_amount := ROUND((RANDOM() * 50)::NUMERIC, 2);

                -- Проверяем, достаточно ли средств на балансе пользователя
                IF (user_balance - payment_amount) >= 0 THEN
                    -- Вычитаем сумму оплаты из баланса пользователя
                    user_balance := user_balance - payment_amount;

                    -- Вставляем запись об оплате
                    INSERT INTO payment (amount, created_at, phone_number)
                    VALUES (payment_amount, NOW() + (i || ' minutes')::INTERVAL, '1234567890');

                    -- Связываем оплату с историей платежей пользователя
                    INSERT INTO user_entity_payment_history (user_entity_id, payment_history_id)
                    VALUES (1, (SELECT MAX(id) FROM payment));
                ELSE
                    -- Если средств недостаточно, прерываем цикл
                    EXIT;
                END IF;
            END LOOP;

        -- Обновляем баланс пользователя
        UPDATE user_entity SET balance = user_balance WHERE id = 1;
    END;
$$;

