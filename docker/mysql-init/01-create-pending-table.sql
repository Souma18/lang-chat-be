CREATE TABLE IF NOT EXISTS pending_conversation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_username VARCHAR(255) NOT NULL,
    to_username VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_pending_pair UNIQUE (from_username, to_username, status)
);

CREATE INDEX IF NOT EXISTS idx_pending_to_user_status
    ON pending_conversation (to_username, status);

CREATE TABLE IF NOT EXISTS conversation_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_one VARCHAR(255) NOT NULL,
    user_two VARCHAR(255) NOT NULL,
    theme_id VARCHAR(50) NOT NULL DEFAULT 'DEFAULT',
    CONSTRAINT uq_conv_setting_pair UNIQUE (user_one, user_two)
);
