-- Create table: developmental_assessment_results
CREATE TABLE IF NOT EXISTS developmental_assessment_results (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT NOT NULL REFERENCES children(id),
    criteria_id BIGINT NOT NULL REFERENCES developmental_domain_items(id),
    program_id BIGINT NOT NULL REFERENCES developmental_programs(id),
    status VARCHAR(32) NOT NULL CHECK (status IN ('ACHIEVED','NOT_ACHIEVED','IN_PROGRESS')),
    notes TEXT,
    assessed_at DATE,
    assessor VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_dev_assess_results_child_id ON developmental_assessment_results(child_id);
CREATE INDEX IF NOT EXISTS idx_dev_assess_results_program_id ON developmental_assessment_results(program_id);
CREATE INDEX IF NOT EXISTS idx_dev_assess_results_criteria_id ON developmental_assessment_results(criteria_id);

-- Trigger to auto update updated_at
CREATE OR REPLACE FUNCTION trg_dev_assess_results_set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_dev_assess_results_set_updated_at ON developmental_assessment_results;
CREATE TRIGGER trg_dev_assess_results_set_updated_at
BEFORE UPDATE ON developmental_assessment_results
FOR EACH ROW EXECUTE FUNCTION trg_dev_assess_results_set_updated_at();


