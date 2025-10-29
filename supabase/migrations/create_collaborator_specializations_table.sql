r-- Migration script to create collaborator_specializations table
-- This table stores detailed specialization information for collaborators

CREATE TABLE IF NOT EXISTS collaborator_specializations (
    specialization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    collaborator_id UUID NOT NULL,
    specialization_name VARCHAR(255) NOT NULL,
    specialization_type VARCHAR(100),
    description TEXT,
    years_of_experience INTEGER,
    certifications JSONB,
    skills JSONB,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    proficiency_level VARCHAR(20) NOT NULL DEFAULT 'INTERMEDIATE',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraint
    CONSTRAINT fk_collaborator_specializations_collaborator_id 
        FOREIGN KEY (collaborator_id) 
        REFERENCES collaborators(collaborator_id) 
        ON DELETE CASCADE,
    
    -- Check constraints
    CONSTRAINT chk_proficiency_level 
        CHECK (proficiency_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')),
    
    CONSTRAINT chk_status 
        CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    
    CONSTRAINT chk_years_of_experience 
        CHECK (years_of_experience IS NULL OR years_of_experience >= 0)
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_collaborator_id 
    ON collaborator_specializations(collaborator_id);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_specialization_name 
    ON collaborator_specializations(specialization_name);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_specialization_type 
    ON collaborator_specializations(specialization_type);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_proficiency_level 
    ON collaborator_specializations(proficiency_level);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_status 
    ON collaborator_specializations(status);

CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_is_primary 
    ON collaborator_specializations(is_primary);

-- Create composite index for common queries
CREATE INDEX IF NOT EXISTS idx_collaborator_specializations_collaborator_status 
    ON collaborator_specializations(collaborator_id, status);

-- Add trigger to automatically update updated_at timestamp
CREATE OR REPLACE FUNCTION update_collaborator_specializations_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_collaborator_specializations_updated_at
    BEFORE UPDATE ON collaborator_specializations
    FOR EACH ROW
    EXECUTE FUNCTION update_collaborator_specializations_updated_at();

-- Insert some sample data
INSERT INTO collaborator_specializations (
    collaborator_id,
    specialization_name,
    specialization_type,
    description,
    years_of_experience,
    certifications,
    skills,
    is_primary,
    proficiency_level,
    status
) VALUES 
-- Sample data (you'll need to replace with actual collaborator IDs)
(
    (SELECT collaborator_id FROM collaborators LIMIT 1),
    'Speech Therapy',
    'THERAPY',
    'Specialized in speech and language development for children',
    5,
    '{"certifications": ["ASHA CCC-SLP", "State License"], "expiry_dates": {"ASHA CCC-SLP": "2025-12-31"}}'::jsonb,
    '{"skills": ["Articulation Therapy", "Language Development", "Fluency Therapy"], "age_groups": ["0-3", "3-6", "6-12"]}'::jsonb,
    true,
    'ADVANCED',
    'ACTIVE'
),
(
    (SELECT collaborator_id FROM collaborators LIMIT 1),
    'Occupational Therapy',
    'THERAPY',
    'Focus on fine motor skills and sensory integration',
    3,
    '{"certifications": ["AOTA NBCOT", "State License"], "expiry_dates": {"AOTA NBCOT": "2024-12-31"}}'::jsonb,
    '{"skills": ["Fine Motor Skills", "Sensory Integration", "Handwriting"], "age_groups": ["3-6", "6-12"]}'::jsonb,
    false,
    'INTERMEDIATE',
    'ACTIVE'
);

-- Add comment to table
COMMENT ON TABLE collaborator_specializations IS 'Stores detailed specialization information for collaborators';
COMMENT ON COLUMN collaborator_specializations.specialization_id IS 'Unique identifier for the specialization';
COMMENT ON COLUMN collaborator_specializations.collaborator_id IS 'Reference to the collaborator';
COMMENT ON COLUMN collaborator_specializations.specialization_name IS 'Name of the specialization (e.g., Speech Therapy)';
COMMENT ON COLUMN collaborator_specializations.specialization_type IS 'Type of specialization (THERAPY, ASSESSMENT, EDUCATION)';
COMMENT ON COLUMN collaborator_specializations.description IS 'Detailed description of the specialization';
COMMENT ON COLUMN collaborator_specializations.years_of_experience IS 'Years of experience in this specialization';
COMMENT ON COLUMN collaborator_specializations.certifications IS 'JSON object containing certification information';
COMMENT ON COLUMN collaborator_specializations.skills IS 'JSON object containing skills and competencies';
COMMENT ON COLUMN collaborator_specializations.is_primary IS 'Whether this is the primary specialization';
COMMENT ON COLUMN collaborator_specializations.proficiency_level IS 'Level of proficiency (BEGINNER, INTERMEDIATE, ADVANCED, EXPERT)';
COMMENT ON COLUMN collaborator_specializations.status IS 'Current status of the specialization';
