-- Replace numeric id with UUID id for developmental_domain_items
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Recreate table with UUID PK (no data to migrate as per user)
DROP TABLE IF EXISTS developmental_domain_items CASCADE;

CREATE TABLE developmental_domain_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    domain_id UUID NOT NULL,
    title JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_domain FOREIGN KEY (domain_id) REFERENCES developmental_domains(id)
);

CREATE INDEX IF NOT EXISTS idx_developmental_domain_items_domain_id ON developmental_domain_items(domain_id);

