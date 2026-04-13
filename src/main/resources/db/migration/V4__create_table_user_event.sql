CREATE TABLE user_event
(
    user_id  BIGINT NOT NULL,
    event_id BIGINT NOT NULL,

    CONSTRAINT pk_user_event PRIMARY KEY (user_id, event_id),

    CONSTRAINT fk_user_event_user FOREIGN KEY (user_id)
        REFERENCES app_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_event_event FOREIGN KEY (event_id)
        REFERENCES event (id) ON DELETE CASCADE
);

-- Index required to optimize reverse lookup queries (e.g., "get all users for a specific event")
CREATE INDEX idx_user_event_event_id ON user_event (event_id);