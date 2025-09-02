-- Create table: developmental_programs
CREATE TABLE IF NOT EXISTS developmental_programs (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name TEXT NOT NULL,         -- JSON string for multilingual name
    description TEXT,           -- JSON string for multilingual description
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_developmental_programs_code ON developmental_programs(code);

-- Trigger to auto update updated_at
CREATE OR REPLACE FUNCTION trg_dev_programs_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_dev_programs_set_updated_at ON developmental_programs;
CREATE TRIGGER trg_dev_programs_set_updated_at
BEFORE UPDATE ON developmental_programs
FOR EACH ROW EXECUTE FUNCTION trg_dev_programs_set_updated_at();


