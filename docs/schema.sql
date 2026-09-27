CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE storage_nodes (
    id BIGSERIAL PRIMARY KEY,
    node_id VARCHAR(50) NOT NULL UNIQUE,
    host VARCHAR(255) NOT NULL,
    port INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'OFFLINE',
    free_space BIGINT NOT NULL DEFAULT 0,
    last_heartbeat TIMESTAMP
);


CREATE TABLE files (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT NOT NULL,
    content_hash VARCHAR(64),
    chunk_size INTEGER NOT NULL,
    total_chunks INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_files_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


CREATE TABLE file_chunks (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL,
    chunk_index INTEGER NOT NULL,
    chunk_size INTEGER NOT NULL,
    chunk_hash VARCHAR(64) NOT NULL,

    CONSTRAINT fk_chunks_file
        FOREIGN KEY (file_id)
        REFERENCES files(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_file_chunk
        UNIQUE (file_id, chunk_index)
);


CREATE TABLE chunk_replicas (
    id BIGSERIAL PRIMARY KEY,
    chunk_id BIGINT NOT NULL,
    node_id BIGINT NOT NULL,
    replica_number INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_replica_chunk
        FOREIGN KEY (chunk_id)
        REFERENCES file_chunks(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_replica_node
        FOREIGN KEY (node_id)
        REFERENCES storage_nodes(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_chunk_node
        UNIQUE (chunk_id, node_id)
);


CREATE TABLE file_permissions (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    permission VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_permission_file
        FOREIGN KEY (file_id)
        REFERENCES files(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_permission_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_file_user_permission
        UNIQUE (file_id, user_id)
);