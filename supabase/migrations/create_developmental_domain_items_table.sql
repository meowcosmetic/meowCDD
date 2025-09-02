-- Create table: developmental_domain_items
CREATE TABLE IF NOT EXISTS developmental_domain_items (
    id BIGSERIAL PRIMARY KEY,
    domain_id UUID NOT NULL REFERENCES developmental_domains(id),
    code VARCHAR(20),
    title TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_dev_domain_items_domain_id ON developmental_domain_items(domain_id);

-- Trigger to auto update updated_at
CREATE OR REPLACE FUNCTION trg_dev_domain_items_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_dev_domain_items_set_updated_at ON developmental_domain_items;
CREATE TRIGGER trg_dev_domain_items_set_updated_at
BEFORE UPDATE ON developmental_domain_items
FOR EACH ROW EXECUTE FUNCTION trg_dev_domain_items_set_updated_at();


