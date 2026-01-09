-- PostgreSQL syntax
CREATE TABLE IF NOT EXISTS pending_conversation (
    id BIGSERIAL PRIMARY KEY,
    from_username VARCHAR(255) NOT NULL,
    to_username VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_pending_pair UNIQUE (from_username, to_username, status)
);

CREATE INDEX IF NOT EXISTS idx_pending_to_user_status
    ON pending_conversation (to_username, status);

-- Trigger để auto-update updated_at (PostgreSQL không có ON UPDATE)
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_pending_conversation_updated_at 
    BEFORE UPDATE ON pending_conversation
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
