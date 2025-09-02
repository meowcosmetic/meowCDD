-- Create table: developmental_item_criteria
CREATE TABLE IF NOT EXISTS developmental_item_criteria (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL REFERENCES developmental_domain_items(id),
    code VARCHAR(20),
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_dev_item_criteria_item_id ON developmental_item_criteria(item_id);

-- Trigger to auto update updated_at
CREATE OR REPLACE FUNCTION trg_dev_item_criteria_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_dev_item_criteria_set_updated_at ON developmental_item_criteria;
CREATE TRIGGER trg_dev_item_criteria_set_updated_at
BEFORE UPDATE ON developmental_item_criteria
FOR EACH ROW EXECUTE FUNCTION trg_dev_item_criteria_set_updated_at();


